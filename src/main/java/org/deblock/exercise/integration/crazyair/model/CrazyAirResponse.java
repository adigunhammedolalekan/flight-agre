package org.deblock.exercise.integration.crazyair.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CrazyAirResponse {
    private List<CrazyAirFlight> data;
}
