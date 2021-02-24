package com.warzone.team08.VM.responses;

import java.util.List;

/**
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandResponse {
    /**
     * Represents the user command
     */
    private String d_headCommand;

    /**
     * Value(s) of the head of the command if any
     */
    private List<String> d_commandValues;

    /**
     * Gets the head of command for this user command
     *
     * @return head of the command
     */
    public String getHeadCommand() {
        return d_headCommand;
    }

    /**
     * Sets the head of command for this user command
     *
     * @param p_headCommand head of the command
     */
    public void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }

    /**
     * Gets the values of the head of the command if any.
     *
     * @return Values of the head of the command if any.
     */
    public List<String> getCommandValues() {
        return d_commandValues;
    }

    /**
     * Sets new values for the command.
     *
     * @param d_commandValues New value for the command.
     */
    public void setCommandValues(List<String> d_commandValues) {
        this.d_commandValues = d_commandValues;
    }
}