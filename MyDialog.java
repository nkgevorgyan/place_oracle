package org.processmining.plugins.workshop.helloworld;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.fluxicon.slickerbox.colors.SlickerColors;
import com.fluxicon.slickerbox.components.NiceSlider;
import com.fluxicon.slickerbox.components.NiceSlider.Orientation;
import com.fluxicon.slickerbox.factory.SlickerDecorator;
import com.fluxicon.slickerbox.factory.SlickerFactory;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;



public class MyDialog extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9048821565595960963L;

	/**
	 * Parameter dialog for converting the given workshop model to a workflow
	 * graph.
	 * 
	 * @param model
	 *            The given workshop model.
	 * @param parameters
	 *            The parameters which will be used for the conversion.
	 */
	public MyDialog(final MyParameters parameters) {
		/*
		 * Get a layout containing a single column and two rows, where the top
		 * row height equals 30.
		 */
		double size[][] = { {TableLayoutConstants.FILL, TableLayoutConstants.FILL}, 
				{TableLayoutConstants.FILL, TableLayoutConstants.FILL, TableLayoutConstants.FILL, TableLayoutConstants.FILL, TableLayoutConstants.FILL }};
		setLayout(new TableLayout(size));
		
		//////////// Second column


//		final JCheckBox dfBox = SlickerFactory.instance().createCheckBox("Discover only directly followed reletaions", false);
//		
//		dfBox.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				parameters.setDirectlyFollowed(dfBox.isSelected());
//			}
//
//		});
//
//		add(dfBox, "1, 0");
		
		
		//////////////
		
//		final JCheckBox choiceBox = SlickerFactory.instance().createCheckBox("Discover choice pattern", false);
//		
//		choiceBox.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				parameters.setChoicePattern(choiceBox.isSelected());
//			}
//
//		});
//
//		add(choiceBox, "1, 1");

		//////////////
		
		final JCheckBox minimalPlacesBox = SlickerFactory.instance().createCheckBox("Discover minimal set of places", false);
		
		minimalPlacesBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				parameters.setMinimalPlaces(minimalPlacesBox.isSelected());
			}

		});

		add(minimalPlacesBox, "1, 0");
		
		//////////////
		Object coeffs[] = new Object [2];
		coeffs[0] = "sum";
		coeffs[1] = "max";
	
		
		final JList coeffsList = new javax.swing.JList(coeffs);

	
		coeffsList.setName("Select normalization coefficient");
		coeffsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		coeffsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				parameters.setNormCoeff((String) coeffsList.getSelectedValue());
			}
		});
		JScrollPane classifierScrollPaneCoeff = new javax.swing.JScrollPane();
		SlickerDecorator.instance().decorate(classifierScrollPaneCoeff, SlickerColors.COLOR_BG_3, SlickerColors.COLOR_FG,
				SlickerColors.COLOR_BG_1);
		classifierScrollPaneCoeff.setPreferredSize(new Dimension(100, 100));
		classifierScrollPaneCoeff.setViewportView(coeffsList);
		add(classifierScrollPaneCoeff, "1, 1");
		
		//////////////
		Object approaches[] = new Object [6];
		approaches[0] = "DF matrix";
		approaches[1] = "EF matrix";
		approaches[2] = "EF matrix weighted";
		approaches[3] = "HM matrix";
		approaches[4] = "HM matrix based on EF";
		approaches[5] = "HM matrix based on EF weighted";
		
		final JList approachList = new javax.swing.JList(approaches);

	
		approachList.setName("Select approach to generate sequence relations");
		approachList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		approachList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				parameters.setSequenceApproach((String) approachList.getSelectedValue());
			}
		});
		JScrollPane classifierScrollPane = new javax.swing.JScrollPane();
		SlickerDecorator.instance().decorate(classifierScrollPane, SlickerColors.COLOR_BG_3, SlickerColors.COLOR_FG,
				SlickerColors.COLOR_BG_1);
		classifierScrollPane.setPreferredSize(new Dimension(100, 100));
		classifierScrollPane.setViewportView(approachList);
		add(classifierScrollPane, "1, 2");
		
		////////////////
		Object approaches_choice[] = new Object [6];
		approaches_choice[0] = "DF matrix";
		approaches_choice[1] = "EF matrix";
		approaches_choice[2] = "EF matrix weighted";
		approaches_choice[3] = "HM matrix";
		approaches_choice[4] = "HM matrix based on EF";
		approaches_choice[5] = "HM matrix based on EF weighted";
		
		final JList approachListChoice = new javax.swing.JList(approaches_choice);

		approachListChoice.setName("Select approach to generate sequence relations");
		approachListChoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		approachListChoice.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				parameters.setChoiceApproach((String) approachListChoice.getSelectedValue());
			}
		});
		JScrollPane classifierScrollPaneTwo = new javax.swing.JScrollPane();
		SlickerDecorator.instance().decorate(classifierScrollPaneTwo, SlickerColors.COLOR_BG_3, SlickerColors.COLOR_FG,
				SlickerColors.COLOR_BG_1);
		classifierScrollPaneTwo.setPreferredSize(new Dimension(100, 100));
		classifierScrollPaneTwo.setViewportView(approachListChoice);
		add(classifierScrollPaneTwo, "1, 3");

		//////////////// First column

		final NiceSlider sliderWindow = SlickerFactory.instance()
				.createNiceIntegerSlider("Select context window size", 2,
						7, 2, Orientation.HORIZONTAL);
		

		ChangeListener listenerWindow = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				parameters.setLocalWindowSize(sliderWindow.getSlider().getValue());
			}
		};

		sliderWindow.addChangeListener(listenerWindow);
		listenerWindow.stateChanged(null);
		add(sliderWindow, "0, 0");
		
		/////////////////
		
		final NiceSlider sliderDepth = SlickerFactory.instance()
				.createNiceIntegerSlider("Select depth of choice places", 1,
						4, 3, Orientation.HORIZONTAL);
		

		ChangeListener listenerDepth = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				parameters.setLocalWindowSize(sliderDepth.getSlider().getValue());
			}
		};

		sliderDepth.addChangeListener(listenerDepth);
		listenerDepth.stateChanged(null);
		add(sliderDepth, "0, 1");
		

		////////////////

		final NiceSlider sliderAlpha = SlickerFactory.instance()
				.createNiceIntegerSlider("Select sequence threshold (%)", 0, 
						100, 1, Orientation.HORIZONTAL);
		
		ChangeListener listenerAlpha = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				parameters.setAlpha(sliderAlpha.getSlider().getValue() / 100.0);
			}
		};
		
		sliderAlpha.addChangeListener(listenerAlpha);
		listenerAlpha.stateChanged(null);
		add(sliderAlpha, "0, 2");
		
		///////////////
		
		final NiceSlider sliderBeta = SlickerFactory.instance()
				.createNiceIntegerSlider("Select choise threshold (%)", 0, 
						100, 100, Orientation.HORIZONTAL);
		
		ChangeListener listenerBeta = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				parameters.setBeta(sliderBeta.getSlider().getValue() / 100.0);
			}
		};
		
		sliderBeta.addChangeListener(listenerBeta);
		listenerBeta.stateChanged(null);
		add(sliderBeta, "0, 3");
		///////////////
		
		final NiceSlider sliderGroup = SlickerFactory.instance()
				.createNiceIntegerSlider("Select percent of most frequent groups (%)", 0, 
						100, 0, Orientation.HORIZONTAL);
		
		ChangeListener listenerGroup = new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				parameters.setGroups(sliderGroup.getSlider().getValue() / 100.0);
			}
		};
		
		sliderGroup.addChangeListener(listenerGroup);
		listenerGroup.stateChanged(null);
		add(sliderGroup, "0, 4");
	}
}
