package com.restaurantreservation.api.service.dto.restaurant;

import com.restaurantreservation.api.service.dto.PageDtoBase;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@NoArgsConstructor
public class RestaurantRetrieveDto extends PageDtoBase<RestaurantRetrieveDto.RestaurantInfo> {
    private RestaurantRetrieveDto(Page<Restaurant> page) {
        super(page, page.getContent().stream().map(RestaurantInfo::new).toList());
    }

    public static RestaurantRetrieveDto from(Page<Restaurant> page) {
        return new RestaurantRetrieveDto(page);
    }

    @Data
    public static class RestaurantInfo {
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

        private RestaurantInfo(Restaurant r) {
            id = r.getId();
            name = r.getName();
            description = r.getDescription();
            addressMain = r.getLocation().getAddress().getMain();
            addressDetail = r.getLocation().getAddress().getDetail();
            latitude = r.getLocation().getCoords().getLatitude();
            longitude = r.getLocation().getCoords().getLongitude();
            managerId = r.getManager().getId();
            createdAt = r.getCreatedAt();
            updatedAt = r.getUpdatedAt();
        }
    }
}
