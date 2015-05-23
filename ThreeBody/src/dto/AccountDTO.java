package dto;

import java.awt.Image;

import model.Account;

/**
 * 假如account为空，除了ID返回 “未登录” ， 其他都返回null
 * @author Sissel
 *
 */
public class AccountDTO {
	
	private Account account;
	
	/*
	 * singleton
	 */
	private static AccountDTO instance;

	private AccountDTO(Account account) {
		this.account = account;
	}
	
	public static AccountDTO getInstance(){
		return instance;
	}
	
	public static void initializeByLocalData(Account account){
		instance = new AccountDTO(account);
	}
	
	public static void synchronize(Account acc){
		if(acc == null){
			instance.account = null;
			return;
		}
		if(instance.account == null){
			instance.account = acc;
			return;
		}
		instance.account.synchronize(acc);
	}
	
	public String getId() {
		return account == null ? "未登录" : account.getId();
	}
	public Image getHead() {
		return account == null ? null : account.getHead();
	}
	public int getPoint() {
		return  account == null ? null : account.getPoint();
	}
	public int getRank() {
		return account == null ? null : account.getRank();
	}
	public int getTotalGames() {
		return account == null ? null : account.getTotalGames();
	}
	public int getWins() {
		return account == null ? null : account.getWins();
	}
	public int getLosts() {
		return account == null ? null : account.getLosts();
	}
	
}
