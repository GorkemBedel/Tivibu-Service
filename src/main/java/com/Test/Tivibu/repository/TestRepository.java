package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByType(String type);

    @Query("SELECT DISTINCT t.type FROM Test t")
    Set<String> findAllTestTypes();

}
