package com.edstem.controller;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.BookingService;
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
    private BookingService bookingService;

    @Test
    void testBookTaxi() throws Exception {
        BookingRequest bookingRequest = new BookingRequest("Aluva", "Kakkanad", Status.BOOKED);
        Long id=1L;
        Double distance = 80.0;
        Long taxiId=1L;
        Long userId=1L;
        BookingResponse expectedResponse =
                new BookingResponse(
                        1L,
                        800.0,
                        Status.BOOKED);

        when(bookingService.bookingg(any(BookingRequest.class), anyLong(), anyLong(),anyDouble()))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/booking")
                                .param("userId", String.valueOf(userId))
                                .param("taxiId", String.valueOf(taxiId))
                                .param("distance", String.valueOf(distance))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testViewBookingById() throws Exception {
        Long id = 1L;
        BookingResponse bookingResponse = new BookingResponse();

        when(bookingService.bookingById(id)).thenReturn(bookingResponse);

        mockMvc.perform(get("/booking/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookingResponse)));
    }

    @Test
    public void testCancelById() throws Exception {
        long bookingId = 1L;
        long userId = 1L;
        CancelBookingResponse response = new CancelBookingResponse();

        when(bookingService.cancelById(bookingId, userId)).thenReturn(response);

        mockMvc.perform(delete("/cancel")
                        .param("bookingId", String.valueOf(bookingId))
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));

        verify(bookingService, times(1)).cancelById(bookingId, userId);
    }
    @Test
    void testFindNearest() throws Exception {
        Long userId = 1L;
        String pickupLocation = "payyanur";
        List<TaxiResponse> taxiResponses = new ArrayList<>();

        when(bookingService.findNearest(userId, pickupLocation)).thenReturn(taxiResponses);

        mockMvc.perform(
                        get("/nearest")
                                .param("userId", String.valueOf(userId))
                                .param("pickupLocation", pickupLocation))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(taxiResponses)));
    }
}
