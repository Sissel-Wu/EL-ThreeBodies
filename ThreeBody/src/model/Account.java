package model;

import java.awt.Image;
import java.io.Serializable;

public class Account implements Serializable{
    
    /**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 唯一标识符
	 */
	private String id;
    /*
     * 头像
     */
    private transient Image head;
    /*
     * 积分
     */
    private int point;
    /*
     * 排名
     */
    private int rank;
    /*
     * 总游戏次数，除了输和赢还有强退的
     */
    private int totalGames;
    private int wins;
    private int losts;
    
    private String regions;
    
    /*
     * 构造方法，新创建用户时调用，只需要ID，密码存放在服务器端，不需要放在客户端
     */
    public Account(String id){
    	this.id = id;
    	this.head = null;
    	this.rank = -1;
    	this.point = 1400;
    	this.totalGames = 0;
    	this.wins = 0;
    	this.losts = 0;
    }
    
    /*
     * 构造方法，从Database恢复时调用，只需要ID，密码存放在服务器端，不需要放在客户端
     */
    public Account(String id, int point, int totalGames, int wins,
			int losts, String regions, Image head) {
		this.id = id;
		this.head = head;
		this.point = point;
		this.totalGames = totalGames;
		this.wins = wins;
		this.losts = losts;
		this.regions = regions;
	}
    
    public void synchronize(Account acc){
    	this.rank = acc.rank;
    	this.point = acc.point;
    	this.totalGames = acc.totalGames;
    	this.wins = acc.wins;
    	this.losts = acc.losts;
    }
    
	/*
     * getters and setters
     */
	public String getId() {
		return id;
	}
	public Image getHead() {
//		if(head == null){
//			if(id.equals(AccountDTO.getInstance().getId())){
//				head = new ImageIcon("userdata\\head.png").getImage();
//			}else{
//				head = new ImageIcon("tmp\\"+id+".png").getImage();
//				if(head == null){
//					
//				}
//			}
//		}
		return head;
	}
	public void setHead(Image head) {
		this.head = head;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank){
		this.rank = rank;
	}
	public int getTotalGames() {
		return totalGames;
	}
	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosts() {
		return losts;
	}
	public void setLosts(int losts) {
		this.losts = losts;
	}
	public String getRegions() {
		return regions;
	}
	public void setRegions(String regions) {
		this.regions = regions;
	}
}
