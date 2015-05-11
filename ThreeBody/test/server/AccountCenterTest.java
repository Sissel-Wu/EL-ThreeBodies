package server;

import static org.junit.Assert.assertEquals;

import java.rmi.Naming;
import java.rmi.RemoteException;

import model.Account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.interfaces.RMIAccount;
import server.interfaces.RMIAccountCenter;
import util.R;

public class AccountCenterTest {

	RMIAccountCenter center;
	Account account;

	@Before
	public void setUp() throws Exception {
		try {
			center = (RMIAccountCenter) Naming
					.lookup("rmi://104.236.174.190/AccountCenter");
			if (center.test("init").equals(R.info.SUCCESS)) {
				System.out.println("INIT");
			}
			center.logoutAndClear("Green");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLogUp() {
		try {
			assertEquals(R.info.ALREADY_EXISTED,
					center.logUp("Green", "g1234", "080"));
			assertEquals(R.info.NOT_EXISTED,
					center.logUp("Gree", "g1234", "080"));
			assertEquals(R.info.SUCCESS,
					center.logUp("Sissel", "1234", "fffff1"));
			assertEquals(R.info.ALREADY_EXISTED,
					center.logUp("Sissel", "1234", "fffff1"));
			assertEquals(R.info.NOT_EXISTED,
					center.logUp("Sissel2", "1234", "fffff1"));
			
			RMIAccount service = center.getService("Sissel");
			if(service == null){
				System.out.println("Service is null");
			}
			assertEquals(R.info.SUCCESS, service.test("hello"));

			String transientID = center.getTransientID("Sissel");
			System.out.println("transientID = " + transientID);

			account = service.getAccount();
			System.out.println("id = " + account.getId());

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLogin() {
		try {
			assertEquals(R.info.NOT_EXISTED, center.login("GreenBAY", "g1234"));
			assertEquals(R.info.INVALID, center.login("Green", "kujjj"));
			assertEquals(R.info.SUCCESS, center.login("Green", "g1234"));
			assertEquals(R.info.INVALID, center.login("Green", "g1234"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLogout() {
		try {
			assertEquals(R.info.SUCCESS, center.login("Green", "g1234"));
			assertEquals(R.info.INVALID, center.login("Green", "g1234"));
			assertEquals(R.info.NOT_EXISTED, center.logout("JB"));
			assertEquals(R.info.SUCCESS, center.logout("Green"));
			assertEquals(R.info.SUCCESS, center.login("Green", "g1234"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogoutAndClear(){
		try {
			assertEquals(R.info.SUCCESS, center.login("Green", "g1234"));
			String transientID = center.getTransientID("Green");
			assertEquals(R.info.SUCCESS, center.logout("Green"));
			assertEquals(R.info.SUCCESS, center.loginByTransientID("Green", transientID));
			assertEquals(R.info.SUCCESS, center.logoutAndClear("Green"));
			assertEquals(R.info.INVALID, center.loginByTransientID("Green", transientID));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginByTransientID() {
		try {
			assertEquals(R.info.SUCCESS, center.login("Green", "g1234"));
			String transientID = center.getTransientID("Green");
			assertEquals(R.info.SUCCESS, center.logout("Green"));
			assertEquals(R.info.SUCCESS, center.loginByTransientID("Green", transientID));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		try {
			if (center.test("clear").equals(R.info.SUCCESS)) {
				System.out.println("CLEAR");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	

}
