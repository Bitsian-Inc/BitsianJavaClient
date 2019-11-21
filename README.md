# bitsian - java client

 > A full-featured Bitsian API client for java

- [x] Supports all documented v1.1 endpoints

## Getting started

Clients for both the [REST API](https://docs.bitsian.io/#rest) and
[streaming WebSocket API](https://docs.bitsian.io/#websocket) are included.
Private endpoints as indicated in the API docs require authentication with an API
key and secret key.

Add API key, secret key and passPhrase in the environment variables.

You can learn about the API responses of each endpoint [by reading our
documentation](http://docs.bitsian.io/).

## Methods for Exchange Data
For Exchange data methods, No permission needed for API key.

To create ExchangeDataService instance
```java
@Autowired
ExchangeDataService exchangeDataService;
```
* Get all the exchanges in bitsian
```java
exchangeDataService.getExchanges();
```
* Get all the bitsian supported currencies
```java
exchangeDataService.getCurrencies();
```
* Get the exchange products list by exchange
```java
exchangeDataService.getProducts(2);
```

## Methods for Account
API key need 'balance' permission.

To create BalanceService instance
```java
@AutoWired
BalanceService balanceService;
```
* Get the balance info (filtered by exchange as well as currency)
```javascript
balanceService.getBalance(2,1);
```

## Methods for Order
API key need 'trade' permission for order methods.
To create OrderService instance
```java
@AutoWired
OrderService orderService;
```
* Get all the orders (open / completed)
```java
orderService.getAllOrders('open');
```
* Get an order info
```java
orderService.getOrder('c5a28f8f-6860-4b12-a005-930f3781e195');
```
* Create an order
```java
 createOrderDto order = {

    orderSide:"buy",
    currencyPair:"LTC-USD",
    quantity:0.1,
    price:62.89,
    orderType:"market",
    exchangeId:4

};
orderService.createOrder(order);
```
* Cancel a particular order
```java
orderService.cancelOrder('c5a28f8f-6860-4b12-a005-930f3781e195');
```

## Websocket Client

Once authenticated successful with API keys, subscription of pairs can initiated for getting real time updates.

To create a Websocket StompSession instance
```java
private StompSession stompSession;
```

For subscribing both orderbook and tradetape data,
```java

stompSession.subscribe('kraken', 'btc', 'usd');

```
