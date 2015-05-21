package ui.component;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LengthDocument extends PlainDocument {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private int maxLength;

	public LengthDocument(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if (getLength() + str.length() > maxLength) {
			return;
		} else {
			super.insertString(offs, str, a);
		}
	}

}
