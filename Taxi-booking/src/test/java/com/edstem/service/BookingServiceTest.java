package com.edstem.service;

import com.edstem.constant.Status;
import com.edstem.contract.request.BookingRequest;
import com.edstem.contract.response.BookingResponse;
import com.edstem.contract.response.CancelBookingResponse;
import com.edstem.model.Booking;
import com.edstem.model.Taxi;
import com.edstem.model.Users;
import com.edstem.repository.BookingRepository;
import com.edstem.repository.TaxiRepository;
import com.edstem.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {
    private BookingRepository bookingRepository;
    private ModelMapper modelMapper;
    private BookingService bookingService;
    private TaxiRepository taxiRepository;
    private UsersRepository usersRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        bookingRepository = mock(BookingRepository.class);
        taxiRepository = mock(TaxiRepository.class);
        usersRepository = mock(UsersRepository.class);
        modelMapper = new ModelMapper();
        bookingService =
                new BookingService(modelMapper, bookingRepository, usersRepository, taxiRepository);
    }

    @Test
    void testBookTaxi() {
        Users user = new Users(1L, "Sree", "Sree@gmail.com", "Sree@123", 2000.0);
        Taxi taxi = new Taxi(1L, "David", "KL 39987", "payyanur");
        BookingRequest request = new BookingRequest("payyanur", "annur", Status.BOOKED);
        Long taxiId = 1L;
        Long userId = 1L;
        Double distance = 80.0;
        Double expense = distance * 10D;
        Booking booking =
                Booking.builder()
                        .user(user)
                        .taxi(taxi)
                        .pickupLocation(request.getPickupLocation())
                        .dropoffLocation(request.getDropoffLocation())
                        //.bookingTime(LocalDateTime.parse(LocalDateTime.now().toString()))
                        .fare(expense)
                        .status(Status.BOOKED)
                        .build();
        Users users =
                Users.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .accountBalance(user.getAccountBalance() - booking.getFare())
                        .build();

        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));
        when(bookingRepository.save(any())).thenReturn(booking);
        when(usersRepository.save(any())).thenReturn(users);

        BookingResponse actualResponse = bookingService.bookingg(request, userId, taxiId, distance);
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void testBookingById() {
        Long id = 1L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.bookingById(id));
    }

    @Test
    void testCancelById() {
        long bookingId = 1L;
        long userId = 1L;
        Users user = new Users();
        Booking booking1 = new Booking();
        CancelBookingResponse response = new CancelBookingResponse();
        UsersRepository usersRepository = mock(UsersRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        ModelMapper modelMapper = mock(ModelMapper.class);
        BookingService bookingService = new BookingService(modelMapper, bookingRepository, usersRepository, taxiRepository);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking1));
        when(bookingRepository.existsById(bookingId)).thenReturn(true);
        when(modelMapper.map(any(Booking.class), eq(CancelBookingResponse.class))).thenReturn(response);

        CancelBookingResponse result = bookingService.cancelById(bookingId, userId);

        assertEquals(result, response);
        verify(usersRepository, times(1)).findById(userId);
        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

}
