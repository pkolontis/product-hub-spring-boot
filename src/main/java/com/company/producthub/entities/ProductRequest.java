package com.company.producthub.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a product request that comes from a client.
 * A product request does have a search term, a minimum
 * and a maximum price to search for according to client's
 * search criteria.
 * 
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public class ProductRequest {
    
    private String id;
    private String searchTerm;
    private double minPrice;
    private double maxPrice;
    private final Set<Merchant> merchants;

    /**
     * Creates an empty product request
     */
    public ProductRequest() {
        this.merchants = new HashSet<>();
    }

    /**
     * Gets the unique id of a product request
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of product request
     * 
     * @param id the id to set 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the search term of a product request
     * 
     * @return the search term 
     */
    public String getSearchTerm() {
        return searchTerm;
    }

    /**
     * Sets the search term of a product request
     * 
     * @param searchTerm the search term to set
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * Gets the minimum price of a product request
     * 
     * @return the minimum price 
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the minimum price of a product request
     * 
     * @param minPrice the minimum price to set 
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Gets the maximum price of a product request
     * 
     * @return the maximum price 
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the maximum price of a product request
     * 
     * @param maxPrice the maximum price to set
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Gets a set of merchants that a product request will be sent
     * 
     * @return a set of merchants
     */
    public Set<Merchant> getMerchants() {
        return merchants;
    }
    
    /**
     * Adds the given merchant to the recipients of a product request.
     * Does nothing if the merchant is already present.
     * 
     * @param merchant the merchant to be added
     */
    public void addMerchant(Merchant merchant) {
        if (merchant != null) {
            this.merchants.add(merchant);
        }
    }
    
    /**
     * Removes the given merchant from the recipients of a product request.
     * Does nothing if the merchant is not present.
     * 
     * @param merchant the merchant to be removed 
     */
    public void removeMerchant(Merchant merchant) {
        if (merchant != null) {
            this.merchants.remove(merchant);
        }
    }
    
    /**
     * A string representation of a product request used for logging
     * 
     * @return the string representation 
     */
    @Override
    public String toString() {
        StringBuilder requestStrBuilder = new StringBuilder("ProductRequest{");
        requestStrBuilder.append("id=").append(id);
        requestStrBuilder.append("}");
        
        return requestStrBuilder.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.searchTerm);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.minPrice) ^ (Double.doubleToLongBits(this.minPrice) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.maxPrice) ^ (Double.doubleToLongBits(this.maxPrice) >>> 32));
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
        final ProductRequest other = (ProductRequest) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.searchTerm, other.searchTerm)) {
            return false;
        }
        if (Double.doubleToLongBits(this.minPrice) != Double.doubleToLongBits(other.minPrice)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxPrice) != Double.doubleToLongBits(other.maxPrice)) {
            return false;
        }
        return true;
    }
}
