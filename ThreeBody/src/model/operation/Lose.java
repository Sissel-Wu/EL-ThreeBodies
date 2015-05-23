package model.operation;

import java.util.LinkedList;
import java.util.List;

import model.Player;
import dto.GameDTO;

public class Lose extends Operation implements Operable{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	Player player;
	
	public Lose(String operator, String receiver,Player player) {
		super(operator, receiver);
		this.player=player;
	}

	@Override
	public List<Operation> process() {
		player.setLost(true);
		
		// 判断游戏是否结束
		GameDTO gameDTO = GameDTO.getInstance();
		List<Player> players = gameDTO.getPlayers();
		
		// 判断地球方和归一者方哪边赢
		
		// 只剩下地球
		boolean enemyleft = false;
		for (Player player : players) {
			if(player.getRole().toString() != "地球" && !player.isLost()){
				enemyleft = true;
			}
		}
		if(!enemyleft){
			List<Operation> sub = new LinkedList<Operation>();
			sub.add(new GameOver(operator, receiver));
			return sub;
		}
		// 只剩下归一者
		boolean unifierWin = true;
		for (Player player:players){
			if(player.getRole().toString() != "归一者" && !player.isLost()){
				unifierWin = false;
				break;
			}
		}
		if(unifierWin){
			List<Operation> sub = new LinkedList<Operation>();
			sub.add(new GameOver(operator, receiver));
			return sub;
		}
		// 只剩下三体
		boolean tbLeft = true;
		for (Player player:players){
			if(player.getRole().toString() != "三体" && !player.isLost()){
				tbLeft = false;
				break;
			}
		}
		if(tbLeft){
			List<Operation> sub = new LinkedList<Operation>();
			sub.add(new GameOver(operator, receiver));
			return sub;
		}
		
		return null;
	}
	
	public String toOperator(){
		return this.player.getAccount().getId()+"已阵亡！";
	}
	
	public String toReceiver(){
		return this.player.getAccount().getId()+"已阵亡！";
	}

	public String toOthers(){
		return this.player.getAccount().getId()+"已阵亡！";
	}
}
