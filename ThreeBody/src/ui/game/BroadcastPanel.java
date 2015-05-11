package ui.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Player;
import dto.GameDTO;

public class BroadcastPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField btnCoordinateOne;
	private JTextField btnCoordinateTwo;
	private JTextField btnCoordinateThree;
	private JTextField btnCoordinateFour;
	private JButton btnOK;
	private JButton btnReturn;
	private JComboBox<String> select;
	
	List<Player> players=null;
	Player user;

	public BroadcastPanel() {
		this.setLayout(null);
		setBounds(231, 435, 695, 215);
		players=GameDTO.getInstance().getPlayers();
		user=GameDTO.getInstance().getUser();
		this.initComonent();
	}

	private void initComonent() {
		this.btnCoordinateOne = new JTextField();
		this.btnCoordinateOne.setBounds(12, 16, 159, 80);
		btnCoordinateOne.setFont(new Font("黑体", Font.BOLD, 60));
		this.add(btnCoordinateOne);
		
		this.btnCoordinateTwo = new JTextField();
		this.btnCoordinateTwo.setBounds(183, 16, 159, 80);
		btnCoordinateTwo.setFont(new Font("黑体", Font.BOLD, 60));
		this.add(btnCoordinateTwo);
		
		this.btnCoordinateThree = new JTextField();
		this.btnCoordinateThree.setBounds(353, 16, 159, 80);
		btnCoordinateThree.setFont(new Font("黑体", Font.BOLD, 60));
		this.add(btnCoordinateThree);
		
		this.btnCoordinateFour = new JTextField();
		this.btnCoordinateFour.setBounds(524, 16, 159, 80);
		btnCoordinateFour.setFont(new Font("黑体", Font.BOLD, 60));
		this.add(btnCoordinateFour);
		
		this.btnOK = new JButton(new ImageIcon("button.png"));
		this.btnOK.setContentAreaFilled(false);
		this.btnOK.setBounds(360, 105, 150, 60);
		this.btnOK.setBorderPainted(false);
//		btnOK.addMouseListener(new FindListener());
		this.add(btnOK);
		
		this.btnReturn = new JButton(new ImageIcon("exit.png"));
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(520, 105, 150, 60);
		this.btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);
		
		select = new JComboBox<String>();
		select.setFont(new Font("宋体", Font.PLAIN, 30));
		select.setBounds(100,105, 60, 30);
		//
		if (players != null) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).equals(user)) {
					continue;
				} else {
					select.addItem(players.get(i).getAccount().getId());
				}
			}
		}

		select.addItem("aa");
		select.addItem("bb");
		this.add(select);
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
