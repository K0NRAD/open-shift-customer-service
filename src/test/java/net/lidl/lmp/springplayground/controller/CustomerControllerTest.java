package net.lidl.lmp.springplayground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lidl.lmp.springplayground.model.Customer;
import net.lidl.lmp.springplayground.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {CustomerController.class})
public class CustomerControllerTest {

  @MockBean
  private CustomerService customerService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findAllCustomers() throws Exception {

    when(customerService.findAll()).thenReturn(
            Arrays.asList(
                    Customer.builder().id(1L).firstName("John").lastName("Doe").build(),
                    Customer.builder().id(2L).firstName("Jane").lastName("Doe").build(),
                    Customer.builder().id(3L).firstName("Peter").lastName("Pan").build()));

    mockMvc.perform(get("/customers"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(3));
  }

  @Test
  public void findCustomerByFirstNameAndLastName() throws Exception {
    Long id = 2L;
    String firstName = "Jane";
    String lastName = "Doe";

    when(customerService.findByFirstNameAndLastName(firstName, lastName)).thenReturn(
            Arrays.asList(
                    Customer.builder().id(id).firstName(firstName).lastName(lastName).build()));

    mockMvc.perform(get("/customers/Jane/Doe"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$.[0].firstName").value(firstName))
            .andExpect(jsonPath("$.[0].lastName").value(lastName));

  }

  @Test
  public void createCustomer() throws Exception {

    Long id = 2L;
    String firstName = "Jane";
    String lastName = "Doe";
    Customer customer = Customer.builder().id(null).firstName(firstName).lastName(lastName).build();

    when(customerService.createCustomer(customer)).thenReturn(
            Customer.builder().id(id).firstName(firstName).lastName(lastName).build());

    mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(customer))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.firstName").value(firstName))
            .andExpect(jsonPath("$.lastName").value(lastName));
  }

  @Test
  public void deleteCustomer() throws Exception {

    Long id = 2L;
    String firstName = "Jane";
    String lastName = "Doe";
    Customer customer = Customer.builder().id(null).firstName(firstName).lastName(lastName).build();

    doNothing().when(customerService).deleteCustomer(customer);

    mockMvc.perform(delete("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJsonString(customer)))
            .andExpect(status().isOk());

    verify(customerService,times(1)).deleteCustomer(customer);
  }

  private String toJsonString(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to map JSON to String");
    }

  }
}
