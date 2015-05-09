package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Player;
import dto.GameDTO;

public class HistoryPanel extends JPanel{
	
	private JTextArea hitory;
	JScrollPane scroll;
	private JButton btnReturn;
	
	
	ArrayList<Player> players=null;
	Player user;

	public HistoryPanel() {
		this.setLayout(null);
		setBounds(231, 435, 695, 215);
		players=(ArrayList<Player>) GameDTO.getInstance().getPlayers();
		user=GameDTO.getInstance().getUser();
		this.initComonent();
	}

	private void initComonent() {
		this.hitory = new JTextArea("156484\n dfsdf\n56458\n");
		
		this.hitory.setBounds(80, 0, 560, 90);
		hitory.setFont(new Font("黑体", Font.BOLD, 20));
		this.add(hitory);
//		JScrollPane scroll = new JScrollPane(hitory); 
//		scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scroll.getViewport().add(hitory);
//		this.add(scroll);
		
		this.btnReturn = new JButton(new ImageIcon("exit.png"));
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(520, 95, 150, 60);
		this.btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);

	}
	
	class ReturnListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			setVisible(false);
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("img1.jpg").getImage();
		// 绘制游戏界面
		g.drawImage(IMG_MAIN, 0, 0,695,215, null);
	}
}
