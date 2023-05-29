package org.processmining.plugins.workshop.helloworld;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.processmining.contexts.uitopia.annotations.UIExportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.placebasedlpmdiscovery.model.Place;
import org.processmining.placebasedlpmdiscovery.model.Transition;
import org.processmining.placebasedlpmdiscovery.model.serializable.PlaceSet;

@Plugin(
        name = "Export set of places into a file",
        returnLabels = {},
        returnTypes = {},
        parameterLabels = {"Set of places", "Filename"})
@UIExportPlugin(
        description = "ProM set of places (txt) files",
        extension = "txt")
public class PlaceSetExport {

    @PluginVariant(variantLabel = "Export set of places into a file", requiredParameterLabels = {0, 1})
    public void export(PluginContext context, PlaceSet placeSet, File file) {
        try {

        	FileWriter f = new FileWriter(file);
        	//PrintWriter out = new PrintWriter(f);
        	
            for (Place place : placeSet.getElements()) {
            	List<Transition> inputTransition = new ArrayList<>(place.getInputTransitions());
            	String inputString = inputTransition.get(0).getLabel();
            	for (int i=1; i<inputTransition.size(); i++) {
            		inputString += '#';
            		inputString += inputTransition.get(i).getLabel();
            		
            	}

            	List<Transition> outputTransition = new ArrayList<>(place.getOutputTransitions());
            	String outputString = outputTransition.get(0).getLabel() ;
            	for (int i=1; i<outputTransition.size(); i++) {
            		outputString += '#';
            		outputString += outputTransition.get(i).getLabel();
            		
            	}
            	
            	//f.write(String.join("#", place.getInputTransitions().)   place.getShortString() + System.lineSeparator());
            	f.write(inputString + '|' + outputString + System.lineSeparator());
            }
            
            //out.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
