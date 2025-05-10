package com.alkemy.java2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private long id;

    @NotBlank
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    private Set<String> roles; // solo nombres


}

