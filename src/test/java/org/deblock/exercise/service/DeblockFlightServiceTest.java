package org.deblock.exercise.service;

import org.deblock.exercise.integration.crazyair.CrazyAirService;
import org.deblock.exercise.integration.toughjet.ToughJetService;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeblockFlightServiceTest {

    @Mock
    private CrazyAirService crazyAirService;
    @Mock
    private ToughJetService toughJetService;

    private DeblockFlightService deblockFlightService;

    @BeforeEach
    protected void setUp() {
        deblockFlightService = new DeblockFlightService(crazyAirService, toughJetService);
    }
    @Test
    public void flightService_whenSendFlightRequests_returnFlightsData() {
        // arrange
        final FindFlightsRequest request = FindFlightsRequest.builder().build();
        when(crazyAirService.findFlights(request)).thenReturn(List.of(FlightResponse.builder().build()));
        when(toughJetService.findFlights(request)).thenReturn(List.of(FlightResponse.builder().build()));

        // act
        final List<FlightResponse> responses = deblockFlightService.findFlights(request);

        // assert
        Assertions.assertEquals(2, responses.size());
        verify(crazyAirService, times(1)).findFlights(eq(request));
        verify(toughJetService, times(1)).findFlights(eq(request));
    }
}
