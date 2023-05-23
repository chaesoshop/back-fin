package com.carrot.backend.chatting.webconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketInterceptor webSocketInterceptor;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        //prefix로 붙은 메시지가 송신되었을때 그 메시지를 브로커가 처리하겠다는 함수
        //queue는 1대1, topic은 1대다로 브로드캐스팅될때 주로 사용
        registry.enableSimpleBroker("/sub");
        //바로 브로커로 가는경우가 아니라 가공이나 처리가 필요할때 핸들러로 메시지가 라우팅되는 경로
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/wss/chat").setAllowedOriginPatterns("*");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(webSocketInterceptor);
    }
}
