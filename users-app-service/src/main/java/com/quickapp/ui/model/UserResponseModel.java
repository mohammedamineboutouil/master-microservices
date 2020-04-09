package com.quickapp.ui.model;

import com.quickapp.shared.Permission;
import com.quickapp.shared.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseModel {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<Permission> permissions;
}
