package model.card;

import java.util.List;

import config.CardConfig;
import config.GameConfig;
import model.Coordinate;
import model.Player;
import model.operation.Description;
import model.operation.Operation;
import model.operation.ResourceChange;
import model.operation.ResourceChange.Type;
import dto.GameDTO;

public class WholeBlock extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public WholeBlock(String operator, String receiver) {
		super(operator, receiver);

		GameConfig gc = new GameConfig();
		List<CardConfig> cardList = gc.getCardsConfig();
		this.lifetime = cardList.get(6).getLifetime();
		this.requiredResource = cardList.get(6).getRequiredResource();
		this.requiredTechPoint = cardList.get(6).getRequiredTechPoint();

		this.name = "全局黑域";
	}

	/*
	 * 将玩家的所有坐标全部设为PROTECTED
	 */

	@Override
	public void process(List<Operation> subOperations) {

		GameDTO dto = GameDTO.getInstance();

		// get the operator, now operator==receiver
		Player pOperator = this.findOperator(dto);

		// pay the resources
		ResourceChange rc = new ResourceChange(operator, receiver,
				Type.DECREASE, this.requiredResource);
		subOperations.add(rc);

		// set the protected at position i true
		for (int i = 0; i < Coordinate.DIMENSIONS; i++) {
			pOperator.getCoordinate().setProtected(i, true);
		}

		// description
		Description description = new Description(operator, receiver, operator + "的坐标获得一轮保护");
		subOperations.add(description);
		
		// setUsed
		pOperator.getUsedCards().add(this.getClass());
		
		// setDuring
		pOperator.getDurableCards().put(this, this.getLifetime());
		
		pOperator.refreshCardUnavailable();

	}

	@Override
	public void after(List<Operation> subOperations) {
		GameDTO dto = GameDTO.getInstance();

		// get operator == receiver
		Player pOperator = this.findOperator(dto);
		
		// set all unprotected
		for (int i = 0; i < Coordinate.DIMENSIONS; i++) {
			pOperator.getCoordinate().setProtected(i, false);
		}

		// description
		Description description = new Description(operator, receiver, operator
				+ "全局黑域保护终结");
		subOperations.add(description);
	}

}
