package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.users.Tester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TesterRepository extends JpaRepository<Tester, Long> {

    Optional<Tester> findByEmailIgnoreCase(String emailId);

    Boolean existsByEmail(String email);

    Optional<Tester> findByUsername(String username);

    Boolean existsByUsername(String username);

}
