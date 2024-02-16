package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Etat;
import com.grey.cagnotte.payload.request.EtatRequest;
import com.grey.cagnotte.payload.response.EtatResponse;

import java.util.List;

public interface EtatService {
    List<Etat> getAllEtats();

    long addEtat(EtatRequest EtatRequest);

    void addEtats(List<EtatRequest> EtatRequests);

    EtatResponse getEtatBylibelle(String EtatEmail);

    void editEtat(EtatRequest EtatRequest, long EtatId);

    public void deleteEtatById(long EtatId);

}
