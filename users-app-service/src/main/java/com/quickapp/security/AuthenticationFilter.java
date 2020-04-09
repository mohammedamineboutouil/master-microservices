package com.quickapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.quickapp.service.UserService;
import com.quickapp.shared.UserDto;
import com.quickapp.ui.model.LoginRequestModel;
import com.quickapp.ui.model.LoginResponseModel;
import com.quickapp.ui.model.UserResponseModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;

    @Autowired
    public AuthenticationFilter(UserService userService,
                                Environment environment,
                                AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserByEmail(userName);

        UserResponseModel userModel = new UserResponseModel();

        BeanUtils.copyProperties(userDetails, userModel);

        String token = Jwts.builder()
                .setSubject(userDetails.getId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("jwt.expiration"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret"))
                .claim("user", userModel)
                .compact();

        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");

        String json = new Gson().toJson(new LoginResponseModel(token, userDetails.getId()));
        res.getWriter().write(json);

//        res.addHeader("token", token);
//        res.addHeader("userId", userDetails.getUserId());
    }
}