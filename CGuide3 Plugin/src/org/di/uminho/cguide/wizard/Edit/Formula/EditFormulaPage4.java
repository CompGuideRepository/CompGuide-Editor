package org.di.uminho.cguide.wizard.Edit.Formula;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.protege.editor.core.ui.wizard.WizardPanel;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.AbstractOWLWizardPanel;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

public class EditFormulaPage4 extends AbstractOWLWizardPanel {

	public static final String ID = "EditFormulaPage4";

	public static final String title = "Formula Variable Parameters";

	private JLabel parameter_label;
	private JLabel added_label;

	private JList list_added;
	public DefaultListModel<String> model_added;

	private JList list_total;
	private DefaultListModel<String> model_total;

	private JTextArea info_text;
	private JLabel info_label;

	private JButton btnAdd;
	private JButton btnRemove;

	private Map<String, String> info_list;

	private int last_selected_index_total;
	private int last_selected_index_added;

	Set<OWLNamedIndividual> total_parameters;
	Set<OWLNamedIndividual> formula_parameters;

	public EditFormulaPage4(OWLEditorKit owlEditorKit, String formula_individual) {
		super(ID, title, owlEditorKit);
		fixFormula_parameters(formula_individual);
	}

	protected void createUI(JComponent parent) {
		setInstructions("Please select the Variable Parameters of the Formula.");

		info_list = getDataParameters(getParameters());

		last_selected_index_total = -1;
		last_selected_index_added = -1;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 124, 95, 124, 0 };
		gridBagLayout.rowHeights = new int[] { 43, 14, 77, 101, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		parent.setLayout(gridBagLayout);

		parameter_label = new JLabel("Parameter List:");
		GridBagConstraints gbc_lblSpecialities = new GridBagConstraints();
		gbc_lblSpecialities.anchor = GridBagConstraints.NORTH;
		gbc_lblSpecialities.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpecialities.gridx = 1;
		gbc_lblSpecialities.gridy = 1;
		parent.add(parameter_label, gbc_lblSpecialities);

		added_label = new JLabel("Added:");
		GridBagConstraints gbc_lblAdd = new GridBagConstraints();
		gbc_lblAdd.anchor = GridBagConstraints.NORTH;
		gbc_lblAdd.insets = new Insets(0, 0, 5, 0);
		gbc_lblAdd.gridx = 3;
		gbc_lblAdd.gridy = 1;
		parent.add(added_label, gbc_lblAdd);

		model_total = new DefaultListModel<String>();

		list_total = new JList();
		list_total.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list_total.setCellRenderer(getOWLEditorKit().getWorkspace().createOWLCellRenderer());
		list_total.setModel(model_total);
		list_total.setFixedCellHeight(20);
		list_total.setFixedCellWidth(100);

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridheight = 2;
		// gbc_list.gridwidth = 1;
		gbc_list.gridx = 1;
		gbc_list.gridy = 2;
		parent.add(new JScrollPane(list_total), gbc_list);

		btnAdd = new JButton(new AbstractAction("-> Add ->") {
			public void actionPerformed(ActionEvent e) {
				if (list_total.getSelectedIndex() > -1) {
					model_added.addElement(list_total.getSelectedValue().toString());
					model_total.remove(list_total.getSelectedIndex());
					list_added.setModel(model_added);
					list_total.setModel(model_total);
				}
			}
		});

		GridBagConstraints gbc_btnadd = new GridBagConstraints();
		gbc_btnadd.anchor = GridBagConstraints.SOUTH;
		gbc_btnadd.insets = new Insets(0, 0, 5, 5);
		gbc_btnadd.gridx = 2;
		gbc_btnadd.gridy = 2;
		parent.add(btnAdd, gbc_btnadd);

		model_added = new DefaultListModel<String>();
		list_added = new JList();
		list_added.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list_added.setCellRenderer(getOWLEditorKit().getWorkspace().createOWLCellRenderer());
		list_added.setFixedCellHeight(20);
		list_added.setFixedCellWidth(100);
		list_added.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					info_text.setText(info_list.get(list_added.getSelectedValue()));
					info_text.setCaretPosition(0);
					list_total.clearSelection(); // <- Deselect item in list
				}
			}
		});

		// Add Listener
		list_total.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					info_text.setText(info_list.get(list_total.getSelectedValue()));
					info_text.setCaretPosition(0);
					list_added.clearSelection(); // <- Deselect item in list
				}
			}
		});

		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridheight = 2;
		// gbc_list_1.gridwidth = 1;
		gbc_list_1.gridx = 3;
		gbc_list_1.gridy = 2;
		parent.add(new JScrollPane(list_added), gbc_list_1);

		btnRemove = new JButton(new AbstractAction("<- Remove <-") {
			public void actionPerformed(ActionEvent e) {
				if (list_added.getSelectedIndex() > -1) {
					model_total.addElement(list_added.getSelectedValue().toString());
					model_added.remove(list_added.getSelectedIndex());
					list_total.setModel(model_total);
					list_added.setModel(model_added);
				}
			}
		});

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.NORTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 3;
		parent.add(btnRemove, gbc_button);

		info_label = new JLabel("Selected Parameter Info:");
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfo.gridx = 4;
		gbc_lblInfo.gridy = 1;
		parent.add(info_label, gbc_lblInfo);

		info_text = new JTextArea();
		info_text.setRows(5);
		info_text.setColumns(100);
		info_text.setEnabled(false);
		GridBagConstraints gbc_info_text = new GridBagConstraints();
		gbc_info_text.gridheight = 2;
		gbc_info_text.insets = new Insets(0, 0, 5, 0);
		gbc_info_text.fill = GridBagConstraints.BOTH;
		gbc_info_text.gridx = 4;
		gbc_info_text.gridy = 2;
		parent.add(new JScrollPane(info_text), gbc_info_text);

	}

	public Object getBackPanelDescriptor() {
		return EditFormulaPage3.ID;
	}

	public Object getNextPanelDescriptor() {
		return WizardPanel.FINISH;
	}

	@Override
	public void aboutToDisplayPanel() {
		for (OWLNamedIndividual a : this.total_parameters) {
			model_total.addElement(a.getIRI().getFragment());
		}
		for (OWLNamedIndividual a : this.formula_parameters) {
			model_added.addElement(a.getIRI().getFragment());
		}
		list_total.setModel(model_total);
		list_added.setModel(model_added);
		// TODO Auto-generated method stub
		super.aboutToDisplayPanel();
	}

	public Set<OWLNamedIndividual> getParameters() {
		Set<OWLIndividual> specialities = new HashSet<OWLIndividual>();
		Set<OWLNamedIndividual> clinicalspecialities = new HashSet<OWLNamedIndividual>();

		OWLOntology ontology = getOWLModelManager().getActiveOntology();

		OWLClass cs = getOWLModelManager().getOWLDataFactory().getOWLClass(
				IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#Parameter"));

		specialities = cs.getIndividuals(ontology);

		for (OWLIndividual a : specialities) {
			clinicalspecialities.add(a.asOWLNamedIndividual());
		}

		return clinicalspecialities;
	}

	public Map<String, String> getDataParameters(Set<OWLNamedIndividual> list) {
		Map<String, String> conditions = new HashMap<String, String>();

		for (OWLNamedIndividual individual : list) {
			try {

				String possibleValue = new String(); // Decimal
				String variableName = new String(); // String
				String parameterIdentifier = new String(); // String
				String questionParameter = new String(); // String
				String unit = new String(); // String
				String parameterDescription = new String(); // Choose individual
				String info = new String();

				// Get Data Properties of each Parameter
				Map<OWLDataPropertyExpression, Set<OWLLiteral>> data = individual
						.getDataPropertyValues(getOWLModelManager().getActiveOntology());
				for (Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : data.entrySet()) {
					try {

						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("possibleValue")) {
							possibleValue = entry.getValue().iterator().next().getLiteral();
						}
						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("variableName")) {
							variableName = entry.getValue().iterator().next().getLiteral();
						}
						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("parameterIdentifier")) {
							parameterIdentifier = entry.getValue().iterator().next().getLiteral();
						}
						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("questionParameter")) {
							questionParameter = entry.getValue().iterator().next().getLiteral();
						}
						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("unit")) {
							unit = entry.getValue().iterator().next().getLiteral();
						}
						if (entry.getKey().asOWLDataProperty().getIRI().getFragment().equals("parameterDescription")) {
							parameterDescription = entry.getValue().iterator().next().getLiteral();
						}
					} catch (Exception e) {
					}
				}

				info = "ParameterID:" + individual.getIRI().getFragment() + "\nPossible Value:" + possibleValue
						+ "\nVariable Name:" + variableName + "\nParameterIdentifier:" + parameterIdentifier
						+ "\nQuestion Parameter:" + questionParameter + "\nUnit:" + unit + "\nParameter Description:"
						+ parameterDescription;

				conditions.put(individual.getIRI().getFragment(), info);

			} catch (Exception e) {
			}
		}

		return conditions;
	}

	public Set<OWLNamedIndividual> getFormula_parameters(String formula_individual_name) {

		Set<OWLNamedIndividual> res = new HashSet<OWLNamedIndividual>();

		OWLNamedIndividual formula_individual = getOWLModelManager().getOWLDataFactory().getOWLNamedIndividual(
				IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI() + "#"
						+ formula_individual_name));

		OWLObjectProperty hasVariableParameter_objectproperty = getOWLModelManager().getOWLDataFactory()
				.getOWLObjectProperty(
						IRI.create(getOWLModelManager().getActiveOntology().getOntologyID().getOntologyIRI()
								+ "#hasVariableParameter"));

		Set<OWLIndividual> hasVariableParameter_individual = formula_individual
				.getObjectPropertyValues(hasVariableParameter_objectproperty, getOWLModelManager().getActiveOntology());

		for (OWLIndividual a : hasVariableParameter_individual) {
			res.add(a.asOWLNamedIndividual());
		}
		return res;
	}

	public void fixFormula_parameters(String formula_individual) {
		this.total_parameters = getParameters();
		this.formula_parameters = getFormula_parameters(formula_individual);
		for (OWLNamedIndividual a : this.formula_parameters) {
			try {
				this.total_parameters.remove(a);
			} catch (Exception e) {
			}
		}
	}

}
