package com.grey.cagnotte.service;


import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.payload.request.CagnotteRequest;
import com.grey.cagnotte.payload.response.CagnotteResponse;

import java.util.List;

public interface CagnotteService {

    List<CagnotteResponse> getAllCagnottes();

    List<CagnotteResponse> getPublicCagnottes() ;

    List<CagnotteResponse> getAllMyCagnottes();

    public CagnotteResponse addCagnotte(CagnotteRequest cagnotteRequest) ;

    public CagnotteResponse getCagnotteById(long cagnotteId) ;

    public CagnotteResponse getCagnotteBySlug(String cagnotteSlug) ;

    public CagnotteResponse getPrivateCagnotte(String slug, String accessToken);

    public CagnotteResponse editCagnotte(CagnotteRequest cagnotteRequest , long cagnotteId) ;

    public void deleteCagnotteById(long cagnotteId) ;

}
