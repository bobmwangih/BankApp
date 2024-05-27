package com.coop.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coop.bank.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
