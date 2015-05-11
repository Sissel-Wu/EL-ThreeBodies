package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.rmi.PortableRemoteObject;

import model.Account;
import server.interfaces.RMIAccount;
import server.interfaces.RMIAccountCenter;
import util.R;
import util.R.info;

/**
 * 
 * @author Sissel
 * 
 *         这个类作为单一对象给客户端登陆，并分发各自的后续连接端口
 */
public class AccountCenter extends UnicastRemoteObject implements
		RMIAccountCenter {

	private static final long serialVersionUID = 1L;

	/*
	 * 对应整个服务器上的全部account
	 */
	private Map<String, Account> accounts;
	
	/*
	 * 账号密码对照
	 */
	private Map<String, String> passwords;
	
	/*
	 * 账号暂时登陆码对照
	 */
	private Map<String, String> transientIDs;
	
	/*
	 * 在运行的服务
	 */
	private Map<String, AccountServer> actives;
	
	/*
	 * 邀请码
	 */
	private Set<String> invitationIDs;

	protected AccountCenter() throws RemoteException {
		try {
			
			// TODO 文件IO
			this.passwords = new HashMap<String, String>();
			this.accounts = new HashMap<String, Account>();
			this.invitationIDs = new TreeSet<String>();
			this.transientIDs = new HashMap<String, String>();
			this.actives = new HashMap<String, AccountServer>();
			
			// 检查链接是否正常的线程
			ConnectionSupervision supervisor = new ConnectionSupervision();
			supervisor.start();
			
			Naming.rebind("AccountCenter", (RMIAccountCenter)this);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出但不删除transientID
	 */
	@Override
	public info logout(String id) throws RemoteException {
		if(actives.containsKey(id)){
			this.unbindAccount(id);
			return R.info.SUCCESS;
		}else{
			return R.info.NOT_EXISTED;
		}
	}
	
	/**
	 * 退出且删除transientID
	 */
	@Override
	public info logoutAndClear(String id) throws RemoteException {
		if(actives.containsKey(id)){
			this.unbindAccount(id);
			transientIDs.remove(id);
			return R.info.SUCCESS;
		}else{
			return R.info.NOT_EXISTED;
		}
	}

	@Override
	public info login(String id, String password) throws RemoteException {
		Account account = accounts.get(id);
		if(actives.containsKey(id)){
			return R.info.INVALID;
		}
		if (account == null) {
			return R.info.NOT_EXISTED;
		} else if (!passwords.get(id).equals(password)) {
			return R.info.INVALID;
		}
		// 生成暂时登陆码
	    transientIDs.put(id, generateRandomTransientID());
	    // 分发远程对象
		this.dispatchAccountServer(account);
		return R.info.SUCCESS;
	}

	@Override
	public info logUp(String id, String password, String invitationID)
			throws RemoteException {
		if (accounts.containsKey(id)) {
			return R.info.ALREADY_EXISTED;
		} else if (!invitationIDs.contains(invitationID)) {
			return R.info.NOT_EXISTED;
		}
		// 移除已经使用的邀请码
		invitationIDs.remove(invitationID);
		// 生成账号
		Account newAccount = new Account(id);
		accounts.put(id, newAccount);
		passwords.put(id, password);
		// 生成暂时登陆码
		transientIDs.put(id, generateRandomTransientID());
		// 分发远程对象
		this.dispatchAccountServer(newAccount);
		return R.info.SUCCESS;
	}

	/**
	 * 使用transientID登陆
	 */
	@Override
	public info loginByTransientID(String id, String transientID)
			throws RemoteException {
		Account account = accounts.get(id);
		if(actives.containsKey(id)){
			return R.info.INVALID;
		}
		if (account == null) {
			return R.info.NOT_EXISTED;
		} else if (transientIDs.get(id) == null || !transientIDs.get(id).equals(transientID)) {
			return R.info.INVALID;
		}
		this.dispatchAccountServer(account);
		return R.info.SUCCESS;
	}

	@Override
	public R.info test(String command) {

		switch (command) {
		case "init":
			accounts.put("Red", new Account("Red"));
			accounts.put("Green", new Account("Green"));
			accounts.put("Blue", new Account("Blue"));
			accounts.put("Yellow", new Account("Yellow"));
			passwords.put("Red", "r1234");
			passwords.put("Green", "g1234");
			passwords.put("Blue", "b1234");
			passwords.put("Yellow", "y1234");
			invitationIDs.add("fffff1");
			invitationIDs.add("fffff2");
			invitationIDs.add("fffff3");
			invitationIDs.add("fffff4");
			return R.info.SUCCESS;
		case "clear":
			accounts.clear();
			passwords.clear();
			invitationIDs.clear();
			return R.info.SUCCESS;
		}

		return R.info.INVALID;
	}

	@Override
	public RMIAccount getService(String id) {
		return actives.get(id);
	}

	@Override
	public String getTransientID(String id) {
		return transientIDs.get(id);
	}
	
	public Account getAccount(String id){
		return accounts.get(id);
	}
	
	/**
	 * 生成暂时登陆码
	 */
	private static String generateRandomTransientID(){
		Random random = new Random();
		String result = "k";
		for (int i = 0; i < 10; i++) {
			result += random.nextInt(9);
		}
		return result;
	}
	
	private void dispatchAccountServer(Account account) {
		try {
			if(!actives.containsKey(account.getId())){
				AccountServer as = new AccountServer(this, account);
				actives.put(account.getId(), as);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void unbindAccount(String id) {
		try {
			PortableRemoteObject.unexportObject(actives.get(id));
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
		actives.remove(id);
	}
	
	public void unbindAccount(Account account){
		try {
			PortableRemoteObject.unexportObject(actives.get(account.getId()));
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
		actives.remove(account.getId());
	}
	
	// TODO 保存Account
	public void saveAccount(Account account) {
		accounts.put(account.getId(), account);
		
	}
	
	private class ConnectionSupervision extends Thread{
		@Override
		public void run() {
			while(true){
				System.out.println("Thread alive " + actives.size());
			}
		}
	}
	
	private class AccountData{
		
		private Map<String, Account> accounts;
		private Map<String, String> passwords;
		private Map<String, String> transientIDs;
		private Set<String> invitationIDs;
		
		public AccountData() {
			super();
		}
		
	}

}
