import java.awt.*;

import java.awt.event.*;

import java.io.*;

import java.util.*;

import javax.swing.*;

import javax.swing.tree.*;

import javax.swing.event.*;


public class FileTree1 extends JFrame

{
	private final static int WIDTH=800;
	private final static int HEIGHT=580;
	JMenuBar menuBar=new JMenuBar();
	
	JMenu menu_file=new JMenu("文件(F)");
	
	JMenuItem jmenu_new=new JMenuItem("新建");
	JMenuItem jmenu_open=new JMenuItem("打开");
	JMenuItem jmenu_loadin=new JMenuItem("导入");
	JMenuItem jmenu_exit=new JMenuItem("退出");
	
	JMenu menu_edit=new JMenu("编辑(E)");
	JMenuItem jmenu_copy=new JMenuItem("复制");
	JMenuItem jmenu_paste=new JMenuItem("粘贴");	
	JMenuItem jmenu_cut=new JMenuItem("剪切");
	JMenuItem jmenu_delete=new JMenuItem("删除");
	JMenuItem jmenu_rename=new JMenuItem("重命名");
    

	JMenu menu_view=new JMenu("视图(V)");

	JMenu jmenu_look=new JMenu("外观");
	
	JMenu menu_set=new JMenu("设置(S)");
	
	JMenu menu_help=new JMenu("帮助(H)");
	JMenuItem jmenu_desc=new JMenuItem("说明");
	
	
	
	public static final ImageIcon ICON_COMPUTER =	new ImageIcon("E:\\PC_Icon.jpg");
	public static final ImageIcon ICON_DISK =new ImageIcon("E:\\Disk_Icon.jpg");
	public static final ImageIcon ICON_FOLDER =new ImageIcon("E:\\4.jpg");
	public static final ImageIcon ICON_EXPANDEDFOLDER =new ImageIcon("E:\\PC_Icon.jpg");

	protected JTree m_tree;
	protected DefaultTreeModel m_model;
	protected JTextField m_display;

	public FileTree1()

	{

        
		 UIManager.LookAndFeelInfo[] infos=UIManager.getInstalledLookAndFeels();
		 for(UIManager.LookAndFeelInfo info :infos){
			makeJMenuItemLooks(info.getName(),info.getClassName());
		 }
   	 menu_file.setMnemonic('F');
   	 menu_edit.setMnemonic('E');
   	 menu_view.setMnemonic('V');
   	 menu_set.setMnemonic('S');
   	 menu_help.setMnemonic('H');
   	 
   	 //菜单选项快捷键设置
   	 jmenu_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
   	 jmenu_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
   	 jmenu_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
   	 jmenu_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
   	 
   	 
   	 setJMenuBar(menuBar);
   	 
   	 menuBar.add(menu_file);
   	 menuBar.add(menu_edit);
   	 menuBar.add(menu_view);
   	 menuBar.add(menu_set);
   	 menuBar.add(menu_help);
   	 setSize(WIDTH,HEIGHT);
   	 setTitle("magic图片处理(Beta版)");
   	 Toolkit kit=Toolkit.getDefaultToolkit();
   	 Image img=kit.getImage("E:\\2.jpg");
   	 setIconImage(img);
   	 //this.getContentPane().setLayout(new BorderLayout());
   	 menu_file.add(jmenu_new);
   	 menu_file.add(jmenu_open);
   	 menu_file.add(jmenu_loadin);
   	 menu_file.add(jmenu_exit);
   	 
   	 menu_edit.add(jmenu_copy);
        menu_edit.add(jmenu_paste);
        menu_edit.add(jmenu_cut);
        menu_edit.add(jmenu_delete);
        menu_edit.add(jmenu_rename);
   	 
        menu_view.add(jmenu_look);
   	 menu_help.add(jmenu_desc);
   	 
   	   MyImageJPanel panel=new MyImageJPanel();
   	   add(panel);
        JPopupMenu popup=new JPopupMenu();
        JMenuItem item=new JMenuItem("复制");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        popup.add(item);
        JMenuItem item2=new JMenuItem("粘贴");
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
        popup.add(item2);
        JMenuItem item3=new JMenuItem("剪切");
        item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
        popup.add(item3);
        panel.setComponentPopupMenu(popup);

		
		JToolBar bar=new JToolBar();
        this.add(bar,BorderLayout.NORTH);
        ImageIcon lastIcon=new ImageIcon("Image_Use/last.jpg");
        JButton button_last=new JButton(lastIcon);
        ImageIcon nextIcon=new ImageIcon("Image_Use/next.jpg");
        JButton button_next=new JButton(nextIcon);
       
        bar.add(button_last);
        bar.add(button_next);
   	    button_last.setToolTipText("上一张(Page Up)");
   	    button_next.setToolTipText("下一张(Page Down)");

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(ICON_COMPUTER, null, "Computer"));

