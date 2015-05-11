package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import control.MainControl;

public class SelectPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnReturn;
	private JButton btnFourPlayer;
	private JButton btnTwoPlayer;
	private JButton btnSixPlayer;

	private MainControl mainControl;

	public SelectPanel(MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.initComonent();
	}

	private void initComonent() {
//		this.btnReturn = new JButton("返回");
//		this.btnReturn.setContentAreaFilled(false);
//		this.btnReturn.setBounds(483, 480, 200, 60);
//		// this.btnMultyPlay.setBorderPainted(false);
//		btnReturn.addMouseListener(new ReturnListener());
//		this.add(btnReturn);

		this.btnTwoPlayer = new JButton("3人游戏");
		this.btnTwoPlayer.setContentAreaFilled(false);
		this.btnTwoPlayer.setBounds(233, 120, 200, 300);
		this.btnTwoPlayer.addMouseListener(new ThreePlayerListener());

		this.add(btnTwoPlayer);

		this.btnFourPlayer = new JButton("6人游戏");
		this.btnFourPlayer.setContentAreaFilled(false);
		this.btnFourPlayer.setBounds(483, 120, 200, 300);
		this.btnFourPlayer.addMouseListener(new SixPlayerListener());
		this.add(btnFourPlayer);

		this.btnSixPlayer = new JButton("8人游戏");
		this.btnSixPlayer.setContentAreaFilled(false);
		this.btnSixPlayer.setBounds(733, 120, 200, 300);
		this.btnSixPlayer.addMouseListener(new EightPlayerListener());
		this.add(btnSixPlayer);

	}

	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("img2.jpg").getImage();
		// ������Ϸ����
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
	}

	class ReturnListener implements MouseListener {
		int x = btnReturn.getX();
		int y = btnReturn.getY();

		@Override
		public void mouseClicked(MouseEvent e) {
			mainControl.toStartMenu();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			btnReturn.setLocation(x + 3, y + 3);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			btnReturn.setLocation(x - 2, y - 2);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnReturn.setLocation(x - 2, y - 2);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnReturn.setLocation(x, y);
		}
	}

	class ThreePlayerListener implements MouseListener {
	

		@Override
		public void mouseClicked(MouseEvent e) {
			mainControl.toLobby(3);
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	
	class EightPlayerListener implements MouseListener {
		

		@Override
		public void mouseClicked(MouseEvent e) {
			mainControl.toLobby(8);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	
	
class SixPlayerListener implements MouseListener {
		

		@Override
		public void mouseClicked(MouseEvent e) {
			mainControl.toLobby(6);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

}
