/*
 * org.openmicroscopy.shoola.agents.fsimporter.util.ThumbnailLabel 
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2011 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.agents.fsimporter.util;


//Java imports
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.events.iviewer.ViewImage;
import org.openmicroscopy.shoola.agents.events.iviewer.ViewImageObject;
import org.openmicroscopy.shoola.agents.fsimporter.ImporterAgent;
import org.openmicroscopy.shoola.agents.util.ui.RollOverThumbnailManager;
import org.openmicroscopy.shoola.env.data.model.ThumbnailData;
import org.openmicroscopy.shoola.env.event.EventBus;
import org.openmicroscopy.shoola.util.image.geom.Factory;

import pojos.ImageData;

/** 
 * Component displaying the thumbnail.
 *
 * @author Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since 3.0-Beta4
 */
class ThumbnailLabel 
	extends JLabel
{

	/** The border of the thumbnail label. */
	private static final Border		LABEL_BORDER = 
							BorderFactory.createLineBorder(Color.black, 1);
	
	/** The text displayed in the tool tip when the image has been imported. */
	private static final String		THUMBNAIL_LABEL_TOOLTIP = 
		"Click on thumbnail to launch the viewer.";
	
	/** The text displayed in the tool tip when the image has been imported. */
	private static final String		IMAGE_LABEL_TOOLTIP = 
		"Click on icon to launch the viewer.";
	
	/** The thumbnail or the image to host. */
	private Object data;
	
	/** Posts an event to view the image. */
	private void viewImage()
	{
		EventBus bus = ImporterAgent.getRegistry().getEventBus();
		if (data instanceof ThumbnailData) {
			ThumbnailData thumbnail = (ThumbnailData) data;
			bus.post(new ViewImage(new ViewImageObject(
					thumbnail.getImage()), null));
		} else if (data instanceof ImageData) {
			ImageData image = (ImageData) data;
			bus.post(new ViewImage(new ViewImageObject(image), null));
		}
	}
	
	/** Rolls over the node. */
	private void rollOver()
	{
		if (data instanceof ThumbnailData) {
			ThumbnailData thumbnail = (ThumbnailData) data;
			RollOverThumbnailManager.rollOverDisplay(thumbnail.getThumbnail(), 
		   			 getBounds(), getLocationOnScreen(), toString());
		} 
	}
	
	/**  
	 * Creates a new instance. 
	 * 
	 * @param icon The icon to display.
	 */
	ThumbnailLabel(Icon icon)
	{
		super(icon);
	}
	
	/** Creates a default new instance. */
	ThumbnailLabel() {}
	
	/** Sets the image that have been imported. */
	void setImage(ImageData data)
	{
		if (data == null) return;
		this.data = data;
		setToolTipText(IMAGE_LABEL_TOOLTIP);
		addMouseListener(new MouseAdapter() {
			
			/**
			 * Views the image.
			 * @see MouseListener#mousePressed(MouseEvent)
			 */
			public void mousePressed(MouseEvent e)
			{
				if (e.getClickCount() == 2)
					viewImage(); 
			}
		});
	}

	/** 
	 * Sets the thumbnail to view. 
	 * 
	 * @param data The value to set.
	 */
	void setThumbnail(ThumbnailData data)
	{
		if (data == null) return;
		ImageIcon icon = new ImageIcon(Factory.magnifyImage(0.25, 
				data.getThumbnail()));
		this.data = data;
		setToolTipText(THUMBNAIL_LABEL_TOOLTIP);
		setBorder(LABEL_BORDER);
		if (icon != null) {
			setIcon(icon);
			//setPreferredSize(new Dimension(icon.getIconWidth(), 
			//		icon.getIconHeight()));
		}
		addMouseListener(new MouseAdapter() {
			
			/**
			 * Views the image.
			 * @see MouseListener#mousePressed(MouseEvent)
			 */
			public void mousePressed(MouseEvent e)
			{
				if (e.getClickCount() == 2)
					viewImage(); 
			}

			/**
			 * Removes the zooming window from the display.
			 * @see MouseListener#mouseExited(MouseEvent)
			 */
			public void mouseExited(MouseEvent e)
			{
				RollOverThumbnailManager.stopOverDisplay();
			}
			
			/**
			 * Zooms the thumbnail.
			 * @see MouseListener#mouseEntered(MouseEvent)
			 */
			public void mouseEntered(MouseEvent e) { rollOver(); }
		});
	}
	
}
