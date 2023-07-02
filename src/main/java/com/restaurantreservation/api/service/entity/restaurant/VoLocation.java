package com.restaurantreservation.api.service.entity.restaurant;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoLocation{
    @Embedded
    private VoAddress address;
    @Embedded
    private VoCoords coords;
}