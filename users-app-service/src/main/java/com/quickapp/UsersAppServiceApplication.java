package com.quickapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class UsersAppServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UsersAppServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("It's ruining....");
//        userRepository.deleteAll();
//
//        List<Permission> costumerPermissions = new ArrayList<>();
//        costumerPermissions.add(Permission.WRITE);
//        costumerPermissions.add(Permission.READ);
//
//        List<Permission> adminPermissions = new ArrayList<>();
//        adminPermissions.add(Permission.WRITE);
//        adminPermissions.add(Permission.EXECUTE);
//        adminPermissions.add(Permission.OWNER);
//        adminPermissions.add(Permission.READ);
//        adminPermissions.add(Permission.GROUP);
//
//        UserDto admin = new UserDto("mohammed", "amine", "mohammed@admin.com", "admin", Role.SUPER_ADMIN, adminPermissions);
//        UserDto costumer = new UserDto("salah", "toubali", "salah@costumer.com", "costumer", Role.COSTUMER, costumerPermissions);
//
//        UserEntity adminEntity = new UserEntity();
//        UserEntity costumerEntity = new UserEntity();
//
//        BeanUtils.copyProperties(admin, adminEntity);
//        BeanUtils.copyProperties(costumer, costumerEntity);
//
//        this.userRepository.save(adminEntity);
//        this.userRepository.save(costumerEntity);
//
//        for (UserEntity userEntity : this.userRepository.findAll()) {
//            System.out.println(userEntity.getId());
//        }
    }
}