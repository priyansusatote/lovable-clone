package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.chat.ChatEventResponse;
import com.priyansu.project.lovable_clone.dto.chat.ChatResponse;
import com.priyansu.project.lovable_clone.entity.ChatEvent;
import com.priyansu.project.lovable_clone.entity.ChatMessage;
import com.priyansu.project.lovable_clone.enums.ChatEventType;
import com.priyansu.project.lovable_clone.enums.MessageRole;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-05T15:38:30+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ChatMapperImpl implements ChatMapper {

    @Override
    public List<ChatResponse> toChatResponseList(List<ChatMessage> chatMessagesList) {
        if ( chatMessagesList == null ) {
            return null;
        }

        List<ChatResponse> list = new ArrayList<ChatResponse>( chatMessagesList.size() );
        for ( ChatMessage chatMessage : chatMessagesList ) {
            list.add( chatMessageToChatResponse( chatMessage ) );
        }

        return list;
    }

    protected ChatEventResponse chatEventToChatEventResponse(ChatEvent chatEvent) {
        if ( chatEvent == null ) {
            return null;
        }

        Long id = null;
        ChatEventType type = null;
        Integer sequenceOrder = null;
        String content = null;
        String filePath = null;
        String metadata = null;

        id = chatEvent.getId();
        type = chatEvent.getType();
        sequenceOrder = chatEvent.getSequenceOrder();
        content = chatEvent.getContent();
        filePath = chatEvent.getFilePath();
        metadata = chatEvent.getMetadata();

        ChatEventResponse chatEventResponse = new ChatEventResponse( id, type, sequenceOrder, content, filePath, metadata );

        return chatEventResponse;
    }

    protected List<ChatEventResponse> chatEventListToChatEventResponseList(List<ChatEvent> list) {
        if ( list == null ) {
            return null;
        }

        List<ChatEventResponse> list1 = new ArrayList<ChatEventResponse>( list.size() );
        for ( ChatEvent chatEvent : list ) {
            list1.add( chatEventToChatEventResponse( chatEvent ) );
        }

        return list1;
    }

    protected ChatResponse chatMessageToChatResponse(ChatMessage chatMessage) {
        if ( chatMessage == null ) {
            return null;
        }

        Long id = null;
        String content = null;
        MessageRole role = null;
        List<ChatEventResponse> events = null;
        Integer tokenUsed = null;
        Instant createdAt = null;

        id = chatMessage.getId();
        content = chatMessage.getContent();
        role = chatMessage.getRole();
        events = chatEventListToChatEventResponseList( chatMessage.getEvents() );
        tokenUsed = chatMessage.getTokenUsed();
        createdAt = chatMessage.getCreatedAt();

        ChatResponse chatResponse = new ChatResponse( id, content, role, events, tokenUsed, createdAt );

        return chatResponse;
    }
}
