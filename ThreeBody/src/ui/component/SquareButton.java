package ui.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SquareButton extends JButton implements MouseListener{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	String picturePath;
	ImageIcon before;
	ImageIcon after;
	MouseListener ml;

	public SquareButton(String picturePath) {
		super();
		this.picturePath = picturePath;
		before = new ImageIcon(picturePath);
		String afterPath = picturePath.substring(0, picturePath.length()-4);
		if(picturePath.endsWith(".png")){
			after = new ImageIcon(afterPath + "2.png");
		}else{
			after = new ImageIcon(afterPath + "2.jpg");
		}
		
		// 初始状态
		this.setIcon(before);
	}
	
	public void addMouseListener(MouseListener ml) {
		this.ml = ml;
		super.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		ml.mouseClicked(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setIcon(after);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(before);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		ml.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		ml.mouseReleased(arg0);
	}
	
}
