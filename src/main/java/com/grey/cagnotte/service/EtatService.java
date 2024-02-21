package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Etat;
import com.grey.cagnotte.payload.request.EtatRequest;
import com.grey.cagnotte.payload.response.EtatResponse;

import java.util.List;

public interface EtatService {
    List<Etat> getAllEtats();

    long addEtat(EtatRequest EtatRequest);

    void addEtat(List<EtatRequest> etatRequests);

    EtatResponse getEtatById(long etatId);

    void editEtat(EtatRequest etatRequest, long etatId);

    public void deleteEtatById(long etatId);

}
