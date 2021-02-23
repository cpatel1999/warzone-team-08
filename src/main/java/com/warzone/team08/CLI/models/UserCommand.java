package com.warzone.team08.CLI.models;

import java.util.*;

/**
 * This class represents the interpreted command from user input text.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserCommand {
    /**
     * Represents the user command
     */
    private String d_headCommand;

    /**
     * Represents the argument and its values
     */
    private final Map<String, List<String>> d_userArguments;

    /**
     * Value(s) of the head of the command if any
     */
    private List<String> d_commandValues;

    /**
     * Represents the predefined command for this command.
     */
    private PredefinedUserCommand d_predefinedUserCommand;

    public UserCommand(PredefinedUserCommand p_predefinedUserCommand) {
        setHeadCommand(p_predefinedUserCommand.getHeadCommand());
        d_predefinedUserCommand = p_predefinedUserCommand;
        // Initialise references
        d_userArguments = new HashMap<>();
        d_commandValues = new ArrayList<>();
    }

    /**
     * Gets the head of command for this user command
     *
     * @return head of the command
     */
    public String getHeadCommand() {
        return d_headCommand;
    }

    /**
     * Sets the head of command for this user command.
     * <p>
     * Only accessible with in the class which will be used at the constructor.
     *
     * @param p_headCommand head of the command
     */
    private void setHeadCommand(String p_headCommand) {
        d_headCommand = p_headCommand;
    }

    /**
     * Gets the list of argument key and its value(s)
     *
     * @return Value of the list of argument key and its value(s)
     */
    public Map<String, List<String>> getUserArguments() {
        return d_userArguments;
    }

    /**
     * Adds element to the list of user argument mappings.
     *
     * @param argKey Value of argument key.
     * @param values Value of the list of argument values.
     */
    public void pushUserArgument(String argKey, List<String> values) {
        d_userArguments.put(argKey, values);
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

    /**
     * Gets the predefined version of the user command.
     * <p>Can be used at taking action for the appropriate command.
     *
     * @return Value of predefined command.
     */
    public PredefinedUserCommand getPredefinedUserCommand() {
        return d_predefinedUserCommand;
    }

    /**
     * Checks the head of the command and its argument key only
     *
     * @param l_p_o UserCommand need to checked with
     * @return true if both objects are equal
     */
    @Override
    public boolean equals(Object l_p_o) {
        if (this == l_p_o) return true;
        if (l_p_o == null || getClass() != l_p_o.getClass()) return false;
        UserCommand l_that = (UserCommand) l_p_o;
        return Objects.equals(d_headCommand, l_that.d_headCommand) &&
                Objects.equals(d_userArguments.keySet(), l_that.d_userArguments.keySet());
    }
}
