package com.example.hirehustle.users.hr;

import com.example.hirehustle.email.EmailService;
import com.example.hirehustle.token.Token;
import com.example.hirehustle.token.TokenService;
import com.example.hirehustle.token.TokenType;
import com.example.hirehustle.users.Person.Person;
import com.example.hirehustle.users.Person.UserService;
import com.example.hirehustle.users.Responses.Login.LoginFailedResponse;
import com.example.hirehustle.users.Responses.Login.LoginResponse;
import com.example.hirehustle.users.Responses.Login.LoginSuccessResponse;
import com.example.hirehustle.users.Responses.Registration.RegistrationFailedResponse;
import com.example.hirehustle.users.Responses.Registration.RegistrationResponse;
import com.example.hirehustle.users.Responses.Registration.RegistrationSuccessResponse;
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
    public RegistrationResponse register(HR hr){

        RegistrationResponse registrationResponse;

        // If hr is stored and activated.
        if (isExist(hr.getUsername(),hr.getEmail()) && hr.isActivated()){
            String message = "Email or Username is already taken.";
            registrationResponse = new RegistrationFailedResponse("failed", message);
            return registrationResponse;
        }
        // If hr is not exist at all.
        else if (!isExist(hr.getUsername(),hr.getEmail())) {
            userService.saveUser(new Person(hr.getUsername(),hr.getEmail()));
            Token token = tokenService.generateAuthorizationToken(null,hr);
            String activationLink = "https://hirehustle-production.up.railway.app/api/v1/hr/confirmToken?token=" + token.getToken();
            emailService.sendEmail(hr.getUsername(), hr.getEmail(), activationLink);
            tokenService.saveToken(token);
            hr.setTokens(token);
            hrRepository.save(hr);
            String data = "Registration success, please check your mail.";
            registrationResponse = new RegistrationSuccessResponse("success", data);
            return registrationResponse;
        }
        // If hr is stored but not activated.
        HR savedHr = hrRepository.getByUsername(hr.getUsername());
        Token token = tokenService.getToken(getAuthorizationToken(savedHr));
        String activationLink = "https://hirehustle-production.up.railway.app/api/v1/hr/confirmToken?token=" + token.getToken();
        emailService.sendEmail(hr.getUsername(), hr.getEmail(), activationLink);
        String data = "Please check your mail to activate your account.";
        registrationResponse = new RegistrationSuccessResponse("success", data);
        return registrationResponse;
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

    public void disableHR(String email) {
        HR hr = hrRepository.getByEmail(email);
        hr.setEnabled(false);
        hrRepository.save(hr);
    }

    @Transactional
    public String confirmToken(String token){
        String result = tokenService.confirmToken(token);
        if (result.equals("confirmed")){
            Token confirmationToken = tokenService.getToken(token);
            activateHR(confirmationToken.getHr().getEmail());
        }
        return result;
    }

    public void activateHR(String email) {
        HR hr = hrRepository.getByEmail(email);
        hr.setActivated(true);
        hrRepository.save(hr);
    }

    public LoginResponse hrLogin(HR hr) {
        LoginResponse response;
        if (!dataIsValid(hr.getUsername(),hr.getPassword())){
            String message = "The password or username that you have entered is incorrect.";
            response = new LoginFailedResponse("failed", message);
            return response;
        }
        HR hr1 = hrRepository.getByUsername(hr.getUsername());
        if (hr1.isActivated()) {
            Token token = tokenService.generateAccessToken(null,hr1);
            tokenService.saveToken(token);
            response = new LoginSuccessResponse("success", token.getToken());
            return response;
        }
        if (!hr1.isEnabled()){
            return new LoginFailedResponse("failed", "Sorry, Your account is disabled by admin.");
        }
        return new LoginFailedResponse("failed", "Sorry, Account is not activated.");
    }

    public boolean dataIsValid(String username, String password){
        return hrRepository.dataIsValid(username,password).isPresent();
    }
}
