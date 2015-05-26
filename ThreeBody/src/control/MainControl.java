package control;

import io.NetClient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.AboutUsPanel;
import ui.AnimatePanel;
import ui.FrameUtil;
import ui.HelpPanel;
import ui.MainFrame;
import ui.PreferencePanel;
import ui.RoomPanel;
import ui.ScorePanel;
import ui.StartMenuPanel;
import ui.account.AccountPanel;
import ui.game.GamePanel;
import ui.lobby.LobbyPanel;
import ui.sound.Media;
import ui.sound.Sound;
import util.R;
import dto.AccountDTO;
import dto.PreferenceDTO;

public class MainControl {
	
	private static MainControl instance;

	public JPanel currentPanel = null;
	public JFrame frame = null;
	private JPanel startMenuPanel = null;
	private JPanel gamePanel = null;
	private JPanel aboutUs = null;
	public LobbyPanel lobbyPanel = null;
	private JPanel account=null;
	private JPanel preference=null;
	public RoomPanel roomPanel=null;
	private AnimatePanel animate=null;
	private JPanel score=null;
	
	public AccountControl accountControl;
	public LobbyControl lobbyControl;
	public RoomControl roomControl;
	public GameControl gameControl;
	
	private volatile boolean connected = false;
	private volatile boolean connecting = false;
	
	public static void main(String[] args) {

		final MainControl mc = new MainControl();
		
		instance = mc;
		
		// 初始化用户配置
		PreferenceDTO.init();
		
		// 初始化账号控制
		mc.accountControl = new AccountControl(mc);
		
		// 开个线程联网，节约后面时间
		new Thread(new Runnable(){
			public void run(){
				try {
					// 下面代码的顺序很重要
					String id = AccountDTO.getInstance().getId();
					if(!id.equals("未登录") && PreferenceDTO.getInstance().isAutoLogin()){
						mc.setConnecting(true);
						if (mc.accountControl.loginByTransientID(id) == R.info.SUCCESS) {
							mc.connected = true;
							mc.connecting = false;
						}
					}
					NetClient.getInstance();
				} catch (MalformedURLException | RemoteException
						| NotBoundException e) {
					FrameUtil.sendMessageByPullDown(mc.currentPanel, "网络连接出错");
				}
			}
		}).start();
		
		mc.frame = new MainFrame(mc);
		
		if(PreferenceDTO.getInstance().isEffectOn()){
			mc.toAnimate("opening");
		}else{
			Media.playBGM(PreferenceDTO.getInstance().getBgm());
			Media.getBgmPlayer().setVolume((float) PreferenceDTO.getInstance().getVolume());
			mc.toStartMenu(true);
		}

		instance = mc;
	}
	
	public static MainControl getInstance(){
		return instance;
	}

	public void toStartMenu(boolean welcome) {
		this.startMenuPanel = new StartMenuPanel(welcome,this);
		if(currentPanel!=null&&currentPanel==lobbyPanel){
			currentPanel.setVisible(false);	
		}
		currentPanel = this.startMenuPanel;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}

	public void toAnimate(String fileName) {
		if(currentPanel != null){
			currentPanel.setVisible(false);
		}
		this.animate = new AnimatePanel(fileName,this);
		currentPanel = this.animate;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
		
//		animate.run();
		new Thread(animate).start();
	}

	public void toPreference() {
		currentPanel.setVisible(false);
		if (this.preference == null) {
			this.preference = new PreferencePanel(this);
		}
		currentPanel = this.preference;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}

	public void toTutorial() {
		currentPanel.setVisible(false);
		HelpPanel hp = new HelpPanel(this);
		currentPanel = hp;
		frame.setContentPane(hp);
		currentPanel.setVisible(true);
		frame.validate();
	}

	public void toGame(int numOfPlayers) {
		Media.stopBGM(Sound.BGM);
		
		// new GameControl
		gameControl = roomControl.getGameService();
		// 绘制gamePanel
		currentPanel.setVisible(false);
		this.gamePanel = new GamePanel(this,gameControl);
		currentPanel = this.gamePanel;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
		// 设定panel，开始游戏
		gameControl.setPanel((GamePanel)gamePanel);
		gameControl.turnChange();
		
		Sound.load("Cornfield Chase");
		Media.playBGM(Sound.BGM);
	}

	public void toLobby() {
		// new LobbyControl
		if (lobbyControl == null){
			lobbyControl = new LobbyControl(this);
		}
		currentPanel.setVisible(false);
		this.lobbyPanel = new LobbyPanel(this);
		currentPanel = this.lobbyPanel;
		lobbyControl.setLobbyPanel((LobbyPanel)this.lobbyPanel);
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		lobbyControl.startRefresh();
	}

	public void toRoom(String roomName) {
		if(roomControl == null){
			roomControl = lobbyControl.getRoomService(roomName);
		}
		currentPanel.setVisible(false);
		roomPanel = new RoomPanel(this,roomControl);
		roomControl.setRoomPanel((RoomPanel)roomPanel);
		currentPanel = this.roomPanel;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}

	public void toAboutUs() {
		currentPanel.setVisible(false);
		this.aboutUs = new AboutUsPanel(this);
		currentPanel = this.aboutUs;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}
	
	public void toAccount(String id) {
		currentPanel.setVisible(false);
		this.account = new AccountPanel(this,id,this.accountControl);
		currentPanel = this.account;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}

	public void exit() {
		if(gameControl != null){
			gameControl.exitGame(false);
		}
		if(roomControl != null){
			roomControl.exit();
		}
		if(connected){
			accountControl.logout();
		}
		System.exit(0);
	}
	
	public void toScore(boolean isLost) {
		currentPanel.setVisible(false);
		this.score = new ScorePanel(isLost,this);
		currentPanel = this.score;
		frame.setContentPane(currentPanel);
		currentPanel.setVisible(true);
		frame.validate();
	}
	
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isConnecting() {
		return connecting;
	}

	public void setConnecting(boolean connecting) {
		this.connecting = connecting;
	}

}
