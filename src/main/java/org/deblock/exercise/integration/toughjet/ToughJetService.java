package org.deblock.exercise.integration.toughjet;

import org.deblock.exercise.Data;
import org.deblock.exercise.integration.Supplier;
import org.deblock.exercise.integration.toughjet.mapper.ToughJetDataMapper;
import org.deblock.exercise.integration.toughjet.model.ToughJetFlight;
import org.deblock.exercise.integration.toughjet.model.ToughJetRequest;
import org.deblock.exercise.integration.toughjet.model.ToughJetResponse;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.deblock.exercise.rest.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToughJetService implements Supplier {
    private final ApiService apiService;
    private final ToughJetDataMapper mapper;
    private final String serviceUrl;

    public ToughJetService(ApiService service, ToughJetDataMapper mapper, @Value("${services.toughjet}") String serviceUrl) {
        this.apiService = service;
        this.serviceUrl = serviceUrl;
        this.mapper = mapper;
    }
    @Override
    public List<FlightResponse> findFlights(FindFlightsRequest request) {
        final ToughJetRequest toughJetRequest = mapper.convertRequest(request);
        final List<ToughJetFlight> flights = apiService.request(serviceUrl,
                ToughJetResponse.builder().data(Data.TOUGH_JET_FLIGHTS).build(), toughJetRequest.getFrom(), toughJetRequest.getTo(),
                toughJetRequest.getOutboundDate().toString(), toughJetRequest.getInboundDate().toString(),
                String.valueOf(toughJetRequest.getNumberOfAdults())).getData();
        return flights.stream().map(mapper::convert).collect(Collectors.toList());
    }
}
