package com.warzone.team08.CLI.layouts;

import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.states.GameState;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.layouts.classes.CommonClassLayout;
import com.warzone.team08.CLI.layouts.classes.GamePlayClassLayout;
import com.warzone.team08.CLI.layouts.classes.MapEditorClassLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * This class maps the class-layout classes to their game state. As UserCommandLayout class, this class also can be used
 * without creating any instance.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class UserClassLayout {
    /**
     * The list of all classes across each GameState (A state of the game).
     */
    private static Map<GameState, ClassLayout> d_GameStateListMap = new HashMap<>();

    /**
     * The object which has its user commands that can be entered during any <code>GAME_STATE</code>.
     */
    private static final CommonClassLayout COMMON_CLASS_LAYOUT = new CommonClassLayout();

    /**
     * The object which has its user commands for <code>MAP_EDITOR</code> game state.
     */
    private static final MapEditorClassLayout MAP_EDITOR_LAYOUT = new MapEditorClassLayout();

    /**
     * The object which has its user commands for <code>GAME_PLAY</code> game state.
     */
    private static final GamePlayClassLayout GAME_PLAY_CLASS_LAYOUT = new GamePlayClassLayout();

    /**
     * Stores the commands according to the game state
     */
    static {
        d_GameStateListMap.put(
                GameState.MAP_EDITOR,
                UserClassLayout.MAP_EDITOR_LAYOUT
        );

        d_GameStateListMap.put(
                GameState.GAME_PLAY,
                UserClassLayout.GAME_PLAY_CLASS_LAYOUT
        );
    }

    /**
     * Gets matched class name with the provided command.
     *
     * @param p_headOfCommand Value of the head of the command for which class name has to locate.
     * @return Value of the class name which matched with <code>p_headOfCommand</code>
     * @throws InvalidCommandException If no command had found matching the provided head of the command.
     */
    public static String matchAndGetClassName(String p_headOfCommand) throws InvalidCommandException {
        try {
            // Gets the class name for the command using the game state.
            // If the command exists in COMMON_CLASS_LAYOUT, return that class name from that.
            return COMMON_CLASS_LAYOUT.getMappings().containsKey(p_headOfCommand) ?
                    COMMON_CLASS_LAYOUT.getMappings().get(p_headOfCommand) :
                    d_GameStateListMap.get(Application.getGameState()).getMappings().get(p_headOfCommand);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidCommandException("Unrecognized command!");
        }
    }

    /**
     * Gets the instance that has the mappings between command list and its Java class for the <code>MAP_EDITOR</code>
     * game state
     *
     * @return Value of instance having the list of the mappings.
     */
    private static MapEditorClassLayout getMapEditorLayout() {
        return UserClassLayout.MAP_EDITOR_LAYOUT;
    }

    /**
     * Gets the instance that has the mappings between command list and its Java class for the <code>GAME_PLAY</code>
     * game state
     *
     * @return Value of instance having the list of the mappings.
     */
    private static GamePlayClassLayout getGamePlayLayout() {
        return UserClassLayout.GAME_PLAY_CLASS_LAYOUT;
    }
}
