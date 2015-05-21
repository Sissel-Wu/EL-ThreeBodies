package model.operation;

import java.util.LinkedList;
import java.util.List;

import model.Coordinate;
import model.Player;
import dto.GameDTO;

public class Broadcast extends Operation implements Operable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	Coordinate coordinate;
	
	public Broadcast(String operator, String receiver,Coordinate coordinate) {
		super(operator, receiver);
		this.coordinate=coordinate;
	}

	@Override
	public List<Operation> process() {
		GameDTO dto = GameDTO.getInstance();
		
		// setUsed
		Player pOperator = dto.findPlayerByID(operator);
		pOperator.setBroadcastable(false);
		
		// 如果某个坐标与广播的坐标相同
		// 1.该玩家本来就输了
		// 2.该坐标对应玩家输
		List<Operation> subOperations = null;
		
		for (Player player : dto.getPlayers()) {
			if (player.getCoordinate().equals(coordinate) && !player.isLost()) {
				Lose lose = new Lose(null, null, player);
				subOperations = new LinkedList<Operation>();
				subOperations.add(lose);
			}
		}
		return subOperations;
	}
	
	public String toOperator(){
		return this.operator+"发布了广播:"+coordinate.toString();
	}
	
	public String toReceiver (){
		return "有人发了广播:"+coordinate.toString();
	}
	
	public String toOthers(){
		return "有人发了广播:"+coordinate.toString();
	}

}
