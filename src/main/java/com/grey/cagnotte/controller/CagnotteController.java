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
    @GetMapping("/public/{url}")
    public ResponseEntity<CagnotteResponse> getCagnotteByUrl(@PathVariable("url") String url) {
        log.info("CagnotteController | getCagnotteBySlug is called");
        CagnotteResponse cagnotte = cagnotteService.getCagnotteByUrl(url, true);

        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }

    /**
     * Promote A DRAFT state cagnotte to ACTIVE.
     */
    @GetMapping("/{url}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<CagnotteResponse> publishCagnotte(@PathVariable("url") String url) {
        log.info("CagnotteController | getCagnotteBySlug is called");
        CagnotteResponse cagnotte = cagnotteService.publishCagnotte(url);

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

    @PutMapping("/{url}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> editCagnotte(@RequestBody CagnotteRequest cagnotteRequest,
                                                  @PathVariable("url") String cagnotteUrl
    ) {
        log.info("CagnotteController | editCagnotte is called");
        log.info("CagnotteController | editCagnotte | cagnotteId : " + cagnotteUrl);

        cagnotteService.editCagnotte(cagnotteRequest, cagnotteUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/private/{url}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CagnotteResponse> getPrivateCagnotte(
            @PathVariable String url,
            @RequestParam("access_token") String accessToken) {

        CagnotteResponse cagnotte = cagnotteService.getPrivateCagnotte(url, accessToken);

        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }


    @DeleteMapping("/{url}")
    @PreAuthorize("isAuthenticated()")
    public void deleteCagnotteById(@PathVariable("url") String cagnotteUrl) {
        cagnotteService.deleteCagnotteByUrl(cagnotteUrl);
    }
}
