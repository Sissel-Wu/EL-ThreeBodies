package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

import model.Account;
import server.interfaces.RMIAccount;
import util.R;
import util.R.info;

/**
 * 每个对象服务于相对应的客户端
 * @author Sissel
 */

public class AccountServer extends UnicastRemoteObject implements RMIAccount,Serializable{
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	public enum State{
		NORMAL,
		TIMEOUT,
		FAIL
	}
	
	private AccountCenter center;
	private Account account;
	private State state;
	private long lastWriten;

	protected AccountServer(AccountCenter center, Account account) throws RemoteException {
		super();
		this.center = center;
		this.account = account;
		this.lastWriten = 0;
		state = State.NORMAL;
		
		new ConnectionChecker().run();
	}

	@Override
	public info uploadChange(Account account) throws RemoteException {
		this.account = account;
		center.saveAccount(account);
		return R.info.SUCCESS;
	}

	@Override
	public Account getAccount() throws RemoteException {
		return this.account;
	}

	@Override
	public synchronized info connect() throws RemoteException {
		lastWriten = Calendar.getInstance().getTimeInMillis();
		return R.info.SUCCESS;
	}

	@Override
	public info test(String command) {
		switch(command){
		case "changeGames":
			account.setTotalGames(998);
			break;
		}
		return R.info.SUCCESS;
	}
	
	public State getState(){
		return this.state;
	}
	
	private synchronized void checkLink(){
		if(lastWriten == 0){
			lastWriten = Calendar.getInstance().getTimeInMillis();
			return;
		}
		long now = Calendar.getInstance().getTimeInMillis();
		if(now - lastWriten >= 90000){
			state = State.FAIL;
			return;
		}
		if(now - lastWriten >= 20000){
			state = State.TIMEOUT;
			return;
		}
		state = State.NORMAL;
	}
	
	/**
	 * 
	 * @author Sissel
	 * 每过一秒检查一次连接状况，并设定连接状态state，state为FAIL时结束线程，通知AccountCenter解除绑定
	 * 
	 */
	private class ConnectionChecker extends Thread{
		public void run(){
			while(AccountServer.this.state != AccountServer.State.FAIL){
				checkLink();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			center.unbindAccount(account);
		}
	}
	
}
