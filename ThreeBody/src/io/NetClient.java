package io;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.interfaces.RMIAccountCenter;
import server.interfaces.RMIImage;
import server.interfaces.RMILobby;
import server.interfaces.RMIServerControl;


public class NetClient {
    
	private RMIAccountCenter rmiac;
	private RMILobby rmilb;
	private RMIImage rmii;
	private RMIServerControl rmisc;
	private static NetClient instance;
	
	private static boolean connected = false;
	
	private NetClient() throws MalformedURLException, RemoteException, NotBoundException{
		rmiac = (RMIAccountCenter)Naming.lookup("rmi://104.236.174.190/AccountCenter");
		rmilb = (RMILobby)Naming.lookup("rmi://104.236.174.190/LobbyServer");
		rmii = (RMIImage) Naming.lookup("rmi://104.236.174.190/ImgService");
		rmisc = ((RMIServerControl)Naming.lookup("rmi://104.236.174.190/ServerControl"));
		connected = true;
	}		
	
	public static NetClient getInstance() throws MalformedURLException, RemoteException, NotBoundException{
		if(!connected){
			instance = new NetClient();
		}
		return instance;
	}
	
	public RMIAccountCenter getAccountCenter(){
		return rmiac;
	}
	
    public RMILobby getLobbyServer(){
    	return rmilb;
    }
    
    public RMIImage getImageServer(){
    	return rmii;
    }

	public RMIServerControl getRmisc() {
		return rmisc;
	}

}
