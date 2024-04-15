package com.loop614.sitrate.review;

import java.util.List;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.review.entity.Review;

public interface ReviewService {
    Review save(Review review);

    List<Review> saveAll(List<Review> reviews);

    PopularProductsResponse getTopRatedProductsJoinFromReview();

    PopularProductsResponse getTopRatedProductsTwoQueries();
}
