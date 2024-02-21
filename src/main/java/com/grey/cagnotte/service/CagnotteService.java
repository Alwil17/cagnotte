package com.grey.cagnotte.service;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;

import java.util.List;

public interface CagnotteService {

    List<Cagnotte> getAllCagnottes () ;

    public long addCagnotte(CagnotteRequest cagnotteRequest) ;

    public CagnotteResponse getCagnotteById(long cagnotteId) ;

    public void editCagnotte(CagnotteRequest cagnotteRequest , long cagnotteId) ;

    public void deleteCagnotteById(long cagnotteId) ;

}
