package ui.login;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JButton btnlogin;
	private JFrame loginFrame;
	private JTextField idField;
	private JPasswordField passwordField;
	private JLabel idLabel;
	private JLabel passwordLabel;
	
	
	public LoginPanel(JFrame loginFrame) {
		this.setLayout(null);
		this.loginFrame=loginFrame;
		this.initComonent();
	}
	private void initComonent() {
		this.btnlogin = new JButton(new ImageIcon("login.png"));
		this.btnlogin.setBounds(160, 220, 80, 40);
		btnlogin.setFont(new Font("黑体", Font.BOLD, 60));
		btnlogin.setContentAreaFilled(false);
		btnlogin.addMouseListener(new LoginListener());
		this.add(btnlogin);
		
		idField = new JTextField();
		idField.setBounds(100,60,240,30);
		this.add(idField);
		
		passwordField =new JPasswordField();
		passwordField.setBounds(100,110,240,30);
		this.add(passwordField);
		
		idLabel = new JLabel();
		idLabel.setBounds(30,60,60,30);
		idLabel.setIcon(new ImageIcon("logid.png"));
		this.add(idLabel);
		
		passwordLabel = new JLabel();
		passwordLabel.setBounds(30,110,60,30);
		passwordLabel.setIcon(new ImageIcon("logpassword.png"));
		this.add(passwordLabel);
	}
	class LoginListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			loginFrame.setVisible(false);
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
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("img1.jpg").getImage();
		g.drawImage(img, 0, 0, null);
		}
}
