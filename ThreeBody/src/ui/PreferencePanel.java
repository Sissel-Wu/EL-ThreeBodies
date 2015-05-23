package ui;

import io.UserData;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.component.BackButton;
import ui.component.NormalLabel;
import ui.sound.Media;
import util.R;
import control.MainControl;
import dto.PreferenceDTO;

public class PreferencePanel extends JPanel {
	/*
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnVolumnUp;
	private JButton btnVolumnDown;
	private JButton bgmPrev;
	private JButton bgmNext;
	private JButton btnReturn;
	private JButton effectSwitch;
	private JButton autoLoginSwitch;
	
	private JLabel labelVolume;
	private JLabel labelBGM;
	
	private MainControl mainControl;
	private PreferenceDTO pdto;
	private float volume;

	public PreferencePanel(MainControl mainControl) {
		this.setLayout(null);
		this.mainControl = mainControl;
		this.pdto = PreferenceDTO.getInstance();
		this.volume = (float) pdto.getVolume();
		Media.getBgmPlayer().setVolume(volume);
		this.initComonent();
	}

	private void initComonent() {
		
		this.add(new NormalLabel("images/volume.png", 240, 110, 120, 60));
		this.add(new NormalLabel("images/music.png", 240, 210, 120, 60));
		this.add(new NormalLabel("images/特技.png", 240, 310, 120, 60));
		this.add(new NormalLabel("images/自动登录.png", 255, 410, 160, 60));
		
		labelVolume = new JLabel();
		labelVolume.setBounds(490, 115, 400, 50);
		labelVolume.setFont(new Font("Courier", Font.PLAIN, 30));
		labelVolume.setHorizontalAlignment(JLabel.CENTER);
		labelVolume.setForeground(R.color.LIGHT_YELLOW);
		labelVolume.setText(translateToDecimal(volume) + "");
		this.add(labelVolume);

		effectSwitch = new JButton();
		if(pdto.isEffectOn()){
			effectSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOn.png")
			.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
		}else{
			effectSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOff.png")
			.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
		}
		effectSwitch.setContentAreaFilled(false);
		effectSwitch.setBorderPainted(false);
		effectSwitch.setBounds(835, 325, 75, 25);
		effectSwitch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pdto.setEffectOn(!pdto.isEffectOn());
				if(pdto.isEffectOn()){
					effectSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOn.png")
					.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
				}else{
					effectSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOff.png")
					.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
				}
			}
		});
		this.add(effectSwitch);
		
		autoLoginSwitch = new JButton();
		if(pdto.isAutoLogin()){
			autoLoginSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOn.png")
			.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
		}
		if(! pdto.isAutoLogin()){
			autoLoginSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOff.png")
			.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
		}
		autoLoginSwitch.setContentAreaFilled(false);
		autoLoginSwitch.setBorderPainted(false);
		autoLoginSwitch.setFocusable(false);
		autoLoginSwitch.setBounds(835, 430, 75, 25);
		autoLoginSwitch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pdto.setAutoLogin(! pdto.isAutoLogin());
				if(pdto.isAutoLogin()){
					autoLoginSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOn.png")
					.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
				}
				if(! pdto.isAutoLogin()){
					UserData.clearAccount();
					autoLoginSwitch.setIcon(new ImageIcon(new ImageIcon("images/switchOff.png")
					.getImage().getScaledInstance(75, 25, Image.SCALE_SMOOTH)));
				}
			}
		});
		this.add(autoLoginSwitch);
		
		labelBGM = new JLabel();
		labelBGM.setBounds(490, 215, 400, 50);
		labelBGM.setHorizontalAlignment(JLabel.CENTER);
		labelBGM.setFont(new Font("Courier", Font.PLAIN, 30));
		labelBGM.setForeground(R.color.LIGHT_YELLOW);
		labelBGM.setText(pdto.getBgm());
		this.add(labelBGM);

		this.btnReturn = new BackButton(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				mainControl.toStartMenu(false);
				pdto.save();
			}
		});
		this.add(btnReturn);
		
		this.btnVolumnUp = new JButton("Up");
		this.btnVolumnUp.setContentAreaFilled(false);
		this.btnVolumnUp.setBounds(800,120,160,40);
		btnVolumnUp.setBorderPainted(false);
		btnVolumnUp.setFocusable(false);
		btnVolumnUp.setFont(new Font("宋体", Font.BOLD, 30));
		btnVolumnUp.setForeground(R.color.LIGHT_YELLOW);
		btnVolumnUp.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseReleased(MouseEvent e) {
				volume *= 2;
				if (volume == 0.0f) {
					volume = (float) 0.00390625;
				}
				volume = volume >= 1.0f ? 1.0f : volume;
				labelVolume.setText(translateToDecimal(volume) + "");
				Media.getBgmPlayer().setVolume(volume);
				pdto.setVolume(volume);
			}
		});
		this.add(btnVolumnUp);
		
		this.btnVolumnDown = new JButton("Down");
		this.btnVolumnDown.setContentAreaFilled(false);
		this.btnVolumnDown.setBounds(445,120,160,40);
		btnVolumnDown.setBorderPainted(false);
		btnVolumnDown.setFocusable(false);
		btnVolumnDown.setFont(new Font("宋体", Font.BOLD, 30));
		btnVolumnDown.setForeground(R.color.LIGHT_YELLOW);
		btnVolumnDown.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseReleased(MouseEvent e) {
				volume /= 2;
				volume = volume < 0.003f ? 0.0f : volume;
				labelVolume.setText(translateToDecimal(volume) + "");
				Media.getBgmPlayer().setVolume(volume);
				pdto.setVolume(volume);
			}
		});
		this.add(btnVolumnDown);
		
		this.bgmPrev = new JButton("Prev");
		this.bgmPrev.setContentAreaFilled(false);
		this.bgmPrev.setBounds(445,220,160,40);
		this.bgmPrev.setBorderPainted(false);
		this.bgmPrev.setFocusable(false);
		this.bgmPrev.setFont(new Font("宋体", Font.BOLD, 30));
		this.bgmPrev.setForeground(R.color.LIGHT_YELLOW);
		this.bgmPrev.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent arg0) {
				pdto.preBGM();
				Media.playBGM(pdto.getBgm());
				Media.getBgmPlayer().setVolume(volume);
				labelBGM.setText(pdto.getBgm());
			}
		});
		this.add(bgmPrev);
		
		this.bgmNext = new JButton("Next");
		this.bgmNext.setContentAreaFilled(false);
		this.bgmNext.setBounds(790,220,160,40);
		this.bgmNext.setBorderPainted(false);
		this.bgmNext.setFocusable(false);
		this.bgmNext.setFont(new Font("宋体", Font.BOLD, 30));
		this.bgmNext.setForeground(R.color.LIGHT_YELLOW);
		this.bgmNext.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent arg0) {
				pdto.nextBGM();
				Media.playBGM(pdto.getBgm());
				Media.getBgmPlayer().setVolume(volume);
				labelBGM.setText(pdto.getBgm());
			}
		});
		this.add(bgmNext);

	}

	private int translateToDecimal(float volume) {
		if (volume > 0.9) {
			return 9;
		} else if (volume > 0.49) {
			return 8;
		} else if (volume > 0.24) {
			return 7;
		} else if (volume > 0.123) {
			return 6;
		} else if (volume > 0.060) {
			return 5;
		} else if (volume > 0.030) {
			return 4;
		} else if (volume > 0.014) {
			return 3;
		} else if (volume > 0.007) {
			return 2;
		} else if (volume > 0.003) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Image IMG_MAIN = new ImageIcon("images/模糊背景.jpg").getImage();
		g.drawImage(IMG_MAIN, 0, 0, 1158, 650, null);
	}
}
