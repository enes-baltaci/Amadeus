package com.example.amadeus.controller;

import com.example.amadeus.entity.Flight;
import com.example.amadeus.exception.FlightNotFoundException;
import com.example.amadeus.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public void createFlight(@RequestBody Flight flight) {
        flightService.save(flight);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.findById(id).orElseThrow(() -> new FlightNotFoundException(id));
    }

    @PutMapping("/{id}")
    public void updateFlight(@PathVariable Long id, @RequestBody Flight updatedFlight) {
        flightService.findById(id)
                .ifPresentOrElse(flight -> {
                    flight.setDepartureAirport(updatedFlight.getDepartureAirport());
                    flight.setArrivalAirport(updatedFlight.getArrivalAirport());
                    flight.setDepartureDateTime(updatedFlight.getDepartureDateTime());
                    flight.setReturnDateTime(updatedFlight.getReturnDateTime());
                    flight.setPrice(updatedFlight.getPrice());
                    flightService.save(flight);
                }, () -> {
                    throw new FlightNotFoundException(id);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteById(id);
    }
}
