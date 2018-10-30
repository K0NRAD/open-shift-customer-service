package net.lidl.lmp.springplayground.controller;

import net.lidl.lmp.springplayground.model.Customer;
import net.lidl.lmp.springplayground.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public List<Customer> findAll() {
    return customerService.findAll();
  }

  @GetMapping(path = {"/{firstName}/{lastName}"})
  public List<Customer> findByFirstNameAndLastName(
          @PathVariable("firstName") String firstName,
          @PathVariable("lastName") String lastName) {
    return customerService.findByFirstNameAndLastName(firstName, lastName);
  }

  @PostMapping
  public Customer createCustomer(@RequestBody Customer customer) {
    return customerService.createCustomer(customer);
  }

  @DeleteMapping
  public void deleteCustomer(@RequestBody Customer customer) {
    customerService.deleteCustomer(customer);
  }
}
