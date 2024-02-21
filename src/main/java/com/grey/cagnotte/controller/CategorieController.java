package com.grey.cagnotte.controller;

import com.grey.cagnotte.entity.Categorie;
import com.grey.cagnotte.payload.request.CategorieRequest;
import com.grey.cagnotte.payload.response.CategorieResponse;
import com.grey.cagnotte.service.CategorieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategorieController {

    private CategorieService categorieService;

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllcategorie (){

        return new ResponseEntity<>(categorieService.getAllCategorie(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Long> addCategorie(@RequestBody CategorieRequest categorieRequest){

        long categorieId = categorieService.addCategorie(categorieRequest);
        return new ResponseEntity<>(categorieId,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<CategorieResponse> getCategorie (@PathVariable("id") long id){
        CategorieResponse categorieResponse = categorieService.getCategorie(id);
        return  new ResponseEntity<>(categorieResponse,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Long> editCategorie (@RequestBody CategorieRequest categorieRequest, @PathVariable("id") long id){
        long categorieId = categorieService.editCategorie(categorieRequest,id);
        return new ResponseEntity<>(categorieId,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCategorie (@PathVariable("id") long id ){
        categorieService.deleteCategorie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
