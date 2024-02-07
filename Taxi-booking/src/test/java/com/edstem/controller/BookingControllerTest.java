package com.edstem.controller;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.request.SignupRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.contract.response.SignupResponse;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.BookingService;
import com.edstem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingService bookingService;

    @Test
    void testBooking() {
        BookingRequest bookingRequest = new BookingRequest("payyanur", "annur", Status.BOOKED);
        BookingResponse bookingResponse =
                new BookingResponse(1L, 120D, Status.BOOKED);
        when(bookingService.bookingg(bookingRequest, 1L, 1L, 2D)).thenReturn(bookingResponse);
    }

//    @Test
//    void testViewBookingDetail() throws Exception {
//        Long bookingId = 1L;
//        BookingResponse expectedResponse =
//                new BookingResponse(
//                        bookingId,
//                        800.0,
//                        Status.BOOKED);
//
//        when(bookingService.bookingById(bookingId)).thenReturn(expectedResponse);
//
//        mockMvc.perform(get("/booking/{bookingId}", bookingId).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
//    }
@Test
void testCancelBooking() throws Exception {
    Long bookingId = 1L;
    Long userId = 1L;
    CancelBookingResponse expectedResponse =
            CancelBookingResponse.builder().build();

    when(bookingService.cancelById(anyLong(), anyLong())).thenReturn(expectedResponse);

    mockMvc.perform(
                    delete("/cancel/")
                            .param("userId", String.valueOf(userId))
                            .param("bookingId",String.valueOf(bookingId))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
}
}
