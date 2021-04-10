package com.warzone.team08.VM.entities;

import com.warzone.team08.VM.constants.enums.CardType;
import com.warzone.team08.VM.constants.enums.StrategyType;
import com.warzone.team08.VM.constants.interfaces.Card;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.strategy.HumanStrategy;
import com.warzone.team08.VM.entities.strategy.PlayerStrategy;
import com.warzone.team08.VM.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Player {
    /**
     * Represents the unique name of each player.
     */
    private String d_name;
    /**
     * List of orders issued by the player.
     */
    private final List<Order> d_orders = new ArrayList<>();
    /**
     * List of orders executed by the <code>GameEngine</code>.
     */
    private final List<Order> d_executedOrders = new ArrayList<>();
    /**
     * List of cards owned by the player.
     */
    private final List<Card> d_cards = new ArrayList<>();
    private List<Country> d_assignedCountries = new ArrayList<>();
    private int d_reinforcementsCount = 0;
    private int d_remainingReinforcementCount = 0;
    private int d_assignedCountryCount = 0;
    private final List<Player> d_negotiatePlayer = new ArrayList<>();
    private PlayerStrategy d_playerStrategy;
    private boolean d_isDone = false;

    /**
     * Default constructor. This constructor should not be used to create an instance of <code>Player</code>.
     * <p>
     * TODO remove this constructor from everywhere. Use Player(StrategyType p_strategyType).
     */
    @Deprecated
    public Player() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates <code>Player</code> using the decided strategy.
     */
    public Player(StrategyType p_strategyType) {
        this.setPlayerStrategyUsingType(p_strategyType);
    }

    /**
     * Getter method for reinforcement armies.
     *
     * @return reinforcement armies.
     */
    public int getReinforcementCount() {
        return d_reinforcementsCount;
    }

    /**
     * Setter method to assign reinforce armies.
     *
     * @param p_reinforcementsCount reinforcement armies.
     */
    public void setReinforcementCount(int p_reinforcementsCount) {
        d_reinforcementsCount = p_reinforcementsCount;
        // Sets the count for remaining number of reinforcements as well.
        d_remainingReinforcementCount = p_reinforcementsCount;
    }

    /**
     * Getter method for player name.
     *
     * @return player name.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Setter method for player name.
     *
     * @param p_name player name.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Setter method to assign countries.
     *
     * @return list of assigned countries.
     */
    public List<Country> getAssignedCountries() {
        return d_assignedCountries;
    }

    /**
     * Setter method to assign countries.
     *
     * @param p_assignedCountries list of assigned countries.
     */
    public void setAssignedCountries(List<Country> p_assignedCountries) {
        d_assignedCountries = p_assignedCountries;
    }

    /**
     * Adds assigned country to the list.
     *
     * @param p_assignedCountry Value of assigned countries.
     */
    public void addAssignedCountries(Country p_assignedCountry) {
        d_assignedCountries.add(p_assignedCountry);
    }

    /**
     * Removes the country from the list of assigned countries.
     *
     * @param p_county Country object.
     */
    public void removeCountry(Country p_county) {
        d_assignedCountries.remove(p_county);
    }

    /**
     * Gets the number of assigned countries for this player.
     *
     * @return Value of the count.
     */
    public int getAssignedCountryCount() {
        return d_assignedCountryCount;
    }

    /**
     * Sets the number of countries that will be assigned to this player.
     *
     * @param p_assignedCountryCount Value of the count to be set.
     */
    public void setAssignedCountryCount(int p_assignedCountryCount) {
        d_assignedCountryCount = p_assignedCountryCount;
    }

    /**
     * Returns the list of cards owned by the player.
     *
     * @return List of cards owned by the player.
     */
    public List<Card> getCards() {
        return d_cards;
    }

    /**
     * Returns the list of mapping between the number of card-type the player is having and the card-type.
     *
     * @return Value of the list of mapping.
     */
    public Map<CardType, Integer> getMapOfCardTypeAndNumber() {
        Map<CardType, Integer> l_cardTypeIntegerMap = new HashMap<>();
        for (Card l_card : getCards()) {
            CardType l_cardType = l_card.getType();
            if (l_cardTypeIntegerMap.containsKey(l_cardType)) {
                l_cardTypeIntegerMap.replace(l_cardType, l_cardTypeIntegerMap.get(l_cardType) + 1);
            } else {
                l_cardTypeIntegerMap.put(l_cardType, 1);
            }
        }
        return l_cardTypeIntegerMap;
    }

    /**
     * Adds the order to the list of player's orders.
     *
     * @param p_order Order to be added.
     */
    public void addOrder(Order p_order) {
        d_orders.add(p_order);
    }

    /**
     * Gets the number of remaining reinforcements.
     *
     * @return Total number of remaining reinforcements.
     */
    public int getRemainingReinforcementCount() {
        return d_remainingReinforcementCount;
    }

    /**
     * Sets the remaining reinforcement army
     *
     * @param p_remainingReinforcementCount reinforcement count
     */
    public void setRemainingReinforcementCount(int p_remainingReinforcementCount) {
        d_remainingReinforcementCount = p_remainingReinforcementCount;
    }

    /**
     * Gets order from the user and stores the order for the player.
     *
     * @return True if the player doesn't want to being asked to issue order.
     * @throws InvalidCommandException  If there is an error while preprocessing the user command.
     * @throws InvalidArgumentException If the mentioned value is not of expected type.
     * @throws EntityNotFoundException  If the target country not found.
     * @throws ExecutionException       If any error while processing concurrent thread.
     * @throws InterruptedException     If scheduled thread was interrupted.
     */
    public void issueOrder() throws
            InvalidCommandException,
            EntityNotFoundException,
            ExecutionException,
            InterruptedException,
            InvalidArgumentException {
        d_isDone = false;
        d_playerStrategy.execute();
        if (this.d_playerStrategy.getType() != StrategyType.HUMAN) {
            this.doneWithOrder();
        }
    }

    /**
     * Adds the order to the list of player's executed orders.
     *
     * @param p_executedOrder Executed order to be added.
     */
    public void addExecutedOrder(Order p_executedOrder) {
        d_executedOrders.add(p_executedOrder);
    }

    /**
     * Gets the list of executed orders.
     *
     * @return Value of the list of orders.
     */
    public List<Order> getExecutedOrders() {
        return d_executedOrders;
    }

    /**
     * Checks if the player has any order for execution.
     *
     * @return Value of true if the player has one or more orders.
     */
    public boolean hasOrders() {
        return d_orders.size() > 0;
    }

    /**
     * Gets the first order from the order list and removes the order from the list before returning it.
     *
     * @return Value of order to be executed.
     * @throws OrderOutOfBoundException If the player does not have any order.
     */
    public Order nextOrder() throws OrderOutOfBoundException {
        if (this.hasOrders())
            return d_orders.remove(0);
        else {
            throw new OrderOutOfBoundException("No order left for execution.");
        }
    }

    /**
     * Gets the player order list.
     *
     * @return the list of orders.
     */
    public List<Order> getOrders() {
        return d_orders;
    }

    /**
     * Used for add player between which negotiation happend.
     *
     * @param p_player is the player with whom current player negotiate.
     */
    public void addNegotiatePlayer(Player p_player) {
        d_negotiatePlayer.add(p_player);
    }

    /**
     * Used for remove player after 1 turn as diplomacy card effect ended.
     *
     * @param p_player is the player with whom current player did negotiation.
     */
    public void removeNegotiatePlayer(Player p_player) {
        d_negotiatePlayer.remove(p_player);
    }

    /**
     * Gets the negotiated player list.
     *
     * @return the list of players.
     */
    public List<Player> getFriendPlayers() {
        return d_negotiatePlayer;
    }

    /**
     * This method checks whether or not this player has not negotiated with other player.
     *
     * @param p_otherPlayer Other player.
     * @return True if the players has no peace treaty signed.
     */
    public boolean isNotNegotiation(Player p_otherPlayer) {
        return !this.getFriendPlayers().contains(p_otherPlayer);
    }

    /**
     * Checks whether this player has the provided card.
     *
     * @param p_cardType Card type.
     * @return True if player has the card; false otherwise.
     */
    public boolean hasCard(CardType p_cardType) {
        List<Card> l_filteredCards = this.d_cards.stream().filter(p_card ->
                p_card.getType() == p_cardType
        ).collect(Collectors.toList());
        return l_filteredCards.size() > 0;
    }

    /**
     * Adds the card to the list of cards owned by the player.
     *
     * @param p_card Card name.
     */
    public void addCard(Card p_card) {
        d_cards.add(p_card);
    }

    /**
     * Gets the card of specific type from this player's card list.
     *
     * @param p_cardType Card type.
     * @return True if player has the card; false otherwise.
     * @throws CardNotFoundException Card not found in the player's card list.
     */
    public Card getCard(CardType p_cardType) throws CardNotFoundException {
        List<Card> l_filteredCards = this.d_cards.stream().filter(p_card ->
                p_card.getType() == p_cardType
        ).collect(Collectors.toList());
        if (l_filteredCards.size() > 0) {
            return l_filteredCards.get(0);
        }
        throw new CardNotFoundException(String.format("Player doesn't have %s card", p_cardType.d_jsonValue));
    }

    /**
     * Removes the card of specific type from this player's card list.
     *
     * @param p_card Card to be removed.
     */
    public void removeCard(Card p_card) {
        this.d_cards.remove(p_card);
    }

    /**
     * Gets the strategy type being used by this player.
     *
     * @return Type of strategy of the player.
     */
    public StrategyType getPlayerStrategyType() {
        return d_playerStrategy.getType();
    }

    public void setPlayerStrategyUsingType(StrategyType p_strategyUsingType) {
        if (p_strategyUsingType == StrategyType.HUMAN) {
            d_playerStrategy = new HumanStrategy(this);
        }
        // TODO Deep Patel instantiate other strategies.
    }

    /**
     * Human strategy will call this method if the player is done with issuing orders.
     */
    public void doneWithOrder() {
        d_isDone = true;
    }

    /**
     * Checks if the player is done with issuing the orders.
     *
     * @return True if the player doesn't want to issue order.
     */
    public boolean isDone() {
        return d_isDone;
    }
}
