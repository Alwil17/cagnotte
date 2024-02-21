package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participations")
@RequiredArgsConstructor
@Log4j2
public class ParticipationController {

    private final ParticipationService participationService;

    @GetMapping
    public ResponseEntity<List<Participation>> getAllParticipations() {

        log.info("ParticipationController | getAllParticipations is called");
        return new ResponseEntity<>(participationService.getAllParticipations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addParticipation(@RequestBody ParticipationRequest participationRequest) {

        log.info("ParticipationController | addParticipation is called");

        log.info("ParticipationController | addParticipation | participationRequest : " + participationRequest.toString());

        long participationId = participationService.addParticipation(participationRequest);
        return new ResponseEntity<>(participationId, HttpStatus.CREATED);
    }

    @GetMapping("/cagnotte/{id}")
    public ResponseEntity<List<Participation>> getParticipationByCagnotteId(@PathVariable("id") long cagnotteId) {
        log.info("ParticipationController | getParticipationByCagnotteId is called");

        List<Participation> participations
                = participationService.getParticipationsByCagnotteId(cagnotteId);
        return new ResponseEntity<>(participations, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editParticipation(@RequestBody ParticipationRequest participationRequest,
            @PathVariable("id") long participationId
    ) {

        log.info("ParticipationController | editParticipation is called");

        log.info("ParticipationController | editParticipation | participationId : " + participationId);

        participationService.editParticipation(participationRequest, participationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipationById(@PathVariable("id") long participationId) {
        participationService.deleteParticipationById(participationId);
    }
}