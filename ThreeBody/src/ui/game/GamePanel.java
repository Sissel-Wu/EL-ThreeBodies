package ui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Coordinate;
import model.Player;
import model.card.Card;
import model.card.NoBroadcasting;
import model.card.PartialBlock;
import model.card.ResourcePotion;
import model.card.SillySophon;
import model.card.Sophon;
import model.card.TechPotion;
import model.card.WholeBlock;
import model.operation.CardUse;
import model.operation.Operation;
import model.role.Role;
import ui.FrameUtil;
import ui.InformFrame;
import ui.block.PatialBlockFrame;
import ui.block.PatialBlockPanel;
import ui.component.BackButton;
import ui.component.SquareButton;
import ui.sophon.SophonFinderFrame;
import ui.sophon.SophonFinderPanel;
import util.R;
import control.GameControl;
import dto.AccountDTO;
import dto.GameDTO;

public class GamePanel  extends JPanel{
    private static final long serialVersionUID = 1L;
	
	private JButton btnReturn;
	// 双缓冲机制
	private Image iBuffer;
	private Graphics gBuffer;
		
	private JButton btnCardSophon;
	private JButton btnCardSillySophon;
	private JButton btnCardWholeBlock;
	private JButton btnCardPatialBlock;
	private JButton btnCardNoBroadcasting;
	private JButton btnCardTechPotion;
	private JButton btnCardResourcePotion;
	private JButton btnCardResourceGambling;
	private JButton btnPriviledgeGetRole;
	private JButton[] btnCards;
	
	private boolean isMyTurn;
	
	private JButton btnBroadcast;
	private JButton btnHistory;
	private JButton btnMessage;
	
	private JLabel numOfBout;
	
	private JButton btnTurnEnd;
	
	private JPanel panelBroadcast= new BroadcastPanel(this);
	private JPanel panelMessage= new MessagePanel(this);
	private JPanel panelHistory= new HistoryPanel();
	
	private JPanel panelTurn = new TurnPanel();
	private JPanel panelCountDown = new CountDownPanel();
	private JPanel panelTech = new TechPanel();
	private JPanel panelResource = new ResourcePanel();
	
	private JLabel resourceString;
	private JLabel techString;
	
	private JLabel[] enemyLabels = new JLabel[7];
	private JLabel[] coordinateOfEnemies = new JLabel[7];
	private JLabel[] promptLabels = new JLabel[9];
	private ImageIcon[] prompts = new ImageIcon[9];
	private List<Rectangle> location = new ArrayList<Rectangle>(7);
	
	private List<Player> enemies = new ArrayList<Player>();
	private Player user = GameDTO.getInstance().getUser();
	private GameControl gameControl;
	private GameDTO gameDTO;
	
	private Map<String,Integer> cardTechList = R.card.cardTechList;
	private Map<String,Integer> cardRsrList = R.card.cardRsrList;
	
	public GamePanel(GameControl gameControl) {
		this.gameControl = gameControl;
		this.gameDTO = GameDTO.getInstance();
		// 初始化对方玩家
		for (Player player : gameDTO.getPlayers()) {
			if(!player.getAccount().getId().equals(user.getAccount().getId())){
				enemies.add(player);
			}
		}
		this.setLayout(null);
		if(gameDTO.getWhoseTurn() == gameDTO.getUser()){
			isMyTurn=true;
		}else{
			isMyTurn=false;
		}
		this.initComonent();
		this.initEnemyLocation();
		this.initPrompt();
		this.createEnemy();
		this.createCoordinatePanel();
		
	}
	
	public void refresh() {
		if(GameDTO.getInstance().getWhoseTurn()==user){
			isMyTurn=true;
		}else{
			isMyTurn=false;
		}
		
		this.panelTurn.repaint();
		this.panelTech.repaint();
		this.panelResource.repaint();
	}
	
	private void initPrompt() {
		prompts[0]=new ImageIcon("images/psSophonLabel.png");
		prompts[1]=new ImageIcon("images/psSillySophonLabel.png");
		prompts[2]=new ImageIcon("images/psWholeBlockLabel.png");
		prompts[3]=new ImageIcon("images/psPartialBlockLabel.png");
		prompts[4]=new ImageIcon("images/psNoBroadcastLabel.png");
		prompts[5]=new ImageIcon("images/psTechPotionLabel.png");
		prompts[6]=new ImageIcon("images/psResourcePotionLabel.png");
		prompts[7]=new ImageIcon("images/psGambleLabel.png");
		prompts[8]=new ImageIcon("images/psRoleGetLabel.png");
		for (int i = 0; i < prompts.length; i++) {
			promptLabels[i] = new JLabel();
			promptLabels[i].setIcon(prompts[i]);
			promptLabels[i].setVisible(false);
		}
	}
	
