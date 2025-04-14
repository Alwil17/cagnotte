package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.State;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.StateRequest;
import com.grey.cagnotte.payload.response.StateResponse;
import com.grey.cagnotte.repository.StateRepository;
import com.grey.cagnotte.service.NotificationService;
import com.grey.cagnotte.service.StateService;
import com.grey.cagnotte.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {
    private final String NOT_FOUND= "STATE_NOT_FOUND";
    private final String name= "StateServiceImpl | ";
    // Inject JavaMailSender or your preferred notification mechanism
    // private final JavaMailSender mailSender;

    // For demo, a simple stub returning admin emails.
    public List<String> getAdminEmails() {
        return List.of("admin1@example.com", "admin2@example.com");
    }

    public void sendEmail(String to, String subject, String message) {
        // Implement your email sending logic here.
        // For instance, using mailSender.send(...)
        System.out.printf("Email sent to %s: %s - %s\n", to, subject, message);
    }
}
