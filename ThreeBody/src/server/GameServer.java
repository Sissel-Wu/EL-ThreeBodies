package server;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.rmi.PortableRemoteObject;

import model.Account;
import model.Coordinate;
import model.Player;
import model.operation.Operation;
import model.role.Role;
import model.role.ThreeBody;
import model.role.Unifier;
import server.interfaces.RMIGame;
import util.R;
import util.R.info;
import util.RandomCombiner;

public class GameServer extends UnicastRemoteObject implements RMIGame{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,LinkedList<Operation>> unhandledOperations;
	private LinkedList<Player> players;
	private RoomServer rs;
	
	/*
	 * 离开游戏的人
	 */
	private Map<Account,Boolean> gameFinish;
	
	/*
	 * 游戏进行状态，1为进行中，0为结束，-1为有人强退
	 */
	private int gameState;

	protected GameServer(RoomServer rs,List<Account> accounts) throws RemoteException {
		super();
		
		this.rs = rs;
		
		// 初始化gameState
		gameState = 1;
		
		// 初始化unhandled
		unhandledOperations = new HashMap<String,LinkedList<Operation>>();
		for (Account account : accounts) {
			unhandledOperations.put(account.getId(), new LinkedList<Operation>());
		}
		
		// 初始化players，随机搭配
		players = new LinkedList<Player>();
		RandomCombiner rc = new RandomCombiner(accounts.size());
		rc.addColumn(accounts.toArray());
		
		// 角色数量分配
		switch(accounts.size()){
		case 3:
			rc.addColumn(Role.generateRoles(2, 1, 0));
			break;
		case 6:
			rc.addColumn(Role.generateRoles(3, 2, 1));
			break;
		case 8:
			rc.addColumn(Role.generateRoles(4, 3, 1));
			break;
		}
		rc.addColumn(Coordinate.generateCoordinates(accounts.size()));
		for (Object[] records : rc.generate()) {
			Account ac = (Account)records[0];
			Role rl = (Role)records[1];
			Coordinate cd = (Coordinate)records[2];
			players.add(new Player(ac,rl,cd,false));
		}
		
		// 初始化gameFinish
		gameFinish = new HashMap<Account, Boolean>();
		for (Account account : accounts) {
			gameFinish.put(account, false);
		}
		
		// 所有人游戏次数加1
		for (Account account : accounts) {
			account.setTotalGames(account.getTotalGames()+1);
		}
	}
	
	@Override
	public synchronized LinkedList<Operation> downloadOperation(String id) throws RemoteException {
		LinkedList<Operation> result = unhandledOperations.get(id);
		// 清空
		unhandledOperations.put(id, new LinkedList<Operation>());
		return result;
	}

	@Override
	public synchronized info uploadOperation(String id, List<Operation> unhandled) throws RemoteException {
		for (Entry<String, LinkedList<Operation>> entries : unhandledOperations.entrySet()) {
			if(!entries.getKey().equals(id)){
				entries.getValue().addAll(unhandled);
			}
		}
		return R.info.SUCCESS;
	}

	@Override
	public LinkedList<Player> getPlayers() throws RemoteException {
		return players;
	}

	@Override
	public info exitGame(Player player, boolean correct) throws RemoteException {
		if(gameState == -1 || gameState == 0){
			return R.info.NOT_EXISTED;
		}
		
		// 强退的情况
		Set<Account> accounts = gameFinish.keySet();
		if(!correct){
			gameState = -1;
			// 找到强退的，扣他100分 = =
			for (Account account : accounts) {
				if(account.getId().equals(player.getAccount().getId())){
					account.setPoint(account.getPoint()-100);
				}
				// 全员退出
				rs.exit(account.getId());
			}
			return R.info.INVALID;
		}
		// 正常退出
		for (Account account : accounts) {
			if(account.getId().equals(player.getAccount().getId())){
				gameFinish.put(account, true);
				rs.exit(account.getId());
				// 输了扣20,败数+1
				if(player.isLost()){
					account.setPoint(account.getPoint()-20);
					account.setLosts(account.getLosts()+1);
				}else{
					// 归一者额外加分80
					if(player.getRole() instanceof Unifier){
						account.setPoint(account.getPoint()+80);
						account.setWins(account.getWins()+1);
						continue;
					}
					// 三体存活但是没侵占地球不得分
					if(player.getRole() instanceof ThreeBody){
						continue;
					}
					// 其他加50分
					account.setPoint(account.getPoint()+50);
					account.setWins(account.getWins()+1);
				}
			}
		}
		// 判断游戏是否结束
		for (Account account : accounts) {
			if(!gameFinish.get(account)){
				// 没结束返回SUCCESS
				return R.info.SUCCESS;
			}
		}
		gameState = 0;
		closeThisLater();
		return R.info.NOT_EXISTED;
	}
	
	private void closeThisLater(){
		// 20s后unexport
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					try {
						PortableRemoteObject.unexportObject(GameServer.this);
					} catch (NoSuchObjectException e1) {
						e1.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public int getGameState() throws RemoteException {
		return this.gameState;
	}

}
