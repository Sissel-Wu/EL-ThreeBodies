package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.sound.Media;
import control.MainControl;
import dto.PreferenceDTO;

public class AnimatePanel extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	File[] files;
	private int imageIndex = 0;
	private MainControl mainControl;
	
	// 双缓冲机制
	private Image iBuffer;
	private Graphics gBuffer;

	public AnimatePanel(String fileName, MainControl mainControl) {
		this.mainControl = mainControl;
		files = this.getFiles(fileName);
	}
	
	@Override
	public void update(Graphics scr)
	{
	    if(iBuffer==null)
	    {
	       iBuffer=createImage(this.getSize().width,this.getSize().height);
	       gBuffer=iBuffer.getGraphics();
	    }
	       gBuffer.setColor(getBackground());
	       gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);
	       paint(gBuffer);
	       scr.drawImage(iBuffer,0,0,this);
	}

	public void paintComponent(Graphics g) {
		Image im = new ImageIcon(files[imageIndex].getPath()).getImage();
		g.drawImage(im, 0, 0, 1158, 650, null);
	}

	private File[] getFiles(String filePath) {
		File file = new File(filePath);
		File[] files = file.listFiles();
		return files;
	}

	public void run() {
		while (imageIndex < files.length) {
			AnimatePanel.this.repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			imageIndex++;
		}
		AnimatePanel.this.setVisible(false);
		AnimatePanel.this.mainControl.toStartMenu(true);
		Media.playBGM(PreferenceDTO.getInstance().getBgm());
		Media.getBgmPlayer().setVolume((float) PreferenceDTO.getInstance().getVolume());
	}

}
