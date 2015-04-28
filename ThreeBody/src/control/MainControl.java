package control;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.MainFrame;
import ui.StartMenuPanel;
import ui.sound.Media;
import ui.sound.Sound;

/*
 * 负责模块间的转跳
 */
public class MainControl {

    JPanel currentPanel;
    JFrame frame;
    JPanel startMainPanel;
    
    public void toStartMenu() {

    }

    public void toAnimate() {

    }

    public void toConfig() {

    }

    public void toTutorial() {

    }

    public void toGame() {

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
    	mc.startMainPanel = new StartMenuPanel(mc);
    	mc.frame = new MainFrame();
    	mc.frame.setContentPane(mc.startMainPanel);
    	Sound.load("BGM1");
    	Media.playBGM(Sound.BGM);
	}
}
