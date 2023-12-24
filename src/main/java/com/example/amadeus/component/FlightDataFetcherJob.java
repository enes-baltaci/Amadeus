package com.example.amadeus.component;

import com.example.amadeus.entity.Airport;
import com.example.amadeus.entity.Flight;
import com.example.amadeus.service.AirportService;
import com.example.amadeus.service.FlightService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightDataFetcherJob {

    private final FlightService flightService;
    private final AirportService airportService;

    @Scheduled(initialDelay = 1000, fixedRate = 999999999) // Run every day at midnight
    @Transactional
    public void loadFlightsFromJson() {
        filler();
    }

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    @Transactional
    public void loadFlightsFromJsonAtMidnight() {
        filler();
    }

    private void filler() {
        try {
            // Read flight data from the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/flights.json");
            List<Flight> flights = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

            for (Flight flight : flights) {
                addAirportIfNotExists(flight.getDepartureAirport());
                addAirportIfNotExists(flight.getArrivalAirport());
            }

            // Save fetched flights to the database
            flightService.saveAll(flights);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as per your application's requirements
        }
    }

    private void addAirportIfNotExists(Airport airport) {
        Optional<Airport> airportExist = airportService.findById(airport.getId());

        if (airportExist.isEmpty()) {
            airportService.save(airport);
        }
    }
}
