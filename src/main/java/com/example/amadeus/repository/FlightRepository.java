package com.example.amadeus.repository;

import com.example.amadeus.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureAirportCityAndArrivalAirportCityAndDepartureDateTime(String departureCity, String arrivalCity, String departureDate);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.city = :departureCity " +
            "AND f.arrivalAirport.city = :arrivalCity " +
            "AND f.departureDateTime = :departureDate " +
            "AND f.returnDateTime = :returnDate")
    List<Flight> findByDepartureAndReturnDates(String departureCity, String arrivalCity, String departureDate, String returnDate);
}
