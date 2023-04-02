package org.deblock.exercise.integration.toughjet.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ToughJetResponse {
    private List<ToughJetFlight> data;
}
