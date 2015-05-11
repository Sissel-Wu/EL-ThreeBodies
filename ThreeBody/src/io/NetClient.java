package io;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.interfaces.RMIAccountCenter;
import server.interfaces.RMILobby;


public class NetClient {
    
	private RMIAccountCenter rmiac;
	private RMILobby rmilb;
	private static NetClient instance = new NetClient();
	
	private NetClient() {
		try {
			rmiac = (RMIAccountCenter)Naming.lookup("rmi://104.236.174.190/AccountCenter");
			rmilb = (RMILobby)Naming.lookup("rmi://104.236.174.190/LobbyServer");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static NetClient getInstance(){
		return instance;
	}
	
	public RMIAccountCenter getAccountCenter(){
		return rmiac;
	}
	
    public RMILobby getLobbyServer(){
    	return rmilb;
    }

}
