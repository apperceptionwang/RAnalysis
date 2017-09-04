package mongodb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShopSuccessWindow extends JFrame{

	public ShopSuccessWindow() {
		// TODO Auto-generated constructor stub
		int x,y;
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		x = ds.height;
		y=ds.width;
	    setLayout(null);
	    setBounds((ds.width - 300) / 2, (ds.height - 100) / 2, 300,180);
	    
	    JLabel jlable = new JLabel();
        jlable.setBounds(0, 0, 300, 50);
        
        jlable.setText("<html><center><font size = 5 ><br/>&nbsp&nbsp&nbsp添加下载车成功！</font></center></html>");
        
        JButton bt1 = new JButton("去下载车");
        JButton bt2 = new JButton("继续添加");
        bt1.setBounds(50, 80, 80, 30);
        bt2.setBounds(170, 80, 80, 30);
        
	    add(jlable);
	    add(bt1);
	    add(bt2);
	    
	    
	    bt1.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			 DownLoadCar DownloadFrame = new  DownLoadCar();
			 dispose();
				
			}
		});
	    
        bt2.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				dispose();
				
			}
		});
	    
		setVisible(true);
	}

}
