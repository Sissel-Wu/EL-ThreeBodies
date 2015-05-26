package ui.game;

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

import ui.InformFrame;
import util.R;
import control.GameControl;
import control.MainControl;

public class BackWarningPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	private JButton btnOk;
	private JButton btnCancel;
	
	private JFrame frame;
	private MainControl mc;
	private GameControl gc;
	
	public BackWarningPanel(JFrame frame, MainControl mc, GameControl gc) {
		super();
		this.frame = frame;
		this.mc = mc;
		this.gc = gc;
		this.setLayout(null);
		this.initComponent();
	}
	
	public static void main(String[] args) {
		JFrame frame = new InformFrame("强退警告", 450, 300);
		JPanel bwp = new BackWarningPanel(frame, null, null);
		frame.add(bwp);
		bwp.setVisible(true);
	}

	private void initComponent(){
		this.label = new JLabel("强退会扣100分，且对别的玩家不礼貌，继续？",JLabel.CENTER);
		this.label.setBounds(0, 75, 450, 50);
		this.label.setForeground(R.color.LIGHT_YELLOW);
		this.label.setFont(new Font("宋体",Font.PLAIN,20));
		this.add(label);
		
		this.btnCancel = new JButton(new ImageIcon("images/littlecancel.png"));
		this.btnCancel.setBounds(245, 180, 60, 30);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.addMouseListener(new CancelListener());
		this.add(btnCancel);
		
		this.btnOk = new JButton(new ImageIcon("images/btnOk.png"));
		this.btnOk.setContentAreaFilled(false);
		this.btnOk.setBorderPainted(false);
		this.btnOk.setBounds(145, 180, 60,30);
		this.btnOk.addMouseListener(new OkListener());
		this.add(btnOk);
	}
	
	class CancelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			frame.setVisible(false);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCancel.setIcon(new ImageIcon("images/littlecancel2.png"));
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCancel.setIcon(new ImageIcon("images/littlecancel.png"));
		}
	}
	
	class OkListener  extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			gc.rush();
			mc.toStartMenu(false);
			frame.setVisible(false);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnOk.setIcon(new ImageIcon("images/btnOk2.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {			
			btnOk.setIcon(new ImageIcon("images/btnOk.png"));
		}
	}
	
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("images/img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
	}

}
