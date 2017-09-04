package mongodb;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;;

public class VerificationUser {
	public static int Try(String name,String password) throws Exception {
		Mongo mongo=null;
		try{
		
		new ipvalue();
		mongo=new Mongo("127.0.0.1", 27017);
		}
		catch(MongoException e){
			JOptionPane.showMessageDialog(null, "Çë¼ì²éÍøÂçÁ¬½Ó");
		}
		DB db = mongo.getDB("User");
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
		//JOptionPane.showMessageDialog(null, rsstart);
		if(password.equals(rsstart[24])){
				return 1;
		}
		else {
			
			return -1;
		}

		}
	}
