package ui.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Coordinate;
import model.Player;
import model.operation.Broadcast;
import model.operation.Conquer;
import model.operation.Operation;
import model.role.Role;
import ui.FrameUtil;
import ui.component.SquareButton;
import ui.component.YellowTransparentTextField;
import control.GameControl;
import dto.AccountDTO;
import dto.GameDTO;

public class BroadcastPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField btnCoordinateOne;
	private JTextField btnCoordinateTwo;
	private JTextField btnCoordinateThree;
	private JTextField btnCoordinateFour;
	private JButton btnOK;
	private JButton btnReturn;
	private JButton btnConquer;
//	private JComboBox select;
	List<Player> players=null;
	Player user;
	
	private GamePanel gp;

	public BroadcastPanel(GamePanel gp) {
		this.gp = gp;
		this.setLayout(null);
		setBounds(231, 435, 695, 215);
		players = GameDTO.getInstance().getPlayers();
		user = GameDTO.getInstance().getUser();
		this.initComonent();
	}
    //926,650
	private void initComonent() {
		this.btnCoordinateOne = new YellowTransparentTextField(1);
		this.btnCoordinateOne.setHorizontalAlignment(JLabel.CENTER);
		this.btnCoordinateOne.setBounds(12, 16, 159, 80);
		btnCoordinateOne.setFont(new Font("宋体", Font.BOLD, 60));
		this.add(btnCoordinateOne);
		
		this.btnCoordinateTwo = new YellowTransparentTextField(1);
		this.btnCoordinateTwo.setHorizontalAlignment(JLabel.CENTER);
		this.btnCoordinateTwo.setBounds(183, 16, 159, 80);
		btnCoordinateTwo.setFont(new Font("宋体", Font.BOLD, 60));
		this.add(btnCoordinateTwo);
		
		this.btnCoordinateThree = new YellowTransparentTextField(1);
		this.btnCoordinateThree.setHorizontalAlignment(JLabel.CENTER);
		this.btnCoordinateThree.setBounds(353, 16, 159, 80);
		btnCoordinateThree.setFont(new Font("宋体", Font.BOLD, 60));
		this.add(btnCoordinateThree);
		
		this.btnCoordinateFour = new YellowTransparentTextField(1);
		this.btnCoordinateFour.setHorizontalAlignment(JLabel.CENTER);
		this.btnCoordinateFour.setBounds(524, 16, 159, 80);
		btnCoordinateFour.setFont(new Font("宋体", Font.BOLD, 60));
		this.add(btnCoordinateFour);
		
		this.btnOK = new SquareButton("images/btnbroadcast.png");
		this.btnOK.setContentAreaFilled(false);
		this.btnOK.setBounds(380, 95, 120, 60);
		this.btnOK.setBorderPainted(false);
		btnOK.addMouseListener(new BroadcastListener());
		this.add(btnOK);
		
		this.btnReturn = new SquareButton("images/btnbroadcastcancel.png");
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(520, 95, 120, 60);
		this.btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);
		
		this.btnConquer = new SquareButton("images/conquer.png");
		this.btnConquer.setContentAreaFilled(false);
		this.btnConquer.setBounds(240, 95, 120, 60);
		this.btnConquer.setBorderPainted(false);
		btnConquer.addMouseListener(new ConquerListener());
		Role roleName = GameDTO.getInstance().getUser().getRole();
		if(roleName.toString().equals("三体")){
			this.add(btnConquer);
		}
		
//		select = new JComboBox<String>();
//		select.setFont(new Font("宋体", Font.PLAIN, 30));
//		select.setBounds(100,105, 60, 30);
//		//
//		for (int i = 0; i < players.size(); i++) {
//			if (players.get(i).equals(user)) {
//				continue;
//			} else {
//				select.addItem(players.get(i).getAccount().getId());
//			}
//		}
//		this.add(select);
	}
	
	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			setVisible(false);
		}
	}
	
	class BroadcastListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				if(!(GameDTO.getInstance().getWhoseTurn()==user)){
					FrameUtil.sendMessageByPullDown(gp, "不是您的回合");
					return;
				}
				if(!user.isBroadcastable()){
					FrameUtil.sendMessageByPullDown(gp,  "不能使用广播");
					return;
				}
				int[] sequence = new int[4];
				sequence[0] = Integer.parseInt(btnCoordinateOne.getText());
				sequence[1] = Integer.parseInt(btnCoordinateTwo.getText());
				sequence[2] = Integer.parseInt(btnCoordinateThree.getText());
				sequence[3] = Integer.parseInt(btnCoordinateFour.getText());
				Coordinate coordinate = new Coordinate(sequence);
				String id = AccountDTO.getInstance().getId();
				Operation broadcast = new Broadcast(id,null,coordinate);
				GameControl.getInstance().doOperation(broadcast);
			} catch (Exception exception) {
				FrameUtil.sendMessageByPullDown(gp, "坐标输入错误");
			}
		}
	}
	
	class ConquerListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				if(!(GameDTO.getInstance().getWhoseTurn()==user)){
					FrameUtil.sendMessageByPullDown(gp, "不是您的回合");
					return;
				}
				int[] sequence = new int[4];
				sequence[0] = Integer.parseInt(btnCoordinateOne.getText());
				sequence[1] = Integer.parseInt(btnCoordinateTwo.getText());
				sequence[2] = Integer.parseInt(btnCoordinateThree.getText());
				sequence[3] = Integer.parseInt(btnCoordinateFour.getText());
				Coordinate coordinate = new Coordinate(sequence);
				String id = AccountDTO.getInstance().getId();
				Operation conquer = new Conquer(id,null,coordinate);
				GameControl.getInstance().doOperation(conquer);
			} catch (Exception exception) {
				FrameUtil.sendMessageByPullDown(gp, "坐标输入错误");
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/opaqueHalf.png").getImage();
		// 绘制游戏界面
		g.drawImage(IMG_MAIN, 0, 0,695,215, null);
	}
	
}
