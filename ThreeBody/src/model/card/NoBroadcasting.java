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

/*
 * 对某个玩家禁一轮广播坐标功能
 */
public class NoBroadcasting extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public NoBroadcasting(String operator, String receiver) {
		super(operator, receiver);
		
		GameConfig gc = new GameConfig();
		List<CardConfig> cardList = gc.getCardsConfig();
		this.lifetime = cardList.get(0).getLifetime();
		this.requiredResource = cardList.get(0).getRequiredResource();
		this.requiredTechPoint = cardList.get(0).getRequiredTechPoint();
		this.name = "电波干扰";
	}

	@Override
	public void process(List<Operation> subOperations) {

		GameDTO dto = GameDTO.getInstance();

		// get the receiver
		Player pReceiver = this.findReceiver(dto);
		Player pOperator = this.findOperator(dto);
		
		// setUsed
		pOperator.getUsedCards().add(this.getClass());

		// setDuring
		pOperator.getDurableCards().put(this, this.lifetime);

		pOperator.refreshCardUnavailable();

		// pay the resource
		ResourceChange rc = new ResourceChange(operator, receiver,
				Type.DECREASE, this.requiredResource);
		subOperations.add(rc);

		// 将receiver的broadcast设为false，在lifetime轮中
		pReceiver.setBroadcastable(false);
		
		// description
		Description description = new Description(operator, receiver, operator+"对"+receiver+"进行电波干扰");
		subOperations.add(description);
		
	}

	@Override
	public void after(List<Operation> subOperations) {
		// 不用改成true,重新轮到他的回合就是别人改好的
		
		// description
		Description description = new Description(operator, receiver, operator
				+ "对" + receiver + "的电波干扰效果结束");
		subOperations.add(description);
	}
	
}
