package com.skywell.config;

import com.skywell.handlers.EchoHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@ComponentScan("com.skywell")
//@EnableWebSocket
@EnableAutoConfiguration
@EnableAsync
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer /*implements WebSocketConfigurer*/ {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

//    @Bean
//    public SimpleAsyncTaskExecutor taskExecutor() {
//        return new SimpleAsyncTaskExecutor();
//    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(echoHandler(), "/echoHandler")
//                .withSockJS();
//    }
//
//    @Bean
//    public WebSocketHandler echoHandler() {
//        return new EchoHandler();
//    }

}