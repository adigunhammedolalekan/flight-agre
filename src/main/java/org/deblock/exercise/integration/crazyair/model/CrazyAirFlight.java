package org.deblock.exercise.integration.crazyair.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CrazyAirFlight {
    private String airline;
    private double price;
    private String cabinClass;
    private String departureAirportCode;
    private String destinationAirportCode;
    private Date departureDate;
    private Date arrivalDate;
}
