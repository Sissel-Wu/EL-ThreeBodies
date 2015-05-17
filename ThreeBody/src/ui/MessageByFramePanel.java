package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageByFramePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel msgLabel;
	private TwoSecondCloseFrameThread tscf;
	/**
	 * 
	 * @param successInformFrame 
	 */
	public MessageByFramePanel(InformFrame successInformFrame,String message) {
		this.setLayout(null);
		this.frame=successInformFrame;
		this.initComonent(message);
		this.tscf = new TwoSecondCloseFrameThread();
		this.tscf.start();
	}
	
	private void initComonent(String message) {

		
		msgLabel = new JLabel(message,JLabel.CENTER);
		msgLabel.setForeground(Color.YELLOW);
		msgLabel.setFont(new Font("汉仪菱心体简", Font.PLAIN, 20));
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
	private class TwoSecondCloseFrameThread extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.dispose();
		}
	}
}
