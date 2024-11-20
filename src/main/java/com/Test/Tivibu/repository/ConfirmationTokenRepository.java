package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<Confirmation, Long> {
    Optional<Confirmation> findByConfirmationToken(String confirmationToken);
}
