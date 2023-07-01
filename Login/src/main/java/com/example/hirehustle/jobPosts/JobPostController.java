package com.example.hirehustle.jobPosts;

import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobPosts")
@Controller
public class JobPostController {

    private final JobPostService jobPostService;

    private final Gson gson = new Gson();


    @GetMapping("/allValidJobPosts")
    public String getAllValidJobPosts(){
        JobPostAdditionResponse response = jobPostService.fetchAllValidJobPosts();
        return gson.toJson(response);
    }
}
