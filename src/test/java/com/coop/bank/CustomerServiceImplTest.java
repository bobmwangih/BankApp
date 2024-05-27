package com.coop.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coop.bank.model.Account;
import com.coop.bank.model.Customer;
import com.coop.bank.repository.CustomerRepo;
import com.coop.bank.service.CustomerServiceImpl;

public class CustomerServiceImplTest {

	@Mock
	private CustomerRepo customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private Customer customer;
	private Account account;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		customer = new Customer();
		customer.setId(1L);
		customer.setName("John Doe");
		customer.setEmail("john.doe@example.com");

		account = new Account();
		account.setId(1L);
		account.setAccountNumber("123456789");
		account.setBalance(1000.0);
		account.setCustomer(customer);

		customer.setAccounts(Arrays.asList(account));
	}

	@Test
	void testSaveCustomer() {
		when(customerRepository.save(customer)).thenReturn(customer);
		Customer savedCustomer = customerService.saveCustomer(customer);
		assertNotNull(savedCustomer);
		assertEquals("John Doe", savedCustomer.getName());
	}

	@Test
	void testUpdateCustomer() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		Customer updatedCustomer = customerService.updateCustomer(1L, customer);
		assertNotNull(updatedCustomer);
		assertEquals("John Doe", updatedCustomer.getName());
	}

	@Test
	void testGetCustomerById() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Customer foundCustomer = customerService.getCustomerById(1L);
		assertNotNull(foundCustomer);
		assertEquals("John Doe", foundCustomer.getName());
	}

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = Arrays.asList(customer);
		when(customerRepository.findAll()).thenReturn(customers);
		List<Customer> foundCustomers = customerService.getAllCustomers();
		assertNotNull(foundCustomers);
		assertEquals(1, foundCustomers.size());
	}

	@Test
	void testDeleteCustomer() {
		doNothing().when(customerRepository).deleteById(1L);
		customerService.deleteCustomer(1L);
		verify(customerRepository, times(1)).deleteById(1L);
	}

	@Test
	void testAddAccountToCustomer() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		Customer updatedCustomer = customerService.addAccountToCustomer(1L, account);
		assertNotNull(updatedCustomer);
		assertEquals(1, updatedCustomer.getAccounts().size());
	}

	@Test
	void testUpdateAccount() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		Account updatedAccount = customerService.updateAccount(1L, 1L, account);
		assertNotNull(updatedAccount);
		assertEquals("123456789", updatedAccount.getAccountNumber());
	}

	@Test
	void testGetAccountsByCustomerId() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		List<Account> accounts = customerService.getAccountsByCustomerId(1L);
		assertNotNull(accounts);
		assertEquals(1, accounts.size());
	}
}
