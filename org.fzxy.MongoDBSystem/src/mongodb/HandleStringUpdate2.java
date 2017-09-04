package mongodb;

import java.io.File;
import java.io.FileWriter;

import net.sf.json.JSONObject;

//package mongoall;

public class HandleStringUpdate2 {
	// static String rs[] ;

	File file = new File("C:\\db\\" + "tmpTable3.txt");

	FileWriter fWriter = null;
	// OutputStream out = null;
	String[] writedata = null;
	String test = null;
		
	public String[] start(String s) {

		int j = 0;
		int w = 0;
		// System.out.print(s);
		// JOptionPane.showConfirmDialog(null, s);
		
		 
		 /*
		 for(int i=0;i<rsstart.length;i++){ 
		 
		 System.out.print(i);
		 System.out.print(rsstart[i]); 
		 System.out.print("\n"); 
		 }
		 */
		 
		System.out.println(s);
		String[] rs = new String[6];
		
		JSONObject object = JSONObject.fromObject(s);
		rs[0]=object.getString("_id");
		rs[1]=",";
		//取出filename的值
		rs[2]=object.getString("name");
		rs[3]=",";
		//取出date;
		rs[4]=object.getString("date");
		rs[5]="\n";
		return rs;
		/*
		if (main.IO == 0) {
			rs[0] = rsstart[11];// 11 18 37
			rs[1] = ",";
			rs[2] = rsstart[18];// 44 5 24
			rs[3] = ",";
			rs[4] = rsstart[37];
			rs[5] = "\n";
		} 
		else {
			rs[0] = rsstart[44];// 11 18 37
			rs[1] = ",";
			rs[2] = rsstart[5];// 44 5 24
			rs[3] = ",";
			rs[4] = rsstart[24];
			rs[5] = "\n";
		}
		return rs;
		*/
	}

	public void writeInFile(String data) {


		try {
			FileWriter fWriter = new FileWriter(file, true);
			
			writedata = new HandleStringUpdate2().start(data);

			String str = "";
			String str2 = (String) writedata[2];
			String str3 = "desc";

			if (str2.indexOf(str3) == -1) {

				for (int i = 0; i < writedata.length; i++) {

					str += (String) writedata[i];

				}

				fWriter.write(false + "," + str);

			}

			fWriter.flush();

			fWriter.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void writeInFile(String file,String data) {

		File filepath = new File(file);
		try {
			FileWriter fWriter = new FileWriter(filepath, true);
			
			writedata = new HandleStringUpdate2().start(data);

			String str = "";
			String str2 = (String) writedata[2];
			String str3 = "desc";

			if (str2.indexOf(str3) == -1) {

				for (int i = 0; i < writedata.length; i++) {

					str += (String) writedata[i];

				}

				fWriter.write(false + "," + str);

			}

			fWriter.flush();

			fWriter.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
