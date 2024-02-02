package com.edstem.service;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.request.CancelBookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.model.Booking;
import com.edstem.model.Taxi;
import com.edstem.model.Users;
import com.edstem.repository.BookingRepository;
import com.edstem.repository.TaxiRepository;
import com.edstem.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final UsersRepository usersRepository;
    private final TaxiRepository taxiRepository;


  public BookingResponse bookingg(BookingRequest bookingRequest,long userId,long taxiId,double distance){
      Users user= usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
      Taxi taxi=taxiRepository.findById(taxiId).orElseThrow(()-> new EntityNotFoundException());
      Double  RATE_PER_KM=10.00;
      double fare=distance*RATE_PER_KM;
      if(fare>user.getAccountBalance()){
          throw new RuntimeException("insufficient balance");
      }
            Booking booking=Booking.builder()
                      .user(user)
                      .taxi(taxi)
                      .pickupLocation(bookingRequest.getPickupLocation())
                      .dropoffLocation(bookingRequest.getDropoffLocation())
                      .bookingTime(LocalDateTime.parse(LocalDateTime.now().toString()))
                      .status(Status.BOOKED)
                      .fare(fare)
              .build();
      Users balance=Users.builder()
              .id(user.getId())
              .name(user.getName())
              .email(user.getEmail())
              .password(user.getPassword())
              .accountBalance(user.getAccountBalance()-booking.getFare())
              .build();
      balance=usersRepository.save(balance);
      booking=bookingRepository.save(booking);
      return modelMapper.map(booking,BookingResponse.class);


  }
   public BookingResponse bookingById(long id){
     Booking booking=bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
     return modelMapper.map(booking,BookingResponse.class);
  }

    public CancelBookingResponse cancelById(long bookingId,long userId) {
        Users user= usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        if(!bookingRepository.existsById(bookingId)){
            throw new RuntimeException("booking not found");
        }
            bookingRepository.deleteById(bookingId);
        Booking booking = Booking.builder()
                .status(Status.CANCELLED)
                .build();
        Users balance=Users.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(user.getAccountBalance()+booking.getFare())
                .build();
        balance=usersRepository.save(balance);
        return modelMapper.map(booking, CancelBookingResponse.class);
    }

    }


