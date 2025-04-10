package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;

import java.util.List;

public interface StateService {
    List<State> getAllStates();

    long addState(StateRequest StateRequest);

    void addState(List<StateRequest> stateRequests);

    StateResponse getStateById(long stateId);

    void editState(StateRequest stateRequest, long stateId);

    public void deleteStateById(long stateId);

}
