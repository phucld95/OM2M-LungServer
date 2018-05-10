package org.eclipse.om2m.ipe.sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DBCursor;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.eclipse.om2m.ipe.sample.constants.SampleConstants;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class DatabaseHandle {
	private static DatabaseHandle singleton = new DatabaseHandle();
	private DatabaseHandle() { }
	
	public static DatabaseHandle getInstance( ) {
		return singleton;
	}
	
	private void DatabaseHandle() {}

	public BasicDBObject saveUser(JsonElement params) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection users = database.getCollection(SampleConstants.TB_USER);
		long userId = 1;
	    if (database.collectionExists(SampleConstants.TB_USER)) {
		    userId = users.count() + 1;
	    }
		
		String name = params.getAsJsonObject().get("name").getAsString();;
		String age = params.getAsJsonObject().get("age").getAsString();
		String email = params.getAsJsonObject().get("email").getAsString();;
		String password = params.getAsJsonObject().get("password").getAsString();
		String dayOfBirth = params.getAsJsonObject().get("day_of_birth").getAsString();
		String weight = params.getAsJsonObject().get("weight").getAsString();
		String height = params.getAsJsonObject().get("height").getAsString();
		String gender = params.getAsJsonObject().get("gender").getAsString();
		String location = params.getAsJsonObject().get("location").getAsString();
		String smokingStatus = params.getAsJsonObject().get("smoking_status").getAsString();
						
		BasicDBObject doc1 = new BasicDBObject();
	    doc1.append("_id", String.valueOf(userId));
	    doc1.append("name", name);
	    doc1.append("email", email);
	    doc1.append("password", password);
	    doc1.append("dayOfBirth", dayOfBirth);
	    doc1.append("weight", weight);
	    doc1.append("age", age);
	    doc1.append("height", height);
	    doc1.append("gender", gender);
	    doc1.append("location", location);
	    doc1.append("isAdmin", "0");
	    doc1.append("smokingStatus", smokingStatus);
	    users.insert(doc1);
		
	    doc1.remove("password");
	    mongoClient.close();
		return doc1;
	}
	
	public long saveRecord(String user, String record, String engCurve, String frmTimes,String PEF, String FVC, String FEV1, String  flowCurve, String volumes) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(SampleConstants.DB_SERVER));
	    DB database = mongoClient.getDB(SampleConstants.DB_NAME);
		DBCollection records = database.getCollection(SampleConstants.TB_RECORD);
		long recordId = 1;
	    if (database.collectionExists(SampleConstants.TB_RECORD)) {
	    	recordId = records.count() + 1;
	    }
						
		BasicDBObject doc1 = new BasicDBObject();
	    doc1.append("_id", String.valueOf(recordId));
	    doc1.append("userId", user);
	    doc1.append("record", record);
	    doc1.append("engCurve", engCurve);
	    doc1.append("frmTimes", frmTimes);
	    doc1.append("PEF", PEF);
	    doc1.append("FVC", FVC);
	    doc1.append("FEV1", FEV1);
	    doc1.append("flowCurve", flowCurve);
	    doc1.append("volumes", volumes);
	    records.insert(doc1);
		
	    mongoClient.close();
		return recordId;
	}
	
//	
//	public static void main(String[] args) throws Exception {
//		 BasicDBObject obj = gestUser("lephuc@gmail.com", "12345678");
//		 System.out.println(obj);
//	   }
	
	public static BasicDBObject gestUser(String email, String password) {
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

}
