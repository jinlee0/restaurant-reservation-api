package com.restaurantreservation.api.service.repository;

import com.restaurantreservation.api.service.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}