package mongodb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.math.R.RserverConf;
import org.math.R.Rsession;
import org.rosuda.REngine.REXPMismatchException;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RAnalysis extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private int count;
	private JButton button = null;
	
	public RAnalysis(JTable jTable) {
		table = jTable;
	}
			@Override
			public void actionPerformed(ActionEvent e1) {
				// TODO Auto-generated method stub
				frame = new JFrame("�����ھ�");
				

				Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();

				frame.setSize(new Dimension((int) (ds.width * 0.6), (int) (ds.height * 0.8)));
				
				frame.setLocation((ds.width - 1000) / 2, (ds.height - 800) / 2);
				frame.setLocationRelativeTo(null);
				Image icon4 = Toolkit.getDefaultToolkit().getImage("res/diannao.png");
				frame.setIconImage(icon4);
				frame.setResizable(false);

				File file = new File("c:\\db\\test.xls");
				
				Workbook wb = null;
				try {
					InputStream inputStream = new FileInputStream(file);
					String fileName = file.getName();
					if (fileName.endsWith("xls")) {
						wb = new HSSFWorkbook(inputStream);// ����xls��ʽ
					} else if (fileName.endsWith("xlsx")) {
						wb = new XSSFWorkbook(inputStream);// ����xlsx��ʽ
					}
				} catch (FileNotFoundException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (IOException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
				Sheet sheet = wb.getSheetAt(0);// ��һ��������
				Row row;
				int firstRowIndex = sheet.getFirstRowNum();
				int lastRowIndex = sheet.getLastRowNum();
				row = sheet.getRow(firstRowIndex);
				int firstCellIndex = row.getFirstCellNum();
				int lastCellIndex = row.getLastCellNum();
				int rowNum = lastRowIndex - firstRowIndex + 1;
				int columuNum = lastCellIndex - firstCellIndex;

				Vector<String> columuNames = new Vector<String>();
				columuNames.addElement(" ");
				for (int i = 0; i < columuNum; i++) {
					columuNames.addElement(sheet.getRow(0).getCell(i).toString());
					//columuNames.add("");
				}

				Vector<Vector> tableData = new Vector<Vector>();
				for (int i = 1; i < rowNum; i++) {
					Vector<Object> rowData = new Vector<Object>();
					rowData.add("");
					for (int j = 0; j < columuNum; j++) {

						if (sheet.getRow(i).getCell(j) == null) {
							rowData.add(" ");
						} else {
							rowData.add(sheet.getRow(i).getCell(j).toString());
						}
					}
					tableData.add(rowData);
				}

				final TableModel model = new DefaultTableModel(tableData, columuNames) {

					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
						if (column == 0) {

							return false;

						}
						return true;
					}
				};
				table = new JTable(model);
				// �����и�
				table.setRowHeight(20);
				// ���ñ���в����ƶ�
				table.getTableHeader().setReorderingAllowed(false);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// ���ñ���С���ɱ�
				table.setColumnSelectionAllowed(true);
				table.setRowSelectionAllowed(true);
				// ���ñ�����ݾ�����ʾ
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
				table.setDefaultRenderer(Object.class, renderer);
				// ��ӡȫ���еı���ֵ
				TableColumn[] tc = new TableColumn[columuNum + 1];
				tc[0]=table.getColumnModel().getColumn(0);
				tc[0].setPreferredWidth(40);
				for (int i = 1; i < columuNum + 1; i++) {

					tc[i] = table.getColumnModel().getColumn(i);
					tc[i].setPreferredWidth(80);
				}

				table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

					/*
					 * (non-Javadoc) �˷��������򷽷������߷���ĳһ��Ԫ�����Ⱦ��������ʾ���ݵ��齨--��ؼ���
					 * ����ΪJCheckBox JComboBox JTextArea ��
					 * 
					 * @see javax.swing.table.TableCellRenderer#
					 * getTableCellRendererComponent(javax.swing.JTable,
					 * java.lang.Object, boolean, boolean, int, int)
					 */
					@Override
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						// �������ڷ��ص���Ⱦ���
						// JCheckBox ck = new JCheckBox();
						// ʹ���н�����ж�Ӧ�ĸ�ѡ��ѡ��
						if (isSelected) {
							table.setColumnSelectionInterval(1, table.getColumnCount() - 1);
							table.setRowSelectionInterval(row, row);
						}
						JTextField jTextField = new JTextField(value.toString());
						//jTextField.setBorder(null);
						jTextField.setHorizontalAlignment((int) 0.5f);
						// ck.setSelected(isSelected);
						// ���õ�ѡbox.setSelected(hasFocus);
						// ʹ��ѡ���ڵ�Ԫ���ھ�����ʾ
						// ck.setHorizontalAlignment((int) 0.5f);
						jTextField.setBackground(Color.gray);
						return jTextField;

					}
				});

				final JTableHeader tableHeader = table.getTableHeader();
				tableHeader.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

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
						int col = tableHeader.columnAtPoint(e.getPoint());// ��õ������ǵڼ���
						table.setColumnSelectionInterval(col, col);
						table.setRowSelectionInterval(0, table.getRowCount() - 1);
					}
				});

				final JScrollPane jScrollPane = new JScrollPane();
				jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jScrollPane.setViewportView(table);
				final JPanel panel = new JPanel();
				panel.setLayout(null);
				JButton averButton = new JButton("��ֵ");
				averButton.setBounds(10, 5, 68, 30);
				panel.add(averButton);

				JButton sumButton = new JButton("���");
				sumButton.setBounds(88, 5, 68, 30);
				panel.add(sumButton);

				JButton varButton = new JButton("����");
				varButton.setBounds(166, 5, 68, 30);
				panel.add(varButton);

				JButton calButton = new JButton("�궨");
				calButton.setBounds(244, 5, 68, 30);
				panel.add(calButton);

				JButton classButton = new JButton("����");
				classButton.setBounds(322, 5, 68, 30);
				panel.add(classButton);
				frame.getContentPane().add(panel);

				JButton hist = new JButton(" ֱ��ͼ");
				hist.setBounds(400, 5, 68, 30);
				panel.add(hist);
				frame.getContentPane().add(panel);
				
				JButton pie = new JButton("��ͼ");
				pie.setBounds(478, 5, 68, 30);
				panel.add(pie);
				frame.getContentPane().add(panel);

				JLabel label = new JLabel("�����");
				label.setBounds(10, 38, 42, 30);
				panel.add(label);

				textField_1 = new JTextField();
				textField_1.setBounds(55, 41, 335, 27);
				panel.add(textField_1);
				textField_1.setColumns(10);
				frame.getContentPane().add(jScrollPane);
				frame.setVisible(true);
				frame.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent arg0) {

						panel.setLocation(0, 0);
						panel.setSize(frame.getWidth(), 80);
						jScrollPane.setLocation(0, panel.getHeight());
						jScrollPane.setSize(frame.getWidth() - 10, frame.getHeight() - panel.getHeight() - 30);

					}
				});

				averButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						float sum = 0;
						int count = 0;
						ArrayList<Float> value = new ArrayList<>();
						for (int i = 0; i < table.getSelectedRowCount(); i++) {

							for (int j = 0; j < table.getSelectedColumnCount(); j++) {
								if (table.getValueAt(table.getSelectedRow() + i,
										table.getSelectedColumn() + j) != null) {
									sum = sum + Float.parseFloat((String) table.getValueAt(table.getSelectedRow() + i,
											table.getSelectedColumn() + j));
								} else {
									sum = sum + 0;
								}
								count++;
							}

						}

						System.out.println(sum / count);
						textField_1.setText(String.valueOf(sum / count));
					}
				});

				sumButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

						float sum = 0;
						ArrayList<Float> value = new ArrayList<>();
						for (int i = 0; i < table.getSelectedRowCount(); i++) {

							for (int j = 0; j < table.getSelectedColumnCount(); j++) {
								if (table.getValueAt(table.getSelectedRow() + i,
										table.getSelectedColumn() + j) != null) {
									sum = sum + Float.parseFloat((String) table.getValueAt(table.getSelectedRow() + i,
											table.getSelectedColumn() + j));
								} else {
									sum = sum + 0;
								}
							}

						}

						System.out.println(sum);
						textField_1.setText(String.valueOf(sum));
					}
				});

				varButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						RserverConf rconf = new RserverConf("127.0.0.1", 6311, "", "", new Properties());
				        Rsession re = Rsession.newInstanceTry(System.out, rconf);
						//Rengine re = new Rengine(null, false, null);
						ArrayList<String> value = new ArrayList<>();
						for (int i = 0; i < table.getSelectedRowCount(); i++) {

							for (int j = 0; j < table.getSelectedColumnCount(); j++) {
								if (table.getValueAt(table.getSelectedRow() + i, table.getSelectedColumn() + j) != null) {
									// sum = sum+ Float.parseFloat((String)
									// table.getValueAt(table.getSelectedRow()+i,
									// table.getSelectedColumn()+j));
									value.add((String) table.getValueAt(table.getSelectedRow() + i,
											table.getSelectedColumn() + j));
								}
							}
						}
						int size = value.size();
						String[] array = new String[size];
						for (int i = 0; i < size; i++) {
							array[i] = value.get(i);
						}
						for (int i = 0; i < array.length; i++) {
							System.out.println(array[i]);
						}

						re.set("x",array);
						//REXP rexp = re.eval("var(x)");
						//System.out.println(rexp);
						
						double var = 0;
						try {
							var = re.eval("var(x)").asDouble();
						} catch (REXPMismatchException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println(max);
						textField_1.setText(String.valueOf(var));
						re.end();

					}
				});

				calButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						((DefaultTableModel) model).addColumn("�궨", new Vector<>());
						table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

							@Override
							public Component getTableCellRendererComponent(JTable table, Object value,
									boolean isSelected, boolean hasFocus, int row, int column) {
								// �������ڷ��ص���Ⱦ���
								JTextField jTextField = new JTextField(value.toString());
								if (isSelected) {
									table.setColumnSelectionInterval(1, table.getColumnCount() - 1);
									table.setRowSelectionInterval(row, row);
								}
								 jTextField = new JTextField(value.toString());
								//jTextField.setBorder(null);
								jTextField.setHorizontalAlignment((int) 0.5f);
								// ck.setSelected(isSelected);
								// ���õ�ѡbox.setSelected(hasFocus);
								// ʹ��ѡ���ڵ�Ԫ���ھ�����ʾ
								// ck.setHorizontalAlignment((int) 0.5f);
								jTextField.setBackground(Color.gray);

								return jTextField;

							}
						});
						count = Integer.parseInt(JOptionPane.showInputDialog("������궨������"));

					}
				});

				classButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						svm_node[][] datas = new svm_node[table.getSelectedRowCount()][table.getSelectedColumnCount()];
						double[] lables = new double[count];
						for (int i = 0; i < count; i++) {
							lables[i] = (Double.parseDouble((String) table.getValueAt(i, table.getColumnCount() - 1)));
						}

						for (int i = 0; i < count; i++) {
							for (int j = 0; j < table.getSelectedColumnCount(); j++) {

								datas[i][j] = new svm_node();
								datas[i][j].index = j;
								datas[i][j].value = Double.parseDouble((String) table
										.getValueAt(table.getSelectedRow() + i, table.getSelectedColumn() + j));

							}

						}
						// ����svm_problem����
						svm_problem problem = new svm_problem();
						problem.l = table.getSelectedColumnCount(); // ����
						problem.x = datas; // ѵ����������
						problem.y = lables; // ��Ӧ��lable����

						// ����svm_parameter����
						svm_parameter param = new svm_parameter();
						param.svm_type = svm_parameter.C_SVC;
						param.kernel_type = svm_parameter.LINEAR;
						param.cache_size = 100;
						param.eps = 0.00001;
						param.C = 1;

						// ѵ��SVM����ģ��
						System.out.println(svm.svm_check_parameter(problem, param)); // �������û�����⣬��svm.svm_check_parameter()��������null,���򷵻�error������
						svm_model sModel = svm.svm_train(problem, param); // svm.svm_train()ѵ����SVM����ģ��

						// ����������ݵ�c
						svm_node[][] test = new svm_node[table.getSelectedRowCount() - count][table
								.getSelectedColumnCount()];
						for (int i = count; i < table.getSelectedRowCount() - count; i++) {
							for (int j = 0; j < table.getSelectedColumnCount(); j++) {

								test[i][j] = new svm_node();
								test[i][j].index = j;
								test[i][j].value = Double.parseDouble((String) table
										.getValueAt(table.getSelectedRow() + i, table.getSelectedColumn() + j));

							}

						}

						// Ԥ��������ݵ�lable
						for (int i = 0; i < table.getSelectedRowCount() - count; i++) {

							table.setValueAt(svm.svm_predict(sModel, test[i]), count + i, table.getColumnCount() - 1);
							System.out.println(svm.svm_predict(sModel, test[i]));

						}

					}
				});

				hist.addActionListener(new ActionListener() {

					@SuppressWarnings("rawtypes")
					@Override
					public void actionPerformed(ActionEvent e) {
						RserverConf rconf = new RserverConf("127.0.0.1", 6311, "", "", new Properties());
				        Rsession re = Rsession.newInstanceTry(System.out, rconf);
						//Rengine re = new Rengine(null, false, null);
						ArrayList<String> value = new ArrayList<>();
						File file = new File("c:\\db\\hist.txt");
						FileWriter fw = null;
						try {
							 fw = new FileWriter(file,false);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for (int i = 0; i < table.getSelectedRowCount(); i++) {

							for (int j = 0; j < table.getSelectedColumnCount(); j++) {
								if (table.getValueAt(table.getSelectedRow() + i, table.getSelectedColumn() + j) != null) {
									
									try {
									
										fw.write("\""+(String) table.getValueAt(table.getSelectedRow() + i,table.getSelectedColumn() + j)+"\"");
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							
								}
							}
						}
						try {
							fw.flush();
							fw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String[] cmd = {"C:\\Program Files\\R\\R-3.2.3\\bin\\Rscript.exe",
										"C:\\db\\hist.R",
										""};
	
				Process process = null;
				try {
					process = Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					process.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// �������
				JFrame f = new JFrame("ͳ��ͼ");
				JPanel p1 = new JPanel();
				f.add(p1, BorderLayout.CENTER);
				f.setSize(600, 600);
				f.setVisible(true);
				// ����һ��ͼ���ļ�		
				JLabel lable = null;
				ImageIcon img = null;
				img = new ImageIcon("C:\\\\db\\\\hist.jpg");
				lable = new JLabel(img);
				p1.add(lable);
				
				re.end();
					}
				
				});
				
				pie.addActionListener(new ActionListener() {

					@SuppressWarnings("rawtypes")
					@Override
					public void actionPerformed(ActionEvent e) {
						RserverConf rconf = new RserverConf("127.0.0.1", 6311, "", "", new Properties());
				        Rsession re = Rsession.newInstanceTry(System.out, rconf);
						//Rengine re = new Rengine(null, false, null);
						ArrayList<String> value = new ArrayList<>();
						File file = new File("c:\\db\\pie.txt");
						FileWriter fw = null;
						try {
							 fw = new FileWriter(file,false);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for (int i = 0; i < table.getSelectedRowCount(); i++) {

							for (int j = 0; j < table.getSelectedColumnCount(); j++) {
								if (table.getValueAt(table.getSelectedRow() + i, table.getSelectedColumn() + j) != null) {
									
									try {
									
										fw.write("\""+(String) table.getValueAt(table.getSelectedRow() + i,table.getSelectedColumn() + j)+"\"");
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							
								}
							}
						}
						try {
							fw.flush();
							fw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String[] cmd = {"C:\\Program Files\\R\\R-3.2.3\\bin\\Rscript.exe",
										"C:\\db\\pie.R",
										""};
	
				Process process = null;
				try {
					process = Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					process.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// �������
				JFrame f = new JFrame("ͳ��ͼ");
				JPanel p1 = new JPanel();
				f.add(p1, BorderLayout.CENTER);
				f.setSize(600, 600);
				f.setVisible(true);
				// ����һ��ͼ���ļ�		
				JLabel lable = null;
				ImageIcon img = null;
				img = new ImageIcon("C:\\\\db\\\\pie.jpg");
				lable = new JLabel(img);
				p1.add(lable);
				
				re.end();
					}
				
				});
			}
		

	

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return button;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

	@Override


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}

}
