package com.quickapp.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document("Department")
@AllArgsConstructor @ToString @Builder
public class DepartmentEntity  implements Serializable {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private UserEntity manager;
    @DBRef
    private List<UserEntity> employees;
}
