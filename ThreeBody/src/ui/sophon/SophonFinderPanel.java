package ui.sophon;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Coordinate;
import model.Player;
import model.card.SillySophon;
import model.card.Sophon;
import model.operation.CardUse;
import ui.component.SquareButton;
import util.SillySophonHelper;
import control.GameControl;
import dto.GameDTO;

public class SophonFinderPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton btnCoordinateOne;
	private JButton btnCoordinateTwo;
	private JButton btnCoordinateThree;
	private JButton btnCoordinateFour;
	private JButton btnOK;
	private JButton btnReturn;
	private JFrame sophonFinderFrame;
	private JComboBox<String> select;
	private String result;
	private JPanel sillyResultPanel;
	private ResultPanel resultPanel;
	
	private int position;
	List<Player> players=null;
	Player user;
	private GameControl gameControl;
	
	public SophonFinderPanel(JFrame sophonFinder,GameControl gameControl) {
		this.gameControl = gameControl;
		this.setLayout(null);
		players= GameDTO.getInstance().getPlayers();
		user=GameDTO.getInstance().getUser();
		this.sophonFinderFrame=sophonFinder;
		resultPanel = new ResultPanel(sophonFinder);
		this.initComonent();
	}
	
	private void initComonent() {
		
		position=0;
		
		this.btnCoordinateOne = new JButton();
		this.btnCoordinateOne.setBounds(11, 16, 99, 60);
		btnCoordinateOne.setFont(new Font("宋体", Font.BOLD, 60));
		btnCoordinateOne.setContentAreaFilled(false);
		btnCoordinateOne.addMouseListener(new CoordinateOneListener());
		
		this.btnCoordinateTwo = new JButton();
		this.btnCoordinateTwo.setBounds(120, 16, 99, 60);
		btnCoordinateTwo.setFont(new Font("宋体", Font.BOLD, 60));
		btnCoordinateTwo.setContentAreaFilled(false);
		btnCoordinateTwo.addMouseListener(new CoordinateTwoListener());
		
		this.btnCoordinateThree = new JButton();
		this.btnCoordinateThree.setBounds(229, 16, 99, 60);
		btnCoordinateThree.setFont(new Font("宋体", Font.BOLD, 60));
		btnCoordinateThree.setContentAreaFilled(false);
		btnCoordinateThree.addMouseListener(new CoordinateThreeListener());
		
		this.btnCoordinateFour = new JButton();
		this.btnCoordinateFour.setBounds(339, 16, 99, 60);
		btnCoordinateFour.setFont(new Font("宋体", Font.BOLD, 60));
		btnCoordinateFour.setContentAreaFilled(false);
		btnCoordinateFour.addMouseListener(new CoordinateFourListener());
		
		this.add(btnCoordinateOne);
		this.add(btnCoordinateTwo);
		this.add(btnCoordinateThree);
		this.add(btnCoordinateFour);
		this.setPicture(position);
		
		this.btnOK = new SquareButton("images/find.png");
		this.btnOK.setContentAreaFilled(false);
		this.btnOK.setBounds(250, 125, 60, 30);
		btnOK.setBorderPainted(false);
		btnOK.addMouseListener(new FindListener());
		this.add(btnOK);
		
		this.btnReturn = new SquareButton("images/littlecancel.png");
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(335, 125, 60, 30);
		btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);
		
		select = new JComboBox<String>();
		select.setFont(new Font("宋体", Font.PLAIN, 30));
		select.setBounds(50,105, 60, 30);
		//
		if (players != null) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).equals(user)) {
					continue;
				} else {
					select.addItem(players.get(i).getAccount().getId());
				}
			}
		}else{
			select.addItem("aa");
			select.addItem("bb");
		}
		this.add(select);
	}
	
	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			sophonFinderFrame.setVisible(false);
		}
	}
	class FindListener extends MouseAdapter {
		
		boolean jumpOrNot = true;
		
		private void useSophon() {
			String id = user.getAccount().getId();
			GameDTO dto = GameDTO.getInstance();
			String receiverName = select.getSelectedItem().toString();
			if(sophonFinderFrame.getTitle().equals("智子")){
				
				Sophon sophon  =new Sophon(id, receiverName, position);
				CardUse cardSophon = new CardUse(user.getAccount().getId(), select.getSelectedItem().toString(), sophon);
				gameControl.doOperation(cardSophon);
				
			}else if(sophonFinderFrame.getTitle().equals("人造智子")){
				Player receiver = dto.findPlayerByID(receiverName);
				int value = receiver.getCoordinate().probeCoordinateElement(position);
				if(value == Coordinate.PROTECTED){
					jumpOrNot = false;
					// 获取失败
					SillySophon ss = new SillySophon(id, receiverName, position, null, -1);
					CardUse cardUse = new CardUse(id, receiverName, ss);
					gameControl.doOperation(cardUse);
				}else{
					sillyResultPanel = new SillyResultPanel(sophonFinderFrame, 
							SillySophonHelper.generatePossibleList(value),
							receiverName,
							position);
				}
			}
		}
		private void jumpPanel() {
			if(sophonFinderFrame.getTitle().equals("智子")){
				sophonFinderFrame.setVisible(false);
				sophonFinderFrame.setContentPane(resultPanel);
				resultPanel.setVisible(true);	
				sophonFinderFrame.validate();
			}else if(sophonFinderFrame.getTitle().equals("人造智子")){
				setVisible(false);
				sophonFinderFrame.setContentPane(sillyResultPanel);
				sillyResultPanel.setVisible(true);	
				sophonFinderFrame.validate();
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			useSophon();
			if(jumpOrNot == true){
				jumpPanel();
			}
		}
	}
	class CoordinateOneListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			position=0;
			setPicture(position);
			repaint();
		}
	}
	class CoordinateTwoListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			position=1;
			setPicture(position);
			repaint();
		}
	}
	class CoordinateThreeListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			position=2;
			setPicture(position);
			repaint();
		}
	}
	class CoordinateFourListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			position=3;
			setPicture(position);
			repaint();
		}
	}
	public void setPicture(int cooperate) {
		btnCoordinateOne.setIcon(new ImageIcon("images/coNothing.png"));
		btnCoordinateTwo.setIcon(new ImageIcon("images/coNothing.png"));
		btnCoordinateThree.setIcon(new ImageIcon("images/coNothing.png"));
		btnCoordinateFour.setIcon(new ImageIcon("images/coNothing.png"));
		if(cooperate==0){
			btnCoordinateOne.setIcon(new ImageIcon("images/select.png"));
		}
		if(cooperate==1){
			btnCoordinateTwo.setIcon(new ImageIcon("images/select.png"));
		}
		if(cooperate==2){
			btnCoordinateThree.setIcon(new ImageIcon("images/select.png"));
		}
		if(cooperate==3){
			btnCoordinateFour.setIcon(new ImageIcon("images/select.png"));
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("images/img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
//		FrameUtil.drawCoordinate(123,156,502,666,g);
	}

}
