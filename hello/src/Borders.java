import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class Borders extends JPanel {
	static JPanel showBorder(Border b) {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		String nm = b.getClass().toString();
		nm = nm.substring(nm.lastIndexOf('.') + 1);
		jp.add(new JLabel(nm, JLabel.CENTER), BorderLayout.CENTER);
		jp.setBorder(b);
		return jp;
	}

	public Borders() {
		setLayout(new GridLayout(2, 4));
		add(showBorder(new TitledBorder("Title")));
		add(showBorder(new EtchedBorder()));
		add(showBorder(new LineBorder(Color.blue)));
		add(showBorder(new MatteBorder(5, 5, 30, 30, Color.green)));
		add(showBorder(new BevelBorder(BevelBorder.RAISED)));
		add(showBorder(new SoftBevelBorder(BevelBorder.LOWERED)));
		add(showBorder(new CompoundBorder(new EtchedBorder(), new LineBorder(
				Color.red))));
	}

	public static void main(String args[]) {
		// Show.inFrame(new Borders(), 500, 300);
		InFrame frame = new InFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
} //

class InFrame extends JFrame {
	public InFrame() {
		setTitle("title");
		setSize(500, 300);
		Borders border = new Borders();
		add(border);
		
	}
}