package com.example.hirehustle.jobPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    @Query("SELECT jp FROM JobPost jp WHERE jp.jobPostState = :state")
    List<JobPost> findByState(@Param("state") JobPostStates state);
}
