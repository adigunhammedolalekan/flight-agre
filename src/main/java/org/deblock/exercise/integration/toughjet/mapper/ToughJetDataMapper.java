package org.deblock.exercise.integration.toughjet.mapper;

import org.deblock.exercise.integration.toughjet.model.ToughJetFlight;
import org.deblock.exercise.integration.toughjet.model.ToughJetRequest;
import org.deblock.exercise.model.FindFlightsRequest;
import org.deblock.exercise.model.FlightResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToughJetDataMapper {
    public final String SUPPLIER = "ToughJet";
    default FlightResponse convert(ToughJetFlight flight) {
        final int totalFare = flight.getBasePrice() + flight.getTax();
        final double fare = totalFare - (totalFare * flight.getDiscount() / 100);
        return FlightResponse.builder()
                .airline(flight.getCarrier())
                .arrivalDate(flight.getInboundDateTime())
                .departureDate(flight.getOutboundDateTime())
                .departureAirportCode(flight.getDepartureAirportName())
                .destinationAirportCode(flight.getArrivalAirportName())
                .supplier(SUPPLIER)
                .fare(fare)
                .build();
    }
    default ToughJetRequest convertRequest(FindFlightsRequest request) {
        return ToughJetRequest.builder()
                .from(request.getOrigin())
                .to(request.getDestination())
                .outboundDate(request.getDepartureDate())
                .inboundDate(request.getReturnDate())
                .numberOfAdults(request.getNumberOfPassengers())
                .build();
    }
}
