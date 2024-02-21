package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.*;
import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.service.CagnotteService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class CagnotteServiceImpl implements CagnotteService {
    private final String NOT_FOUND= "CAGNOTTE_NOT_FOUND";

    private final CagnotteRepository cagnotteRepository;


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

        copyProperties(cagnotte, cagnotteResponse);

        log.info("CagnotteServiceImpl |  getCagnotteById | cagnotteResponse :" + cagnotteResponse.toString());
        return cagnotteResponse;
    }

    @Override
    public long addCagnotte(CagnotteRequest cagnotteRequest) {
        log.info("CagnotteServiceImpl | addCagnotte is called");

        Cagnotte cagnotte = Cagnotte.builder()
            .libelle(cagnotteRequest.getLibelle())
            .slug(Str.slug(cagnotteRequest.getLibelle()))
            .reference(cagnotteRequest.getReference())
            .organisateur(cagnotteRequest.getOrganisateur())
            .concerne(cagnotteRequest.getConcerne())
            .date_creation(cagnotteRequest.getDate_creation())
            .date_echeance(cagnotteRequest.getDate_echeance())
            .montant_objectif(cagnotteRequest.getMontant_objectif())
            .montant_collecte(cagnotteRequest.getMontant_collecte())
            .type_participation(cagnotteRequest.getType_participation())
            .message_personnalise(cagnotteRequest.getMessage_personnalise())
            .image(cagnotteRequest.getImage())
            .lieu_evenement(cagnotteRequest.getLieu_evenement())
            .url(cagnotteRequest.getUrl())
                .build();

        cagnotte = cagnotteRepository.save(cagnotte);

        log.info("CagnotteServiceImpl | addCagnotte | Cagnotte Created | Id : " + cagnotte.getId());
        return cagnotte.getId();
    }

    @Override
    public void editCagnotte(CagnotteRequest cagnotteRequest, long cagnotteId) {
        log.info("CagnotteServiceImpl | editCagnotte is called");

        Cagnotte cagnotte
                = cagnotteRepository.findById(cagnotteId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Cagnotte with given Id not found",
                        NOT_FOUND
                ));
        cagnotte.setLibelle(cagnotteRequest.getLibelle());
        cagnotte.setSlug(Str.slug(cagnotteRequest.getLibelle()));
        cagnotte.setReference(cagnotteRequest.getReference());
        cagnotte.setOrganisateur(cagnotteRequest.getOrganisateur());
        cagnotte.setConcerne(cagnotteRequest.getConcerne());
        cagnotte.setDate_creation(cagnotteRequest.getDate_creation());
        cagnotte.setDate_echeance(cagnotteRequest.getDate_echeance());
        cagnotte.setMontant_objectif(cagnotteRequest.getMontant_objectif());
        cagnotte.setMontant_collecte(cagnotteRequest.getMontant_collecte());
        cagnotte.setType_participation(cagnotteRequest.getType_participation());
        cagnotte.setMessage_personnalise(cagnotteRequest.getMessage_personnalise());
        cagnotte.setImage(cagnotteRequest.getImage());
        cagnotte.setLieu_evenement(cagnotteRequest.getLieu_evenement());
        cagnotte.setUrl(cagnotteRequest.getUrl());
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
