package com.example.WebSocket.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatWebSocketHandler extends TextWebSocketHandler {


    private CopyOnWriteArrayList<WebSocketSession> copyOnWriteArrayList = new CopyOnWriteArrayList();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        copyOnWriteArrayList.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        copyOnWriteArrayList.forEach(webSocketSession -> {
            try {
                if(!session.getId().equals(webSocketSession.getId()))
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        copyOnWriteArrayList.remove(session);
    }
}