	/**
	 * 存放敌人的位置
	 */
	private void initEnemyLocation() {
		Rectangle  enemy1 = new Rectangle(350,100,100,100);
		Rectangle  enemy2 = new Rectangle(650,100,100,100);
		Rectangle  enemy3= new Rectangle(250,300,100,100);
		Rectangle  enemy4 =new Rectangle(500,300,100,100);
		Rectangle  enemy5 = new Rectangle(750,300,100,100);
		Rectangle  enemy6 =new Rectangle(100,100,100,100);
		Rectangle  enemy7 =new Rectangle(900,100,100,100);
		location.add(enemy1);
		location.add(enemy2);
		location.add(enemy3);
		location.add(enemy4);
		location.add(enemy5);
		location.add(enemy6);
		location.add(enemy7);
	}
	
	/**
	 * 添加敌人
	 */
	private void createEnemy() {
		for (int i = 0; i < gameDTO.getPlayers().size() -1; i++) {
			enemyLabels[i] = new JLabel();
			enemyLabels[i].setIcon(new ImageIcon("images/star07.gif"));
			enemyLabels[i].setBounds(location.get(i));
			enemyLabels[i].addMouseListener(new EnemyListener(i));
			this.add(enemyLabels[i]);
		}
	}
	
	/*
	 * 鼠标移到星球上显示我方已知的信息
	 */
	private void coordinateShow(int i){
		Player pi = this.enemies.get(i);
		String role = user.getFoundRoles().get(pi) == null ? "未明" : pi.getRole().toString();
		coordinateOfEnemies[i].setText("<html>玩家："+pi.getAccount().getId()+"<br>"
				+"坐标："+user.getFoundCoordinates().get(pi).toString()+"<br>"
				+"角色："+role+"</html>");
		coordinateOfEnemies[i].setVisible(true);
	}
	
