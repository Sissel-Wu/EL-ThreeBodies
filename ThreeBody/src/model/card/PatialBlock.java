package model.card;

import model.Coordinate;
import model.Player;
import model.operation.ResourceChange;
import model.operation.ResourceChange.Type;
import dto.GameDTO;

public class PatialBlock extends Card{
	
	/*
	 * 首先要知道玩家想要保护第几个坐标
	 */

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private int lifetime=3;
	private int position;
	
	
	public int getLifetime() {
		return lifetime;
	}


	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}


	public PatialBlock(String operator, String receiver,int position) {
		super(operator, receiver);
		// TODO 
		this.requiredResource=30;
		this.requiredTechPoint=20;
		this.position=position;
	}


	@Override
	public void process() {
		GameDTO dto = GameDTO.getInstance();
		
		//get operator == receiver
		Player pOperator = this.findOperator(dto);
		Player pReceiver = pOperator;
		
		//pay the resources
		ResourceChange rc = new ResourceChange(operator, receiver, Type.DECREASE, this.requiredResource);
		dto.depositOperation(rc);
		
		//set the coordinate according to position
		pOperator.getCoordinate().setCoordinateElement(position, 10086);
		
	}
	
	
}
