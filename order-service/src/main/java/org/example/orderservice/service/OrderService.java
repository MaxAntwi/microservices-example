package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.InventoryResponse;
import org.example.orderservice.dto.OrderLineItemsRequest;
import org.example.orderservice.dto.OrderRequest;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderLineItems;
import org.example.orderservice.exceptions.OutOfStockException;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final WebClient.Builder webClientBuilder;

    private final OrderRepository orderRepository;


    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList().stream()
                .map(this::mapToOrderLineItems).toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes =order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        try {
            //Call inventory service, to check if product is in stock
            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/v1/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            Boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);
            if (Boolean.TRUE.equals(allProductsInStock)) {
                orderRepository.save(order);
                return "Order Placed Successfully";
            } else {
                throw new OutOfStockException("Product is not available", HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            log.error("Failed to place order: {}", e.getMessage());
            throw e;
        }
    }

    public OrderLineItems mapToOrderLineItems(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        return orderLineItems;
    }
}