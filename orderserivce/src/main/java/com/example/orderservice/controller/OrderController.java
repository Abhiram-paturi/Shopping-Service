package com.example.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.DTO.OrderRequest;
import com.example.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public String placeOrder(@RequestBody OrderRequest orderRequest){
      orderService.placeOrder(orderRequest);
      return "Order placed Successfully";
}

}
