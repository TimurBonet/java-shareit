package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private long id;
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    @NotBlank
    @Email
    @Size(min = 2, max = 254)
    private String email;
}
