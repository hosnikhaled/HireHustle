package com.example.hirehustle.jobPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public String createJobPost(JobPost jobPost) {
        try {
            int expirationPeriod = jobPost.getExpirationPeriod();
            LocalDateTime expirationTime = LocalDateTime.now().plusDays(expirationPeriod);
            jobPost.setExpirationTime(expirationTime);
            jobPost.setCreationTime(LocalDateTime.now());
            jobPostRepository.save(jobPost);
            return "Job Post Created Successfully.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<JobPost> fetchAllJobPosts() {
        return jobPostRepository.findAll();
    }
}
