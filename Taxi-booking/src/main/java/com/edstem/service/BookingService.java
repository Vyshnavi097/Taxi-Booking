package com.edstem.service;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.request.CancelBookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.contract.response.TaxiResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final UsersRepository usersRepository;
    private final TaxiRepository taxiRepository;


    public BookingResponse bookingg(BookingRequest bookingRequest, long userId, long taxiId, Double distance) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Taxi taxi = taxiRepository.findById(taxiId).orElseThrow(() -> new EntityNotFoundException("taxi not found"));
        Double amount = distance * 10d;
        if (amount > user.getAccountBalance()

        ) {
            throw new RuntimeException("insufficient balance");
        }
        Booking booking = Booking.builder()
                .user(user)
                .taxi(taxi)
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropoffLocation(bookingRequest.getDropoffLocation())
                 .bookingTime(LocalDateTime.parse(LocalDateTime.now().toString()))
                .status(Status.BOOKED)
                .fare(amount)
                .build();
        Users users = Users.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(user.getAccountBalance() - booking.getFare())
                .build();
        users = usersRepository.save(users);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);


    }

    public BookingResponse bookingById(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
        return modelMapper.map(booking, BookingResponse.class);
    }

    public CancelBookingResponse cancelById(long bookingId, long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Booking booking1 = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("booking not found"));
        if (!bookingRepository.existsById(bookingId)) {
            throw new RuntimeException("booking not found");
        }
        bookingRepository.deleteById(bookingId);

        Booking booking = Booking.builder()
                .status(Status.CANCELLED)
                .build();
        Users balance = Users.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(user.getAccountBalance() + booking1.getFare())
                .build();
        balance = usersRepository.save(balance);
        return modelMapper.map(booking, CancelBookingResponse.class);
    }

    public List<TaxiResponse> findNearest(Long userId, String pickupLocation) {
        Users user =
                usersRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Taxi> taxi = taxiRepository.findAll();
        List<Taxi> taxiAvailable = new ArrayList<>();
        for (Taxi taxis : taxi) {
            if (taxis.getCurrentLocation().equals(pickupLocation)) {
                taxiAvailable.add(taxis);
            }
        }
        if (taxiAvailable.isEmpty()) {
            throw new EntityNotFoundException("No taxi available");
        } else {
            return taxiAvailable.stream()
                    .map(taxi1 -> modelMapper.map(taxi1, TaxiResponse.class))
                    .collect(Collectors.toList());
        }

    }
}


