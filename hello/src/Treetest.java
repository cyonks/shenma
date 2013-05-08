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
    //������Դ���������������а������˫���¼� 
    public static void main(String[] args) { 
    	File[] files = File.listRoots();
    	for(int i=0;i<files.length;i++){
    		System.out.println(files[i].toString()+"t");
    	}
        final Display display = new Display(); 
        final Shell shell = new Shell(display); 
        shell.setText("ϵͳ�ļ���Ŀ¼��Tree��ʾ"); 
        shell.setLayout(new FillLayout(SWT.VERTICAL));        final Tree tree = new Tree(shell, SWT.BORDER); 
        TreeItem computer = new TreeItem(tree, SWT.NONE); 
        computer.setText("�ҵĵ���"); 
        Image computericon = new Image(shell.getDisplay(), 
                "E:\\baidu player\\14.ico");// ����ͼ�� 
        computer.setImage(computericon);        //���ؿɻ�õ��ļ���Ŀ¼���������ļ�ϵͳ�ṹ����㣨��Windowsƽ̨�ϵ��̷��� 
        File[] roots = File.listRoots(); 
        for (int i = 0; i < roots.length; i++) { 
            //                TreeItem root = new TreeItem(tree, 0); 
            TreeItem root = new TreeItem(computer, 0); 
            root.setText(roots[i].toString()); 
            root.setData(roots[i]);//���浱ǰ�ڵ����� 
            Image cdicon = new Image(shell.getDisplay(), "E:\\baidu player\\14.ico");// ��������ͼ�� 
            root.setImage(cdicon); 
            new TreeItem(root, 0);//�ѵ�ǰ�ڵ���ΪĿ¼�ڵ�        }        
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
                File[] files = file.listFiles();//����File�����ļ������ļ��������� 
                //�����ļ���Ŀ¼���������жϵ�ǰ·���Ƿ�Ϊ�ļ��У�����File�����ļ��У������� 
                //File[] files = file.listFiles(new DirFilter()); 
                // �г���Ŀ¼�е��ļ������ļ�����������һ���ļ������� 
                // String[] filespath=file.list(); 
                // for (int i = 0; i < filespath.length; i++) { 
                // System.out.println("�ļ�Ϊ�� " + filespath[i]); 
                // }                if (files == null) 
                 //  return; 
                for (int i = 0; i < files.length; i++) { 
                    //����ʾ�����ļ���·��                    //if(files[i].isHidden()==false){//�жϵ�ǰ·�����ļ��Ƿ�Ϊ�����ļ� 
                    if ((files[i].isHidden() == false) 
                            && (files[i].isFile() == false)) {//�жϵ�ǰ·���Ƿ�Ϊ�����ļ����ļ��� 
                        TreeItem item = new TreeItem(root, 0); 
                        item.setText(files[i].getName()); 
                        //Ҷ�ӽڵ��Ӧ����ֵΪ��Ӧ�ļ��е�File���� 
                        item.setData(files[i]); 
                        Image foldericon = new Image(shell.getDisplay(), 
                                "E:\\baidu player\\14.ico");// �����ļ���ͼ�� 
                        item.setImage(foldericon); 
                        //��ǰΪ�ļ�Ŀ¼�������ļ���ʱ���������Ŀ���Ա�ֻ����ʾ�ļ��У��������ļ��У���������ʾ�ļ����µ��ļ� 
                        if (files[i].isDirectory()) { 
                            new TreeItem(item, 0); 
                        } 
                    } 
                } 
            } 
        });        //        ��굥��ѡ������Ҷ�ӽڵ��¼� 
        final Treetest tr = new Treetest(); 
        tree.addListener(SWT.MouseDown, new Listener() { 
            public void handleEvent(Event event) { 
                Point point = new Point(event.x, event.y); 
                TreeItem item = tree.getItem(point); 
                if ((item != null) && (item.getData() != null)) { 
                    System.out.println("��ѡ�е��ļ������ǣ�" + item.getText()); 
                    File s = (File) item.getData(); 
                    // �õ����ص�ȱʡ��ʽ 
                    NumberFormat nf = NumberFormat.getInstance(); 
                    double fsizebyte = (double) tr.getDirectorySize(s); 
                    System.out.println("��ѡ�е��ļ��д�С�ǣ�" + nf.format(fsizebyte) 
                            + "�ֽ�"); 
                    double fsizemb = (double) tr.getDirectorySize(s) / 1024; 
                    System.out.println("��ѡ�е��ļ��д�С�ǣ�" + nf.format(fsizemb) 
                            + "MB"); 
                    System.out.println("��ѡ�е��ļ���·���ǣ�" + item.getData());                } 
            } 
        });        //����Tree�Ĵ�С����shell�Ĵ�С 
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
    * �����ļ���Ŀ¼���ļ��������С(��λΪ���ֽ�) 
    */ 
    public long getDirectorySize(File dir) { 
        long retSize = 0;//�ļ���Ŀ¼���ļ��Ĵ�С 
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
