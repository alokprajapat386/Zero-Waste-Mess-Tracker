package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private  String token;
    private  String username;
    private Role role;
}
