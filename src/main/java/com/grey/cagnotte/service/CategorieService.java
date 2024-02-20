package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Categorie;
import com.grey.cagnotte.payload.request.CategorieRequest;
import com.grey.cagnotte.payload.response.CategorieResponse;

import java.util.List;

public interface CategorieService {

     List<Categorie> getAllCategorie () ;

     public long addCategorie(CategorieRequest categorieRequest) ;

     public CategorieResponse getCategorie(long categorieId) ;

     public long editCategorie(CategorieRequest categorieRequest , long categorieId) ;

     public void deleteCategorie(long categorieId) ;

}
