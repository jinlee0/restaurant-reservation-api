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
public class VoAddress {
    @Column(name = "address_main", nullable = false)
    private String main;
    @Column(name = "address_detail", nullable = false)
    private String detail = "";
}
