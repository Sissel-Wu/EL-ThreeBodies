package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.component.BackButton;
import ui.sound.Media;
import util.R;
import control.MainControl;

public class PreferencePanel extends JPanel{
	/*
	 * default
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnVolumnUp;
	private JButton btnVolumnDown;
	private JButton btnReturn;
	private JButton btnBGM1;
	private JButton btnBGM2;
	private JButton btnBGM3;
	private JButton btnBG1;
	private JButton btnBG2;
	private JLabel labelVolume;
	private MainControl mainControl;
	private float volume = Media.getBgmPlayer().getVolume();
	
	public PreferencePanel(MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.initComonent();
	}

	private void initComonent() {
		labelVolume = new JLabel();
		labelVolume.setBounds(440,200,160,30);
		labelVolume.setFont(new Font("宋体",Font.PLAIN,30));
		labelVolume.setForeground(R.color.LIGHT_YELLOW);
		labelVolume.setText(translateToDecimal(volume)+"");
		this.add(labelVolume);
		
		this.btnReturn = new BackButton(new ReturnListener());
		this.add(btnReturn);
		
		this.btnVolumnUp = new JButton("Up");
		this.btnVolumnUp.setContentAreaFilled(false);
		this.btnVolumnUp.setBounds(600,200,60,40);
		btnVolumnUp.setFont(new Font("宋体", Font.BOLD, 20));
		btnVolumnUp.setForeground(R.color.LIGHT_YELLOW);
		btnVolumnUp.addMouseListener(new VolumnUpListener());
		this.add(btnVolumnUp);
		
		this.btnVolumnDown = new JButton("Down");
		this.btnVolumnDown.setContentAreaFilled(false);
		this.btnVolumnDown.setBounds(360,200,80,40);
		btnVolumnDown.setFont(new Font("宋体", Font.BOLD, 20));
		btnVolumnDown.setForeground(R.color.LIGHT_YELLOW);
		btnVolumnDown.addMouseListener(new VolumnDownListener());
		this.add(btnVolumnDown);
	}
	
	private int translateToDecimal(float volume){
		if(volume > 0.9){
			return 9;
		}else if(volume > 0.49){
			return 8;
		}else if(volume > 0.24){
			return 7;
		}else if(volume > 0.123){
			return 6;
		}else if(volume > 0.060){
			return 5;
		}else if(volume > 0.030){
			return 4;
		}else if(volume > 0.014){
			return 3;
		}else if(volume > 0.007){
			return 2;
		}else if(volume > 0.003){
			return 1;
		}else{
			return 0;
		}
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
	
	class VolumnUpListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			

		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			volume*=2;
			if (volume==0.0f) {
				volume=(float)0.00390625;
			}
			volume=volume>=1.0f?1.0f:volume;
			labelVolume.setText(translateToDecimal(volume)+"");
			Media.getBgmPlayer().setVolume(volume);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	class VolumnDownListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			volume/=2;
			volume=volume<0.003f?0.0f:volume;
			labelVolume.setText(translateToDecimal(volume)+"");
			Media.getBgmPlayer().setVolume(volume);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
		
		Image img = new ImageIcon("images/下拉.png").getImage();
		g.drawImage(img, 0, 0, 260, 45, 0, 0, 625, 80, null);
	}
}
