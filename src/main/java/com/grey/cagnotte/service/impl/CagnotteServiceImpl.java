package com.grey.cagnotte.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.enums.StateEnum;
import com.grey.cagnotte.events.CagnotteDraftCreatedEvent;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.CategoryRepository;
import com.grey.cagnotte.repository.ParticipationRepository;
import com.grey.cagnotte.repository.StateRepository;
import com.grey.cagnotte.service.CagnotteService;
import com.grey.cagnotte.service.UserService;
import com.grey.cagnotte.utils.SecurityUtils;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CagnotteServiceImpl implements CagnotteService {
    private final String NOT_FOUND = "CAGNOTTE_NOT_FOUND";

    private final CagnotteRepository cagnotteRepository;
    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final StateRepository stateRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${app.accessTokenValidityMinutes:1440}") // Par défaut, 1440 minutes (24h)
    private long tokenValidityMinutes;

    @Override
    public List<CagnotteResponse> getAllCagnottes() {
        List<Cagnotte> cagnottes = cagnotteRepository.findAll();
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CagnotteResponse> getPublicCagnottes() {
        List<Cagnotte> cagnottes = cagnotteRepository.findAllByPublicTrue();
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CagnotteResponse> getAllMyCagnottes() {
        User user = userService.getMe();
        List<Cagnotte> cagnottes = cagnotteRepository.findAllMyCagnotte(user.getId());
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CagnotteResponse addCagnotte(CagnotteRequest cagnotteRequest) {
        log.info("CagnotteServiceImpl | addCagnotte is called");

        User user = userService.getMe();

        Cagnotte cagnotte = Cagnotte.builder()
                .label(cagnotteRequest.getLabel())
                .concerns(cagnotteRequest.getConcerns())
                .dateCreation(LocalDateTime.now())
                .description(cagnotteRequest.getDescription())
                .eventLocation(cagnotteRequest.getEventLocation())
                .isPublic(cagnotteRequest.isPublic())
                .category(categoryRepository.findBySlug(cagnotteRequest.getCategory_slug()).orElseThrow())
                .build();
        // If there is no user with this email,
        // it will do nothing
        if(user != null) cagnotte.setUser(user);

        if(cagnotteRequest.getDateDue() != null) cagnotte.setDateDue(cagnotteRequest.getDateDue());
        if(cagnotteRequest.getGoalAmount() != 0) cagnotte.setGoalAmount(cagnotteRequest.getGoalAmount());
        if(cagnotteRequest.getCollectedAmount() != 0) cagnotte.setCollectedAmount(cagnotteRequest.getCollectedAmount());
        if(cagnotteRequest.getParticipationAmount() != 0) cagnotte.setParticipationAmount(cagnotteRequest.getParticipationAmount());

        if(cagnotteRequest.getImage() != null && !cagnotteRequest.getImage().isBlank()){
            cagnotte.setImage(cagnotteRequest.getImage());
        }
        else {
            String uri = getClass().getResource("/images/cagnotte-default.jpg").toString();
            cagnotte.setImage(uri);
        }

        // If the fundraiser is private and you want to authorize access via a secure link,
        // we generate an access token.
        if (!cagnotte.isPublic()) {
            String accessToken = UUID.randomUUID().toString(); // ou une autre méthode de génération sécurisée
            cagnotte.setAccessToken(accessToken);
            // Optional: Défine expiration date
            cagnotte.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(tokenValidityMinutes));
        }

        cagnotte.setState(stateRepository.findByLabel(StateEnum.DRAFT.name()).orElseThrow());

        cagnotte = cagnotteRepository.save(cagnotte);

        String slug = Str.slug(cagnotte.getLabel());
        String url = String.format("%s-%08d", slug, cagnotte.getId());
        cagnotte.setSlug(slug);
        cagnotte.setUrl(url);

        cagnotte = cagnotteRepository.save(cagnotte);

        // Publish event if the state is DRAFT
        if("DRAFT".equalsIgnoreCase(cagnotte.getState().getLabel())) {
            eventPublisher.publishEvent(new CagnotteDraftCreatedEvent(this, cagnotte));
        }

        log.info("CagnotteServiceImpl | addCagnotte | CagnotteApplication Created | Id : " + cagnotte.getId());
        return mapToResponse(cagnotte);
    }

    @Override
    public CagnotteResponse getCagnotteById(long cagnotteId) {
        Cagnotte cagnotte
                = cagnotteRepository.findById(cagnotteId)
                .orElseThrow(
                        () -> new CagnotteCustomException("CagnotteApplication with given id not found", NOT_FOUND));

        return mapToResponse(cagnotte);
    }

    /**
     * Method for recovering a private or public cagnotte with access logic.
     */
    @Override
    public Cagnotte getCagnotteByUrl(String cagnotteUrl, boolean isPublic, String accessToken) {
        User user = userService.getMe();
        Cagnotte cagnotte
                = cagnotteRepository.findByUrl(cagnotteUrl)
                .orElseThrow(
                        () -> new CagnotteCustomException("CagnotteApplication with given URL not found", NOT_FOUND));
        // CHeck if cagnotte visibility is the same with method arg passed.
        if(cagnotte.isPublic() == isPublic){
            // In case the cagnotte is private, we will look for access token
            if(!cagnotte.isPublic()){
                // If the token is present and valid, allow access
                if (accessToken != null && isValidAccessToken(mapToResponse(cagnotte), accessToken)) {
                    return cagnotte;
                }
                // Otherwise, verify via RBAC (e.g., if the user is owner or admin)
                // Verification will be done via your security component, or here by code
                if (SecurityUtils.isAdmin(user)
                        || (user.getId() == cagnotte.getUser().getId())) {
                    return cagnotte;
                }
            }
            // Assuming the cagnotte is public.
            return cagnotte;
        }
        throw new CagnotteCustomException("You are trying to access public or private cagnotte through wrong url.", "WRONG_URL");
    }

    /**
     * Checks if the provided access token is valid for the fundraiser.
     */
    private boolean isValidAccessToken(CagnotteResponse cagnotte, String token) {
        if (cagnotte == null || token == null) {
            return false;
        }
        // Check that the token matches
        boolean match = token.equals(cagnotte.getAccessToken());
        // Check the expiration date, if set
        if (match && cagnotte.getAccessTokenExpiresAt() != null) {
            match = LocalDateTime.now().isBefore(cagnotte.getAccessTokenExpiresAt());
        }
        return match;
    }

    @Override
    public CagnotteResponse editCagnotte(CagnotteRequest cagnotteRequest, String cagnotteUrl) {
        log.info("CagnotteServiceImpl | editCagnotte is called");
        User user = userService.getMe();

        Cagnotte cagnotte
                = cagnotteRepository.findByUrl(cagnotteUrl)
                .orElseThrow(() -> new CagnotteCustomException(
                        "CagnotteApplication with given Url not found",
                        NOT_FOUND
                ));

        cagnotte.setLabel(cagnotteRequest.getLabel());
        cagnotte.setSlug(Str.slug(cagnotteRequest.getLabel()));
        cagnotte.setConcerns(cagnotteRequest.getConcerns());
        cagnotte.setDateDue(cagnotteRequest.getDateDue());
        cagnotte.setGoalAmount(cagnotteRequest.getGoalAmount());
        cagnotte.setCollectedAmount(cagnotteRequest.getCollectedAmount());
        cagnotte.setDescription(cagnotteRequest.getDescription());
        cagnotte.setImage(cagnotteRequest.getImage());
        cagnotte.setEventLocation(cagnotteRequest.getEventLocation());
        // S'il n'y a pas d'utilisateur avec cet email,
        // il ne fera donc rien
        if(user != null) cagnotte.setUser(user);
        if (user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                || (user.getId() == cagnotte.getUser().getId())) {
            cagnotteRepository.save(cagnotte);
            log.info("CagnotteServiceImpl | editCagnotte | CagnotteApplication Updated | CagnotteApplication Id :" + cagnotte.getId());
            return mapToResponse(cagnotte);
        }
        throw new AccessDeniedException("Access denied to this cagnotte");
    }

    @Override
    public void deleteCagnotteByUrl(String cagnotteUrl) {
        log.info("CagnotteApplication url: {}", cagnotteUrl);
        User user = userService.getMe();
        Cagnotte cagnotte
                = cagnotteRepository.findByUrl(cagnotteUrl)
                .orElseThrow(() -> new CagnotteCustomException(
                        "CagnotteApplication with given Url not found",
                        NOT_FOUND
                ));
        log.info("Deleting CagnotteApplication with url: {}", cagnotteUrl);

        if (user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                || (user.getId() == cagnotte.getUser().getId())) {
            cagnotteRepository.deleteById(cagnotte.getId());
        }
        throw new AccessDeniedException("Access denied to this cagnotte");
    }

    @Override
    public void addSubstractParticipationAmount(Cagnotte cagnotte, double amount) {
        log.info("CagnotteServiceImpl | addParticipationAmount is called");
        cagnotte.setParticipationAmount(cagnotte.getParticipationAmount() + amount);
        cagnotteRepository.save(cagnotte);
    }

    @Override
    public CagnotteResponse publishCagnotte(String url) {
        Cagnotte cagnotte
                = cagnotteRepository.findByUrl(url)
                .orElseThrow(
                        () -> new CagnotteCustomException("CagnotteApplication with given URL not found", NOT_FOUND));
        if(cagnotte.getState().getLabel().equals(StateEnum.DRAFT.name())){
            cagnotte.setState(stateRepository.findByLabel(StateEnum.ACTIVE.name()).orElseThrow());
            cagnotteRepository.save(cagnotte);
        }
        return mapToResponse(cagnotte);
    }

    public CagnotteResponse mapToResponse(Cagnotte cagnotte){
        CagnotteResponse cagnotteResponse = new CagnotteResponse();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        cagnotteResponse = mapper.convertValue(cagnotte, CagnotteResponse.class);

        int participationCount = participationRepository.countByCagnotteId(cagnotte.getId());
        cagnotteResponse.setParticipationCount(participationCount);

        return cagnotteResponse;
    }
}
