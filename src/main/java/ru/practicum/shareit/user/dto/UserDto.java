package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validationgroups.Marker;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(groups = {Marker.Create.class})
    private String name;
    @Email(groups = Marker.Create.class)
    @Email(groups = Marker.Update.class)
    private String email;
}
