package org.deblock.exercise.integration.toughjet.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ToughJetRequest {
    private String from;
    private String to;
    private Date outboundDate;
    private Date inboundDate;
    private int numberOfAdults;
}
