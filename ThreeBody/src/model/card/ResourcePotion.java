package model.card;

import java.util.List;

import model.Player;
import model.operation.Operation;
import model.operation.ResourceChange;
import model.operation.TechChange;
import config.CardConfig;
import config.GameConfig;
import dto.GameDTO;

/*
 *资源药水 
 *通过消耗科技来增长资源
 */
public class ResourcePotion extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public ResourcePotion(String operator, String receiver) {
		super(operator, receiver);

		GameConfig gc = new GameConfig();
		List<CardConfig> cardList = gc.getCardsConfig();
		this.lifetime = cardList.get(2).getLifetime();
		this.requiredResource = cardList.get(2).getRequiredResource();
		this.requiredTechPoint = cardList.get(2).getRequiredTechPoint();

		this.name = "资源爆发";
	}

	@Override
	public void process(List<Operation> subOperations) {
		Player pOperator = this.findOperator(GameDTO.getInstance());

		// get resources
		ResourceChange rc = new ResourceChange(operator, receiver,
				ResourceChange.Type.INCREASE, (int)(pOperator.getResource()*0.5));
		subOperations.add(rc);

		// description is not needed

		// setDuring
		pOperator.getDurableCards().put(this, this.lifetime);

		// setUsed
		pOperator.setPrivilegeResource(false);

		pOperator.refreshCardUnavailable();
	}

	@Override
	public void after(List<Operation> subOperations) {
		Player pOperator = GameDTO.getInstance().findPlayerByID(operator);

		// pay techPoint
		TechChange tc = new TechChange(operator, receiver,
				TechChange.Type.DECREASE, pOperator.getRole().getTchDevelopSpeed());
		subOperations.add(tc);
	}

}
