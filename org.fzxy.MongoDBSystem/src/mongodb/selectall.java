package mongodb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class selectall implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem selAll = (JMenuItem)e.getSource();
		if (selAll.getText()=="ȫѡ") {//ȫѡ
			selAll.setText("ȡ��");
			for (int i = 0; i < main.table.getRowCount(); i++) {//����ÿ��  ����Ϊtrue
				main.table.setValueAt(Boolean.TRUE, i, 0);
			}
			
		}else {//ȡ��ȫѡ
			selAll.setText("ȫѡ");
			for (int i = 0; i < main.table.getRowCount(); i++) {
				main.table.setValueAt(Boolean.FALSE, i, 0);
			}
		}
		
	}

}
