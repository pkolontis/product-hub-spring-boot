package com.company.producthub.services;

import com.company.producthub.entities.ProductRequest;
import com.company.producthub.entities.ProductResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.constraints.NotNull;

/**
 * Represents a product service interface that performs operations related to
 * product request and response.
 *
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public interface ProductService {

    /**
     * Sends a product request to the given merchant url and returns a product
     * response that will be completed in the future.
     *
     * @param request the product request to be sent
     * @param merchantUrl the api url of a merchant
     *
     * @return a CompletableFuture<ProductResponse>
     *
     * Note: This method will be asynchronously executed
     */
    public CompletableFuture<ProductResponse> asyncRequestToMerchant(@NotNull ProductRequest request,
            @NotNull String merchantUrl);

    /**
     * Searches winner product response based on a given list of product
     * responses that will be completed in the future. The search process will
     * be started when all future responses are completed.
     *
     * Returns the product response containing the product with the minimum
     * price among all available product responses. Returns null if there are no
     * available product responses.
     *
     * @param prodResps the list of CompletableFuture<ProductResponse> to search
     * for
     *
     * @return the winner product response, otherwise null if not found
     */
    public ProductResponse searchWinnerProduct(@NotNull List<CompletableFuture<ProductResponse>> prodResps);

    /**
     * Sets merchants to the given product request based on request's data.
     *
     * @param prodRequest the product request to be populated with merchants
     */
    public void setMerchantsToProductRequest(@NotNull ProductRequest prodRequest);
}