		DefaultMutableTreeNode node;

		File[] roots = File.listRoots();

		for (int k = 0; k < roots.length; k++)

		{

			node = new DefaultMutableTreeNode(new IconData(ICON_DISK,null, new FileNode(roots[k])));

			top.add(node);

			node.add(new DefaultMutableTreeNode(new Boolean(true)));

		}

		m_model = new DefaultTreeModel(top);

		m_tree = new JTree(m_model);

		m_tree.putClientProperty("JTree.lineStyle", "Angled");

		TreeCellRenderer renderer = new	IconCellRenderer();

		m_tree.setCellRenderer(renderer);

		m_tree.addTreeExpansionListener(new DirExpansionListener());

		m_tree.addTreeSelectionListener(new DirSelectionListener());

		m_tree.getSelectionModel().setSelectionMode(

		TreeSelectionModel.SINGLE_TREE_SELECTION);

		m_tree.setShowsRootHandles(true);

		m_tree.setEditable(false);
		m_tree.putClientProperty("JTree.lineStyle", "None");// Angled


		JScrollPane s = new JScrollPane();
		s.getViewport().add(m_tree);

		//getContentPane().add(s, BorderLayout.CENTER);
		JPanel pan=new JPanel();
		JSplitPane jsplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,s,pan);
        add(jsplit);
		m_display = new JTextField();
        m_display.setHorizontalAlignment(JTextField.CENTER);
		m_display.setEditable(false);
        
		getContentPane().add(m_display, BorderLayout.SOUTH);
        add(bar,BorderLayout.NORTH);
		WindowListener wndCloser = new WindowAdapter()

		{

			public void windowClosing(WindowEvent e)

			{

				System.exit(0);

			}

		};

		addWindowListener(wndCloser);

		setVisible(true);

	   	 try{
			 String plaf="com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
			 UIManager.setLookAndFeel(plaf);
			 SwingUtilities.updateComponentTreeUI(this);
		 }
		 catch(Exception ex){
			 ex.printStackTrace();
		 }
		
	}
	public void makeJMenuItemLooks(String name,final String plafName){
   	 JMenuItem button=new JMenuItem(name);
		 jmenu_look.add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try{
					
					UIManager.setLookAndFeel(plafName);
					SwingUtilities.updateComponentTreeUI(FileTree1.this);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
    }

	DefaultMutableTreeNode getTreeNode(TreePath path)

	{

		return (DefaultMutableTreeNode) (path.getLastPathComponent());

	}

	FileNode getFileNode(DefaultMutableTreeNode node)

	{

		if (node == null)

			return null;

		Object obj = node.getUserObject();

		if (obj instanceof IconData)

			obj = ((IconData) obj).getObject();

		if (obj instanceof FileNode)

			return (FileNode) obj;

		else

			return null;

	}

	// Make sure expansion is threaded and updating the tree model

	// only occurs within the event dispatching thread.

	class DirExpansionListener implements TreeExpansionListener

	{

		public void treeExpanded(TreeExpansionEvent event)

		{

			final DefaultMutableTreeNode node = getTreeNode(

			event.getPath());

			final FileNode fnode = getFileNode(node);

			Thread runner = new Thread()

			{

				public void run()

				{

					if (fnode != null && fnode.expand(node))

					{

						Runnable runnable = new Runnable()

						{

							public void run()

							{

								m_model.reload(node);

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

	}

	class DirSelectionListener implements TreeSelectionListener

	{

		public void valueChanged(TreeSelectionEvent event)

		{

			DefaultMutableTreeNode node = getTreeNode(

			event.getPath());

			FileNode fnode = getFileNode(node);

			if (fnode != null)

				m_display.setText(fnode.getFile().

				getAbsolutePath());

			else

				m_display.setText("");

		}

	}

	public static void main(String argv[])

	{

		new FileTree1();

	}

}

class IconCellRenderer extends JLabel

implements TreeCellRenderer

{

	protected Color m_textSelectionColor;

	protected Color m_textNonSelectionColor;

	protected Color m_bkSelectionColor;

	protected Color m_bkNonSelectionColor;

	protected Color m_borderSelectionColor;

	protected boolean m_selected;

	public IconCellRenderer()

	{

		super();

		m_textSelectionColor = UIManager.getColor(

		"Tree.selectionForeground");

		m_textNonSelectionColor = UIManager.getColor(

		"Tree.textForeground");

		m_bkSelectionColor = UIManager.getColor(

		"Tree.selectionBackground");

		m_bkNonSelectionColor = UIManager.getColor(

		"Tree.textBackground");

		m_borderSelectionColor = UIManager.getColor(

		"Tree.selectionBorderColor");

		setOpaque(false);

	}

	public Component getTreeCellRendererComponent(JTree tree,

	Object value, boolean sel, boolean expanded, boolean leaf,

	int row, boolean hasFocus)

	{

		DefaultMutableTreeNode node =

		(DefaultMutableTreeNode) value;

		Object obj = node.getUserObject();

		setText(obj.toString());

		if (obj instanceof Boolean)

			setText("Retrieving data");

		if (obj instanceof IconData)

		{

			IconData idata = (IconData) obj;

			if (expanded)

				setIcon(idata.getExpandedIcon());

			else

				setIcon(idata.getIcon());

		}

		else

			setIcon(null);

		setFont(tree.getFont());

		setForeground(sel ? m_textSelectionColor :

		m_textNonSelectionColor);

		setBackground(sel ? m_bkSelectionColor :

		m_bkNonSelectionColor);

		m_selected = sel;

		return this;

	}

	public void paintComponent(Graphics g)

	{

		Color bColor = getBackground();

		Icon icon = getIcon();

		g.setColor(bColor);

		int offset = 0;

		if (icon != null && getText() != null)

			offset = (icon.getIconWidth() + getIconTextGap());

		g.fillRect(offset, 0, getWidth() - 1 - offset,

		getHeight() - 1);

		if (m_selected)

		{

			g.setColor(m_borderSelectionColor);

			g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

		}

		super.paintComponent(g);

	}

}

class IconData

{

	protected Icon m_icon;

	protected Icon m_expandedIcon;

	protected Object m_data;

	public IconData(Icon icon, Object data)

	{

		m_icon = icon;

		m_expandedIcon = null;

		m_data = data;

	}

	public IconData(Icon icon, Icon expandedIcon, Object data)

	{

		m_icon = icon;

		m_expandedIcon = expandedIcon;

		m_data = data;

	}

	public Icon getIcon()

	{

		return m_icon;

	}

	public Icon getExpandedIcon()

	{

		return m_expandedIcon != null ? m_expandedIcon : m_icon;

	}

	public Object getObject()

	{

		return m_data;

	}

	public String toString()

	{

		return m_data.toString();

	}

}

class FileNode

{

	protected File m_file;

	public FileNode(File file)

	{

		m_file = file;

	}

	public File getFile()

	{

		return m_file;

	}

	public String toString()

	{

		return m_file.getName().length() > 0 ? m_file.getName() :

		m_file.getPath();

	}

	public boolean expand(DefaultMutableTreeNode parent)

	{

		DefaultMutableTreeNode flag =

		(DefaultMutableTreeNode) parent.getFirstChild();

		if (flag == null) // No flag

			return false;

		Object obj = flag.getUserObject();

		if (!(obj instanceof Boolean))

			return false; // Already expanded

		parent.removeAllChildren(); // Remove Flag

		File[] files = listFiles();

		if (files == null)

			return true;

		Vector v = new Vector();

		for (int k = 0; k < files.length; k++)

		{

			File f = files[k];

			if (!(f.isDirectory()))

				continue;

			FileNode newNode = new FileNode(f);

			boolean isAdded = false;

			for (int i = 0; i < v.size(); i++)

			{

				FileNode nd = (FileNode) v.elementAt(i);

				if (newNode.compareTo(nd) < 0)

				{

					v.insertElementAt(newNode, i);

					isAdded = true;

					break;

				}

			}

			if (!isAdded)

				v.addElement(newNode);

		}

		for (int i = 0; i < v.size(); i++)

		{

			FileNode nd = (FileNode) v.elementAt(i);

			IconData idata = new IconData(FileTree1.ICON_FOLDER,

			FileTree1.ICON_EXPANDEDFOLDER, nd);

			DefaultMutableTreeNode node = new

			DefaultMutableTreeNode(idata);

			parent.add(node);

			if (nd.hasSubDirs())

				node.add(new DefaultMutableTreeNode(

				new Boolean(true)));

		}

		return true;

	}

	public boolean hasSubDirs()

	{

		File[] files = listFiles();

		if (files == null)

			return false;

		for (int k = 0; k < files.length; k++)

		{

			if (files[k].isDirectory())

				return true;

		}

		return false;

	}

	public int compareTo(FileNode toCompare)

	{

		return m_file.getName().compareToIgnoreCase(

		toCompare.m_file.getName());

	}

	protected File[] listFiles()

	{

		if (!m_file.isDirectory())

			return null;

		try

		{

			return m_file.listFiles();

		}

		catch (Exception ex)

		{

			JOptionPane.showMessageDialog(null,

			"Error reading directory " + m_file.getAbsolutePath(),

			"Warning", JOptionPane.WARNING_MESSAGE);

			return null;

		}

	}

}
