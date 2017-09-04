package mongodb;

import java.io.File;
import java.io.FileWriter;

import net.sf.json.JSONObject;


public class handleString {
	// static String rs[] ;

	File file = new File("C:\\db\\" + "tmpTable.txt");

	FileWriter fWriter = null;
	// OutputStream out = null;
	String[] writedata = null;
	String test = null;
		
	public String[] start(String s) {

		int j = 0;
		int w = 0;
		
		String[] rs = new String[6];
		
		JSONObject object = JSONObject.fromObject(s);
		JSONObject id = JSONObject.fromObject(object.get("_id"));
		rs[0]=id.getString("$oid");
		rs[1]=",";
		//取出filename的值
		rs[2]=object.getString("filename");
		rs[3]=",";
		//取出date
		JSONObject date = JSONObject.fromObject(object.get("uploadDate"));
		rs[4]=date.getString("$date");
		rs[5]="\n";
		return rs;
		
	}

	public void writeInFile(String data) {


		try {
			FileWriter fWriter = new FileWriter(file, true);
			
			writedata = new handleString().start(data);

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
			
			writedata = new handleString().start(data);

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
