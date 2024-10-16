package com.Test.Tivibu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "subTestResult")
@Builder
@Data
@AllArgsConstructor
public class SubTestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOk;

    private Boolean v1_isOk;
    private String v1_comment;
    private Boolean v2_isOk;
    private String v2_comment;


    @Lob
    @JsonIgnore
    @JdbcTypeCode(Types.BINARY)
    private byte[] v1_reportPhoto;

    @Lob
    @JsonIgnore
    @JdbcTypeCode(Types.BINARY)
    private byte[] v2_reportPhoto;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id", nullable = false)
    private TestResult testResult;


    public SubTestResult() {
    }



}
