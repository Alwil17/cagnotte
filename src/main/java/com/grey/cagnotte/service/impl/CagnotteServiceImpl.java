package com.grey.cagnotte.service.impl;

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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CagnotteServiceImpl implements CagnotteService {
    private final String NOT_FOUND = "CAGNOTTE_NOT_FOUND";

    private final CagnotteRepository cagnotteRepository;
    private final UserService userService;


    @Override
    public List<Cagnotte> getAllCagnottes() {
        return cagnotteRepository.findAll();
    }

    @Override
    public CagnotteResponse getCagnotteById(long cagnotteId) {
        Cagnotte cagnotte
                = cagnotteRepository.findById(cagnotteId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Cagnotte with given id not found", NOT_FOUND));

        CagnotteResponse cagnotteResponse = new CagnotteResponse();

        /* Cette fonction aurait copié les données plus vite
        * Mais le User peut être null, donc, je préfère m'en prévenir.
        *
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        cagnotteResponse = mapper.convertValue(cagnotte, CagnotteResponse.class);
        */
        copyProperties(cagnotte, cagnotteResponse);


        if(cagnotte.getUser() != null) {
            UserResponse user = new UserResponse();
            copyProperties(cagnotte.getUser(), user);
            cagnotteResponse.setUser(user);
        }

        log.info("CagnotteServiceImpl |  getCagnotteById | cagnotteResponse :" + cagnotteResponse.toString());
        return cagnotteResponse;
    }

    @Override
    public long addCagnotte(CagnotteRequest cagnotteRequest) {
        log.info("CagnotteServiceImpl | addCagnotte is called");

        User user = userService.getMe();

        Cagnotte cagnotte = Cagnotte.builder()
                .label(cagnotteRequest.getLabel())
                .slug(Str.slug(cagnotteRequest.getLabel()))
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
                .url(cagnotteRequest.getUrl())
                .build();
        // S'il n'y a pas d'utilisateur avec cet email,
        // il ne fera donc rien
        if(user != null) cagnotte.setUser(user);

        cagnotte = cagnotteRepository.save(cagnotte);

        log.info("CagnotteServiceImpl | addCagnotte | Cagnotte Created | Id : " + cagnotte.getId());
        return cagnotte.getId();
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
        cagnotte.setDateCreation(cagnotteRequest.getDateCreation());
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


}
