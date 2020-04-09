package com.quickapp.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private Role role;
    private List<Permission> permissions;

    public UserDto(String firstName, String lastName, String email, String password, Role role, List<Permission> permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.permissions = permissions;
    }
}
