package com.bitsian.BitsianJavaClient.websocket;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BITSIAN_FEED_ENDPOINT;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.WEBSOCKET_BASE_URL;

@Component
public class ServiceClient {

    public StompSession onConnected() throws ExecutionException, InterruptedException {

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setInboundMessageSizeLimit(Integer.MAX_VALUE);

        String url = WEBSOCKET_BASE_URL + BITSIAN_FEED_ENDPOINT;
        StompSessionHandler sessionHandler = new MySessionHandler();

        return stompClient.connect(url, sessionHandler).get();


    }


}
