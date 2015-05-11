package control;

import java.rmi.RemoteException;

import io.UserData;
import dto.AccountDTO;
import model.Account;
import server.interfaces.RMIAccount;
import server.interfaces.RMIAccountCenter;
import util.R;

/**
 * 跟账户控制有关
 * 与UserData交互，实现缓存读取
 * 与服务器的AccountCenter，AccountServer通信，实现同步
 * @author Sissel
 */
public class AccountControl {
    
    private Account account;
    private RMIAccountCenter rmiac;
    private RMIAccount rmia;
    
    public AccountControl(RMIAccountCenter rmiac, RMIAccount rmia) {
		this.rmiac = rmiac;
		this.rmia = rmia;
		
		// 读取缓存
		account = UserData.loadAccount();
		AccountDTO.init(account);
	}
    
    public R.info logUp(String id,String password,String invitationID){
    	R.info feedback = null;
    	try {
			feedback = rmiac.logUp(id, password, invitationID);
			if(feedback == R.info.SUCCESS){
				rmia = rmiac.getService(id);
				account = rmia.getAccount();
				AccountDTO.synchronize(account);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return feedback;
    }

    // TODO 比较两端的不同
	public R.info login(String id,String password){
		R.info feedback = null;
		try {
			feedback = rmiac.login(id, password);
			if(feedback == R.info.SUCCESS){
				rmia = rmiac.getService(id);
				account = rmia.getAccount();
				AccountDTO.synchronize(account);
				new ConnectionChecker().start();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return feedback;
    }
	
	// TODO 比较两端的不同
	public R.info loginByTransientID(String id){
		R.info feedback = null;
		String transientID = UserData.loadTransientID();
		try {
			feedback = rmiac.loginByTransientID(id, transientID);
			if(feedback == R.info.SUCCESS){
				rmia = rmiac.getService(id);
				account = rmia.getAccount();
				AccountDTO.synchronize(account);
				new ConnectionChecker().start();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return feedback;
	}
    
	/*
     *  与checkLink存在race，设成synchronized
     */
    public synchronized R.info logout(){
    	R.info feedback = null;
    	try {
			feedback = rmiac.logout(account.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	rmia = null;
    	account = null;
    	AccountDTO.synchronize(account);
    	return feedback;
    }
    
    /*
     *  与checkLink存在race，设成synchronized
     */
    public synchronized R.info logoutAndClear(){
    	R.info feedback = null;
    	try {
			feedback = rmiac.logoutAndClear(account.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	rmia = null;
    	account = null;
    	AccountDTO.synchronize(account);
    	return feedback;
    }
    
    /*
     * 将头像上传到服务器端
     */
    public R.info uploadHead(){
    	return null;
    }
    
    /*
     * 将服务器端改变下载,同步
     */
    public void synchronize(){
    	try {
			account = rmia.getAccount();
			AccountDTO.synchronize(account);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    private class ConnectionChecker extends Thread{
    	@Override
    	public void run(){
    		while(true){
    			if(!checkLink()){
					break;
				}
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    /*
     *  与logout存在race，设成synchronized
     */
    private synchronized boolean checkLink(){
    	if(rmia == null){
    		return false;
    	}
    	try {
			rmia.connect();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
}
