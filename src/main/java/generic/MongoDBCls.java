package generic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientURI ;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;

public class MongoDBCls {

	public MongoClient getAllCollection() throws IOException {
		
		FileCredential fc=new FileCredential();
		String mongo_db_url=fc.readCredential("MongoDBUrl");
		String db_nm=fc.readCredential("DatabaseName");
		MongoClientURI uri = new MongoClientURI(mongo_db_url);
		MongoClient mongoClient = new MongoClient(uri);
		
		//MongoDatabase database = mongoClient.getDatabase(db_nm);
		
		 /*for (String name : database.listCollectionNames()) {
	         System.out.println(name);
	      }*/
		 
		return mongoClient;
	}

	/*public MongoCollection getACollection(String CollectionName) throws IOException {
		
		FileCredential fc=new FileCredential();
		String db_nm=fc.readCredential("DatabaseName");
		MongoClient mongoClient = getAllCollection();
		MongoDatabase database=mongoClient.getDatabase(db_nm);
		MongoCollection collection=database.getCollection(CollectionName);
		 
		return collection;
	}*/
	
	/*public static void main(String[] args) throws IOException, ParseException {
		
		MongoDBCls mng=new MongoDBCls();
		
		MongoCollection collection=mng.getACollection("invoices");
		
		Document document = (Document) collection
	              .find(new BasicDBObject("invoice_number", "ee"))
	              .projection(Projections.fields(Projections.include("invoice_date"), Projections.excludeId()))
	              .first();
	}*/
}
