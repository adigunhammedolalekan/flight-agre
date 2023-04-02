package org.deblock.exercise.integration.crazyair.mapper;

import org.deblock.exercise.integration.crazyair.model.CrazyAirFlight;
import org.deblock.exercise.integration.crazyair.model.CrazyAirRequest;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CrazyAirDataMapper {
    public static final String SUPPLIER = "CrazyAir";
    default FlightResponse convert(CrazyAirFlight flight) {
        return FlightResponse.builder()
                .fare(flight.getPrice())
                .airline(flight.getAirline())
                .departureAirportCode(flight.getDepartureAirportCode())
                .destinationAirportCode(flight.getDestinationAirportCode())
                .arrivalDate(flight.getArrivalDate())
                .departureDate(flight.getDepartureDate())
                .supplier(SUPPLIER)
                .build();
    }

    default CrazyAirRequest convertRequest(FindFlightsRequest request) {
        return CrazyAirRequest.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureDate(request.getDepartureDate())
                .returnDate(request.getReturnDate())
                .passengerCount(request.getNumberOfPassengers())
                .build();
    }
}
