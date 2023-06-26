package com.example.hirehustle.users.hr;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
//    private String companyRepresentativeName;
//    private String companyRepresentativeEmail;
//    private String personalEmail;
    private String mobileNumber;
    private String nationalId;
    private String addressLine;
    private String username;
    private String password;
    private String email;
    private boolean isActivated = false;
    private boolean isEnabled = true;
    private int age;

    @OneToMany(mappedBy = "hr", cascade = CascadeType.ALL)
    List<Token> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "hr", cascade = CascadeType.ALL)
    List<JobPost> jobPosts;
    public HR(String firstName,
              String lastName,
              String companyRepresentativeName,
              String companyRepresentativeEmail,
              String personalEmail,
              String mobileNumber,
              String nationalId,
              String addressLine,
              String username,
              String password,
              String email,
              int age) {
        this.firstName = firstName;
        this.lastName = lastName;
//        this.companyRepresentativeName = companyRepresentativeName;
//        this.companyRepresentativeEmail = companyRepresentativeEmail;
//        this.personalEmail = personalEmail;
        this.mobileNumber = mobileNumber;
        this.nationalId = nationalId;
        this.addressLine = addressLine;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void setTokens(Token token) {
        tokens.add(token);
    }
}
