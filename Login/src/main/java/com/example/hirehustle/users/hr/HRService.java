package com.example.hirehustle.users.hr;

import com.example.hirehustle.email.EmailService;
import com.example.hirehustle.token.Token;
import com.example.hirehustle.token.TokenService;
import com.example.hirehustle.token.TokenType;
import com.example.hirehustle.users.Applicant.Applicant;
import com.example.hirehustle.users.Person.Person;
import com.example.hirehustle.users.Person.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HRService {

    private final UserService userService;
    private final TokenService tokenService;
    private final HRRepository hrRepository;
    private final EmailService emailService;

    public List<HR> getAllHRs() {
        return hrRepository.findAll();
    }

    @Transactional
    public String register(HR hr){
        // If hr is stored and Enabled.
        if (isExist(hr.getUsername(),hr.getEmail()) && hr.isEnabled()){
            throw new IllegalStateException("Email or Username is already taken.");
        }
        // If hr is not exist at all.
        else if (!isExist(hr.getUsername(),hr.getEmail())) {
            userService.saveUser(new Person(hr.getUsername(),hr.getEmail()));
            Token token = tokenService.generateAuthorizationToken(null,hr);
            String activationLink = "http://localhost:8080/api/v1/applicant/confirmToken?token=" + token;
            emailService.sendEmail(hr.getUsername(), hr.getEmail(), activationLink);
            tokenService.saveToken(token);
            hr.setTokens(token);
            hrRepository.save(hr);
            return token.getToken();
        }
        // If hr is stored but not enabled.
        // TODO: Get Token of the hr.
        HR savedHr = hrRepository.getByUsername(hr.getUsername());
        Token token = tokenService.getToken(getAuthorizationToken(savedHr));
        String activationLink = "http://localhost:8080/api/v1/hr/confirmToken?token=" + token;
        emailService.sendEmail(hr.getUsername(), hr.getEmail(), activationLink);
        return token.getToken();
    }

    public boolean isExist(String username, String email){
        return userService.isExist(username, email);
    }

    public String getAuthorizationToken(HR hr){
        List<Token> tokens = hr.getTokens();
        for (Token token : tokens) {
            if (token.getTokenType() == TokenType.Authorization) {
                return token.getToken();
            }
        }
        return null;
    }

    public void enableHR(String email) {
        HR hr = hrRepository.getByEmail(email);
        hr.setEnabled(true);
        hrRepository.save(hr);
    }

    @Transactional
    public String confirmToken(String token){
        String result = tokenService.confirmToken(token);
        if (result.equals("confirmed")){
            Token confirmationToken = tokenService.getToken(token);
            enableHR(confirmationToken.getHr().getEmail());
        }
        return result;
    }

    public String hrLogin(HR hr) {
        if (!dataIsValid(hr.getUsername(),hr.getPassword())){
            throw new IllegalStateException("The password or username that you've entered is incorrect.");
        }
        HR hr1 = hrRepository.getByUsername(hr.getUsername());
        if (hr1.isEnabled()) {
            Token token = tokenService.generateAccessToken(null,hr1);
            tokenService.saveToken(token);
            return token.getToken();
        }
        return "Sorry, Account not activated.";
    }

    public boolean dataIsValid(String username, String password){
        return hrRepository.dataIsValid(username,password).isPresent();
    }
}
