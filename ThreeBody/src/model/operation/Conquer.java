package model.operation;

import java.util.LinkedList;
import java.util.List;

import model.Coordinate;
import model.Player;
import model.role.Earth;
import dto.GameDTO;

public class Conquer extends Operation implements Operable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Coordinate coordinate;

	public Conquer(String operator, String receiver, Coordinate coordinate) {
		super(operator, receiver);
		this.coordinate = coordinate;
	}
	
	//TODO 如果对面不是地球怎么办
	@Override
	public List<Operation> process() {
		//如果某个坐标与广播的坐标相同
		//该坐标对应玩家就输了，通过设定域实现
		GameDTO dto = GameDTO.getInstance();
		List<Operation> subOperations = null;
		for(Player player:dto.getPlayers()){
			if(player.getCoordinate().equals(coordinate)){
				// 先判断该玩家是否已经退场
				subOperations = new LinkedList<Operation>();
				if(player.isLost() || !(player.getRole() instanceof Earth)){
					Description description = new Description(operator, receiver, "侵略失败");
					subOperations.add(description);
					return subOperations;
				}
				// 改变角色
				CharacterChange cc = new CharacterChange(operator, null);
				subOperations.add(cc);
				// 使某玩家失败
				Lose lose = new Lose(null,null,player);
				subOperations.add(lose);
			}
		}
		return subOperations;
	}
	
	public String toOperator(){
		return this.operator+"向坐标:"+coordinate.toString()+"发动了侵略";
	}

}
