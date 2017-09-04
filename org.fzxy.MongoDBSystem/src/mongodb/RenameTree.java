package mongodb;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

public class RenameTree {

	public static void rename(TreePath path,boolean isNewFile) {
		String newname = null;

		boolean isok = true;

		MyNode n = (MyNode) path.getLastPathComponent();
		MyNode par = (MyNode) n.getParent();
		int index = par.getIndex(n);
		String oldname = n.getText();
		
		do {
			isok = false;
			newname = JOptionPane.showInputDialog("请输入", n.getText());
		
			if (newname == null) {
				newname = oldname;
				if (!isNewFile) {
					break;
				}
				n.removeFromParent();
				break;
			}
			for (int i = 0; i < par.getChildCount(); i++) {
				if (i == index) {
					continue;
				}
				if (newname.equals(((MyNode) par.getChildAt(i)).getText())) {
					JOptionPane.showMessageDialog(null, "已存在的名字，请重新输入");
					isok = true;
					break;
				}
			}
		} while (isok);

		n.setText(newname);

		main.myTree.updateUI();
	}
}

// static class editName implements Runnable {
//
// @Override
// public void run() {
// // TODO Auto-generated method stub
// while (main.myTree.isEditing()) {
//
// try {
//
// Thread.sleep(1000);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
// System.out.println("编辑完成");
//
// }
//
// }
//
// }
