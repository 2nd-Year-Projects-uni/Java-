package com.project.tailorshop.controllers;

import com.project.tailorshop.dto.ApiResponse;
import com.project.tailorshop.entities.Contact;
import com.project.tailorshop.repositories.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Contact>> submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String service,
            @RequestParam String message) {

        Contact contact = new Contact(name, email, phone, service, message);
        Contact savedContact = contactRepository.save(contact);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Contact submitted successfully", savedContact));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Contact>>> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Contacts retrieved successfully", contacts));
    }

}
