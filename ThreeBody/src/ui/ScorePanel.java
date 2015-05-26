package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.OperationTranslator;
import control.MainControl;
import dto.GameDTO;

public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private MainControl mainControl;
	private JButton btnreturn;
	private JButton btnInformation;
	private boolean isLost;
	public ScorePanel(boolean isLost, MainControl mainControl) {
		this.isLost=isLost;
		this.mainControl=mainControl;
		init();
	}
	
	private void init() {
		this.setLayout(null);
		
		this.btnInformation = new JButton();
		this.btnInformation.setBorderPainted(false);
		this.btnInformation.setIcon(new ImageIcon("images/生成战报.png"));
		this.btnInformation.setContentAreaFilled(false);
		this.btnInformation.setBounds(610, 500, 160, 60);
		this.btnInformation.addMouseListener(new InformationListener());
		this.add(btnInformation);
		
		this.btnreturn = new JButton();
		this.btnreturn.setBorderPainted(false);
		this.btnreturn.setIcon(new ImageIcon("images/返回.png"));
		this.btnreturn.setContentAreaFilled(false);
		this.btnreturn.setBounds(390, 500, 120, 60);
		this.btnreturn.addMouseListener(new ReturnListener());
		this.add(btnreturn);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isLost) {
			Image background = new ImageIcon("images/lose.png").getImage();
			g.drawImage(background, 0, 0, null);
		}else{
			Image background = new ImageIcon("images/win.png").getImage();
			g.drawImage(background, 0, 0, null);
		}
		
	}
	
	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			mainControl.toStartMenu(false);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			btnreturn.setIcon(new ImageIcon("images/返回2.png"));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			btnreturn.setIcon(new ImageIcon("images/返回.png"));
		}
		
	}
	
	class InformationListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			FrameUtil.sendMessageByPullDown(ScorePanel.this, "战报已保存至temp.txt");
			try {
				OperationTranslator.translate(GameDTO.getInstance().getHistoryOperations(), "temp.txt");
			} catch (IOException e1) {
				FrameUtil.sendMessageByPullDown(ScorePanel.this, "战报生成失败 T_T");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnInformation.setIcon(new ImageIcon("images/生成战报2.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnInformation.setIcon(new ImageIcon("images/生成战报.png"));
		}
		
	}

}
