package com.example.hirehustle.users.hr;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.jobPosts.JobPostService;
import com.example.hirehustle.users.responses.Login.LoginResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
public class HRController {

    private final HRService hrService;
    private final JobPostService jobPostService;

    private final Gson gson = new Gson();

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
        LoginResponse loginResponse = hrService.hrLogin(hr);
        return gson.toJson(loginResponse.mapToArrangeGson());
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
