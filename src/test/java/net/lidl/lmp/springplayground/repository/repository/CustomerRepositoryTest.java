package net.lidl.lmp.springplayground.repository.repository;

import net.lidl.lmp.springplayground.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository.deleteAll();
    }

    @Test
    public void createAndPersistCustomer() {
        Long id = null;
        String firstName = "John";
        String lastName = "Doe";

        Customer customer = Customer.builder().id(id).firstName(firstName).lastName(lastName).build();

        Customer persistedCustomer = customerRepository.save(customer);

        assertThat(persistedCustomer.getId()).isGreaterThan(0L);
    }

    @Test
    public void findAllCustomers() {
        customerRepository.saveAll(
                Arrays.asList(
                        Customer.builder().firstName("John").lastName("Doe").build(),
                        Customer.builder().firstName("Jane").lastName("Doe").build(),
                        Customer.builder().firstName("Peter").lastName("Pan").build()
                ));

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(3);
    }

    @Test
    public void findCustomerByFirstNameAndLastName() {
        customerRepository.saveAll(
                Arrays.asList(
                        Customer.builder().firstName("John").lastName("Doe").build(),
                        Customer.builder().firstName("Jane").lastName("Doe").build(),
                        Customer.builder().firstName("Peter").lastName("Pan").build()
                ));
        String firstName = "Jane";
        String lastName = "Doe";

        List<Customer> customers = customerRepository.findByFirstNameAndLastName(firstName, lastName);

        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0).getFirstName()).isEqualTo(firstName);
        assertThat(customers.get(0).getLastName()).isEqualTo(lastName);
    }

    @Test
    public void deleteCustomer() {
        customerRepository.saveAll(
                Arrays.asList(
                        Customer.builder().firstName("John").lastName("Doe").build(),
                        Customer.builder().firstName("Jane").lastName("Doe").build(),
                        Customer.builder().firstName("Peter").lastName("Pan").build()
                ));

        Customer customer = customerRepository.findByFirstNameAndLastName("Jane", "Doe").get(0);

        customerRepository.delete(customer);

        int nCustomers = customerRepository.findAll().size();

        assertThat(nCustomers).isEqualTo(2);
    }
}
