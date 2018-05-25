package org.eclipse.om2m.ipe.sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;
import org.eclipse.om2m.ipe.sample.model.Record;
import org.eclipse.om2m.ipe.sample.model.User;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class DataHelper {
	private static DataHelper singleton = new DataHelper();
	private DataHelper() { }
	
	public static DataHelper getInstance( ) {
		return singleton;
	}
	
	private void DatabaseHandle() {}

	public BasicDBObject saveUser(User user) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_USER);
		long userId = 1;
	    if (database.collectionExists(SampleConstants.TB_USER)) {
		    userId = users.count() + 1;
	    }
		
		String name = user.getName();
		String email = user.getEmail();
		String password = user.getPassword();
		String dayOfBirth = user.getDayOfBirth();
		String weight = user.getWeight();
		String height = user.getHeight();
		String gender = user.getGender();
		String location = user.getLocation();
		String smokingStatus = user.getSmokingStatus();
		String isCaretakers = user.getIsCaretakers();		
		
		BasicDBObject doc1 = new BasicDBObject();
	    doc1.append("_id", String.valueOf(userId));
	    doc1.append("name", name);
	    doc1.append("email", email);
	    doc1.append("password", password);
	    doc1.append("dayOfBirth", dayOfBirth);
	    doc1.append("weight", weight);
	    doc1.append("height", height);
	    doc1.append("gender", gender);
	    doc1.append("location", location);
	    doc1.append("isCaretakers", isCaretakers);
	    doc1.append("smokingStatus", smokingStatus);
	    users.insert(doc1);
		
	    doc1.remove("password");
	    mongoClient.close();
		return doc1;
	}
	
	public boolean unfollowUser(JsonElement params) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection follows = database.getCollection(SampleConstants.TB_FOLLOW);
		
		String follower = params.getAsJsonObject().get("follower").getAsString();
		String followed = params.getAsJsonObject().get("followed").getAsString();
		        
		BasicDBObject document = new BasicDBObject();
		document.put("follower", follower);
		document.put("followed", followed);

		WriteResult remoted = follows.remove(document);
	    mongoClient.close();
	    if (remoted.getN() > 0) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	public BasicDBObject followUser(JsonElement params) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_FOLLOW);
		long userId = 1;
	    if (database.collectionExists(SampleConstants.TB_FOLLOW)) {
		    userId = users.count() + 1;
	    }
		
		String follower = params.getAsJsonObject().get("follower").getAsString();
		String followed = params.getAsJsonObject().get("followed").getAsString();	
		
		BasicDBObject doc1 = new BasicDBObject();
	    doc1.append("_id", String.valueOf(userId));
	    doc1.append("follower", follower);
	    doc1.append("followed", followed);
	    users.insert(doc1);
	    mongoClient.close();
		return doc1;
	}
	
	public List<BasicDBObject> getFollowed(String follower) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection follows = database.getCollection(SampleConstants.TB_FOLLOW);
	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("follower", follower);
		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = follows.find(where);
		List<BasicDBObject> result = new ArrayList<BasicDBObject>();

		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			String id = obj.getString("followed");
		    result.add(getUser(id));
	    }
	    mongoClient.close();
		return result;
	}
	
	public BasicDBObject getUser(String id) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_USER);
	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("_id", id);
		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = users.find(where);

		if(cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			obj.remove("password");
		    mongoClient.close();
		    return obj;
		} else {
		    mongoClient.close();
			return null;
		}		
	}
	
	public long saveRecord(Record record) {		
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection records = database.getCollection(SampleConstants.TB_RECORD);
		long recordId = 1;
	    if (database.collectionExists(SampleConstants.TB_RECORD)) {
	    	recordId = records.count() + 1;
	    }
	    
		try {
			new HadoopHelper().run("RecordID_" + String.valueOf(recordId),record.getRecord());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		BasicDBObject doc1 = new BasicDBObject();
	    doc1.append("_id", String.valueOf(recordId));
	    doc1.append("userId", record.getUserId());
	    doc1.append("engCurve", record.getEngCurve());
	    doc1.append("frmTimes", record.getFrmTimes());
	    doc1.append("PEF", record.getPEF());
	    doc1.append("FVC", record.getFVC());
	    doc1.append("FEV1", record.getFEV1());
	    doc1.append("flowCurve", record.getFlowCurve());
	    doc1.append("volumes", record.getVolumes());
	    doc1.append("time", getToday());
	    doc1.append("pred_PEF", record.getPred_PEF());
	    doc1.append("pred_FEV1", record.getPred_FEV1());
	    doc1.append("pred_FVC", record.getPred_FVC());
	    records.insert(doc1);
		
	    mongoClient.close();
		return recordId;
	}

	
	private String getToday() {
		Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String dateString = df.format(date);
        return dateString;
        
	}
	
	public BasicDBObject gestUser(String email, String password) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_USER);
	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("email", email);
		whereBuilder.append("password", password);
		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = users.find(where);

		if(cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			obj.remove("password");
		    mongoClient.close();
		    return obj;
		} else {
		    mongoClient.close();
			return null;
		}		
	}
	
	public List<BasicDBObject> getTimeline(String userId) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_RECORD);
	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("userId", userId);
		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = users.find(where);
		List<BasicDBObject> result = new ArrayList<BasicDBObject>();

		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			obj.remove("record");
		    result.add(obj);
	    }
	    mongoClient.close();
		return result;
	}
	
	public List<BasicDBObject> searchUser(JsonElement params) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_USER);
		String email = params.getAsJsonObject().get("email").getAsString();	
		String id = params.getAsJsonObject().get("id").getAsString();	

	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("email", email);
		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = users.find(where);
		List<BasicDBObject> result = new ArrayList<BasicDBObject>();

		while (cursor.hasNext()) {			
			BasicDBObject obj = (BasicDBObject) cursor.next();
			String followed = obj.getString("_id");
			
			boolean isFollowed = searchFollow(id, followed);
			if (isFollowed == true) {
				obj.append("followed", "1");
			} else {
				obj.append("followed", "0");
			}
		    result.add(obj);
	    }
	    mongoClient.close();
		return result;
	}
	
	
	public boolean searchFollow(String follower, String followed) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_FOLLOW);
	
		BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
		whereBuilder.append("follower", follower);
		whereBuilder.append("followed", followed);

		DBObject where  = whereBuilder.get();
		        
		// Query
		DBCursor cursor = users.find(where);

		if(cursor.hasNext()) {
		    mongoClient.close();
		    return true;
		} else {
		    mongoClient.close();
			return false;
		}		
	}
	
//	public static void main(String[] args) throws Exception {
//		boolean result = searchFollow("13","13");
//		System.out.println(result);
//	}

}
