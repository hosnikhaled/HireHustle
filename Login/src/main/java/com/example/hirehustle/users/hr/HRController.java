package com.example.hirehustle.users.hr;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.jobPosts.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
public class HRController {

    private final HRService hrService;
    private final JobPostService jobPostService;

    @GetMapping("/getAllHrs")
    public List<HR> getAllHRs(){
        return hrService.getAllHRs();
    }

    @PostMapping("/register")
    public String HrRegister(@RequestBody HR hr){
        return hrService.register(hr);
    }

    @PostMapping("/login")
    public String HrLogin(@RequestBody HR hr){
        return hrService.hrLogin(hr);
    }

    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam("token") String token){
        return hrService.confirmToken(token);
    }

    @PostMapping("/createJobPost")
    public String createJobPost(@RequestBody JobPost jobPost){
        return jobPostService.createJobPost(jobPost);
    }
}
