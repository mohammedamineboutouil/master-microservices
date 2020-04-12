package com.quickapp.ui.model;

import com.quickapp.shared.Authority;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseModel {
    private String id;
    private String lastName;
    private String email;
    private List<Authority> authorities;
    private boolean isActivated;
}