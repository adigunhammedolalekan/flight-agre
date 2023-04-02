package org.deblock.exercise.integration.crazyair.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CrazyAirRequest {
    private String origin;
    private String destination;
    private Date departureDate;
    private Date returnDate;
    private int passengerCount;
}
