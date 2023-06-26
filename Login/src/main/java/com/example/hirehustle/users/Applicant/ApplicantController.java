package com.example.hirehustle.users.Applicant;

import com.example.hirehustle.users.Responses.Login.LoginResponse;
import com.example.hirehustle.users.Responses.Registration.RegistrationResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final Gson gson = new Gson();

    @GetMapping("/getAllApplicants")
    public List<Applicant> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @PostMapping("/register")
    public String applicantRegister(@RequestBody Applicant applicant) {
        RegistrationResponse registrationResponse = applicantService.register(applicant);
        return gson.toJson(registrationResponse.mapToArrangeGson());
    }

    @PostMapping("/login")
    public String applicantLogin(@RequestBody Applicant applicant) {
        LoginResponse loginResponse = applicantService.login(applicant);
        return gson.toJson(loginResponse.mapToArrangeGson());
    }

    @GetMapping("/confirmToken")
    public String confirmToken(@RequestParam("token") String token) {
        return applicantService.confirmToken(token);
    }
}
