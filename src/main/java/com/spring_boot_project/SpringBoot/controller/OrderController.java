package com.spring_boot_project.SpringBoot.controller;

import com.spring_boot_project.SpringBoot.dto.OrderRequestDTO;
import com.spring_boot_project.SpringBoot.model.Item;
import com.spring_boot_project.SpringBoot.model.Order;
import com.spring_boot_project.SpringBoot.model.OrderItem;
import com.spring_boot_project.SpringBoot.model.OrderItemId;
import com.spring_boot_project.SpringBoot.repository.ItemRepository;
import com.spring_boot_project.SpringBoot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Create new order
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderDTO) {
        try {
            Order order = new Order();
            order.setDate(LocalDateTime.parse(orderDTO.getDate(), DateTimeFormatter.ISO_DATE_TIME));
            order.setCustomerId(orderDTO.getCustomerId());
            order.setTotalPrice(BigDecimal.valueOf(orderDTO.getTotalPrice()));

            Set<OrderItem> orderItems = new HashSet<>();

            for (OrderRequestDTO.OrderItemDTO itemDTO : orderDTO.getItems()) {
                Optional<Item> optionalItem = itemRepository.findById(itemDTO.getItemId());
                if (optionalItem.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("Item ID " + itemDTO.getItemId() + " not found");
                }

                Item item = optionalItem.get();

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setItem(item);
                orderItem.setQuantity(itemDTO.getQuantity());

                OrderItemId orderItemId = new OrderItemId();
                orderItemId.setOrderId(order.getId());
                orderItemId.setItemId(item.getId());
                orderItem.setId(orderItemId);

                orderItems.add(orderItem);
            }

            order.setOrderItems(orderItems);
            orderRepository.save(order);

            // Update OrderItemIds after save
            for (OrderItem oi : order.getOrderItems()) {
                oi.getId().setOrderId(order.getId());
            }
            orderRepository.save(order);

            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating order");
        }
    }

    // Get all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return ResponseEntity.status(404).body("Order ID " + id + " not found");
        }
        return ResponseEntity.ok(order.get());
    }

    // Edit an order
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editOrder(@PathVariable int id, @RequestBody OrderRequestDTO orderDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(404).body("Order ID " + id + " not found");
        }

        try {
            Order order = optionalOrder.get();
            order.setCustomerId(orderDTO.getCustomerId());
            order.setDate(LocalDateTime.parse(orderDTO.getDate(), DateTimeFormatter.ISO_DATE_TIME));
            order.setTotalPrice(BigDecimal.valueOf(orderDTO.getTotalPrice()));

            // Clear previous items and set new ones
            order.getOrderItems().clear();
            Set<OrderItem> newItems = new HashSet<>();

            for (OrderRequestDTO.OrderItemDTO itemDTO : orderDTO.getItems()) {
                Optional<Item> optionalItem = itemRepository.findById(itemDTO.getItemId());
                if (optionalItem.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("Item ID " + itemDTO.getItemId() + " not found");
                }

                Item item = optionalItem.get();
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setItem(item);
                orderItem.setQuantity(itemDTO.getQuantity());

                OrderItemId orderItemId = new OrderItemId();
                orderItemId.setOrderId(order.getId());
                orderItemId.setItemId(item.getId());
                orderItem.setId(orderItemId);

                newItems.add(orderItem);
            }

            order.setOrderItems(newItems);
            orderRepository.save(order);

            return ResponseEntity.ok("Order updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating order");
        }
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Order ID " + id + " not found");
        }
        orderRepository.deleteById(id);
        return ResponseEntity.ok("Order ID " + id + " deleted successfully");
    }
}
