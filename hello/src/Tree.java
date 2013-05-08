import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;
final public class Tree extends JFrame {
    public Tree(File dir) throws HeadlessException {
        super("File Tree");
        JTree tree;
        add(new JScrollPane(tree =new JTree(buildTreeModel(dir))));
        tree.setCellRenderer(new FileTreeRenderer());
        setSize(400,600);
        setVisible(true);
    }

    private TreeModel buildTreeModel(File dir){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(dir);
        walkthrough(dir,root);
        return new DefaultTreeModel(root);
    }

    private static void walkthrough(File f,DefaultMutableTreeNode node){
        for (File fle : f.listFiles()) {
            if(fle.isDirectory()||fle.getName().toString().toLowerCase().matches(".+\\.(jpg|gif|jpeg|bmp|png)")){
                    	DefaultMutableTreeNode n = new DefaultMutableTreeNode(fle);
            node.add(n);
            if (fle.isDirectory()){
                walkthrough(fle, n);
            }	
            }

        }
    }

    private class FileTreeRenderer extends DefaultTreeCellRenderer  {
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            JLabel cmp = (JLabel)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode)value;
                Object obj = n.getUserObject();
                if (obj instanceof File) {
                    File f = (File)obj;
                    cmp.setText(f.getName());
                    cmp.setForeground(f.isDirectory()?Color.BLUE:Color.BLACK);
                }
            }
            return cmp;
        }
    }

    public static void main(String[] args)throws Exception {
    	File[] root=File.listRoots();
        new Tree(new File("E:"));
    }
}