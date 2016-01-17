package com.skywell.clients;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by viv on 15.01.2016.
 */
@ClientEndpoint
public class WSClient {

    private static Logger LOG = Logger.getLogger(WSClient.class.getName());

    private static Object waitLock = new Object();
    private static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received msg: " + message);
    }

    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static class MySessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("!!!!!!!!!!!!!");
        }
    }

    public static void main(String[] args) {

        WebSocketContainer container;
        Session session = null;
        try {
            WebSocketClient transport = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(transport);
            stompClient.setMessageConverter(new StringMessageConverter());
//            stompClient.setTaskScheduler(timer); // for heartbeats, receipts

            String url = "ws://192.168.56.1:8080/app/ws";
            StompSessionHandler handler = new MySessionHandler();
            HttpHeaders headers = new HttpHeaders();
            headers.add("quest", "quest");
            stompClient.connect(url, new WebSocketHttpHeaders(headers), handler);


            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}