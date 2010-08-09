/*
 *   $Id$
 *
 *   Copyright 2008 Glencoe Software, Inc. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */
package integration;

import static omero.rtypes.*;

import java.util.UUID;

import omero.RString;
import omero.client;
import omero.api.IAdminPrx;
import omero.api.ServiceFactoryPrx;
import omero.model.Experimenter;
import omero.model.ExperimenterGroup;
import omero.model.ExperimenterGroupI;
import omero.model.ExperimenterI;
import omero.model.PermissionsI;

import org.testng.annotations.Test;

/**
 * Various uses of the {@link omero.client} object.
 * All configuration comes from the ICE_CONFIG
 * environment variable.
 */
public class ClientUsageTest 
	extends AbstractTest
{
 
    /**
     * Closes automatically the session.
     * @throws Exception If an error occurred.
     */
    @Test
    public void testClientClosedAutomatically() 
    	throws Exception
    {
    	IAdminPrx svc = root.getSession().getAdminService();
    	String uuid = UUID.randomUUID().toString();
    	Experimenter e = new ExperimenterI();
    	e.setOmeName(omero.rtypes.rstring(uuid));
    	e.setFirstName(omero.rtypes.rstring("integeration"));
    	e.setLastName(omero.rtypes.rstring("tester"));
    	ExperimenterGroup g = new ExperimenterGroupI();
    	g.setName(omero.rtypes.rstring(uuid));
    	g.getDetails().setPermissions(new PermissionsI("rw----"));
    	svc.createGroup(g);
    	svc.createUser(e, uuid);
    	client = new omero.client();
        client.createSession(uuid, uuid);
        client.closeSession();
    }

    /**
     * Tests the usage of memory.
     * @throws Exception If an error occurred.
     */
    @Test
    public void testUseSharedMemory()
    	throws Exception
    {
    	IAdminPrx svc = root.getSession().getAdminService();
    	String uuid = UUID.randomUUID().toString();
    	Experimenter e = new ExperimenterI();
    	e.setOmeName(omero.rtypes.rstring(uuid));
    	e.setFirstName(omero.rtypes.rstring("integeration"));
    	e.setLastName(omero.rtypes.rstring("tester"));
    	ExperimenterGroup g = new ExperimenterGroupI();
    	g.setName(omero.rtypes.rstring(uuid));
    	g.getDetails().setPermissions(new PermissionsI("rw----"));
    	svc.createGroup(g);
    	svc.createUser(e, uuid);
    	client = new omero.client();
        client.createSession(uuid, uuid);

        assertEquals(0, client.getInputKeys().size());
        client.setInput("a", rstring("b"));
        assertEquals(1, client.getInputKeys().size());
        assertTrue(client.getInputKeys().contains("a"));
        assertEquals("b", ((RString) client.getInput("a")).getValue());

        client.closeSession();
    }

    /**
     * Test the creation of an insecure client.
     * @throws Exception If an error occurred.
     */
    @Test
    public void testCreateInsecureClientTicket2099()
    	throws Exception
    {
        IAdminPrx svc = root.getSession().getAdminService();
    	String uuid = UUID.randomUUID().toString();
    	Experimenter e = new ExperimenterI();
    	e.setOmeName(omero.rtypes.rstring(uuid));
    	e.setFirstName(omero.rtypes.rstring("integeration"));
    	e.setLastName(omero.rtypes.rstring("tester"));
    	ExperimenterGroup g = new ExperimenterGroupI();
    	g.setName(omero.rtypes.rstring(uuid));
    	g.getDetails().setPermissions(new PermissionsI("rw----"));
    	svc.createGroup(g);
    	svc.createUser(e, uuid);
    	client secure = new omero.client();
    	ServiceFactoryPrx factory = secure.createSession(uuid, uuid);
        assertTrue(secure.isSecure());
        try {
        	factory.getAdminService().getEventContext();
            omero.client insecure = secure.createClient(false);
            try {
                insecure.getSession().getAdminService().getEventContext();
                assertFalse(insecure.isSecure());
            } finally {
                insecure.closeSession();
            }
        } finally {
            secure.closeSession();
        }
    }

}
