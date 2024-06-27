package com.pds.biblioteca.controller;

import com.pds.biblioteca.entity.Booking;
import com.pds.biblioteca.service.firestore.BookingFirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingFirestoreService bookingFirestoreService;

    @PostMapping
    public ResponseEntity<Booking> doBooking(@RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookingFirestoreService.doBooking(booking));
    }

}
