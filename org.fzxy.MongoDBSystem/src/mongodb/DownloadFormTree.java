package mongodb;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class DownloadFormTree {
	/**
	 *
	 * 下载
	 *
	 * */
	static DB db;

	public static void download() throws IOException {
		String collName = "";
		db = main.mongo.getDB("test");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("下载到");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 选择下载位置
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();// 获取选择的下载位置

			MyNode downloadNode = (MyNode) main.myTree
					.getLastSelectedPathComponent();// 获取选中的节点
			collName = uploadFiles.getCollName(downloadNode);
			filePath = filePath + "/" + downloadNode.getText();// 下载路径+名字
			File rootFile = new File(filePath);
			if (rootFile.exists()) {// 如果下载位置已存在此文件（夹）则无法下载
				JOptionPane.showMessageDialog(null, "已存在的文件    下载失败");
				return;
			}

			writeFile(filePath, downloadNode, collName);
			JOptionPane.showMessageDialog(null, "下载完成");
		}
	}

	/**
	 * 
	 * @param filepath
	 *            下载的位置路径
	 * @param downloadNode
	 *            要下载的节点
	 * */
	private static void writeFile(String filepath, MyNode downloadNode,
			String collName) throws IOException {
		if (downloadNode.getAllowsChildren()) {// 允许有子节点 文件夹
			File file = new File(filepath);
			file.mkdirs();// 创建文件夹

			for (int i = 0; i < downloadNode.getChildCount(); i++) {
				writeFile(filepath + "/"
						+ ((MyNode) downloadNode.getChildAt(i)).getText(),
						(MyNode) downloadNode.getChildAt(i), collName);
				// 递归调用
			}
		} else {//下载文件
			//FIXME
			String downNode_Path = "/";
			String Item_name = "";
			int path_count = 0;
			String[] DNPath = new String[100];
			MyNode getTN_Parnet = (MyNode) downloadNode.getParent();
			while (!(getTN_Parnet.getText().equals(((MyNode) downloadNode
					.getRoot()).getText()))) {
				DNPath[path_count++] = getTN_Parnet.getText() + "/";
				getTN_Parnet = (MyNode) getTN_Parnet.getParent();

			}
			DNPath[path_count] = "库/";
			for (int i = path_count; i >= 0; i--) {
				downNode_Path += DNPath[i];
			}
			Item_name = DNPath[path_count - 1];

			Item_name = Item_name.substring(0, Item_name.length() - 1);
			DB db = main.mongo.getDB("test");
			GridFS gfs = new GridFS(db, Item_name);
			GridFSDBFile gfsFile = gfs.findOne(downNode_Path
					+ downloadNode.getText());
			try {
				
				gfsFile.writeTo(filepath);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static String getName(MyNode node) {
		String name = "";
		while (!node.isRoot()) {
			name = "/" + node.getText() + name;
			node = (MyNode) node.getParent();
		}
		name = "/库";
		return name;
	}
}
