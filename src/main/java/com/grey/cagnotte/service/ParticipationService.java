package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.Participation;
import com.grey.cagnotte.payload.request.ParticipationRequest;
import com.grey.cagnotte.payload.response.ParticipationResponse;

import java.util.List;

public interface ParticipationService {
    List<Participation> getAllParticipations();

    long addParticipation(ParticipationRequest participationRequest);

    void addParticipation(List<ParticipationRequest> participationRequests);

    List<Participation> getParticipationsByCagnotteId(long cagnotteId);

    void editParticipation(ParticipationRequest participationRequest, long participationId);

    public void deleteParticipationById(long participationId);
}
