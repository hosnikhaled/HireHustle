package com.example.hirehustle.applyJob;

import com.example.hirehustle.jobPosts.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyToJob, Long> {
    List<ApplyToJob> findByJobPost(JobPost jobPost);
}
