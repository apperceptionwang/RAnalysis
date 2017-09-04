package mongodb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class selectall implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem selAll = (JMenuItem)e.getSource();
		if (selAll.getText()=="全选") {//全选
			selAll.setText("取消");
			for (int i = 0; i < main.table.getRowCount(); i++) {//遍历每行  设置为true
				main.table.setValueAt(Boolean.TRUE, i, 0);
			}
			
		}else {//取消全选
			selAll.setText("全选");
			for (int i = 0; i < main.table.getRowCount(); i++) {
				main.table.setValueAt(Boolean.FALSE, i, 0);
			}
		}
		
	}

}
