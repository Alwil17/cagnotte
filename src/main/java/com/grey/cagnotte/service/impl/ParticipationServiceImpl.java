package com.grey.cagnotte.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.enums.StateEnum;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.payload.response.ParticipationResponse;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.ParticipationRepository;
import com.grey.cagnotte.service.CagnotteService;
import com.grey.cagnotte.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ParticipationServiceImpl implements ParticipationService {
    private final String NOT_FOUND = "USER_NOT_FOUND";

    private final ParticipationRepository participationRepository;
    private final CagnotteService cagnotteService;

    @Override
    public List<ParticipationResponse> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<Participation> getParticipationByCagnotteUrl(String cagnotteUrl, String accessToken) {
        log.info("Fetching participations for cagnotte URL: {}", cagnotteUrl);
        // Since getCagnotteByURL is secure, we just call it here so security is guaranted.
        Cagnotte cagnotte;
        if(accessToken != null && !accessToken.isBlank()){
            cagnotte = cagnotteService.getCagnotteByUrl(cagnotteUrl, false, accessToken);
        }else {
            cagnotte = cagnotteService.getCagnotteByUrl(cagnotteUrl, true, null);
        }

        List<Participation> participations = participationRepository.findAllByCagnotteUrl(cagnotteUrl);
        log.info("Found {} participations for cagnotte URL: {}", participations.size(), cagnotteUrl);
        return participations;
    }

    @Override
    public ParticipationResponse addParticipation(ParticipationRequest participationRequest) {
        log.info("ParticipationServiceImpl | addParticipation is called");

        if(participationRequest.getAmount() <= 0) {
            throw new CagnotteCustomException("Your contribution is too low for this cagnotte.", "CONTRIBUTION_NOT_POSSIBLE");
        }

        Cagnotte cagnotte;
        if(participationRequest.getCagnotte_access_token() != null && !participationRequest.getCagnotte_access_token().isBlank()){
            cagnotte = cagnotteService.getCagnotteByUrl(participationRequest.getCagnotte_url(), false, participationRequest.getCagnotte_access_token());
        }else {
            cagnotte = cagnotteService.getCagnotteByUrl(participationRequest.getCagnotte_url(), true, null);
        }

        if(!cagnotte.getState().getLabel().equals(StateEnum.ACTIVE.name())){
            throw new CagnotteCustomException("This cagnotte is not (yet) open for contributions.", "CONTRIBUTION_NOT_POSSIBLE");
        }

        if(cagnotte.getParticipationAmount() != 0) {
            if (participationRequest.getAmount() != cagnotte.getParticipationAmount()){
                throw new CagnotteCustomException("The amount of your participation must be equal to cagnotte fixed amount.", "CONTRIBUTION_NOT_POSSIBLE");
            }
        }

        Participation participation = Participation.builder()
                .amount(participationRequest.getAmount())
                .dateParticipation(LocalDateTime.now())
                .participantName(participationRequest.getParticipantName())
                .customMessage(participationRequest.getCustomMessage())
                .isAnonymous(participationRequest.isAnonymous())
                .showAmount(participationRequest.isShowAmount())
                .cagnotte(cagnotte)
                .build();

        participation = participationRepository.save(participation);

        cagnotte.setCollectedAmount(cagnotte.getCollectedAmount() + participation.getAmount());
        cagnotteService.addParticipationAmount(cagnotte, participation.getAmount());

        log.info("ParticipationServiceImpl | addParticipation | Participation Created | Id : " + participation.getId());
        return mapToResponse(participation);
    }


    @Override
    public ParticipationResponse editParticipation(ParticipationRequest participationRequest, long participationId) {
        log.info("ParticipationServiceImpl | editParticipation is called");

        Participation participation
                = participationRepository.findById(participationId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Participation with given Id not found",
                        NOT_FOUND
                ));
        participation.setAmount(participationRequest.getAmount());
        participation.setDateParticipation(participationRequest.getDateParticipation());
        participation.setParticipantName(participationRequest.getParticipantName());
        participation.setCustomMessage(participationRequest.getCustomMessage());
        participation.setAnonymous(participationRequest.isAnonymous());
        participation.setShowAmount(participationRequest.isShowAmount());
        participationRepository.save(participation);

        log.info("ParticipationServiceImpl | editParticipation | Participation Updated | Participation Id :" + participation.getId());
        return mapToResponse(participation);
    }

    @Override
    public void deleteParticipationById(long participationId) {
        log.info("Participation id: {}", participationId);

        if (!participationRepository.existsById(participationId)) {
            log.info("Im in this loop {}", !participationRepository.existsById(participationId));
            throw new CagnotteCustomException(
                    "Participation with given with Id: " + participationId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Participation with id: {}", participationId);
        participationRepository.deleteById(participationId);
    }

    public ParticipationResponse mapToResponse(Participation participation){
        ParticipationResponse participationResponse = new ParticipationResponse();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        participationResponse = mapper.convertValue(participation, ParticipationResponse.class);

        if(participation.isAnonymous()) participationResponse.setParticipantName("Anonymous");
        if(!participation.isShowAmount()) participationResponse.setAmount(0);

        return participationResponse;
    }
}
