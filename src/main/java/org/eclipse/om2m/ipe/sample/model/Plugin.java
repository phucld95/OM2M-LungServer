/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.model;

public class Plugin {

    /** Default device location */
    public final static String LOCATION = "Home";
    /** Toggle */
    public final static String TOGGLE = "toggle";
    /** Default device type */
    public final static String TYPE = "LAMP";
    /** device state */
    private boolean state = false;
    private String deviceId;
    
    public Plugin(String deviceId, boolean initState){
    	this.deviceId = deviceId;
    	this.state = initState;
    }

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String lampId) {
		this.deviceId = lampId;
	}
	
}
