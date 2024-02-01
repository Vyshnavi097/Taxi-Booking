package com.edstem.service;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.model.Booking;
import com.edstem.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

  public BookingResponse bookingg(BookingRequest bookingRequest){
      Booking booking=Booking.builder()
              .pickupLocation(bookingRequest.getPickupLocation())
              .dropoffLocation(bookingRequest.getDropoffLocation())
              .status(Status.BOOKED)
              .build();
      booking=bookingRepository.save(booking);
      return modelMapper.map(booking,BookingResponse.class);

  }
   public BookingResponse bookingById(long id){
     Booking booking=bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
     return modelMapper.map(booking,BookingResponse.class);
  }
}
