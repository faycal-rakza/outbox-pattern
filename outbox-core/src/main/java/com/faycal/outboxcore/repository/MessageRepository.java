package com.faycal.outboxcore.repository;

import com.faycal.outboxcore.entity.TruckMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<TruckMessage, Long> {
    List<TruckMessage> findByStatusIsNullOrderByDateTimeAsc();
}
