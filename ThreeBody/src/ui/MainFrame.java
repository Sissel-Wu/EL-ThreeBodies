package ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import control.MainControl;

public class MainFrame extends JFrame {
	// default
	private static final long serialVersionUID = 1L;
	
	private MainControl mc ;
	
	public MainFrame(MainControl mc){
		//设置标题
		this.setTitle("Three Body");
    	//设置默认关闭属性（程序结束）
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//设置窗口大小
    	this.setSize(1158, 650);
    	//不允许用户改变窗口大小
    	this.setResizable(false);
    	//窗口居中
    	FrameUtil.setFrameCenter(this);
//    	this.setContentPane(panel);
    	//默认窗口为显示
    	this.setVisible(true);
    	
    	this.mc = mc;
    	
    	this.addWindowListener(new WindowListener(){
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				MainFrame.this.mc.exit();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
    	});
	}

}

