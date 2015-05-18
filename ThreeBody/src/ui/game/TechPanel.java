package ui.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.FrameUtil;
import dto.GameDTO;

public class TechPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	public TechPanel() {
		this.setLayout(null);
		setBounds(100, 510, 140, 32);
		this.setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g) {
		Image techPanel = new ImageIcon("techPanel.png").getImage();
		g.drawImage(techPanel, 0, 0,140,32, null);
		FrameUtil.drawNumberLeftPad(60, 0, GameDTO.getInstance().getUser().getTechPoint(), 3, g);
	}
}
