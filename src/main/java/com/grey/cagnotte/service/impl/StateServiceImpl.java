package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;
import com.grey.cagnotte.repository.StateRepository;
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
    private final String NOT_FOUND= "STATE_NOT_FOUND";
    private final String name= "StateServiceImpl | ";
    private final StateRepository stateRepository;

    @Override
    public List<State> getAllStates(){return stateRepository.findAll();}

    @Override
    public State addState(StateRequest stateRequest) {
        log.info(name+"addState is called");
        State state;
        if(!stateRepository.existByLabel(stateRequest.getLabel())) {
            state = State.builder()
                    .label(stateRequest.getLabel())
                    .slug(Str.slug(stateRequest.getLabel()))
                    .description(stateRequest.getDescription())
                    .color(stateRequest.getColor())
                    .build();
            stateRepository.save(state);
        }else {
            state = stateRepository.findByLabel(stateRequest.getLabel()).orElseThrow();
            editState(stateRequest, state.getId());
        }
        log.info(name+"addState | State Created | Id : " + state.getId());
        return state;
    }

    @Override
    public void addState(List<StateRequest> stateRequests) {
        log.info(name+"addStates is called");
        stateRequests.forEach(this::addState);
        log.info(name+"addStates | States Created");
    }

    @Override
    public StateResponse getStateById(long stateId) {
        log.info(name+"getStateById is called");

        State state
                = stateRepository.findById(stateId)
                .orElseThrow(
                        () -> new CagnotteCustomException("State with given id not found", NOT_FOUND));

        StateResponse stateResponse = new StateResponse();

        copyProperties(state, stateResponse);

        log.info(name+"getStateById | stateResponse :" + stateResponse.toString());

        return stateResponse;
    }

    @Override
    public State getStateBySlug(String slug) {
        return stateRepository.findByLabel(slug)
                .orElseThrow(
                        () -> new CagnotteCustomException("State with given id not found", NOT_FOUND));
    }

    @Override
    public void editState(StateRequest stateRequest, long stateId) {
        log.info(name+"editState is called");
        State state = stateRepository.findById(stateId)
                .orElseThrow(()-> new CagnotteCustomException("State with given id not found", NOT_FOUND));
        state.setLabel(stateRequest.getLabel());
        state.setSlug(Str.slug(stateRequest.getLabel()));
        state.setDescription(stateRequest.getDescription());
        state.setColor(stateRequest.getColor());
        stateRepository.save(state);
        log.info(name+"editState | State Updated | State Id: "+ state.getId());
    }

    @Override
    public void deleteStateById(long stateId) {
        log.info("State id: {}", stateId);
        if(!stateRepository.existsById(stateId)) {
            throw new CagnotteCustomException("State with given Id: " + stateId + " not found", NOT_FOUND);
        }
        log.info("Deleting State with id: {}", stateId);
        stateRepository.deleteById(stateId);
    }
}
