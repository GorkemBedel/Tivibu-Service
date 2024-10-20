package com.Test.Tivibu.model;

import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.serializer.ResultSerializer;
import com.Test.Tivibu.serializer.TesterSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.List;

@Entity
@Table(name = "test_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @JsonIgnore
    @JdbcTypeCode(Types.BINARY)
    private byte[] testPhoto;

    // if v1_result and v2_result are both true, then isOk is true
    private boolean testOk = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "v1_result_id", referencedColumnName = "id", nullable = true)
    @JsonSerialize(using = ResultSerializer.class) // Özel serileştirici
    private Result v1_result;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "v2_result_id", referencedColumnName = "id", nullable = true)
    @JsonSerialize(using = ResultSerializer.class) // Özel serileştirici
    private Result v2_result;


    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubTestResult> subTestsResults;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tester_id", referencedColumnName = "tester_id", nullable = false)
//    @JsonSerialize(using = TesterSerializer.class) // Özel serileştirici
    private Tester tester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;  // Every "TestResult" object has to belong to a "Device"


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;     // Every "TestResult" object has to belong to a "Test"

}
