package com.Test.Tivibu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "result")
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


    public Result() {
    }

    public Result(Long id, Boolean isOk, String comment, byte[] reportPhoto) {
        this.id = id;
        this.isOk = isOk;
        this.comment = comment;
        this.reportPhoto = reportPhoto;
    }

    public Result(Long id, String comment, Boolean isOk) {
        this.id = id;
        this.comment = comment;
        this.isOk = isOk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(byte[] reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }
}
