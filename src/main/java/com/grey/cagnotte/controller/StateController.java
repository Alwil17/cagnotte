package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;
import com.grey.cagnotte.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
@Log4j2
public class StateController {
    private final String name= "StateController | ";
    private final StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> getStates(){
        log.info(name+"getStates is called");

        return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADD_STATE')")
    @PostMapping
    public ResponseEntity<Long> addState(@RequestBody StateRequest stateRequest){
        log.info(name+"addState is called");

        log.info(name+"addUser | stateRequest : " + stateRequest.toString());
        long stateId= stateService.addState(stateRequest);
        return new ResponseEntity<>(stateId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> getStateById(@PathVariable final long id){
        log.info(name+"getStateBy Id is callled");
        log.info(name+"getStateBy | Id: "+ id);

        return new ResponseEntity<>(stateService.getStateById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editState(@RequestBody StateRequest stateRequest,@PathVariable final long id){
        log.info(name+"editState Id is callled");
        log.info(name+"editState | Id: "+ id);

        stateService.editState(stateRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_STATE')")
    @DeleteMapping("/{id}")
    public void deleteState(@PathVariable final long id){
        stateService.deleteStateById(id);
    }
}
