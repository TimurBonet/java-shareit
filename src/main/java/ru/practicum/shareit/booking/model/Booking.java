package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Дата начала бронирования должна присутствовать")
    @Column(name = "start_date")
    private LocalDateTime start;

    @NotNull(message = "Дата окончания бронирования должна присутствовать")
    @Column(name = "end_date")
    private LocalDateTime end;

    @NotNull(message = "Арендованный предмет не может быть пустым")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull(message = "Id пользователя который бронирует не может быть пустым")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
