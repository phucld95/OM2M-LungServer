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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.sample.RequestSender;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.model.Device;
import org.eclipse.om2m.ipe.sample.model.Model;
import org.eclipse.om2m.ipe.sample.util.ObixUtil;

public class LifeCycleManager {

	private static Log LOGGER = LogFactory.getLog(LifeCycleManager.class); 

	/**
	 * Handle the start of the plugin with the resource representation
	 */
	public static void start(){
		Map<String, Device> lamps = new HashMap<String, Device>();
		for(int i=0; i<2; i++) {
			String lampId = Device.TYPE+"_"+i;
			lamps.put(lampId, new Device(lampId, false));
		}
		Model.setModel(lamps);
		createResources("SPIRO_SMART", false, SampleConstants.POA);
	}

	/**
	 * Stop the GUI if it is present
	 */
	public static void stop(){
//		if(SampleConstants.GUI){
//			GUI.stop();
//		}
		
//		Close database in here TO_DO
	}
	
	/**
	 * Creates all required resources.
	 * @param appId - Application ID
	 * @param initValue - initial lamp value
	 * @param poa - lamp Point of Access
	 */
	private static void createResources(String appId, boolean initValue, String poa) {
		// Create the Application resource
		Container container = new Container();
		container.getLabels().add("lungfuction");
		container.setMaxNrOfInstances(BigInteger.valueOf(0));
		

		AE ae = new AE();
		ae.setRequestReachability(true);
		ae.getPointOfAccess().add(poa);
		ae.setAppID(appId);
		ae.setName(appId);

		ResponsePrimitive response = RequestSender.createAE(ae);
		// Create Application sub-resources only if application not yet created
		if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			container = new Container();
			container.setMaxNrOfInstances(BigInteger.valueOf(10));
			// Create DESCRIPTOR container sub-resource
			container.setName(SampleConstants.DESC);
			LOGGER.info(RequestSender.createContainer(response.getLocation(), container));
			// Create STATE container sub-resource
			container.setName(SampleConstants.DATA);
			LOGGER.info(RequestSender.createContainer(response.getLocation(), container));

			String content;
			// Create DESCRIPTION contentInstance on the DESCRIPTOR container resource
			content = ObixUtil.getDescriptorRep(SampleConstants.CSE_ID, appId, SampleConstants.DATA);
			ContentInstance contentInstance = new ContentInstance();
			contentInstance.setContent(content);
			contentInstance.setContentInfo(MimeMediaType.OBIX);
			RequestSender.createContentInstance(
					SampleConstants.CSE_PREFIX + "/" + appId + "/" + SampleConstants.DESC, contentInstance);

			// Create initial contentInstance on the STATE container resource
			content = ObixUtil.getStateRep(appId, initValue);
			contentInstance.setContent(content);
			RequestSender.createContentInstance(
					SampleConstants.CSE_PREFIX + "/" + appId + "/" + SampleConstants.DATA, contentInstance);
		}
	}
}
