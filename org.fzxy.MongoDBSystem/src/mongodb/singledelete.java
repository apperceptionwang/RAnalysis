package mongodb;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;

public class singledelete extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
	private static final long serialVersionUID = 1L;
	private JButton button = null;

	public singledelete(final JTable table, final String name, final JScrollPane sp2) {
		// TODO Auto-generated constructor stub
		button = new JButton("删除");
		button.addActionListener(this);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String filename = (String) table.getValueAt(table.getSelectedRow(), 2);// 获取选定指定行的

				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				System.err.println(table.getSelectedRow());
				tableModel.removeRow(table.getSelectedRow());// rowIndex是要删除的行序号
				JOptionPane.showMessageDialog(null, "删除成功!");

				DB db = main.mongo.getDB("test");
				GridFS gfs = new GridFS(db, batchDownLoad.searchCollection(table, filename));
				gfs.remove(gfs.findOne(filename));
				new refresh(table, filename, sp2, name);

			}

		});
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return button;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
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
