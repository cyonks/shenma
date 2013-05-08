
import java.io.File; 

public class ShowTree { 
    static void displayDir(File dir, String prefix) { 
        System.out.print(prefix); 
        System.out.println(dir.getName()); 
        
        prefix = prefix.replace("©À", "©¦"); 
        prefix = prefix.replace("©¸", " "); 
        if (dir.isFile()) { 
            return; 
        } 
        File files[] = dir.listFiles(); 
        
        for (int i = 0; files != null && i < files.length; i++) { 
            if (i == files.length -1 ) { 
                displayDir(files[i], prefix + "©¸");                
            } else { 
                displayDir(files[i], prefix + "©À"); 
            } 
        } 
    } 
    
    static void addDir(String parent, String dir) { 
        File file = new File(parent, dir); 
        if (!file.exists() || !file.isDirectory()) { 
            file.mkdir(); 
        } 
    } 
    
    static void delDir(String parent, String dir) { 
        File file = new File(parent, dir); 
        if (file.exists() && file.isDirectory()) { 
            file.delete(); 
        } 
    } 
    /** 
     * @param args 
     */ 
    public static void main(String[] args) { 
        // TODO Auto-generated method stub 
        String targetDirName = "c:\\"; 
        String operateName = ""; 
        String newDirName = null; 
        
        switch(args.length) { 
        case 1: 
            targetDirName = args[0]; 
        case 0:           displayDir(new File(targetDirName), ""); 
            break; 
        case 2: 
            if (args[0].equals("add")) { 
                addDir(targetDirName, args[1]); 
            } else if (args[0].equals("del")) { 
                delDir(targetDirName, args[1]); 
            } 
            displayDir(new File(targetDirName), ""); 
        } 
    } 

} 