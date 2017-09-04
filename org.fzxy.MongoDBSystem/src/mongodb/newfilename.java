package mongodb;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JScrollPane;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class newfilename {
	final Set set1 = new TreeSet();

	public newfilename(String newfilename, String filename, JScrollPane sp2, String name) {
		// TODO Auto-generated constructor stub

		DB db = main.mongo.getDB("test");// 获得一个数据库对象

		File myimage = new File(filename);
		// String fileName = myimage.getName();
		GridFS gfs = new GridFS(db, batchDownLoad.searchCollection(main.table, filename));
		GridFSInputFile gfsFile = null;
		try {

			gfsFile = gfs.createFile(myimage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		gfsFile.setFilename(newfilename);
		gfsFile.save();
		new refresh(main.table, filename, sp2, name);

	}
}
