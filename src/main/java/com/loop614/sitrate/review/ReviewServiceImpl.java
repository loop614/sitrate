package com.loop614.sitrate.review;

import org.springframework.stereotype.Service;

import com.loop614.sitrate.review.entity.Review;
import com.loop614.sitrate.review.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review)
    {
        return this.reviewRepository.save(review);
    }
}
