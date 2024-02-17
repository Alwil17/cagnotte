package com.grey.cagnotte.controller;

import com.grey.cagnotte.payload.request.EtatRequest;
import com.grey.cagnotte.payload.response.EtatResponse;
import com.grey.cagnotte.service.EtatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class EtatController {
    private final String name= "EtatController | ";
    public EtatService etatService;

    @PostMapping
    public ResponseEntity<Long> addEtat(@RequestBody EtatRequest etatRequest){
        log.info(name+"addEtat is called");

        log.info(name+"addUser | etatRequest : " + etatRequest.toString());
        long etatId= etatService.addEtat(etatRequest);
        return new ResponseEntity<>(etatId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtatResponse> getEtatById(@PathVariable final long id){
        log.info(name+"getEtatBy Id is callled");
        log.info(name+"getEtatBy | Id: "+ id);

        return new ResponseEntity<>(etatService.getEtatById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> editEtat(@RequestBody EtatRequest etatRequest,@PathVariable final long id){
        log.info(name+"editEtat Id is callled");
        log.info(name+"editEtat | Id: "+ id);

        etatService.editEtat(etatRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final long id){
        etatService.deleteEtatById(id);
    }
}
