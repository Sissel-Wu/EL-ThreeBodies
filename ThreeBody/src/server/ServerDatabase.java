package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Account;

public class ServerDatabase {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/game";
	private static final String USER = "root";
	private static final String PASSWORD = "wwww12345";

	private Connection conn = null;
	private Statement statement = null;

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
	 * 邀请码
	 */
	private Set<String> invitationIDs;

	/*
	 * singleton
	 */
	private static ServerDatabase instance = new ServerDatabase();

	private ServerDatabase() {}
	
	/*
	 * 加载驱动
	 */
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ServerDatabase getInstance() {
		return instance;
	}

	public void openDB() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDB() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 第一次从Database加载数据
	 */
	public void loadData() {
		this.passwords = new HashMap<String, String>();
		this.accounts = new HashMap<String, Account>();
		this.invitationIDs = new HashSet<String>();
		this.transientIDs = new HashMap<String, String>();
		try {
			// 加载account,password,transientID
			ResultSet rs = statement.executeQuery("select * from account");
			while (rs.next()) {
				accounts.put(rs.getString(1),new Account(
						rs.getString(1), 
						rs.getInt(3),
						rs.getInt(4), 
						rs.getInt(5), 
						rs.getInt(6),
						rs.getString(8), 
						null));
				passwords.put(rs.getString(1), rs.getString(2));
				transientIDs.put(rs.getString(1), rs.getString(9));
			}
			rs.close();
			// 加载邀请码
			rs = statement.executeQuery("select * from invitations");
			while(rs.next()){
				invitationIDs.add(rs.getString(2));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TESTED
	public void updateTransientID(String id,String transientID){
		String url;
		if(transientID == null){
			url = "update account set transientPW = null"
					+" where id = \""+id+"\"";
		}else{
			url = "update account set transientPW = \""+transientID+"\""
					+" where id = \""+id+"\"";
		}
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TODO
	public void updatePassword(String id,String newPassword){
		String url = "update account set password = \""+newPassword+"\""
				+" where id = \""+id+"\"";
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TODO
	public void updateAccount(Account account){
		String url = "update account set point = \""+account.getPoint()+"\""
				+" totalgames = "+account.getTotalGames()
				+" wins = "+account.getWins()
				+" losts = "+account.getLosts()
				+" where id = \""+account.getId()+"\"";
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TESTED
	public void addAccount(String id,String password,String transientID){
		String url = "insert into account values("
				+"\""+id+"\","
				+"\""+password+"\","
				+"1400," //point
				+"0," //total games
				+"0," //wins
				+"0," //losts
				+"null," //head
				+"null," //regions
				+"\""+transientID+"\""
				+")";
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TESTED
	public void addInvitationID(String invitationID){
		String url = "insert into invitations values(null,\""+invitationID+"\")";
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TESTED
	public void deleteInvitationID(String invitationID){
		String url = "delete from invitations where value = \""+invitationID+"\"";
		try {
			statement.execute(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public Map<String, String> getPasswords() {
		return passwords;
	}

	public Map<String, String> getTransientIDs() {
		return transientIDs;
	}

	public Set<String> getInvitationIDs() {
		return invitationIDs;
	}

}
