package ui.component;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SquareButton extends JButton{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	String picturePath;
	ImageIcon before;
	ImageIcon after;
	MouseListener originMouseListener;
	MouseListener newMouseListner;

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
	
	public SquareButton(String picturePath,int width,int height){
		super();
		this.picturePath = picturePath;
		before = new ImageIcon(picturePath);
		String afterPath = picturePath.substring(0, picturePath.length()-4);
		if(picturePath.endsWith(".png")){
			after = new ImageIcon(afterPath + "2.png");
		}else{
			after = new ImageIcon(afterPath + "2.jpg");
		}
		
		Image bi = before.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		this.before = new ImageIcon(bi);
		Image ai = after.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		this.after = new ImageIcon(ai);
		
		this.setIcon(this.before);
	}
	
	public void addMouseListener(MouseListener ml) {
		this.originMouseListener = ml;
		this.newMouseListner = new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				originMouseListener.mouseClicked(arg0);
			}

			public void mouseEntered(MouseEvent arg0) {
				SquareButton.this.setIcon(after);
			}

			public void mouseExited(MouseEvent arg0) {
				SquareButton.this.setIcon(before);
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		};
		super.addMouseListener(newMouseListner);
	}

}
