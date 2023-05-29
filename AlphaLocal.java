package org.processmining.plugins.workshop.helloworld;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.deckfour.uitopia.api.event.TaskListener.InteractionResult;
import org.deckfour.xes.classification.XEventClass;
import org.deckfour.xes.classification.XEventClasses;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.placebasedlpmdiscovery.model.Place;
import org.processmining.placebasedlpmdiscovery.model.Transition;
import org.processmining.placebasedlpmdiscovery.model.serializable.PlaceSet;


public class AlphaLocal {

		@Plugin(
			name = "Local Alpha Miner",
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
        public static PlaceSet AlphaLocalDiscovery(UIPluginContext context, XLog log) {
			MyParameters parameters = new MyParameters();
			MyDialog dialog = new MyDialog(parameters);
			InteractionResult result = context.showWizard("Workshop Miner", true, true, dialog);
			if (result != InteractionResult.FINISHED) {
				return null;
			}
			return AlphaLocalDiscovery(context, log, parameters);
        }
        
		
		
		@PluginVariant(variantLabel = "Your plug-in name, dialog", requiredParameterLabels = { 0, 1 })
        @UITopiaVariant(
                affiliation = "RWTH", 
                author = "Narek Gevorgyan", 
                email = "narek.gevorgyan@rwth-aachen.de"
        )
        public static PlaceSet AlphaLocalDiscovery(PluginContext context, XLog log, MyParameters params) {//, XEventClassifier classifier) {
        	// fix starttime
			long startTime = System.nanoTime();
			
        	
        	int localWindowSize = params.getLocalWindowSize();
			int choiceDepth = params.getChoiceDepth();
			
			double alpha = params.getAlpha();
			double beta = params.getBeta();
			//double groupbs = params.getGroups();
			// boolean choicePatterns = params.isChoicePattern();
			
			boolean minimalPlaces = params.isMinimalPlaces();
			
			String sequenceApproach = params.getSequenceApproach();
			String choiseApproach = params.getChoiceApproach();
			String normCoeff = params.getNormCoeff();
			

			XEventClassifier classifier = log.getClassifiers().get(0);
			XEventClasses classes = XEventClasses.deriveEventClasses(classifier, log);
            
        	Set<Place> places = new HashSet<Place>();
        	
            float eventsQuantity = 0;
            // directly follow relationships matrix
        	double[][] DFMatrix = new double[classes.size()][classes.size()];
        	// eventually follow relationships matrix
        	double[][] EFMatrix = new double[classes.size()][classes.size()];
        	// weighted eventually follow relationships matrix 
        	double[][] EFMatrixWeighted = new double[classes.size()][classes.size()];
        	// heuristic miner like relationships matrix
        	double[][] HMMatrix = new double[classes.size()][classes.size()];
        	// heuristic miner like relationships based on EF matrix
        	double[][] HMMatrixEF = new double[classes.size()][classes.size()];
        	// heuristic miner like relationships based on EF weighted matrix
        	double[][] HMMatrixWeighted = new double[classes.size()][classes.size()];
        	
        	double maxDFMatrix = 0.0;
        	double maxEFMatrix = 0.0;
        	double maxEFMatrixWeighted = 0.0;
        			
      
        	// create DF and EF matrices 
        	for (XTrace trace : log) {
    			if (!trace.isEmpty()) {
    				for (int start_ind = 0; start_ind < trace.size() - 1; start_ind++) {
    					DFAOutput localDFA = LocalDFA(trace, start_ind, localWindowSize, classes, false);
    					    					
    					eventsQuantity += localDFA.getTransitionsQuantity();
    					
    					for (Pair key: localDFA.getSkipMatrix().keySet()) {
    						if (localDFA.getDfaMatrix().get(key) != null) {
    							DFMatrix[key.index][key.value] += localDFA.getDfaMatrix().get(key);
    						}
 	            			EFMatrix[key.index][key.value] += localDFA.getSkipMatrix().get(key);
	            			EFMatrixWeighted[key.index][key.value] += localDFA.getSkipMatrixWeighted().get(key);
	            			
	            			maxDFMatrix = Math.max(maxDFMatrix, DFMatrix[key.index][key.value]);
	            			maxEFMatrix = Math.max(maxEFMatrix, EFMatrix[key.index][key.value]);
	            			maxEFMatrixWeighted = Math.max(maxEFMatrixWeighted, EFMatrixWeighted[key.index][key.value]);
    					}	
    				}
    			}
    			
    		}
        
        	
        	// create heuristic miner like matrix
        	for (int i = 0; i < EFMatrix[0].length; i++) {
        		for (int j = 0; j <= i; j++) {
        			if (i == j) {
        				HMMatrix[i][j] = DFMatrix[i][j] / (DFMatrix[i][j] + 1);
        				HMMatrixEF[i][j] = EFMatrix[i][j] / (EFMatrix[i][j] + 1);
        				HMMatrixWeighted[i][j] = EFMatrixWeighted[i][j] / (EFMatrixWeighted[i][j] + 1);
        			} else {
        				//HMMatrix[i][j] = (java.lang.Math.abs(DFMatrix[i][j]) - java.lang.Math.abs(DFMatrix[j][i])) / (java.lang.Math.abs(DFMatrix[i][j]) + java.lang.Math.abs(DFMatrix[j][i]) + 1);
        				HMMatrix[i][j] = (DFMatrix[i][j] - DFMatrix[j][i]) / (DFMatrix[i][j] + DFMatrix[j][i] + 1);
        				HMMatrix[j][i] = - HMMatrix[i][j];
        				
        				//HMMatrixEF[i][j] = (java.lang.Math.abs(EFMatrix[i][j]) - java.lang.Math.abs(EFMatrix[j][i])) / (java.lang.Math.abs(EFMatrix[i][j]) + java.lang.Math.abs(EFMatrix[j][i]) + 1);
        				HMMatrixEF[i][j] = (EFMatrix[i][j] - EFMatrix[j][i]) / (EFMatrix[i][j] + EFMatrix[j][i] + 1);
        				HMMatrixEF[j][i] = - HMMatrixEF[i][j];
        				
        				//HMMatrixWeighted[i][j] = (java.lang.Math.abs(EFMatrixWeighted[i][j]) - java.lang.Math.abs(EFMatrixWeighted[j][i])) / (java.lang.Math.abs(EFMatrixWeighted[i][j]) + java.lang.Math.abs(EFMatrixWeighted[j][i]) + 1);
        				HMMatrixWeighted[i][j] = (EFMatrixWeighted[i][j] - EFMatrixWeighted[j][i]) / (EFMatrixWeighted[i][j] + EFMatrixWeighted[j][i] + 1);
        				HMMatrixWeighted[j][i] = - HMMatrixWeighted[i][j];
        			}
        		
        		}
        	}

 ///     sequence relation parameters
        	double[][] seq_matrix = new double[classes.size()][classes.size()];
        	double divider_seq = eventsQuantity;
        	
        	if (normCoeff.equals("max")) {
        		divider_seq = 0;
        	}
        	
        	// add sequence places 
        	switch(sequenceApproach) {
                case "DF matrix":
                	if (divider_seq == 0) {
                		divider_seq = maxDFMatrix; 
                	}
                	seq_matrix = DFMatrix;
                	break;                
                case "EF matrix":
                	if (divider_seq == 0) {
                		divider_seq = maxEFMatrix; 
                	} 
                	seq_matrix = EFMatrix;
                	break;
                case "EF matrix weighted":
                	if (divider_seq == 0) {
                		divider_seq = maxEFMatrixWeighted; 
                	} 
                	seq_matrix = EFMatrixWeighted;                	
                	break;
                case "HM matrix":  
                	seq_matrix = HMMatrix;
                	divider_seq = 1.0;
                	break;
                case "HM matrix based on EF":  
                	seq_matrix = HMMatrixEF;             
                	divider_seq = 1.0;
                	break;
                case "HM matrix based on EF weighted":  
                	seq_matrix = HMMatrixWeighted;
                	divider_seq = 1.0;
                	break;
        	}
///     choice relation parameters
        	double[][] choice_matrix = new double[classes.size()][classes.size()];
        	double divider_choice = eventsQuantity;
        	
        	if (normCoeff.equals("max")) {
        		divider_choice = 0;
        	}
        	
        	// add sequence places 
        	switch(choiseApproach) {
                case "DF matrix":
                	if (divider_choice == 0) {
                		divider_choice = maxDFMatrix; 
                	}
                	choice_matrix = DFMatrix;
                	break;                
                case "EF matrix":
                	if (divider_choice == 0) {
                		divider_choice = maxEFMatrix; 
                	} 
                	choice_matrix = EFMatrix;
                	break;
                case "EF matrix weighted":
                	if (divider_choice == 0) {
                		divider_choice = maxEFMatrixWeighted; 
                	} 
                	choice_matrix = EFMatrixWeighted;                	
                	break;
                case "HM matrix":  
                	choice_matrix = HMMatrix;
                	divider_choice = 1.0;
                	break;
                case "HM matrix based on EF":  
                	choice_matrix = HMMatrixEF;             
                	divider_choice = 1.0;
                	break;
                case "HM matrix based on EF weighted":  
                	choice_matrix = HMMatrixWeighted;
                	divider_choice = 1.0;
                	break;
        	}

        	
        	Set<Pair> io = new HashSet<Pair>();
        	        	
        	for (int i = 0; i < seq_matrix[0].length; i++) {
        		for (int j = 0; j < seq_matrix[1].length; j++) {        			
        			
        			if (i != j && (seq_matrix[i][j] / divider_seq >= alpha)) {

        				for (int k = 0; k < choice_matrix[1].length; k ++) {

        					if ( k != i && k != j && (seq_matrix[i][k] / divider_seq >= alpha)) {
        						// abs(|c->b| - |b->c|)/(|c->b| + |b->c|+1) < beta
        						//boolean first_choice_constraint = minimalPlaces ? (choice_matrix[j][k] <= beta && choice_matrix[k][j] <= beta) : (java.lang.Math.abs(choice_matrix[j][k]) / divider_choice <= beta);
        						// if (java.lang.Math.abs(choice_matrix[j][k] - choice_matrix[k][j]) / divider_choice <= beta)
        						if (java.lang.Math.abs(choice_matrix[j][k]) / divider_choice <= (1 - beta)) {
        							Set<Integer> inputIndices = new HashSet<>(Arrays.asList(i));
        							Set<Integer> outputIndices = new HashSet<>(Arrays.asList(j, k));
        							
        							io.add(new Pair(i,j));
        							io.add(new Pair(i,k));
        	        				places.add(getPlace(classes, inputIndices, outputIndices));
        						}	
        					}
        					
        					if (k != i && k != j &&  seq_matrix[k][j] / divider_seq >= alpha) {
        						// abs(|c->b| - |b->c|)/(|c->b| + |b->c|+1) < beta
        						// if (java.lang.Math.abs(choice_matrix[i][k]- choice_matrix[k][i]) / divider_choice <= beta)
        						//boolean second_choice_constraint = minimalPlaces ? (choice_matrix[i][k] <= beta && choice_matrix[k][i] <= beta) : (java.lang.Math.abs(choice_matrix[i][k]- choice_matrix[k][i]) / divider_choice <= beta);
        						if (java.lang.Math.abs(choice_matrix[i][k]) / divider_choice <= (1 - beta)) {
        							Set<Integer> inputIndices = new HashSet<>(Arrays.asList(i, k));
        							Set<Integer> outputIndices = new HashSet<>(Arrays.asList(j));
        							
        							io.add(new Pair(i,j));
        							io.add(new Pair(k,j));
        	        				places.add(getPlace(classes, inputIndices, outputIndices));
        						}	
        					}
        					
        				}
        			
        			}
        		}
        	}    
 
        	for (int i = 0; i < seq_matrix[0].length; i++) {
        		for (int j = 0; j < seq_matrix[1].length; j++) {
        			boolean additional_constraint = minimalPlaces ? (seq_matrix[j][i] / divider_seq <= alpha) : true;
        			
        			if ((seq_matrix[i][j] / divider_seq > alpha) && additional_constraint &&(!minimalPlaces || !io.contains(new Pair(i,j))))  {
        				
        				Place newPlace = new Place();
        				newPlace.addInputTransition(new Transition(classes.getByIndex(i).toString(), false));
        				newPlace.addOutputTransition(new Transition(classes.getByIndex(j).toString(), false));
        				places.add(newPlace);
        			
        			}
        		}
        	}  

        	
        	long delta = System.nanoTime() - startTime;
        	System.out.println("===========================================================");
        	System.out.println("Number of places: " + places.size());
        	System.out.println("Runtime " + delta / 1e9 + "S");
        	System.out.println("===========================================================");
        	return new PlaceSet(places);
			
        }
        
		
        
       public static DFAOutput LocalDFA(XTrace trace, int startIndex, int contexSize, XEventClasses classes, boolean directlyFollow) {
    	   // derive DFA for a specific subtrace

    	   HashMap<Pair, Double> dfa = new HashMap<>();
    	   HashMap<Pair, Double> skip = new HashMap<>();
    	   HashMap<Pair, Double> skip_weighted = new HashMap<>();

    	   DFAOutput results = new DFAOutput();
    	   int maxStep = Math.min(trace.size() - startIndex - 1, contexSize - 1);
    	   for (int step = 1; step <= maxStep; step++) {
    		   XEventClass from = classes.getClassOf(trace.get(startIndex));
    		   XEventClass to = classes.getClassOf(trace.get(startIndex + step));
    		   Pair index = new Pair(from.getIndex(), to.getIndex());
    		   
    		   ///
    		   if (step == 1) {
    			   Double dfa_count = dfa.get(index);
    			   if (dfa_count == null) dfa_count = 0.0;
    			   dfa.put(index, dfa_count + 1.0);
				
				}

				////
				Double skip_weighted_count = skip_weighted.get(index);
				if (skip_weighted_count == null) skip_weighted_count = 0.0;
				skip_weighted.put(index, skip_weighted_count + 1.0 / step);
				
				////
				Double skip_count = skip.get(index);
				if (skip_count == null) skip_count = 0.0;
				skip.put(index, skip_count + 1.0);
				
				results.addTransition();
				
    	   }
			results.setDfaMatrix(dfa);
			results.setSkipMatrix(skip);
			results.setSkipMatrixWeighted(skip_weighted);
			return results;
       }	
    	   
              
       public static Place getPlace(XEventClasses classes, Set<Integer> inputIndices, Set<Integer> outputIndices) {
    	   Place newPlace = new Place();
    	   for (int activity: inputIndices) {
    		   newPlace.addInputTransition(new Transition(classes.getByIndex(activity).toString(), false));
    	   }
    	   for (int activity: outputIndices) {
    		   newPlace.addOutputTransition(new Transition(classes.getByIndex(activity).toString(), false));
    	   }
    	   return newPlace;
       }

}
