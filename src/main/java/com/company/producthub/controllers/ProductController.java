package com.company.producthub.controllers;

import com.company.producthub.entities.ProductRequest;
import com.company.producthub.entities.ProductResponse;
import com.company.producthub.services.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the controller of a product request received from a client.
 *
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * Serves a product request for a product.
     *
     * It consumes a product request submitted by a client. Populates merchants
     * of the request, posts them the product request and waiting for their
     * product response.
     *
     * Produces a winner product response that will be sent to the client. The
     * winner is the product containing the minimum price.
     *
     * @param prodRequest the product request to be served
     * @return the winner product response along with an http status 200 if it
     * is present Otherwise, an http status 204 without a product response
     */
    @PostMapping(path = "/products", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductResponse> serve(@RequestBody ProductRequest prodRequest) {

        log.debug("{} received from a client", prodRequest);
        productService.setMerchantsToProductRequest(prodRequest);
        ProductResponse winnerProductResp = productService.
                searchWinnerProduct(asyncRequestToMerchants(prodRequest));

        ResponseEntity<ProductResponse> responseEntity;
        if (winnerProductResp == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = ResponseEntity.ok(winnerProductResp);
        }
        log.debug("{} successfully processed with response {}", prodRequest, responseEntity);

        return responseEntity;
    }

    /**
     * Sends a product request to all merchants of the given product request and
     * returns a list of product responses that will be completed in the future.
     *
     * @param request the product request to be sent
     *
     * @return a list of CompletableFuture<ProductResponse>
     */
    private List<CompletableFuture<ProductResponse>> asyncRequestToMerchants(@NotNull ProductRequest request) {
        List<CompletableFuture<ProductResponse>> futureProdResps = new ArrayList<>();

        request.getMerchants()
                .stream()
                .map(merchant -> merchant.getApiUrl())
                .forEach(merchantUrl -> {
                    futureProdResps.add(productService.asyncRequestToMerchant(request, merchantUrl));
                });

        return futureProdResps;
    }
}
