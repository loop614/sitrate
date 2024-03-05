package com.loop614.sitrate.review;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;
import com.loop614.sitrate.review.entity.Review;

public interface ReviewService {
    Review save(Review review);

    PopularProductsResponse getTopRatedProductsJoinFromReview();

    PopularProductsResponse getTopRatedProductsTwoQueries();
}
