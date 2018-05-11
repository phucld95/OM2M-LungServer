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
package org.eclipse.om2m.ipe.sample;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.sample.constants.Operations;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.controller.SampleController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
 

public class SampleRouter implements InterworkingService{

	private static Log LOGGER = LogFactory.getLog(SampleRouter.class);

	
//	public static void main(String[] args) throws Exception {
//		String json = "{  \n" + 
//				"   \"op\":\"GetTimeline\",\n" + 
//				"   \"user_id\":\"1\"\n" + 
//				"}";
//		System.out.println(json);
//		Gson gson = new Gson();
//		JsonElement params = gson.fromJson(json, JsonElement.class);
//		String option = params.getAsJsonObject().get("op").getAsString();	
//		
//		System.out.println(option);
//		JsonElement temp = params.getAsJsonObject().get("user_id");
//		System.out.println(temp);
//		String userId = temp.getAsString();
//		List<BasicDBObject> result = DatabaseHandle.getInstance().getTimeline(userId);
//		System.out.println(result.size());
//		String jsonContent = "{\"result\" : " + result.toString() + "}";
//		System.out.println(jsonContent);
//
//	}

	
	
	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		String id;
	    String name;		    
	    String email;
	    String weight;
	    String dayOfBirth;
	    String age;		    
	    String height;
	    String gender;
	    String location;
	    String isAdmin;		    
	    String smokingStatus;
	    BasicDBObject obj;
	    JsonElement temp;
	    
		// Request execute
		ResponsePrimitive response = new ResponsePrimitive(request);
		System.out.print("-----------------REQUEST----------------------");
		String json = (String) request.getContent();
		System.out.print(json);
		Gson gson = new Gson();
		JsonElement params = gson.fromJson(json, JsonElement.class);
		String option = params.getAsJsonObject().get("op").getAsString();	
		System.out.print("---------------------------------------");
		
		Operations op = Operations.getOperationFromString(option);
		LOGGER.info("Received request in Sample IPE: op=" + option);
		
		System.out.print(op);

