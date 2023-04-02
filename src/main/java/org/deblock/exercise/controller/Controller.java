package org.deblock.exercise.controller;

import org.deblock.exercise.model.ApiResponse;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.deblock.exercise.service.DeblockFlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class Controller {
    private final DeblockFlightService flightService;
    public Controller(DeblockFlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public ResponseEntity<ApiResponse<List<FlightResponse>>> findFlights(@RequestParam(value = "origin") @Size(min = 3, max = 3, message = "origin is invalid") String origin,
                                                            @RequestParam(value = "destination") @Size(min = 3, max = 3, message = "destination is invalid") String destination,
                                                            @RequestParam(value = "departureDate") @DateTimeFormat(pattern= "yyyy-MM-dd") @FutureOrPresent(message = "Departure date must be a future date") Date departureDate,
                                                            @RequestParam(value = "returnDate") @DateTimeFormat(pattern= "yyyy-MM-dd") @FutureOrPresent(message = "Return date must be a future date") Date returnDate,
                                                            @RequestParam(value = "numberOfPassengers") @Min(value = 1, message = "at least one passenger is required") Integer numberOfPassengers) {
        final FindFlightsRequest request = FindFlightsRequest.builder()
                .origin(origin).destination(destination).numberOfPassengers(numberOfPassengers == null ? 1 : numberOfPassengers)
                .departureDate(departureDate).returnDate(returnDate)
                .build();
        final List<FlightResponse> responses = flightService.findFlights(request);
        final ApiResponse<List<FlightResponse>> data = ApiResponse.<List<FlightResponse>>builder()
                .data(responses).message("success").error(false).build();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
