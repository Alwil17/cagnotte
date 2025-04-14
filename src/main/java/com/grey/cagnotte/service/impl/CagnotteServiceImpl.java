package com.grey.cagnotte.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.payload.response.UserResponse;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.UserRepository;
import com.grey.cagnotte.service.CagnotteService;
import com.grey.cagnotte.service.UserService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
    private final UserService userService;

    @Value("${app.accessTokenValidityMinutes:1440}") // Par défaut, 1440 minutes (24h)
    private long tokenValidityMinutes;

    @Override
    public List<CagnotteResponse> getAllCagnottes() {
        List<Cagnotte> cagnottes = cagnotteRepository.findAll();
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CagnotteResponse> getPublicCagnottes() {
        List<Cagnotte> cagnottes = cagnotteRepository.findAll();
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CagnotteResponse> getAllMyCagnottes() {
        User user = userService.getMe();
        List<Cagnotte> cagnottes = cagnotteRepository.findAllMyCagnotte(user.getId());
        return cagnottes.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CagnotteResponse getCagnotteById(long cagnotteId) {
        Cagnotte cagnotte
                = cagnotteRepository.findById(cagnotteId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Cagnotte with given id not found", NOT_FOUND));

        return mapToResponse(cagnotte);
    }

    @Override
    public CagnotteResponse getCagnotteBySlug(String cagnotteSlug) {
        Cagnotte cagnotte
                = cagnotteRepository.findBySlug(cagnotteSlug)
                .orElseThrow(
                        () -> new CagnotteCustomException("Cagnotte with given id not found", NOT_FOUND));
        return mapToResponse(cagnotte);
    }

    @Override
    public long addCagnotte(CagnotteRequest cagnotteRequest) {
        log.info("CagnotteServiceImpl | addCagnotte is called");

        User user = userService.getMe();

        Cagnotte cagnotte = Cagnotte.builder()
                .label(cagnotteRequest.getLabel())
                .reference(cagnotteRequest.getReference())
                .organizer(cagnotteRequest.getOrganizer())
                .concerns(cagnotteRequest.getConcerns())
                .dateCreation(LocalDateTime.now())
                .dateDue(cagnotteRequest.getDateDue())
                .goalAmount(cagnotteRequest.getGoalAmount())
                .collectedAmount(cagnotteRequest.getCollectedAmount())
                .personalizedMessage(cagnotteRequest.getPersonalizedMessage())
                .image(cagnotteRequest.getImage())
                .eventLocation(cagnotteRequest.getEventLocation())
                .isPublic(cagnotteRequest.isPublic())
                .url(cagnotteRequest.getUrl())
                .accessToken(cagnotteRequest.getAccessToken())
                .accessTokenExpiresAt(cagnotteRequest.getAccessTokenExpiresAt())
                .build();
        // S'il n'y a pas d'utilisateur avec cet email,
        // il ne fera donc rien
        if(user != null) cagnotte.setUser(user);

        String slug = Str.slug(cagnotteRequest.getLabel());
        cagnotte.setSlug(slug);
        cagnotte.setUrl("/" + slug);

        // Si la cagnotte est privée et que l'on souhaite autoriser l'accès via un lien sécurisé,
        // on génère un access token.
        if (!cagnotte.isPublic()) {
            String accessToken = UUID.randomUUID().toString(); // ou une autre méthode de génération sécurisée
            cagnotte.setAccessToken(accessToken);
            // Optionnel: définir une date d'expiration
            cagnotte.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(tokenValidityMinutes));
        }

        cagnotte = cagnotteRepository.save(cagnotte);

        log.info("CagnotteServiceImpl | addCagnotte | Cagnotte Created | Id : " + cagnotte.getId());
        return cagnotte.getId();
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

    /**
     * Method for recovering a private kitty with access logic.
     */
    public CagnotteResponse getPrivateCagnotte(String slug, String accessToken) {
        User user = userService.getMe();
        CagnotteResponse cagnotte = getCagnotteBySlug(slug);

        // If the token is present and valid, allow access
        if (accessToken != null && isValidAccessToken(cagnotte, accessToken)) {
            return cagnotte;
        }
        // Otherwise, verify via RBAC (e.g., if the user is owner or admin)
        // Verification will be done via your security component, or here by code
        if (user.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                        || (user.getId() == cagnotte.getUser().getId())) {
            return cagnotte;
        }
        throw new AccessDeniedException("Access denied to this cagnotte");
    }

    @Override
    public void editCagnotte(CagnotteRequest cagnotteRequest, long cagnotteId) {
        log.info("CagnotteServiceImpl | editCagnotte is called");

        User user = userService.getMe();

        Cagnotte cagnotte
                = cagnotteRepository.findById(cagnotteId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Cagnotte with given Id not found",
                        NOT_FOUND
                ));

        cagnotte.setLabel(cagnotteRequest.getLabel());
        cagnotte.setSlug(Str.slug(cagnotteRequest.getLabel()));
        cagnotte.setReference(cagnotteRequest.getReference());
        cagnotte.setOrganizer(cagnotteRequest.getOrganizer());
        cagnotte.setConcerns(cagnotteRequest.getConcerns());
        cagnotte.setDateDue(cagnotteRequest.getDateDue());
        cagnotte.setGoalAmount(cagnotteRequest.getGoalAmount());
        cagnotte.setCollectedAmount(cagnotteRequest.getCollectedAmount());
        cagnotte.setPersonalizedMessage(cagnotteRequest.getPersonalizedMessage());
        cagnotte.setImage(cagnotteRequest.getImage());
        cagnotte.setEventLocation(cagnotteRequest.getEventLocation());
        cagnotte.setUrl(cagnotteRequest.getUrl());
        cagnotte.setUpdated_at(LocalDateTime.now());
        // S'il n'y a pas d'utilisateur avec cet email,
        // il ne fera donc rien
        if(user != null) cagnotte.setUser(user);
        cagnotteRepository.save(cagnotte);

        log.info("CagnotteServiceImpl | editCagnotte | Cagnotte Updated | Cagnotte Id :" + cagnotte.getId());
    }

    @Override
    public void deleteCagnotteById(long cagnotteId) {
        log.info("Cagnotte id: {}", cagnotteId);

        if (!cagnotteRepository.existsById(cagnotteId)) {
            log.info("Im in this loop {}", !cagnotteRepository.existsById(cagnotteId));
            throw new CagnotteCustomException(
                    "Cagnotte with given with Id: " + cagnotteId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Cagnotte with id: {}", cagnotteId);
        cagnotteRepository.deleteById(cagnotteId);
    }

    public CagnotteResponse mapToResponse(Cagnotte cagnotte){
        CagnotteResponse cagnotteResponse = new CagnotteResponse();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        cagnotteResponse = mapper.convertValue(cagnotte, CagnotteResponse.class);

        return cagnotteResponse;
    }
}
