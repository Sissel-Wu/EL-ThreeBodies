package ui.game;

import java.awt.Graphics;

import javax.swing.JPanel;

import dto.GameDTO;
import ui.FrameUtil;

public class TechPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	public TechPanel() {
		this.setLayout(null);
		setBounds(100, 510, 140, 32);
		this.setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g) {
		FrameUtil.drawNumberLeftPad(60, 0, GameDTO.getInstance().getUser().getTechPoint(), 3, g);
	}
}
