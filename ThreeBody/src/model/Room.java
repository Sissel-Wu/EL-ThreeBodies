package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.R;
import util.R.info;

public class Room implements Serializable{
    
    /**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Account> accounts;
	/**
	 * manager 可以决定开始游戏（所有人ready后）
	 */
	private Account manager;
	/**
	 * 房间名字，唯一识别
	 */
	private String name;
    /**
     * 是否开始
     */
	private boolean start;
    /**
     * 每位玩家是否准备好
     */
	private Map<String,Boolean> ready;
    /**
     * 房间人数上限
     */
	private int size;
	
	public Room(Account creater, String name, int size) {
		this.manager = creater;
		this.name = name;
		this.size = size;
		this.start = false;
		
		accounts = new ArrayList<Account>();
		accounts.add(creater);
		ready = new HashMap<String,Boolean>();
		ready.put(creater.getId(), true);
	}
	
	public info enterRoom(Account account){
		// 满人
		if(size == accounts.size()){
			return R.info.ROOM_FULL;
		}
		// 游戏已经开始
		if(this.start){
			return R.info.INVALID;
		}
		ready.put(account.getId(), false);
		accounts.add(account);
		return R.info.SUCCESS;
	}
	
	public info ready(Account account){
		ready.put(account.getId(), true);
		return R.info.SUCCESS;
	}
	
	public info cancelReady(Account account) {
		ready.put(account.getId(), false);
		return R.info.SUCCESS;
	}

	public void exit(Account account) {
		accounts.remove(account);
		if(account == manager && accounts.size() != 0){
			manager = accounts.get(0);
		}
	}

	/*
	 * setters
	 */
	public void setStart(boolean state) {
		this.start = state;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * getters
	 */
	public List<Account> getAccounts() {
		return accounts;
	}
	public Account getCreater() {
		return manager;
	}
	public String getName() {
		return name;
	}
	public boolean isStart() {
		return start;
	}
	public boolean isReady(String id){
		return ready.get(id);
	}
	public boolean isAllReady(){
		if(accounts.size()<size){
			return false;
		}else{
			for(boolean bl:ready.values()){
				if(!bl){
					return false;
				}
			}
		}
		return true;
	}
	public int getSize() {
		return size;
	}

}
