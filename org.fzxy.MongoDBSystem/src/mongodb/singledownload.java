package mongodb;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFSDBFile;

public class singledownload extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JButton button = null;

	public singledownload(final JTable table) {
		// TODO Auto-generated constructor stub
		button = new JButton("下载");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = (String) table.getValueAt(table.getSelectedRow(), 2);// 获取选定指定行的
				System.err.println(filename);

				if (filename == null) {
					JOptionPane.showMessageDialog(null, "filename is null");
				} else {

					int result = 0;

					JFileChooser chose2 = new JFileChooser("//");
					chose2.setFileSelectionMode(chose2.DIRECTORIES_ONLY);
					chose2.setApproveButtonText("确认");
					chose2.setDialogTitle("请选择路径");
					JFrame f = new JFrame();
					result = chose2.showOpenDialog(f);

					String str = null;
					if (result == JFileChooser.APPROVE_OPTION) {

						String Path = chose2.getSelectedFile().getAbsolutePath();

						DB db = main.mongo.getDB("test");

						long start = System.currentTimeMillis();

						GridFSDBFile gfsFile = batchDownLoad.searchGFSFile(table, filename);
						if (Path.endsWith("\\")) {
							String name = Path + gfsFile.getFilename();
							try {
								gfsFile.writeTo(name);

							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else {
							String name = Path + "\\" + gfsFile.getFilename();
							// JOptionPane.showMessageDialog(null, Path);
							try {
								gfsFile.writeTo(name);

							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}

						long afterQuery = System.currentTimeMillis();
						System.out.println("download use time:" + (afterQuery - start));
						JOptionPane.showMessageDialog(null, "下载成功！");
					}
				}
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		// System.err.println(value);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub

		return button;
	}
}