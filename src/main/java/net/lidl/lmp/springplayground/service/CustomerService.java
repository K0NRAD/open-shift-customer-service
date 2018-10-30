package net.lidl.lmp.springplayground.service;

import net.lidl.lmp.springplayground.model.Customer;
import net.lidl.lmp.springplayground.repository.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public List<Customer> findByFirstNameAndLastName(String firsName, String lastName) {
    return customerRepository.findByFirstNameAndLastName(firsName, lastName);
  }

  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  public void deleteCustomer(Customer customer) {
    customerRepository.delete(customer);
  }
}
