package mongodb;

import java.io.RandomAccessFile;


public class tempfile {
	static String temp() throws Exception{
		RandomAccessFile bat=new RandomAccessFile("res/tempfile.bat", "rw");

		
		 bat.seek(0);
		 String path=bat.readLine(); 
		 bat.close();
		 System.err.println(path);
		// JOptionPane.showMessageDialog(null, path);
		return path;
		}

}
