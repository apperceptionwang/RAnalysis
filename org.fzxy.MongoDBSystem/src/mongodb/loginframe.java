package mongodb;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class loginframe extends JFrame implements ActionListener{
	private JPasswordField password;
    private  JTextField username;
    private JButton config;
	private JButton login;
	private JButton reset;
	private JButton Join;
	private JLabel jLabel;
	private JLabel jLabel2;
	public static main myframe= null;
	public loginframe() {
		
		super("天然气水合物系统");
		


		
		Container container =super.getContentPane();
		
		container.setLayout(null);
		
		JLabel backPicture = new JLabel();
		ImageIcon loginIcon= new ImageIcon(this.getClass().getResource("/login.jpg"));
		backPicture.setIcon(loginIcon);
		backPicture.setBackground(Color.blue);
		backPicture.setOpaque(true);
		backPicture.setBounds(0, 0, 438, 100);
		container.add(backPicture);
		
		
		jLabel = new JLabel("用户名:  ");
		jLabel2 = new JLabel("密  码: ");
		
		Font font = new Font("宋体", 1, 15);
		jLabel.setFont(font);
		jLabel2.setFont(font);
		
		jLabel.setBounds(45, 118, 100, 50);
		jLabel2.setBounds(45,166, 100, 50);
		
		username = new JTextField();
		password = new JPasswordField();
		password.setEchoChar('●');
		username.setBounds(147, 131, 200, 25);
		password.setBounds(147, 179, 200, 25);
		
		container.add(jLabel);
		container.add(jLabel2);
		container.add(username);
		container.add(password);
		
		config=new JButton("配置");
		config.setFont(new Font("宋体", Font.BOLD, 15));
		login = new JButton("登录");
		reset = new JButton("重置");
		Join = new JButton("注册");
		
		login.setFont(new Font("宋体", Font.BOLD, 15));
		reset.setFont(new Font("宋体", Font.BOLD, 15));
		Join.setFont(new Font("宋体", Font.BOLD, 15));
		
		
		Join.setBounds(304, 226, 70, 25);
		reset.setBounds(224, 226, 70, 25);
		login.setBounds(144, 226, 70, 25);
		config.setBounds(64, 226, 70, 25);
		
		
		container.add(login);
		container.add(reset);
		container.add(Join);
		container.add(config);
		
		
		password.addActionListener(this);
		
		Image icon4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/diannao.png")); 
		setIconImage(icon4);
		
//		getContentPane().add(jPanel, BorderLayout.NORTH);
//		getContentPane().add(jPanel2, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(430, 200, 420, 320);
		setVisible(true);
		setResizable(false);
		//Image icon4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/diannao.png")); 
		//setIconImage(icon4);
		
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = ds.width,y1 = ds.height;
		
		setBounds((x1-420)/2, (y1-320)/2, 441, 328);
	
		config.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new configurationframe();
               
				
				
			}
		});
		
		login.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				 
				String name = username.getText();
				String mima= password.getText();
			
				if( username.getText().length()==0){
					JOptionPane.showMessageDialog(null, "请输入用户名");
				}
				else if(password.getText().length()==0){
					JOptionPane.showMessageDialog(null, "请输入密码");
				}
				else {
					int s = 0;
					try {
						s = VerificationUser.Try(name,mima);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(s==1){
                             new main();
                             dispose();
						
					}
					else if (s==-1){
						JOptionPane.showMessageDialog(null, "密码不正确，请确认密码重新登录");
					}
					else {
						JOptionPane.showMessageDialog(null, "此账户不存在！请注册后登录");
						new  Register();
					}
				}
			}
		});
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				username.setText(null);
				password.setText(null);
			}
		});
		Join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*try {  
				     
				     Process ps = Runtime.getRuntime().exec("cmd.exe  /k start  "+ClassLoader.getSystemResource("startdb.bat").toString()); 
				     
				       
				 } catch (IOException ioe) {  
				     ioe.printStackTrace();  
				 }  
				 */
				new  Register();
				
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		password.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent event) 
			{ 
			if(event.getKeyCode()==KeyEvent.VK_ENTER) 
				{ 
				login.doClick(); 
				} 
			}                                    
		});
	}
	
}