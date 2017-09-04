package mongodb;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class uploadFiles {
	/**
	 * @param root
	 *            在此节点下添加子节点
	 * @param filePath
	 *            添加节点的文件路径
	 * @param fileName
	 *            添加节点的文件名称
	 * @param cheak
	 *            是否检查重名
	 * 
	 * @return 是否上传成功
	 * */
	public static boolean addtree(MyNode root, String filePath,
			String fileName, boolean cheak) {
		File upFile = new File(filePath);

		if (cheak) {
			// 检查重名
			for (int i = 0; i < root.getChildCount(); i++) {

				if (fileName.equals(((MyNode) root.getChildAt(i)).getText())) {
					JOptionPane.showMessageDialog(null, "已存在文件夹  " + fileName
							+ "   请重新选择");
					return false;
				}
			}
		}

		MyNode childNode = null;
		if (!upFile.isDirectory()) {// 如果是文件
			// TODO 上传至数据库
			if (root.isRoot()) {
				JOptionPane.showMessageDialog(null, "无法在此上传文件");
				return false;
			}
			String[] endIndex = upFile.getName().split("\\.");
			String type = endIndex[endIndex.length - 1];// 获取后缀
			ImageIcon ic = ChoiceIcon.choiceicon(type);
			MyNode tNode = new MyNode(ic, upFile.getName());
			tNode.setAllowsChildren(false);// 根据是否允许有子节点判断是否为文件
			root.add(tNode);
			String collName = getCollName(root);
			uptoDBdata(tNode, filePath, collName);

		} else {// 如果是文件夹则继续建立子节点
			File[] files = upFile.listFiles();
			childNode = new MyNode(new ImageIcon(icon.FOLDER_CLOSE), fileName);
			root.add(childNode);
			for (int i = 0; i < files.length; i++) {
				String[] names = files[i].toString().split("\\\\");
				String name = names[names.length - 1];
				if (!addtree(childNode, files[i].toString(), name, false)) {
					return false;
				}// 递归调用
			}
		}

		if (root.isRoot()) {
			((MyNode) root.getChildBefore(childNode)).removeFromParent();
			root.add(new MyNode(new ImageIcon(""), ""));
		}
		TreePath path = main.myTree.getSelectionPath();
		main.myTree.expandPath(path);
		main.myTree.updateUI();

		return true;
	}

	public static void uptoDBdata(MyNode root,
			String filePath, String collName){
		// TreeNode
		TreeNode[] treeNodes = root.getPath();
		File upFile = new File(filePath);

		String name = "";
		for (int i = 0; i < treeNodes.length; i++) {
			MyNode t = (MyNode) treeNodes[i];
			
			name += "/";
			name += t.getText();
		}
		
		DB db = main.mongo.getDB("test");
		if (!db.collectionExists(collName)) {
			DBObject options = new BasicDBObject().append("capped", false);
			db.createCollection(collName, options);
		}
		GridFS gfs = new GridFS(db, collName);
		GridFSInputFile gfsFile = null;
		try {
			gfsFile = gfs.createFile(upFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gfsFile.setFilename(name);
		gfsFile.save();
	}

	public static String getCollName(MyNode node) {
		if (node.isRoot()) {
			return null;
		}
		while (node.getParent() != node.getRoot()) {
			node = (MyNode) node.getParent();
		}
		return node.getText();
	}

}
