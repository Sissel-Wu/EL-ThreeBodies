package model.operation;

import java.util.List;

import model.Player;
import dto.GameDTO;


public class SendMessage extends Operation implements Operable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg;

	public SendMessage(String operator, String receiver, String msg) {
		super(operator, receiver);
		this.msg = msg;
	}

	public String toOperator(){
		
		return this.operator+"向"+this.receiver+"发送了一条消息： "+msg;
		
	}
	
	public String toReceiver(){
		
		return this.operator+"向"+this.receiver+"发送了一条消息： "+msg;
	}

	@Override
	public List<Operation> process() {
		Player pOperator = GameDTO.getInstance().findPlayerByID(operator);
		pOperator.setMessageable(false);
		
		return null;
	}
	
}
