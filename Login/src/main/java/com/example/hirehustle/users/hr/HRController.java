package com.example.hirehustle.users.hr;

import com.example.hirehustle.jobPosts.JobPost;
import com.example.hirehustle.jobPosts.JobPostService;
import com.example.hirehustle.users.Responses.JobPosts.JobPostAdditionResponse;
import com.example.hirehustle.users.Responses.Login.LoginResponse;
import com.example.hirehustle.users.Responses.Registration.RegistrationResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

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
        String encodedImage = hr.getProfileImage();
        hr.setImage(Base64.getDecoder().decode(encodedImage));
        RegistrationResponse registrationResponse = hrService.register(hr);
        return gson.toJson(registrationResponse.mapToArrangeGson());
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
    public Map<String, Object> createJobPost(@RequestBody JobPost jobPost){
        JobPostAdditionResponse response = jobPostService.createJobPost(jobPost);
        return response.mapToArrangeGson();
    }
}
