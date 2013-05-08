import java.io.File;
import java.text.NumberFormat;
import org.eclipse.swt.layout.FillLayout; 
import org.eclipse.swt.graphics.Image; 
import org.eclipse.swt.graphics.Point; 
import org.eclipse.swt.widgets.Event; 
import org.eclipse.swt.widgets.Listener; 
import org.eclipse.swt.widgets.Shell; 
import org.eclipse.swt.widgets.Display; 
import org.eclipse.swt.widgets.Tree; 
import org.eclipse.swt.widgets.TreeItem; 
import org.eclipse.swt.SWT;
public class Treetest { 
    //类似资源管理器的树，其中包含鼠标双击事件 
    public static void main(String[] args) { 
    	File[] files = File.listRoots();
    	for(int i=0;i<files.length;i++){
    		System.out.println(files[i].toString()+"t");
    	}
        final Display display = new Display(); 
        final Shell shell = new Shell(display); 
        shell.setText("系统文件夹目录的Tree显示"); 
        shell.setLayout(new FillLayout(SWT.VERTICAL));        final Tree tree = new Tree(shell, SWT.BORDER); 
        TreeItem computer = new TreeItem(tree, SWT.NONE); 
        computer.setText("我的电脑"); 
        Image computericon = new Image(shell.getDisplay(), 
                "E:\\baidu player\\14.ico");// 创建图像 
        computer.setImage(computericon);        //返回可获得的文件根目录，即本地文件系统结构的最顶层（如Windows平台上的盘符） 
        File[] roots = File.listRoots(); 
        for (int i = 0; i < roots.length; i++) { 
            //                TreeItem root = new TreeItem(tree, 0); 
            TreeItem root = new TreeItem(computer, 0); 
            root.setText(roots[i].toString()); 
            root.setData(roots[i]);//保存当前节点数据 
            Image cdicon = new Image(shell.getDisplay(), "E:\\baidu player\\14.ico");// 创建磁盘图像 
            root.setImage(cdicon); 
            new TreeItem(root, 0);//把当前节点作为目录节点        }        
            tree.addListener(SWT.Expand, new Listener() { 
            public void handleEvent(final Event event) { 
                final TreeItem root = (TreeItem) event.item; 
                TreeItem[] items = root.getItems(); 
                for (int i = 0; i < items.length; i++) { 
                    if (items[i].getData() != null) 
                        return; 
                    items[i].dispose(); 
                } 
                File file = (File) root.getData(); 
                File[] files = file.listFiles();//返回File对象（文件夹与文件）的数组 
                //采用文件夹目录过滤器来判断当前路径是否为文件夹，返回File对象（文件夹）的数组 
                //File[] files = file.listFiles(new DirFilter()); 
                // 列出该目录中的文件名与文件夹名，返回一个文件名数组 
                // String[] filespath=file.list(); 
                // for (int i = 0; i < filespath.length; i++) { 
                // System.out.println("文件为： " + filespath[i]); 
                // }                if (files == null) 
                 //  return; 
                for (int i = 0; i < files.length; i++) { 
                    //不显示隐藏文件的路径                    //if(files[i].isHidden()==false){//判断当前路径的文件是否为隐藏文件 
                    if ((files[i].isHidden() == false) 
                            && (files[i].isFile() == false)) {//判断当前路径是否为隐藏文件与文件夹 
                        TreeItem item = new TreeItem(root, 0); 
                        item.setText(files[i].getName()); 
                        //叶子节点对应的数值为相应文件夹的File对象 
                        item.setData(files[i]); 
                        Image foldericon = new Image(shell.getDisplay(), 
                                "E:\\baidu player\\14.ico");// 创建文件夹图像 
                        item.setImage(foldericon); 
                        //当前为文件目录而不是文件的时候，添加新项目，以便只是显示文件夹（包括空文件夹），而不显示文件夹下的文件 
                        if (files[i].isDirectory()) { 
                            new TreeItem(item, 0); 
                        } 
                    } 
                } 
            } 
        });        //        鼠标单击选择树的叶子节点事件 
        final Treetest tr = new Treetest(); 
        tree.addListener(SWT.MouseDown, new Listener() { 
            public void handleEvent(Event event) { 
                Point point = new Point(event.x, event.y); 
                TreeItem item = tree.getItem(point); 
                if ((item != null) && (item.getData() != null)) { 
                    System.out.println("您选中的文件夹名是：" + item.getText()); 
                    File s = (File) item.getData(); 
                    // 得到本地的缺省格式 
                    NumberFormat nf = NumberFormat.getInstance(); 
                    double fsizebyte = (double) tr.getDirectorySize(s); 
                    System.out.println("您选中的文件夹大小是：" + nf.format(fsizebyte) 
                            + "字节"); 
                    double fsizemb = (double) tr.getDirectorySize(s) / 1024; 
                    System.out.println("您选中的文件夹大小是：" + nf.format(fsizemb) 
                            + "MB"); 
                    System.out.println("您选中的文件夹路径是：" + item.getData());                } 
            } 
        });        //根据Tree的大小更改shell的大小 
        Point size = tree.computeSize(300, SWT.DEFAULT); 
        int width = Math.max(300, size.x); 
        int height = Math.max(300, size.y); 
        shell.setSize(shell.computeSize(width, height)); 
        shell.open(); 
        while (!shell.isDisposed()) { 
            if (!display.readAndDispatch()) 
                display.sleep(); 
        } 
        display.dispose();
        } 
    }
        /** 
    * 计算文件夹目录下文件的整体大小(单位为：字节) 
    */ 
    public long getDirectorySize(File dir) { 
        long retSize = 0;//文件夹目录下文件的大小 
        if ((dir == null) || !dir.isDirectory()) { 
            return retSize; 
        } 
        File[] entries = dir.listFiles(); 
        int count = entries.length; 
        for (int i = 0; i < count; i++) { 
            if (entries[i].isDirectory()) { 
                retSize += getDirectorySize(entries[i]); 
            } else { 
                retSize += entries[i].length(); 
            } 
        } 
        return retSize; 
    }} 
