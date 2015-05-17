package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import util.R;
import util.R.info;

public interface RMIAccountCenter extends Remote {

	R.info logUp(String id, String password, String invitationID)
			throws RemoteException;

	R.info login(String id, String password) throws RemoteException;

	R.info loginByTransientID(String id, String transientID)
			throws RemoteException;
	
	info logout(String id) throws RemoteException;
	
	info logoutAndClear(String id) throws RemoteException;

	String command(String command) throws RemoteException;

	RMIAccount getService(String id) throws RemoteException;

	String getTransientID(String id) throws RemoteException;
	
	R.info editPassword(String id,String password,String newPassword) throws RemoteException;

}
