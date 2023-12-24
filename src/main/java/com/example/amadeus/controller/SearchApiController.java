package com.example.amadeus.controller;

import com.example.amadeus.entity.Flight;
import com.example.amadeus.service.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Search APIs")
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

        return ResponseEntity.ok(flights);
    }
}
