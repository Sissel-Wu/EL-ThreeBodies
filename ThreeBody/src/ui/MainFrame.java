package ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	//Ϊ�˲�Ʈ���߼ӵ�һ�д��룬��Ҫ�е����
	private static final long serialVersionUID = 1L;
	
	public MainFrame(){
		//���ñ���
		this.setTitle("Three Bodies");
    	//����Ĭ�Ϲر����ԣ����������
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//���ô��ڴ�С
    	this.setSize(1158, 650);
    	//�������û��ı䴰�ڴ�С
    	this.setResizable(false);
    	//���ھ���
    	FrameUtil.setFrameCenter(this);
//    	this.setContentPane(panel);
    	//Ĭ�ϴ���Ϊ��ʾ
    	this.setVisible(true);
	}

}

