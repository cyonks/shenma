package jtr;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
//import mypackage.ProgramToHtml;

public class JtreeFile extends JPanel implements TreeSelectionListener,
		TreeExpansionListener, MouseListener, ActionListener, KeyListener {

	JTree jtree1 = new JTree();
	DefaultTreeModel dftm;
	DefaultMutableTreeNode root, Child;
	JScrollPane jsp;
	JPanel jp1;
	DefaultTreeCellRenderer redder;
	JPopupMenu popupMenu = new JPopupMenu();

	//JMenuItem openFile, changeFile, exitFile, newName, delFile, newFile;
	Boolean isctrl = false, ischose = true;
	File[] fi2;
	int n = 0;
	ArrayList<File> a = new ArrayList<File>();
	ArrayList<File> b = new ArrayList<File>();


	public JtreeFile() {
		root = new DefaultMutableTreeNode("Desktop");
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			Child = new DefaultMutableTreeNode(new FileNode(roots[i]));
			root.add(Child);
			Child.add(new DefaultMutableTreeNode(true));
		}

		dftm = new DefaultTreeModel(root);
		jtree1 = new JTree(dftm);


		jtree1.setCellRenderer(new TReeCellRenderer());
		jtree1.addTreeSelectionListener(this);

		jtree1.addTreeExpansionListener(this);
		jtree1.addMouseListener(this);
		jtree1.addKeyListener(this);
		jtree1.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		this.setLayout(new BorderLayout());
		jtree1.add(popupMenu);
		// jtree1.setEditable(true);
		jsp = new JScrollPane(jtree1); // 滚动
		// jp1 = new JPanel(new GridLayout(1, 2));
		this.add(jsp);

	}

	DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode getFileNode(DefaultMutableTreeNode node) {
		if (node == null) {
			return null;
		}
		Object obj = node.getUserObject();
		if (obj instanceof FileNode) {
			return (FileNode) obj;
		} else {
			return null;
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = getTreeNode(e.getPath());
		FileNode fnode = getFileNode(node);
		try {
			if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
				b.add(new File(fnode.getFile().getAbsolutePath()));
			}
		} catch (Exception e2) {
		}
		if (isctrl) {
			if (ischose) {
				if (b.size() >= 2) {
					a.add(b.get(b.size() - 2));
				}

				ischose = false;
			}
			try {
				if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
					a.add(new File(fnode.getFile().getAbsolutePath()));
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public void treeExpanded(TreeExpansionEvent e) {
		final DefaultMutableTreeNode node = getTreeNode(e.getPath());
		final FileNode fnode = getFileNode(node);
		Thread runner = new Thread() {

			public void run() {
				if (fnode != null && fnode.expand(node)) {
					Runnable runnable = new Runnable() {

						public void run() {
							dftm.reload(node);
						}

					};
					SwingUtilities.invokeLater(runnable);
				}
			}
		};
		runner.start();

	}

	public void treeCollapsed(TreeExpansionEvent event) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		JTree tree = (JTree) e.getSource();
		int selRow = tree.getRowForLocation(e.getX(), e.getY());
		if (selRow != -1) {
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			TreeNode node = (TreeNode) selPath.getLastPathComponent();
			FileNode fnode = getFileNode((DefaultMutableTreeNode) node);

			// 是叶节点
			if (node.isLeaf()) {
				// 此处有bug，默认每次新打开的节点都是叶节点
				//
				// System.out.println(123);
				if (e.getClickCount() == 1) {
					// 单击
					try {
						if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
							// 右击事件
							if (!new File(fnode.getFile().getAbsolutePath())
									.isDirectory()) {
								// 如果为文件夹就不触发
								// 弹出菜单
								popupMenu.show(tree, e.getX(), e.getY());

							}

						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				} else if (e.getClickCount() == 2) {
					// 双击
					// System.out.println(fnode.getFile().getAbsolutePath());
					if (!new File(fnode.getFile().getAbsolutePath())
							.isDirectory()) {
						// 如果为文件夹就不触发

						File[] fi = { new File(fnode.getFile()
								.getAbsolutePath()) };
						//ProgramToHtml.getFrame().open(fi);
						// System.out.println(fnode.getFile().getAbsolutePath());
					}
				}
			}

		}
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isctrl = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isctrl = false;
			ischose = true;
			b.clear();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}