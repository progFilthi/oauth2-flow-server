package com.product.oauthintegration.services;

import com.product.oauthintegration.models.User;
import com.product.oauthintegration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    @Transactional
    public User requireCurrentUser() throws IllegalStateException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof OAuth2AuthenticationToken token) || !auth.isAuthenticated()) {
            throw new RuntimeException("Login required");
        }

        String provider = token.getAuthorizedClientRegistrationId();
        Object principal = token.getPrincipal();

        String providerUserId;
        String email;
        String name;
        String pictureUrl;

        if (principal instanceof OidcUser oidc) {
            providerUserId = oidc.getSubject();
            email = oidc.getEmail();
            name = oidc.getFullName();
            pictureUrl = oidc.getPicture() != null ? oidc.getPicture() : null;
        } else if (principal instanceof OAuth2User oauth2) {
            providerUserId = oauth2.getAttribute("sub");     // provider-specific; ok for Google OIDC-ish
            email = oauth2.getAttribute("email");
            name = oauth2.getAttribute("name");
            Object pic = oauth2.getAttribute("picture");
            pictureUrl = pic != null ? pic.toString() : null;
        } else {
            throw new IllegalStateException("Unsupported principal type: " + principal.getClass().getName());
        }

        if (providerUserId == null) {
            throw new IllegalStateException("Missing user id (sub) for provider=" + provider);
        }

        return userRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .map(u -> {
                    u.setEmail(email);
                    u.setName(name);
                    u.setPictureUrl(pictureUrl);
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    User u = new User();
                    u.setProvider(provider);
                    u.setProviderUserId(providerUserId);
                    u.setEmail(email);
                    u.setName(name);
                    u.setPictureUrl(pictureUrl);
                    return userRepository.save(u);
                });
    }
}
