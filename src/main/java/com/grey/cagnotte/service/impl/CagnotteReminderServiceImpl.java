package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.repository.CagnotteRepository;
import com.grey.cagnotte.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CagnotteReminderServiceImpl {

    private final CagnotteRepository cagnotteRepository;
    private final ParticipationRepository participationRepository;

    @Scheduled(cron = "0 0 9 * * *") // Everyday at 9 O'clock
    public void checkCagnottes() {
        List<Cagnotte> all = cagnotteRepository.findAll();

        for (Cagnotte cagnotte : all) {
            LocalDateTime now = LocalDateTime.now();

            // Case 1 : The cagnotte is ending in less than 3 days
            if (cagnotte.getDateDue() != null && now.plusDays(3).isAfter(cagnotte.getDateDue())) {
                log.info("CagnotteApplication with url #{} is ending in 3 days !", cagnotte.getUrl());
                // -> notifier l’utilisateur
            }

            // Cas 2 : No participation in 7 days
            if (participationRepository.countByCagnotteId(cagnotte.getId()) == 0
                    && cagnotte.getCreated_at().plusDays(7).isBefore(now)) {
                log.info("CagnotteApplication with url #{} didn't receive participation yet.", cagnotte.getUrl());
                // TODO: Add notification here
            }
        }
    }
}

