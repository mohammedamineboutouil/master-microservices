package com.quickapp.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseModel {
    private String token;
    private String userId;
}
