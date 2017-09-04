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

		// 清空txt文件
		try {
			// 增加判断如有txt不新建

			File f5 = new File(tempfile.temp() + "tmpTable2.txt");// 这里是你txt的路径，记得路径下一级用“\\"实现
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
			JOptionPane.showMessageDialog(null, "数据库中无相关字段文件");
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
		q.addElement("选择");
		q.addElement("文件名");
		q.addElement("日期");
		q.addElement("下载");
		q.addElement("更新");
		q.addElement("删除");
		q.addElement("描述");
		q.addElement("查看");
		q.addElement("R语言分析");
		Vector<Vector> p = new Vector<Vector>();// 声明所有行
		// ----------------------------------------开始读文本文件
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

		String eachLine = null;// 定义每一行
		try {
			while ((eachLine = br.readLine()) != null) {// 读文件至末尾
				// -----split(String):根据给定正则表达式的匹配拆分此字符串。
				String[] temp = eachLine.split(",");// 每一行里的空格
				// -----声明每一行，必须在这里，外部是不行地。
				Vector<Object> row = new Vector<Object>();
				row.add(new Boolean(false));
				for (int i = 2; i < temp.length; i++) {// 遍历每一行
					row.add(temp[i]);// 把每一行都加入row
				}
				p.add(row);// 再把每一个row的数据给rowData
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
		// 重命名
		JTextField box = new JTextField();

		TableColumn aColumn = table.getColumnModel().getColumn(0);
		aColumn.setCellEditor(table.getDefaultEditor(Boolean.class));
		aColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		
		table.setDefaultEditor(Object.class, new MyEditor(box));
		table.setDefaultRenderer(Object.class, new MyRenderer());

		// 打印全部中的表格的值
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
		// System.out.println("CellRender调用.");
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
