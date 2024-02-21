package com.grey.cagnotte.service;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;


import java.util.List;
import java.util.Optional;

public interface CagnotteService {

    List<Cagnotte> getAllCagnottes();

    public Optional<Cagnotte> getCagnotteById(Long id);

    void editCagnotte (CagnotteRequest cagnotteRequest, Long id);

    public Cagnotte saveCagnotte(Cagnotte cagnotte);

    public void deleteCagnotteById(Long id);

}
