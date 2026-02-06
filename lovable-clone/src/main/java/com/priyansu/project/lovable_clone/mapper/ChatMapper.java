package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.chat.ChatResponse;
import com.priyansu.project.lovable_clone.entity.ChatEvent;
import com.priyansu.project.lovable_clone.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    List<ChatResponse> toChatResponseList(List<ChatMessage> chatMessagesList); //from List of chatMessage to List of ChatResponse
}

