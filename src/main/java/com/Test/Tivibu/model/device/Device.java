package com.Test.Tivibu.model.device;

import com.Test.Tivibu.model.TestResult;
import jakarta.persistence.*;
import lombok.Builder;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device")
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceType;
    private String version;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<TestResult> testResults = new ArrayList<>();  //Every device has 90 "TestResult" objects

}
