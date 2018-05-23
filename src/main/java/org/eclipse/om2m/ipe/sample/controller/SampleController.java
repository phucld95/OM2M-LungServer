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
package org.eclipse.om2m.ipe.sample.controller;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.model.Model;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;

public class SampleController {
	
	public static CseService CSE;
	protected static String AE_ID;
	
	public static void setLampState(String deviceId, boolean value){
		// Set the value in the "real world" model
		Model.setLampState(deviceId, value);
		// Send the information to the CSE
		String targetID = SampleConstants.CSE_PREFIX + "/" + deviceId + "/" + SampleConstants.DATA;
		ContentInstance cin = new ContentInstance();
		cin.setContent(ObixUtil.getStateRep(deviceId, value));
		cin.setContentInfo(MimeMediaType.OBIX + ":" + MimeMediaType.ENCOD_PLAIN);
		RequestSender.createContentInstance(targetID, cin);
	}

	public static void setAllOn(){
	
	}
	
	public static void setAllOff(){
		
	}
	
	public static void toogleAll(){
		
	}

	public static void setCse(CseService cse){
		CSE = cse;
	}
	
}
