package mongodb;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyNodeRenderer extends DefaultTreeCellRenderer// �̳и���
{
	// ��д�÷���
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus); // ���ø���ĸ÷���
		Icon icon = ((MyNode) value).getIcon();// �ӽڵ��ȡͼƬ
		String txt = ((MyNode) value).getText(); // �ӽڵ��ȡ�ı�
		setIcon(icon);// ����ͼƬ
		setText(txt);// �����ı�
		return this;
	}
}

// ����ڵ���
class MyNode extends DefaultMutableTreeNode {
	protected Icon icon;
	protected String txt;

	// ֻ�����ı��Ľڵ㹹��
	public MyNode(String txt) {
		super();
		this.txt = txt;
	}

	// �����ı���ͼƬ�Ľڵ㹹��
	public MyNode(Icon icon, String txt) {
		super();
		this.icon = icon;
		this.txt = txt;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setText(String txt) {
		this.txt = txt;
	}

	public String getText() {
		return txt;
	}
}
