package ui.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Player;
import model.card.ResourceGambling;
import model.operation.CardUse;
import ui.FrameUtil;
import ui.component.SquareButton;
import ui.component.YellowTransparentTextField;
import util.R;
import control.GameControl;
import dto.GameDTO;

public class GamblePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JButton btnOK;
	private JLabel msgLabel;
	private JLabel idLabel;
	private JTextField idField;
	private GamePanel gp;
	/**
	 * 
	 * @param mainControl 
	 * @param successInformFrame 
	 */
	public GamblePanel(GamePanel gp,JFrame frame,String message) {
		this.gp = gp;
		this.setLayout(null);
		this.frame=frame;
		this.initComonent(message);
	}
	private void initComonent(String message) {
		this.btnOK = new SquareButton("images/btnOk.png");
		this.btnOK.setContentAreaFilled(false);
		this.btnOK.setBounds(120, 132,60, 30);
		this.btnOK.setBorderPainted(false);
		btnOK.addMouseListener(new OKListener());
		this.add(btnOK);
		
		msgLabel = new JLabel();
		msgLabel.setForeground(R.color.LIGHT_YELLOW);
		msgLabel.setText(message);
		msgLabel.setFont(new Font("宋体", Font.BOLD, 20));
		msgLabel.setBounds(60,0,180,80);
		this.add(msgLabel);
		
		idLabel = new JLabel();
		idLabel.setBounds(35,75,60,30);
		idLabel.setIcon(new ImageIcon("images/gamble.png"));
		this.add(idLabel);
		
		idField = new YellowTransparentTextField(3);
		idField.setBounds(105,75,140,30);
		idField.setOpaque(false);
		this.add(idField);
		
	}
	
	class OKListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				int NumberOfResource = Integer.parseInt(idField.getText());
				
				Player user = GameDTO.getInstance().getUser();
				String userAccountId = user.getAccount().getId();
				
				if (user.getResource() < NumberOfResource){
					FrameUtil.sendMessageByPullDown(gp, "资源不足！");
					return;
				}
				
				Random random = new Random();
				ResourceGambling rg;
				if(random.nextInt(2) == 0){
					rg = new ResourceGambling(userAccountId, userAccountId, NumberOfResource,true);
				}else{
					rg = new ResourceGambling(userAccountId, userAccountId, NumberOfResource,false);
				}
				CardUse cardUseRg = new CardUse(userAccountId, userAccountId, rg);
				GameControl.getInstance().doOperation(cardUseRg);
				frame.setVisible(false);
				
			} catch (Exception ex) {
				FrameUtil.sendMessageByPullDown(gp, "输入错误！");
			}
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/img1.jpg").getImage();
		// 绘制游戏界面
		g.drawImage(IMG_MAIN, 0, 0, null);
	}
}
