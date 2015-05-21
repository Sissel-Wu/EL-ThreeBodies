package ui.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.FrameUtil;
import dto.GameDTO;

public class TurnPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TurnPanel() {
		this.setLayout(null);
		setBounds(30, 165, 140, 32);
		this.setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Image techPanel = new ImageIcon("resourcePanel.png").getImage();
		g.drawImage(techPanel, 0, 0, 140, 32, null);
		FrameUtil.drawNumberLeftPad(60, 0, GameDTO.getInstance().getBout(), 3, g);
	}
}
