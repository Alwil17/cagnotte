package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.EtatRequest;
import com.grey.cagnotte.payload.response.EtatResponse;
import com.grey.cagnotte.repository.EtatRepository;
import com.grey.cagnotte.service.EtatService;
import com.grey.cagnotte.utils.Str;
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
    private final EtatRepository etatRepository;

    @Override
    public List<State> getAllEtats(){return etatRepository.findAll();}

    @Override
    public long addEtat(EtatRequest etatRequest) {
        log.info(name+"addEtat is called");
        State state;
        if(!etatRepository.existByLibelle(etatRequest.getLibelle())) {
            state = State.builder()
                    .libelle(etatRequest.getLibelle())
                    .slug(Str.slug(etatRequest.getLibelle()))
                    .build();
            etatRepository.save(state);
        }else {
            state = etatRepository.findByLibelle(etatRequest.getLibelle()).orElseThrow();
            editEtat(etatRequest, state.getId());
        }
        log.info(name+"addEtat | Etat Created | Id : " + state.getId());

        return state.getId();
    }

    @Override
    public void addEtat(List<EtatRequest> etatRequests) {
        log.info(name+"addEtats is called");
        etatRequests.forEach(etatRequest ->{
            State state;
            if(!etatRepository.existByLibelle(etatRequest.getLibelle())) {
                state = State.builder()
                        .libelle(etatRequest.getLibelle())
                        .slug(Str.slug(etatRequest.getLibelle()))
                        .build();
                etatRepository.save(state);
            }
        });
        log.info(name+"addEtats | Etats Created");
    }

    @Override
    public EtatResponse getEtatById(long etatId) {
        log.info(name+"getEtatById is called");

        State state
                = etatRepository.findById(etatId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));

        EtatResponse etatResponse = new EtatResponse();

        copyProperties(state, etatResponse);

        log.info(name+"getEtatById | etatResponse :" + etatResponse.toString());

        return etatResponse;
    }

    @Override
    public void editEtat(EtatRequest etatRequest, long etatId) {
        log.info(name+"editEtat is called");
        State state = etatRepository.findById(etatId)
                .orElseThrow(()-> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));
        state.setSlug(Str.slug(etatRequest.getLibelle()));
        state.setLibelle(etatRequest.getLibelle());
        etatRepository.save(state);
        log.info(name+"editEtat | Etat Updated | Etat Id: "+ state.getId());
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
