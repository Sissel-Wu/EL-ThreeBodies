package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.MainControl;

public class AnimatePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	File[] files;
	private int imageIndex;
	private MainControl mainControl;
	
	public AnimatePanel(String fileName, MainControl mainControl){
		this.mainControl=mainControl;
		files = this.getFiles(fileName);
//		System.out.println("fileName="+fileName);
//		System.out.println("files="+(files == null));
	}
	
	
	public void paintComponent(Graphics g) {
		Image im = new ImageIcon(files[imageIndex].getPath()).getImage();
		g.drawImage(im, 0, 0,1158,650,null);
	}

	private File[] getFiles(String filePath) {
		
		File file = new File(filePath);
		File[] files = file.listFiles();
		return files;
		
	}

	public void run() {
		
		while(imageIndex < files.length) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			imageIndex++;
			this.repaint();
			if(imageIndex == files.length-1) {
				//TODO
				this.setVisible(false);
				mainControl.toStartMenu();
			}
			
		}
		
	} 
}
