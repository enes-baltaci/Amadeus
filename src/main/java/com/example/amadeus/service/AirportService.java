package com.example.amadeus.service;

import com.example.amadeus.entity.Airport;
import com.example.amadeus.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public void save(Airport airport) {
        airportRepository.save(airport);
    }

    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    public void deleteById(Long id) {
        airportRepository.deleteById(id);
    }
}
