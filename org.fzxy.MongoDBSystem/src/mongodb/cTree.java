package mongodb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;

public class cTree {
	
	protected static String init="库";
	static MyNode[] nodes;//节点栈
	static String[] paths;//节点名字栈
	//最多可读取50深度的树
	
	public static void saveNode() throws FileNotFoundException, UnsupportedEncodingException{
		String filepath = "res/node.txt";
		File file = new File(filepath);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		
		getNode(main.root, init,bw);
		
	}
	
	/**
	 * 递归遍历现有树结构，保存到txt文档
	 * */
	private static void getNode(MyNode nowNode,String parentName,BufferedWriter bw) {
		for (int i = 0; i < nowNode.getChildCount(); i++) {
			if (((MyNode) nowNode.getChildAt(i)).getText() == "") {
				return ;
			}
			String name = parentName + "/" + ((MyNode) nowNode.getChildAt(i)).getText();
			
			try {
				if (!((MyNode)nowNode.getChildAt(i)).getAllowsChildren()) {
					name += "?";
				}
				bw.write(name);
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getNode((MyNode)nowNode.getChildAt(i), parentName+((MyNode)nowNode.getChildAt(i)).getText(),bw);
		}
	}
	

	public static void readNode() throws IOException {
		String filepath = "res/node.txt";
		nodes = new MyNode[50];
		paths = new String[50];
		
		File file = new File(filepath);
		if (!file.exists()) {
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			
			String[] names = {"库/图形图像","库/实验报告","库/数字记录","库/文档文献","库/软件应用"};
			for (int i = 0; i < names.length; i++) {
				bw.write(names[i]);
				bw.newLine();
				bw.flush();
			}
			fos.close();
			osw.close();
			bw.close();
		}
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String eachline;
		String parentPath = "库";
		MyNode parentNode = main.root;
		boolean flags = true;
		ImageIcon img;
		try {
			while ((eachline = br.readLine())!=null) {
				String path = eachline.split("/")[0];
				String name = eachline.split("/")[1];
				
				while (!parentPath.equals(path)) {//回溯至父节点
					parentPath = popPaths();
					if (parentPath == null) {
						System.out.println("error");
						return;
					}
					parentNode = popNodes();
				}
				
				if (name.endsWith("?")) {
					name = name.substring(0, name.length()-1);
					flags = false;
					String[] endIndex = name.split("\\.");
					String type = endIndex[endIndex.length-1];
					img = ChoiceIcon.choiceicon(type);
				}
				else {
					flags = true;
					img = new ImageIcon(icon.FOLDER_CLOSE);
				}
				
				MyNode childNode = new MyNode(img,name);
				childNode.setAllowsChildren(flags);
				
				parentNode.add(childNode);
				
				//入栈
				pushPaths(path);
				pushNodes(parentNode);
				parentPath = path+name;
				parentNode = childNode;
			}
			MyNode hideNode = new MyNode(new ImageIcon(""),"");
			main.root.add(hideNode);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static void pushPaths(String path) {
		for (int i = 0; i < paths.length; i++) {
			if (paths[i]==null) {
				paths[i]=path;
				return ;
			}
		}
	}
	
	private static String popPaths() {
		String t;
		int i=0;
		
		for (i = 0; i < paths.length; i++) {
			if (paths[i]==null) {
				break;
			}
		}
		if (i==0) {
			return null;
		}
		t = paths[i-1];
		paths[i-1]=null;
		return t;
	}
	
	private static void pushNodes(MyNode node) {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i]==null) {
				nodes[i]=node;
				return ;
			}
		}
	}
	
	private static MyNode popNodes() {
		MyNode t;
		int i=0;
		
		for (i = 0; i < nodes.length; i++) {
			if (nodes[i]==null) {
				break;
			}
		}
		if (i==0) {
			return null;
		}
		t = nodes[i-1];
		nodes[i-1]=null;
		return t;
	}
}
