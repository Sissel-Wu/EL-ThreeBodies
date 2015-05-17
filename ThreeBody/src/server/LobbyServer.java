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
	public info createRoom(String name,String createrID,int size) throws RemoteException {
		// 房间名已存在
		if(rooms.containsKey(name)){
			return R.info.ALREADY_EXISTED;
		}
		Room newRoom = new Room(accountCenter.getAccount(createrID),name,size);
		rooms.put(name, newRoom);
		actives.put(name, new RoomServer(this,newRoom));
		return R.info.SUCCESS;
	}

	@Override
	public RMIRoom getRoomService(String roomName) throws RemoteException {
		return actives.get(roomName);
	}
	
	Room rm1;
	Room rm2;
	
	@Override
	public String command(String command) throws RemoteException {
		String[] parts = command.split(" ");
		StringBuilder sb = new StringBuilder();
		switch(parts[0]){
		case "cr":
			sb.append("total:");
			sb.append(rooms.size());
			sb.append("\n");
			for(Room room:rooms.values()){
				sb.append("RoomName:");
				sb.append(room.getName());
				sb.append(" Creater:");
				sb.append(room.getCreater().getId());
				sb.append("\n");
			}
			return sb.toString();
		case "dr":
			deleteRoom(rooms.get(parts[1]));
			return "success";
		}
		return "INVALID";
	}

	public void deleteRoom(Room room){
		rooms.remove(room.getName());
		actives.remove(room.getName());
	}

}
