package org.deblock.exercise.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class FlightResponse {
    private String airline;
    private String supplier;
    private double fare;
    private String departureAirportCode;
    private String destinationAirportCode;
    private Date departureDate;
    private Date arrivalDate;
}
