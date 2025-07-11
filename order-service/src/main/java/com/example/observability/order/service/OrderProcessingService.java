package com.example.observability.order.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.observability.order.model.OrderDAO;
import com.example.observability.order.model.OrderStatus;
import com.example.observability.order.model.dto.Order;
import com.example.observability.tracewise.context.TraceWiseContextAware;
import com.example.observability.tracewise.context.TraceWiseContextAware.TraceWiseContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProcessingService {

	@Autowired
	OrderPaymentService orderPaymentService;

	public void processOrder(Order order) {
		validateOrder(order);
//        persistOrder(order);
		persistOrderNewThread(order);
		orderPaymentService.processOrderPayments(order);
	}

	private void persistOrderNewThread(Order order) {
		TraceWiseContext traceContext = TraceWiseContextAware.setTraceContext();
		(new Thread(() -> {
			traceContext.set();
			log.info("New Thread");
			persistOrder(order);
			traceContext.clear();
		})).start();
	}

	private void persistOrder(Order order) {
		OrderDAO orderDAO = new OrderDAO(order.id(), order.customerId(), order.items(), OrderStatus.PLACED,
				LocalDateTime.now(), LocalDateTime.now());
		log.info("Persisting order details for order : order id {}", order.id());
	}

	private void validateOrder(Order order) {
		log.info("validating order details for order {}", order.id());
	}
}
