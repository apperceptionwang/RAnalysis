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
	int iscreate = 1; // �Ƿ񴴽������ļ���0Ϊ������1Ϊ������

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
		String filename = (String) main.table.getValueAt(rows, 1);// ��ȡѡ��ָ���е�
		String name = filename.replaceAll("[.][^.]+$", "") + "desc.txt";
		File desFile = new File(path + name);

		DB db = main.mongo.getDB("test");

		GridFS gfs = new GridFS(db, "describeFile");
		
		GridFSDBFile gfsFile1 = gfs.findOne(name);
		// ���û���ļ�����׺���ļ���

	
		final Set set1 = new TreeSet();

		try {

			File f5 = new File("C:\\db\\tmpTable.txt");// ��������txt��·�����ǵ�·����һ���á�\\"ʵ��
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

		/* �������ݿ����Ƿ�����Ӧ�������ļ� */
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			String[] desname = obj.toString().split(" ");

			String desName = "\""+name+"\"}";
			
			if (desname[desname.length-1].equals(desName)) {
				count++;
				break;
			}
		}
		cur.close();// �ر��α�
		if (count == 0) {// ���ݿ���û�ж�Ӧ�����ļ�

			iscreate = JOptionPane.showConfirmDialog(null, "�����ļ������ڣ��Ƿ񴴽������ļ���",
					"���������ļ�", JOptionPane.YES_NO_OPTION);

			if (iscreate == 0) {// iscreate=0���������ļ�

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
			
			} else {// �����������ļ�������

				return;

			}
		}

		else {// �����ж�Ӧ�����ļ�������ָ��·��
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

		int isupload = JOptionPane.showConfirmDialog(null, "�Ƿ��ϴ������ļ���", null,
				JOptionPane.YES_NO_OPTION);

		if (isupload == 0) {// ��Ҫ�ϴ�
			if(iscreate==0){
				coll.insert(query);
			}
			JOptionPane.showMessageDialog(null, "�ϴ��ɹ�");

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
