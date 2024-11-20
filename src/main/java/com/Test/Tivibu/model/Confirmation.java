package com.Test.Tivibu.model;

import com.Test.Tivibu.model.users.Tester;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId")
    private Long tokenId;

    @OneToOne(targetEntity = Tester.class)
    @JoinColumn(nullable = false, name = "tester_id")
    private Tester tester;

    @Column(name = "confirmationToken", unique = true)
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    public Confirmation(Tester tester) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.tester = tester;
    }

    public Confirmation() {
    }
}
