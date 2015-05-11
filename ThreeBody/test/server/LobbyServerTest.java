package server;

import static org.junit.Assert.assertEquals;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;

import model.Account;
import model.Room;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.interfaces.RMIAccount;
import server.interfaces.RMIAccountCenter;
import server.interfaces.RMILobby;
import server.interfaces.RMIRoom;
import util.R;

public class LobbyServerTest {
	
	static RMIAccountCenter center;
	static Account account;
	static RMILobby lobby;
	static RMIRoom roomService;

	@Before
	public void setUp() throws Exception {
		try {
			// 连接到AccountCenter
			center = (RMIAccountCenter) Naming
					.lookup("rmi://104.236.174.190/AccountCenter");
			if (center.test("init").equals(R.info.SUCCESS)) {
				System.out.println("INIT1");
			}
			
			//连接到LobbyServer
			lobby = (RMILobby) Naming.lookup("rmi://104.236.174.190/LobbyServer");
			if (lobby.test("init").equals(R.info.SUCCESS)) {
				System.out.println("INIT2");
			}
			
			center.login("Green", "g1234");
			RMIAccount service = center.getService("Green");
			account = service.getAccount();
			System.out.println("Account: "+account.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		try {
			center.logoutAndClear("Green");
			if (center.test("clear").equals(R.info.SUCCESS)) {
				System.out.println("CLEAR1");
			}
			if (lobby.test("clear").equals(R.info.SUCCESS)) {
				System.out.println("CLEAR2");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRooms() {
		try {
			Collection<Room> rooms = lobby.getRooms();
			for (Room room : rooms) {
				System.out.println(room.getName());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEnterRoom() {
		try {
			assertEquals(R.info.SUCCESS,lobby.enterRoom("Green", "Room2"));
			assertEquals(R.info.ROOM_FULL,lobby.enterRoom("Yellow", "Room2"));
			roomService = lobby.getRoomService("Room2");
			assertEquals(R.info.SUCCESS,roomService.ready("Green"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateRoom() {
		Room newRoom = new Room(account,"RMB",6);
		Room invalidRoom = new Room(account,"Room1",4);
		try {
			assertEquals(R.info.SUCCESS, lobby.createRoom(newRoom));
			assertEquals(R.info.ALREADY_EXISTED, lobby.createRoom(invalidRoom));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.testGetRooms();
	}
	
}
