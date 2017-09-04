package mongodb;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class View extends AbstractCellEditor implements TableCellRenderer,
		ActionListener, TableCellEditor {
	private JButton button = null;

	public View() {
		
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
			if (s.equals("true")) {
				rows = i;
				break;
			}
		}
		
		String path;
		try {
			path = tempfile.temp();

			GridFSDBFile gfsFile = batchDownLoad.getSelectCollName(rows);
			String name = path + gfsFile.getFilename().split("/")[(gfsFile.getFilename().split("/")).length-1];
			try {
				gfsFile.writeTo(name);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				File tFile = new File(name);
				Desktop.getDesktop().open(tFile);
				tFile.deleteOnExit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		return button;
	}
}