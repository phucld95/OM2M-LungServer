package org.eclipse.om2m.ipe.sample;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DatabaseHandle {
	public static final String DB_NAME ="ABC";
	public static final String TB_User ="User";

	public void createMongoClient() {
		 // Creating a Mongo client 
		
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:12345"));
		System.out.println("Connected to the database successfully");  

	      // Accessing the database 
	      DB database = mongoClient.getDB(DB_NAME);
	      DBCollection users = database.getCollection(TB_User);
	     
	      System.out.println("Done!");
	      mongoClient.close();
	      
	      return;
	}
	
	public boolean saveUser(String params) {
		return true;
	}
	
	public boolean saveRecord(String params) {
		return true;
	}
	
	
	public void insertTestData(DBCollection users) {
		// Insert Document 1
	      BasicDBObject doc1 = new BasicDBObject();
	      doc1.append("_id", 10);
	      doc1.append("dept_no", "D10");
	      doc1.append("dept_name", "ACCOUNTING");
	      doc1.append("location", "NEW YORK");
	      users.insert(doc1);
	      
	      // Insert Document 2
	      
	      BasicDBObject doc2 = new BasicDBObject();
	      doc2.append("_id", 20);
	      doc2.append("dept_no", "D20");
	      doc2.append("dept_name", "RESEARCH");
	      doc2.append("location", "DALLAS");
	      doc2.append("description", "First department");
	      users.insert(doc2);
	  
	      // Insert Document 3
	      BasicDBObject doc3 = new BasicDBObject();
	      doc3.append("_id", 30);
	      doc3.append("dept_no", "D30");
	      doc3.append("dept_name", "SALES");
	      doc3.append("location", "CHICAGO");
	      users.insert(doc3);
	  
	      // Insert Document 4
	      BasicDBObject doc4 = new BasicDBObject();
	      doc4.append("_id", 40);
	      doc4.append("dept_no", "D40");
	      doc4.append("dept_name", "OPERATIONS");
	      doc4.append("location", "BOSTON");
	      users.insert(doc4);
	}
}
