package com.warzone.team08.VM.map_editor.services;

import com.warzone.team08.VM.constants.interfaces.SingleCommand;
import com.warzone.team08.VM.entities.Continent;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.VMException;
import com.warzone.team08.VM.logger.LogEntryBuffer;
import com.warzone.team08.VM.map_editor.MapEditorEngine;
import com.warzone.team08.VM.utils.FileUtil;
import com.warzone.team08.VM.utils.PathResolverUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

/**
 * This class is to save the edited conquest Map File.
 * <p>
 * The service handles `savemap` user command.
 *
 * @author CHARIT
 */
public class SaveConquestMapService implements SingleCommand {
    /**
     * Engine to store and retrieve map data.
     */
    private final MapEditorEngine d_mapEditorEngine;
    private final LogEntryBuffer d_logEntryBuffer;

    /**
     * Fetches the singleton instance of <code>MapEditorEngine</code>
     */
    public SaveConquestMapService() {
        d_mapEditorEngine = MapEditorEngine.getInstance();
        d_logEntryBuffer = LogEntryBuffer.getLogger();
    }

    /**
     * Reads from map editor engine and saves the data to file.
     *
     * @param p_fileObject File path to create if not exists and write into files.
     * @return Value of response of the request.
     * @throws InvalidInputException Throws if the file write operation was not successful.
     */
    public String saveToFile(File p_fileObject) throws InvalidInputException {
        HashMap<String, String> l_mapDetails;

        try (Writer l_writer = new FileWriter(p_fileObject)) {
            l_writer.write("[" + "Map" + "]\n");
            l_mapDetails = d_mapEditorEngine.getMapDetails();

            for (String l_name : l_mapDetails.keySet()) {
                String l_key = l_name;
                String l_value = l_mapDetails.get(l_name);
                System.out.println(l_key + "=" + l_value);
                l_writer.write(l_key + "=" + l_value + "\n");
            }

            l_writer.write("\n[" + "Continents" + "]\n");

            for (Continent l_continents : d_mapEditorEngine.getContinentList()) {
                l_writer.write(l_continents.getContinentName() + "=" + l_continents.getContinentControlValue() + "\n");
            }

            l_writer.write("\n[" + "Territories" + "]\n");

            for (Country l_country : d_mapEditorEngine.getCountryList()) {
                String l_join = "";
                l_writer.write(l_country.getCountryName() + "," + l_country.getXCoordinate() + "," + l_country.getYCoordinate() + "," + l_country.getContinent());
                for (Country l_neighbor : l_country.getNeighbourCountries()) {
                    l_join = String.join(",", l_join, l_neighbor.getCountryName());
                }
                l_writer.write(l_join + "\n");
            }
            return "File saved successfully";
        } catch (IOException p_ioException) {
            throw new InvalidInputException("Error while saving the file!");
        }
    }

    /**
     * Takes the path of the file which user wants to save(edited file).
     *
     * @param p_commandValues Value of parameters entered by the user.
     * @return Value of string acknowledging user that the file is saved or not.
     * @throws VMException Throws if error occur during processing in VM.
     * @throws IOException Throws if error occur during IO operation.
     */
    @Override
    public String execute(List<String> p_commandValues) throws VMException, IOException {
        return saveToFile(FileUtil.retrieveFile(PathResolverUtil.resolveFilePath(p_commandValues.get(0))));
    }
}