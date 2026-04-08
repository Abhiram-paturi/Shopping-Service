package com.example.orderservice.service;

import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;

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

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineiItems> orderLineiItems = orderRequest.getOrderLineItemsDto()
                    .stream()
                    .map(orderLineitemsDto -> mapToDto(orderLineitemsDto))
                    .toList();

        order.setOrderLineItems(orderLineiItems );

        orderRepository.save(order);
    }

    private OrderLineiItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineiItems orderLineiItems = new OrderLineiItems();
        orderLineiItems.setPrice(orderLineItemsDto.getPrice());
        orderLineiItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineiItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineiItems;

    }
}
