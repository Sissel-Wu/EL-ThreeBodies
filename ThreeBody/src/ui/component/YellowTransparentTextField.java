package ui.component;

import java.awt.Font;

import javax.swing.JTextField;

import util.R;

public class YellowTransparentTextField extends JTextField{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public YellowTransparentTextField(int maxLength) {
		super();
		this.setDocument(new LengthDocument(maxLength));
		this.setOpaque(false);
		this.setFont(new Font("宋体", Font.BOLD, 20));
		this.setForeground(R.color.LIGHT_YELLOW);
	}
	
}
