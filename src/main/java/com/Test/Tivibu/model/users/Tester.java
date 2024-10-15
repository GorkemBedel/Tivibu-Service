package com.Test.Tivibu.model.users;

import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.TestResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "testers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tester implements UserDetails {

    @Id
    @Column(name = "tester_id")
    private Long tester_id;
    private String name;

    //Fields for the UserDetails interface
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean accountNonExpired;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isEnabled;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean accountNonLocked;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean credentialsNonExpired;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_TESTER;

    @JsonIgnore
    @OneToMany(mappedBy = "tester", cascade = CascadeType.ALL)
    private List<TestResult> testResults = new ArrayList<>();  //Every tester has 90 "TestResult" objects


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "all_authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Role> authorities = new HashSet<>(List.of(Role.ROLE_TESTER));

}
