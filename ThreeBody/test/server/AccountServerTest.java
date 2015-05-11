package server;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import model.Account;

import org.junit.After;
import org.junit.Test;

import server.interfaces.RMIAccount;
import server.interfaces.RMIAccountCenter;
import util.R;

public class AccountServerTest {
	
	RMIAccountCenter center;
	Account account;
	RMIAccount service;

	public void setUp() {
		try {
			center = (RMIAccountCenter) Naming
					.lookup("rmi://104.236.174.190/AccountCenter");
			if (center.test("init").equals(R.info.SUCCESS)) {
				System.out.println("INIT");
			}
			center.login("Green", "g1234");
			service = center.getService("Green");
			account = service.getAccount();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		
		// connection
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						assertEquals(R.info.SUCCESS, service.connect());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Test
	public void testUploadChange() {
		
		this.setUp();
		
		account.setTotalGames(100);
		try {
			assertEquals(R.info.SUCCESS,service.uploadChange(account));
			
			service.test("changeGames");
			account = service.getAccount();
			assertEquals(998, account.getTotalGames());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void testDownloadChange() {
//		account.setTotalGames(100);
//		try {
//			assertEquals(R.info.SUCCESS,service.uploadChange(account));
//			service.test("changeGames");
//			account = service.downloadChange();
//			assertEquals(998, account.getTotalGames());
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
	
}
