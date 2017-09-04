package mongodb;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.rosuda.JRI.Rengine;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;

/**
 * XmlJTree class
 * 
 * @author Ibrabel
 */
@SuppressWarnings("serial")
public class main {

	DefaultTreeModel dtModel = null;

	static JTree myTree;
	static MyNode root;
	static JTable table;
	static JScrollPane sp2 = new JScrollPane();
	static JFrame myFrame = null;
	static Mongo mongo = null;
	static File file = new File("C:\\db\\" + "tmpTable3.txt");
	static String ss = "";
	static Rengine re = null;
	private JPopupMenu tableMenu;
	static JMenuItem fileselectAllItem = new JMenuItem("ȫѡ");
	static JMenuItem filedownloadItem = new JMenuItem("����");
	static JMenuItem fileupdataItem = new JMenuItem("����");
	static JMenuItem filedeleteItem = new JMenuItem("ɾ��");
	static JMenuItem filedescribeItem = new JMenuItem("����");
	static JMenuItem fileviewItem = new JMenuItem("�鿴");
	static JMenuItem fileR = new JMenuItem("R���Է���");
	static JMenuItem add;
	static JMenuItem delete;
	static JMenuItem modify;
	static JMenuItem upload;
	static JMenuItem downloadFile;
	static String realName = null;

