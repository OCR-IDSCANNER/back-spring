package com.example.backspring.repository;

import com.example.backspring.entities.CardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDataRepository extends JpaRepository<CardData, Long> {

}
