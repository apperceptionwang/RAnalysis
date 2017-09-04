package mongodb;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;


public class batchDownLoad {
	public static void start(JTable table, String path, String dbname) {

		DB db = main.mongo.getDB("test");
		for (int i = 0; i < table.getRowCount(); i++)
		{
			String s1 = table.getValueAt(i, 0).toString();

			if (s1.equals("true")) {
				
				String filename = (String) table.getValueAt(i, 2);
				
				GridFSDBFile gfsFile = getSelectCollName(i);
				
				if (path.endsWith("\\")) {
					String name = path + filename;
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
				} else {
					String name = path + "\\" + filename;
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

		
		JOptionPane.showMessageDialog(null, "下载成功");

	}

	public static GridFSDBFile searchGFSFile(JTable table, String filename) {
		//GridFS是MongoDB中的一个内置功能，可以用于存放大量小文件
		GridFS gfs = null;
		GridFSDBFile gfsFile = null;
		Set collections = main.mongo.getDB("test").getCollectionNames();
		Set result = new HashSet<>();
		for (Object object : collections) {
			if (!object.equals("system.indexes")) {
				String collname = object.toString();
				result.add(collname.split("\\.")[0]);
			}
		}
		String[] colls = new String[result.size()];
		int i = 0;
		for (Object object : result) {
			colls[i++] = object.toString();
		}
		for (String string : colls) {
			gfs = new GridFS(main.mongo.getDB("test"), string);
			if (gfs.findOne(filename) != null) {
				gfsFile = gfs.findOne(filename);
			} else {
				continue;
			}
		}
		return gfsFile;

	}

	public static String searchCollection(JTable table, String filename) {
		String coll = null;
		GridFS gfs = null;
		Set collections = main.mongo.getDB("test").getCollectionNames();
		Set result = new HashSet<>();
		for (Object object : collections) {
			if (!object.equals("system.indexes")) {
				String collname = object.toString();
				
				result.add(collname.split("\\.")[0]);
			}
		}
		String[] colls = new String[result.size()];
		int i = 0;
		for (Object object : result) {
			colls[i++] = object.toString();
		}
		for (String string : colls) {
			gfs = new GridFS(main.mongo.getDB("test"), string);
			if (gfs.findOne(filename) != null) {
				coll = string;
			} else {
				continue;
			}
		}
		return coll;

	}

	public static GridFSDBFile getSelectCollName(int i) {
		String filePath = (String) main.table.getValueAt(i, 1);
		String filename = (String) main.table.getValueAt(i, 2);
		String collName = filePath.split("/")[0];
		filePath = "/库/" + filePath + filename;
		DB db = main.mongo.getDB("test");
		GridFS gfs = new GridFS(db,collName);
		GridFSDBFile gfsFile = gfs.findOne(filePath);
		return gfsFile;
	}
}
