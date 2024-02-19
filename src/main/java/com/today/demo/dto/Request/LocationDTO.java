package com.today.demo.dto.Request;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class LocationDTO {
    private double latitude;
    private double longitude;
}