	private void createCoordinatePanel() {
		for (int i = 0; i < gameDTO.getPlayers().size()-1; i++) {
			
			coordinateOfEnemies[i] = new JLabel();
			coordinateOfEnemies[i].setFont(new Font("宋体",Font.PLAIN,20));
			coordinateOfEnemies[i].setForeground(R.color.LIGHT_YELLOW);
			coordinateOfEnemies[i].setBackground(Color.DARK_GRAY);
			coordinateOfEnemies[i].setOpaque(true);
			Rectangle rec = location.get(i);
			rec.x-=25; rec.y+=85; rec.width+=50; rec.height=100;
			coordinateOfEnemies[i].setBounds(rec);
			coordinateOfEnemies[i].setVisible(false);
			this.add(coordinateOfEnemies[i]);
		}
	}
	/**
	 * 初始化
	 */
	private void initComonent() {
		this.btnReturn = new BackButton(new ReturnListener());
		this.add(btnReturn);
		
		this.btnBroadcast = new JButton(new ImageIcon("images/广播.png"));
		this.btnBroadcast.setContentAreaFilled(false);
		this.btnBroadcast.setBorderPainted(false);
		this.btnBroadcast.setBounds(250, 590, 60, 30);
		this.btnBroadcast.addMouseListener(new BroadcastListener());
		this.add(btnBroadcast);
		
		this.btnHistory = new JButton(new ImageIcon("images/历史记录.png"));
		this.btnHistory.setContentAreaFilled(false);
		this.btnHistory.setBorderPainted(false);
		this.btnHistory.setBounds(503, 590, 80, 30);
		this.btnHistory.addMouseListener(new HistoryListener());
		this.add(btnHistory);
		
		this.btnMessage = new JButton(new ImageIcon("images/留言.png"));
		this.btnMessage.setContentAreaFilled(false);
		this.btnMessage.setBounds(806, 590, 60, 30);
		this.btnMessage.setBorderPainted(false);
		this.btnMessage.addMouseListener(new MessageListener());
		this.add(btnMessage);
		
		this.btnTurnEnd = new JButton(new ImageIcon("images/turnEnd.png"));
		this.btnTurnEnd.setContentAreaFilled(false);
		this.btnTurnEnd.setBorderPainted(false);
		this.btnTurnEnd.setBounds(1000, 580, 80, 40);
		this.btnTurnEnd.setFont(new Font("宋体", Font.BOLD, 15));
		this.btnTurnEnd.setForeground(R.color.LIGHT_YELLOW);
		this.btnTurnEnd.addMouseListener(new EndListener());
		this.add(btnTurnEnd);
		
		this.btnCardSophon = new JButton("三体智子");
		this.btnCardSophon.setContentAreaFilled(false);
		this.btnCardSophon.setBorderPainted(false);
		this.btnCardSophon.setBounds(1070, 30, 150, 30);
		this.btnCardSophon.setIcon(new ImageIcon("images/78.png"));
//		if (isMyTurn) {
//			this.btnCardSophon.setEnabled(true);
//		}else{
//			this.btnCardSophon.setEnabled(false);
//		}
		this.btnCardSophon.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardSophon.addMouseListener(new CardSophonListener());
		this.add(btnCardSophon);
		
		
		this.btnCardSillySophon = new JButton("人造智子");
		this.btnCardSillySophon.setContentAreaFilled(false);
		this.btnCardSillySophon.setBounds(1070, 60, 150, 30);
//		if (isMyTurn) {
//			this.btnCardSillySophon.setEnabled(true);
//		}else{
//			this.btnCardSillySophon.setEnabled(false);
//		}
		this.btnCardSillySophon.setBorderPainted(false);
		this.btnCardSillySophon.setIcon(new ImageIcon("images/240.png"));
		this.btnCardSillySophon.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardSillySophon.addMouseListener(new CardSillySophonListener());
		this.add(btnCardSillySophon);
		
		this.btnCardWholeBlock = new JButton("全局黑域");
		this.btnCardWholeBlock.setContentAreaFilled(false);
		this.btnCardWholeBlock.setBorderPainted(false);
		this.btnCardWholeBlock.setIcon(new ImageIcon("images/288.png"));
//		if (isMyTurn) {
//			this.btnCardWholeBlock.setEnabled(true);
//		}else{
//			this.btnCardWholeBlock.setEnabled(false);
//		}
		this.btnCardWholeBlock.setBounds(1070, 90, 150, 30);
		this.btnCardWholeBlock.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardWholeBlock.addMouseListener(new CardWholeBlockListener());
		this.add(btnCardWholeBlock);
		
		this.btnCardPatialBlock = new JButton("局部黑域");
		this.btnCardPatialBlock.setContentAreaFilled(false);
		this.btnCardPatialBlock.setBorderPainted(false);
		this.btnCardPatialBlock.setIcon(new ImageIcon("images/36.png"));
//		if (isMyTurn) {
//			this.btnCardPatialBlock.setEnabled(true);
//		}else{
//			this.btnCardPatialBlock.setEnabled(false);
//		}
		this.btnCardPatialBlock.setBounds(1070, 120, 150, 30);
		this.btnCardPatialBlock.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardPatialBlock.addMouseListener(new CardPatialBlockListener());
		this.add(btnCardPatialBlock);
		
		this.btnCardNoBroadcasting = new JButton("电波干扰");
		this.btnCardNoBroadcasting.setContentAreaFilled(false);
		this.btnCardNoBroadcasting.setBorderPainted(false);
		this.btnCardNoBroadcasting.setIcon(new ImageIcon("images/268.png"));
//		if (isMyTurn) {
//			this.btnCardNoBroadcasting.setEnabled(true);
//		}else{
//			this.btnCardNoBroadcasting.setEnabled(false);
//		}
		this.btnCardNoBroadcasting.setBounds(1070, 150, 150, 30);
		this.btnCardNoBroadcasting.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardNoBroadcasting.addMouseListener(new CardNoBroadcastingListener());
		this.add(btnCardNoBroadcasting);
		
		this.btnCardTechPotion = new JButton("科技革命");
		this.btnCardTechPotion.setContentAreaFilled(false);
		this.btnCardTechPotion.setBorderPainted(false);
		this.btnCardTechPotion.setIcon(new ImageIcon("images/20.png"));
//		if (isMyTurn) {
//			this.btnCardTechPotion.setEnabled(true);
//		}else{
//			this.btnCardTechPotion.setEnabled(false);
//		}
		this.btnCardTechPotion.setBounds(1070, 180, 150, 30);
		this.btnCardTechPotion.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardTechPotion.addMouseListener(new CardTechPotionListener());
		this.add(btnCardTechPotion);
		
		this.btnCardResourcePotion = new JButton("资源爆发");
		this.btnCardResourcePotion.setContentAreaFilled(false);
		this.btnCardResourcePotion.setBorderPainted(false);
		this.btnCardResourcePotion.setIcon(new ImageIcon("images/112.png"));
//		if (isMyTurn) {
//			this.btnCardResourcePotion.setEnabled(true);
//		}else{
//			this.btnCardResourcePotion.setEnabled(false);
//		}
		this.btnCardResourcePotion.setBounds(1070, 210, 150, 30);
		this.btnCardResourcePotion.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardResourcePotion.addMouseListener(new CardResourcePotionListener());
		this.add(btnCardResourcePotion);
		
		this.btnCardResourceGambling = new JButton("资源赌博");
		this.btnCardResourceGambling.setContentAreaFilled(false);
		this.btnCardResourceGambling.setBorderPainted(false);
		this.btnCardResourceGambling.setIcon(new ImageIcon("images/16.png"));
//		if (isMyTurn) {
//			this.btnCardResourceGambling.setEnabled(true);
//		}else{
//			this.btnCardResourceGambling.setEnabled(false);
//		}
		this.btnCardResourceGambling.setBounds(1070, 240, 150, 30);
		this.btnCardResourceGambling.setForeground(R.color.LIGHT_YELLOW);
		this.btnCardResourceGambling.addMouseListener(new CardResourceGamblingListener());
		this.add(btnCardResourceGambling);
		
		this.btnPriviledgeGetRole = new JButton("身份探知");
		this.btnPriviledgeGetRole.setContentAreaFilled(false);
		this.btnPriviledgeGetRole.setBorderPainted(false);
		this.btnPriviledgeGetRole.setIcon(new ImageIcon("images/164.png"));
//		if (isMyTurn) {
//			this.btnPriviledgeGetRole.setEnabled(true);
//		}else{
//			this.btnPriviledgeGetRole.setEnabled(false);
//		}
		this.btnPriviledgeGetRole.setBounds(1070, 270, 150, 30);
		this.btnPriviledgeGetRole.setForeground(R.color.LIGHT_YELLOW);
		this.btnPriviledgeGetRole.addMouseListener(new PriviledgeGetRoleListener());
		this.add(btnPriviledgeGetRole);
		
		this.resourceString = new JLabel(new ImageIcon("images/resource.png"));
		this.resourceString.setBounds(30,200,60,30);
		this.add(resourceString);
		
		this.techString = new JLabel(new ImageIcon("images/tech.png"));
		this.techString.setBounds(30,230,60,30);
		this.add(techString);
		
		this.numOfBout = new JLabel(new ImageIcon("images/回合.png"));
		this.numOfBout.setBounds(30, 165,60,30);
		this.add(numOfBout);
		
		this.add(panelTech);
		this.add(panelTurn);
		this.add(panelResource);
		this.add(panelCountDown);
		
		this.add(panelHistory);
		this.add(panelMessage);
		this.add(panelBroadcast);
		panelHistory.setVisible(false);
		panelMessage.setVisible(false);
		panelBroadcast.setVisible(false);
		
		btnCards = new JButton[9];
		btnCards[0] = this.btnCardNoBroadcasting;
		btnCards[1] = this.btnCardPatialBlock;
		btnCards[2] = this.btnCardResourceGambling;
		btnCards[3] = this.btnCardResourcePotion;
		btnCards[4] = this.btnCardSillySophon;
		btnCards[5] = this.btnCardSophon;
		btnCards[6] = this.btnCardTechPotion;
		btnCards[7] = this.btnCardWholeBlock;
		btnCards[8] = this.btnPriviledgeGetRole;
	}

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
		Image IMG_MAIN = new ImageIcon("images/sky5.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
	}
	
