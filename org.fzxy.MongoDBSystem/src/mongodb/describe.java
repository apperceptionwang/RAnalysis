package mongodb;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class describe extends AbstractCellEditor implements TableCellRenderer,
		ActionListener, TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JButton button = null;
	String path = "C:\\db\\";
	String path1 = "C:\\db2\\";
	int iscreate = 1; // 是否创建描述文件，0为创建，1为不创建

	public describe() {
	}


	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int rows = -1;
		for (int i = 0; i < main.table.getRowCount(); i++) {
			String s = main.table.getValueAt(i, 0).toString();
			if(s.equals("true")){
				rows=i;
				break;
			}
		}
		if (rows==-1) {
			
			return ;
		}
		String filename = (String) main.table.getValueAt(rows, 1);// 获取选定指定行的
		String name = filename.replaceAll("[.][^.]+$", "") + "desc.txt";
		File desFile = new File(path + name);

		DB db = main.mongo.getDB("test");

		GridFS gfs = new GridFS(db, "describeFile");
		
		GridFSDBFile gfsFile1 = gfs.findOne(name);
		// 获得没有文件名后缀的文件名

	
		final Set set1 = new TreeSet();

		try {

			File f5 = new File("C:\\db\\tmpTable.txt");// 这里是你txt的路径，记得路径下一级用“\\"实现
			FileWriter fw5 = new FileWriter(f5);
			BufferedWriter bw1 = new BufferedWriter(fw5);
			bw1.write("");

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		DBCollection coll = db.getCollection("describeFile");
		
		Pattern pattern = Pattern.compile("^.*" + name + ".*$",
				Pattern.CASE_INSENSITIVE);
		
		BasicDBObject query = new BasicDBObject();
		query.put("name", name);
		DBCursor cur = coll.find();
		int count = 0;

		/* 搜索数据库中是否有相应的描述文件 */
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			String[] desname = obj.toString().split(" ");

			String desName = "\""+name+"\"}";
			
			if (desname[desname.length-1].equals(desName)) {
				count++;
				break;
			}
		}
		cur.close();// 关闭游标
		if (count == 0) {// 数据库中没有对应描述文件

			iscreate = JOptionPane.showConfirmDialog(null, "描述文件不存在，是否创建描述文件？",
					"创建描述文件", JOptionPane.YES_NO_OPTION);

			if (iscreate == 0) {// iscreate=0创建描述文件

				try {
					desFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					Desktop.getDesktop().open(new File(path + name));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			} else {// 不创建描述文件，返回

				return;

			}
		}

		else {// 库中有对应描述文件，下载指定路径
			try {
				File eFile = new File(path+name);
				if (!eFile.exists()) {
					eFile.createNewFile();
				}
				Desktop.getDesktop().open(eFile);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		
		File file1;
		file1 = new File(path + name);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		int isupload = JOptionPane.showConfirmDialog(null, "是否上传描述文件？", null,
				JOptionPane.YES_NO_OPTION);

		if (isupload == 0) {// 需要上传
			if(iscreate==0){
				coll.insert(query);
			}
			JOptionPane.showMessageDialog(null, "上传成功");

			iscreate++;

		}

		
}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

}
