package org.deblock.exercise;

import org.deblock.exercise.integration.crazyair.model.CrazyAirFlight;
import org.deblock.exercise.integration.toughjet.model.ToughJetFlight;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public final class Data {
    public static final List<ToughJetFlight> TOUGH_JET_FLIGHTS = List.of(
            ToughJetFlight.builder()
                    .tax(10)
                    .arrivalAirportName("LHR")
                    .departureAirportName("LOS")
                    .basePrice(100)
                    .carrier("British Airways")
                    .discount(10)
                    .inboundDateTime(Date.from(Instant.now()))
                    .outboundDateTime(Date.from(Instant.now()))
                    .build(),
            ToughJetFlight.builder()
                    .tax(15)
                    .arrivalAirportName("LHR")
                    .departureAirportName("DXB")
                    .basePrice(200)
                    .carrier("Etihad Airways")
                    .discount(0)
                    .inboundDateTime(Date.from(Instant.now()))
                    .outboundDateTime(Date.from(Instant.now()))
                    .build(),
            ToughJetFlight.builder()
                    .tax(10)
                    .arrivalAirportName("LOS")
                    .departureAirportName("SPR")
                    .basePrice(150)
                    .carrier("Wix Air")
                    .discount(10)
                    .inboundDateTime(Date.from(Instant.now()))
                    .outboundDateTime(Date.from(Instant.now()))
                    .build()
    );

    public static List<CrazyAirFlight> CRAZY_AIR_FLIGHTS = List.of(
            CrazyAirFlight.builder()
                    .destinationAirportCode("LOS")
                    .departureAirportCode("LHR")
                    .price(250)
                    .departureDate(Date.from(Instant.now()))
                    .arrivalDate(Date.from(Instant.now()))
                    .airline("Virgin Atlantic")
                    .cabinClass("Premium Economy")
                    .build(),
            CrazyAirFlight.builder()
                    .destinationAirportCode("JFK")
                    .departureAirportCode("LHR")
                    .price(350)
                    .departureDate(Date.from(Instant.now()))
                    .arrivalDate(Date.from(Instant.now()))
                    .airline("American Airline")
                    .cabinClass("First Class")
                    .build(),
            CrazyAirFlight.builder()
                    .destinationAirportCode("LCR")
                    .departureAirportCode("DXB")
                    .price(250)
                    .departureDate(Date.from(Instant.now()))
                    .arrivalDate(Date.from(Instant.now()))
                    .airline("British Airways")
                    .cabinClass("Economy")
                    .build(),
            CrazyAirFlight.builder().build()
    );
}