	class ReturnListener extends MouseAdapter  {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 测试
			System.out.println(AccountDTO.getInstance().getId()+"端:");
			System.out.println("第"+gameDTO.getBout()+"回合");
			System.out.println("回合玩家："+gameDTO.getWhoseTurn().getAccount().getId());
			List<Player> players = gameDTO.getPlayers();
			for (Player player : players) {
				System.out.println("--"+player.getAccount().getId()+":");
				System.out.println("--"+player.getRole()+":");
				System.out.println("--"+"资源："+player.getResource());
				System.out.println("--"+"科技："+player.getTechPoint());
				System.out.println("--"+"坐标："+player.getCoordinate().toString());
				System.out.println("--"+"保护情况"+player.getCoordinate().getProtectingState());
				if(player.isPrivilegeGetRole()){
					System.out.println("--可以用特权");
				}else{
					System.out.println("--不可以用特权");
				}
				if(player.isBroadcastable()){
					System.out.println("--可以用广播");
				}else{
					System.out.println("--不能用广播");
				}
				if(player.isLost()){
					System.out.println("--已死");
				}else{
					System.out.println("--存活中");
				}
				System.out.println("----"+"发现坐标：");
				for(Entry<Player,Coordinate> entry:player.getFoundCoordinates().entrySet()){
					System.out.println("----"+entry.getKey().getAccount().getId()+":"+entry.getValue());
				}
				System.out.println("----"+"发现角色：");
				for(Entry<Player,Role> entry:player.getFoundRoles().entrySet()){
					System.out.println("----"+entry.getKey().getAccount().getId()+":"+entry.getValue());
				}
				System.out.println("---------------------------");
			}
			System.out.println("=======================================================================");
		}
	}
	/**
	 * 智子
	 * @author user
	 *
	 */
	class CardSophonListener implements MouseListener {
		int x = btnCardSophon.getX();
		int y = btnCardSophon.getY();
		Rectangle rec = btnCardSophon.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (user.getUnavailableCards().contains(Sophon.class)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("Sophon")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");	
				return;
			}
			if (user.getResource() < cardRsrList.get("Sophon")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			initSophon();
		}
		private void initSophon() {
			JFrame sophonFinderFrame = new SophonFinderFrame("智子");
			JPanel finder = new SophonFinderPanel(sophonFinderFrame,gameControl);
			sophonFinderFrame.setContentPane(finder);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardSophon.setLocation(x-40, y);
			btnCardSillySophon.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardSophon.setLocation(x-40, y);
			btnCardSillySophon.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardSophon.setLocation(x-40, y);
			btnCardSillySophon.setLocation(x-10, y+30);
			promptLabels[0].setBounds(rec.x-prompts[0].getIconWidth()-15,rec.y,prompts[0].getIconWidth(),prompts[0].getIconHeight());
			promptLabels[0].setVisible(true);
			add(promptLabels[0]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardSophon.setLocation(x, y);
			btnCardSillySophon.setLocation(x, y+30);
			promptLabels[0].setVisible(false);
		}
	}
	/**
	 * 低级智子
	 * @author user
	 *
	 */
	class CardSillySophonListener implements MouseListener {
		int x = btnCardSillySophon.getX();
		int y = btnCardSillySophon.getY();
		Rectangle rec = btnCardSillySophon.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (gameDTO.getUser().getUnavailableCards().contains(SillySophon.class)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("SillySophon")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");	
				return;
			}
			if (user.getResource() < cardRsrList.get("SillySophon")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			initSillySophon();
		}
		private void initSillySophon() {
			
			JFrame sophonFinder = new SophonFinderFrame("人造智子");
			JPanel finder = new SophonFinderPanel(sophonFinder,gameControl);
			sophonFinder.setContentPane(finder);
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardSophon.setLocation(x-10, y-30);
			btnCardSillySophon.setLocation(x-40, y);
			btnCardWholeBlock.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardSophon.setLocation(x-10, y-30);
			btnCardSillySophon.setLocation(x-40, y);
			btnCardWholeBlock.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardSophon.setLocation(x-10, y-30);
			btnCardSillySophon.setLocation(x-40, y);
			btnCardWholeBlock.setLocation(x-10, y+30);
			promptLabels[1].setBounds(rec.x-prompts[1].getIconWidth()-15,rec.y,prompts[1].getIconWidth(),prompts[1].getIconHeight());
			promptLabels[1].setVisible(true);
			add(promptLabels[1]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardSophon.setLocation(x, y-30);
			btnCardSillySophon.setLocation(x, y);
			btnCardWholeBlock.setLocation(x, y+30);
			promptLabels[1].setVisible(false);
		}
	}
	/**
	 * 全局黑域
	 * @author user
	 *
	 */
	class CardWholeBlockListener implements MouseListener {
		int x = btnCardWholeBlock.getX();
		int y = btnCardWholeBlock.getY();
		Rectangle rec = btnCardWholeBlock.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (gameDTO.getUser().getUnavailableCards().contains(WholeBlock.class)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("WholeBlock")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");	
				return;
			}
			if (user.getResource() < cardRsrList.get("WholeBlock")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			FrameUtil.sendMessageByPullDown(GamePanel.this, "保护所有坐标一轮");
			String id = AccountDTO.getInstance().getId();
			Card wholeBlock = new WholeBlock(id, null);
			Operation cardUse = new CardUse(id,null,wholeBlock);
			GameControl.getInstance().doOperation(cardUse);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardSillySophon.setLocation(x-10, y-30);
			btnCardWholeBlock.setLocation(x-40, y);
			btnCardPatialBlock.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardSillySophon.setLocation(x-10, y-30);
			btnCardWholeBlock.setLocation(x-40, y);
			btnCardPatialBlock.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardSillySophon.setLocation(x-10, y-30);
			btnCardWholeBlock.setLocation(x-40, y);
			btnCardPatialBlock.setLocation(x-10, y+30);
			promptLabels[2].setBounds(rec.x-prompts[2].getIconWidth()-15,rec.y,prompts[2].getIconWidth(),prompts[2].getIconHeight());
			promptLabels[2].setVisible(true);
			add(promptLabels[2]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardSillySophon.setLocation(x, y-30);
			btnCardWholeBlock.setLocation(x, y);
			btnCardPatialBlock.setLocation(x, y+30);
			promptLabels[2].setVisible(false);
		}
	}
	/**
	 * 局部黑域
	 * @author user
	 *
	 */
	class CardPatialBlockListener implements MouseListener {
		int x = btnCardPatialBlock.getX();
		int y = btnCardPatialBlock.getY();
		Rectangle rec = btnCardPatialBlock.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (gameDTO.getUser().getUnavailableCards().contains(PartialBlock.class)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("PartialBlock")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");	
				return;
			}
			if (user.getResource() < cardRsrList.get("PartialBlock")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			JFrame patialBlock = new PatialBlockFrame();
			JPanel block = new PatialBlockPanel(patialBlock);
			patialBlock.setContentPane(block);
			initPatialBlock();
		}
		private void initPatialBlock() {
			
			
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardWholeBlock.setLocation(x-10, y-30);
			btnCardPatialBlock.setLocation(x-40, y);
			btnCardNoBroadcasting.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardWholeBlock.setLocation(x-10, y-30);
			btnCardPatialBlock.setLocation(x-40, y);
			btnCardNoBroadcasting.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardWholeBlock.setLocation(x-10, y-30);
			btnCardPatialBlock.setLocation(x-40, y);
			btnCardNoBroadcasting.setLocation(x-10, y+30);
			promptLabels[3].setBounds(rec.x-prompts[3].getIconWidth()-15,rec.y,prompts[3].getIconWidth(),prompts[3].getIconHeight());
			promptLabels[3].setVisible(true);
			add(promptLabels[3]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardWholeBlock.setLocation(x, y-30);
			btnCardPatialBlock.setLocation(x, y);
			btnCardNoBroadcasting.setLocation(x, y+30);
			promptLabels[3].setVisible(false);
		}
	}
	/**
	 * 电波干扰（禁广播）
	 * @author user
	 *
	 */
	class CardNoBroadcastingListener implements MouseListener {
		int x = btnCardNoBroadcasting.getX();
		int y = btnCardNoBroadcasting.getY();
		Rectangle rec = btnCardNoBroadcasting.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (gameDTO.getUser().getUnavailableCards().contains(NoBroadcasting.class)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("NoBroadcasting")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");
				return;
			}
			if (user.getResource() < cardRsrList.get("NoBroadcasting")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			FrameUtil.sendMessageByPullDown(GamePanel.this, "干扰成功！");
			JFrame iframe = new InformFrame("电波干扰", 300, 200);
			JPanel selectEnemyPanel = new SelectEnemyPanel(iframe,"选择要干扰的敌人");
			iframe.add(selectEnemyPanel);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardPatialBlock.setLocation(x-10, y-30);
			btnCardNoBroadcasting.setLocation(x-40, y);
			btnCardTechPotion.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardPatialBlock.setLocation(x-10, y-30);
			btnCardNoBroadcasting.setLocation(x-40, y);
			btnCardTechPotion.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardPatialBlock.setLocation(x-10, y-30);
			btnCardNoBroadcasting.setLocation(x-40, y);
			btnCardTechPotion.setLocation(x-10, y+30);
			promptLabels[4].setBounds(rec.x-prompts[4].getIconWidth()-15,rec.y,prompts[4].getIconWidth(),prompts[4].getIconHeight());
			promptLabels[4].setVisible(true);
			add(promptLabels[4]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardPatialBlock.setLocation(x, y-30);
			btnCardNoBroadcasting.setLocation(x, y);
			btnCardTechPotion.setLocation(x, y+30);
			promptLabels[4].setVisible(false);
		}
	}
	/**
	 * 科技爆发
	 * @author Ann
	 *
	 */
	class CardTechPotionListener implements MouseListener {
		int x = btnCardTechPotion.getX();
		int y = btnCardTechPotion.getY();
		Rectangle rec = btnCardTechPotion.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (!gameDTO.getUser().isPrivilegeTechnology()){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (user.getTechPoint() < cardTechList.get("TechPotion")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "科技不足！");	
				return;
			}
			if (user.getResource() < cardRsrList.get("TechPotion")){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "资源不足！");
				return;
			}
			FrameUtil.sendMessageByPullDown(GamePanel.this, "科技革命！");
			useTechPotion();
		}
		private void useTechPotion() {
			
			String id=user.getAccount().getId();
			TechPotion tp = new TechPotion(id, id);
			CardUse cardUseTp=new CardUse(id, id, tp);
			gameControl.doOperation(cardUseTp);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardNoBroadcasting.setLocation(x-10, y-30);
			btnCardTechPotion.setLocation(x-40, y);
			btnCardResourcePotion.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardNoBroadcasting.setLocation(x-10, y-30);
			btnCardTechPotion.setLocation(x-40, y);
			btnCardResourcePotion.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardNoBroadcasting.setLocation(x-10, y-30);
			btnCardTechPotion.setLocation(x-40, y);
			btnCardResourcePotion.setLocation(x-10, y+30);
			promptLabels[5].setBounds(rec.x-prompts[5].getIconWidth()-15,rec.y,prompts[5].getIconWidth(),prompts[5].getIconHeight());
			promptLabels[5].setVisible(true);
			add(promptLabels[5]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardNoBroadcasting.setLocation(x, y-30);
			btnCardTechPotion.setLocation(x, y);
			btnCardResourcePotion.setLocation(x, y+30);
			promptLabels[5].setVisible(false);
		}
	}
	/**
	 * 资源爆发
	 * @author user
	 *
	 */
	class CardResourcePotionListener implements MouseListener {
		Rectangle rec = btnCardResourcePotion.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (!gameDTO.getUser().isPrivilegeResource()){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			FrameUtil.sendMessageByPullDown(GamePanel.this, "资源爆发！");
			String id = AccountDTO.getInstance().getId();
			Card resourcePotion = new ResourcePotion(id, null);
			Operation cardUse = new CardUse(id,null,resourcePotion);
			GameControl.getInstance().doOperation(cardUse);
			repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardTechPotion.setLocation(rec.x-10, rec.y-30);
			btnCardResourcePotion.setLocation(rec.x-40, rec.y);
			btnCardResourceGambling.setLocation(rec.x-10, rec.y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardTechPotion.setLocation(rec.x-10, rec.y-30);
			btnCardResourcePotion.setLocation(rec.x-40, rec.y);
			btnCardResourceGambling.setLocation(rec.x-10, rec.y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardTechPotion.setLocation(rec.x-10, rec.y-30);
			btnCardResourcePotion.setLocation(rec.x-40, rec.y);
			btnCardResourceGambling.setLocation(rec.x-10,rec. y+30);
			promptLabels[6].setBounds(rec.x-prompts[6].getIconWidth()-15,rec.y,prompts[6].getIconWidth(),prompts[6].getIconHeight());
			promptLabels[6].setVisible(true);
			add(promptLabels[6]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardTechPotion.setLocation(rec.x, rec.y-30);
			btnCardResourcePotion.setLocation(rec.x, rec.y);
			btnCardResourceGambling.setLocation(rec.x, rec.y+30);
			promptLabels[6].setVisible(false);
		}
	}
	/**
	 * 赌博
	 * @author Ann
	 *
	 */
	class CardResourceGamblingListener implements MouseListener {
		int x = btnCardResourceGambling.getX();
		int y = btnCardResourceGambling.getY();
		Rectangle rec = btnCardResourceGambling.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if (!gameDTO.getUser().isPrivilegeGamble()){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			if (isMyTurn) {
				JFrame iframe = new InformFrame("资源赌博", 300, 200);
				JPanel gamblePanel = new GamblePanel(GamePanel.this,iframe,"输入要赌博的资源");
				iframe.add(gamblePanel);
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardResourcePotion.setLocation(x-10, y-30);
			btnCardResourceGambling.setLocation(x-40, y);
			btnPriviledgeGetRole.setLocation(x-10, y+30);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardResourcePotion.setLocation(x-10, y-30);
			btnCardResourceGambling.setLocation(x-40, y);
			btnPriviledgeGetRole.setLocation(x-10, y+30);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardResourcePotion.setLocation(x-10, y-30);
			btnCardResourceGambling.setLocation(x-40, y);
			btnPriviledgeGetRole.setLocation(x-10, y+30);
			promptLabels[7].setBounds(rec.x-prompts[7].getIconWidth()-15,rec.y,prompts[7].getIconWidth(),prompts[7].getIconHeight());
			promptLabels[7].setVisible(true);
			add(promptLabels[7]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardResourcePotion.setLocation(x, y-30);
			btnCardResourceGambling.setLocation(x, y);
			btnPriviledgeGetRole.setLocation(x, y+30);
			promptLabels[7].setVisible(false);
		}
	}
	/**
	 * 特权
	 * @author user
	 *
	 */
	class PriviledgeGetRoleListener implements MouseListener {
		int x = btnPriviledgeGetRole.getX();
		int y = btnPriviledgeGetRole.getY();
		Rectangle rec = btnPriviledgeGetRole.getBounds();
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isMyTurn) {
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			if(gameDTO.getPlayers().size() == 3){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "三人局不支持身份查找");
				return;
			}
			if (!gameDTO.getUser().isPrivilegeGetRole()){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "已经使用过这个卡片！");
				return;
			}
			JFrame iframe = new InformFrame("特权_身份探知", 300, 200);
			JPanel selectEnemyPanel = new SelectEnemyPanel(iframe,"选择要探知的敌人");
			iframe.add(selectEnemyPanel);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			btnCardResourceGambling.setLocation(x-10, y-30);
			btnPriviledgeGetRole.setLocation(x-40, y);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			btnCardResourceGambling.setLocation(x-10, y-30);
			btnPriviledgeGetRole.setLocation(x-40, y);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			btnCardResourceGambling.setLocation(x-10, y-30);
			btnPriviledgeGetRole.setLocation(x-40, y);
			promptLabels[8].setBounds(rec.x-prompts[8].getIconWidth()-15,rec.y,prompts[8].getIconWidth(),prompts[8].getIconHeight());
			promptLabels[8].setVisible(true);
			add(promptLabels[8]);
			repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnCardResourceGambling.setLocation(x, y-30);
			btnPriviledgeGetRole.setLocation(x, y);
			promptLabels[8].setVisible(false);
		}
	}
	
	class BroadcastListener extends MouseAdapter  {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(panelBroadcast.isVisible()){
				panelBroadcast.setVisible(false);
				GamePanel.this.repaint();
			}
			panelBroadcast.setVisible(true);
			panelMessage.setVisible(false);
			panelHistory.setVisible(false);
		}
	}
	
	class HistoryListener extends MouseAdapter  {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(panelHistory.isVisible()){
				panelHistory.setVisible(false);
				GamePanel.this.repaint();
			}
			panelMessage.setVisible(false);
			panelBroadcast.setVisible(false);
			panelHistory.setVisible(true);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			repaint();
		}
	}
	class MessageListener extends MouseAdapter  {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(panelMessage.isVisible()){
				panelMessage.setVisible(false);
				GamePanel.this.repaint();
			}
			panelBroadcast.setVisible(false);
			panelHistory.setVisible(false);
			panelMessage.setVisible(true);
		}
	}
	
	class EnemyListener extends MouseAdapter {
		int number;
		public EnemyListener(int number) {
			this.number=number;
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			coordinateShow(number);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			coordinateOfEnemies[number].setVisible(false);
		}
	}
	
	class EndListener implements MouseListener{
		@Override
		public void mouseReleased(MouseEvent e) {
			if(!(GameDTO.getInstance().getWhoseTurn()==user)){
				FrameUtil.sendMessageByPullDown(GamePanel.this, "不是您的回合");
				return;
			}
			GameControl.getInstance().turnChange();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			btnTurnEnd.setIcon(new ImageIcon("images/turnEnd2.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			btnTurnEnd.setIcon(new ImageIcon("images/turnEnd.png"));
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}
	}

	/*
     * 被人广播时播放的特效
     */
    public void boom(){
        
    }
    
    /*
     * 被三体侵占时播放的特效
     */
    public void conquer(){
        
    }

    /*
     * getters and setters
     */
    public JPanel getCountdownPanel(){
    	return this.panelCountDown;
    }
}
