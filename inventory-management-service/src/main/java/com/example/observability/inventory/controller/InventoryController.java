package com.example.observability.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/inventory")
@Slf4j
public class InventoryController {

	@GetMapping("/inventory-management")
	public ResponseEntity<String> inventoryMaangement() {
		log.info("Inventory Service");
		return ResponseEntity.ok("Inventory called successfully");
	}

}
