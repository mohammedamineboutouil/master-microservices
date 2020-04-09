package com.quickapp.ui.controllers;

import com.quickapp.service.UserService;
import com.quickapp.shared.Permission;
import com.quickapp.shared.Role;
import com.quickapp.shared.UserDto;
import com.quickapp.ui.model.CreateUserRequestModel;
import com.quickapp.ui.model.UserResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment environment;
    private final UserService userService;
    HashMap<String, Object> result = new HashMap<>();

    @Autowired
    public UsersController(Environment environment, UserService userService) {
        this.environment = environment;
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + environment.getProperty("local.server.port") + ", with token " + environment.getProperty("jwt.secret") + " and expiration " + environment.getProperty("jwt.expiration");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserRequestModel userDetails, Errors errors) {
        result.clear();

        if (errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());

        UserDto isUserExisted = this.userService.getUserByEmail(userDetails.getEmail());
        if (isUserExisted != null) {
            result.put("message", isUserExisted.getEmail() + " is already existed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        UserResponseModel returnValue = new UserResponseModel();

        BeanUtils.copyProperties(createdUser, returnValue);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/me", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getCurrentUser(@RequestHeader(name = "authorization", required = false) String token) {
        result.clear();
        if (token == null) {
            result.put("message", "there is no token provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        UserDto userDto = userService.getUserByToken(token);

        UserResponseModel returnValue = new UserResponseModel();

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        UserResponseModel returnValue = new UserResponseModel();

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping("/exec")
    public void test() {
        List<Permission> costumerPermissions = new ArrayList<>();
        costumerPermissions.add(Permission.WRITE);
        costumerPermissions.add(Permission.READ);

        List<Permission> adminPermissions = new ArrayList<>();
        adminPermissions.add(Permission.WRITE);
        adminPermissions.add(Permission.EXECUTE);
        adminPermissions.add(Permission.OWNER);
        adminPermissions.add(Permission.READ);
        adminPermissions.add(Permission.GROUP);

        UserDto admin = new UserDto("mohammed", "amine", "mohammed@admin.com", "admin", Role.SUPER_ADMIN, adminPermissions);
        UserDto costumer = new UserDto("salah", "toubali", "salah@costumer.com", "costumer", Role.COSTUMER, costumerPermissions);

//        UserEntity adminEntity = new UserEntity();
//        UserEntity costumerEntity = new UserEntity();
//
//        BeanUtils.copyProperties(admin, adminEntity);
//        BeanUtils.copyProperties(costumer, costumerEntity);
//
//        this.userRepository.save(adminEntity);
//        this.userRepository.save(costumerEntity);

        UserDto storedAdmin = this.userService.createUser(admin);
        UserDto storedCostumer = this.userService.createUser(costumer);

        System.out.println(storedAdmin.toString());
        System.out.println(storedCostumer.toString());
    }
}

