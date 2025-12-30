package com.ecommerce.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String email;
    private String password;
}

