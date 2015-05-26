package ui;

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

import ui.login.LoginFrame;
import ui.login.LoginPanel;
import ui.sound.Media;
import ui.sound.Sound;
import util.R;
import control.MainControl;
import dto.AccountDTO;

public class StartMenuPanel extends JPanel{
	/*
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel btnStartGame;
	private JButton btnOption;
	private JButton btnAboutUs;
	private JButton btnExit;
	private JButton btnHelp;
	public JLabel labelLogIn;
	private JLabel labelWelcome;
	private MainControl mainControl;
	
	public StartMenuPanel(boolean welcome,MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.initComonent(welcome);
	}
	
	private void initComonent(boolean welcome) {
		
		this.btnStartGame = new JLabel(new ImageIcon("images/GameStart.gif"));
		this.btnStartGame.setBounds(523, 120, 260, 260);
		this.btnStartGame.addMouseListener(new StartGameListener());
		this.add(btnStartGame);

		Image option = new ImageIcon("images/Preference.png").getImage();
		option = option.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		this.btnOption = new JButton(new ImageIcon(option));
		this.btnOption.setContentAreaFilled(false);
		this.btnOption.setBounds(380, 92, 150, 150);
		this.btnOption.setBorderPainted(false);
		this.btnOption.addMouseListener(new OptionListener());
		this.add(btnOption);

		Image aboutUs = new ImageIcon("images/AboutUs.png").getImage();
		aboutUs=aboutUs.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		this.btnAboutUs = new JButton(new ImageIcon(aboutUs));
		this.btnAboutUs.setContentAreaFilled(false);
		this.btnAboutUs.setBounds(350, 340, 100, 100);
		this.btnAboutUs.setBorderPainted(false);
		this.btnAboutUs.addMouseListener(new AboutUsListener());
		this.add(btnAboutUs);

		Image exit = new ImageIcon("images/exit.png").getImage();
		exit = exit.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		this.btnExit = new JButton(new ImageIcon(exit));
		this.btnExit.setContentAreaFilled(false);
		this.btnExit.setBounds(473, 360, 150, 150);
		this.btnExit.setBorderPainted(false);
		this.btnExit.addMouseListener(new ExitListener());
		this.add(btnExit);
		
		Image help = new ImageIcon("images/Help.png").getImage();
		help=help.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		this.btnHelp = new JButton(new ImageIcon(help));
		this.btnHelp.setContentAreaFilled(false);
		this.btnHelp.setBounds(400, 236, 150, 150);
		this.btnHelp.setBorderPainted(false);
		this.btnHelp.setFocusable(false);
		this.btnHelp.addMouseListener(new HelpListener());
		this.add(btnHelp);
		
		this.labelLogIn = new JLabel(AccountDTO.getInstance().getId(),JLabel.CENTER);
		this.labelLogIn.setBounds(0, 0, 260, 45);
		this.labelLogIn.setForeground(R.color.LIGHT_YELLOW);
		this.labelLogIn.setFont(new Font("华文细黑",Font.PLAIN,20));
		this.labelLogIn.addMouseListener(new LogInListener());
		this.add(labelLogIn);
		
		if(welcome){
			this.labelLogIn.setVisible(false);
			this.btnStartGame.setVisible(false);
			this.btnAboutUs.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnHelp.setVisible(false);
			this.btnOption.setVisible(false);
			
			this.labelWelcome = new JLabel(new ImageIcon("images/DARKFOREST1.gif"));
			this.labelWelcome.setBounds(0, 0, 1158, 650);
			this.labelWelcome.setVisible(true);
			this.labelWelcome.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent arg0) {
					mainControl.toStartMenu(false);
				}
			});
			this.add(labelWelcome);
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/sky3.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
		
		Image img = new ImageIcon("images/下拉.png").getImage();
		g.drawImage(img, 0, 0, 260, 45, 0, 0, 625, 80, null);
		
		if(labelLogIn != null){
			labelLogIn.setText(AccountDTO.getInstance().getId());
		}
	}

	class StartGameListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.enter);
			if(mainControl.isConnected()){
				mainControl.toLobby();
			}else{
				FrameUtil.sendMessageByPullDown(StartMenuPanel.this, "没登录啦>_<");
			}
		}
	}
	class OptionListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.enter);
			mainControl.toPreference();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Image option = new ImageIcon("images/Preference2.png").getImage();
			option = option.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnOption.setIcon(icon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Image option = new ImageIcon("images/Preference.png").getImage();
			option = option.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnOption.setIcon(icon);
		}
		
		
	}
	class AboutUsListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.enter);
			mainControl.toAboutUs();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Image option = new ImageIcon("images/AboutUs2.png").getImage();
			option = option.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnAboutUs.setIcon(icon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Image option = new ImageIcon("images/AboutUs.png").getImage();
			option = option.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnAboutUs.setIcon(icon);
		}
		
		
	}
	class ExitListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.enter);
			mainControl.exit();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Image option = new ImageIcon("images/exit2.png").getImage();
			option = option.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnExit.setIcon(icon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Image option = new ImageIcon("images/exit.png").getImage();
			option = option.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnExit.setIcon(icon);
		}
		
	}
	
	class LogInListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.choose);
			if(mainControl.isConnecting()){
				FrameUtil.sendMessageByPullDown(StartMenuPanel.this, "自动登录中");
				return;
			}
			if(!mainControl.isConnected()){
				JFrame loginFrame = new LoginFrame();
				JPanel loginPanel = new LoginPanel(StartMenuPanel.this ,mainControl, loginFrame,mainControl.accountControl);
				loginFrame.setContentPane(loginPanel);
				repaint();
			}else{
				if(mainControl.isConnected()){
					mainControl.toAccount(AccountDTO.getInstance().getId());
				}else{
					mainControl.toAccount(AccountDTO.getInstance().getId());
				}
			}
		}
	}
	
	class HelpListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			mainControl.toTutorial();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Image option = new ImageIcon("images/Help2.png").getImage();
			option = option.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnHelp.setIcon(icon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Image option = new ImageIcon("images/Help.png").getImage();
			option = option.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(option);
			btnHelp.setIcon(icon);
		}
	}
}
