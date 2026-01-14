package com.project.tailorshop.repositories;

import com.project.tailorshop.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
