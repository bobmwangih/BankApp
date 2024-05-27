package com.coop.bank;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.coop.bank.controller.CustomerController;
import com.coop.bank.model.Account;
import com.coop.bank.model.Customer;
import com.coop.bank.service.CustomerService;

public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

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
    void testCreateCustomer() throws Exception {
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(any(Long.class), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer));

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddAccountToCustomer() throws Exception {
        when(customerService.addAccountToCustomer(any(Long.class), any(Account.class))).thenReturn(customer);

        mockMvc.perform(post("/api/v1/customers/1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountNumber\":\"123456789\",\"balance\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts[0].accountNumber").value("123456789"))
                .andExpect(jsonPath("$.accounts[0].balance").value(1000.0));
    }

    @Test
    void testUpdateAccount() throws Exception {
        when(customerService.updateAccount(any(Long.class), any(Long.class), any(Account.class))).thenReturn(account);

        mockMvc.perform(put("/api/v1/customers/1/accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountNumber\":\"123456789\",\"balance\":2000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456789"))
                .andExpect(jsonPath("$.balance").value(2000.0));
    }

    @Test
    void testGetAccountsByCustomerId() throws Exception {
        when(customerService.getAccountsByCustomerId(1L)).thenReturn(Arrays.asList(account));

        mockMvc.perform(get("/api/v1/customers/1/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("123456789"))
                .andExpect(jsonPath("$[0].balance").value(1000.0));
    }
}