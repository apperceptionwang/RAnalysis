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
	 *            �ڴ˽ڵ�������ӽڵ�
	 * @param filePath
	 *            ��ӽڵ���ļ�·��
	 * @param fileName
	 *            ��ӽڵ���ļ�����
	 * @param cheak
	 *            �Ƿ�������
	 * 
	 * @return �Ƿ��ϴ��ɹ�
	 * */
	public static boolean addtree(MyNode root, String filePath,
			String fileName, boolean cheak) {
		File upFile = new File(filePath);

		if (cheak) {
			// �������
			for (int i = 0; i < root.getChildCount(); i++) {

				if (fileName.equals(((MyNode) root.getChildAt(i)).getText())) {
					JOptionPane.showMessageDialog(null, "�Ѵ����ļ���  " + fileName
							+ "   ������ѡ��");
					return false;
				}
			}
		}

		MyNode childNode = null;
		if (!upFile.isDirectory()) {// ������ļ�
			// TODO �ϴ������ݿ�
			if (root.isRoot()) {
				JOptionPane.showMessageDialog(null, "�޷��ڴ��ϴ��ļ�");
				return false;
			}
			String[] endIndex = upFile.getName().split("\\.");
			String type = endIndex[endIndex.length - 1];// ��ȡ��׺
			ImageIcon ic = ChoiceIcon.choiceicon(type);
			MyNode tNode = new MyNode(ic, upFile.getName());
			tNode.setAllowsChildren(false);// �����Ƿ��������ӽڵ��ж��Ƿ�Ϊ�ļ�
			root.add(tNode);
			String collName = getCollName(root);
			uptoDBdata(tNode, filePath, collName);

		} else {// ������ļ�������������ӽڵ�
			File[] files = upFile.listFiles();
			childNode = new MyNode(new ImageIcon(icon.FOLDER_CLOSE), fileName);
			root.add(childNode);
			for (int i = 0; i < files.length; i++) {
				String[] names = files[i].toString().split("\\\\");
				String name = names[names.length - 1];
				if (!addtree(childNode, files[i].toString(), name, false)) {
					return false;
				}// �ݹ����
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
