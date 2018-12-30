package com.company.producthub.entities;

import java.util.Objects;

/**
 * Represents a product of a merchant that controlled through the product hub.
 * A product is sold by a merchant at a price.
 * 
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public class Product {
    
    private String id;
    private String name;
    private String description;
    private Merchant merchant;
    private double price;

    /**
     * Creates an empty product
     */
    public Product() {
    }

    /**
     * Gets the unique id of a product
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of a product
     * 
     * @param id the id to set 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of a product
     * 
     * @return the name 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a product
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of a product
     * 
     * @return the description 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of a product
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the merchant that sells a product 
     * 
     * @return the merchant 
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * Sets the merchant that sells a product
     * 
     * @param merchant the merchant to set 
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * Gets the price of a product
     * 
     * @return the price 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of a product
     * 
     * @param price the price to set 
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.merchant);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.merchant, other.merchant)) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        return true;
    }
}
