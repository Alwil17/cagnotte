package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.service.CagnotteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cagnottes")
@RequiredArgsConstructor
@Log4j2
public class CagnotteController {

    private final CagnotteService cagnotteService;

    @GetMapping
    public ResponseEntity<List<Cagnotte>> getAllCagnottes() {

        log.info("CagnotteController | getAllCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getAllCagnottes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addCagnotte(@RequestBody CagnotteRequest cagnotteRequest) {

        log.info("CagnotteController | addCagnotte is called");

        log.info("CagnotteController | addCagnotte | cagnotteRequest : " + cagnotteRequest.toString());

        long cagnotteId = cagnotteService.addCagnotte(cagnotteRequest);
        return new ResponseEntity<>(cagnotteId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CagnotteResponse> getCagnotteById(@PathVariable("id") long cagnotteId) {
        log.info("CagnotteController | getCagnotteByCagnotteId is called");

        CagnotteResponse cagnotte
                = cagnotteService.getCagnotteById(cagnotteId);
        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editCagnotte(@RequestBody CagnotteRequest cagnotteRequest,
                                                  @PathVariable("id") long cagnotteId
    ) {

        log.info("CagnotteController | editCagnotte is called");

        log.info("CagnotteController | editCagnotte | cagnotteId : " + cagnotteId);

        cagnotteService.editCagnotte(cagnotteRequest, cagnotteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCagnotteById(@PathVariable("id") long cagnotteId) {
        cagnotteService.deleteCagnotteById(cagnotteId);
    }
}
