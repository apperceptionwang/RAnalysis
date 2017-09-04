package mongodb;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;


public class remove {
	@SuppressWarnings("deprecation")
	public static void start(String filename,String db2,String coll2)
	{

		DB db = main.mongo.getDB(db2);
	    GridFS gfs = new GridFS(db,coll2);
	    gfs.remove(gfs.findOne(filename));
	}

}
