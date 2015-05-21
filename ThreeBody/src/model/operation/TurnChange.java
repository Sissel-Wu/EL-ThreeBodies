package model.operation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import model.Player;
import model.card.Card;
import control.GameControl;
import dto.GameDTO;

public class TurnChange extends Operation implements Operable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public TurnChange(String operator, String receiver) {
		super(operator,receiver);
	}

	@Override
	public List<Operation> process() {
		
		GameDTO dto = GameDTO.getInstance();
		Player whoseTurn = null;
		List<Operation> subOperations = new LinkedList<Operation>();
		
		// 回合数增加
		int bout = dto.getBout()+1;
		dto.setBout(bout);
		
		// 将现在玩家的broadcastable和messageable设成true,因为别人的回合可以禁用
		// used设为空
		Player pOperator = dto.findPlayerByID(operator);
		pOperator.setMessageable(true);
		pOperator.setBroadcastable(true);
		pOperator.getUsedCards().clear();
		
		//1.得到现在玩家的序号
		int index = dto.getPlayers().indexOf(dto.getWhoseTurn());
		
		//2.得到玩家的数量
		int playerNum = dto.getPlayers().size();

		//3.将whoseTurn设为下一个没输的玩家
		for(int i = (index+1) % playerNum;;i++){
			if(i == playerNum){
				i = 0;
			}
			whoseTurn = dto.getPlayers().get(i);
			// 如果p没输就轮到p，否则轮到下一个
			if(!whoseTurn.isLost()){
				dto.setWhoseTurn(whoseTurn);
				break;
			}
		}
		
		// 将下一个玩家的资源和科技改变
		String id = whoseTurn.getAccount().getId();
		subOperations.add(new ResourceChange(
				id, 
				null, 
				ResourceChange.Type.INCREASE, 
				whoseTurn.getRole().getRsrRestoreSpeed()));
		subOperations.add(new TechChange(
				id, 
				null, 
				TechChange.Type.INCREASE, 
				whoseTurn.getRole().getTchDevelopSpeed()));
		
		// 处理下一个玩家的“持续型卡牌的”效果
		// !!!不能边迭代边删除元素，会发生ConcurrentModificationException
		Player nowPlayer = dto.getWhoseTurn();
		List<Card> unpredecated = new LinkedList<Card>();
		for (Entry<Card, Integer> entry : nowPlayer.getDurableCards().entrySet()) {
			nowPlayer.getDurableCards().put(entry.getKey(), entry.getValue() - 1);
			if (entry.getValue() == 0) {
				unpredecated.add(entry.getKey());
			} else {
				entry.getKey().processEachRound(subOperations);
			}
		}
		// 目测能解决ConcurrentModificationException
		for (Card card : unpredecated) {
			// 先remove再调用after，这关系到after的逻辑
			nowPlayer.getDurableCards().remove(card);
			card.after(subOperations);
		}
		
		// 刷新unavailable
		nowPlayer.refreshCardUnavailable();
		
		// 如果刚好轮到我方，开启时钟线程
		if(dto.getUser() == dto.getWhoseTurn()){
			// 倒计时线程
			GameControl.getInstance().startCountdown();
		}
		
		return subOperations;
	}
	
	public String toOperator(){
		if(GameDTO.getInstance().getBout() == 0){
			return null;
		}
		return operator+"回合结束";
	}
	
	public String toReceiver(){
		return null;
	}
	
	public String toOthers(){
		if(GameDTO.getInstance().getBout() == 0){
			return null;
		}
		return "回合变更";
	}

}
