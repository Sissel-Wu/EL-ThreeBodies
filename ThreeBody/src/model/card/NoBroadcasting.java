package model.card;

import model.Player;
import model.operation.ResourceChange;
import model.operation.ResourceChange.Type;
import dto.GameDTO;

/*
 * 对某个玩家禁一轮广播坐标功能
 */
public class NoBroadcasting extends Card{	
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private int lifetime=1;
	
	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public NoBroadcasting(String operator, String receiver) {
		super(operator, receiver);
		this.requiredResource=40;
		this.requiredTechPoint=50;
	}

	@Override
	public void process() {
		
		GameDTO dto=GameDTO.getInstance();
		
		//get the  receiver
		Player pReceiver=this.findReceiver(dto);
		
		//pay the resource
		ResourceChange rc=new ResourceChange(operator, receiver, Type.DECREASE, this.requiredResource);
		dto.depositOperation(rc);
		
		//将receiver的broadcast设为false，在lifetime轮中
		pReceiver.setBroadcast(false);
		
	}
	
}
