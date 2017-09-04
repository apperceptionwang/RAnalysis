package mongodb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.rosuda.JRI.REXP;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class R extends JFrame implements ActionListener{
	private JTextArea inField = new JTextArea();
	private JTextField rsField = new JTextField();
	JButton Ex = new JButton("\u5747\u503C");
	JButton Dx = new JButton("\u65B9\u5DEE");
	JButton Sx = new JButton("\u6807\u51C6\u5DEE");
	JLabel label_2 = new JLabel("\u7ED3    \u679C\uFF1A");
	JLabel calculateLable = new JLabel("\u8F93\u5165\u6570\u636E\uFF1A");
	ArrayList<Double> list = new ArrayList<Double>();

	
	public R(){
		super("R语言分析");
		setTitle("\u6570\u636E\u5206\u6790");
		setVisible(true);
		getContentPane().setLayout(null);
		
		
		inField.setBounds(134, 36, 296, 86);
		getContentPane().add(inField);
		inField.setColumns(10);
		inField.setBorder (BorderFactory.createLineBorder(Color.gray,2));
		
	
		calculateLable.setBounds(25, 37, 99, 21);
		getContentPane().add(calculateLable);
		
		
		rsField.setBounds(134, 154, 137, 21);
		getContentPane().add(rsField);
		rsField.setColumns(10);
		
		
		label_2.setBounds(35, 154, 61, 21);
		getContentPane().add(label_2);
		
		Ex.setBounds(440, 36, 93, 23);
		Ex.addActionListener(this);
		getContentPane().add(Ex);
		
		
		Dx.setBounds(440, 69, 93, 23);
		Dx.addActionListener(this);
		getContentPane().add(Dx);
		Sx.setBounds(440, 99, 93, 23);
		Sx.addActionListener(this);
		getContentPane().add(Sx);
		
		JLabel label = new JLabel("\uFF08\u8BF7\u8F93\u5165\u6570\u636E\u5DF2\u7A7A\u683C\u5206\u5F00\uFF09");
		label.setBounds(134, 10, 162, 21);
		getContentPane().add(label);
		Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
		int x1 = ds.width,y1 = ds.height;
		this.setBounds((x1-420)/2, (y1-320)/2, 585, 253);
		

		
	}
	public ArrayList<Double>  getData() {
		
		int i = 0;
		
		String s = inField.getText();
		String Data[] = s.split(" ");
		for (String w : Data) {
			list.add(Double.parseDouble(w));
		}
		
		return list;
	}

	/**
	 * 计算均值 返回double类型的变量
	 * @return
	 */
	public double getEx() {
		double a[] = new double[list.size()];
		int i =0;
		for (Double dou : list) {
			a[i]=dou;
			i++;
		}
		main.re.assign("a", a);
		REXP x = main.re.eval("mean(a)");
		double E = x.asDouble();
		return E ;
	}
	/**
	 * 计算方差  返回double类型的变量
	 * @return
	 */
	public double getDx() {
		double a[] = new double[list.size()];
		int i =0;
		for (Double dou : list) {
			a[i]=dou;
			i++;
		}
		main.re.assign("a", a);
		REXP x = main.re.eval("var(a,na.rm=FALSE)");
		double D = x.asDouble();
		return D ;
	}
	/**
	 * 计算标准差S
	 */
	public double getSx() {
		double a[] = new double[list.size()];
		int i =0;
		for (Double dou : list) {
			a[i]=dou;
			i++;
		}
		main.re.assign("a", a);
		REXP x = main.re.eval("sd(a,na.rm=FALSE)");
		double S = x.asDouble();
		return S ;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("0.000");
		if(inField.getText().length()==0){
			JOptionPane.showMessageDialog(null, "请输入参数!");
			 
			return ;
		}
		else{
				 getData();
		}
		if(arg0.getSource()==Ex)
			rsField.setText(df.format(getEx())+"");
		if(arg0.getSource()==Dx){
			rsField.setText(df.format(getDx())+"");
		}
		if(arg0.getSource()==Sx){
			rsField.setText(df.format(getSx())+"");
		}
	}
}
