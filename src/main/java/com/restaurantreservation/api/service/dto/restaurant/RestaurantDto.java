package com.restaurantreservation.api.service.dto.restaurant;

import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RestaurantDto {
    private String id;
    private String name;
    private String description;
    private String addressMain;
    private String addressDetail;
    private Double latitude;
    private Double longitude;
    private String managerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantDto from(Restaurant restaurant) {
        return RestaurantDto.builder()
            .id(restaurant.getId())
            .name(restaurant.getName())
            .description(restaurant.getDescription())
            .addressMain(restaurant.getLocation().getAddress().getMain())
            .addressDetail(restaurant.getLocation().getAddress().getDetail())
            .latitude(restaurant.getLocation().getCoords().getLatitude())
            .longitude(restaurant.getLocation().getCoords().getLongitude())
            .managerId(restaurant.getManager().getId())
            .createdAt(restaurant.getCreatedAt())
            .updatedAt(restaurant.getUpdatedAt())
            .build();
    }
}
