package net.lidl.lmp.springplayground.service;

import net.lidl.lmp.springplayground.model.Customer;
import net.lidl.lmp.springplayground.repository.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

  @Autowired
  private CustomerService customerService;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  public void findAllCustomers() {
    when(customerRepository.findAll()).thenReturn(
            Arrays.asList(
                    Customer.builder().id(1L).firstName("John").lastName("Doe").build(),
                    Customer.builder().id(2L).firstName("Jane").lastName("Doe").build(),
                    Customer.builder().id(3L).firstName("Peter").lastName("Pan").build()));

    List<Customer> customers = customerService.findAll();

    assertThat(customers.size()).isGreaterThan(0);
  }

  @Test
  public void findCustomerByFirstNameAndLastName() {
    Long id = 2L;
    String firsName = "Jane";
    String lastName = "Doe";

    when(customerRepository.findByFirstNameAndLastName(firsName, lastName)).thenReturn(
            Arrays.asList(
                    Customer.builder().id(id).firstName(firsName).lastName(lastName).build()));

    List<Customer> customers = customerService.findByFirstNameAndLastName(firsName, lastName);

    assertThat(customers.size()).isEqualTo(1);
    assertThat(customers.get(0).getFirstName()).isEqualTo(firsName);
    assertThat(customers.get(0).getLastName()).isEqualTo(lastName);
  }


  @Test
  public void createCustomer() {
    Long id = null;
    String firsName = "Jane";
    String lastName = "Doe";

    Customer customer = Customer.builder().id(id).firstName(firsName).lastName(lastName).build();

    when(customerRepository.save(customer)).thenReturn(
            Customer.builder().id(1L).firstName(firsName).lastName(lastName).build());

    Customer newCustomer = customerService.createCustomer(customer);

    assertThat(newCustomer.getId()).isGreaterThan(0L);

  }

  @Test
  public void deleteCustomer() {
    Long id = 1L;
    String firsName = "Jane";
    String lastName = "Doe";

    Customer customer = Customer.builder().id(id).firstName(firsName).lastName(lastName).build();

    doNothing().when(customerRepository).delete(customer);

    customerService.deleteCustomer(customer);

    verify(customerRepository, times(1)).delete(customer);
  }
}
