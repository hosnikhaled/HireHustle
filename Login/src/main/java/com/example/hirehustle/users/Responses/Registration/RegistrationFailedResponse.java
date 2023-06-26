package com.example.hirehustle.users.Responses.Registration;

import java.util.LinkedHashMap;
import java.util.Map;


public class RegistrationFailedResponse extends RegistrationResponse {

    private final String message;

    public RegistrationFailedResponse(String status, String message) {
        super(status);
        this.message = message;
    }

    @Override
    public Map<String, Object> mapToArrangeGson(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", super.getStatus());
        map.put("message", message);
        return map;
    }

    public String getMessage() {
        return message;
    }
}
