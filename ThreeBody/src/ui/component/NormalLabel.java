package ui.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class NormalLabel extends JLabel{

	public NormalLabel(String path,int x,int y,int w, int h) {
		super();
		this.setLayout(null);
		this.setBounds(x,y,w,h);
		this.setIcon(new ImageIcon(path));
	}

}
