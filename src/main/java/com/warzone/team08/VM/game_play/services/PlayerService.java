package com.warzone.team08.VM.game_play.services;

import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.game_play.GamePlayEngine;
import com.warzone.team08.VM.repositories.PlayerRepository;

/**
 * This class handles `gameplayer` user command to add and/or remove game player from the game.
 *
 * @author Brijesh Lakkad
 * @author MILESH
 * @version 1.0
 */
public class PlayerService {

    /**
     * Engine to store and retrieve map data.
     */
    private final GamePlayEngine d_gamePlayEngine;

    /**
     * Player repository.
     */
    private PlayerRepository d_playerRepository;

    public PlayerService() {
        d_gamePlayEngine = GamePlayEngine.getInstance();
    }

    /**
     * Adds the player to the list stored at Game Play engine.
     *
     * @param p_playerName Value of the player name.
     * @return Value of response of the request.
     * @throws InvalidInputException Throws if processing the player creation.
     */
    public String add(String p_playerName) throws InvalidInputException {
        if(!existByPlayerName(p_playerName))
        {
            try {
                Player l_player = new Player();
                l_player.setName(p_playerName);
                d_gamePlayEngine.addPlayer(l_player);
                return String.format("%s player added!", p_playerName);
            } catch (Exception e) {
                throw new InvalidInputException("Player name is not valid");
            }
        }
        else
        {
            throw new InvalidInputException("Player name already exists....Please provide different name.");
        }

    }

    /**
     * Checks whether the player having same name already exists or not.
     *
     * @param p_playerName player name
     * @return true if player with same name already exists in the list of joined players, otherwise false.
     */
    public boolean existByPlayerName(String p_playerName)
    {
        return GamePlayEngine.getInstance().getPlayerList().stream().filter(p_player ->
                p_player.getName().equals(p_playerName)
        ).count() == 1;
    }

    /**
     * Removes the player from the list using the name.
     *
     * @param p_playerName Value of the continent name.
     * @return Value of response of the request.
     * @throws EntityNotFoundException If the player with provided name not found.
     */
    public String remove(String p_playerName) throws EntityNotFoundException {
        // We can check if the continent exists before filtering?
        // Filters the continent list using the continent name
        Player l_player = d_playerRepository.findByPlayerName(p_playerName);
        d_gamePlayEngine.removePlayer(l_player);
        return String.format("%s player removed!", p_playerName);
    }
}
