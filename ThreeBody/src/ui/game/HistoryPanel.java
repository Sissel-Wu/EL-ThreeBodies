package ui.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import model.Player;
import dto.GameDTO;

public class HistoryPanel extends JPanel{
	
	private JList history;
	String[] historyOperation;
	JScrollPane scroll;
	private JButton btnReturn;
	
	
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
		historyOperation=new String[10];
		for (int i = 0; i < 10; i++) {
			historyOperation[i]="aa"+i;
		}
		this.history = new JList(historyOperation);
		
		this.history.setBounds(80, 30, 560, 80);
		history.setFont(new Font("黑体", Font.BOLD, 20));
		history.setVisibleRowCount(4);
		history.setVisible(true);
		
		JScrollPane scroller = new JScrollPane(history);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(80, 30, 560, 120);
		
		this.add(scroller);
		repaint();
		
//		this.btnReturn = new JButton(new ImageIcon("exit.png"));
//		this.btnReturn.setContentAreaFilled(false);
//		this.btnReturn.setBounds(520, 95, 150, 60);
//		this.btnReturn.setBorderPainted(false);
//		btnReturn.addMouseListener(new ReturnListener());
//		this.add(btnReturn);

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
