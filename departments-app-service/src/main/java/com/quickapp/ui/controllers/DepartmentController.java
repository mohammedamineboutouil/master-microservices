package com.quickapp.ui.controllers;

import com.quickapp.data.clients.UserServiceClient;
import com.quickapp.data.entities.DepartmentEntity;
import com.quickapp.data.entities.UserEntity;
import com.quickapp.data.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserServiceClient userServiceClient;
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(UserServiceClient userServiceClient, DepartmentRepository departmentRepository) {
        this.userServiceClient = userServiceClient;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public void test(){
        logger.info("Test Calling Users microservice");
        List<UserEntity> userEntities = this.userServiceClient.getUsers();
        userEntities.forEach(System.out::println);
        DepartmentEntity departmentEntity = DepartmentEntity
                .builder()
                .description("Management department")
//                .manager(userEntities.get(0))
                .employees(userEntities)
                .name("HR")
                .build();
        DepartmentEntity storedEntity = this.departmentRepository.save(departmentEntity);
        System.out.println(storedEntity);
    }

    @GetMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> all(){
        List<DepartmentEntity> entities = this.departmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }
}
