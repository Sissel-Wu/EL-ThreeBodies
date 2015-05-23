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
	private JButton btnLogIn;
	public JLabel labelLogIn;
	private MainControl mainControl;
	public String accountId;
	private Image opaque  = new ImageIcon("coNothing.png").getImage();
	
	public StartMenuPanel(MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.accountId= AccountDTO.getInstance().getId();
		this.initComonent();
	}
	
	private void initComonent() {

		this.btnStartGame = new JLabel(new ImageIcon("images/GameStart7.gif"));
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
		this.btnHelp.addMouseListener(new HelpListener());
		this.add(btnHelp);
		
//		this.btnLogIn = new JButton();
//		this.btnLogIn.setIcon(new ImageIcon("images/下拉.png"));
//		this.btnLogIn.setBorderPainted(false);
//		this.btnLogIn.setContentAreaFilled(false);
//		this.btnLogIn.setBounds(-365, -35, 625, 80);
//		this.btnLogIn.addMouseListener(new LogInListener());
//		this.add(btnLogIn);
		
		this.labelLogIn = new JLabel(accountId,JLabel.CENTER);
		this.labelLogIn.setBounds(0, 0, 260, 45);
		this.labelLogIn.setForeground(R.color.LIGHT_YELLOW);
		this.labelLogIn.setFont(new Font("华文细黑",Font.PLAIN,20));
		this.labelLogIn.setVisible(true);
		this.labelLogIn.addMouseListener(new LogInListener());
		this.add(labelLogIn);
	}
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/sky3.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
		
		Image img = new ImageIcon("images/下拉.png").getImage();
		g.drawImage(img, 0, 0, 260, 45, 0, 0, 625, 80, null);
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
	}
	class AboutUsListener extends MouseAdapter {

		
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.enter);
			Media.playBGM(Sound.career);
			mainControl.toAboutUs();
		}
	}
	class ExitListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.goback);
			mainControl.exit();
		}
	}
	
	class LogInListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			Media.playSound(Sound.choose);
			if(AccountDTO.getInstance().getId() == "未登录"){
				JFrame loginFrame = new LoginFrame();
				JPanel loginPanel = new LoginPanel(mainControl, loginFrame,mainControl.accountControl);
				loginFrame.setContentPane(loginPanel);
				repaint();
			}else{
				if(mainControl.isConnected()){
					mainControl.toAccount(AccountDTO.getInstance().getId());
					System.out.println("Account界面，目前账号为："+AccountDTO.getInstance().getId()+" 已连接");
				}else{
					mainControl.toAccount(AccountDTO.getInstance().getId());
					System.out.println("Account界面，目前账号为："+AccountDTO.getInstance().getId()+" 未连接");
				}
			}
		}
	}
	
	class HelpListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
