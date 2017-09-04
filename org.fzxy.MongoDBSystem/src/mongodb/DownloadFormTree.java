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
	 * ����
	 *
	 * */
	static DB db;

	public static void download() throws IOException {
		String collName = "";
		db = main.mongo.getDB("test");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("���ص�");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// ѡ������λ��
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();// ��ȡѡ�������λ��

			MyNode downloadNode = (MyNode) main.myTree
					.getLastSelectedPathComponent();// ��ȡѡ�еĽڵ�
			collName = uploadFiles.getCollName(downloadNode);
			filePath = filePath + "/" + downloadNode.getText();// ����·��+����
			File rootFile = new File(filePath);
			if (rootFile.exists()) {// �������λ���Ѵ��ڴ��ļ����У����޷�����
				JOptionPane.showMessageDialog(null, "�Ѵ��ڵ��ļ�    ����ʧ��");
				return;
			}

			writeFile(filePath, downloadNode, collName);
			JOptionPane.showMessageDialog(null, "�������");
		}
	}

	/**
	 * 
	 * @param filepath
	 *            ���ص�λ��·��
	 * @param downloadNode
	 *            Ҫ���صĽڵ�
	 * */
	private static void writeFile(String filepath, MyNode downloadNode,
			String collName) throws IOException {
		if (downloadNode.getAllowsChildren()) {// �������ӽڵ� �ļ���
			File file = new File(filepath);
			file.mkdirs();// �����ļ���

			for (int i = 0; i < downloadNode.getChildCount(); i++) {
				writeFile(filepath + "/"
						+ ((MyNode) downloadNode.getChildAt(i)).getText(),
						(MyNode) downloadNode.getChildAt(i), collName);
				// �ݹ����
			}
		} else {//�����ļ�
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
			DNPath[path_count] = "��/";
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
		name = "/��";
		return name;
	}
}
