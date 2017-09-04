package mongodb;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class carDownload {
	public static void downloadfile(JTable table, String downloadfilename, String path) {
		int i = 0;
		DB db = main.mongo.getDB("test");
		GridFS gfs = new GridFS(db, batchDownLoad.searchCollection(table, downloadfilename));
		GridFSDBFile gfsFile = gfs.findOne(downloadfilename);
		if (gfsFile != null) {
			//JOptionPane.showMessageDialog(null, "start");
			String name = path + "\\" + gfsFile.getFilename();
			File file = new File(name);
			if (file.exists()) {// 判断文件是否已存在
				JOptionPane.showMessageDialog(null, "文件名重复！");
				return;
			}

			try {
				gfsFile.writeTo(name);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
