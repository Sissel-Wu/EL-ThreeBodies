package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.component.BackButton;
import ui.component.SquareButton;
import ui.sound.Media;
import ui.sound.Sound;
import control.MainControl;

public class HelpPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private JButton btnNextPage;
	private JButton btnBack;
	private JLabel picture;
	private MainControl mainControl;
	private int pNum = 6;
	String[] pictureList = new String[pNum + 1];
	private int i = 0;

	public HelpPanel(MainControl mainControl) {

		// store the turns of pictureInfo

		pictureList[0] = "images/说明1.png";
		pictureList[1] = "images/说明2.png";
		pictureList[2] = "images/说明3.png";
		pictureList[3] ="images/说明4.png";
		pictureList[4] ="images/说明5.png";
		pictureList[5] ="images/说明6.png";
		
		this.setLayout(null);
		this.initComonent();
		this.mainControl = mainControl;

	}

	private void initComonent() {
		this.btnNextPage = new JButton(new ImageIcon("images/下一页.png"));
		this.btnNextPage.setBorderPainted(false);
		this.btnNextPage.setContentAreaFilled(false);
		this.btnNextPage.setBounds(930, 500, 120, 60);
		// this.btnNextPage.setBorderPainted(false);

		btnNextPage.addMouseListener(new NextPageListener());
		btnNextPage.setVisible(true);
		this.add(btnNextPage);
		
		btnBack = new BackButton(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mainControl.toStartMenu(false);
			}
		});
		this.add(btnBack);

		// show the first picture
		Icon icon = new ImageIcon(pictureList[0]);
		this.picture = new JLabel(icon);
		this.picture.setBounds(200, 0, 800, 600);
		this.picture.setVisible(true);
		this.add(picture);
	}

	public void changeImage() {
		if (i < (pNum - 1)) {
			i++;
			picture.setIcon(new ImageIcon(pictureList[i]));
			if (i == (pNum - 1)) {
				this.btnNextPage.setVisible(false);
			}
		} 
	}

	class NextPageListener implements MouseListener {
		public void mouseClicked(MouseEvent arg0) {
			Media.playSound(Sound.enter);
			changeImage();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			Media.playSound(Sound.choose);
			btnNextPage.setIcon(new ImageIcon("images/下一页2.png"));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			btnNextPage.setIcon(new ImageIcon("images/下一页.png"));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}
	}

	
	// background picture
	public void paintComponent(Graphics g) {
		Image background = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(background, 0, 0, null);
	}

}

