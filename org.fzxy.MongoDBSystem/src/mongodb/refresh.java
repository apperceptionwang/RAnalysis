package mongodb;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class refresh {
	final Set set1 = new TreeSet();

	public refresh(JTable table, String filename, JScrollPane sp2, String name) {

		// ���txt�ļ�
		try {
			// �����ж�����txt���½�

			File f5 = new File(tempfile.temp() + "tmpTable2.txt");// ��������txt��·�����ǵ�·����һ���á�\\"ʵ��
			FileWriter fw5 = new FileWriter(f5);
			BufferedWriter bw1 = new BufferedWriter(fw5);
			bw1.write("");

		} catch (Exception e1) {

		}

		name = "";
		int Resultcount = 0;

		DB db1 = main.mongo.getDB("test");
		Set<String> colls = db1.getCollectionNames();
		Pattern pattern = null;
		for (String collname : colls) {

			DBCollection coll = db1.getCollection(collname);

			if (collname.contains(name)) {
				pattern = Pattern.compile("^.*" + "" + ".*$", Pattern.CASE_INSENSITIVE);
			} else {
				pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
			}

			BasicDBObject query = new BasicDBObject();
			query.put("filename", pattern);
			DBCursor cur = coll.find(query);

			while (cur.hasNext()) {
				DBObject obj = cur.next();
				set1.add(obj.toString());
				Resultcount++;

			}
			cur.close();

		}
		if (Resultcount == 0) {
			JOptionPane.showMessageDialog(null, "���ݿ���������ֶ��ļ�");
		} else {
			Iterator<String> iterator = set1.iterator();
			while (iterator.hasNext()) {
				String s = iterator.next().toString();
				try {
					new handleString().writeInFile(tempfile.temp() + "tmpTable2.txt", s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iterator.remove();
			}
		}
		Vector<String> q = new Vector<String>();
		q.addElement("ѡ��");
		q.addElement("�ļ���");
		q.addElement("����");
		q.addElement("����");
		q.addElement("����");
		q.addElement("ɾ��");
		q.addElement("����");
		q.addElement("�鿴");
		q.addElement("R���Է���");
		Vector<Vector> p = new Vector<Vector>();// ����������
		// ----------------------------------------��ʼ���ı��ļ�
		FileReader reader = null;

		try {

			reader = new FileReader(tempfile.temp() + "tmpTable2.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(reader);

		String eachLine = null;// ����ÿһ��
		try {
			while ((eachLine = br.readLine()) != null) {// ���ļ���ĩβ
				// -----split(String):���ݸ���������ʽ��ƥ���ִ��ַ�����
				String[] temp = eachLine.split(",");// ÿһ����Ŀո�
				// -----����ÿһ�У�����������ⲿ�ǲ��еء�
				Vector<Object> row = new Vector<Object>();
				row.add(new Boolean(false));
				for (int i = 2; i < temp.length; i++) {// ����ÿһ��
					row.add(temp[i]);// ��ÿһ�ж�����row
				}
				p.add(row);// �ٰ�ÿһ��row�����ݸ�rowData
			}

			br.readLine();

			br.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table = new JTable(new DefaultTableModel(p, q) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		TableModel model = new DefaultTableModel(p, q);
		table = new JTable(model);
		// ������
		JTextField box = new JTextField();

		TableColumn aColumn = table.getColumnModel().getColumn(0);
		aColumn.setCellEditor(table.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		
		table.setDefaultEditor(Object.class, new MyEditor(box));
		table.setDefaultRenderer(Object.class, new MyRenderer());

		// ��ӡȫ���еı���ֵ
		//
		TableColumnModel tcm = table.getColumnModel();

		TableColumn tc = table.getColumnModel().getColumn(0);
		table.setRowHeight(20);
		table.setShowVerticalLines(false);
		table.getTableHeader().setReorderingAllowed(false);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tc.setPreferredWidth(80);
		TableColumn tc2 = table.getColumnModel().getColumn(1);
		tc2.setPreferredWidth(200);
		TableColumn tc3 = table.getColumnModel().getColumn(2);
		tc3.setPreferredWidth(200);
		TableColumn tc4 = table.getColumnModel().getColumn(3);
		tc4.setPreferredWidth(94);
		TableColumn tc5 = table.getColumnModel().getColumn(4);
		tc5.setPreferredWidth(94);
		TableColumn tc6 = table.getColumnModel().getColumn(5);
		tc6.setPreferredWidth(94);
		TableColumn tc7 = table.getColumnModel().getColumn(6);
		tc7.setPreferredWidth(94);
		TableColumn tc8 = table.getColumnModel().getColumn(7);
		tc8.setPreferredWidth(94);
		TableColumn tc9 = table.getColumnModel().getColumn(8);
		tc9.setPreferredWidth(94);

		sp2.setViewportView(table);

	}

}

class MyRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// System.out.println("CellRender����.");
		if (column != 0) {
			JTextField jtf = new JTextField();
			jtf.setText(value.toString());
			return jtf;
		}
		table.setShowVerticalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn tc2 = table.getColumnModel().getColumn(1);
		tc2.setPreferredWidth(222);
		TableColumn tc3 = table.getColumnModel().getColumn(2);
		tc3.setPreferredWidth(222);
		TableColumn tc4 = table.getColumnModel().getColumn(3);
		tc4.setPreferredWidth(180);
		TableColumn tc5 = table.getColumnModel().getColumn(4);
		tc5.setPreferredWidth(94);
		TableColumn tc6 = table.getColumnModel().getColumn(5);
		tc6.setPreferredWidth(94);
		TableColumn tc7 = table.getColumnModel().getColumn(6);
		tc7.setPreferredWidth(94);
		TableColumn tc8 = table.getColumnModel().getColumn(7);
		tc8.setPreferredWidth(94);
		TableColumn tc9 = table.getColumnModel().getColumn(8);
		tc9.setPreferredWidth(94);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}

class MyEditor extends DefaultCellEditor {
	private JTextField box;

	public MyEditor(JTextField box) {
		super(box);
		// TODO Auto-generated constructor stub
		this.box = box;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return this.box;
	}

}
