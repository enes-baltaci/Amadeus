package com.example.amadeus.controller;

import com.example.amadeus.entity.Flight;
import com.example.amadeus.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class SearchApiController {

    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(@RequestParam("departureCity") String departureCity,
                                           @RequestParam("arrivalCity") String arrivalCity,
                                           @RequestParam("departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String departureDate,
                                           @RequestParam(value = "returnDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String returnDate) {
        List<Flight> flights;

        if (returnDate != null) {
            // Two-way flight search
            flights = flightService.searchTwoWayFlights(departureCity, arrivalCity, departureDate, returnDate);
        } else {
            // One-way flight search
            flights = flightService.searchOneWayFlights(departureCity, arrivalCity, departureDate);
        }

        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (returnDate != null && flights.size() < 2) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insufficient data for two-way flight");
        }

        return ResponseEntity.ok(flights);
    }
}
