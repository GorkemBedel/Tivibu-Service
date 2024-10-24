package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByType(String type);

}
