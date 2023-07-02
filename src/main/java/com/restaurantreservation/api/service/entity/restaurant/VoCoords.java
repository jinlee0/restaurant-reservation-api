package com.restaurantreservation.api.service.entity.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class VoCoords{
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
};
