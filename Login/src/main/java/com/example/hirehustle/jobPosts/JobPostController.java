package com.example.hirehustle.jobPosts;

import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobPosts")
@Controller
public class JobPostController {

    private final JobPostService jobPostService;


    @GetMapping("/allValidJobPosts")
    public Map<String, Object> getAllValidJobPosts(){
        JobPostAdditionResponse response = jobPostService.fetchAllValidJobPosts();
        return response.mapToArrangeGson();
    }
}
