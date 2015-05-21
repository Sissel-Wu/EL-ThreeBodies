package ui.lobby;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Room;
import util.R;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel numStrLabel;
	private JLabel numLabel;
	private JLabel sizeStrLabel;
	private JLabel sizeLabel;
	private Room room;
	public ButtonPanel(Room room) {
		this.room=room;
		this.setLayout(null);
		this.initComonent();
	}
	private void initComonent() {
		idLabel = new JLabel(new ImageIcon("images/roomname.png"));
		idLabel.setBounds(30,5,60,30);
		idLabel.setIcon(new ImageIcon("images/roomname.png"));
		this.add(idLabel);
		
		nameLabel = new JLabel(room.getName());
		nameLabel.setForeground(R.color.LIGHT_YELLOW);
		nameLabel.setBounds(185,5,175,30);
		this.add(nameLabel);
		
		sizeStrLabel = new JLabel();
		sizeStrLabel.setBounds(20,45,80,30);
		sizeStrLabel.setIcon(new ImageIcon("images/roomsize.png"));
		this.add(sizeStrLabel);
		
		sizeLabel = new JLabel(room.getSize()+"人");
		sizeLabel.setForeground(R.color.LIGHT_YELLOW);
		sizeLabel.setBounds(185,45,175,30);
		this.add(sizeLabel);
		
		numStrLabel = new JLabel();
		numStrLabel.setBounds(20,85,80,30);
		numStrLabel.setIcon(new ImageIcon("images/roomnumber.png"));
		this.add(numStrLabel);
		
		numLabel = new JLabel(room.getAccounts().size()+"人");
		numLabel.setForeground(R.color.LIGHT_YELLOW);
		numLabel.setBounds(185,85,175,30);
		this.add(numLabel);
		
	}

	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("images/img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
	}

}
