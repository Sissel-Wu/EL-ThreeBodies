package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Account;
import model.Room;
import ui.component.BackButton;
import ui.lobby.ButtonPanel;
import util.R;
import control.MainControl;
import control.RoomControl;
import dto.AccountDTO;

public class RoomPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	//双缓冲机制
	private Image iBuffer;  
	private Graphics gBuffer;
	
	private JButton btn_ready;
	private JButton btn_lobbyReturn;
	private JButton btn_roomInfo;
	private JLabel psId;
	private JLabel labelId;
	private JLabel labelHead;
	private JLabel psPoint;
	private JLabel labelPoint;
	private JLabel psTotalGames;
	private JLabel labelTotalGames;
	private JLabel psWins;
	private JLabel labelWins;
	private JLabel psLosts;
	private JLabel labelLosts;
	private JLabel nowReady;
	private boolean isAbleToPress=true;
	private Image opaque = new ImageIcon("images/coNothing.png").getImage();
	private List<Rectangle> locations = new ArrayList<Rectangle>(8);
	
	private MainControl mainControl;
	private RoomControl roomControl;
	private Room room;
	private List<Account> accounts;
	
	public RoomPanel(MainControl mc,RoomControl roomControl) {
		this.setLayout(null);
		this.roomControl = roomControl;
		this.room = roomControl.getRoom();
		this.mainControl = mc;
		this.accounts=room.getAccounts();
		this.initLocation();
		this.initComponent();
		this.initAccountsInfo();
	}
	
	public void refresh() {
		this.room = roomControl.getRoom();
		this.accounts = room.getAccounts();
		for(Component cp:this.getComponents()){
			if(cp == btn_lobbyReturn){
				continue;
			}
			this.remove(cp);
		}
		this.initComponent();
		this.initAccountsInfo();
		mainControl.frame.setContentPane(this);
	}
	
	private void initAccountsInfo() {
		for (int i = 0; i < accounts.size(); i++) {
			initPlayer(i);
		}
	}
		
	private void initPlayer(int i) {
		Rectangle rect=locations.get(i);
		
		psId = new JLabel();
		psId.setBounds(rect.x,rect.y,60,30);
		Image img_id=new ImageIcon("images/accountId.png").getImage();
		psId.setIcon(new ImageIcon(img_id.getScaledInstance(60, 30, Image.SCALE_SMOOTH)));
		
		labelId = new JLabel();
		labelId.setBounds(rect.x,rect.y+30,120,30);
		labelId.setFont(new Font("宋体",Font.PLAIN,20));
		labelId.setForeground(R.color.LIGHT_YELLOW);
		labelId.setText(accounts.get(i).getId());
		
		labelHead = new JLabel();
		labelHead.setBounds(rect.x+90,rect.y+15,30,30);
		if(accounts.get(i).getHead()!=null){
			Image headImage=accounts.get(i).getHead();
			headImage=headImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			labelHead.setIcon(new ImageIcon(headImage));
		}else{
			Image headImage = new ImageIcon("images/headtest.jpg").getImage();
			headImage=headImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			labelHead.setIcon(new ImageIcon(headImage));
		}
		
		psPoint = new JLabel();
		psPoint.setBounds(rect.x+155,rect.y+15,60,30);
		Image img_point=new ImageIcon("images/accountpoint.png").getImage();
		psPoint.setIcon(new ImageIcon(img_point.getScaledInstance(60, 30, Image.SCALE_SMOOTH)));
		
		labelPoint = new JLabel();
		labelPoint.setBounds(rect.x+235,rect.y+15,60,30);
		labelPoint.setFont(new Font("宋体",Font.PLAIN,30));
		labelPoint.setForeground(R.color.LIGHT_YELLOW);
		labelPoint.setText(accounts.get(i).getPoint()+"");
		
		psTotalGames = new JLabel();
		psTotalGames.setBounds(rect.x+335,rect.y+15,60,30);
		Image img_totalGames=new ImageIcon("images/accounttotalgames.png").getImage();
		psTotalGames.setIcon(new ImageIcon(img_totalGames.getScaledInstance(60, 30, Image.SCALE_SMOOTH)));
		
		labelTotalGames = new JLabel();
		labelTotalGames.setBounds(rect.x+450,rect.y+15,60,30);
		labelTotalGames.setFont(new Font("宋体",Font.PLAIN,30));
		labelTotalGames.setForeground(R.color.LIGHT_YELLOW);
		labelTotalGames.setText(accounts.get(i).getTotalGames()+"");
		
		psWins = new JLabel();
		psWins.setBounds(rect.x+500,rect.y,60,30);
		Image img_wins=new ImageIcon("images/accountwins.png").getImage();
		psWins.setIcon(new ImageIcon(img_wins.getScaledInstance(60, 30, Image.SCALE_SMOOTH)));
		
		labelWins = new JLabel();
		labelWins.setBounds(rect.x+610,rect.y,60,30);
		labelWins.setFont(new Font("宋体",Font.PLAIN,30));
		labelWins.setForeground(R.color.LIGHT_YELLOW);
		labelWins.setText(accounts.get(i).getWins()+"");
		
		psLosts = new JLabel();
		psLosts.setBounds(rect.x+500,rect.y+30,60,30);
		Image img_losts=new ImageIcon("images/accountloses.png").getImage();
		psLosts.setIcon(new ImageIcon(img_losts.getScaledInstance(60, 30, Image.SCALE_SMOOTH)));
		
		labelLosts = new JLabel();
		labelLosts.setBounds(rect.x+610,rect.y+30,60,30);
		labelLosts.setFont(new Font("宋体",Font.PLAIN,30));
		labelLosts.setForeground(R.color.LIGHT_YELLOW);
		labelLosts.setText(accounts.get(i).getLosts()+"");
		
		
		nowReady = new JLabel();
		nowReady.setBounds(rect.x+670,rect.y,91,60);
		addNowReady(i);
		this.add(nowReady);
		
		this.add(psId);
		this.add(labelId);
		this.add(labelHead);
		this.add(psPoint);
		this.add(labelPoint);
		this.add(psTotalGames);
		this.add(labelTotalGames);
		this.add(psWins);
		this.add(labelWins);
		this.add(psLosts);
		this.add(labelLosts);
}

	private void addNowReady(int i) {
		if(room.getCreater().getId().equals(accounts.get(i).getId())){
			nowReady.setIcon(new ImageIcon());
			return;
		}
		if (room.isReady(accounts.get(i).getId())) {
			Image nowReadyImg = new ImageIcon("images/NowReady.png").getImage();
			nowReadyImg = nowReadyImg.getScaledInstance(91, 60,	Image.SCALE_SMOOTH);
			nowReady.setIcon(new ImageIcon(nowReadyImg));
		} else {
			nowReady.setIcon(new ImageIcon());
		}
	}

	public void initLocation(){
		locations.add(new Rectangle(50,60,800,60));
		locations.add(new Rectangle(50,130,800,60));
		locations.add(new Rectangle(50,200,800,60));
		locations.add(new Rectangle(50,270,800,60));
		locations.add(new Rectangle(50,340,800,60));
		locations.add(new Rectangle(50,410,800,60));
		locations.add(new Rectangle(50,480,800,60));
		locations.add(new Rectangle(50,550,800,60));
	}
	
	public void initComponent() {
		
		this.btn_roomInfo = new JButton();
		this.btn_roomInfo.setIcon(new ImageIcon(opaque.getScaledInstance(300, 125, Image.SCALE_SMOOTH)));
		JPanel roomPanel = new ButtonPanel(room);
		roomPanel.setBounds(700, 80, 300, 125);
		this.btn_roomInfo.setBounds(820, 60, 300, 125);
		this.btn_roomInfo.setContentAreaFilled(false);
		this.btn_roomInfo.setBorderPainted(false);
		this.btn_roomInfo.add(roomPanel);
		this.add(btn_roomInfo);
		
		this.btn_ready = new JButton();
		this.btn_ready.setIcon(new ImageIcon("images/ready.png"));
		this.btn_ready.setContentAreaFilled(false);
		this.btn_ready.setBorderPainted(false);
		this.btn_ready.setBounds(840, 500, 100, 50);
		this.btn_ready.setEnabled(isAbleToPress);
		MultiFunctionListener msl = new MultiFunctionListener(this.btn_ready);
		msl.refresh();
		this.btn_ready.addMouseListener(msl);
		this.add(btn_ready);

		if(btn_lobbyReturn == null){
			this.btn_lobbyReturn = new BackButton(new ReturnListener());
			this.add(btn_lobbyReturn);
		}
	}
	
	@Override
	public void update(Graphics scr)
	{
	    if(iBuffer==null)
	    {
	       iBuffer=createImage(this.getSize().width,this.getSize().height);
	       gBuffer=iBuffer.getGraphics();
	    }
	       gBuffer.setColor(getBackground());
	       gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);
	       paint(gBuffer);
	       scr.drawImage(iBuffer,0,0,this);
	}
	@Override
	public void paintComponent(Graphics g) {
		Image background = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(background, 0, 0, null);
	}

	class MultiFunctionListener extends MouseAdapter  {
		/*
		 * 0:ready 1:cancelReady 2:start
		 */
		int state;
		JButton owner;
		
		public MultiFunctionListener(JButton owner) {
			super();
			this.owner = owner;
		}
		
		public void refresh(){
			if(room.getCreater().getId().equals(AccountDTO.getInstance().getId())){
				// TODO 显示为“开始”
				owner.setIcon(new ImageIcon("images/roomGameStart.png"));
				state = 2;
		    }else if(room.isReady(AccountDTO.getInstance().getId())){
		    	// TODO 显示“取消预备”
		    	owner.setIcon(new ImageIcon("images/readyCancel.png"));
		    	state = 1;
		    }else{
		    	// TODO 显示“预备”
		    	owner.setIcon(new ImageIcon("images/ready.png"));
		    	state = 0;
		    }
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch(state){
			case 2:
				if(roomControl.start() == R.info.SUCCESS){
					FrameUtil.sendMessageByPullDown(RoomPanel.this, "载入游戏中");
				}else{
					FrameUtil.sendMessageByPullDown(RoomPanel.this, "还没准备好");
				}
				break;
			case 1:
				FrameUtil.sendMessageByPullDown(RoomPanel.this, "正在取消准备");
				roomControl.cancelReady();
				refresh();
				break;
			case 0:
				FrameUtil.sendMessageByPullDown(RoomPanel.this, "正在准备");
				roomControl.ready();
				refresh();
				break;
			}
		}
	}

	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			roomControl.exit();
		}
	}
}
