package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(long bookerId);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where br.id = :bookerId and b.end < :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByBookerAndPast(@Param("bookerId") long bookerId, @Param("now") LocalDateTime now);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where br.id = :bookerId and b.start > :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByBookerAndFuture(@Param("bookerId") long bookerId, @Param("now") LocalDateTime now);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where br.id = :bookerId and b.start < :now  and b.end > :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByBookerAndCurrent(@Param("bookerId") long bookerId, @Param("now") LocalDateTime now);

    List<Booking> findByBookerIdAndStatus(long bookerId, String status);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where o.id = :ownerId and b.end < :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByOwnerAndPast(@Param("ownerId") long ownerId, @Param("now") LocalDateTime now);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where o.id = :ownerId and b.start > :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByOwnerAndFuture(@Param("ownerId") long ownerId, @Param("now") LocalDateTime now);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where o.id = :ownerId and b.start < :now and b.end > :now " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByOwnerAndCurrent(@Param("ownerId") long ownerId, @Param("now") LocalDateTime now);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where o.id = :ownerId and b.status = :status " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByOwnerAndStatus(@Param("ownerId") long ownerId, @Param("status") String status);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "join i.owner as o " +
            "where o.id = :ownerId " +
            "group by b.start " +
            "order by b.start desc ")
    List<Booking> findAllBookingByOwner(@Param("ownerId") long ownerId);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "where i.id = :itemId ")
    Booking findByItemId(@Param("itemId") long itemId);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "where i.id = :itemId and b.end < :now and b.status = :status")
    Booking findByItemIdPast(@Param("itemId") long itemId,
                             @Param("now") LocalDateTime now,
                             @Param("status") Status status);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "where i.id = :itemId and b.start > :now and b.status = :status")
    Booking findByItemIdFuture(@Param("itemId") long itemId,
                               @Param("now") LocalDateTime now,
                               @Param("status") Status status);

    @Query("select b " +
            "from Booking as b " +
            "join b.item as i " +
            "join b.booker as br " +
            "where br.id = :userId and b.end < :now")
    Optional<Booking> findByUserId(@Param("userId") long userId, @Param("now") LocalDateTime now);


}
