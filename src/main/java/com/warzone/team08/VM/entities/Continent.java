package com.warzone.team08.VM.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Continent {
    /**
     * Auto-generated ID of the continent.
     */
    private Integer d_continentId;
    private String d_continentName;
    private Integer d_continentControlValue;
    private String d_continentColor;
    private Set<Country> d_countrySet;

    /**
     * Used to keep the track of unique IDs for the continent.
     */
    public static int serialNumber = 0;

    public Continent() {
        this.d_continentId = ++serialNumber;
        d_countrySet = new HashSet<>();
    }

    /**
     * Get the id of the continent which is the index where the continent is located.
     *
     * @return Value of the continent ID.
     */
    public Integer getContinentId() {
        return d_continentId;
    }

    /**
     * Sets the value of continent name.
     *
     * @param p_continentName Name of the continent.
     */
    public void setContinentName(String p_continentName) {
        d_continentName = p_continentName;
    }

    /**
     * Gets continent name.
     *
     * @return continent name.
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Sets the continent control value.
     *
     * @param p_continentControlValue Value of the continent control.
     */
    public void setContinentControlValue(int p_continentControlValue) {
        d_continentControlValue = p_continentControlValue;
    }

    /**
     * Gets the continent control value.
     *
     * @return Value of the continent control.
     */
    public int getContinentControlValue() {
        return d_continentControlValue;
    }

    /**
     * Gets the countries of this continent.
     *
     * @return Value of the list of countries
     */
    public Set<Country> getCountrySet() {
        return d_countrySet;
    }

    /**
     * Sets the country list for this continent.
     *
     * @param p_countryList Value of the list.
     */
    public void setCountrySet(Set<Country> p_countryList) {
        d_countrySet = p_countryList;
    }

    /**
     * Adds a single country to the list of countries belonging to this continent.
     *
     * @param p_country Value of the country to be added.
     */
    public void addCountry(Country p_country) {
        // Set will not have any duplicate elements.
        d_countrySet.add(p_country);
    }

    /**
     * Removes country from the list.
     *
     * @param p_country Value of the country to be removed.
     */
    public void removeCountry(Country p_country) {
        // Set will not have any duplicate elements.
        d_countrySet.remove(p_country);
    }

    /**
     * Checks if both objects are the same using continent id of the object.
     *
     * @param l_p_o Value of the second element to be checked with.
     * @return True if the both are same.
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        Continent l_that = (Continent) l_p_o;
        return d_continentId.equals(l_that.d_continentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_continentId);
    }
}
