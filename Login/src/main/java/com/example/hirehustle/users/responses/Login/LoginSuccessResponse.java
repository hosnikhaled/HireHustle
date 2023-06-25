package com.example.hirehustle.users.responses.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoginSuccessResponse extends LoginResponse{

    private final String data;

    public LoginSuccessResponse(String status, String data) {
        super(status);
        this.data = data;
    }

    @Override
    public Map<String, Object> mapToArrangeGson(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", super.getStatus());
        map.put("data", data);
        return map;
    }

    public String getData() {
        return data;
    }
}
