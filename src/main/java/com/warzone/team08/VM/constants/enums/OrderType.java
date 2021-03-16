package com.warzone.team08.VM.constants.enums;

/**
 * This enum lists all the orders which player can issue during <code>issue orders</code> phase.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public enum OrderType {
    /**
     * Order of deploying the reinforcements.
     */
    deploy("deploy");

    /**
     * Variable to set enum value.
     */
    public String d_jsonValue;

    /**
     * Sets the string value of the enum.
     *
     * @param p_jsonValue Value of the enum.
     */
    private OrderType(String p_jsonValue) {
        this.d_jsonValue = p_jsonValue;
    }

    /**
     * Gets the string value of the enum
     *
     * @return Value of the enum
     */
    public String getJsonValue() {
        return d_jsonValue;
    }
}
