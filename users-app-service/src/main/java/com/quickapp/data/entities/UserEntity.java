package com.quickapp.data.entities;

import com.quickapp.shared.Permission;
import com.quickapp.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("Users")
public class UserEntity implements Serializable {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
    private Role role;
    private List<Permission> permissions;
}
