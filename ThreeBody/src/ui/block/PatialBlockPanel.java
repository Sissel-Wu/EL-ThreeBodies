package ui.block;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.FrameUtil;

public class PatialBlockPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnCoordinateOne;
	private JButton btnCoordinateTwo;
	private JButton btnCoordinateThree;
	private JButton btnCoordinateFour;
	private JButton btnReturn;
	private JFrame patialBlock;
	private int coordinate;
//	int coordinateOne;
//	int coordinateTwo;
//	int coordinateThree;
	
	public PatialBlockPanel(JFrame patialBlock) {
		this.setLayout(null);
		this.patialBlock=patialBlock;
		this.initComonent();
	}
	
	private void initComonent() {
coordinate=1;
		
		this.btnCoordinateOne = new JButton();
		this.btnCoordinateOne.setBounds(11, 16, 99, 60);
		btnCoordinateOne.setFont(new Font("黑体", Font.BOLD, 60));
		btnCoordinateOne.setContentAreaFilled(false);
		btnCoordinateOne.addMouseListener(new CoordinateOneListener());
		
		
		this.btnCoordinateTwo = new JButton();
		this.btnCoordinateTwo.setBounds(120, 16, 99, 60);
		btnCoordinateTwo.setFont(new Font("黑体", Font.BOLD, 60));
		btnCoordinateTwo.setContentAreaFilled(false);
		btnCoordinateTwo.addMouseListener(new CoordinateTwoListener());
		
		
		this.btnCoordinateThree = new JButton();
		this.btnCoordinateThree.setBounds(229, 16, 99, 60);
		btnCoordinateThree.setFont(new Font("黑体", Font.BOLD, 60));
		btnCoordinateThree.setContentAreaFilled(false);
		btnCoordinateThree.addMouseListener(new CoordinateThreeListener());
		
		
		this.btnCoordinateFour = new JButton();
		this.btnCoordinateFour.setBounds(339, 16, 99, 60);
		btnCoordinateFour.setFont(new Font("黑体", Font.BOLD, 60));
		btnCoordinateFour.setContentAreaFilled(false);
		btnCoordinateFour.addMouseListener(new CoordinateFourListener());
		
		this.add(btnCoordinateOne);
		this.add(btnCoordinateTwo);
		this.add(btnCoordinateThree);
		this.add(btnCoordinateFour);
		this.setPicture(coordinate);
		
		
		this.btnReturn = new JButton(new ImageIcon("exit.png"));
		this.btnReturn.setContentAreaFilled(false);
		this.btnReturn.setBounds(160, 105, 150, 60);
		this.btnReturn.setBorderPainted(false);
		btnReturn.addMouseListener(new ReturnListener());
		this.add(btnReturn);

		
	}
	
	class ReturnListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			patialBlock.setVisible(false);
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
	class CoordinateOneListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			coordinate=1;
			setPicture(coordinate);
			repaint();
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
	class CoordinateTwoListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			coordinate=2;
			setPicture(coordinate);
			repaint();
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
	class CoordinateThreeListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			coordinate=3;
			setPicture(coordinate);
			repaint();
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
	class CoordinateFourListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			coordinate=4;
			setPicture(coordinate);
			repaint();
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
	public void setPicture(int cooperate) {
		btnCoordinateOne.setIcon(new ImageIcon("coNothing.png"));
		btnCoordinateTwo.setIcon(new ImageIcon("coNothing.png"));
		btnCoordinateThree.setIcon(new ImageIcon("coNothing.png"));
		btnCoordinateFour.setIcon(new ImageIcon("coNothing.png"));
		if(cooperate==1){
			btnCoordinateOne.setIcon(new ImageIcon("select.png"));
		}
		if(cooperate==2){
			btnCoordinateTwo.setIcon(new ImageIcon("select.png"));
		}
		if(cooperate==3){
			btnCoordinateThree.setIcon(new ImageIcon("select.png"));
		}
		if(cooperate==4){
			btnCoordinateFour.setIcon(new ImageIcon("select.png"));
		}
	}
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
		FrameUtil.drawCoordinate(123,156,502,666,g);
		}
	
}


