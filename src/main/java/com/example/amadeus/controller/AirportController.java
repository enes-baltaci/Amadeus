package com.example.amadeus.controller;

import com.example.amadeus.entity.Airport;
import com.example.amadeus.exception.AirportNotFoundException;
import com.example.amadeus.service.AirportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
@Tag(name = "Airport", description = "Airport CRUD APIs")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public void createAirport(@RequestBody Airport airport) {
        airportService.save(airport);
    }

    @GetMapping("/{id}")
    public Airport getAirportById(@PathVariable("id") Long id) {
        return airportService.findById(id).orElseThrow(() -> new AirportNotFoundException(id));
    }

    @PutMapping("/{id}")
    public void updateAirport(@PathVariable Long id, @RequestBody Airport updatedAirport) {
        airportService.findById(id)
                .ifPresentOrElse(airport -> {
                    airport.setCity(updatedAirport.getCity());
                    airportService.save(airport);
                }, () -> {
                    throw new AirportNotFoundException(id);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
    }
}
