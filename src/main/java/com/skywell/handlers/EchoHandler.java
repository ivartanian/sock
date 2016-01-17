package com.skywell.handlers;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by uartan on 16.01.2016.
 */
public class EchoHandler extends TextWebSocketHandler {

    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        session.sendMessage(new TextMessage(textMessage.getPayload()));
    }
}
