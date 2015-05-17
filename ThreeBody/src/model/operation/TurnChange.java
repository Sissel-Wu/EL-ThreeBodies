package model.operation;

import java.util.LinkedList;
import java.util.List;

import control.GameControl;
import model.Player;
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
		
		//1.得到现在玩家的序号
		int index = dto.getPlayers().indexOf(dto.getWhoseTurn());
		
		//2.得到玩家的数量
		int playerNum = dto.getPlayers().size();

		//3.将whoseTurn设为下一个没输的玩家
		for(int i = index+1 % playerNum;;i++){
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
		
		// 将现在的玩家的资源和科技改变
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
		
		// 如果刚好轮到我方，开启时钟线程
		if(dto.getUser() == dto.getWhoseTurn()){
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
