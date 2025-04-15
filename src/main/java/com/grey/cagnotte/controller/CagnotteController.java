package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.payload.response.ParticipationResponse;
import com.grey.cagnotte.service.CagnotteService;
import com.grey.cagnotte.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cagnottes")
@RequiredArgsConstructor
@Log4j2
public class CagnotteController {

    private final CagnotteService cagnotteService;
    private final ParticipationService participationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<CagnotteResponse>> getAllCagnottes() {
        log.info("CagnotteController | getAllCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getAllCagnottes(), HttpStatus.OK);
    }

    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CagnotteResponse>> getAllMyCagnottes() {
        log.info("CagnotteController | getAllMyCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getAllMyCagnottes(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CagnotteResponse> addCagnotte(@RequestBody CagnotteRequest cagnotteRequest) {
        log.info("CagnotteController | addCagnotte is called");
        log.info("CagnotteController | addCagnotte | cagnotteRequest : " + cagnotteRequest.toString());

        CagnotteResponse cagnotte = cagnotteService.addCagnotte(cagnotteRequest);
        return new ResponseEntity<>(cagnotte, HttpStatus.CREATED);
    }

    /**
     * Endpoint for a user to contribute (i.e., participate) to a specific cagnotte.
     * URL example: POST /cagnottes/123/participations
     */
    @PostMapping("/{url}/participate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ParticipationResponse> participateInCagnotte(
            @PathVariable("url") String cagnotteUrl,
            @RequestBody ParticipationRequest participationRequest,
            @RequestParam(value = "access_token", required = false) String accessToken) {

        log.info("CagnotteParticipationController | participateInCagnotte called for cagnotteUrl: {}", cagnotteUrl);
        participationRequest.setCagnotte_url(cagnotteUrl);
        if(accessToken != null && !accessToken.isBlank()) {
            participationRequest.setCagnotte_access_token(accessToken);
        }
        ParticipationResponse participationResponse = participationService.addParticipation(participationRequest);
        return new ResponseEntity<>(participationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{url}/participations")
    public ResponseEntity<List<Participation>> getParticipationByCagnotteUrl(@PathVariable("url") String cagnotteUrl,
                                                                             @RequestParam(value = "access_token", required = false) String accessToken) {
        log.info("ParticipationController | getParticipationByCagnotteUrl is called");
        // Assuming that the front will check for access_token if cagnotte is private
        List<Participation> participations
                = participationService.getParticipationByCagnotteUrl(cagnotteUrl, accessToken);
        return new ResponseEntity<>(participations, HttpStatus.OK);
    }


    /**
     * List of all public cagnottes.
     */
    @GetMapping("/public")
    public ResponseEntity<List<CagnotteResponse>> getPublicCagnottes() {
        log.info("CagnotteController | getPublicCagnottes is called");
        return new ResponseEntity<>(cagnotteService.getPublicCagnottes(), HttpStatus.OK);
    }

    /**
     * Retrieves a cagnotte by its url.
     * Access depends on its visibility:
     * - If the cagnotte is public, access is free.
     * - If the cagnotte is private, access_token is needed.
     */
    @GetMapping("/{url}")
    public ResponseEntity<Cagnotte> getCagnotteByUrl(@PathVariable("url") String url,
                                                             @RequestParam(value = "access_token", required = false) String accessToken) {
        log.info("CagnotteController | getCagnotteBySlug is called");
        Cagnotte cagnotte;
        if(accessToken != null && !accessToken.isBlank()){
            cagnotte = cagnotteService.getCagnotteByUrl(url, false, accessToken);
        }else {
            cagnotte = cagnotteService.getCagnotteByUrl(url, true, null);
        }
        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
    }

    /**
     * Promote A DRAFT state cagnotte to ACTIVE.
     */
    @PatchMapping("/{url}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CagnotteResponse> publishCagnotte(@PathVariable("url") String url) {
        log.info("CagnotteController | publishCagnotte is called");
        CagnotteResponse cagnotte = cagnotteService.publishCagnotte(url);

        return new ResponseEntity<>(cagnotte, HttpStatus.OK);
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

    @DeleteMapping("/{url}")
    @PreAuthorize("isAuthenticated()")
    public void deleteCagnotteById(@PathVariable("url") String cagnotteUrl) {
        cagnotteService.deleteCagnotteByUrl(cagnotteUrl);
    }
}
