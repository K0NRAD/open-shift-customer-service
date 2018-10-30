package net.lidl.lmp.springplayground.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CustomerTest {

  @Test
  public void testCreateCustomer() {
    Long id = 1000L;
    String firstName = "John";
    String lastName = "Doe";

    Customer customer = new Customer(id, firstName, lastName);
    Assertions.assertThat(customer).isNotNull();
  }
}
