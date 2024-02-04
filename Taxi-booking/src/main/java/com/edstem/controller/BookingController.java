package com.edstem.controller;

import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.contract.response.TaxiResponse;
import com.edstem.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor


public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking")

  public BookingResponse booking(@RequestBody BookingRequest bookingRequest, @RequestParam long userId,@RequestParam long taxiId,@RequestParam Double distance){
      return bookingService.bookingg(bookingRequest,userId,taxiId,distance);
  }
  @GetMapping("/booking/{id}")
  public BookingResponse bookingById(@PathVariable long id){
        return bookingService.bookingById(id);
  }

  @DeleteMapping("/cancel")
    public CancelBookingResponse cancelById(@RequestParam long bookingId,@RequestParam long userId){
        return bookingService.cancelById(bookingId,userId);
  }

    @GetMapping("/nearest")
    public List<TaxiResponse> findNearest(
            @RequestParam Long userId, @RequestParam String pickupLocation) {
        return bookingService.findNearest(userId, pickupLocation);
    }
}
