package org.processmining.plugins.workshop.helloworld;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DFAOutput {
	private Set<Integer> activityIndices;
	
//	private double[][] dfaMatrix;
//	private double[][] skipMatrix;
	private int transitionsQuantity;
//	private double[][] skipMatrixWeighted;
	private HashMap<Pair, Double> dfaMatrix;
	private HashMap<Pair, Double> skipMatrix;
	private HashMap<Pair, Double>skipMatrixWeighted;
	
	
	public DFAOutput() {
		
		this.setDfaMatrix(new HashMap<>());
		this.setSkipMatrix(new HashMap<>());
		this.setSkipMatrixWeighted(new HashMap<>());

		this.activityIndices = new HashSet<Integer>();
		this.transitionsQuantity = 0;
	}
	
	public void addIndex (int index) {
		this.activityIndices.add(index);
	}
	
	public int getTransitionsQuantity() {
		return transitionsQuantity;
	}

	public void addTransition () {
		this.transitionsQuantity++;
	}

	public Set<Integer> getactivityIndices() {
		return activityIndices;
	}
	
	public HashMap<Pair, Double> getDfaMatrix() {
		return dfaMatrix;
	}
	
	public void setDfaMatrix(HashMap<Pair, Double> dfaMatrix) {
		this.dfaMatrix = dfaMatrix;
	}

	public Map<Pair, Double> getSkipMatrix() {
		return skipMatrix;
	}
	public void setSkipMatrix(HashMap<Pair, Double> skipMatrix) {
		this.skipMatrix = skipMatrix;
	}

	public HashMap<Pair, Double> getSkipMatrixWeighted() {
		return skipMatrixWeighted;
	}

	public void setSkipMatrixWeighted(HashMap<Pair, Double> skipMatrixWeighted) {
		this.skipMatrixWeighted = skipMatrixWeighted;
	}
	
	
}
