package org.di.uminho.cguide.wizard.Edit.Plan;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.AbstractOWLWizardPanel;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

public class EditPlanPage3 extends AbstractOWLWizardPanel {

	public static final String ID = "EditPlanPage3";

	public static final String title = "Plan Periodicity Restrictions";

	public int periodicity_type = 0;
	private JRadioButton maxminperiodicity_ratiobutton;
	private JRadioButton periodicity_ratiobutton;
	public JTextField maxperiodicity_textfield;
	public JTextField periodicity_textfield;
	private JLabel temporalunit_label;
	public JTextField minperiodicity_textfield;
	public JComboBox temporalunit_combobox;
	private JLabel repetitionvalue_label;
	public JTextField repetitionvalue_textfield;

	String maxperiodicity = new String(), minperiodicity = new String(), periodicity = new String(),
			temporalunit = new String(), repetitionvalue = new String();
	int type = -1;

	public EditPlanPage3(OWLEditorKit owlEditorKit, String individual) {
		super(ID, title, owlEditorKit);
		getIndividual(individual);
	}

	protected void createUI(JComponent parent) {
		setInstructions("Please insert data required for the creation of the associated Plan.");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 106, 5, 180, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 23, 0, 20, 20, 20, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		parent.setLayout(gridBagLayout);

		maxminperiodicity_ratiobutton = new JRadioButton("Max/Min Periodicity Restriction:", true);
		GridBagConstraints gbc_maxminperiodicity_ratiobutton = new GridBagConstraints();
		gbc_maxminperiodicity_ratiobutton.anchor = GridBagConstraints.EAST;
		gbc_maxminperiodicity_ratiobutton.insets = new Insets(0, 0, 5, 5);
		gbc_maxminperiodicity_ratiobutton.gridwidth = 2;
		gbc_maxminperiodicity_ratiobutton.gridx = 0;
		gbc_maxminperiodicity_ratiobutton.gridy = 0;
		parent.add(maxminperiodicity_ratiobutton, gbc_maxminperiodicity_ratiobutton);

		maxperiodicity_textfield = new JTextField();
		maxperiodicity_textfield.setColumns(10);
		maxperiodicity_textfield.setEnabled(true);
		maxperiodicity_textfield.setToolTipText("Enter Maximum Periodicity Restriction");
		GridBagConstraints gbc_maxperiodicity_textfield = new GridBagConstraints();
		gbc_maxperiodicity_textfield.insets = new Insets(0, 0, 5, 5);
		gbc_maxperiodicity_textfield.gridx = 2;
		gbc_maxperiodicity_textfield.gridy = 0;
		parent.add(maxperiodicity_textfield, gbc_maxperiodicity_textfield);

		minperiodicity_textfield = new JTextField();
		minperiodicity_textfield.setEnabled(true);
		minperiodicity_textfield.setColumns(10);
		minperiodicity_textfield.setToolTipText("Enter Minimum Periodicity Restriction");
		GridBagConstraints gbc_minperiodicity_textfield = new GridBagConstraints();
		gbc_minperiodicity_textfield.insets = new Insets(0, 0, 5, 5);
		gbc_minperiodicity_textfield.gridx = 2;
		gbc_minperiodicity_textfield.gridy = 1;
		parent.add(minperiodicity_textfield, gbc_minperiodicity_textfield);

		periodicity_ratiobutton = new JRadioButton("Average Periodicity Restriction:");
		GridBagConstraints gbc_periodicity_ratiobutton = new GridBagConstraints();
		gbc_periodicity_ratiobutton.anchor = GridBagConstraints.EAST;
		gbc_periodicity_ratiobutton.insets = new Insets(0, 0, 5, 5);
		gbc_periodicity_ratiobutton.gridwidth = 2;
		gbc_periodicity_ratiobutton.gridx = 0;
		gbc_periodicity_ratiobutton.gridy = 2;
		parent.add(periodicity_ratiobutton, gbc_periodicity_ratiobutton);

		ButtonGroup buttongroup = new ButtonGroup();
		buttongroup.add(maxminperiodicity_ratiobutton);
		buttongroup.add(periodicity_ratiobutton);

		// Add Listener
		maxminperiodicity_ratiobutton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// Periodicity Restriction max/min -> type 0
					periodicity_type = 0;
					maxperiodicity_textfield.setEnabled(true);
					minperiodicity_textfield.setEnabled(true);
					periodicity_textfield.setEnabled(false);
					periodicity_textfield.setText("");
				}
			}
		});

		periodicity_ratiobutton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// Average Periodicity Restriction -> type 1
					periodicity_type = 1;
					maxperiodicity_textfield.setEnabled(false);
					minperiodicity_textfield.setEnabled(false);
					periodicity_textfield.setEnabled(true);
					maxperiodicity_textfield.setText("");
					minperiodicity_textfield.setText("");
				}
			}
		});

		periodicity_textfield = new JTextField();
		periodicity_textfield.setColumns(10);
		periodicity_textfield.setEnabled(false);
		GridBagConstraints gbc_periodicity_textfield = new GridBagConstraints();
		gbc_periodicity_textfield.insets = new Insets(0, 0, 5, 5);
		gbc_periodicity_textfield.gridx = 2;
		gbc_periodicity_textfield.gridy = 2;
		parent.add(periodicity_textfield, gbc_periodicity_textfield);

		repetitionvalue_label = new JLabel("Repetition Value:");
		GridBagConstraints gbc_repetitionvalue_label = new GridBagConstraints();
		gbc_repetitionvalue_label.anchor = GridBagConstraints.EAST;
		gbc_repetitionvalue_label.insets = new Insets(0, 0, 5, 5);
		gbc_repetitionvalue_label.gridx = 1;
		gbc_repetitionvalue_label.gridy = 3;
		parent.add(repetitionvalue_label, gbc_repetitionvalue_label);

		repetitionvalue_textfield = new JTextField();
		repetitionvalue_textfield.setColumns(10);
		repetitionvalue_textfield.setEnabled(true);
		GridBagConstraints gbc_repetitionvalue_textfield = new GridBagConstraints();
		gbc_repetitionvalue_textfield.insets = new Insets(0, 0, 5, 5);
		gbc_repetitionvalue_textfield.gridx = 2;
		gbc_repetitionvalue_textfield.gridy = 3;
		parent.add(repetitionvalue_textfield, gbc_repetitionvalue_textfield);

		temporalunit_label = new JLabel("Temporal Unit:");
		GridBagConstraints gbc_temporalunit_label = new GridBagConstraints();
		gbc_temporalunit_label.anchor = GridBagConstraints.EAST;
		gbc_temporalunit_label.gridwidth = 2;
		gbc_temporalunit_label.insets = new Insets(0, 0, 5, 5);
		gbc_temporalunit_label.gridx = 0;
		gbc_temporalunit_label.gridy = 4;
		parent.add(temporalunit_label, gbc_temporalunit_label);

		temporalunit_combobox = new JComboBox();
		temporalunit_combobox.addItem("-");
		for (OWLNamedIndividual individual : getTemporalUnit()) {
			temporalunit_combobox.addItem(individual.getIRI().getFragment());
		}
		temporalunit_combobox.setSelectedIndex(0);
		GridBagConstraints gbc_temporalunit_combobox = new GridBagConstraints();
		gbc_temporalunit_combobox.insets = new Insets(0, 0, 5, 5);
		gbc_temporalunit_combobox.gridx = 2;
		gbc_temporalunit_combobox.gridy = 4;
		parent.add(temporalunit_combobox, gbc_temporalunit_combobox);

	}

	public Object getBackPanelDescriptor() {
		return EditPlanPage2.ID;
	}

	public Object getNextPanelDescriptor() {
		return EditPlanPage4.ID;
	}

	@Override
	public void aboutToDisplayPanel() {
		if (this.type == 0) {
			maxperiodicity_textfield.setText(this.maxperiodicity);
			maxperiodicity_textfield.setCaretPosition(0);
			minperiodicity_textfield.setText(this.minperiodicity);
			minperiodicity_textfield.setCaretPosition(0);

			maxminperiodicity_ratiobutton.setSelected(true);

			maxperiodicity_textfield.setEnabled(true);
			minperiodicity_textfield.setEnabled(true);
			periodicity_textfield.setEnabled(false);
		} else if (this.type == 1) {
			periodicity_textfield.setText(this.periodicity);
			periodicity_textfield.setCaretPosition(0);

			periodicity_ratiobutton.setSelected(true);

			maxperiodicity_textfield.setEnabled(false);
			minperiodicity_textfield.setEnabled(false);
			periodicity_textfield.setEnabled(true);
		}
		if (!(this.temporalunit.isEmpty())) {
			temporalunit_combobox.setSelectedItem(this.temporalunit);
		}

		repetitionvalue_textfield.setText(this.repetitionvalue);
		repetitionvalue_textfield.setCaretPosition(0);

		// TODO Auto-generated method stub
		super.aboutToDisplayPanel();
	}

	public void getIndividual(String individual_name) {
		OWLNamedIndividual individual = getOWLModelManager().getOWLDataFactory().getOWLNamedIndividual(IRI.create(
				getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#" + individual_name));

		try {

			OWLObjectProperty hasPeriodicity_objectproperty = getOWLModelManager().getOWLDataFactory()
					.getOWLObjectProperty(
							IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
									+ "#hasPeriodicity"));
			OWLNamedIndividual periodicity_individual = individual
					.getObjectPropertyValues(hasPeriodicity_objectproperty, getOWLModelManager().getActiveOntology())
					.iterator().next().asOWLNamedIndividual();

			try {
				OWLDataProperty maxPeriodicityValue_dataproperty = getOWLModelManager().getOWLDataFactory()
						.getOWLDataProperty(
								IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
										+ "#maxPeriodicityValue"));
				OWLLiteral maxPeriodicityValue_literal = periodicity_individual
						.getDataPropertyValues(maxPeriodicityValue_dataproperty,
								getOWLModelManager().getActiveOntology())
						.iterator().next();

				this.maxperiodicity = maxPeriodicityValue_literal.getLiteral();
				this.type = 0;
			} catch (Exception e) {
			}

			try {
				OWLDataProperty minPeriodicityValue_dataproperty = getOWLModelManager().getOWLDataFactory()
						.getOWLDataProperty(
								IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
										+ "#minPeriodicityValue"));
				OWLLiteral minPeriodicityValue_literal = periodicity_individual
						.getDataPropertyValues(minPeriodicityValue_dataproperty,
								getOWLModelManager().getActiveOntology())
						.iterator().next();

				this.minperiodicity = minPeriodicityValue_literal.getLiteral();
				this.type = 0;
			} catch (Exception e) {
			}

			try {
				OWLDataProperty PeriodicityValue_dataproperty = getOWLModelManager().getOWLDataFactory()
						.getOWLDataProperty(
								IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
										+ "#periodicityValue"));
				OWLLiteral PeriodicityValue_literal = periodicity_individual
						.getDataPropertyValues(PeriodicityValue_dataproperty, getOWLModelManager().getActiveOntology())
						.iterator().next();

				this.periodicity = PeriodicityValue_literal.getLiteral();
				this.type = 1;
			} catch (Exception e) {
			}

			try {
				OWLDataProperty repetitionValue_dataproperty = getOWLModelManager().getOWLDataFactory()
						.getOWLDataProperty(
								IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
										+ "#repetitionValue"));
				OWLLiteral repetitionValue_literal = periodicity_individual
						.getDataPropertyValues(repetitionValue_dataproperty, getOWLModelManager().getActiveOntology())
						.iterator().next();

				this.repetitionvalue = repetitionValue_literal.getLiteral();
			} catch (Exception e) {
			}

			try {
				OWLObjectProperty hasTemporalUnit_objectproperty = getOWLModelManager().getOWLDataFactory()
						.getOWLObjectProperty(
								IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
										+ "#hasTemporalUnit"));

				OWLNamedIndividual temporalunit_individual = periodicity_individual
						.getObjectPropertyValues(hasTemporalUnit_objectproperty,
								getOWLModelManager().getActiveOntology())
						.iterator().next().asOWLNamedIndividual();

				this.temporalunit = temporalunit_individual.getIRI().getFragment();
			} catch (Exception e) {
			}

		} catch (Exception e) {
		}
	}

	public Set<OWLNamedIndividual> getTemporalUnit() {
		Set<OWLIndividual> specialities = new HashSet<OWLIndividual>();
		Set<OWLNamedIndividual> clinicalspecialities = new HashSet<OWLNamedIndividual>();

		OWLOntology ontology = getOWLModelManager().getActiveOntology();

		OWLClass cs = getOWLModelManager().getOWLDataFactory().getOWLClass(IRI
				.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#TemporalUnit"));

		specialities = cs.getIndividuals(ontology);

		for (OWLIndividual a : specialities) {
			clinicalspecialities.add(a.asOWLNamedIndividual());
		}

		return clinicalspecialities;
	}

}
