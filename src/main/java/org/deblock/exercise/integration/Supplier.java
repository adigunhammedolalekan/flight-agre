package org.deblock.exercise.integration;

import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;

import java.util.List;

public interface Supplier {
    List<FlightResponse> findFlights(FindFlightsRequest request);
}
