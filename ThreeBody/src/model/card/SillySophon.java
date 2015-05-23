package model.card;

import java.util.List;

import ui.FrameUtil;
import ui.game.GamePanel;
import config.CardConfig;
import config.GameConfig;
import model.Coordinate;
import model.Player;
import model.operation.CoordinateGet;
import model.operation.CoordinateGetFail;
import model.operation.Description;
import model.operation.Operation;
import model.operation.ResourceChange;
import dto.AccountDTO;
import dto.GameDTO;

public class SillySophon extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private int position;
	private int guess;
	private int[] set;

	public SillySophon(String operator, String receiver, int position, int[] set, int guess) {
		super(operator, receiver);
		this.position = position;
		this.guess = guess;
		this.set = set;

		GameConfig gc = new GameConfig();
		List<CardConfig> cardList = gc.getCardsConfig();
		this.lifetime = cardList.get(3).getLifetime();
		this.requiredResource = cardList.get(3).getRequiredResource();
		this.requiredTechPoint = cardList.get(3).getRequiredTechPoint();
		this.name = "人造智子";
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

		// 消耗资源
		ResourceChange rc = new ResourceChange(operator, receiver,
				ResourceChange.Type.DECREASE, this.getResource());
		subOperations.add(rc);

		// 执行获取坐标操作
		Coordinate coordinate = pReceiver.getCoordinate();
		int result = coordinate.probeCoordinateElement(position);
		
		// 或取失败后描述操作
		if(result == Coordinate.PROTECTED){
			subOperations.add(new CoordinateGetFail(operator, receiver));
			return;
		}
		
		// 或取成功后描述操作
		StringBuilder sb = new StringBuilder();
		sb.append(operator);
		sb.append("对"+receiver+"第"+(position+1)+"个坐标观测，发现可疑坐标:");
		for(int i:set){
			sb.append(i+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" ");
		sb.append(operator+"选择了"+guess);
		
		Description description = new Description(operator, receiver,sb.toString());
		subOperations.add(description);
		
		if(result == guess){
			if(operator.equals(AccountDTO.getInstance().getId())){
				FrameUtil.sendMessageByPullDown(GamePanel.instance, "坐标获取成功");
			}
			pOperator.findCoordinate(pReceiver, position, result);
			CoordinateGet cg = new CoordinateGet(operator, receiver, position, result);
			subOperations.add(cg);
		}else{
			if(operator.equals(AccountDTO.getInstance().getId())){
				FrameUtil.sendMessageByPullDown(GamePanel.instance, "坐标获取失败");
			}
			CoordinateGetFail cgf = new CoordinateGetFail(operator, receiver);
			subOperations.add(cgf);
		}
		
	}

}
