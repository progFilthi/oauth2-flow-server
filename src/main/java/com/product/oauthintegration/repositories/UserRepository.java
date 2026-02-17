package com.product.oauthintegration.repositories;

import com.product.oauthintegration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderAndProviderUserId(
            String provider,
            String providerUserId
    );
}
