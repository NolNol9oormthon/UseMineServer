package com.nolnol.useminserver.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from item i " +
            "where i.available_end_time >= :now " +
            "and i.available_start_time >= :cursorStartTime " +
            "and ((i.available_start_time = :cursorStartTime and i.id > :cursorId) " +
            "or i.available_start_time >= :cursorStartTime and i.id != :cursorId) " +
            "order by i.available_end_time, i.available_end_time, i.id " +
            "limit :pageSize",
            nativeQuery = true)
    List<Item> findAllBeforeExpiration(LocalDateTime now, LocalDateTime cursorStartTime, Long cursorId, int pageSize);

    @Query(value = "select * from item i " +
            "where i.available_end_time >= :now " +
            "and i.category = :category " +
            "and ((i.available_start_time = :cursorStartTime and i.id > :cursorId) " +
            "or i.available_start_time >= :cursorStartTime and i.id != :cursorId) " +
            "order by i.available_end_time, i.available_end_time, i.id " +
            "limit :pageSize",
            nativeQuery = true)
    List<Item> findAllByCategoryBeforeExpiration(String category, LocalDateTime now, LocalDateTime cursorStartTime, Long cursorId, int pageSize);

    @Query(value = "select * from item i order by i.available_start_time, i.id limit 1", nativeQuery = true)
    Optional<Item> findByMinStartTime();

    List<Item> findAllByOwnerIdOrderByIdDesc(Long memberId);
}
