package mongodb;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;  
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultTreeCellRenderer;  
  
/** 
 * �Զ�����������,������ÿ���ڵ����óɲ�ͬ��ͼ�� 
 * @author RuiLin.Xie - xKF24276 
 * 
 */  
public class treelogo extends DefaultTreeCellRenderer{  
	/*
	 �����������һ��Component����ؼ���Ҳ������Ҫ���õ����нڵ����ʾ���
	 ��Ȼ��ʵ�ֵ�ʱ������Լ̳�һ��Jcomponent������࣬
	 Ҳ��������������һ��˽�б���Ȼ�󷵻ء�������������кܶ������
	 ������Jtree ���������Ҫ���õ�������Ӧ�Ķ���
	 Ȼ����value,�����ʵ�ǽڵ㣬ͨ��������Ի�ýڵ�����ݣ��Լ���Ӧ���ӽڵ㸸�ڵ�ȡ�
    ������sel��ʾ�����ѡ��ʱ�������ʾ��
     Expanded���ʾ���������չ״̬�����ʾ�ڵ㣬
    Ȼ����leaf��Ҷ�ӽڵ����ʾ��ʽ����ͨ������������á�
    ����row���������������Щû�������Ŀ��ԣ�����������Ļ���row����ʱ�ı�������õ�ʱ��ҪС�ģ�
    ���һ�����Ƿ�ӵ�н��㣬����ӵ�н���ʱ����ʾ��ʽ��
    ʵ���������������setCellRende()��������һ��������ʵ�������ˡ�
	 */
    private static final long   serialVersionUID    = 1L;
 
  
    /** 
     * ��д����DefaultTreeCellRenderer�ķ��� 
     */  
    @Override  
    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus){  
  
        //ִ�и���ԭ�Ͳ���  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);  
     
        //���չ��ʱ��ͼ��
        //ImageIcon loginIcon= new ImageIcon(this.getClass().getResource("/login.jpg"));
        ImageIcon ExpandedIcon=new ImageIcon(this.getClass().getResource("/jian.png"));

        //����۵�ʱ��ͼ��
        ImageIcon CollapsedIcon=new ImageIcon(this.getClass().getResource("/jia.png"));

        //Ҷ�ڵ��ͼ�꣬Ҳ��������û���ӽ��Ľڵ�ͼ��
        ImageIcon LeafIcon=new ImageIcon(this.getClass().getResource("/jishiben.jpg"));
       
        //��Ҷ�ڵ�ر�ʱ��ͼ�꣬Ҳ�����������ӽ��Ľڵ�ͼ��
        ImageIcon ClosedIcon=new ImageIcon(this.getClass().getResource("/wenjianjia.jpg"));
       
        //��Ҷ�ڵ��ʱ��ͼ��
        //System.err.println(this.getClass().getResource("wenjianjia.jpg"));
       ImageIcon OpenedIcon=new ImageIcon(this.getClass().getResource("/wenjianjia.jpg"));
       DefaultTreeCellRenderer render=(DefaultTreeCellRenderer)(tree.getCellRenderer());
       render.setLeafIcon(LeafIcon);
       render.setClosedIcon(ClosedIcon);
       render.setOpenIcon(OpenedIcon);
   
       // Swap text colors  
       Color textSelection = render.getTextSelectionColor();  
       render.setTextSelectionColor(render.getTextNonSelectionColor());  
       render.setTextNonSelectionColor(textSelection);

       BasicTreeUI ui=(BasicTreeUI)(tree.getUI());

       ui.setCollapsedIcon(CollapsedIcon);

       ui.setExpandedIcon(ExpandedIcon);

       return this;  
    }
}  



