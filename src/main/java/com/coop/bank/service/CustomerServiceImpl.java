package com.coop.bank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coop.bank.model.Account;
import com.coop.bank.model.Customer;
import com.coop.bank.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		Optional<Customer> existingCustomer = customerRepo.findById(id);
		if (existingCustomer.isPresent()) {
			Customer updatedCustomer = existingCustomer.get();
			updatedCustomer.setName(customer.getName());
			updatedCustomer.setEmail(customer.getEmail());
			return customerRepo.save(updatedCustomer);
		} else {
			throw new RuntimeException("Customer not found with id " + id);
		}
	}

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepo.deleteById(id);
	}

	@Override
	public Customer addAccountToCustomer(Long customerId, Account account) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
		account.setCustomer(customer);
		customer.getAccounts().add(account);
		return customerRepo.save(customer);
	}

	@Override
	public Account updateAccount(Long customerId, Long accountId, Account account) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
		Account existingAccount = customer.getAccounts().stream().filter(a -> a.getId().equals(accountId)).findFirst()
				.orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));
		existingAccount.setAccountNumber(account.getAccountNumber());
		existingAccount.setBalance(account.getBalance());
		customerRepo.save(customer);
		return existingAccount;
	}

	@Override
	public List<Account> getAccountsByCustomerId(Long customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
		return customer.getAccounts();
	}
}