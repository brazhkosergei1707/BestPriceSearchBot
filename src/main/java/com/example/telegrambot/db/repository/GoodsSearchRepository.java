package com.example.telegrambot.db.repository;

import com.example.telegrambot.entity.GoodsSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsSearchRepository extends JpaRepository<GoodsSearch, Long> {
}
