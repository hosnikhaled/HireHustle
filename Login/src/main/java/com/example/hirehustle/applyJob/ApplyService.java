package com.example.hirehustle.applyJob;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.jobPosts.JobPostRepository;
import com.example.hirehustle.users.Applicant.Applicant;
import com.example.hirehustle.users.Applicant.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final JobPostRepository jobPostRepository;
    private final ApplicantRepository applicantRepository;

    public void applyForJob(Long jobPostId, Long applicantId) throws Exception {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new Exception("Job post not found"));
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new Exception("Applicant not found"));

        ApplyToJob apply = new ApplyToJob();
        apply.setJobPost(jobPost);
        apply.setApplicant(applicant);

        applyRepository.save(apply);
    }

    public List<Applicant> getApplicantsForJob(Long jobPostId) throws Exception {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new Exception("Job post not found"));

        List<ApplyToJob>  applyNumbers= applyRepository.findByJobPost(jobPost);

        List<Applicant> applicants = applyNumbers.stream()
                .map(ApplyToJob::getApplicant)
                .toList();

        return applicants;
    }
}
