package ui.sophon;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPanel extends JPanel {

	private JFrame sophonFinder;
	private String result="";
	
	public void setResult(String result) {
		this.result = result;
	}

	public ResultPanel(JFrame sophonFinder) {
		this.setLayout(null);
		this.sophonFinder=sophonFinder;
		this.initComonent();
	}

	private void initComonent() {

		
	}
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
		paintResult(result,g);
	}

	private void paintResult(String result, Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
