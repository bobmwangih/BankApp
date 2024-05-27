package com.coop.bank.service;

import java.util.List;

import com.coop.bank.model.Account;
import com.coop.bank.model.Customer;

public interface CustomerService {
	Customer saveCustomer(Customer customer);

	Customer updateCustomer(Long id, Customer customer);

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	void deleteCustomer(Long id);

	Customer addAccountToCustomer(Long customerId, Account account);

	Account updateAccount(Long customerId, Long accountId, Account account);

	List<Account> getAccountsByCustomerId(Long customerId);
}
