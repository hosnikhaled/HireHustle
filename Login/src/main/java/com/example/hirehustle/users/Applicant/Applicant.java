package com.example.hirehustle.users.Applicant;

import com.example.hirehustle.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String nationalId;
    private String addressLine;
    private String username;
    private String password;
    private String residencePlace;
    private boolean isActivated = false;
    private boolean isEnabled = false;
    private int age;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    List<Token> tokens = new ArrayList<>();

    public Applicant(String firstName,
                     String lastName,
                     String mobileNumber,
                     String nationalId,
                     String addressLine,
                     String username,
                     String password,
                     String residencePlace,
                     String email,
                     int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.nationalId = nationalId;
        this.addressLine = addressLine;
        this.username = username;
        this.password = password;
        this.residencePlace = residencePlace;
        this.email = email;
        this.age = age;
    }

    public void setTokens(Token token) {
        tokens.add(token);
    }
}
