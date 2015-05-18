package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import model.Player;
import model.operation.Operation;
import util.R;

public interface RMIGame extends Remote{
	
	LinkedList<Operation> downloadOperation(String id) throws RemoteException;
	
	LinkedList<Player> getPlayers() throws RemoteException;
	
	R.info uploadOperation(String id,List<Operation> unhandled) throws RemoteException;
	
	R.info exitGame(Player player, boolean normal) throws RemoteException;
	
	int getGameState() throws RemoteException;

}
