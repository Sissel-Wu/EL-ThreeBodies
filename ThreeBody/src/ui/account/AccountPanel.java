package ui.account;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.FrameUtil;
import ui.InformFrame;
import ui.component.BackButton;
import ui.component.SquareButton;
import util.R;
import control.AccountControl;
import control.MainControl;
import dto.AccountDTO;



public class AccountPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel psId;
	private JLabel labelId;
	private JLabel labelHead;
	private JLabel psPoint;
	private JLabel labelPoint;
//	private JLabel psRank;
//	private JLabel labelRank;
	private JLabel psTotalGames;
	private JLabel labelTotalGames;
	private JLabel psWins;
	private JLabel labelWins;
	private JLabel psLosts;
	private JLabel labelLosts;
	private JButton btnReturn;
	private JButton btnLogout;
	private JButton btnRevisePW;
	private AccountDTO accountDTO;
	private AccountControl accountControl;
	private MainControl mainControl;
	public AccountPanel(MainControl mainControl, String id,AccountControl accountControl){
		this.setLayout(null);
		this.accountDTO = AccountDTO.getInstance();
		this.mainControl=mainControl;
		this.accountControl=accountControl;
		init();
	}
	private void init() {
		psId = new JLabel();
		psId.setBounds(400,90,120,60);
		psId.setIcon(new ImageIcon("images/accountId.png"));
		this.add(psId);
		
		labelId = new JLabel();
		labelId.setBounds(370,150,180,60);
		labelId.setHorizontalAlignment(JLabel.CENTER);
		labelId.setFont(new Font("宋体",Font.PLAIN,60));
		labelId.setForeground(R.color.LIGHT_YELLOW);
		labelId.setText(accountDTO.getId());
		this.add(labelId);
		
		labelHead = new JLabel();
		labelHead.setBounds(600,70,150,150);
		if(accountDTO.getHead()!=null){
			Image headImage=accountDTO.getHead();
			headImage=headImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			labelHead.setIcon(new ImageIcon(headImage));
		}else{
			Image headImage = new ImageIcon("images/headtest.jpg").getImage();
			headImage=headImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			labelHead.setIcon(new ImageIcon(headImage));
		}
		labelHead.addMouseListener(new HeadSelectListener());
		this.add(labelHead);
		
		psPoint = new JLabel();
		psPoint.setBounds(390,270,60,30);
		psPoint.setIcon(new ImageIcon("images/accountpoint.png"));
		this.add(psPoint);
		
		labelPoint = new JLabel();
		labelPoint.setBounds(690,270,60,30);
		labelPoint.setFont(new Font("宋体",Font.PLAIN,30));
		labelPoint.setForeground(R.color.LIGHT_YELLOW);
		labelPoint.setText(accountDTO.getPoint()+"");
		labelPoint.setHorizontalAlignment(JLabel.RIGHT);
		this.add(labelPoint);
		
		//TODO MD 排名不写了 = =
//		psRank = new JLabel();
//		psRank.setBounds(400,350,60,30);
//		psRank.setIcon(new ImageIcon("images/accountlevel.png"));
//		this.add(psRank);
//		
//		labelRank = new JLabel();
//		labelRank.setBounds(690,350,60,30);
//		labelRank.setFont(new Font("宋体",Font.PLAIN,30));
//		labelRank.setForeground(R.color.LIGHT_YELLOW);
//		labelRank.setText(accountDTO.getRank()+"");
//		this.add(labelRank);;
		
		psTotalGames = new JLabel();
		psTotalGames.setBounds(400,320,80,30);
		psTotalGames.setIcon(new ImageIcon("images/accounttotalgames.png"));
		this.add(psTotalGames);
		
		labelTotalGames = new JLabel();
		labelTotalGames.setBounds(690,320,60,30);
		labelTotalGames.setFont(new Font("宋体",Font.PLAIN,30));
		labelTotalGames.setForeground(R.color.LIGHT_YELLOW);
		labelTotalGames.setText(accountDTO.getTotalGames()+"");
		labelTotalGames.setHorizontalAlignment(JLabel.RIGHT);
		this.add(labelTotalGames);
		
		
		psWins = new JLabel();
		psWins.setBounds(400,370,80,30);
		psWins.setIcon(new ImageIcon("images/accountwins.png"));
		this.add(psWins);
		
		labelWins = new JLabel();
		labelWins.setBounds(690,370,60,30);
		labelWins.setFont(new Font("宋体",Font.PLAIN,30));
		labelWins.setForeground(R.color.LIGHT_YELLOW);
		labelWins.setText(accountDTO.getWins()+"");
		labelWins.setHorizontalAlignment(JLabel.RIGHT);
		this.add(labelWins);
		
		
		psLosts = new JLabel();
		psLosts.setBounds(400,420,80,30);
		psLosts.setIcon(new ImageIcon("images/accountloses.png"));
		this.add(psLosts);
		
		
		labelLosts = new JLabel();
		labelLosts.setBounds(690,420,60,30);
		labelLosts.setFont(new Font("宋体",Font.PLAIN,30));
		labelLosts.setForeground(R.color.LIGHT_YELLOW);
		labelLosts.setText(accountDTO.getLosts()+"");
		labelLosts.setHorizontalAlignment(JLabel.RIGHT);
		this.add(labelLosts);
		
		this.btnReturn = new BackButton(new ReturnListener());
		this.add(btnReturn);
		
		this.btnLogout = new SquareButton("images/logOut.png");
		this.btnLogout.setContentAreaFilled(false);
		this.btnLogout.setBounds(670, 510, 100, 40);
		this.btnLogout.setBorderPainted(false);
		btnLogout.setFont(new Font("宋体", Font.BOLD, 20));
		btnLogout.setForeground(R.color.LIGHT_YELLOW);
		btnLogout.addMouseListener(new LogOutListener());
		this.add(btnLogout);
		
		this.btnRevisePW = new SquareButton("images/修改密码.png");
		this.btnRevisePW.setContentAreaFilled(false);
		this.btnRevisePW.setBounds(400, 510, 120, 40);
		this.btnRevisePW.setBorderPainted(false);
		btnRevisePW.setFont(new Font("宋体", Font.BOLD, 20));
		btnRevisePW.setForeground(R.color.LIGHT_YELLOW);
		btnRevisePW.addMouseListener(new RevisePWListener());
		this.add(btnRevisePW);
		
	}
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
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
			mainControl.toStartMenu();
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	class LogOutListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			FrameUtil.sendMessageByPullDown(AccountPanel.this, "登出中...");
			switch(accountControl.logoutAndClear()){
			case SUCCESS:
				FrameUtil.sendMessageByPullDown(AccountPanel.this, "登出成功，本地缓存已删除");
				break;
			default:
				FrameUtil.sendMessageByPullDown(AccountPanel.this, "登出失败 T_T");
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	class RevisePWListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			InformFrame revisePWFrame = new InformFrame("修改密码", 400, 300);
			RevisePWPanel revisePWpanel = new RevisePWPanel(revisePWFrame, accountControl,AccountPanel.this);
			revisePWFrame.add(revisePWpanel);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	class HeadSelectListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(jfc);
			File fileHead = jfc.getSelectedFile();
			System.out.println(fileHead.getPath());
			Image headImage = new ImageIcon(fileHead.getPath()).getImage();
			headImage=headImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			labelHead.setIcon(new ImageIcon(headImage));
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	
}
