package com.eva.EvaSupportAgent.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eva.EvaSupportAgent.product.service.OrderHistoryService;
import com.eva.EvaSupportAgent.product.vo.OrderHistoryVO;
@RestController
@RequestMapping("/api/order-history")
public class OrderHistoryController {

    private final OrderHistoryService service;

    public OrderHistoryController(OrderHistoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderHistoryVO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        List<OrderHistoryVO> history = service.getByUserId(userId);
        return ResponseEntity.ok(history);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderHistoryVO vo) {
        try {
            OrderHistoryVO saved = service.save(vo);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to save order history.");
        }
    }
}


