package com.warzone.team08.VM.logger;

import com.warzone.team08.VM.exceptions.InvalidInputException;
import com.warzone.team08.VM.exceptions.ResourceNotFoundException;

import java.io.IOException;

/**
 * This interface defines the methods to implement the Observable.
 *
 * @author MILESH
 */
public interface Observable {
    void attach(Observer o);

    void detach(Observer o);

    void notifyObservers(Observable p_o) throws ResourceNotFoundException, IOException, InvalidInputException;
}
