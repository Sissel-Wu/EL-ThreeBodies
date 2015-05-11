package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.login.LoginFrame;
import ui.login.LoginPanel;
import ui.sound.Media;
import ui.sound.Sound;
import control.MainControl;

public class StartMenuPanel extends JPanel{
	/*
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnStartGame;
	private JButton btnOption;
	private JButton btnAboutUs;
	private JButton btnExit;
	private JButton btnAccount;
	private JButton btnLogIn;
	
	private MainControl mainControl;
	private String accountId;
	
	public StartMenuPanel(MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.initComonent();
//		this.accountId=AccountDTO.getInstance().getId();
	}
	
	private void initComonent() {

		this.btnStartGame = new JButton(new ImageIcon("button.png"));
		this.btnStartGame.setContentAreaFilled(false);
		this.btnStartGame.setBounds(483, 120, 200, 80);
		this.btnStartGame.setBorderPainted(false);
		this.btnStartGame.addMouseListener(new StartGameListener());
		this.add(btnStartGame);

		this.btnOption = new JButton(new ImageIcon("option.png"));
		this.btnOption.setContentAreaFilled(false);
		this.btnOption.setBounds(483, 220, 200, 80);
		this.btnOption.setBorderPainted(false);
		this.btnOption.addMouseListener(new OptionListener());

		this.add(btnOption);

		this.btnAboutUs = new JButton(new ImageIcon("about.png"));
		this.btnAboutUs.setContentAreaFilled(false);
		this.btnAboutUs.setBounds(483, 320, 200, 80);
		this.btnAboutUs.setBorderPainted(false);
		this.btnAboutUs.addMouseListener(new AboutUsListener());
		this.add(btnAboutUs);

		this.btnExit = new JButton(new ImageIcon("exit.png"));
		this.btnExit.setContentAreaFilled(false);
		this.btnExit.setBounds(483, 420, 200, 80);
		this.btnExit.setBorderPainted(false);
		this.btnExit.addMouseListener(new ExitListener());
		this.add(btnExit);

		
		this.btnLogIn = new JButton(accountId);
		this.btnLogIn.setContentAreaFilled(false);
		this.btnLogIn.setBounds(0, 0, 150, 25);
		this.btnLogIn.addMouseListener(new LogInListener());
		this.add(btnLogIn);
		
		
		this.btnAccount = new JButton("账号");
		this.btnAccount.setContentAreaFilled(false);
		this.btnAccount.setBounds(0, 25, 150, 25);
		this.btnAccount.addMouseListener(new AccountListener());
	}
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("img2.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
		
	}

	class StartGameListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.enter);
			mainControl.toSelect();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	class OptionListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.enter);
			mainControl.toPreference();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	class AboutUsListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.enter);
			Media.playBGM(Sound.career);
			mainControl.toAboutUs();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	class ExitListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.goback);
			mainControl.exit();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	class AccountListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	class LogInListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.choose);
			if(accountId==null){
				JFrame loginFrame = new LoginFrame();
				JPanel loginPanel = new LoginPanel(loginFrame);
				loginFrame.setContentPane(loginPanel);
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
}
