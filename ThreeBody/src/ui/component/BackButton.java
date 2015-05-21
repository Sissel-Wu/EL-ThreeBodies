package ui.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BackButton extends JButton implements MouseListener{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private static ImageIcon before;
	private static ImageIcon after;
	
	static{
		before = new ImageIcon("images/箭头.png");
		after = new ImageIcon("images/back.png");
	}
	
	private MouseListener ml;
	
	public BackButton(MouseListener ml){
		this.setIcon(before);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setBounds(0, 542, 90, 88);
		this.ml = ml;
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ml.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBounds(0, 470, 170, 160);
		this.setIcon(after);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBounds(0, 542, 90, 88);
		this.setIcon(before);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ml.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.setBounds(0, 542, 90, 88);
		this.setIcon(before);
		ml.mouseReleased(e);
	}

}
