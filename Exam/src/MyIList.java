import java.util.ArrayList;


public class MyIList implements IList{
	 ArrayList list=new ArrayList();
     public MyIList(){
    	 
     }
     public void add(Object elem){
    	 list.add(elem);
     }
     public Object get(int index){
    	 return list.get(index);
     }
     public int size(){
    	 return list.size();
     }
     public void clear(){
    	 list.clear();
     }
}
