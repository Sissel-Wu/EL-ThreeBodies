package model.card;

import java.util.List;

import model.Coordinate;
import model.Player;
import model.operation.CoordinateGet;
import model.operation.CoordinateGetFail;
import model.operation.Description;
import model.operation.Operation;
import model.operation.ResourceChange;
import config.CardConfig;
import config.GameConfig;
import dto.GameDTO;

/**
 * 
 * @author Sissel 高级智子
 */
public class Sophon extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 坐标的第几个
	 */
	private int position;

	/**
	 * 
	 * @param operator
	 * @param receiver
	 * @param position
	 *            想要获取的玩家的第几个坐标
	 * @throws Exception
	 */
	public Sophon(String operator, String receiver, int position) {
		super(operator, receiver);
		// TODO 游戏平衡配置
		this.position = position;
		this.name = "三体智子";
		GameConfig gc = new GameConfig();
		List<CardConfig> cardList = gc.getCardsConfig();
		this.lifetime = cardList.get(4).getLifetime();
		this.requiredResource = cardList.get(4).getRequiredResource();
		this.requiredTechPoint = cardList.get(4).getRequiredTechPoint();
	}

	@Override
	public void process(List<Operation> subOperations) {

		GameDTO dto = GameDTO.getInstance();

		// 得到操作者与被操作者
		Player pOperator = this.findOperator(dto);
		Player pReceiver = this.findReceiver(dto);
		
		// setUsed
		pOperator.getUsedCards().add(this.getClass());

		pOperator.refreshCardUnavailable();

		// 消耗相应的资源，通过放置新Operation来实现
		ResourceChange rc = new ResourceChange(operator, receiver,
				ResourceChange.Type.DECREASE, this.requiredResource);
		subOperations.add(rc);
		
		// 描述
		Description dsc = new Description(operator, receiver, "观测了"+receiver+"的第"+(position+1)+"个坐标");
		subOperations.add(dsc);

		// 执行获取坐标操作
		Coordinate coordinate = pReceiver.getCoordinate();
		int result = coordinate.probeCoordinateElement(position);
		
		// 反馈
		if (result == Coordinate.PROTECTED) {
			CoordinateGetFail cgf = new CoordinateGetFail(operator, receiver);
			subOperations.add(cgf);
		} else {
			pOperator.findCoordinate(pReceiver, position, result);
			CoordinateGet cg = new CoordinateGet(operator, receiver, position, result);
			subOperations.add(cg);
		}
		
	}

}
