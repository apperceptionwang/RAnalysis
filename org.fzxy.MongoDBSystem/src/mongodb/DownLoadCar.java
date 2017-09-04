package mongodb;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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




public class DownLoadCar extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTable table;
	final JTextField textField = new JTextField(50);
	final Set set1 = new TreeSet();
	
	
	
	public DownLoadCar() {
		// TODO Auto-generated constructor stub
		setTitle("下载车");
		setSize(585,600);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = ds.width, y1 = ds.height;
		setLocation((ds.width - 585) / 2, (ds.height - 600) / 2);
		setLocationRelativeTo(null);
		Image icon4 = Toolkit.getDefaultToolkit().getImage("res/diannao.png");
		setIconImage(icon4);
		setResizable(true);

		Vector<String> q = new Vector<String>();
		q.addElement("选择");
		
		q.addElement("文件名");
		q.addElement("修改日期");

		Vector<Vector> p = new Vector<Vector>();// 声明所有行
		
		
		DB db = main.mongo.getDB("Userdownload");
		DBCollection collection = db.getCollection("username");
		DBCursor cur = collection.find();
		boolean b = false;
		while (cur.hasNext()) {
			DBObject obj = cur.next();
			Vector<Object> row =new Vector<Object>();
			
			String[] strings = new HandleStringUpdate2().start(obj.toString());
			
			row.add(b);
			row.add(strings[0]);
			row.add(strings[2]);
			
			p.add(row);
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
		
		 table.setRowHeight(30);
		 table.setDefaultEditor(Object.class, new MyEditor2(box));
		 table.setDefaultRenderer(Object.class, new MyRenderer2());
 
		// 打印全部中的表格的值
		//
		TableColumnModel tcm = table.getColumnModel();				
		TableColumn tc = table.getColumnModel().getColumn(0);
		tc.setPreferredWidth(80);
		TableColumn tc2 = table.getColumnModel().getColumn(1);
		tc2.setPreferredWidth(220);
		TableColumn tc3 = table.getColumnModel().getColumn(2);
		tc3.setPreferredWidth(200);
		
		
		JScrollPane s = new JScrollPane(table);
		JPanel panel = new  JPanel();
		JButton download = new JButton("下载");
		JButton delete = new JButton("删除");
		panel.add(download,BorderLayout.CENTER);
		panel.add(delete,BorderLayout.CENTER);
		
		Container con =getContentPane();
		con.add(panel, BorderLayout.SOUTH);
	    getContentPane().add(s,BorderLayout.CENTER);
		setVisible(true);
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				int selectRows=0;
				for(int i=0;i<table.getRowCount();i++)
				{
			         String s1=table.getValueAt(i, 0).toString();
			         
					if(s1.equals("true"))
					{
						selectRows++;
					}
				}
				if(selectRows==0){
					
					JOptionPane.showMessageDialog(null, "请先选择要删除的数据！");
					return;
				}
				
				for (int i = table.getRowCount() - 1; i >= 0; i--) {

					String s1 = table.getValueAt(i, 0).toString();


					if (s1.equals("true")) {
						
						String id = (String)table.getValueAt(i, 1);
				    	DBCollection dbCol = main.mongo.getDB("Userdownload").getCollection("username");  		   
						Pattern pattern = Pattern.compile("^.*" + id + ".*$", Pattern.CASE_INSENSITIVE);
						BasicDBObject query = new BasicDBObject();
						query.put("_id", pattern); 
				        
				        dbCol.remove(dbCol.findOne(query));
						DefaultTableModel tableModel = (DefaultTableModel) table.getModel();						
						tableModel.removeRow(i);// rowIndex是要删除的行序号
						  
					}
				}
				JOptionPane.showMessageDialog(null, "删除成功！");
				
               
			}
		});
		
		download.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				int selectRows=0;
				for(int i=0;i<table.getRowCount();i++)
				{
			         String s1=table.getValueAt(i, 0).toString();
			         
					if(s1.equals("true"))
					{
						selectRows++;
					}
				}
				if(selectRows==0){
					
					JOptionPane.showMessageDialog(null, "请先选择要下载的数据！");
					return;
				}
				ArrayList<String> filename = new ArrayList<String>();
				for (int i = table.getRowCount() - 1; i >= 0; i--) {
					String s1 = table.getValueAt(i, 0).toString();
					if (s1.equals("true")) {
						
						filename.add(table.getValueAt(i, 2).toString());
					}
				}
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("下载");
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					String Path = fileChooser.getSelectedFile().getAbsolutePath();		
					for (String downloadfilename : filename) {
						
						carDownload.downloadfile(table, downloadfilename, Path);
					}
					
				}
				for (int x = table.getRowCount() - 1; x>= 0; x--) {

					String s1 = table.getValueAt(x, 0).toString();


					if (s1.equals("true")) {
						
						String id = (String)table.getValueAt(x, 1);
				    	DBCollection dbCol = main.mongo.getDB("Userdownload").getCollection("username");  		   
						Pattern pattern = Pattern.compile("^.*" + id + ".*$", Pattern.CASE_INSENSITIVE);
						BasicDBObject query = new BasicDBObject();
						query.put("_id", pattern); 
				        
				        dbCol.remove(dbCol.findOne(query));
						DefaultTableModel tableModel = (DefaultTableModel) table.getModel();						
						tableModel.removeRow(x);// rowIndex是要删除的行序号
						  
					}
				}
				JOptionPane.showMessageDialog(null, "下载完成");
				
			}
			
		});
	}

}
class MyRenderer2 extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		if (column != 0) {
			JTextField jtf = new JTextField();
			jtf.setText(value.toString());
			return jtf;
		}
		table.setShowVerticalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}

class MyEditor2 extends DefaultCellEditor {
	private JTextField box;

	public MyEditor2(JTextField box) {
		super(box);
		// TODO Auto-generated constructor stub
		this.box = box;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
			int column) {
		return this.box;
	}

}


