package model.operation;

import model.Player;
import dto.GameDTO;

public class ResourceChange extends Operation implements Operable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Type{
		INCREASE,
		DECREASE
	}
	
	private Type type;
	private int amount;

	public ResourceChange(String operator,String receiver,Type type,int amount) {
		super(operator,receiver);
		this.type = type;
		this.amount = amount;
	}

	@Override
	public String toOperator() {
		return null;
	}

	@Override
	public String toReceiver() {
		return null;
	}

	@Override
	public String toOthers() {
		return null;
	}

	@Override
	public void process() {
		GameDTO dto = GameDTO.getInstance();
		Player pReceiver = null;
		// 找到对应的玩家
		for (Player player : dto.getPlayers()) {
			if(player.getAccount().getId().equals(this.receiver)){
				pReceiver = player;
			}
		}
		
		int change = 0;
		switch(type){
		case INCREASE:
			change = this.amount;
		case DECREASE:
			change = -this.amount;
		}
		
		int nowResource = pReceiver.getResource();
		pReceiver.setResource(nowResource+change);
	}

}
