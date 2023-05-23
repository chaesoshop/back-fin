package com.carrot.backend.chatting.dao;


import com.carrot.backend.chatting.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRepository extends JpaRepository<Chatting, Integer> {
    List<Chatting> findByRoomId(String roomId);
}
