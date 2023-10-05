package com.example.telegrambot.db.repository;

import com.example.telegrambot.entity.GoodsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDetailsRepository extends JpaRepository<GoodsDetails, Long> {
    @Query("SELECT b FROM GoodsDetails b WHERE b.telegramChatId = :telegramChatId and b.activeSearch = :activeSearch")
    List<GoodsDetails> getAllActiveGoodsDetailsByTelegramChatId(String telegramChatId, boolean activeSearch);

    @Query("SELECT b FROM GoodsDetails b WHERE b.activeSearch = :activeSearch")
    List<GoodsDetails> getAllActiveGoodsDetails(boolean activeSearch);
    @Query("SELECT b FROM GoodsDetails b WHERE b.lastUpdateTime > :date")
    List<GoodsDetails> findByUpdatedDateAfter(@Param("date") Long date);
}
