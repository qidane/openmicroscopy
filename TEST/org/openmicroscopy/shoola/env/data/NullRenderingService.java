/*
 * org.openmicroscopy.shoola.env.data.NullRenderingService
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

package org.openmicroscopy.shoola.env.data;




//Java imports
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Third-party libraries

//Application-internal dependencies
import ome.model.core.Pixels;
import ome.model.core.PixelsDimensions;
import omeis.providers.re.data.PlaneDef;
import org.openmicroscopy.shoola.env.rnd.RenderingControl;
import org.openmicroscopy.shoola.env.rnd.RenderingServiceException;


/** 
 * 
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
public class NullRenderingService
    implements OmeroImageService
{

    /**
     * No-op implementation
     * @see OmeroImageService#loadRenderingControl(long)
     */
    public RenderingControl loadRenderingControl(long pixelsID)
            throws DSOutOfServiceException, DSAccessException
    {
        return null;
    }

    /**
     * No-op implementation
     * @see OmeroImageService#renderImage(long, PlaneDef)
     */
    public BufferedImage renderImage(long pixelsID, PlaneDef pd)
            throws RenderingServiceException
    {
        return null;
    }

    /**
     * No-op implementation
     * @see OmeroImageService#renderImage(long)
     */
    public BufferedImage renderImage(long pixelsID)
            throws RenderingServiceException
    {
        return null;
    }

    /**
     * No-op implementation
     * @see OmeroImageService#shutDown(long)
     */
    public void shutDown(long pixelsID) {}

    /**
     * No-op implementation
     * @see OmeroImageService#getThumbnail(long, int, int)
     */
    public BufferedImage getThumbnail(long pixelsID, int sizeX, int sizeY)
            throws RenderingServiceException
    {
        return null;
    }

	/**
     * No-op implementation
     * @see OmeroImageService#loadPixelsDimensions(long)
     */
	public PixelsDimensions loadPixelsDimensions(long pixelsID) 
		throws DSOutOfServiceException, DSAccessException
	{
		return null;
	}

	/**
     * No-op implementation
     * @see OmeroImageService#loadPixels(long)
     */
	public Pixels loadPixels(long pixelsID) 
		throws DSOutOfServiceException, DSAccessException 
	{
		return null;
	}

	/**
     * No-op implementation
     * @see OmeroImageService#getThumbnailByLongestSide(long, int)
     */
	public BufferedImage getThumbnailByLongestSide(long pixelsID, int maxLength) 
		throws RenderingServiceException 
	{
		return null;
	}

	/**
     * No-op implementation
     * @see OmeroImageService#getPlane(long, int, int, int)
     */
	public byte[] getPlane(long pixelsID, int z, int t, int c) 
		throws DSOutOfServiceException, DSAccessException
	{
		return null;
	}

	/**
     * No-op implementation
     * @see OmeroImageService#pasteRenderingSettings(long, Class, Set)
     */
	public Map pasteRenderingSettings(long pixelsID, Class rootNodeType, 
			Set<Long> nodeIDs)
		throws DSOutOfServiceException, DSAccessException
	{
		return null;
	}

	/**
     * No-op implementation
     * @see OmeroImageService#reloadRenderingService(long)
     */
	public RenderingControl reloadRenderingService(long pixelsID) 
		throws DSAccessException, RenderingServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
