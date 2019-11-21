package com.bitsian.BitsianJavaClient.order;

import com.bitsian.BitsianJavaClient.connector.GetResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.*;

@Service
public class OrderService {
    private GetResponse getResponse;
    private Gson gson = new Gson();

    @Autowired
    public OrderService(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    /**
     * get all order details
     *
     * @param
     * @return all orders
     * @throws
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public OrderResponse getAllOrders(String resolution) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        ResponseEntity<String> orderResponse = this.getResponse.getResponse(REST_END_POINT,
                ORDER_DETAILS + ((resolution == null) ? "" : "?resolution=" + resolution),
                HttpMethod.GET, null);
        JsonObject data = this.gson.fromJson(orderResponse.getBody(), JsonObject.class);
        return this.gson.fromJson(data, OrderResponse.class);

    }

    /**
     * create order
     *
     * @param dto
     * @return created order details
     * @throws
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public Order createOrders(CreateOrderDto dto) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        ResponseEntity<String> orderResponse = this.getResponse.getResponse(
                REST_END_POINT,  ORDER_DETAILS, HttpMethod.POST, new Gson().toJson(dto));
        JsonObject data = this.gson.fromJson(orderResponse.getBody(), JsonObject.class);
        Order order = this.gson.fromJson(data.getAsJsonObject(RESPONSE_DATA), Order.class);
        return order;
    }

    /**
     * cancel order
     *
     * @param orderId
     * @return cancelled order details
     * @throws
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public Order cancelOrder(String orderId) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        ResponseEntity<String> orderResponse = this.getResponse.getResponse(
                REST_END_POINT,  ORDER_DETAILS + "/" + orderId + ORDER_CANCEL, HttpMethod.POST, null);
        JsonObject data = this.gson.fromJson(orderResponse.getBody(), JsonObject.class);
        Order orders = this.gson.fromJson(data.getAsJsonObject(RESPONSE_DATA), Order.class);
        return orders;
    }

    /**
     * get order status
     *
     * @param orderId
     * @return order details
     * @throws
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public Order getOrder(String orderId) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        ResponseEntity<String> orderResponse = this.getResponse.getResponse(
                REST_END_POINT,  ORDER_DETAILS + "/" + orderId, HttpMethod.GET, null);
        JsonObject data = this.gson.fromJson(orderResponse.getBody(), JsonObject.class);
        Order order = this.gson.fromJson(data.getAsJsonObject(RESPONSE_DATA), Order.class);
        return order;
    }
}
