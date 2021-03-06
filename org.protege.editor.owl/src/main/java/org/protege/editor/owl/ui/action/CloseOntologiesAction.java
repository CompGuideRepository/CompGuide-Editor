package org.protege.editor.owl.ui.action;

import org.protege.editor.core.ProtegeManager;
import org.protege.editor.core.ui.util.JOptionPaneEx;
import org.protege.editor.owl.ui.selector.OWLOntologySelectorPanel2;
import org.semanticweb.owlapi.model.OWLOntology;

import javax.swing.*;
import java.awt.event.ActionEvent;
/*
* Copyright (C) 2007, University of Manchester
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Sep 30, 2008<br><br>
 */
public class CloseOntologiesAction extends ProtegeOWLAction {

    public void actionPerformed(ActionEvent event) {
        OWLOntologySelectorPanel2 ontologyList = new OWLOntologySelectorPanel2(getOWLEditorKit(),
                                                                               getOWLModelManager().getOntologies());
        ontologyList.checkAll(false);

        if (JOptionPaneEx.showConfirmDialog(getWorkspace(),
                                                      "Close ontologies",
                                                      ontologyList,
                                                      JOptionPane.PLAIN_MESSAGE,
                                                      JOptionPane.OK_CANCEL_OPTION,
                                                      ontologyList) == JOptionPane.OK_OPTION){
            if (ontologyList.getSelectedOntologies().size() == getOWLModelManager().getOntologies().size()){
                ProtegeManager.getInstance().disposeOfEditorKit(getOWLEditorKit());
            }
            else{
                for (OWLOntology ont : ontologyList.getSelectedOntologies()){
                    getOWLModelManager().removeOntology(ont);
                }
            }
        }
    }


    public void initialise() throws Exception {
        // do nothing
    }


    public void dispose() throws Exception {
        // do nothing
    }
}
