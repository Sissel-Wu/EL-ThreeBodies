package ui.component;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.R;

public class PullDownPanel extends JPanel{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel lastBackground;
	private static JLabel lastLabel;
	
	private static final ImageIcon background = new ImageIcon("images/下拉.png");
	private double duration;
	private JPanel father;
	private JLabel msgLabel;
	
	public PullDownPanel(JPanel jp,String message,double duration){
		father = jp;
		this.duration = duration;
		
		this.setLayout(null);
		this.setBounds(266, -35, 625, 80);
		
		// 先去除之前残留的还没消去的
		for(Component cp : father.getComponents()){
			if(cp == lastBackground || cp == lastLabel){
				father.remove(cp);
			}
		}
		
		msgLabel = new JLabel();
		msgLabel.setForeground(R.color.LIGHT_YELLOW);
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		msgLabel.setFont(new Font("华文细黑", Font.PLAIN, 20));
		msgLabel.setBounds(0,0,1158,45);
		msgLabel.setText(message);
		father.add(msgLabel);
		lastLabel = msgLabel;
		
		father.add(this);
		lastBackground = this;
		father.repaint();
		
		new Thread(new AutoCloseThread()).start();
	}

	@Override
	public void paint(Graphics g) {
		Image img = background.getImage();
		g.drawImage(img, 0, 0, null);
	}
	
	private class AutoCloseThread implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep((int)(duration * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			PullDownPanel.this.setVisible(false);
			msgLabel.setVisible(false);
			father.remove(msgLabel);
			father.remove(PullDownPanel.this);
		}
	}
}
