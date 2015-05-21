package ui.login;

import javax.swing.JFrame;

import ui.FrameUtil;

public class LoginFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public LoginFrame(){
		//设置标题
		this.setTitle("登录");
    	//设置窗口大小
    	this.setSize(400,300);
    	//不允许用户改变窗口大小
    	this.setResizable(false);
    	//窗口居中
    	FrameUtil.setFrameCenter(this);
    	//默认窗口为显示
    	this.setVisible(true);
    	
	}
}
