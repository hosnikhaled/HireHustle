package com.example.hirehustle.jobPosts;

import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionFailedResponse;
import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionResponse;
import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostAdditionResponse createJobPost(JobPost jobPost) {
        JobPostAdditionResponse response;
        try {
            jobPost.setCreationTime(LocalDateTime.now());
            jobPostRepository.save(jobPost);
            response = new JobPostAdditionSuccessResponse(
                    "success",
                    "Job Post Created Successfully."
            );
            return response;
        } catch (Exception e) {
            response = new JobPostAdditionFailedResponse(
                    "failed",
                    "Sorry, Error occurred in storing the job post."
            );
            System.out.println(e.getMessage());
            return response;
        }
    }

    public JobPostAdditionResponse fetchAllValidJobPosts() {
        JobPostAdditionResponse response;
        try {
            List<JobPost> jobPosts = jobPostRepository.findByState(JobPostStates.VALID);
            response = new JobPostAdditionSuccessResponse(
                    "success",
                    jobPosts
            );
            return response;
        }catch (Exception e){
            response = new JobPostAdditionFailedResponse(
                    "failed",
                    "Sorry, Error occurred fetching job posts."
            );
            System.out.println(e.getMessage());
            return response;
        }
    }

    public JobPost getJobPost(Long jobPostId){
        if (jobPostRepository.findById(jobPostId).isPresent())
            return jobPostRepository.findById(jobPostId).get();
        return null;
    }
}
