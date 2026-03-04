package com.example.userservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
}
