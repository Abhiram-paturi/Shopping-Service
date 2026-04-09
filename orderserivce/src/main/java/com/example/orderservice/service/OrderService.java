package com.example.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.DTO.InventoryResponse;
import com.example.orderservice.DTO.OrderLineItemsDto;
import com.example.orderservice.DTO.OrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineiItems;
import com.example.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineiItems> orderLineiItems = orderRequest.getOrderLineItemsDto()
                    .stream()
                    .map(orderLineitemsDto -> mapToDto(orderLineitemsDto))
                    .toList();

        order.setOrderLineItems(orderLineiItems );

      List<String> skuCodes= order.getOrderLineItems().stream()
                                 .map(orderLineiItem -> orderLineiItem.getSkuCode())
                                 .toList();

        //call Inventory Service , and place order if product 
        //is in stock
      InventoryResponse[] inventoryResponseArray = webClient.get()
                                  .uri("http://localhost:8082/api/inventory",
                                   uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                  .retrieve()
                                  .bodyToMono(InventoryResponse[].class)
                                  .block();
       boolean allProductsInStock =  Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse -> InventoryResponse.getIsInStock());
  
        if(allProductsInStock){
        orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock, please try agian later ");
        }
    }

    private OrderLineiItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineiItems orderLineiItems = new OrderLineiItems();
        orderLineiItems.setPrice(orderLineItemsDto.getPrice());
        orderLineiItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineiItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineiItems;

    }
}
