package model.card;

import java.util.List;

import model.Player;
import model.operation.Description;
import model.operation.Operation;
import model.operation.ResourceChange;
import model.operation.ResourceChange.Type;
import config.CardConfig;
import config.GameConfig;
import dto.GameDTO;

public class PartialBlock extends Card{
	
	/*
	 * 首先要知道玩家想要保护第几个坐标
	 */

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private  int position;
	
	public PartialBlock(String operator, String receiver,int position)  {
		super(operator, receiver);
		this.position=position;	
		
		GameConfig gc=new GameConfig();
		List<CardConfig> cardList=gc.getCardsConfig();
		this.lifetime=cardList.get(1).getLifetime();
		this.requiredResource=cardList.get(1).getRequiredResource();
		this.requiredTechPoint=cardList.get(1).getRequiredTechPoint();
		this.name = "局部黑域";
	}

	@Override
	public void process(List<Operation> subOperations) {
		GameDTO dto = GameDTO.getInstance();
		
		//get operator == receiver
		Player pOperator = this.findOperator(dto);
		
		//pay the resources
		ResourceChange rc = new ResourceChange(operator, receiver, Type.DECREASE, this.requiredResource);
		subOperations.add(rc);
		
		//set the coordinate according to position
		pOperator.getCoordinate().setProtected(position, true);
		
		// description
		Description description = new Description(operator, receiver, operator
				+ "对坐标" + position + "进行保护");
		subOperations.add(description);
		
		// setUsed
		pOperator.getUsedCards().add(this.getClass());
		
		// setDuring
		pOperator.getDurableCards().put(this, this.lifetime);
		
		pOperator.refreshCardUnavailable();
	}

	@Override
	public void after(List<Operation> subOperations) {
		GameDTO dto = GameDTO.getInstance();

		// get operator == receiver
		Player pOperator = this.findOperator(dto);
		
		// set the coordinate according to position
		pOperator.getCoordinate().setProtected(position, false);

		// description
		Description description = new Description(operator, receiver, operator
				+ "对坐标" + position + "保护终结");
		subOperations.add(description);
	}
	
}
