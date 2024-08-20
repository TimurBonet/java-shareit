package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(Long id);

    @Query("select i " +
            "from Item as i " +
            "join i.owner as u " +
            "where i.available = true and (lower(i.name) like :text or lower(i.description) like :text) ")
    List<Item> findBySearchText(@Param("text") String text);


}
