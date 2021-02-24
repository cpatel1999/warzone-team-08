package com.warzone.team08.CLI.layouts;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.states.GameState;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.layouts.commands.CommonCommandLayout;
import com.warzone.team08.CLI.layouts.commands.GamePlayCommandLayout;
import com.warzone.team08.CLI.layouts.commands.MapEditorCommandLayout;
import com.warzone.team08.CLI.models.PredefinedUserCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class maps the command-layout classes to their game state. The class can be used without creating any instance.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserCommandLayout {
    /**
     * The list of all classes across each GameState (A state of the game).
     */
    private static Map<GameState, CommandLayout> d_gameStateListMap = new HashMap<>();

    /**
     * The object which has its user commands for <code>GAME_PLAY</code> game state.
     */
    private static final CommonCommandLayout COMMON_LAYOUT = new CommonCommandLayout();

    /**
     * The object which has its user commands for <code>Map_Editor</code> game state.
     */
    private static final MapEditorCommandLayout MAP_EDITOR_LAYOUT = new MapEditorCommandLayout();

    /**
     * The object which has its user commands for <code>GAME_PLAY</code> game state.
     */
    private static final GamePlayCommandLayout GAME_PLAY_LAYOUT = new GamePlayCommandLayout();

    /*
     * Stores the commands according to the game state
     */
    static {
        d_gameStateListMap.put(
                GameState.MAP_EDITOR,
                UserCommandLayout.MAP_EDITOR_LAYOUT
        );

        d_gameStateListMap.put(
                GameState.GAME_PLAY,
                UserCommandLayout.GAME_PLAY_LAYOUT
        );
    }

    /**
     * Gets matched the user command It decides the which list of predefined command using the game state Then it
     * matches the user command with the head of the command provided
     *
     * @param p_headOfCommand head of the command which needs to be matched the list of predefined commands
     * @return Value of the user command which matched with p_headOfCommand
     * @throws InvalidCommandException If no command had found matching the provided head of the command.
     */
    public static PredefinedUserCommand matchAndGetUserCommand(String p_headOfCommand) throws InvalidCommandException {
        // Gets the list of command from the layout, and then it is being streamed over to filter the list
        List<PredefinedUserCommand> l_globalCommandList = UserCommandLayout.findByHeadOfCommand(COMMON_LAYOUT, p_headOfCommand);
        return l_globalCommandList.size() > 0 ?
                l_globalCommandList.get(0) :
                UserCommandLayout.findFirstByHeadOfCommand(d_gameStateListMap.get(Application.getGameState()),
                        p_headOfCommand);
    }

    /**
     * Finds the list of matched <code>PredefinedUserCommand</code> using the head of the command
     * <code>headOfCommand</code> data member.
     *
     * @param p_commandLayout Represents the <code>CommandLayout</code> sub class.
     * @param p_headOfCommand Value of head command that need to be used to find <code>PredefinedUserCommand</code>.
     * @return Value of found <code>PredefinedUserCommand</code>.
     */
    private static List<PredefinedUserCommand> findByHeadOfCommand(CommandLayout p_commandLayout, String p_headOfCommand) {
        return p_commandLayout.getUserCommands()
                .stream().filter((userCommand) ->
                        userCommand.getHeadCommand().equals(p_headOfCommand)
                ).collect(Collectors.toList());

    }

    /**
     * Finds the first matched <code>PredefinedUserCommand</code> using the head of the command
     * <code>headOfCommand</code> data member.
     *
     * @param p_commandLayout Represents the <code>CommandLayout</code> sub class.
     * @param p_headOfCommand Value of head command that need to be used to find <code>PredefinedUserCommand</code>.
     * @return Value of found <code>PredefinedUserCommand</code>.
     * @throws InvalidCommandException If command not found in this <code>GAME_STATE</code>.
     */
    private static PredefinedUserCommand findFirstByHeadOfCommand(CommandLayout p_commandLayout, String p_headOfCommand) {
        try {
            return UserCommandLayout.findByHeadOfCommand(p_commandLayout, p_headOfCommand).get(0);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidCommandException("Unrecognized command!");
        }
    }

    /**
     * Gets the instance which has the user command list for the <code>MAP_EDITOR</code> game state.
     *
     * @return Value of instance having the list of <code>MAP_EDITOR</code> user commands.
     */
    private static MapEditorCommandLayout getMapEditorLayout() {
        return UserCommandLayout.MAP_EDITOR_LAYOUT;
    }

    /**
     * Gets the instance which has the user command list for the <code>GAME_PLAY</code> game state.
     *
     * @return Value of instance having the list of <code>GAME_PLAY</code> user commands.
     */
    private static GamePlayCommandLayout getGamePlayLayout() {
        return UserCommandLayout.GAME_PLAY_LAYOUT;
    }
}
