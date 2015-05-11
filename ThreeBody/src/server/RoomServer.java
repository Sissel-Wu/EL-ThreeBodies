package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.PortableRemoteObject;

import model.Account;
import model.Room;
import server.interfaces.RMIGame;
import server.interfaces.RMIRoom;
import util.R;
import util.R.info;

public class RoomServer extends UnicastRemoteObject implements RMIRoom {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private Room room;
	private LobbyServer lobbyServer;

	protected RoomServer(LobbyServer lobbyServer,Room room) throws RemoteException {
		super();
		this.room = room;
		this.lobbyServer = lobbyServer;
	}
	
	@Override
	public info ready(String id) throws RemoteException {
		return room.ready(this.searchAccountByID(id));
	}

	@Override
	public info cancelReady(String id) throws RemoteException {
		return room.cancelReady(this.searchAccountByID(id));
	}

	@Override
	public info start() throws RemoteException {
		for(Boolean ready:room.getReady().values()){
			if(!ready){
				return R.info.INVALID;
			}
		}
		room.setStart(true);
		return R.info.SUCCESS;
	}

	@Override
	public info exit(String id) throws RemoteException {
		room.exit(this.searchAccountByID(id));
		// 当房间空掉时，this的生命周期结束
		if(room.getAccounts().size() == 0){
			this.lobbyServer.deleteRoom(room);
			PortableRemoteObject.unexportObject(this);
		}
		return R.info.SUCCESS;
	}
	
	@Override
	public Room refresh() throws RemoteException {
		return room;
	}

	private Account searchAccountByID(String id){
		for (Account ac : room.getAccounts()) {
			if(ac.getId().equals(id)){
				return ac;
			}
		}
		return null;
	}

	@Override
	public RMIGame getGameServer() throws RemoteException {
		if(room.isStart()){
			return new GameServer(room.getAccounts());
		}
		return null;
	}

}
