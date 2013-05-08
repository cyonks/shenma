package jtr;

import javax.swing.JFrame;

public class TestFile {

	public static void main(String[]args){
		JFrame frame =new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,700);
		JtreeFile panel=new JtreeFile();
		frame.add(panel);
		frame.setVisible(true);
	}
}
