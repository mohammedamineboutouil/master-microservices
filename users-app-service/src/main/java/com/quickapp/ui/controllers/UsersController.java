package com.quickapp.ui.controllers;

import com.quickapp.service.UserService;
import com.quickapp.shared.Authority;
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


    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllUser() {

        List<UserDto> users = userService.getAllUsers();

        List<UserResponseModel> returnValue = new ArrayList<>();

        users.forEach(user -> {
            UserResponseModel model = new UserResponseModel();
            BeanUtils.copyProperties(user,model);
            returnValue.add(model);
        });

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping(
            value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestModel userDetails, Errors errors) {
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

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        UserResponseModel returnValue = new UserResponseModel();

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping(
            value = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> getCurrentUser(@RequestHeader(name = "authorization", required = false) String token) {
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
//
//    @GetMapping("/exec")
//    public void test() {
//        List<Authority> authoritiesAdmin = new ArrayList<>();
//        authoritiesAdmin.add(Authority.SUPER_ADMIN);
//
//        List<Authority> authoritiesCostumer = new ArrayList<>();
//        authoritiesCostumer.add(Authority.COSTUMER);
//
//        UserDto admin = new UserDto("mohammed", "amine", "mohammed@admin.com", "admin", authoritiesAdmin, true);
//        UserDto costumer = new UserDto("salah", "toubali", "salah@costumer.com", "costumer", authoritiesCostumer, true);
//
////        UserEntity adminEntity = new UserEntity();
////        UserEntity costumerEntity = new UserEntity();
////
////        BeanUtils.copyProperties(admin, adminEntity);
////        BeanUtils.copyProperties(costumer, costumerEntity);
////
////        this.userRepository.save(adminEntity);
////        this.userRepository.save(costumerEntity);
//
//        UserDto storedAdmin = this.userService.createUser(admin);
//        UserDto storedCostumer = this.userService.createUser(costumer);
//
//        System.out.println(storedAdmin.toString());
//        System.out.println(storedCostumer.toString());
//    }
}

