package org.eclipse.om2m.ipe.sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;


import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.controller.SampleController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mongodb.BasicDBObject;

public class RequestSender {
	
	/**
	 * Private constructor to avoid creation of this object
	 */
	private RequestSender(){}
	
	public static ResponsePrimitive createResource(String targetId, Resource resource, int resourceType){
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setResourceType(BigInteger.valueOf(resourceType));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setContent(resource);
		request.setOperation(Operation.CREATE);
		return SampleController.CSE.doRequest(request);
	}
	
	public static ResponsePrimitive createAE(AE resource){
		return createResource("/" + Constants.CSE_ID, resource, ResourceType.AE);
	}
	
	public static ResponsePrimitive createContainer(String targetId, Container resource){
		return createResource(targetId, resource, ResourceType.CONTAINER);
	}
	
	public static ResponsePrimitive createContentInstance(String targetId, ContentInstance resource){
		return createResource(targetId, resource, ResourceType.CONTENT_INSTANCE);
	}
	

	public static ResponsePrimitive getRequest(String targetId){
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setRequestContentType(MimeMediaType.OBJ);
		return SampleController.CSE.doRequest(request);
	}
	
	public static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, today).getYears();
        } else {
            return 0;
        }
    }
	
	public static String getFFT(String record, String id) {
		BasicDBObject user = DataHelper.getInstance().getUser(id);
		String height = user.getString("height");
		String weight = user.getString("weight");
		String date = user.getString("dayOfBirth");
		
		String strArray[] = date.split("-") ;
		date = "";
		date = date + strArray[2] + "-" + strArray[1] + "-" + strArray[0];
		 
		LocalDate localDate = LocalDate.parse(date);
	    int age = calculateAge(localDate);		
	    String params = "{ \"stream_file\" : \"" + record +"\","
	    		+ "\"height\" : \"" + height + "\","
	    		+ "\"weight\" : \"" + weight + "\","
	    		+ "\"age\" : \"" + age + "\"}";
	    String content = excutePost(SampleConstants.FFT_BASE_URL, params);
	    return content;
	}
	
	public static String postToPoliLearnServer(String params) {		
		String content = excutePost(SampleConstants.POLI_BASE_URL, params);
		System.out.println(content);
		return content;
	}
	
	private static String excutePost(String targetURL, String urlParameters){
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/json");
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	}
}