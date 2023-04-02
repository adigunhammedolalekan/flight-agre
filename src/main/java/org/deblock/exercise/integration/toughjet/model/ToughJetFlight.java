package org.deblock.exercise.integration.toughjet.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ToughJetFlight {
    private String carrier;
    private int basePrice;
    private int tax;
    private double discount;
    private String departureAirportName;
    private String arrivalAirportName;
    private Date outboundDateTime;
    private Date inboundDateTime;
}
