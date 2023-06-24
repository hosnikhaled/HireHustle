package com.example.hirehustle.jobPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobPosts")
@Controller
public class JobPostController {

    private final JobPostService jobPostService;

    @PostMapping("/createJobPost")
    public String createJobPost(@RequestBody JobPost jobPost){
        return jobPostService.createJobPost(jobPost);
    }

    @GetMapping("/AllJobPosts")
    public List<JobPost> getAllJobPosts(){
        return jobPostService.fetchAllJobPosts();
    }
}
