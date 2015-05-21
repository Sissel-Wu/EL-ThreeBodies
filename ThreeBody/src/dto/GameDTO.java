package dto;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import model.Information;
import model.Player;
import model.operation.Operation;

public class GameDTO {
	
	/*
	 * singleton
	 */
	private static GameDTO dto;
    
	/*
	 * 所有玩家
	 */
    private List<Player> players;
    
    /*
     * 该回合的玩家
     */
    private Player whoseTurn;
    
    /*
     * 回合数，每人完成一次操作即为增加一个回合
     */
    private int bout;
    
    /*
     * 倒计时
     */
    private int countdowns;
 
    /*
     * 本地的玩家
     */
    private Player user;
    /*
     * 历史消息记录
     */
    private Queue<Information> informations;
    /*
     * 历史操作记录
     */
    private Queue<Operation> historyOperations;
    /*
     * 待同步操作
     */
    private Queue<Operation> unSyncOperations;
    /*
     * 游戏是否结束
     */
    private boolean gameOver;
    
    public static void setUp(List<Player> players){
    	dto = new GameDTO(players);
    }
    
    private GameDTO(List<Player> players){
    	this.players = players;
    	// 找USER
    	for(Player player:players){
    		if(player.getAccount().getId().equals(AccountDTO.getInstance().getId())){
    			user = player;
    		}
    	}
    	informations = new ConcurrentLinkedQueue<Information>();
    	historyOperations = new ConcurrentLinkedQueue<Operation>();
    	unSyncOperations = new ConcurrentLinkedQueue<Operation>();
    	bout = 0;
    	whoseTurn = this.players.get(0);
    	gameOver = false;
    }
    
    public void init(){
    	for (Player player : this.players) {
			player.initFoundCoordinates();
		}
    }
    
    public static GameDTO getInstance(){
    	return dto;
    }
    
    public synchronized void depositHistoryOperation(Operation operation){
    	this.historyOperations.add(operation);
    }
    
    public synchronized void depositInformation(Information br){
    	this.informations.add(br);
    	System.out.println("now size+"+informations.size());
    }
    
    public synchronized void depositUnSyncOperation(Operation operation){
    	this.unSyncOperations.add(operation);
    }
    
    public synchronized void setSynced(){
    	this.unSyncOperations.clear();
    }

    /*
     * getters and setters
     */
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player findPlayerByID(String id){
		Player result = null;
		for (Player player : getPlayers()) {
			if (player.getAccount().getId().equals(id)) {
				result = player;
			}
		}
		return result;
	}
	
	public synchronized int getBout() {
		return bout;
	}

	public synchronized void setBout(int bout) {
		this.bout = bout;
	}

	public Player getUser() {
		return user;
	}

	public synchronized String[] getInformations(){
		String[] infos = new String[informations.size()];
		int i = 0;
		for (Information information : informations) {
			infos[i] = information.getContent();
			i++;
		}
		return infos;
	}

	public synchronized Queue<Operation> getHistoryOperations() {
		return historyOperations;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public synchronized Player getWhoseTurn() {
		return whoseTurn;
	}

	public synchronized void setWhoseTurn(Player whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	public synchronized Queue<Operation> getUnSyncOperations() {
		return unSyncOperations;
	}

	public int getCountdowns() {
		return countdowns;
	}

	public void setCountdowns(int countdowns) {
		this.countdowns = countdowns;
	}

}
