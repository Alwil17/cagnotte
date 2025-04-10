package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;
import com.grey.cagnotte.repository.EtatRepository;
import com.grey.cagnotte.service.StateService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class StateServiceImpl implements StateService {
    private final String NOT_FOUND= "ETAT_NOT_FOUND";
    private final String name= "StateServiceImpl | ";
    private final EtatRepository etatRepository;

    @Override
    public List<State> getAllStates(){return etatRepository.findAll();}

    @Override
    public long addState(StateRequest stateRequest) {
        log.info(name+"addEtat is called");
        State state;
        if(!etatRepository.existByLabel(stateRequest.getLabel())) {
            state = State.builder()
                    .label(stateRequest.getLabel())
                    .slug(Str.slug(stateRequest.getLabel()))
                    .build();
            etatRepository.save(state);
        }else {
            state = etatRepository.findByLabel(stateRequest.getLabel()).orElseThrow();
            editState(stateRequest, state.getId());
        }
        log.info(name+"addEtat | Etat Created | Id : " + state.getId());

        return state.getId();
    }

    @Override
    public void addState(List<StateRequest> stateRequests) {
        log.info(name+"addEtats is called");
        stateRequests.forEach(stateRequest ->{
            State state;
            if(!etatRepository.existByLabel(stateRequest.getLabel())) {
                state = State.builder()
                        .label(stateRequest.getLabel())
                        .slug(Str.slug(stateRequest.getLabel()))
                        .build();
                etatRepository.save(state);
            }
        });
        log.info(name+"addEtats | Etats Created");
    }

    @Override
    public StateResponse getStateById(long stateId) {
        log.info(name+"getEtatById is called");

        State state
                = etatRepository.findById(stateId)
                .orElseThrow(
                        () -> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));

        StateResponse stateResponse = new StateResponse();

        copyProperties(state, stateResponse);

        log.info(name+"getEtatById | stateResponse :" + stateResponse.toString());

        return stateResponse;
    }

    @Override
    public void editState(StateRequest stateRequest, long stateId) {
        log.info(name+"editEtat is called");
        State state = etatRepository.findById(stateId)
                .orElseThrow(()-> new CagnotteCustomException("Etat with given id not found", NOT_FOUND));
        state.setSlug(Str.slug(stateRequest.getLabel()));
        state.setLabel(stateRequest.getLabel());
        etatRepository.save(state);
        log.info(name+"editEtat | Etat Updated | Etat Id: "+ state.getId());
    }

    @Override
    public void deleteStateById(long stateId) {
        log.info("Etat id: {}", stateId);
        if(!etatRepository.existsById(stateId)) {
            throw new CagnotteCustomException("Etat with given Id: " + stateId + " not found", NOT_FOUND);
        }
        log.info("Deleting Etat with id: {}", stateId);
        etatRepository.deleteById(stateId);
    }
}
