package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import util.R;
import model.Account;

/**
 * 
 * @author Sissel
 * 作为特定对象为特定客户端的连接端口提供服务
 */

public interface RMIAccount extends Remote {

	R.info uploadChange(Account account) throws RemoteException;
	
	Account getAccount() throws RemoteException;
	
	R.info connect() throws RemoteException;
	
	//TODO test
	R.info test(String command) throws RemoteException;
}
