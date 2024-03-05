package com.loop614.sitrate.review.controller;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.review.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review/popular/join")
    public ResponseEntity<PopularProductsResponse> popularReviewJoinFromReview() {
        return new ResponseEntity<PopularProductsResponse>(this.reviewService.getTopRatedProductsJoinFromReview(), HttpStatus.OK);
    }

    @GetMapping("/review/popular/two")
    public ResponseEntity<PopularProductsResponse> popularReviewTwoQueries() {
        return new ResponseEntity<PopularProductsResponse>(this.reviewService.getTopRatedProductsTwoQueries(), HttpStatus.OK);
    }
}
