package com.loop614.sitrate.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.review.ReviewService;

@RestController
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review/popular/join")
    public ResponseEntity<PopularProductsResponse> popularReviewJoinFromReview() {
        return new ResponseEntity<>(this.reviewService.getTopRatedProductsJoinFromReview(), HttpStatus.OK);
    }

    @GetMapping("/review/popular/two")
    public ResponseEntity<PopularProductsResponse> popularReviewTwoQueries() {
        return new ResponseEntity<>(this.reviewService.getTopRatedProductsTwoQueries(), HttpStatus.OK);
    }
}
