package com.example.hirehustle.users.Applicant;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @GetMapping("/getAllApplicants")
    public List<Applicant> getAllApplicants(){
        return applicantService.getAllApplicants();
    }

    @PostMapping("/register")
    public String applicantRegister(@RequestBody Applicant applicant){
        return applicantService.register(applicant);
    }

    @PostMapping("/login")
    public String applicantLogin(@RequestBody Applicant applicant){
        return applicantService.login(applicant);
    }

    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam("token") String token){
        return applicantService.confirmToken(token);
    }
}
