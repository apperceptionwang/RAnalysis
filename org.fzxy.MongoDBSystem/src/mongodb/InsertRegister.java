package mongodb;
import javax.swing.JOptionPane;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class InsertRegister {

	@SuppressWarnings("deprecation")
	public int start(String user,String password,String workLocation,String contact,String  phone,String  permission) throws Exception
	{
		DB db=main.mongo.getDB("User");
		DBCollection  coll=db.getCollection("table");
		DBObject doc=new BasicDBObject();
		       
		   doc.put("username",user);
		   doc.put("password",password);
		   doc.put("workLocation",workLocation);
		   doc.put("contact", contact);
		   doc.put("phone", phone);
		   doc.put("permission", permission);
		   DBObject [] colls=new DBObject[1];
		   colls[0]=doc;
		   coll.insert(colls);

		   int s =new VerificationRegister().Try(user, password);
		   if(s==1){
			   JOptionPane.showMessageDialog(null, "注册请求成功，请等待管理员认证");
			   
		   }
		   else {
			JOptionPane.showMessageDialog(null, "注册失败，请检查网络连接");
		}
		   return 0 ;
	}

	private Mongo Mongo(String string, int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
