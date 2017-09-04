package mongodb;
import java.net.URL;

import javax.swing.ImageIcon;

import mongodb.main;
public class CreatecdIcon {
public static ImageIcon add(String ImageName){
	URL IconUrl = main.class.getResource("/"+ImageName);
	ImageIcon icon=new ImageIcon(IconUrl);
	return icon;
}
}