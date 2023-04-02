package org.deblock.exercise.service;

import org.deblock.exercise.integration.Supplier;
import org.deblock.exercise.integration.crazyair.CrazyAirService;
import org.deblock.exercise.integration.toughjet.ToughJetService;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeblockFlightService {
    private final Set<Supplier> suppliers;
    public DeblockFlightService(CrazyAirService crazyAirService, ToughJetService toughJetService) {
        suppliers = new HashSet<>(Arrays.asList(crazyAirService, toughJetService));
    }
    public List<FlightResponse> findFlights(FindFlightsRequest request) {
        return suppliers.stream().map(supplier ->
                supplier.findFlights(request)).flatMap(List::stream)
                .sorted(Comparator.comparingDouble(FlightResponse::getFare).reversed()).collect(Collectors.toList());
    }
}
