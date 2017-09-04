package mongodb;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;  
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultTreeCellRenderer;  
  
/** 
 * 自定义树描述类,将树的每个节点设置成不同的图标 
 * @author RuiLin.Xie - xKF24276 
 * 
 */  
public class treelogo extends DefaultTreeCellRenderer{  
	/*
	 这个方法返回一个Component这个控件，也就是你要设置的树中节点的显示风格，
	 当然在实现的时候你可以继承一个Jcomponent类的子类，
	 也可以在类中设置一个私有变量然后返回。在这个方法中有很多参数，
	 首先是Jtree 这个就是你要设置的树，对应的对象，
	 然后是value,这个其实是节点，通过他你可以获得节点的数据，以及对应的子节点父节点等。
    接着是sel表示如果被选中时该如何显示。
     Expanded则表示如果出于扩展状态如何显示节点，
    然后是leaf，叶子节点的显示方式可以通过这个条件设置。
    还有row这个参数，对于那些没有伸缩的可以，如果有伸缩的话，row是随时改变的所以用的时候要小心，
    最后一个是是否拥有焦点，设置拥有焦点时的显示方式。
    实现了这个方法后，用setCellRende()方法设置一下这个类的实例就行了。
	 */
    private static final long   serialVersionUID    = 1L;
 
  
    /** 
     * 重写父类DefaultTreeCellRenderer的方法 
     */  
    @Override  
    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus){  
  
        //执行父类原型操作  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);  
     
        //句柄展开时的图标
        //ImageIcon loginIcon= new ImageIcon(this.getClass().getResource("/login.jpg"));
        ImageIcon ExpandedIcon=new ImageIcon(this.getClass().getResource("/jian.png"));

        //句柄折叠时的图标
        ImageIcon CollapsedIcon=new ImageIcon(this.getClass().getResource("/jia.png"));

        //叶节点的图标，也就是下面没有子结点的节点图标
        ImageIcon LeafIcon=new ImageIcon(this.getClass().getResource("/jishiben.jpg"));
       
        //非叶节点关闭时的图标，也就是下面有子结点的节点图标
        ImageIcon ClosedIcon=new ImageIcon(this.getClass().getResource("/wenjianjia.jpg"));
       
        //非叶节点打开时的图标
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



