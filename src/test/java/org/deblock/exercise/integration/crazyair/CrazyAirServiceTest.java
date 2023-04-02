package org.deblock.exercise.integration.crazyair;

import org.deblock.exercise.integration.crazyair.mapper.CrazyAirDataMapper;
import org.deblock.exercise.integration.crazyair.mapper.CrazyAirDataMapperImpl;
import org.deblock.exercise.integration.crazyair.model.CrazyAirFlight;
import org.deblock.exercise.integration.crazyair.model.CrazyAirResponse;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.deblock.exercise.rest.ApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CrazyAirServiceTest {

    private final CrazyAirDataMapper dataMapper = new CrazyAirDataMapperImpl();
    @Mock
    private ApiService apiService;

    private static final String SERVICE_URL = "http://test.url";
    private CrazyAirService crazyAirService;

    @BeforeEach
    protected void setUp() {
        crazyAirService = new CrazyAirService(apiService, dataMapper, SERVICE_URL);
    }

    @Test
    public void crazyAirService_whenServiceRequested_thenReturnStructuredFlightsData() {
        // arrange
        final double price = 100;
        final Date departureDate = new Date();
        final Date arrivalDate = new Date();
        final List<CrazyAirFlight> crazyAirFlights = List.of(
                CrazyAirFlight.builder().cabinClass("E").airline("BA")
                        .departureDate(departureDate).price(price)
                        .arrivalDate(arrivalDate).destinationAirportCode("LHR").departureAirportCode("LOS").build()
        );
        final List<FlightResponse> flightResponses = List.of(
                FlightResponse.builder().fare(price).airline("BA").destinationAirportCode("LHR").departureAirportCode("LOS")
                        .supplier(CrazyAirDataMapper.SUPPLIER).arrivalDate(arrivalDate).departureDate(departureDate)
                        .build()
        );
        final FindFlightsRequest request = FindFlightsRequest.builder()
                .returnDate(arrivalDate)
                .numberOfPassengers(1)
                .departureDate(departureDate)
                .origin("LHR")
                .destination("LOS")
                .build();
        when(apiService.request(eq(SERVICE_URL), any(CrazyAirResponse.class),
                eq(request.getOrigin()), eq(request.getDestination()), eq(request.getDepartureDate().toString()),
                eq(request.getReturnDate().toString()), eq(String.valueOf(request.getNumberOfPassengers()))))
                .thenReturn(CrazyAirResponse.builder().data(crazyAirFlights).build());

        // act
        final List<FlightResponse> responses = crazyAirService.findFlights(request);

        // assert
        Assertions.assertEquals(1, responses.size());
        Assertions.assertTrue(new ReflectionEquals(flightResponses.get(0)).matches(responses.get(0)));
        verify(apiService, times(1)).request(eq(SERVICE_URL), any(CrazyAirResponse.class), eq(request.getOrigin()),
                eq(request.getDestination()), eq(request.getDepartureDate().toString()),
                eq(request.getReturnDate().toString()), eq(String.valueOf(request.getNumberOfPassengers())));
    }
}
