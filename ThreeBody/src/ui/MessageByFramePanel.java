package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.R;

public class MessageByFramePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel msgLabel;
	private double duration;
	/**
	 * 
	 * @param successInformFrame 
	 */
	public MessageByFramePanel(InformFrame successInformFrame,String message) {
		this(successInformFrame,message,2);
	}
	
	public MessageByFramePanel(InformFrame successInformFrame,String message,double duration){
		this.setLayout(null);
		this.duration = duration;
		this.frame=successInformFrame;
		this.initComponent(message);
		new Thread(new TwoSecondCloseFrameThread()).start();
	}
	
	private void initComponent(String message) {
		msgLabel = new JLabel(message,JLabel.CENTER);
		msgLabel.setForeground(R.color.LIGHT_YELLOW);
		msgLabel.setFont(new Font("华文细黑", Font.PLAIN, 20));
		msgLabel.setBounds(0,0,300,200);
		this.add(msgLabel);
	}
	
	class OKListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			frame.dispose();
		}
	}
	
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/sky8.jpeg").getImage();
		// 绘制游戏界面
		g.drawImage(IMG_MAIN, 0, 0,300,200, null);
	}
	private class TwoSecondCloseFrameThread implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep((int)(duration * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.dispose();
		}
	}
}
