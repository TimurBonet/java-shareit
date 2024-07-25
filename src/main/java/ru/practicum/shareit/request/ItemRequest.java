package ru.practicum.shareit.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class ItemRequest {
    @NotNull
    private Long id;
    private String description;
    @NonNull
    private Long requestor;
    private LocalDateTime created;
}
