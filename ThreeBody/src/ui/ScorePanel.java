package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ui.RoomPanel.ReturnListener;
import control.MainControl;

public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private MainControl mainControl;
	private JButton btnreturn;
	private boolean isLost;
	public ScorePanel(boolean isLost, MainControl mainControl) {
		this.isLost=isLost;
		this.mainControl=mainControl;
		init();
	}
	
	private void init() {
		this.setLayout(null);
		this.btnreturn = new JButton();
		this.btnreturn.setIcon(new ImageIcon("images/返回.png"));
		this.btnreturn.setContentAreaFilled(false);
		this.btnreturn.setBounds(469, 500, 221, 89);
		this.btnreturn.addMouseListener(new ReturnListener());
		this.add(btnreturn);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isLost) {
			Image background = new ImageIcon("images/lose.png").getImage();
			g.drawImage(background, 0, 0, null);
		}else{
			Image background = new ImageIcon("images/win.png").getImage();
			g.drawImage(background, 0, 0, null);
		}
		
	}
	
	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			mainControl.toStartMenu();
		}
	}

}
