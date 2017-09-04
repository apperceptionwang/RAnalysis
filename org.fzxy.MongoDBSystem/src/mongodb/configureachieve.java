package mongodb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JTextField;

public class configureachieve {
	
	
	
	public configureachieve(JTextField startdb,JTextField ip,JTextField tempfile) throws IOException, InterruptedException{
		
		//启动mongodb
		
      
		try {
		
	     File file = new File("res/startdb.bat");
	     FileOutputStream fos = new FileOutputStream(file);
	     OutputStreamWriter osw = new OutputStreamWriter(fos);
	     BufferedWriter bw = new BufferedWriter(osw);
         System.err.println(startdb.getText());
	     String s1 = new String(startdb.getText().toString());
	     bw.write(s1);
	     bw.newLine();
	     
	     bw.flush();
	     bw.close();
	     osw.close();
	     fos.close();

	    }

	    catch (FileNotFoundException e1) {
	     e1.printStackTrace();
	    } catch (IOException e2) {
	     e2.printStackTrace();
	    }
	//配置ip
	FileWriter writer = null;  
    try {     
       
        writer = new FileWriter("res/ip.bat");     
        writer.write(ip.getText());       
    } catch (IOException e) {     
        e.printStackTrace();     
    } finally {     
        try {     
            if(writer != null){  
                writer.close();     
            }  
        } catch (IOException e) {     
            e.printStackTrace();  
        }
        } 
    
    //创建系统缓存路径FileWriter writer = null;  
    try {     
       
        writer = new FileWriter("res/tempfile.bat");     
        writer.write(tempfile.getText());       
    } catch (IOException e) {     
        e.printStackTrace();     
    } finally {     
        try {     
            if(writer != null){  
                writer.close();     
            }  
        } catch (IOException e) {     
            e.printStackTrace();  
        }
        } 
  
    File file =new File(tempfile.getText());    
  //如果文件夹不存在则创建    
  if  (!file .exists()  && !file .isDirectory())      
  {       
      System.out.println("//不存在");  
      file .mkdir();    
  } else   
  {  
      System.out.println("//目录存在");  
  }    

        
 
	}
}

