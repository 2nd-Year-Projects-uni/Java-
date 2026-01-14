package com.project.tailorshop.services;

import com.project.tailorshop.dto.AddToCartRequest;
import com.project.tailorshop.entities.*;
import com.project.tailorshop.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository,
            ProductRepository productRepository, CustomProductRepository customProductRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.customProductRepository = customProductRepository;
    }

    @Transactional
    public Cart addToCart(AddToCartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElse(new Cart(user));
        // If new, saving later will cascade, but safer to ensure it's managed if we
        // access lazy collections

        String name;
        double price;
        String imageUrl;

        // Normalize type
        String type = request.getProductType() == null ? "standard" : request.getProductType().toLowerCase();
        boolean isCustom = "custom".equals(type);

        if (isCustom) {
            CustomProduct cp = customProductRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Custom Product not found"));
            name = cp.getName();
            price = cp.getPrice();
            imageUrl = cp.getImageUrl();
        } else {
            Product p = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            name = p.getName();
            price = p.getPrice();
            imageUrl = p.getImageUrl();
        }

        // Check if item exists
        // For standard items, we merge quantities.
        // For custom items, we only merge if measurements match exactly
        // (simplification: assume distinct for now if measurements present)

        Optional<CartItem> existingItem = Optional.empty();

        if (!isCustom) {
            existingItem = cart.getItems().stream()
                    .filter(item -> item.getProductId() != null
                            && item.getProductId().equals(request.getProductId())
                            && item.getProductType() != null
                            && item.getProductType().equalsIgnoreCase(type))
                    .findFirst();
        }

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem(
                    request.getProductId(),
                    type,
                    name,
                    price,
                    imageUrl,
                    request.getQuantity(),
                    request.getMeasurements());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public Cart removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItemQuantity(Long userId, Long cartItemId, int newQuantity) {
        Cart cart = getCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (newQuantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(newQuantity);
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
