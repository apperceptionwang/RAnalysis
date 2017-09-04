package mongodb;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class configurationframe {

	private JFrame frame;
	public static JTextField ip;
	private JLabel lblStartMongodb;
	public static JTextField startdb;
	private JTextField tempfile;

	
	/**
	 * Create the application.
	 */
	public configurationframe() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\hadoop\\Desktop\\diannao.png"));
		frame.setTitle("\u914D\u7F6E\u754C\u9762");
		frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 15));
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMongodbip = new JLabel("\u6570\u636E\u5E93\u8DEF\u7531IP");
		lblMongodbip.setFont(new Font("宋体", Font.PLAIN, 15));
		lblMongodbip.setBounds(100, 34, 109, 15);
		frame.getContentPane().add(lblMongodbip);
		
		ip = new JTextField("localhost");
		ip.setBounds(210, 31, 214, 21);
		frame.getContentPane().add(ip);
		ip.setColumns(10);
		
		lblStartMongodb = new JLabel("\u6570\u636E\u5E93\u542F\u52A8\u8DEF\u5F84");
		lblStartMongodb.setFont(new Font("宋体", Font.PLAIN, 15));
		lblStartMongodb.setBounds(100, 148, 109, 15);
		frame.getContentPane().add(lblStartMongodb);
		
	    startdb= new JTextField("C:\\mongo\\bin\\mongod --dbpath=c:\\db");
	    startdb.setBounds(210, 145, 214, 21);
		frame.getContentPane().add(startdb);
		startdb.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u6587\u4EF6\u7F13\u5B58\u8DEF\u5F84");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(100, 99, 93, 15);
		frame.getContentPane().add(lblNewLabel);
		
		tempfile = new JTextField("C:\\db\\");
		tempfile.setBounds(210, 96, 214, 21);
		frame.getContentPane().add(tempfile);
		tempfile.setColumns(10);
		JButton reset = new JButton("\u91CD\u7F6E");
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ip.setText(null);
		
			tempfile.setText(null);
			startdb.setText(null);
			}
		});
		reset.setBounds(74, 205, 93, 23);
		frame.getContentPane().add(reset);
		JButton ok = new JButton("\u786E\u5B9A");
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//启动mongodb
				try {
					try {
						new configureachieve(startdb,ip,tempfile);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
   		
		ok.setBounds(252, 205, 93, 23);
		frame.getContentPane().add(ok);
	}
}
