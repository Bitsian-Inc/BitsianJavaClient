package com.bitsian.BitsianJavaClient.websocket;

import com.bitsian.BitsianJavaClient.utils.CommonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Date;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.API_KEY;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.AUTH_FEED;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BITSIAN_API_KEY;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BITSIAN_API_SIGN;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BITSIAN_PASSPHRASE;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BITSIAN_TIMESTAMP;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BODY;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.ERROR;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.METHOD;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.ORDER_BOOK;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.PASSPHRASE;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.PATH;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.SNAP_SHOT;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.TRADE_TAPE;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.WEBSOCKET_DESTINATION_PRIFEX;

@Component
public class MySessionHandler extends StompSessionHandlerAdapter {

    private Logger log = LoggerFactory.getLogger(MySessionHandler.class);

    private StompSession stompSession;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

        this.stompSession = session;
    }


    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

        exception.printStackTrace();
        log.error("Exception Message handler" + exception.getMessage());
        session.disconnect();
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {


        log.error("Exception in Transport handler" + throwable.getMessage());
        throwable.printStackTrace();

    }

    public void authenticateSubscription() {

        String nonce = String.valueOf(new Date().getTime());

        String signature = CommonUtils.generateSignature(nonce, METHOD, PATH, BODY);

        log.info(signature + " " + new Date());

        StompHeaders headers = new StompHeaders();
        headers.setDestination(WEBSOCKET_DESTINATION_PRIFEX + AUTH_FEED);
        headers.add(BITSIAN_API_KEY, API_KEY);
        headers.add(BITSIAN_TIMESTAMP, nonce);
        headers.add(BITSIAN_API_SIGN, signature);
        headers.add(BITSIAN_PASSPHRASE, PASSPHRASE);


        this.stompSession.subscribe(headers, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info("Authentication Message : " + payload.toString() + " " + new Date());

                if (!payload.toString().contains(ERROR)) {

                    /*
                     *Subscribe individual exchange order book
                     */
                    StompSession.Subscription exchangeOrderBook =
                            exchangeOrderBook("coinbase", "btc", "usd");
//                                exchangeOrderBook.unsubscribe();


                    /*
                     *  Subscribe individual exchange trade tape
                     */
                    StompSession.Subscription exchangeTradeTape =
                            exchangeTradeTape("bitmart", "ltc", "usdt");
//                               exchangeTradeTape.unsubscribe();

                    /*
                     *  Subscribe aggregated order book
                     */
                    StompSession.Subscription aggregatedOrderBook =
                            aggregatedOrderBook("btc", "usd");
//                             aggregatedOrderBook.unsubscribe();


                    /*
                     * Subscribe aggregated trade tape
                     */
                    StompSession.Subscription aggregatedTradeTape =
                            aggregatedTradeTape("ltc", "usdt");
//                                 aggregatedTradeTape.unsubscribe();

                }

            }
        });

    }

    private StompSession.Subscription exchangeOrderBook(String exchange, String baseCurrency, String quoteCurrency) {

        return this.stompSession.subscribe(String.format("%s%s/%s/%s/%s",
                WEBSOCKET_DESTINATION_PRIFEX, ORDER_BOOK, exchange, baseCurrency, quoteCurrency)
                , new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, Object payload) {

                        if (new JSONObject(payload.toString()).has(SNAP_SHOT)) {

                            log.info("exchangeOrderBook Snapshot Message : " + payload.toString());

                        } else {

                            log.info("exchangeOrderBook l2updates Message : " + payload.toString());
                        }
                    }
                });
    }

    private StompSession.Subscription exchangeTradeTape(String exchange, String baseCurrency, String quoteCurrency) {

        return this.stompSession.subscribe(String.format("%s%s/%s/%s/%s",
                WEBSOCKET_DESTINATION_PRIFEX, TRADE_TAPE, exchange, baseCurrency, quoteCurrency)
                , new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, Object object) {

                        Object payload = new JSONTokener(object.toString()).nextValue();

                        if (payload instanceof JSONArray) {

                            log.info("aggregatedTradeTape Snapshot Message : " + payload.toString());

                        } else {

                            log.info("aggregatedTradeTape trade Message : " + payload.toString());
                        }
                    }
                });
    }

    private StompSession.Subscription aggregatedOrderBook(String baseCurrency, String quoteCurrency) {

        return this.stompSession.subscribe(String.format("%s%s/%s/%s",
                WEBSOCKET_DESTINATION_PRIFEX, ORDER_BOOK, baseCurrency, quoteCurrency)
                , new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, Object payload) {

                        if (new JSONObject(payload.toString()).has(SNAP_SHOT)) {

                            log.info("aggregatedOrderBook Snapshot Message : " + payload.toString());

                        } else {

                            log.info("aggregatedOrderBook l2updates Message : " + payload.toString());
                        }
                    }
                });
    }

    private StompSession.Subscription aggregatedTradeTape(String baseCurrency, String quoteCurrency) {

        return this.stompSession.subscribe(String.format("%s%s/%s/%s",
                WEBSOCKET_DESTINATION_PRIFEX, TRADE_TAPE, baseCurrency, quoteCurrency)
                , new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, Object object) {

                        Object payload = new JSONTokener(object.toString()).nextValue();

                        if (payload instanceof JSONArray) {

                            log.info("aggregatedTradeTape Snapshot Message : " + payload.toString());

                        } else {

                            log.info("aggregatedTradeTape trade Message : " + payload.toString());
                        }
                    }
                });
    }
}

