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

    // Use Cagnotte instead of CagnotteResponse since we don't know which
    // entity call consume this service.
    public Cagnotte getCagnotteByUrl(String url, boolean isPublic, String accessToken) ;

    public CagnotteResponse editCagnotte(CagnotteRequest cagnotteRequest , String cagnotteUrl) ;

    public void deleteCagnotteByUrl(String cagnotteUrl) ;

    public void addSubstractParticipationAmount(Cagnotte cagnotte, double amount) ;

    CagnotteResponse publishCagnotte(String url);
}
