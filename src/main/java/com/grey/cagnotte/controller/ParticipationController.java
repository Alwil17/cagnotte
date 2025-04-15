package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.payload.response.ParticipationResponse;
import com.grey.cagnotte.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
@RequiredArgsConstructor
@Log4j2
public class ParticipationController {

    private final ParticipationService participationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ParticipationResponse>> getAllParticipations() {
        log.info("ParticipationController | getAllParticipations is called");
        return new ResponseEntity<>(participationService.getAllParticipations(), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ParticipationResponse> addParticipation(@RequestBody ParticipationRequest participationRequest) {
        log.info("ParticipationController | addParticipation is called");
        log.info("ParticipationController | addParticipation | participationRequest : " + participationRequest.toString());

        ParticipationResponse participation = participationService.addParticipation(participationRequest);
        return new ResponseEntity<>(participation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> editParticipation(@RequestBody ParticipationRequest participationRequest,
            @PathVariable("id") long participationId
    ) {

        log.info("ParticipationController | editParticipation is called");

        log.info("ParticipationController | editParticipation | participationId : " + participationId);

        participationService.editParticipation(participationRequest, participationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public void deleteParticipationById(@PathVariable("id") long participationId) {
        participationService.deleteParticipationById(participationId);
    }
}