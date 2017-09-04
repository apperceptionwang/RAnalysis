package mongodb;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

public class Adddoc {
	public static void add() {
		MyNode node = (MyNode) main.myTree
				.getLastSelectedPathComponent();
		if (!node.getAllowsChildren()) {
			JOptionPane.showMessageDialog(null, "�޷�������Ŀ¼");
			return ;
		}

		MyNode child = new MyNode(new ImageIcon(icon.FOLDER_CLOSE),"�½�Ŀ¼");
		node.add(child);
		if (node.isRoot()) {
			((MyNode)node.getChildBefore(child)).removeFromParent();
			node.add(new MyNode(new ImageIcon(""),""));
		}
		TreePath path = main.myTree.getSelectionPath();
		main.myTree.expandPath(path);
		main.myTree.updateUI();
		path = new TreePath(child.getPath());

		RenameTree.rename(path,true);
	}
}
