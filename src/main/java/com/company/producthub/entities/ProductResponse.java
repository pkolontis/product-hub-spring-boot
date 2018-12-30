package com.company.producthub.entities;

import java.util.Objects;

/**
 * Represents a product response that comes from a merchant.
 * A product response should have a product that matches 
 * the search criteria of client's product request.
 * 
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public class ProductResponse {
    
    private String id;
    private Product product;

    /**
     * Creates an empty product response
     */
    public ProductResponse() {
    }

    /**
     * Gets the unique id of a product response
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of a product response
     * 
     * @param id the id to set 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the product of a product response
     * 
     * @return the product 
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product of a product response
     * 
     * @param product the product to set 
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    
    /**
     * A string representation of a product response used for logging
     * 
     * @return a string representation 
     */
    @Override
    public String toString() {
        StringBuilder responseStrBuilder = new StringBuilder("ProductResponse{");
        responseStrBuilder.append("id=").append(id);
        responseStrBuilder.append("}");
        
        return responseStrBuilder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductResponse other = (ProductResponse) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }
}
