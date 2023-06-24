package com.example.hirehustle.jobPosts;

import com.example.hirehustle.users.hr.HR;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String jobTitle;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String jobLocation;
    private int salary;
    private String benefits;
    @Column(nullable = false)
    private int workingHours;
    private int overtime;
    @Column(
            nullable = false,
            length = 5000 // Maximum length of 5000 characters.
    )
    private String jobDescription;
    @Column(
            nullable = false,
            length = 5000
    )
    private String jobRequirements;
    private String additionalRequirements;
    private int appliedNumber;
    @Column(nullable = false)
    private LocalDateTime creationTime;
    @Column(nullable = false)
    private int expirationPeriod;
    @Column(nullable = false)
    private LocalDateTime expirationTime;

    //many jobPost to one hr
    @ManyToOne
    @JoinColumn(
            name = "HrId"
    )
    private HR hr;


}