	public static void main(String[] args) throws IOException {

		try {
			mongo = new Mongo("127.0.0.1", 27017);
		} catch (MongoException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			new loginframe();// �½�����

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public main() {

		myFrame = new JFrame();

		Container contentPane = myFrame.getContentPane();
		Container con1 = new Container();
		JPanel jp = new JPanel();
		myFrame.setTitle("������");

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane sp1 = new JScrollPane();

		/*---------------�Ҽ��˵�  S------------------*/
		tableMenu = new JPopupMenu();

		tableMenu.add(fileselectAllItem);
		tableMenu.add(filedownloadItem);
		tableMenu.add(fileviewItem);
		// tableMenu.add(fileupdataItem);
		tableMenu.add(filedeleteItem);
		//tableMenu.add(filedescribeItem);
		tableMenu.add(fileR);

		myFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				try {
					cTree.saveNode();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		/*---------------�Ҽ��˵�  E------------------*/

		root = new MyNode(new ImageIcon(icon.FOLDER_OPEN), "��");

		try {
			cTree.readNode();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		myTree = new JTree(root);
		myTree.setRootVisible(false);
		
		myTree.setCellRenderer(new MyNodeRenderer());
		DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) myTree
				.getCellRenderer();// ��ȡ������Renderer
		cellRenderer.setClosedIcon(new ImageIcon(icon.FOLDER_CLOSE));// �رմ�ͼ��
		cellRenderer.setOpenIcon(new ImageIcon(icon.FOLDER_OPEN));// ����չ��ͼ��

		sp1.add(myTree);
		sp1.setViewportView(myTree);
		JSplitPane splitPane = new JSplitPane();// ����һ��������
		splitPane.setOneTouchExpandable(false);// �÷ָ��߲���ʾ����ͷ
		splitPane.setContinuousLayout(true);
		splitPane.setPreferredSize(new Dimension(800, 400));
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// ���÷ָ���Ϊˮƽ�ָ���
		splitPane.setLeftComponent(sp1);
		splitPane.setRightComponent(sp2);
		splitPane.setDividerLocation(2);
		splitPane.setDividerLocation(250);

		final JTextField textField = new JTextField(50);
		JButton search = new JButton("����");
		final Set data = new TreeSet();
		describe fdes = new describe();
		filedescribeItem.addActionListener(fdes);
		RAnalysis rfenxi = new RAnalysis(table);
		fileR.addActionListener(rfenxi);
		View fview = new View();
		fileviewItem.addActionListener(fview);
		selectall fileSelectall = new selectall();
		fileselectAllItem.addActionListener(fileSelectall);

		// ����
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// ���txt�ļ�
				try {
					// �����ж�����txt���½�

					File f5 = new File(tempfile.temp() + "tmpTable.txt");// ��������txt��·�����ǵ�·����һ���á�\\"ʵ��
					FileWriter fw5 = new FileWriter(f5);
					BufferedWriter bw1 = new BufferedWriter(fw5);
					bw1.write("");

				} catch (Exception e1) {

				}

				String searchconfition = textField.getText();
				int Resultcount = 0;

				DB db1 = main.mongo.getDB("test");
				Set<String> colls = db1.getCollectionNames();
				Pattern pattern = null;
				for (String collname : colls) {

					DBCollection coll = db1.getCollection(collname);

					if (collname.contains(searchconfition)) {
						pattern = Pattern.compile("^.*" + "" + ".*$",
								Pattern.CASE_INSENSITIVE);
					} else {
						pattern = Pattern.compile("^.*" + searchconfition
								+ ".*$", Pattern.CASE_INSENSITIVE);
					}

					BasicDBObject query = new BasicDBObject();
					query.put("filename", pattern);
					DBCursor cur = coll.find(query);

					while (cur.hasNext()) {
						DBObject obj = cur.next();
						data.add(obj.toString());
						Resultcount++;

					}
					cur.close();

				}
				if (Resultcount == 0) {
					JOptionPane.showMessageDialog(null, "���ݿ���������ֶ��ļ�");
				} else {
					Iterator<String> iterator = data.iterator();
					while (iterator.hasNext()) {
						String s = iterator.next().toString();
						new handleString().writeInFile(s);
						iterator.remove();
					}
				}
				Vector<String> q = new Vector<String>();
				q.addElement("ѡ��");
				q.addElement("·��");
				q.addElement("�ļ���");
				q.addElement("�޸�����");

				Vector<Vector> p = new Vector<Vector>();// ����������
				// ----------------------------------------��ʼ���ı��ļ�
				FileReader reader = null;

				try {
					reader = new FileReader(tempfile.temp() + "tmpTable.txt");
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
						String[] paths = temp[2].split("/");
						String path = "";
						for (int i = 2; i < paths.length-1; i++) {
							path += paths[i]+ "/";
						}
						temp[2] = temp[2].split("/")[temp[2].split("/").length-1];
						temp[1] = path;
						// -----����ÿһ�У�����������ⲿ�ǲ��еء�
						Vector<Object> row = new Vector<Object>();
						row.add(new Boolean(false));
						for (int i = 1; i < temp.length; i++) {// ����ÿһ��
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
					public boolean isCellEditable(int row, int column) { // jtable�����Ƿ�ɱ��༭
						if (column == 0) {// ѡ����ܱ��༭
							return true;
						}
						return false;
					}
				});

				// ������
				JTextField box = new JTextField();

				TableColumn aColumn = table.getColumnModel().getColumn(0);
				aColumn.setCellEditor(table.getDefaultEditor(Boolean.class));
				aColumn.setCellRenderer(table.getDefaultRenderer(Boolean.class));

				table.setDefaultEditor(Object.class, new MyEditor(box));
				table.setDefaultRenderer(Object.class, new MyRenderer());

				table.getColumnModel();

				TableColumn tc = table.getColumnModel().getColumn(0);
				table.setRowHeight(30);
				table.setShowVerticalLines(false);
				table.getTableHeader().setReorderingAllowed(false);

				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				tc.setPreferredWidth(160);
				TableColumn tc2 = table.getColumnModel().getColumn(1);
				tc2.setPreferredWidth(200);
				TableColumn tc3 = table.getColumnModel().getColumn(2);
				tc3.setPreferredWidth(470);
				TableColumn tc4 = table.getColumnModel().getColumn(3);
				tc4.setPreferredWidth(470);

				rightClickMenu rcm = new rightClickMenu();
				table.addMouseListener(rcm);

				sp2.setViewportView(table);
			}
		});

		search.setSize(1, 1);

		final JPopupMenu popup = new JPopupMenu();
		add = new JMenuItem("������Ŀ¼");
		delete = new JMenuItem("ɾ������Ŀ¼");
		modify = new JMenuItem("������");
		upload = new JMenuItem("�ϴ��ļ�");
		downloadFile = new JMenuItem("����");

		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Adddoc.add();
			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Deletedoc.del();
			}
		});

		upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyNode selectedNode = (MyNode) myTree
						.getLastSelectedPathComponent();
			
				if (!selectedNode.getAllowsChildren()) {
					JOptionPane.showMessageDialog(null, "�޷��ڴ�Ŀ¼�ϴ��ļ�");
					return;
				}

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("�ϴ���test���ݿ�");
				fileChooser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				int returnVal = fileChooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile()
							.getAbsolutePath();

					String name = fileChooser.getSelectedFile().getName();

					if (uploadFiles.addtree(selectedNode, filePath, name, true)) {
						myTree.updateUI();
						JOptionPane.showMessageDialog(null, "�ϴ��ɹ�");
					} else {
						JOptionPane.showMessageDialog(null, "�ϴ�ʧ��");
					}

				}

			}

		});

		modify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TreePath selectpath = main.myTree.getSelectionPath();
				RenameTree.rename(selectpath, false);
			}
		});

		downloadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DownloadFormTree.download();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		popup.add(add);
		popup.add(delete);
		popup.add(modify);
		popup.add(upload);
		popup.add(downloadFile);

		final Set set = new TreeSet();

		myTree.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				int count = root.getChildCount() - 1;
				TreePath t = myTree.getClosestPathForLocation(e.getX(), e.getY());
				
				if (root.getChildAt(count) == t.getLastPathComponent()) {//����հ�λ��
					TreePath t1 = new TreePath(root.getPath());
					myTree.setSelectionPath(t1);
				}else {
					TreePath t1 = myTree.getClosestPathForLocation(e.getX(), e.getY());
					myTree.setSelectionPath(t1);
				}
				if (e.isPopupTrigger()) {
					if (((MyNode) myTree.getLastSelectedPathComponent()).isRoot()) {
						modify.setEnabled(false);
						delete.setEnabled(false);
						downloadFile.setEnabled(false);
					} else {
						modify.setEnabled(true);
						delete.setEnabled(true);
						downloadFile.setEnabled(true);
					}
					popup.show(e.getComponent(), e.getX(), e.getY());// �����Ҽ��˵�
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		JButton batchdelete = new JButton("����ɾ��");
		JButton batchdownload = new JButton("��������");
		JButton downloadcar = new JButton("�������س�");
		JButton opendownloadcar = new JButton("�����س�");

		/* �����س� */
		opendownloadcar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				new DownLoadCar();

			}

		});

		/* �������س� */
		downloadcar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				File file = new File("C:\\db\\" + "tmpTable3.txt");
				String[] str = new String[6];
				FileWriter fw = null;

				try {
					fw = new FileWriter(file, true);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				for (int i = 0; i < table.getRowCount(); i++) {

					String s = table.getValueAt(i, 0).toString();

					if (s.equals("true")) {

						str[0] = table.getValueAt(i, 1).toString();
						str[1] = ",";
						str[2] = table.getValueAt(i, 2).toString();
						str[5] = "\n";

						if (ss.indexOf(str[0]) == -1) {

							String string = "false,";
							for (int j = 0; j < str.length; j++) {

								string += (String) str[j];

							}

							try {
								fw.write(string);
								fw.flush();
								DB db = mongo.getDB("Userdownload");
								DBCollection collection = db
										.getCollection("username");
								BasicDBObject object = new BasicDBObject();
								object.put("_id", str[0]);
								object.put("name", str[2]);
								object.put("date", str[4]);
								collection.insert(object);

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						ss += str[0];
					}

				}
				try {
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				new ShopSuccessWindow();

			}

		});

		downloadAction download = new downloadAction();
		batchdownload.addActionListener(download);
		filedownloadItem.addActionListener(download);

		deleteAction deletefile = new deleteAction();
		batchdelete.addActionListener(deletefile);
		filedeleteItem.addActionListener(deletefile);

		ImageIcon icon3 = new ImageIcon(this.getClass()
				.getResource("/logo.jpg"));
		JLabel label = new JLabel(icon3);
		label.setBounds(0, 20, icon3.getIconWidth(), icon3.getIconHeight());
		myFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		JPanel jp2 = (JPanel) myFrame.getContentPane();
		jp2.setOpaque(false);// ����͸��
		jp.setOpaque(false);// ҲҪ����͸��

		con1.setLayout(new FlowLayout());
		
		jp.setLayout(new FlowLayout());
		jp.add(textField);
		jp.add(search);

		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(con1, BorderLayout.SOUTH);
		contentPane.add(jp, BorderLayout.NORTH);

		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		Image icon4 = Toolkit.getDefaultToolkit().getImage("res/diannao.png");
		myFrame.setIconImage(icon4);
		myFrame.setSize(ds.width, ds.height - 40);
		myFrame.setVisible(true);
	}

	class MyRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (column != 0) {
				JTextField jtf = new JTextField();
				jtf.setText(value.toString());
				return jtf;
			}
			table.setShowVerticalLines(false);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			return super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
		}
	}

	class MyEditor extends DefaultCellEditor {
		private JTextField box;

		public MyEditor(JTextField box) {
			super(box);
			// TODO Auto-generated constructor stub
			this.box = box;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			return this.box;
		}
	}

	class rightClickMenu implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.isPopupTrigger()) {
				int rows = 2;
				int count = 0;
				for (int i = 0; i < main.table.getRowCount() && rows != 0; i++) {
					if ((boolean) main.table.getValueAt(i, 0)) {
						count = i;
						rows--;
					}
				}
				if (rows == 0) {
					filedescribeItem.setEnabled(false);
					fileviewItem.setEnabled(false);
					fileR.setEnabled(false);
					filedeleteItem.setEnabled(true);
					filedownloadItem.setEnabled(true);
				} else if (rows == 2) {
					filedescribeItem.setEnabled(false);
					fileviewItem.setEnabled(false);
					fileR.setEnabled(false);
					filedeleteItem.setEnabled(false);
					filedownloadItem.setEnabled(false);
					fileR.setEnabled(false);
				} else {
					String filename = (String) main.table.getValueAt(count, 1);
					String[] s = filename.split("\\.");
					String suff = s[s.length-1];
				
					if (suff.equals("xls") || suff.equals("xlsx")) {
						fileR.setEnabled(true);
					}else {
						fileR.setEnabled(false);
					}
					filedescribeItem.setEnabled(true);
					fileviewItem.setEnabled(true);
					
					filedeleteItem.setEnabled(true);
					filedownloadItem.setEnabled(true);
					
				}
				tableMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	class downloadAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectRows = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				String s1 = table.getValueAt(i, 0).toString();

				if (s1.equals("true")) {
					selectRows++;
				}
			}
			if (selectRows == 0) {

				JOptionPane.showMessageDialog(null, "����ѡ��Ҫ���ص����ݣ�");
				return;
			}

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("��������");
			int returnVal = fileChooser.showOpenDialog(fileChooser);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String Path = fileChooser.getSelectedFile().getAbsolutePath();

				batchDownLoad.start(table, Path, "test");
			}

			/**
			 * upDownLoad�����start��������ʵ���ļ����� chose2������ǰ���������󴫵ݲ��������������ļ��� ָ��Ŀ¼
			 */
		}
	}

	class deleteAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent Event) {
			int count = 0;
			for (int i = table.getRowCount() - 1; i >= 0; i--) {

				String s1 = table.getValueAt(i, 0).toString();

				if (s1.equals("true")) {
					count = 1;
					break;
				}
			}

			if (count == 0) {

				JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ�������ݣ�");
				return;

			}

			for (int i = table.getRowCount() - 1; i >= 0; i--) {

				String s1 = table.getValueAt(i, 0).toString();

				if (s1.equals("true")) {
					String filename = (String) table.getValueAt(i, 1);
					String collName = filename.split("/")[0];
					filename = "/��/" + filename + table.getValueAt(i, 1).toString();

					DefaultTableModel tableModel = (DefaultTableModel) table
							.getModel();

					DB db = mongo.getDB("test");
					GridFS gfs = new GridFS(db,collName);
					gfs.remove(batchDownLoad.getSelectCollName(i));
					
					tableModel.removeRow(i);// rowIndex��Ҫɾ���������

				}
			}
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		}
	}

}