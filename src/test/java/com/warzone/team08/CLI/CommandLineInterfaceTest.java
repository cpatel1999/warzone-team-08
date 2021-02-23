package com.warzone.team08.CLI;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.layouts.UserCommandLayout;
import com.warzone.team08.CLI.models.UserCommand;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

/**
 * The class to test the interpreted user command and its specific function call with arguments if any.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandLineInterfaceTest {
    private UserCommand d_userCommand;

    /**
     * Sets the application context
     */
    @BeforeClass
    public static void beforeClass() {
        Application l_application = new Application();
        l_application.handleApplicationStartup();
    }

    @Before
    public void before() {
        MapEditorEngine.getInstance().initialise();

        d_userCommand = new UserCommand(UserCommandLayout.matchAndGetUserCommand("editcontinent"));

        // When user enters editcontinent -add continentID 12
        d_userCommand.pushUserArgument("add",
                Arrays.asList("continentID", "12"));

        Application l_application = new Application();
        l_application.handleApplicationStartup();
    }

    /**
     * Tests the interpreted user command and calls the method of the specific class
     */
    @Test(expected = Test.None.class /* no exception expected */)
    public void testTakeAction() {
        // If the method call completes without any raised exception, then the call was successful
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.d_thread.start();
        l_commandLineInterface.takeAction(d_userCommand);
    }
}
