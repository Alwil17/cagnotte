package com.grey.cagnotte.events;

import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CagnotteEventListener {
    private final NotificationService notificationService; // A service handling email or other notifications

    @EventListener
    public void handleCagnotteDraftCreatedEvent(CagnotteDraftCreatedEvent event) {
        Cagnotte cagnotte = event.getCagnotte();
        // Assume you have a list of admin emails, or fetch users with the admin role.
        List<String> adminEmails = notificationService.getAdminEmails();

        String subject = "New Draft CagnotteApplication Created";
        String message = String.format("A new cagnotte '%s' (ID: %d) has been created and is in DRAFT state.",
                cagnotte.getLabel(), cagnotte.getId());

        // Send notification to each admin
        for (String adminEmail : adminEmails) {
            notificationService.sendEmail(adminEmail, subject, message);
        }
    }
}

