package com.loop614.sitrate.review.domain;

import com.loop614.sitrate.product.transfer.PopularProductsResponse;

public interface ReviewReader {
    public PopularProductsResponse getTopRatedProductsJoinFromReview();

    public PopularProductsResponse getTopRatedProductsTwoQueries();
}
