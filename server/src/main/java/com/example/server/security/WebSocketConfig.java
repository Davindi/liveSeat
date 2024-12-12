package com.example.server.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint with STOMP support (SockJS as fallback)
        registry.addEndpoint("/ws") // This is the endpoint clients will use to connect
                .setAllowedOriginPatterns("*");  // Allow all origins (you can restrict this for security reasons)
                //.withSockJS(); // Enable SockJS fallback (you can remove this line if you don't need SockJS)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable a simple in-memory message broker to handle messages prefixed with /topic
        registry.enableSimpleBroker("/topic");
        // Prefix for outgoing messages (this is optional, but good practice)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
