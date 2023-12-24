package com.example.amadeus.service;

import com.example.amadeus.entity.Flight;
import com.example.amadeus.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    public void saveAll(List<Flight> flightData) {
        flightRepository.saveAll(flightData);
    }

    public List<Flight> searchOneWayFlights(String departureCity, String arrivalCity, String departureDate) {
        // Implement logic to search for one-way flights
        // Use flightRepository or custom queries to retrieve flights based on provided criteria
        return flightRepository.findByDepartureAirportCityAndArrivalAirportCityAndDepartureDateTime(departureCity, arrivalCity, departureDate);
    }

    public List<Flight> searchTwoWayFlights(String departureCity, String arrivalCity, String departureDate, String returnDate) {
        // Implement logic to search for two-way flights
        // Use flightRepository or custom queries to retrieve flights based on provided criteria
        return flightRepository.findByDepartureAndReturnDates(departureCity, arrivalCity, departureDate, returnDate);
    }
}
