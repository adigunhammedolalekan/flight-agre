package org.deblock.exercise.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T> {
    private boolean error;
    private String message;
    private T data;
}
