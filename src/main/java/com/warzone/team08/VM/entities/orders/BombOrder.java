package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.repositories.CountryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the operations required to be perform when the Bomb card is used. When bomb card is used then
 * it simple halves the number of armies available in the country on which the bomb operation is performed. A player can
 * perform bomb operation on the countries owned by the other players only.
 *
 * @author CHARIT
 * @version 2.0
 */
public class BombOrder implements Order {
    private final Country d_targetCountry;
    private final Player d_owner;

    /**
     * To find the country using its data members.
     */
    private final CountryRepository d_countryRepository = new CountryRepository();

    /**
     * Sets the country name and current player object.
     *
     * @param p_targetCountry Name of target country.
     * @param p_owner         Current player object.
     * @throws EntityNotFoundException Throws if the target country not found.
     */
    public BombOrder(String p_targetCountry, Player p_owner) throws EntityNotFoundException {
        // Get the target country from repository.
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        d_owner = p_owner;
    }

    /**
     * Performs actual bomb operation.
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     */
    public void execute() throws InvalidOrderException {
        Country l_country;
        List<Country> l_countryList;

        if (d_targetCountry.getOwnedBy() != d_owner) {
            if (!d_owner.getCards().contains("bomb")) {
                throw new InvalidOrderException("Bomb card is not available with the player.");
            }
        } else {
            throw new InvalidOrderException("You have selected your own country to perform bomb operation. Please select opponent player's country");
        }

        if (d_owner.isNotNegotiation(d_targetCountry.getOwnedBy())) {
            List<Country> l_neighbourCountryList = new ArrayList<>();
            l_country = d_targetCountry;
            l_countryList = d_owner.getAssignedCountries();
            for (Country l_co : l_countryList) {
                l_neighbourCountryList.addAll(l_co.getNeighbourCountries());
            }
            if (l_neighbourCountryList.contains(l_country)) {
                int l_finalArmies = l_country.getNumberOfArmies() / 2;
                l_country.setNumberOfArmies(l_finalArmies);
                //Remove card from list
                d_owner.removeCard("bomb");
            } else {
                throw new InvalidOrderException("Invalid Country Name is provided!! Country must be a neighboring country.");
            }
        }
    }

    /**
     * Returns the order type.
     *
     * @return Order type.
     */
    @Override
    public OrderType getType() {
        return OrderType.bomb;
    }
}