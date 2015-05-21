package ui.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.role.Earth;
import model.role.Role;
import model.role.ThreeBody;
import model.role.Unifier;
import ui.FrameUtil;
import dto.GameDTO;

public class CountDownPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameDTO gameDTO;
	
	private Image earth = new ImageIcon("images/EarthBackground.png").getImage();
	private Image threeBody = new ImageIcon("images/ThreeBodyBackground.png").getImage();
	private Image unifier = new ImageIcon("images/UnifierBackground.png").getImage();
	
	public CountDownPanel() {
		this.setLayout(null);
		setBounds(20, 20, 125,125);
		this.gameDTO = GameDTO.getInstance();
	}	
	
	@Override
	public void paint(Graphics g) {
		GameDTO dto = GameDTO.getInstance();
		Role role = dto.getUser().getRole();
		
		Image IMG_MAIN = null; 
		if(role instanceof Earth){
			IMG_MAIN = earth;
		}
		if(role instanceof ThreeBody){
			IMG_MAIN = threeBody;
		}
		if(role instanceof Unifier){
			IMG_MAIN = unifier;
		}
		g.drawImage(IMG_MAIN, 0, 0,125,125, null);
		FrameUtil.drawNumberLeftPad(18, 44, gameDTO.getCountdowns(), 3, g);
	}
}
