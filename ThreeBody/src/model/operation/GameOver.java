package model.operation;

import java.util.List;

import control.GameControl;
import model.Player;
import dto.GameDTO;

public class GameOver extends Operation implements Operable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	public GameOver(String operator, String receiver) {
		super(operator, receiver);
	}

	@Override
	public List<Operation> process() {
		GameControl.getInstance().gameOver();
		return null;
	}
	
	@Override
	public String toOperator() {
		return generateMessage();
	}

	@Override
	public String toReceiver() {
		return generateMessage();
	}

	public String toOthers(){
		return generateMessage();
	}
	
	private String generateMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("游戏结束  存活方:");
		GameDTO gameDTO = GameDTO.getInstance();
		List<Player> players = gameDTO.getPlayers();
		for(Player player:players){
			if(!player.isLost()){
				sb.append(player.getRole().toString()+"-"+player.getAccount().getId()+" ");
			}
		}
		return sb.toString();
	}
	
}
