package mongodb;

import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;;

public class VerificationRegister {
	public static int Try(String name,String password) throws Exception {
		DB db = main.mongo.getDB("Register");
		DBCollection dbCollection = db.getCollection("table");
		Pattern pattern = Pattern.compile("^"+name+"$", Pattern.CASE_INSENSITIVE);
		BasicDBObject query = new BasicDBObject();
		query.put("username",pattern);
		DBObject dbObject = dbCollection.findOne(query);
		if(dbObject==null){
			
			return 0;
		}
		String s = dbObject.toString();
		String rsstart[] = s.split("\\{|\\,|\\}|\\_|\"|\\$|\\:|T");
		
		if(password.equals(rsstart[24])){
				return 1;
		}
		else {
			
			return -1;
		}

		}
	}
