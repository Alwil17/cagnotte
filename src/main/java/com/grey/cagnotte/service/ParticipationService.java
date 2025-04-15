package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.payload.response.ParticipationResponse;

import java.util.List;

public interface ParticipationService {
    List<ParticipationResponse> getAllParticipations();

    ParticipationResponse addParticipation(ParticipationRequest participationRequest);

    ParticipationResponse editParticipation(ParticipationRequest participationRequest, long participationId);

    public void deleteParticipationById(long participationId);

    List<Participation> getParticipationByCagnotteUrl(String cagnotteUrl, String accessToken);
}
