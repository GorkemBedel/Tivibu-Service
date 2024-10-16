package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.users.TesterRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesterRequestRepository extends JpaRepository <TesterRequest, Long> {

    Boolean existsByUsername(String username);

}
