package com.example.observability.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.observability.order.model.dto.Order;
import com.example.observability.order.service.OrderService;
import com.example.observability.tracewise.context.TraceWiseContextAware;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/order")
@Slf4j
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/place-order")
	public ResponseEntity<String> placeOrder(@RequestBody Order order) {
		log.info("Received Order Input :: {}", order);
		submitOrder(order);
		TraceWiseContextAware.supplyAsync(() -> submitOrder(order));
		return ResponseEntity.ok("Order has been placed");
	}

	private Object submitOrder(Order order) {
		orderService.placeOrder(order);
		return null;
	}

	@PostMapping("/update-order-status")
	public ResponseEntity<String> updateOrderStatus(@RequestBody Order order) {
		orderService.updateOrderStatus(order);
		return ResponseEntity.ok("Order has been updated");
	}
}
