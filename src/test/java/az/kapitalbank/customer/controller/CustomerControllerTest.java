package az.kapitalbank.customer.controller;

import static az.kapitalbank.customer.common.TestConstants.BASE_PATH;
import static az.kapitalbank.customer.common.TestConstants.INVALID_CUSTOMER_REQUEST;
import static az.kapitalbank.customer.common.TestConstants.VALID_CUSTOMER_REQUEST;
import static az.kapitalbank.customer.common.TestConstants.VALID_CUSTOMER_RESPONSE;
import static az.kapitalbank.customer.common.TestUtil.json;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void createCustomer_ValidRequest_ReturnsCreated() throws Exception {
        given(customerService.createCustomer(any(CustomerCreateRequestDto.class)))
                .willReturn(VALID_CUSTOMER_RESPONSE);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(VALID_CUSTOMER_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_PATH + "/" + VALID_CUSTOMER_RESPONSE.getCustomerId()))
                .andExpect(jsonPath("$.customerId").value(VALID_CUSTOMER_RESPONSE.getCustomerId()))
                .andExpect(jsonPath("$.name").value(VALID_CUSTOMER_RESPONSE.getName()))
                .andExpect(jsonPath("$.surname").value(VALID_CUSTOMER_RESPONSE.getSurname()))
                .andExpect(jsonPath("$.phoneNumber").value(VALID_CUSTOMER_RESPONSE.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate").value(VALID_CUSTOMER_RESPONSE.getBirthDate().toString()));

        then(customerService).should().createCustomer(VALID_CUSTOMER_REQUEST);
    }

    @Test
    void createCustomer_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(INVALID_CUSTOMER_REQUEST)))
                .andExpect(status().isBadRequest());
    }
} 