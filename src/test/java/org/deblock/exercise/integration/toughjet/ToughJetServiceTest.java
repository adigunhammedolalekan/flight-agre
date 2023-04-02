package org.deblock.exercise.integration.toughjet;

import org.deblock.exercise.integration.toughjet.mapper.ToughJetDataMapper;
import org.deblock.exercise.integration.toughjet.mapper.ToughJetDataMapperImpl;
import org.deblock.exercise.integration.toughjet.model.ToughJetFlight;
import org.deblock.exercise.integration.toughjet.model.ToughJetResponse;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.deblock.exercise.rest.ApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToughJetServiceTest {

    @Mock
    private ApiService apiService;
    private ToughJetDataMapper dataMapper = new ToughJetDataMapperImpl();

    public static final String SERVICE_URL = "http://test.url";

    private ToughJetService toughJetService;

    @BeforeEach
    protected void setUp() {
        toughJetService = new ToughJetService(apiService, dataMapper, SERVICE_URL);
    }

    @ParameterizedTest
    @CsvSource({"100,20,5", "100,20,0"})
    public void toughJetService_whenServiceRequested_thenReturnStructuredFlightsData(String basePrice, String pTax, String pDiscount) {
        final int price = Integer.parseInt(basePrice), tax = Integer.parseInt(pTax), discount = Integer.parseInt(pDiscount); // 5% discount
        final int total = price + tax;
        final double expectedPrice = total - ((double) (total * discount) / 100);
        final Date departureDate = new Date();
        final Date arrivalDate = new Date();
        final List<ToughJetFlight> toughJetFlights = List.of(
                ToughJetFlight.builder().carrier("BA")
                        .outboundDateTime(departureDate).basePrice(price).tax(tax).discount(discount)
                        .inboundDateTime(arrivalDate).departureAirportName("LHR").arrivalAirportName("LOS").build()
        );
        final List<FlightResponse> flightResponses = List.of(
                FlightResponse.builder().fare(expectedPrice).airline("BA").destinationAirportCode("LOS").departureAirportCode("LHR")
                        .supplier(ToughJetDataMapper.SUPPLIER).arrivalDate(arrivalDate).departureDate(departureDate)
                        .build()
        );
        final FindFlightsRequest request = FindFlightsRequest.builder()
                .returnDate(arrivalDate)
                .numberOfPassengers(1)
                .departureDate(departureDate)
                .origin("LHR")
                .destination("LOS")
                .build();
        when(apiService.request(eq(SERVICE_URL), any(ToughJetResponse.class),
                eq(request.getOrigin()), eq(request.getDestination()), eq(request.getDepartureDate().toString()),
                eq(request.getReturnDate().toString()), eq(String.valueOf(request.getNumberOfPassengers()))))
                .thenReturn(ToughJetResponse.builder().data(toughJetFlights).build());

        // act
        final List<FlightResponse> responses = toughJetService.findFlights(request);

        // assert
        Assertions.assertEquals(1, responses.size());
        Assertions.assertTrue(new ReflectionEquals(flightResponses.get(0)).matches(responses.get(0)));
        verify(apiService, times(1)).request(eq(SERVICE_URL), any(ToughJetResponse.class), eq(request.getOrigin()),
                eq(request.getDestination()), eq(request.getDepartureDate().toString()),
                eq(request.getReturnDate().toString()), eq(String.valueOf(request.getNumberOfPassengers())));
    }
}
