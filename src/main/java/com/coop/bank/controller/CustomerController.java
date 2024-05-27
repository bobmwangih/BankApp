package com.coop.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coop.bank.model.Account;
import com.coop.bank.model.Customer;
import com.coop.bank.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Validated @RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		return ResponseEntity.ok(savedCustomer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer) {
		Customer updatedCustomer = customerService.updateCustomer(id, customer);
		return ResponseEntity.ok(updatedCustomer);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
	@PostMapping("/{customerId}/accounts")
	public ResponseEntity<Customer> addAccountToCustomer(@PathVariable Long customerId,
			@Validated @RequestBody Account account) {
		Customer updatedCustomer = customerService.addAccountToCustomer(customerId, account);
		return ResponseEntity.ok(updatedCustomer);
	}

	@PutMapping("/{customerId}/accounts/{accountId}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long customerId, @PathVariable Long accountId,
			@Validated @RequestBody Account account) {
		Account updatedAccount = customerService.updateAccount(customerId, accountId, account);
		return ResponseEntity.ok(updatedAccount);
	}

	@GetMapping("/{customerId}/accounts")
	public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
		List<Account> accounts = customerService.getAccountsByCustomerId(customerId);
		return ResponseEntity.ok(accounts);
	}
}
