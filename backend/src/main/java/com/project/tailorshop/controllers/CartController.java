package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.AddToCartRequest;
import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.dto.UpdateCartItemRequest;
import com.project.tailorshop.entities.Cart;
import com.project.tailorshop.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Cart>> getCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCart(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart retrieved", cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(@RequestBody AddToCartRequest request) {
        try {
            Cart cart = cartService.addToCart(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item added to cart", cart));
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage() != null ? e.getMessage() : "An internal error occurred";
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, msg, null));
        }
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse<Cart>> removeFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            Cart cart = cartService.removeFromCart(userId, itemId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart", cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse<Cart>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest request) {
        try {
            Cart cart = cartService.updateCartItemQuantity(userId, itemId, request.getQuantity());
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart updated", cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
