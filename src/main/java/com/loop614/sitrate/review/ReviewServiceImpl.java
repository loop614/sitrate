package com.loop614.sitrate.review;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.review.domain.ReviewReader;
import com.loop614.sitrate.review.entity.Review;
import com.loop614.sitrate.review.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    private final ReviewReader reviewReader;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewReader reviewReader) {
        this.reviewRepository = reviewRepository;
        this.reviewReader = reviewReader;
    }

    public Review save(Review review) {
        return this.reviewRepository.save(review);
    }

    public PopularProductsResponse getTopRatedProductsJoinFromReview() {
        return this.reviewReader.getTopRatedProductsJoinFromReview();
    }

    public PopularProductsResponse getTopRatedProductsTwoQueries() {
        return this.reviewReader.getTopRatedProductsTwoQueries();
    }
}
