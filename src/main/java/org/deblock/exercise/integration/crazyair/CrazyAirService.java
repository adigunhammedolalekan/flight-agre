package org.deblock.exercise.integration.crazyair;

import org.deblock.exercise.Data;
import org.deblock.exercise.integration.Supplier;
import org.deblock.exercise.integration.crazyair.mapper.CrazyAirDataMapper;
import org.deblock.exercise.integration.crazyair.model.CrazyAirFlight;
import org.deblock.exercise.integration.crazyair.model.CrazyAirRequest;
import org.deblock.exercise.integration.crazyair.model.CrazyAirResponse;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.deblock.exercise.rest.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrazyAirService implements Supplier {
    private final ApiService apiService;
    private final CrazyAirDataMapper mapper;
    private final String serviceUrl;

    public CrazyAirService(ApiService service, CrazyAirDataMapper mapper, @Value("${services.crazyair}") String serviceUrl) {
        this.apiService = service;
        this.serviceUrl = serviceUrl;
        this.mapper = mapper;
    }
    @Override
    public List<FlightResponse> findFlights(FindFlightsRequest request) {
        final CrazyAirRequest crazyAirRequest = mapper.convertRequest(request);
        final List<CrazyAirFlight> flights = apiService.request(serviceUrl,
                CrazyAirResponse.builder().data(Data.CRAZY_AIR_FLIGHTS).build(), crazyAirRequest.getOrigin(), crazyAirRequest.getDestination(),
                crazyAirRequest.getDepartureDate().toString(), crazyAirRequest.getReturnDate().toString(),
                String.valueOf(crazyAirRequest.getPassengerCount())).getData();
        return flights.stream().map(mapper::convert).collect(Collectors.toList());
    }
}
