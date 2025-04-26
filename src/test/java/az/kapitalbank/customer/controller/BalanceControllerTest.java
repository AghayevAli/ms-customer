package az.kapitalbank.customer.controller;

import static az.kapitalbank.customer.common.TestConstants.BASE_PATH;
import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ID;
import static az.kapitalbank.customer.common.TestConstants.VALID_BALANCE_CHANGE_REQUEST;
import static az.kapitalbank.customer.common.TestUtil.json;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import az.kapitalbank.customer.service.InternalBalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InternalBalanceController.class)
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternalBalanceService balanceService;

    @Test
    void increaseBalance_ValidRequest_ReturnsNoContent() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/" + CUSTOMER_ID + "/balance/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(VALID_BALANCE_CHANGE_REQUEST)))
                .andExpect(status().isNoContent());

        then(balanceService).should().increaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST);
    }

    @Test
    void decreaseBalance_ValidRequest_ReturnsNoContent() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/" + CUSTOMER_ID + "/balance/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(VALID_BALANCE_CHANGE_REQUEST)))
                .andExpect(status().isNoContent());

        then(balanceService).should().decreaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST);
    }
}