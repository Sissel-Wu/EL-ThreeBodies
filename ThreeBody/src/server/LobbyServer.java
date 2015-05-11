package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import model.Room;
import server.interfaces.RMILobby;
import server.interfaces.RMIRoom;
import util.R;
import util.R.info;

public class LobbyServer extends UnicastRemoteObject implements RMILobby{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private AccountCenter accountCenter;
	private Map<String,Room> rooms;
	private Map<String,RoomServer> actives;

	protected LobbyServer(AccountCenter ac) throws RemoteException {
		super();
		this.accountCenter = ac;
		rooms = new HashMap<String,Room>();
		actives = new HashMap<String,RoomServer>();
		
		try {
			Naming.rebind("LobbyServer",this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public LinkedList<Room> getRooms() throws RemoteException {
		LinkedList<Room> rooms = new LinkedList<Room>();
		for (Room room : this.rooms.values()) {
			rooms.add(room);
		}
		return rooms;
	}

	/**
	 * 客户端的数据不一定是最新的，所以检查很重要
	 */
	@Override
	public info enterRoom(String id, String roomName) throws RemoteException {
		Room rm = rooms.get(roomName);
		// 房间不存在
		if(rm == null){
			return R.info.NOT_EXISTED;
		}
		return rm.enterRoom(accountCenter.getAccount(id));
	}

	@Override
	public info createRoom(Room room) throws RemoteException {
		// 房间名已存在
		if(rooms.containsKey(room.getName())){
			return R.info.ALREADY_EXISTED;
		}
		rooms.put(room.getName(), room);
		actives.put(room.getName(), new RoomServer(this,room));
		return R.info.SUCCESS;
	}

	@Override
	public RMIRoom getRoomService(String roomName) throws RemoteException {
		return actives.get(roomName);
	}
	
	Room rm1;
	Room rm2;
	
	@Override
	public info test(String command) throws RemoteException {
		switch(command){
		case "init":
			rm1 = new Room(accountCenter.getAccount("Red"),"Room1",4);
			rm2 = new Room(accountCenter.getAccount("Blue"),"Room2",2);
			createRoom(rm1);
			createRoom(rm2);
			return R.info.SUCCESS;
		case "clear":
			deleteRoom(rm1);
			deleteRoom(rm2);
			return R.info.SUCCESS;
		}
		return R.info.INVALID;
	}

	public void deleteRoom(Room room){
		rooms.remove(room.getName());
		actives.remove(room.getName());
	}

}
