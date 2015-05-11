package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MainControl;

public class LobbyPanel extends JPanel{
	private MainControl mc;
	private JButton lobby1;
	private JButton lobby2;
	private JButton lobby3;
	private JButton lobby4;
	private JButton lobby5;
	private JButton lobby6;
	private JButton createRoom;
	private JButton lobbyReturn;
	
	public LobbyPanel(MainControl mc){
		this.setLayout(null);
		this.initComonent();
		//this.ShowPeopleNum();
		this.mc = mc;
		
	}
	public void initComonent(){
	 //lobby room 3*2
		this.lobby1=new JButton();
	    this.lobby1.setIcon(new ImageIcon("下一页.png"));
	    this.lobby1.setContentAreaFilled(false);
	    this.lobby1.setBounds(50,100,300,125);
	    this.lobby1.setVisible(true);  
	    this.lobby1.addMouseListener(new EnterListener());
	    this.add(lobby1);
	    
	    
	    this.lobby2=new JButton();
	    this.lobby2.setIcon(new ImageIcon(""));
	    this.lobby2.setContentAreaFilled(false);
	  	this.lobby2.setBounds(400,100,300,125);
	  	this.lobby2.setVisible(true);  
	  	this.lobby2.addMouseListener(new EnterListener());
	    this.add(lobby2);
	    
	    this.lobby3=new JButton();
	    this.lobby3.setIcon(new ImageIcon(""));
	    this.lobby3.setContentAreaFilled(false);
	  	this.lobby3.setBounds(750,100,300,125);
	  	this.lobby3.setVisible(true);  
	  	this.lobby3.addMouseListener(new EnterListener());
	    this.add(lobby3);
	    
	    
	    this.lobby4=new JButton();
	    this.lobby4.setIcon(new ImageIcon(""));
	    this.lobby4.setContentAreaFilled(false);
	  	this.lobby4.setBounds(50,300,300,125);
	  	this.lobby4.setVisible(true);  
	  	this.lobby4.addMouseListener(new EnterListener());
	    this.add(lobby4);
	    
	    
	    this.lobby5=new JButton();
	    this.lobby5.setIcon(new ImageIcon(""));
	    this.lobby5.setContentAreaFilled(false);
	  	this.lobby5.setBounds(400,300,300,125);
	  	this.lobby5.setVisible(true);  
	  	this.lobby5.addMouseListener(new EnterListener());
	    this.add(lobby5);
	    
	    
	    
	    this.lobby6=new JButton();
	    this.lobby6.setIcon(new ImageIcon(""));
	    this.lobby6.setContentAreaFilled(false);
	  	this.lobby6.setBounds(750,300,300,125);
	  	this.lobby6.setVisible(true);  
	  	this.lobby6.addMouseListener(new EnterListener());
	    this.add(lobby6);
	  	  
	  	  
	    
	    this.createRoom=new JButton();
	    this.createRoom.setIcon(new ImageIcon(""));
	    this.createRoom.setContentAreaFilled(false);
	  	this.createRoom.setBounds(650,500,100,50);
	  	this.createRoom.setVisible(true);  
	  	this.createRoom.addMouseListener(new CreateRoomListener());
	    this.add(createRoom);
	    
	    
	    this.lobbyReturn=new JButton();
	    this.lobbyReturn.setIcon(new ImageIcon(""));
	    this.lobbyReturn.setContentAreaFilled(false);
	  	this.lobbyReturn.setBounds(850,500,100,50);
	  	this.lobbyReturn.setVisible(true);  
	  	this.lobbyReturn.addMouseListener(new ReturnListener());
	    this.add(lobbyReturn);
	}
	
	
	
	
//	public void ShowPeopleNum(){
//	
//		if(totalNum==4){
//        
//		}
//		if(totalNum==6)){
//			SixPerRoom();
//		}
//        if(totalNum==8)){
//			EightRoom();
//		}
//	}  
	
	public void FourPerRoom(){
		
	}
	
    public void SixPerRoom(){
		
	}
    public void EightPerRoom(){
	
    }
	
	
	public void paintComponent(Graphics g) {
		  Image background=new ImageIcon("模糊背景.jpg").getImage();
		  g.drawImage(background,0,0,null);
		}	
	
	class EnterListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			mc.toGame();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
class CreateRoomListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
}		
		
		
class ReturnListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		mc.toSelect();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
}
