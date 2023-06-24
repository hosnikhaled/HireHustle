package com.example.hirehustle.users.admin;

import com.example.hirehustle.token.Token;
import com.example.hirehustle.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    public String addAdmin(Admin admin){
        if (adminIsExist(admin.getUsername())){
            throw new IllegalStateException("username is already taken.");
        }
        adminRepository.save(admin);
        return "Admin is added.";
    }

    public String adminLogin(Admin admin){
        if (dataIsValid(admin.getUsername(),admin.getPassword())){
            throw new IllegalStateException("The password or username that you've entered is incorrect.");
        }
        return "Done";
    }

    public boolean adminIsExist(String username){
        return adminRepository.adminIsExist(username).isPresent();
    }

    private boolean dataIsValid(String username, String password){
        return adminRepository.dataIsValid(username,password).isPresent();
    }

}
