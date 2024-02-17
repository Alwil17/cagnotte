package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Etat;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.EtatRequest;
import com.grey.cagnotte.payload.response.EtatResponse;
import com.grey.cagnotte.repository.EtatRepository;
import com.grey.cagnotte.service.EtatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class EtatServiceImpl implements EtatService {
    private final String NOT_FOUND= "ETAT_NOT_FOUND";
    private final String name= "EtatServiceImpl | ";
    private EtatRepository etatRepository;

    @Override
    public List<Etat> getAllEtats(){return etatRepository.findAll();}

    @Override
    public long addEtat(EtatRequest etatRequest) {
        log.info(name+"addEtat is called");
        Etat etat;
        if(!etatRepository.existByLibelle(etatRequest.getLibelle())) {
            etat = Etat.builder()
                    .libelle(etatRequest.getLibelle())
                    .slug(etatRequest.getSlug())
                    .build();
            etatRepository.save(etat);
        }else {
            etat= etatRepository.findByLibelle(etatRequest.getLibelle()).orElseThrow();
            editEtat(etatRequest, etat.getId());
        }
        log.info(name+"addEtat | Etat Created | Id : " + etat.getId());

        return etat.getId();
    }

    @Override
    public void addEtats(List<EtatRequest> etatRequests) {
        log.info(name+"addUsers is called");
        etatRequests.forEach(etatRequest ->{
            Etat etat;
            if(!etatRepository.existByLibelle(etatRequest.getLibelle())) {
                etat = Etat.builder()
                        .libelle(etatRequest.getLibelle())
                        .slug(etatRequest.getSlug())
                        .build();
                etatRepository.save(etat);
            }
        });
        log.info(name+"addEtats | Etats Created");
    }

    @Override
    public EtatResponse getEtatById(long etatId) {
        log.info(name+"getUserById is called");

        Etat etat
                = etatRepository.findById(etatId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));

        EtatResponse etatResponse = new EtatResponse();

        copyProperties(etat, etatResponse);

        log.info(name+"getEtatById | etatResponse :" + etatResponse.toString());

        return etatResponse;
    }

    @Override
    public void editEtat(EtatRequest etatRequest, long etatId) {
        log.info(name+"editEtat is called");
        Etat etat= etatRepository.findById(etatId)
                .orElseThrow(()-> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));
        etat.setSlug(etatRequest.getSlug());
        etat.setLibelle(etatRequest.getLibelle());
        etatRepository.save(etat);
        log.info(name+"editEtat | Etat Updated | Etat Id: "+etat.getId());
    }

    @Override
    public void deleteEtatById(long etatId) {
        log.info("Etat id: {}", etatId);
        if(!etatRepository.existsById(etatId)) {
            throw new CagnotteCustomException("Etat with given Id: " + etatId + " not found", NOT_FOUND);
        }
        log.info("Deleting Etat with id: {}", etatId);
        etatRepository.deleteById(etatId);
    }
}
