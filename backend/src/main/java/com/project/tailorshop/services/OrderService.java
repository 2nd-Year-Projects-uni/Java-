package com.project.tailorshop.services;

import com.project.tailorshop.entities.*;
import com.project.tailorshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Calculate total
        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Add delivery fee logic if needed (matching frontend logic)
        // Frontend: Delivery is Free if > 20000, else 250.
        // Tax is 2%.

        double tax = totalAmount * 0.02;
        double deliveryFee = (totalAmount + tax) > 20000 ? 0 : 250;
        double grandTotal = totalAmount + tax + deliveryFee;

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Confirmed"); // Initial status
        order.setTotalAmount(grandTotal);

        // Convert CartItems to OrderItems
        for (CartItem cartItem : cart.getItems()) {
            // Handle Stock Reduction for Standard Products
            if (cartItem.getProductId() != null && !"custom".equalsIgnoreCase(cartItem.getProductType())) {
                Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
                if (product != null) {
                    if (product.getStock() < cartItem.getQuantity()) {
                        throw new RuntimeException("Insufficient stock for product: " + product.getName());
                    }
                    product.setStock(product.getStock() - cartItem.getQuantity());
                    productRepository.save(product);
                }
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductType(cartItem.getProductType());
            orderItem.setName(cartItem.getName());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setMeasurements(cartItem.getMeasurements());

            order.addItem(orderItem);
        }

        // Save Order
        Order savedOrder = orderRepository.save(order);

        // Clear Cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
