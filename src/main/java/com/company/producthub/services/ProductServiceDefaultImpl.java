package com.company.producthub.services;

import com.company.producthub.entities.Merchant;
import com.company.producthub.entities.ProductRequest;
import com.company.producthub.entities.ProductResponse;
import com.company.producthub.utils.HttpUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Represents the default implementation of ProductService
 *
 * @see ProductService
 *
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
@Service
public class ProductServiceDefaultImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceDefaultImpl.class);

    @Autowired
    private RestTemplate template;

    /**
     * @see ProductService
     *
     * @param request the request to be sent
     * @param merchantUrl the merchant url
     *
     */
    @Override
    @Async
    public CompletableFuture<ProductResponse> asyncRequestToMerchant(@NotNull ProductRequest request,
            @NotNull String merchantUrl) {
        log.debug("Sending ProductRequest to merchant {}", merchantUrl);
        ProductResponse prodResp = null;
        try {
            HttpEntity<ProductRequest> httpEntity = HttpUtils.buildHttpEntityAppJson(request);
            ResponseEntity<ProductResponse> prodRespEnt = template
                    .postForEntity(merchantUrl, httpEntity, ProductResponse.class);
            prodResp = prodRespEnt.getBody();
            log.debug("{} successfully received from merchant {}", prodRespEnt, merchantUrl);
        } catch (Exception ex) {
            log.warn("Failed to receive ProductResponse from merchant {}", merchantUrl, ex);
        }

        return CompletableFuture.completedFuture(prodResp);
    }

    /**
     * @see ProductService
     *
     * @param prodResps the list of CompletableFuture<ProductResponse>
     */
    @Override
    public ProductResponse searchWinnerProduct(@NotNull List<CompletableFuture<ProductResponse>> prodResps) {
        CompletableFuture.allOf(prodResps.toArray(new CompletableFuture[prodResps.size()])).join();

        log.debug("Searching for Winner ProductResponse...");
        ProductResponse winnerResp = prodResps.stream()
                .map(futureProductResp -> {
                    return futureProductResp.getNow(null);
                }).collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparing(productResponse -> productResponse.getProduct().getPrice()))
                .orElse(null);
        log.debug("Search for Winner ProductResponse successfully completed");

        return winnerResp;
    }

    /**
     * @see ProductService
     *
     * @param request the request
     */
    @Override
    public void setMerchantsToProductRequest(@NotNull ProductRequest request) {
        // TODO: Replace hard-coded merchants with the 
        // implemented logic of business requirements so that 
        // merchants' set can be populated according to request's searchTerm
        request.addMerchant(new Merchant("http://merchant-1.com/api/product"));
        request.addMerchant(new Merchant("http://merchant-2.com/api/product"));
        request.addMerchant(new Merchant("http://merchant-3.com/api/product"));
    }
}
