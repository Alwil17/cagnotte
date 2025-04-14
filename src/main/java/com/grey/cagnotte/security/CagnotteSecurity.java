package com.grey.cagnotte.security;

import com.grey.cagnotte.entity.Cagnotte;
import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.payload.response.CagnotteResponse;
import com.grey.cagnotte.service.CagnotteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("cagnotteSecurity")
@RequiredArgsConstructor
public class CagnotteSecurity {

    private final CagnotteService cagnotteService;

    /**
     * Checks if the authenticated user is the creator of the fundraiser identified by the slug.
     *
     * @param authentication The Authentication object retrieved by Spring Security
     * @param slug The unique slug of the fundraiser
     * @return true if the user is the owner, false otherwise.
     */
    public boolean isOwner(Authentication authentication, String slug) {
        if (authentication == null || slug == null) {
            return false;
        }
        // Suppose que ton Authentication.getPrincipal() retourne une instance de ton entité User
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User currentUser = (User) principal;
            CagnotteResponse cagnotte = cagnotteService.getCagnotteBySlug(slug);
            if (cagnotte == null || cagnotte.getUser() == null) {
                return false;
            }
            return currentUser.getId() == cagnotte.getUser().getId();
        }
        return false;
    }
}