package com.techeer.hackathon.domain.review.service;

import com.techeer.hackathon.domain.restaurant.entity.Restaurant;
import com.techeer.hackathon.domain.restaurant.error.RestaurantNotFoundException;
import com.techeer.hackathon.domain.restaurant.repository.RestaurantRepository;
import com.techeer.hackathon.domain.review.dto.ReviewCreate;
import com.techeer.hackathon.domain.review.dto.ReviewInfo;
import com.techeer.hackathon.domain.review.entity.Review;
import com.techeer.hackathon.domain.review.exception.ReviewNotFoundException;
import com.techeer.hackathon.domain.review.mapper.ReviewMapper;
import com.techeer.hackathon.domain.review.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewMapper reviewMapper;

    public void createReview(ReviewCreate request) {
        Restaurant restaurant = restaurantRepository.findByName(request.getRestaurantName()).orElseThrow(RestaurantNotFoundException::new);
        reviewRepository.save(reviewMapper.toEntity(request, restaurant));
    }

    public ReviewInfo getReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        return reviewMapper.toDto(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.deleteById(id);
    }
}
