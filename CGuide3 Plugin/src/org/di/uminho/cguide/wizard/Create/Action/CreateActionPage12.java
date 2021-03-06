package org.di.uminho.cguide.wizard.Create.Action;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JRadioButton;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.AbstractOWLWizardPanel;

//import org.semanticweb.reasonerfactory.pellet.PelletReasonerFactory;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 02-Jul-2006<br>
 * <br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br>
 * <br>
 */
public class CreateActionPage12 extends AbstractOWLWizardPanel {

	public static final String ID = "CreateActionPage12";

	public static final String title = "Action Next Clinical Task";

	public int choice = -1;

	private JList list;

	private JRadioButton paralleltask;
	private JRadioButton preferencealternativetask;
	private JRadioButton alternativetask;
	private JRadioButton nexttask;

	public CreateActionPage12(OWLEditorKit owlEditorKit) {
		super(ID, title, owlEditorKit);
	}

	protected void createUI(JComponent parent) {
		setInstructions("Please select the type of next Clinical Task.");
		parent.setLayout(new BorderLayout());

		Box box = new Box(BoxLayout.Y_AXIS);
		parent.add(box, BorderLayout.CENTER);

		paralleltask = new JRadioButton("Parallel Task");
		box.add(paralleltask);
		preferencealternativetask = new JRadioButton("Preference Alternative Task");
		box.add(preferencealternativetask);
		alternativetask = new JRadioButton("Alternative Task");
		box.add(alternativetask);
		nexttask = new JRadioButton("Next Task");
		box.add(nexttask);
		ButtonGroup bg = new ButtonGroup();
		bg.add(paralleltask);
		bg.add(preferencealternativetask);
		bg.add(alternativetask);
		bg.add(nexttask);
	}

	public Object getBackPanelDescriptor() {
		return CreateActionPage11.ID;
	}

	public Object getNextPanelDescriptor() {
		if (paralleltask.isSelected()) {
			choice = 1;
			return CreateActionPage13parallel.ID;
		} else if (preferencealternativetask.isSelected()) {
			choice = 2;
			return CreateActionPage13preferencealternative.ID;
		} else if (alternativetask.isSelected()) {
			choice = 3;
			return CreateActionPage13alternative.ID;
		} else if (nexttask.isSelected()) {
			choice = 4;
			return CreateActionPage13next.ID;
		} else
			choice = -1;
		return CreateActionPage12.ID;
	}

}
