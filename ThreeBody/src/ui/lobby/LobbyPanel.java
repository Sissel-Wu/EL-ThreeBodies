package ui.lobby;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Room;
import ui.FrameUtil;
import ui.component.BackButton;
import control.LobbyControl;
import control.MainControl;

public class LobbyPanel extends JPanel implements MouseWheelListener {
	private static final long serialVersionUID = 1L;
	private MainControl mainControl;
	private JButton btn_createRoom;
	private JButton btn_lobbyReturn;

	private List<JButton> roomFamily = new ArrayList<JButton>();
	private List<Room> roomList;
	private List<Rectangle> locationSave = new ArrayList<Rectangle>();
	private LobbyControl lobbyControl;
	//双缓冲机制
	private Image iBuffer;
	private Graphics gBuffer;
	public LobbyPanel(MainControl mc) {
		this.setLayout(null);
		this.mainControl = mc;
		lobbyControl = mc.lobbyControl;
		this.initComponent();
		this.addMouseWheelListener(this);
		roomList = lobbyControl.getRooms();
		createRooms();
	}

	private void createRooms() {
		for (int i = 0; i < roomList.size(); i++) {
			addRoom(i);
		}
		for (int i = 0; i < roomFamily.size(); i++) {
			locationSave.add(roomFamily.get(i).getBounds());
		}
		for (int i = 0; i < roomFamily.size(); i++) {
			this.add(roomFamily.get(i));
		}
	}
	
	public void refresh(){
		roomList = lobbyControl.getRooms();
		roomFamily.clear();
		for (int i = 0; i < roomList.size(); i++) {
			addRoom(i);
		}
//		this.removeAll();
		for (Component cp:this.getComponents()){
			if(cp != btn_lobbyReturn && cp!= btn_createRoom){
				this.remove(cp);
			}
		}
		this.initComponent();
		locationSave.clear();
		for (int i = 0; i < roomFamily.size(); i++) {
			Rectangle rectNewi=roomFamily.get(i).getBounds();
			locationSave.add(rectNewi);
		}
		createRooms(locationSave);
		mainControl.frame.setContentPane(this);
	}
	
	private void createRooms(List<Rectangle> locationSave) {
		for (int i = 0; i < roomFamily.size(); i++) {
			roomFamily.get(i).setBounds(locationSave.get(i));
			roomFamily.get(i).setBorderPainted(false);
		}		
		for (int i = 0; i < roomFamily.size(); i++) {
			this.add(roomFamily.get(i));
		}
	}

	// 添加房间的按钮
	private void addRoom(int roomNumber) {
		JButton room = new JButton();
		JPanel roomPanel = new ButtonPanel(this.roomList.get(roomNumber));
		if (roomNumber != 0) {
			Rectangle rect = roomFamily.get(roomFamily.size() - 1).getBounds();
			rect.x = rect.x + 350;
			room.setBounds(rect);
			roomPanel.setBounds(rect);
		} else {
			room.setBounds(50, 200, 300, 125);
			roomPanel.setBounds(50, 200, 300, 125);
		}
		room.setContentAreaFilled(false);
		room.setBorderPainted(false);
		room.addMouseListener(new EnterListener(roomList.get(roomNumber).getName()));
		room.add(roomPanel);
		roomFamily.add(room);
	}
	
	public void initComponent() {
		// lobby room 3*2
		if(btn_createRoom == null){
//			this.btn_createRoom = new SquareButton("images/newroom.png");
			this.btn_createRoom = new JButton(new ImageIcon("images/newroom.png"));
			this.btn_createRoom.setContentAreaFilled(false);
			this.btn_createRoom.setBounds(825, 500, 110, 50);
			this.btn_createRoom.addMouseListener(new CreateRoomListener());
			btn_createRoom.setBorderPainted(false);
			this.add(btn_createRoom);
		}
		
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
	public void paintComponent(Graphics g) {
		Image background = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(background, 0, 0, null);
	}

	class EnterListener extends MouseAdapter  {
		String roomName;
		public EnterListener(String roomName) {
			this.roomName = roomName;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			FrameUtil.sendMessageByPullDown(LobbyPanel.this, "加载中");
			switch(lobbyControl.enterRoom(roomName)){
			case SUCCESS:
				lobbyControl.changeEntered();
				mainControl.toRoom(roomName);
				break;
			case ROOM_FULL:
				FrameUtil.sendMessageByPullDown(LobbyPanel.this, "房间已满");
				break;
			case NOT_EXISTED:
				FrameUtil.sendMessageByPullDown(LobbyPanel.this, "房间不存在");
				break;
			case INVALID:
				FrameUtil.sendMessageByPullDown(LobbyPanel.this, "房间游戏中");
			default:
				break;
			}
		}
	}

	class CreateRoomListener extends MouseAdapter  {
		@Override
		public void mouseReleased(MouseEvent e) {
			JFrame createRoomFrame = new CreateRoomFrame();
			JPanel createRoomPanel = new CreateRoomPanel(LobbyPanel.this, createRoomFrame,
					lobbyControl,mainControl);
			createRoomFrame.setContentPane(createRoomPanel);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			LobbyPanel.this.btn_createRoom.setBounds(825, 500, 100, 50);
			LobbyPanel.this.btn_createRoom.setIcon(new ImageIcon("images/newroom2.png"));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			LobbyPanel.this.btn_createRoom.setBounds(830, 500, 100, 50);
			LobbyPanel.this.btn_createRoom.setIcon(new ImageIcon("images/newroom.png"));
		}
		
	}

	class ReturnListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			lobbyControl.changeEntered();
			mainControl.toStartMenu();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() == 1) {
			// 往左跑
			for (int i = 0; i < roomFamily.size(); i++) {
				Rectangle rec = roomFamily.get(i).getBounds();
				rec.x = rec.x - 50;
				roomFamily.get(i).setBounds(rec);
				locationSave.remove(i);
				locationSave.add(i,rec);
			}
		}
		if (e.getWheelRotation() == -1) {
			// 往右跑
			for (int i = 0; i < roomFamily.size(); i++) {
				Rectangle rec = roomFamily.get(i).getBounds();
				rec.x = rec.x + 50;
				roomFamily.get(i).setBounds(rec);
				locationSave.remove(i);
				locationSave.add(i,rec);
			}
		}
	}
}
