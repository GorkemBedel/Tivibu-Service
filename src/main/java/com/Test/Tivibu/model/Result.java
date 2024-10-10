package com.Test.Tivibu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "result")
@Builder
@Data
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isOk;
    private String comment;

    @Lob
    @JsonIgnore
    @JdbcTypeCode(Types.BINARY)
    private byte[] reportPhoto;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "testResult_id", nullable = false)
//    private TestResult testResult;


    public Result() {
    }

    public Result(Long id, Boolean isOk, String comment, byte[] reportPhoto) {
        this.id = id;
        this.isOk = isOk;
        this.comment = comment;
        this.reportPhoto = reportPhoto;
    }


}
