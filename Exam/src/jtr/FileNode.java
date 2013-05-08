package jtr;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

class FileNode {

    protected File fileName;
    //JFileChooser chooser=new JFileChooser();

    public FileNode(File file) {
        fileName = file;
    }

    public File getFile() {
        return fileName;
    }

    @Override
    public String toString() {
        

		//return chooser.getFileSystemView().getSystemDisplayName(fileName);
		return fileName.getName().length() > 0 ? fileName.getName() :fileName.getPath();
    }

    public boolean expand(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent.getFirstChild();
        if (flag == null) // No flag
        {
            return false;
        }
        Object obj = flag.getUserObject();
        parent.removeAllChildren(); // Remove Flag

        File[] files = listFiles();
        if (files == null) {
            return true;
        }

        // 灏嗚妭鐐圭殑鍏ㄩ儴瀛愯妭鐐硅杩涙潵
        ArrayList v = new ArrayList();
        for (int k = 0; k < files.length; k++) {
            File f = files[k];
            FileNode newNode = new FileNode(f);
            boolean isAdded = false;
            // 瀵瑰瓙鏂囦欢澶硅繘琛屾帓搴�        
            for (int i = 0; i < v.size(); i++) {
                FileNode nd = (FileNode) v.get(i);
                if (newNode.compareTo(nd) < 0) {
                    v.add(i,newNode);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                v.add(newNode);
            }
        }

        // 娣诲姞鍏ㄩ儴瀛愭枃浠跺す
        for (int i = 0; i < v.size(); i++) {
            FileNode nd = (FileNode) v.get(i);
              DefaultMutableTreeNode node = new DefaultMutableTreeNode(nd);
            parent.add(node);
            if (nd.hasSubDirs()) {
                node.add(new DefaultMutableTreeNode(true));
            }
        }

        return true;
    }

    public boolean hasSubDirs() {
        File[] files = listFiles();
        if (files == null) {
            return false;
        }
        for (int k = 0; k < files.length; k++) {
            if (files[k].isDirectory()) {
                return true;
            }
        }
        return true;
    }

    public int compareTo(FileNode toCompare) {
        return fileName.getName().compareToIgnoreCase(toCompare.fileName.getName());
    }

    protected File[] listFiles() {
        if (!fileName.isDirectory()) {
            return null;
        }
        try {
            return fileName.listFiles();
        } catch (Exception e) {
            return null;
        }
    }
}