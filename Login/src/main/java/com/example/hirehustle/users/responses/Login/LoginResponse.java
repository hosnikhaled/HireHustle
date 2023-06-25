package com.example.hirehustle.users.responses.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public abstract class LoginResponse {

    private String status;

    public abstract Map<String, Object> mapToArrangeGson();
}
