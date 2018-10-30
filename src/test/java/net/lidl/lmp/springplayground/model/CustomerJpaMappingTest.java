package net.lidl.lmp.springplayground.model;

import net.lidl.lmp.springplayground.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerJpaMappingTest {

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void testPersistCustomer() {
    Customer customer = Customer.builder()
            .firstName("John")
            .lastName("Doe")
            .build();

    Customer persistedCustomer = entityManager.persistAndFlush(customer);

    assertThat(persistedCustomer.getId()).isGreaterThan(0L);
  }
}
