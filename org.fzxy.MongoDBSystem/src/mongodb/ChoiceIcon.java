package mongodb;

import javax.swing.ImageIcon;

public class ChoiceIcon {
	public static ImageIcon choiceicon(String type) {
		ImageIcon img;
		switch (type) {
		case "txt":
			img = new ImageIcon(icon.TXT);
			break;
		case "ppt":
		case "pptx":
			img = new ImageIcon(icon.PPT);
			break;
		case "xls":
		case "xlsx":
			img = new ImageIcon(icon.XLS);
			break;
		case "doc":
		case "docx":
			img = new ImageIcon(icon.DOC);
			break;
		case "rar":
		case "zip":
			img = new ImageIcon(icon.RAR);
			break;
		default:
			img = new ImageIcon(icon.DEFAULT);
			break;
		}
		return img;
	}
}
