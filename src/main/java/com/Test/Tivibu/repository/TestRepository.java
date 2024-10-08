package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
