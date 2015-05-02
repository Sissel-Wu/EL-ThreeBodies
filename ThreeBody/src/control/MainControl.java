package control;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.AboutUsPanel;
import ui.AnimatePanel;
import ui.BroadcastPanel;
import ui.GamePanel;
import ui.LobbyPanel;
import ui.MainFrame;
import ui.PreferencePanel;
import ui.RoomPanel;
import ui.SelectPanel;
import ui.StartMenuPanel;
import ui.TutorialPanel;
import ui.sound.Media;
import ui.sound.Sound;

public class MainControl {

    private JPanel currentPanel = null;
    private JFrame frame = null;
    private JPanel startMenuPanel = null;
    private JPanel selectPanel = null;
    private JPanel gamePanel = null;
    private JPanel isOnlinePanel = null;
    private JPanel broadcast = null;
    
    /*
     * TESTED
     */
    public void toStartMenu() {
    	currentPanel.setVisible(false);
    	if(this.startMenuPanel == null){
    		this.startMenuPanel = new StartMenuPanel(this);
    	}
		currentPanel = this.startMenuPanel;
		frame.setContentPane(currentPanel);
    	currentPanel.setVisible(true);	
    	frame.validate();
    }

    public void toAnimate() {
    }

    public void toPreference() {

    }

    public void toTutorial() {
    }
    
    /*
     * TESTED
     */
    public void toSelect(){
    	currentPanel.setVisible(false);
    	if(this.selectPanel == null){
    		this.selectPanel = new SelectPanel(this);
    	}
		currentPanel = this.selectPanel;
		frame.setContentPane(currentPanel);
    	currentPanel.setVisible(true);	
    	frame.validate();
    }

    /*
     * TESTED
     */
    public void toGame() {
    	currentPanel.setVisible(false);
    	if(this.gamePanel == null){
    		this.gamePanel = new GamePanel(this);
    	}
		currentPanel = this.gamePanel;
		frame.setContentPane(currentPanel);
    	currentPanel.setVisible(true);	
    	frame.validate();
    }

    public void toLobby() {
    }

    public void toRoom() {
    }

    public void toAboutUs() {
    }

    public void exit() {
    	System.exit(0);
    }
    public static void main(String[] args) {
    	
    	MainControl mc = new MainControl();
    	//TODO 换个地方放
    	mc.broadcast = new BroadcastPanel(mc);
    	mc.broadcast.setBounds(0, 0, 400, 200);
    	
    	mc.frame = new MainFrame();
    	mc.startMenuPanel = new StartMenuPanel(mc);
    	mc.currentPanel = mc.startMenuPanel;
    	mc.toStartMenu();
    	
    	Sound.load("BGM1");
    	Media.playBGM(Sound.BGM);
	}

    //TODO 换个地方放
	public void openBroadcast() {
    	this.broadcast.setVisible(true);
    	this.frame.add(this.broadcast);
	}

}
