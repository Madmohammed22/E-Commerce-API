package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody Map<String, String> request) {
        UserDto userDto = UserDto.builder()
                .email(request.get("email"))
                .fullName(request.get("fullName"))
                .address(request.get("address"))
                .phone(request.get("phone"))
                .build();

        return ResponseEntity.ok(authService.register(userDto, request.get("password")));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

