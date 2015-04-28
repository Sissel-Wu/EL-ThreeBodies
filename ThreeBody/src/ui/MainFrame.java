package ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	//为了不飘黄线加的一行代码，不要感到奇怪
	private static final long serialVersionUID = 1L;
	
	public MainFrame(){
		//设置标题
		this.setTitle("Three Bodies");
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
	}

}

