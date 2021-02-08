package com.warzone.team08.CLI;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.enums.states.UserInteractionState;
import com.warzone.team08.CLI.constants.layouts.UserClassLayout;
import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.mappers.UserCommandMapper;
import com.warzone.team08.CLI.models.UserCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * This class represents the command line user interface where:
 * User asked to enter the text, interpret the text, and take action accordingly.
 * </pre>
 * The motivation behind this is to let different interfaces use with the same instance of Virtual Machine.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandLineInterface implements Runnable {
    /**
     * Interprets user input text and converts it into the form which can be understood
     */
    private static UserCommandMapper d_userCommandMapper;

    /**
     * Keeps track of user interaction
     */
    private UserInteractionState d_interactionState = UserInteractionState.WAIT;

    /**
     * Gets the state of user interaction
     *
     * @return Value of the state of user interaction
     */
    public UserInteractionState getInteractionState() {
        return d_interactionState;
    }

    /**
     * Sets the state of user interaction whether:
     * 1. the program is waiting for user to enter the input
     * 2. User is waiting for the execution to finish
     *
     * @param p_interactionState the state of user interaction
     */
    public void setInteractionState(UserInteractionState p_interactionState) {
        this.d_interactionState = p_interactionState;
    }

    /**
     * Represents the created thread of this class implementing Runnable interface
     */
    public final Thread d_thread;

    public CommandLineInterface() {
        d_thread = new Thread(this);
        d_userCommandMapper = new UserCommandMapper();
    }

    /**
     * Waits for user input from command line interface
     *
     * @return Value of user text command
     * @throws IOException If any interruption occurs while waiting for user
     */
    private String waitForUserInput() throws IOException {
        // Enter data using BufferReader
        BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        return l_bufferedReader.readLine();
    }

    public void run() {
        while (Application.isRunning()) {
            try {
                if (this.getInteractionState() == UserInteractionState.WAIT) {
                    try {
                        // Takes user input and interprets it for further processing
                        UserCommand l_userCommand = d_userCommandMapper.toUserCommand(this.waitForUserInput());
                        // Takes action according to command instructions.
                        this.takeAction(l_userCommand);
                    } catch (IOException p_e) {
                        p_e.printStackTrace();
                    }
                }
            } catch (InvalidArgumentException | InvalidCommandException e) {
                // Show exception message
                // In Graphical User Interface, we can show different modals respective to the exception.
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Calls the suggested function of the mapped class.
     * Uses the mappings of the command text to the class name.
     * Here, the suggested function is the same as the argument key provided by the user, and its values are the argument to be passed to the function call.
     * (Mappings for this can be created if the both are not the same?)
     *
     * @param p_userCommand Value of the object (instance) which is equivalent to the user text input
     * @throws InvalidArgumentException Raised if the command not found
     * @throws InvalidCommandException  Raised if the argument key(s) not found or its value(s) are not provided.
     */
    public void takeAction(UserCommand p_userCommand) throws InvalidArgumentException, InvalidCommandException {
        try {
            // Gets the mapped class of the command and calls its function; With arguments, if any.
            Class<?> l_class = Class.forName(UserClassLayout.matchAndGetClassName(p_userCommand.getHeadCommand()));
            Object l_object = l_class.getDeclaredConstructor().newInstance();

            // If the command does not have any argument keys
            if (p_userCommand.getUserArguments().size() == 0) {
                // Call the class constructor in this case
                // This type of commands only represents the data
                Constructor<?> l_constructor = l_object.getClass().getConstructor();
                l_constructor.newInstance();
            } else {
                // Iterate over the user arguments
                for (Map.Entry<String, List<String>> entry : p_userCommand.getUserArguments().entrySet()) {
                    String p_argKey = entry.getKey();
                    List<String> p_argValues = entry.getValue();
                    Method l_methodReference;

                    // If the argument key does not have any value
                    if (p_argValues.size() == 0) {
                        l_methodReference = l_object.getClass().getMethod(p_argKey);
                        l_methodReference.invoke(l_object);
                    } else {
                        // Create two arrays:
                        // 1. For type of the value
                        // 2. For the values
                        Class<?>[] l_valueTypes = new Class[p_argValues.size()];
                        Object[] l_values = p_argValues.toArray();
                        for (int l_argIndex = 0; l_argIndex < p_argValues.size(); l_argIndex++) {
                            l_valueTypes[l_argIndex] = String.class;
                        }
                        // Get the reference and call the method with arguments
                        l_methodReference = l_object.getClass().getMethod(p_argKey, l_valueTypes);
                        l_methodReference.invoke(l_object, l_values);
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException p_e) {
            throw new InvalidCommandException("Command not found!");
        } catch (NoSuchMethodException | InvocationTargetException p_e) {
            throw new InvalidArgumentException("Unrecognized argument and/or its values");
        }
    }
}
