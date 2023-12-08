package com.exam.loginApi.controller;

import com.exam.loginApi.encryption.MD5Hash;
import com.exam.loginApi.entity.User;
import com.exam.loginApi.repository.UserRepository;
import com.exam.loginApi.response.ErrorResponse;
import com.exam.loginApi.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository repo;


    @GetMapping()
    public Object login(@RequestHeader("Authorization") String authHeader) {
        String[] credentials = extractCredentials(authHeader);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String encryptedPassword = MD5Hash.encrypt(credentials[1]);

            Optional<User> userOptional = repo.findByUsernameAndPassword(username, encryptedPassword);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return new SuccessResponse(user.getLastName(), user.getFirstName(), user.getMiddleName(), user.getBirthdate());
            }
        }
        return new ErrorResponse("Login Failed!");
    }

    private String[] extractCredentials(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            return credentials.split(":",2);
        }
        return null;
    }
}
