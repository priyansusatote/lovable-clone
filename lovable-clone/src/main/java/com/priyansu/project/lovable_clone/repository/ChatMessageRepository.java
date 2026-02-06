package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.ChatMessage;
import com.priyansu.project.lovable_clone.entity.ChatSession;
import com.priyansu.project.lovable_clone.enums.ChatEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
    SELECT DISTINCT m FROM ChatMessage m
    LEFT JOIN FETCH m.events 
    WHERE m.chatSession = :chatSession
    ORDER BY m.createdAt ASC
""")
    List<ChatMessage> findByChatSession(ChatSession chatSession);
}
