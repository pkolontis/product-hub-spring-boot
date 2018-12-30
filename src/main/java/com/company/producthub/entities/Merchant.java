package com.company.producthub.entities;

import java.util.Objects;

/**
 * Represents a merchant that sells products through the product hub.
 * A merchant can receive product requests from the product hub
 * by calling the api given.
 * 
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
public class Merchant {
    
    private String id;
    private String name;
    private String apiUrl;

    /**
     * Creates an empty merchant
     */
    public Merchant() {
    }

    /**
     * Creates a merchant by setting the api url
     * 
     * @param apiUrl the api url to set 
     */
    public Merchant(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Gets the unique id of a merchant
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of a merchant
     * 
     * @param id the id to set 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of a merchant
     * 
     * @return the name 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a merchant
     * 
     * @param name the name to set 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the api url of a merchant
     * to send a product request
     * 
     * @return the api url 
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Sets the api url of a merchant
     * to send a product request
     * 
     * @param apiUrl the api url to set
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.apiUrl);
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
        final Merchant other = (Merchant) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.apiUrl, other.apiUrl)) {
            return false;
        }
        return true;
    }
}
