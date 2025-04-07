package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.ParticipationRepository;
import com.grey.cagnotte.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ParticipationServiceImpl implements ParticipationService {
    private final String NOT_FOUND = "USER_NOT_FOUND";

    private final ParticipationRepository participationRepository;
    private final CagnotteRepository cagnotteRepository;


    @Override
    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    @Override
    public List<Participation> getParticipationsByCagnotteId(long cagnotteId) {
        return participationRepository.findAllByCagnotteId(cagnotteId);
    }

    @Override
    public long addParticipation(ParticipationRequest participationRequest) {
        log.info("ParticipationServiceImpl | addParticipation is called");


        Participation participation = Participation.builder()
                .montant(participationRequest.getAmount())
                .dateParticipation(participationRequest.getDateParticipation())
                .nomParticipant(participationRequest.getParticipantName())
                .messagePersonnalise(participationRequest.getCustomMessage())
                .isAnonyme(participationRequest.isAnonymous())
                .showMontant(participationRequest.isShowAmount())
                .cagnotte(cagnotteRepository.findById(participationRequest.getCagnotte_id()).get())
                .created_at(LocalDateTime.now())
                .build();

        participation = participationRepository.save(participation);

        log.info("ParticipationServiceImpl | addParticipation | Participation Created | Id : " + participation.getId());
        return participation.getId();
    }

    @Override
    public void addParticipation(List<ParticipationRequest> participationRequests) {
        log.info("ParticipationServiceImpl | addParticipation is called");

        for (ParticipationRequest participationRequest: participationRequests) {

            Participation participation = Participation.builder()
                    .montant(participationRequest.getAmount())
                    .dateParticipation(participationRequest.getDateParticipation())
                    .nomParticipant(participationRequest.getParticipantName())
                    .messagePersonnalise(participationRequest.getCustomMessage())
                    .isAnonyme(participationRequest.isAnonymous())
                    .showMontant(participationRequest.isShowAmount())
                    .cagnotte(cagnotteRepository.findById(participationRequest.getCagnotte_id()).get())
                    .created_at(LocalDateTime.now())
                    .build();

            participationRepository.save(participation);
        }

        log.info("ParticipationServiceImpl | addParticipation | Participations Created");
    }

    @Override
    public void editParticipation(ParticipationRequest participationRequest, long participationId) {
        log.info("ParticipationServiceImpl | editParticipation is called");

        Participation participation
                = participationRepository.findById(participationId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Participation with given Id not found",
                        NOT_FOUND
                ));
        participation.setMontant(participationRequest.getAmount());
        participation.setDateParticipation(participationRequest.getDateParticipation());
        participation.setNomParticipant(participationRequest.getParticipantName());
        participation.setMessagePersonnalise(participationRequest.getCustomMessage());
        participation.setAnonyme(participationRequest.isAnonymous());
        participation.setShowMontant(participationRequest.isShowAmount());
        participation.setCagnotte(cagnotteRepository.findById(participationRequest.getCagnotte_id()).get());
        participation.setUpdated_at(LocalDateTime.now());
        participationRepository.save(participation);

        log.info("ParticipationServiceImpl | editParticipation | Participation Updated | Participation Id :" + participation.getId());
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
}
