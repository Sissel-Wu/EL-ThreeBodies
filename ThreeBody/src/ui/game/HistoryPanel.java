package ui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import model.Player;
import dto.GameDTO;

public class HistoryPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JList<String> history;
	JScrollPane scroll;
	private JButton btnReturn;
	private DefaultListModel<String> model;
	
	List<Player> players=null;
	Player user;

	public HistoryPanel() {
		this.setLayout(null);
		setBounds(231, 435, 695, 215);
		players=GameDTO.getInstance().getPlayers();
		user=GameDTO.getInstance().getUser();
		this.initComonent();
	}

	private void initComonent() {
		model = new DefaultListModel<String>();
		
		this.history = new JList<String>(model);
		
		this.history.setBounds(80, 30, 560, 80);
		history.setFont(new Font("宋体", Font.BOLD, 20));
		history.setVisibleRowCount(4);
		history.setVisible(true);
		history.setOpaque(false);
		
		JScrollPane scroller = new JScrollPane(history);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(80, 30, 560, 120);
		
		this.add(scroller);
		repaint();
		
		Image buttonImg = new ImageIcon("images/refresh.png").getImage();
		this.btnReturn = new JButton(new ImageIcon(buttonImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(650, 95, 40, 40);
		this.btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);

	}
	
	class ReturnListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			String[] infos = GameDTO.getInstance().getInformations();
			for (String string : infos) {
				System.out.println(string);
			}
			model.clear();
			for (int i = 0; i < infos.length; i++) {
				model.addElement(infos[i]);
			}
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
		Image IMG_MAIN = new ImageIcon("images/opaqueHalf.png").getImage();
		// 绘制游戏界面
		g.drawImage(IMG_MAIN, 0, 0,695,215, null);
	}
}
