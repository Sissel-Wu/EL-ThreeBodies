package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Room;
import util.R.info;

public interface RMIRoom extends Remote {

	info ready(String id) throws RemoteException;

	info cancelReady(String id) throws RemoteException;

	info start() throws RemoteException;

	info exit(String id) throws RemoteException;
	
	Room refresh() throws RemoteException;
	
	RMIGame getGameServer() throws RemoteException;
	
}
