package mongodb;

import javax.swing.tree.DefaultMutableTreeNode;

public class Deletedoc {
	public static void del() {
		DefaultMutableTreeNode iselete = (DefaultMutableTreeNode) main.myTree
				.getLastSelectedPathComponent();
		
		iselete.removeFromParent();
		main.myTree.updateUI();
	}
}
