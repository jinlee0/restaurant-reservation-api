package com.restaurantreservation.api.service.appservice;

import com.restaurantreservation.api.global.exception.impl.UserAlreadyHasRestaurant;
import com.restaurantreservation.api.global.util.SecurityService;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantDto;
import com.restaurantreservation.api.service.dto.restaurant.RestaurantRegistrationDto;
import com.restaurantreservation.api.service.entity.User;
import com.restaurantreservation.api.service.entity.restaurant.Restaurant;
import com.restaurantreservation.api.service.entity.restaurant.VoAddress;
import com.restaurantreservation.api.service.entity.restaurant.VoCoords;
import com.restaurantreservation.api.service.entity.restaurant.VoLocation;
import com.restaurantreservation.api.service.repository.RestaurantRepository;
import com.restaurantreservation.api.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantManagementServiceImpl implements RestaurantManagementService {
    private final RestaurantRepository restaurantRepository;
    private final SecurityService securityService;

    @Override
    @Transactional
    public RestaurantDto saveRestaurant(RestaurantRegistrationDto.Request dto) {
        User user = securityService.getUser();
        if(restaurantRepository.existsByManager(user)) // 유저가 이미 음식점을 등록했으면 400
            throw new UserAlreadyHasRestaurant();
        VoAddress address = VoAddress.builder()
            .main(dto.getAddressMain())
            .detail(dto.getAddressDetail())
            .build();
        VoCoords coords = VoCoords.builder()
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .build();
        Restaurant restaurant = restaurantRepository.save(
            Restaurant.builder()
                .manager(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .location(
                    VoLocation.builder()
                        .address(address)
                        .coords(coords)
                        .build()
                ).build()
        );
        return RestaurantDto.from(restaurant);
    }

    @Override
    public Page<Restaurant> retrieveRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }
}
