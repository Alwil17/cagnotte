package com.grey.cagnotte.controller;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.service.CagnotteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CagnotteController {

    @Autowired
    private CagnotteService cagnotteService;

    //save cagnotte
    @PostMapping("/cagnotte")
    public Cagnotte createEmployee(@RequestBody Cagnotte cagnotte) {
        return cagnotteService.saveCagnotte(cagnotte);
    }

    //get cagnotte by id
    @GetMapping("/cagnotte/{id}")
    public Cagnotte getCagnotte(@PathVariable("id") final Long id) {
        Optional<Cagnotte> cagnotte = cagnotteService.getCagnotteById(id);
        if(cagnotte.isPresent()) {
            return cagnotte.get();
        } else {
            return null;
        }
    }

    //get all cagnotte
    @GetMapping("/cagnottes")
    public Iterable<Cagnotte> getAllCagnottes() {
        return cagnotteService.getAllCagnottes();
    }


    //delete any cagnotte by id
    @DeleteMapping("/cagnotte/{id}")
    public void deleteCangotte(@PathVariable("id") final Long id) {
        cagnotteService.deleteCagnotteById(id);
    }

    //update function for cagnotte
    @PutMapping("/cagnotte/{id}")
    public ResponseEntity<Void> updateCagnotte(@RequestBody CagnotteRequest cagnotteRequest, @PathVariable("id") long id) {
       //Optional<Cagnotte> e = cagnotteService.getCagnotteById(id);
        log.info("CagnotteController | editCagnotte is called");

        log.info("UserController | editCagnotte | userId : " + id);

        cagnotteService.editCagnotte(cagnotteRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