		switch(op){
		case CREATE_USER:
		    obj = DatabaseHandle.getInstance().saveUser(params);
		    if (obj != null) {
		    	id = obj.getString("_id");
			    name = obj.getString("name");		    
			    email = obj.getString("email");
			    weight = obj.getString("weight");
			    dayOfBirth = obj.getString("dayOfBirth");
			    age = obj.getString("age");		    
			    height = obj.getString("height");
			    gender = obj.getString("gender");
			    location = obj.getString("location");
			    isAdmin = obj.getString("isAdmin");		    
			    smokingStatus = obj.getString("smokingStatus");
				
			    response.setContent("{\"status\" : \"success\",\n"
			    		+ "\"user_id\" : \"" + id + "\",\n"
			    		+ "\"name\" : \"" + name + "\",\n"
			    		+ "\"email\" : \"" + email + "\",\n"
			    		+ "\"weight\" : \"" + weight + "\",\n"
			    		+ "\"dayOfBirth\" : \"" + dayOfBirth + "\",\n"
			    		+ "\"age\" : \"" + age + "\",\n"
			    		+ "\"height\" : \"" + height + "\",\n"
			    		+ "\"gender\" : \"" + gender + "\",\n"
			    		+ "\"location\" : \"" + location + "\",\n"
			    		+ "\"isAdmin\" : \"" + isAdmin + "\",\n"
			    		+ "\"smokingStatus\" : \"" + smokingStatus + "\"\n"	
			    		+ "}");
		    } else {
		    	response.setContent("{\"status\" : \"false\"}");
		    }
		    
			response.setResponseStatusCode(ResponseStatusCode.OK);
			break;
		
		case CREATE_RECORD:
			JsonElement userJ = params.getAsJsonObject().get("user_id");
			JsonElement recordJ = params.getAsJsonObject().get("record");

			if (userJ == null || recordJ == null) {
				response.setContent("{\"status\" : \"false\"\n}");
		    	response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			}
			String user = userJ.getAsString();
			String record = recordJ.getAsString();			
			
			//Send request to FFT server and handle response.
			String contentString = RequestSender.getFFT(record);
			JsonElement contentJson = gson.fromJson(contentString, JsonElement.class);
			String engCurve = contentJson.getAsJsonObject().get("eng_curve").getAsJsonArray().toString();
			String frmTimes = contentJson.getAsJsonObject().get("frm_times").getAsJsonArray().toString();			
			
			if (contentString == null) {
		    	response.setContent("{\"status\" : \"false\"\n}");
		    	response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			}
			
			//Send request to lean server and handle response.
			String poliServerResponseString = RequestSender.postToPoliLearnServer(contentString);
			JsonElement poliServerResponseJson = gson.fromJson(poliServerResponseString, JsonElement.class);
			String PEF = poliServerResponseJson.getAsJsonObject().get("PEF").getAsString();			
			String FVC = poliServerResponseJson.getAsJsonObject().get("FVC").getAsString();			
			String FEV1 = poliServerResponseJson.getAsJsonObject().get("FEV1").getAsString();			
			String flowCurve = poliServerResponseJson.getAsJsonObject().get("flow_curve").getAsJsonArray().toString();
			String volumes = poliServerResponseJson.getAsJsonObject().get("volumes").getAsJsonArray().toString(); 
			
			if (poliServerResponseString == null) {
		    	response.setContent("{\"status\" : \"false\"}");
		    	response.setResponseStatusCode(ResponseStatusCode.OK);
				break;
			}
			//Save recored to db
			long recordId = DatabaseHandle.getInstance().saveRecord(user, record, engCurve, frmTimes, PEF, FVC, FEV1, flowCurve, volumes);

			//Create response.
			response.setContent(this.customResponse(engCurve, frmTimes, PEF, FVC, FEV1, flowCurve, volumes));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			break;
				
		case GET_TIMELINE:
			temp = params.getAsJsonObject().get("user_id");
			if (temp == null) {
				response.setContent("{\"status\" : \"false\"}");
		    	response.setResponseStatusCode(ResponseStatusCode.OK);
			} else {
				String userId = temp.getAsString();
				List<BasicDBObject> result = DatabaseHandle.getInstance().getTimeline(userId);
				String jsonContent = "{\"result\" : " + result.toString() + "}";
				response.setContent(jsonContent);
				response.setResponseStatusCode(ResponseStatusCode.OK);
			}
			break;
			
		case LOGIN:
			String emailC = params.getAsJsonObject().get("email").getAsString();	
			String passwordC = params.getAsJsonObject().get("password").getAsString();	
		    obj = DatabaseHandle.getInstance().gestUser(emailC, passwordC);			

		    if (obj != null) {
		    	id = obj.getString("_id");
			    name = obj.getString("name");		    
			    email = obj.getString("email");
			    weight = obj.getString("weight");
			    dayOfBirth = obj.getString("dayOfBirth");
			    age = obj.getString("age");		    
			    height = obj.getString("height");
			    gender = obj.getString("gender");
			    location = obj.getString("location");
			    isAdmin = obj.getString("isAdmin");		    
			    smokingStatus = obj.getString("smokingStatus");
				
			    response.setContent("{\"status\" : \"success\",\n"
			    		+ "\"user_id\" : \"" + id + "\",\n"
			    		+ "\"name\" : \"" + name + "\",\n"
			    		+ "\"email\" : \"" + email + "\",\n"
			    		+ "\"weight\" : \"" + weight + "\",\n"
			    		+ "\"dayOfBirth\" : \"" + dayOfBirth + "\",\n"
			    		+ "\"age\" : \"" + age + "\",\n"
			    		+ "\"height\" : \"" + height + "\",\n"
			    		+ "\"gender\" : \"" + gender + "\",\n"
			    		+ "\"location\" : \"" + location + "\",\n"
			    		+ "\"isAdmin\" : \"" + isAdmin + "\",\n"
			    		+ "\"smokingStatus\" : \"" + smokingStatus + "\"\n"	
			    		+ "}");
		    } else {
		    	response.setContent("{\"status\" : \"false\"}");
		    }
		    
			response.setResponseStatusCode(ResponseStatusCode.OK);
			break;
			
		default:
			throw new BadRequestException();
		}
		if(response.getResponseStatusCode() == null){
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}
	
	
	public String customResponse(String engCurve, String frmTimes, String PEF, String FVC, String FEV1, String  flowCurve, String volumes) {
		 String content = "{\"status\" : \"success\",\n" + 
				 	"    \"eng_curve\": " + engCurve + ",\n" + 
					"    \"frm_times\": " + frmTimes + ",\n" + 
					"    \"flow_curve\": " + flowCurve + ",\n" + 
					"    \"volumes\": " + volumes + ",\n" +
					"    \"PEF\": " + PEF + ",\n" + 
					"    \"FVC\": " + FVC + ",\n" + 
					"    \"FEV1\": " + FEV1 + "\n" + 
					"}";
		
		return content;
	}
	
	@Override
	public String getAPOCPath() {
		return SampleConstants.POA;
	}
	
}
