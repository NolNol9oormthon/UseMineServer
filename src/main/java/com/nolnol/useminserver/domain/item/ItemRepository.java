package com.nolnol.useminserver.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query("select i from Item as i where i.availableEndTime >= :now order by i.availableEndTime, i.availableEndTime, i.id")
    List<Item> findAllBeforeExpiration(LocalDateTime now);

    @Query("select i from Item as i where i.availableEndTime >= :now and i.category = :category order by i.availableEndTime, i.availableEndTime, i.id")
    List<Item> findAllByCategoryBeforeExpiration(Category category, LocalDateTime now);
}
