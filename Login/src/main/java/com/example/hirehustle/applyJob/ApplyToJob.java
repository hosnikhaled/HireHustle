package com.example.hirehustle.applyJob;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.users.Applicant.Applicant;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ApplyToJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

}
