package org.processmining.plugins.workshop.helloworld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.placebasedlpmdiscovery.model.Place;
import org.processmining.placebasedlpmdiscovery.model.Transition;
import org.processmining.placebasedlpmdiscovery.model.serializable.PlaceSet;


public class FileToPlaceSet {
	
    @Plugin(
            name = "Pligin to generate PlaseSet from txt file", 
            parameterLabels = {}, 
            returnLabels = { "Set of places" }, 
            returnTypes = { PlaceSet.class }, 
            userAccessible = true, 
            help = "Produces the string: 'Hello world'"
    )
    @UITopiaVariant(
            affiliation = "RWTH", 
            author = "Narek Gevorgyan", 
            email = "narek.gevorgyan@rwth-aachen.de"
    )
    public static PlaceSet RandomGeneratorDiscovery(PluginContext context) throws IOException {
    	
    	Set<Place> places = new HashSet<Place>();
    	
    	FileReader f = new FileReader("/Users/narekgevorgyan/Desktop/test.txt");
    	BufferedReader reader = new BufferedReader(f);
		String line = reader.readLine();

		while (line != null) {
        	Place newPlace = new Place();

			String[] result = line.replace("(", "").replace(")", "").replace(" ", "").split("\\|");
			
			String inputString = result[0];
        	for (int i = 0; i < inputString.length(); i++) {
        		newPlace.addInputTransition(new Transition(String.valueOf(inputString.charAt(i)), false));
        	}
        	
			String outputString = result[1];
        	for (int i = 0; i < outputString.length(); i++) {
        		newPlace.addOutputTransition(new Transition(String.valueOf(outputString.charAt(i)), false));
        	}
			
			places.add(newPlace);
			
			// read next line
			line = reader.readLine();
		}

		reader.close();
    
    	
    	return new PlaceSet(places);//activities.toString();
    }


}



