package ui.sophon;

import javax.swing.JFrame;

import ui.FrameUtil;

public class SophonFinderFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public SophonFinderFrame(String name){
		//设置标题
		this.setTitle(name);
    	//设置窗口大小
    	this.setSize(450,200);
    	//不允许用户改变窗口大小
    	this.setResizable(false);
    	//窗口居中
    	FrameUtil.setFrameCenter(this);
//    	this.setContentPane(panel);
    	//默认窗口为显示
    	this.setVisible(true);
	}
}
