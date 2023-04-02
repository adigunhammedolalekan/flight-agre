package org.deblock.exercise.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class FindFlightsRequest {
    private String origin;
    private String destination;
    private Date departureDate;
    private Date returnDate;
    private int numberOfPassengers;
}
