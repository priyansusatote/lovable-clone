package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
}
