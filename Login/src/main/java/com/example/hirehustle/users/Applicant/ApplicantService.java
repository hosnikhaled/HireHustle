package com.example.hirehustle.users.Applicant;

import com.example.hirehustle.email.EmailService;
import com.example.hirehustle.token.Token;
import com.example.hirehustle.token.TokenService;
import com.example.hirehustle.token.TokenType;
import com.example.hirehustle.users.Person.Person;
import com.example.hirehustle.users.Person.UserService;
import com.example.hirehustle.users.responses.Login.LoginFailedResponse;
import com.example.hirehustle.users.responses.Login.LoginResponse;
import com.example.hirehustle.users.responses.Login.LoginSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final UserService userService;
    private final TokenService tokenService;
    private final ApplicantRepository applicantRepository;
    private final EmailService emailService;

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Transactional
    public String register(Applicant applicant) {
        // If applicant is stored and Enabled.
        if (isExist(applicant.getUsername(), applicant.getEmail()) && applicant.isEnabled()) {
            throw new IllegalStateException("Email or Username is already taken.");
        }
        // If applicant is not exist at all.
        else if (! isExist(applicant.getUsername(), applicant.getEmail())) {
            userService.saveUser(new Person(applicant.getUsername(), applicant.getEmail()));
            Token token = tokenService.generateAuthorizationToken(applicant, null);
            String activationLink = "https://hirehustle-production.up.railway.app/api/v1/applicant/confirmToken?token=" + token.getToken();
            emailService.sendEmail(applicant.getUsername(), applicant.getEmail(), activationLink);
            applicant.setTokens(token);
            tokenService.saveToken(token);
            applicantRepository.save(applicant);
            return token.getToken();
        }
        // If applicant is stored but not enabled.
        // TODO: Get Token of the applicant.
        Applicant savedApplicant = applicantRepository.getByUsername(applicant.getUsername());
        Token token = tokenService.getToken(getAuthorizationToken(savedApplicant));
        String activationLink = "https://hirehustle-production.up.railway.app/api/v1/applicant/confirmToken?token=" + token.getToken();
        emailService.sendEmail(applicant.getUsername(), applicant.getEmail(), activationLink);
        return token.getToken();
    }

    public boolean isExist(String username, String email) {
        return userService.isExist(username, email);
    }

    public void enableApplicant(String email) {
        Applicant applicant = applicantRepository.getByEmail(email);
        applicant.setEnabled(true);
        applicantRepository.save(applicant);
    }

    public void disableApplicant(String email) {
        Applicant applicant = applicantRepository.getByEmail(email);
        applicant.setEnabled(false);
        applicantRepository.save(applicant);
    }

    public String getAuthorizationToken(Applicant applicant) {
        List<Token> tokens = applicant.getTokens();
        for (Token token : tokens) {
            if (token.getTokenType() == TokenType.Authorization) {
                return token.getToken();
            }
        }
        return null;
    }

    @Transactional
    public String confirmToken(String token) {
        String result = tokenService.confirmToken(token);
        if (result.equals("confirmed")) {
            Token confirmationToken = tokenService.getToken(token);
            activateApplicant(confirmationToken.getApplicant().getEmail());
        }
        return result;
    }

    public void activateApplicant(String email) {
        Applicant applicant = applicantRepository.getByEmail(email);
        applicant.setActivated(true);
        applicantRepository.save(applicant);
    }

    public LoginResponse login(Applicant applicant) {
        LoginResponse response;
        if (! dataIsValid(applicant.getUsername(), applicant.getPassword())) {
            String message = "The password or username that you have entered is incorrect.";
            response = new LoginFailedResponse("failed", message);
            return response;
        }
        Applicant applicant1 = applicantRepository.getByUsername(applicant.getUsername());
        if (applicant1.isActivated()) {
            Token token = tokenService.generateAccessToken(applicant1, null);
            tokenService.saveToken(token);
            response = new LoginSuccessResponse("success", token.getToken());
            return response;
        }
        if (!applicant1.isEnabled()){
            return new LoginFailedResponse("failed", "Sorry, Your account is disabled by admin.");
        }
        return new LoginFailedResponse("failed", "Sorry, Account is not activated.");
    }

    public boolean dataIsValid(String username, String password) {
        return applicantRepository.dataIsValid(username, password).isPresent();
    }


}
