package ui.block;

import javax.swing.JFrame;

import ui.FrameUtil;

public class PatialBlockFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public PatialBlockFrame(){
		//设置标题
		this.setTitle("部分黑域");
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
