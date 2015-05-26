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
import ui.sound.Media;
import ui.sound.Sound;
import control.MainControl;

public class AboutUsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton btnNextPage;
	private JButton btnBack;
	private JLabel picture;
	private MainControl mainControl;
	private int pNum = 5;
	String[] pictureList = new String[pNum + 1];
	private int i = 0;

	public AboutUsPanel(MainControl mainControl) {

		// store the turns of pictureInfo

		pictureList[0] = "images/妈蛋队名.png";
		pictureList[1] = "images/妈蛋叔叔.png";
		pictureList[2] = "images/妈蛋小安子.png";
		pictureList[3] = "images/妈蛋天哥哥.png";
		pictureList[4] = "images/妈蛋猿首.png";

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

		// show the first picture
		Icon icon = new ImageIcon(pictureList[0]);
		this.picture = new JLabel(icon);
		this.picture.setBounds(200, 100, 800, 400);
		this.picture.setVisible(true);
		this.add(picture);
		
		btnBack = new BackButton(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mainControl.toStartMenu(false);
			}
		});
		this.add(btnBack);
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
			Media.playSound(Sound.enter);
			changeImage();
		}
	}

	@Override
	// background picture
	public void paintComponent(Graphics g) {
		Image background = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(background, 0, 0, null);
	}

}
