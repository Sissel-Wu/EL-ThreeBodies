package ui.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Player;
import model.operation.SendMessage;
import ui.FrameUtil;
import ui.component.YellowTransparentTextField;
import control.GameControl;
import dto.AccountDTO;
import dto.GameDTO;

public class MessagePanel  extends JPanel{
	private static final long serialVersionUID = 1L;
		private JTextField message;

		private JButton btnSend;
		private JButton btnReturn;
		private JComboBox<String> select;
		private GamePanel gp;
		
		List<Player> players=null;
		Player user;

		public MessagePanel(GamePanel gp) {
			this.gp = gp;
			this.setLayout(null);
			setBounds(231, 435, 695, 215);
			players= GameDTO.getInstance().getPlayers();
			user=GameDTO.getInstance().getUser();
			this.initComonent();
		}

		private void initComonent() {
			this.message = new YellowTransparentTextField(20);;
			this.message.setBounds(80, 32, 560, 30);
			message.setFont(new Font("宋体", Font.BOLD, 30));
			this.add(message);
			
			this.btnSend = new JButton(new ImageIcon("images/btnbroadcast.png"));
			this.btnSend.setContentAreaFilled(false);
			this.btnSend.setBounds(360, 95, 120, 60);
			this.btnSend.setBorderPainted(false);
			btnSend.addMouseListener(new SendMessageListener());
			this.add(btnSend);
			
			this.btnReturn = new JButton(new ImageIcon("images/btnbroadcastcancel.png"));
			this.btnReturn.setContentAreaFilled(false);
			this.btnReturn.setBounds(520, 95, 120, 60);
			this.btnReturn.setBorderPainted(false);
			btnReturn.addMouseListener(new ReturnListener());
			this.add(btnReturn);
			
			select = new JComboBox<String>();
			select.setFont(new Font("宋体", Font.PLAIN, 30));
			select.setBounds(15,32, 60, 30);
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
				setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnReturn.setIcon(new ImageIcon("images/btnbroadcastcancel2.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnReturn.setIcon(new ImageIcon("images/btnbroadcastcancel.png"));
			}
			
		}
		class SendMessageListener extends MouseAdapter {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!(GameDTO.getInstance().getWhoseTurn()==user)){
					FrameUtil.sendMessageByPullDown(gp, "不是您的回合");
					return;
				}
				if(!user.isMessageable()){
					FrameUtil.sendMessageByPullDown(gp, "不能使用消息");
					return;
				}
				String receiver = (String)select.getSelectedItem();
				String messageStr=message.getText();
				SendMessage operation = new SendMessage(AccountDTO.getInstance().getId(), receiver,messageStr);
				GameControl.getInstance().doOperation(operation);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSend.setIcon(new ImageIcon("images/btnbroadcast2.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSend.setIcon(new ImageIcon("images/btnbroadcast.png"));
			}
			
			
		}

		@Override
		public void paintComponent(Graphics g) {
			Image IMG_MAIN = new ImageIcon("images/opaqueHalf.png").getImage();
			// 绘制游戏界面
			g.drawImage(IMG_MAIN, 0, 0,695,215, null);
		}
		
}
