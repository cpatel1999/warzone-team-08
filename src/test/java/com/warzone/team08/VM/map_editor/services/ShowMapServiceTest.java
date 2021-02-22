package com.warzone.team08.VM.map_editor.services;

import com.jakewharton.fliptables.FlipTable;
import com.warzone.team08.VM.exceptions.*;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This test class tests the ShowSelectedMap class functions
 *
 * @author MILESH
 * @author Brijesh Lakkad
 */
public class ShowMapServiceTest {
    ShowMapService d_showMapService;

    /**
     * Setting up the context by loading the map file before testing the class methods.
     */
    @BeforeClass
    public static void beforeClass() throws AbsentTagException, InvalidMapException, ResourceNotFoundException, InvalidInputException, EntityNotFoundException {
        EditMapService l_editMapService = new EditMapService();
        // Re-initialise map editor engine.
        MapEditorEngine.getInstance().initialise();

        URL d_testFilePath = ShowMapServiceTest.class.getClassLoader().getResource("test_map_files/test_map.map");
        assertNotNull(d_testFilePath);
        l_editMapService.handleLoadMap(d_testFilePath.getPath());
    }

    /**
     * This method will initialise the ShowMapService object before running each test cases.
     */
    @Before
    public void before() {
        d_showMapService = new ShowMapService();
    }

    /**
     * It tests the showContinentContent method which returns the String of continent information
     */
    @Test
    public void testShowContinentCountryContentTest() {
        String[] l_header = {"Continent Name", "Control Value", "Countries"};
        String[][] l_mapMatrix = {
                {"Earth", "10", "Earth-Atlantic,Earth-SouthAmerica,Earth-SouthPole"},
                {"Venus", "8", "Venus-East,Venus-South,Venus-Southwest"},
                {"Mercury", "6", "Mercury-East,Mercury-North,Mercury-South,Mercury-West"}
        };
        String l_mapTable = FlipTable.of(l_header, l_mapMatrix);
        String l_mapData = d_showMapService.showContinentCountryContent();
        assertNotNull(l_mapData);
        assertEquals(l_mapTable, l_mapData);
    }

    @Test
    public void testShowNeighbourCountriesTest() {
        String[][] l_neighbourMatrix = {
                {"COUNTRIES", "Mercury-South", "Mercury-East", "Mercury-West", "Mercury-North", "Venus-South", "Venus-East", "Venus-Southwest", "Earth-SouthPole", "Earth-SouthAmerica", "Earth-Atlantic"},
                {"Mercury-South", "X", "X", "X", "O", "O", "O", "O", "O", "O", "O"},
                {"Mercury-East", "X", "X", "X", "X", "O", "O", "O", "O", "O", "O"},
                {"Mercury-West", "X", "X", "X", "X", "O", "O", "O", "O", "O", "O"},
                {"Mercury-North", "O", "X", "X", "X", "X", "O", "O", "O", "O", "O"},
                {"Venus-South", "O", "O", "O", "X", "X", "X", "X", "O", "O", "O"},
                {"Venus-East", "O", "O", "O", "O", "X", "X", "X", "O", "O", "O"},
                {"Venus-Southwest", "O", "O", "O", "O", "X", "X", "X", "O", "O", "O"},
                {"Earth-SouthPole", "O", "O", "X", "O", "O", "O", "O", "X", "X", "O"},
                {"Earth-SouthAmerica", "O", "O", "O", "O", "O", "O", "X", "X", "X", "X"},
                {"Earth-Atlantic", "O", "O", "O", "O", "O", "O", "O", "O", "X", "X"}

        };
        String[] l_countryCountHeader = new String[l_neighbourMatrix.length];
        for (int i = 0; i < l_countryCountHeader.length; i++) {
            l_countryCountHeader[i] = "C" + i;
        }
        String l_countryData = d_showMapService.showNeighbourCountries();
        String l_countryTable = FlipTable.of(l_countryCountHeader, l_neighbourMatrix);
        assertNotNull(l_countryData);
        assertEquals(l_countryTable, l_countryData);
    }
}