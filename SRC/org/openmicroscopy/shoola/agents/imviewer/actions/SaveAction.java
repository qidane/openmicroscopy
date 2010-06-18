/*
 * org.openmicroscopy.shoola.agents.imviewer.actions.SaveAction
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */

package org.openmicroscopy.shoola.agents.imviewer.actions;


//Java imports
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.imviewer.IconManager;
import org.openmicroscopy.shoola.agents.imviewer.util.saver.ImgSaver;
import org.openmicroscopy.shoola.agents.imviewer.util.saver.SaveObject;
import org.openmicroscopy.shoola.agents.imviewer.view.ImViewer;
import org.openmicroscopy.shoola.util.ui.UIUtilities;

/** 
 * Brings up the widget to save the displayed image.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author	Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:a.falconi@dundee.ac.uk">a.falconi@dundee.ac.uk</a>
 * @author	Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $ $Date: $)
 * </small>
 * @since OME2.2
 */
public class SaveAction
    extends ViewerAction
{
    
    /** The name of the action. */
    private static final String NAME = "Save As...";
    
    /** The description of the action. */
    public static final String DESCRIPTION = "Save the image as TIFF, " +
                                                "JPEG, PNG, etc.";

    /** 
     * Sets the enabled flag depending on the tab selected.
     * @see ViewerAction#onTabSelection()
     */
    protected void onTabSelection()
    {
    	setEnabled(true);
    	//setEnabled(model.getSelectedIndex() != ImViewer.PROJECTION_INDEX);
    }
    
    /**
     * Disposes and closes the movie player when the {@link ImViewer} is
     * discarded.
     * @see ViewerAction#onStateChange(ChangeEvent)
     */
    protected void onStateChange(ChangeEvent e)
    {
    	if (model.getState() == ImViewer.READY)
    		 onTabSelection();
    	else setEnabled(false);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param model The model. Mustn't be <code>null</code>.
     */
    public SaveAction(ImViewer model)
    {
        super(model, NAME);
        putValue(Action.SHORT_DESCRIPTION, 
                UIUtilities.formatToolTipText(DESCRIPTION));
        IconManager icons = IconManager.getInstance();
        putValue(Action.SMALL_ICON, icons.getIcon(IconManager.SAVE));
    }
    
    /** 
     * Brings up on screen the {@link ImgSaver} window.
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
    	int index = ImgSaver.BASIC;
    	if (model.getMaxC() > 1 && !model.isBigImage()) 
    		index = ImgSaver.PARTIAL;
    	if (model.hasLens()) index = ImgSaver.FULL;
    	int value = 0;
    	switch (model.getSelectedIndex()) {
			case ImViewer.PROJECTION_INDEX:
				value = ImgSaver.PROJECTED_IMAGE;
				break;
			case ImViewer.GRID_INDEX:
				value = ImgSaver.GRID_IMAGE;
		}
        ImgSaver saver = new ImgSaver(model.getUI(), model, index, value);
        saver.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (ImgSaver.SAVE_IMAGE_PROPERTY.equals(name)) {
					model.saveImage((SaveObject) evt.getNewValue());
				}
			}
		});
        UIUtilities.setLocationRelativeToAndShow(model.getUI(), saver);
    }
    
}
