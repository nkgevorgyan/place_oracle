package org.processmining.plugins.workshop.helloworld;

public class MyParameters {

	/**
	 * Minimal cardinality parameter: If the cardinality of an edge is below
	 * this threshold, it will be omitted from the graph.
	 */
	private int localWindowSize;
	private int choiceDepth;
	private double alpha;
	private double beta;
	private double groups;
	private boolean choicePattern;
	private boolean directlyFollowed;
	private boolean minimalPlaces;
	private String sequenceApproach;
	private String choiceApproach;
	private String normCoeff;

	/**
	 * Create default parameter values.
	 */
	public MyParameters() {
		this.setLocalWindowSize(1);
		this.setChoiceDepth(3);
		this.setAlpha(0.01);
		this.setBeta(0.01);
		this.setDirectlyFollowed(false);
		this.setChoicePattern(true);
		this.setMinimalPlaces(false);
		this.setSequenceApproach("DF matrix");
		this.setChoiceApproach("DF matrix");
		this.setNormCoeff("max");
//		this.setGroups(0);
	}

	public boolean isDirectlyFollowed() {
		return directlyFollowed;
	}

	public void setDirectlyFollowed(boolean directlyFollowed) {
		this.directlyFollowed = directlyFollowed;
	}

	public boolean isChoicePattern() {
		return choicePattern;
	}

	public void setChoicePattern(boolean choicePattern) {
		this.choicePattern = choicePattern;
	}

	public int getLocalWindowSize() {
		return localWindowSize;
	}

	public void setLocalWindowSize(int localWindowSize) {
		this.localWindowSize = localWindowSize;
	}

	public double getAlpha() {
		return this.alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBeta() {
		return this.beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public int getChoiceDepth() {
		return choiceDepth;
	}

	public void setChoiceDepth(int choiceDepth) {
		this.choiceDepth = choiceDepth;
	}

	public boolean isMinimalPlaces() {
		return minimalPlaces;
	}

	public void setMinimalPlaces(boolean minimalPlaces) {
		this.minimalPlaces = minimalPlaces;
	}

	public String getSequenceApproach() {
		return sequenceApproach;
	}

	public void setSequenceApproach(String sequenceApproach) {
		this.sequenceApproach = sequenceApproach;
	}

	public String getChoiceApproach() {
		return choiceApproach;
	}

	public void setChoiceApproach(String choiceApproach) {
		this.choiceApproach = choiceApproach;
	}

//	public double getGroups() {
//		return groups;
//	}
//
//	public void setGroups(double groups) {
//		this.groups = groups;
//	}

	public String getNormCoeff() {
		return normCoeff;
	}

	public void setNormCoeff(String normCoeff) {
		this.normCoeff = normCoeff;
	}


}