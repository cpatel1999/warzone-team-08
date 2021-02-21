package com.warzone.team08.VM.services;

import com.warzone.team08.VM.engines.MapEditorEngine;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.repositories.ContinentRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This service handles `editcontinent` user command to add and/or remove continent from the map.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class ContinentService {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;
    private final ContinentRepository d_continentRepository;

    public ContinentService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_continentRepository = new ContinentRepository();
    }

    /**
     * Adds the continent to the list stored at map editor engine.
     *
     * @param p_continentName Value of the continent name.
     * @param p_countryValue  Value of the control value of the continent.
     * @throws InvalidInputException Throws if the control value can not be parsed into Integer data type.
     */
    public String add(String p_continentName, String p_countryValue) throws InvalidInputException {
        try {
            int l_parsedControlValue = Integer.parseInt(p_countryValue);
            Continent l_continent = new Continent();
            l_continent.setContinentName(p_continentName);
            l_continent.setContinentControlValue(l_parsedControlValue);
            d_mapEditorEngine.addContinent(l_continent);
            return String.format("%s continent added!", p_continentName);
        } catch (Exception e) {
            throw new InvalidInputException("Continent value is not valid");
        }
    }

    /**
     * Removes the continent from the list using the name.
     *
     * @param p_continentName Value of the continent name.
     * @throws EntityNotFoundException Throws if continent is not present.
     */
    public String remove(String p_continentName) throws EntityNotFoundException {
        Continent l_continent = d_continentRepository.findFirstByContinentName(p_continentName);
        // We can check if the continent exists before filtering?
        // Filters the continent list using the continent name
        ArrayList<Continent> l_filteredContinentList = (ArrayList<Continent>) d_mapEditorEngine.getContinentList().stream()
                .filter(p_continent -> p_continent.equals(l_continent)
                ).collect(Collectors.toList());

        d_mapEditorEngine.setContinentList(l_filteredContinentList);
        return String.format("%s continent removed!", p_continentName);
    }
}
