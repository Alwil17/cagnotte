package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Categorie;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.CategorieRequest;
import com.grey.cagnotte.payload.response.CategorieResponse;
import com.grey.cagnotte.repository.CategorieRepository;
import com.grey.cagnotte.service.CategorieService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategorieServiceImpl  implements CategorieService {

    private final CategorieRepository categorieRepository ;

    @Override
    public List<Categorie> getAllCategorie() {
        return categorieRepository.findAll() ;
    }

    @Override
    public long addCategorie(CategorieRequest categorieRequest) {
        Categorie categorie ;
        if(categorieRepository.existsByLibelleEquals(categorieRequest.getLibelle())){
            categorie = categorieRepository.findByLibelle(categorieRequest.getLibelle()).orElseThrow();
            editCategorie(categorieRequest, categorie.getId());
        }
        else {
            categorie = Categorie.builder()
                    .libelle(categorieRequest.getLibelle())
                    .slug(Str.slug(categorieRequest.getLibelle()))
                    .icone(categorieRequest.getIcone())
                    .allow_concerne(categorieRequest.isAllow_concerne())
                    .allow_message(categorieRequest.isAllow_message())
                    .allow_image(categorieRequest.isAllow_image())
                    .allow_lieu(categorieRequest.isAllow_lieu())
                    .allow_url(categorieRequest.isAllow_url())
                    .build();
            categorie =  categorieRepository.save(categorie);
        }


        return categorie.getId();
    }

    @Override
    public CategorieResponse getCategorie(long categorieId) {

        Categorie categorie;

        categorie = categorieRepository.findById(categorieId).orElseThrow(
                () -> new CagnotteCustomException("Categorie not found","404")
        );

        CategorieResponse categorieResponse = new CategorieResponse();

        copyProperties(categorie,categorieResponse);
        return categorieResponse;
    }

    @Override
    public long editCategorie(CategorieRequest categorieRequest, long categorieId) {

        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(
                ()-> new CagnotteCustomException("Categorie with given Id not found","404")
        );
        categorie.setLibelle(categorieRequest.getLibelle());
        categorie.setSlug(Str.slug(categorieRequest.getLibelle()));
        categorie.setIcone(categorieRequest.getIcone());
        categorie.setAllow_concerne(categorieRequest.isAllow_concerne());
        categorie.setAllow_message(categorieRequest.isAllow_message());
        categorie.setAllow_image(categorieRequest.isAllow_image());
        categorie.setAllow_lieu(categorie.isAllow_lieu());
        categorie.setAllow_url(categorieRequest.isAllow_url());

        categorie = categorieRepository.save(categorie);
        return categorie.getId();
    }

    @Override
    public void deleteCategorie(long categorieId) {

       if(!categorieRepository.existsById(categorieId)){
           throw new CagnotteCustomException("Categorie with given ID Not Found","404");
       }
       categorieRepository.deleteById(categorieId);
    }
}
