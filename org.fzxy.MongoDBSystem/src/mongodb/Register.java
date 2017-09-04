package mongodb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame{

	private JLabel Information;
	private JTextField user;
	private JPasswordField password;
	private JTextField	workLocation;
	private JTextField  contact;
	private JTextField phone;
	private JComboBox permission;
	JLabel jLabel;
	JLabel jLabel2;
	JLabel jLabel3;
	 JLabel jLabel4;
	 JLabel jLabel5;
	 JLabel jLabel6;
	 JButton jButton;
	 JButton jButton2;
	public Register() {
	// TODO Auto-generated constructor stub
		super("注册页面");
		this.setLayout(null);
		Information = new JLabel();
		ImageIcon loginIcon= new ImageIcon(this.getClass().getResource("/login.jpg"));
		Information.setIcon(loginIcon);
		Information.setBackground(Color.blue);
		Information.setOpaque(true);
		Information.setBounds(0, 0, 500, 100);
		
		jLabel = new JLabel("用 户 名:");
		jLabel2 = new JLabel("密   码:");
		jLabel3 = new JLabel("工作单位:");
		jLabel4 = new JLabel("联系人姓名:");
		jLabel5 = new JLabel("电   话:");
		jLabel6 = new JLabel("申请权限:");
		
		Font font2 = new Font("宋体", 1, 15);
		jLabel.setFont(font2);
		jLabel2.setFont(font2);
		jLabel3.setFont(font2);
		jLabel4.setFont(font2);
		jLabel5.setFont(font2);
		jLabel6.setFont(font2);
	
		user = new JTextField();
		password = new JPasswordField();
		workLocation = new JTextField();
		contact = new JTextField();
		phone = new JTextField();
		permission = new JComboBox();
		
		

		permission.addItem("普通用户");
		permission.addItem("特权用户");
		//permission.addItem("");
		permission.setSelectedItem("普通用户");//默认选中普通用户
		
		permission.setFont(font2);
		jButton = new JButton("提交");
		jButton2 = new JButton("取消");
		
		jLabel.setBounds(50, 80, 200, 100);
		jLabel2.setBounds(50, 130, 200, 100);
		jLabel3.setBounds(50, 180, 200, 100);
		jLabel4.setBounds(50,230, 200, 100);
		jLabel5.setBounds(50, 280, 200, 100);
		jLabel6.setBounds(50, 330, 200, 100);
		
		user.setBounds(140, 115, 200, 25);
		password.setBounds(140, 165, 200, 25);
		workLocation.setBounds(140, 215, 200, 25);
		contact.setBounds(140, 265, 200, 25);
		phone.setBounds(140, 315, 200, 25);
		permission.setBounds(140, 365, 200, 25);
		
		jButton.setBounds(130, 430, 70, 25);
		jButton2.setBounds(220, 430, 70, 25);
		
		this.add(Information);
		this.add(jLabel);
		this.add(jLabel2);
		this.add(jLabel3);
		this.add(jLabel4);
		this.add(jLabel5);
		this.add(jLabel6);
		this.add(jButton);
		this.add(jButton2);
		this.add(user);
		this.add(password);
		this.add(workLocation);
		this.add(contact);
		this.add(phone);
		this.add(permission);

	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 200, 440, 500);
		setVisible(true);
		setResizable(false);
		Image icon4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/diannao.png")); 
		setIconImage(icon4);
		jButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					new  InsertRegister().start(user.getText(), password.getText(), workLocation.getText(), contact.getText(), phone.getText(), permission.getSelectedItem().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
}
