package com.grey.cagnotte.service;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;

import java.util.List;

public interface NotificationService {
    public List<String> getAdminEmails();

    public void sendEmail(String to, String subject, String message);

}
