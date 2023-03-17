package org.processmining.plugins.workshop.helloworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.placebasedlpmdiscovery.model.Place;
import org.processmining.placebasedlpmdiscovery.model.Transition;
import org.processmining.placebasedlpmdiscovery.model.serializable.PlaceSet;


public class RandomGenerator {
        @Plugin(
                name = "Random Place Generator Plugin", 
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
        public static PlaceSet RandomGeneratorDiscovery(PluginContext context, XLog log) {//, XEventClassifier classifier) {
        	Set<String> activities = new HashSet<String>();
        			
        	for (XTrace trace : log) {
    			if (!trace.isEmpty()) {
    				for (int i = 0; i < trace.size(); i++) {
    					activities.add(trace.get(i).getAttributes().values().toArray()[0].toString());
    				}
    			}
    			
    		}
        	
        	int numberOfActivities = activities.size();
        	int numberOfPlaces = 3;
        	Set<Place> places = new HashSet<Place>();
        	
        	for (int p = 0; p < numberOfPlaces; p++) {
        	
	        	Random rand = new Random();
	
	        	int numberOfInputActivities = rand.nextInt(5);
	        	int numberOfOutputActivities = rand.nextInt(5);
	        	
	        	List<String> inputActivities = new ArrayList<String>(activities);
	            Collections.shuffle(inputActivities);
	
	        	List<String> outputActivities = new ArrayList<String>(activities);
	            Collections.shuffle(outputActivities);
	            
	            
	        	Place newPlace = new Place();
	        	
	        	for (int i = 0; i < numberOfInputActivities; i++) {
	        		newPlace.addInputTransition(new Transition(inputActivities.get(i), false));
	        		
	        		//inputActivitiesSet.add(new Transition(inputActivities.get(i), false));
	        	}
	
	        	for (int i = 0; i < numberOfOutputActivities; i++) { 
	        		newPlace.addOutputTransition(new Transition(outputActivities.get(i), false));
	        		
	        		//outputActivitiesSet.add(new Transition(outputActivities.get(i), false));
	        	}
	        	
	        	places.add(newPlace);
        	}
        	

        	
        	return new PlaceSet(places);//activities.toString();
        }
}
