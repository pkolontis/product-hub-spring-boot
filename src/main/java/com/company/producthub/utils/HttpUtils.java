package com.company.producthub.utils;

import com.company.producthub.entities.ProductRequest;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Represents utilities for http operations
 *
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public class HttpUtils {

    /**
     * Builds an http entity for the given request. The http header contentType
     * will be set to APPLICATION_JSON.
     *
     * @param request the product request
     *
     * @return the HttpEntity of a product request
     */
    public static HttpEntity<ProductRequest> buildHttpEntityAppJson(@NotNull ProductRequest request) {
        return buildHttpEntityWithContentType(request, MediaType.APPLICATION_JSON);
    }

    /**
     * Builds an http entity for the given request. The http header contentType
     * will be set to the given media type.
     *
     * @param request the product request
     * @param mediaType the media type to set
     *
     * @return the HttpEntity object of a product request
     */
    public static HttpEntity<ProductRequest> buildHttpEntityWithContentType(@NotNull ProductRequest request,
            @NotNull MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        return new HttpEntity<>(request, headers);
    }
}
