package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

import model.Room;
import util.R;

public interface RMILobby extends Remote {

	LinkedList<Room> getRooms() throws RemoteException;

	R.info enterRoom(String id, String roomName) throws RemoteException;

	R.info createRoom(String name,String createrID,int size) throws RemoteException;
	
	RMIRoom getRoomService(String roomName) throws RemoteException;

	String command(String command) throws RemoteException;
}
