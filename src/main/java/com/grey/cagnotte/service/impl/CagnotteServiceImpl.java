package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.service.CagnotteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CagnotteServiceImpl implements CagnotteService {

    @Autowired
    public CagnotteRepository cagnotteRepository;

    @Override
    public List<Cagnotte> getAllCagnottes() {
        return cagnotteRepository.findAll();
    }

    @Override
    public Optional<Cagnotte> getCagnotteById(Long id) {
        return cagnotteRepository.findById(id);
    }

    @Override
    public Cagnotte saveCagnotte(Cagnotte cagnotte) {
        Cagnotte savedCagnotte = cagnotteRepository.save(cagnotte);
        return savedCagnotte;
    }

    @Override
    public void deleteCagnotteById(Long id) {
        cagnotteRepository.deleteById(id);
    }

    @Override
    public void editCagnotte(CagnotteRequest cagnotteRequest, Long id) {
        log.info("CagnotteServiceImpl | editCagnotte is called");

        Cagnotte cagnotte
                = cagnotteRepository.findById(id)
                .orElseThrow(() -> new CagnotteCustomException(
                        "Cagnotte with given Id not found",
                        NOT_FOUND
                ));

        cagnotte.setLibelle(cagnotteRequest.getLibelle());
        cagnotte.setSlug(cagnotteRequest.getSlug());
        cagnotte.setReference(cagnotteRequest.getReference());
        cagnotte.setOrganisateur(cagnotteRequest.getOrganisateur());
        cagnotte.setConcerne(cagnotteRequest.getConcerne());
        cagnotte.setDate_creation(cagnotteRequest.getDate_creation());
        cagnotte.setDate_echeance(cagnotteRequest.getDate_echeance());
        cagnotte.setMontant_objectif(cagnotteRequest.getMontant_objectif());
        cagnotte.setMontant_collecte(cagnotteRequest.getMontant_collecte());
        cagnotte.setType_participation(cagnotteRequest.getType_participation());
        cagnotte.setMontant_participation(cagnotteRequest.getMontant_participation());
        cagnotte.setMessage_personnalise(cagnotteRequest.getMessage_personnalise());
        cagnotte.setImage(cagnotteRequest.getImage());
        cagnotte.setLieu_evenement(cagnotteRequest.getLieu_evenement());
        cagnotte.setUrl(cagnotteRequest.getUrl());



        cagnotteRepository.save(cagnotte);
        log.info("CagnotteServiceImpl | editCagnotte | Cagnotte Updated | Cagnotte Id :" + cagnotte.getId());
    }
}
