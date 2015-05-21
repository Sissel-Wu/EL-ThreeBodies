package util;

import io.NetClient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import server.interfaces.RMIAccountCenter;
import server.interfaces.RMILobby;
import server.interfaces.RMIServerControl;

public class ServerCommand {
	
	static RMIAccountCenter accountCenter;
	static RMILobby lobbyServer;
	static RMIServerControl rmisc;
	
	static{
		try {
			accountCenter = NetClient.getInstance().getAccountCenter();
			lobbyServer = NetClient.getInstance().getLobbyServer();
			rmisc = NetClient.getInstance().getRmisc();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws RemoteException {
		Scanner scanner = new Scanner(System.in);
		boolean finish = false;
		while(!finish){
			String command = scanner.nextLine();
			String[] parts = command.split(" ");
			String result;
			switch(parts[0]){
			case "close":
				finish = true;
				break;
			case "cr":
			case "dr":
				result = lobbyServer.command(command);
				System.out.println(result);
				break;
			case "cacc":
			case "aacc":
			case "cconn":
			case "cinvi":
			case "ainvi":
				result = accountCenter.command(command);
				System.out.println(result);
				break;
			case "shut":
				rmisc.command("shut down");
			default:
				System.out.println("wrong command");
			}
		}
		scanner.close();
	}
}
