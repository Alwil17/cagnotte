package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.service.CagnotteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cagnottes")
@RequiredArgsConstructor
@Log4j2
public class CagnotteController {

    private final CagnotteService cagnotteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<CagnotteResponse>> getAllCagnottes() {
        log.info("CagnotteController | getAllCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getAllCagnottes(), HttpStatus.OK);
    }

    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CagnotteResponse>> getAllMyCagnottes() {
        log.info("CagnotteController | getAllCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getAllMyCagnottes(), HttpStatus.OK);
    }

    /**
     * List of all public cagnottes.
     */
    @GetMapping("/public")
    public ResponseEntity<List<CagnotteResponse>> getPublicCagnottes() {
        log.info("CagnotteController | getAllCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getPublicCagnottes(), HttpStatus.OK);
    }

    /**
     * Retrieves a cagnotte by its slug.
     * Access depends on its visibility:
     * - If the cagnotte is public, access is free.
     */
    @GetMapping("/public/{slug}")
    public ResponseEntity<CagnotteResponse> getCagnotteBySlug(@PathVariable("slug") String slug) {
        log.info("CagnotteController | getCagnotteBySlug is called");
        CagnotteResponse cagnotte = cagnotteService.getCagnotteBySlug(slug);

        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CagnotteResponse> addCagnotte(@RequestBody CagnotteRequest cagnotteRequest) {

        log.info("CagnotteController | addCagnotte is called");

        log.info("CagnotteController | addCagnotte | cagnotteRequest : " + cagnotteRequest.toString());

        CagnotteResponse cagnotte = cagnotteService.addCagnotte(cagnotteRequest);
        return new ResponseEntity<>(cagnotte, HttpStatus.CREATED);
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

    @GetMapping("/private/{slug}")
    public ResponseEntity<CagnotteResponse> getPrivateCagnotte(
            @PathVariable String slug,
            @RequestParam(name = "access_token", required = false) String accessToken) {

        CagnotteResponse cagnotte = cagnotteService.getPrivateCagnotte(slug, accessToken);

        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteCagnotteById(@PathVariable("id") long cagnotteId) {
        cagnotteService.deleteCagnotteById(cagnotteId);
    }
}
