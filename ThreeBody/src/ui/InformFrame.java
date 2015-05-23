package ui;

import javax.swing.JFrame;
/**
 * 各种警告的JFrame,
 * 创建时
 * 通过传递JFrame的标题名来设定Frame的标题
 * 通过传递JFrame的大小来设定Frame的大小
 * @author Anko
 *
 */
public class InformFrame extends JFrame {
	 private static final long serialVersionUID = 1L;
		public InformFrame(String name,int width,int height){
			//设置标题
			this.setTitle(name);
	    	//设置窗口大小
	    	this.setSize(width,height);
	    	//不允许用户改变窗口大小
	    	this.setResizable(false);
	    	//窗口居中
	    	FrameUtil.setFrameCenter(this);
//	    	this.setContentPane(panel);
	    	//默认窗口为显示
	    	this.setVisible(true);
		}
}
