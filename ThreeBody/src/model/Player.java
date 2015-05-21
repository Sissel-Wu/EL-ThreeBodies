package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import model.card.Card;
import model.role.Role;
import dto.GameDTO;

public class Player implements Serializable {
	
    /**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	/*
     * 关联的账户
     */
    private Account account;
    private Role role;
    private Coordinate coordinate;
    /*
     * 是否可以使用特权取得身份
     */
    private boolean privilegeGetRole;
    /*
     * 是否可以使用特权赌博
     */
    private boolean privilegeGamble;
    /*
     * 是否可以使用特权爆发资源
     */
    private boolean privilegeResource;
    /*
     * 是否可以使用特权赌博
     */
    private boolean privilegeTechnology;
    /*
     * 是否是AI
     */
    private boolean AI;
    /*
     * 广播是否已经被使用(once a turn)
     */
    private boolean broadcastable;
    /*
     * 是否可以使用消息(once a turn)
     */
    private boolean messageable;
    /*
     * 是否已经败北
     */
    private boolean lost;
    /*
     * 已经获知的其他玩家的坐标
     */
    private HashMap<Player,Coordinate> foundCoordinates;
    /*
     * 已经获知的其他玩家的身份
     */
    private HashMap<Player,Role> foundRoles;
    /*
     * 不能使用的卡牌,包括已经用的和仍在持续的，主要给UI用
     */
    private HashSet<Class<? extends Card>> unavailableCards; 
    /*
     * 本回合已经使用过的卡
     */
    private HashSet<Class<? extends Card>> usedCards; 
    /*
     * 持续型的卡牌
     */
    private HashMap<Card,Integer> durableCards;
    /*
     * 资源，科技点
     */
    private int resource;
    private int techPoint;
    
    public Player(Account account, Role role, Coordinate coordinate,
			boolean aI) {
    	
		super();
		this.account = account;
		this.role = role;
		this.coordinate = coordinate;
		AI = aI;
		
		resource = this.role.getInitialResource();
		techPoint = this.role.getInitialTechPoint();
		
		privilegeGetRole = true;
		privilegeGamble = true;
		privilegeResource = true;
		privilegeTechnology = true;
		lost = false;
		broadcastable = true;
		messageable = true;
		
		durableCards = new HashMap<Card, Integer>();
		unavailableCards = new HashSet<Class<? extends Card>>();
		usedCards = new HashSet<Class<? extends Card>>();
	}
    
    public void findCoordinate(Player player,int position,int value){
    	this.foundCoordinates.get(player).setCoordinateElement(position, value);
    }
    
    public void findRole(Player player,Role role){
    	this.foundRoles.put(player, role);
    }
    
    public void initFoundCoordinates(){
    	foundCoordinates = new HashMap<Player, Coordinate>();
    	foundRoles = new HashMap<Player, Role>();
    	// make四个都为UNKNOWN的坐标
    	for(Player player : GameDTO.getInstance().getPlayers()){
    		int uk = Coordinate.UNKNOWN;
        	int[] uks = new int[Coordinate.DIMENSIONS];
        	Arrays.fill(uks, uk);
    		if(player != this){
    			foundCoordinates.put(player, new Coordinate(uks));
    			foundRoles.put(player, null);
    		}
    	}
    }
    
    // 
    public void refreshCardUnavailable(){
    	unavailableCards.clear();
    	unavailableCards.addAll(usedCards);
        for(Entry<Card,Integer> entry:durableCards.entrySet()){
        	unavailableCards.add( entry.getKey().getClass());
        }
    }

	/*
     * getters and setters
     */
    public boolean isAI() {
		return AI;
	}

	public Account getAccount() {
		return account;
	}

	public model.role.Role getRole() {
		return role;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public boolean isPrivilegeGetRole() {
		return privilegeGetRole;
	}

	public boolean isLost() {
		return lost;
	}

	public int getResource() {
		return resource;
	}

	public int getTechPoint() {
		return techPoint;
	}

	public void setRole(model.role.Role role) {
		this.role = role;
	}

	public void setPrivilegeGetRole(boolean privilegeAvailable) {
		this.privilegeGetRole = privilegeAvailable;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public void setTechPoint(int techPoint) {
		this.techPoint = techPoint;
	}

	public boolean isBroadcastable() {
		return broadcastable;
	}
	
	public void setBroadcastable(boolean broadcastable){
		this.broadcastable = broadcastable;
	}

	public Map<Player, Coordinate> getFoundCoordinates() {
		return foundCoordinates;
	}

	public Map<Player, Role> getFoundRoles() {
		return foundRoles;
	}

	public HashSet<Class<? extends Card>> getUnavailableCards() {
		return unavailableCards;
	}
	
	public Map<Card,Integer> getDurableCards() {
		return durableCards;
	}

	public HashSet<Class<? extends Card>> getUsedCards() {
		return usedCards;
	}

	public boolean isMessageable() {
		return messageable;
	}

	public void setMessageable(boolean messageable) {
		this.messageable = messageable;
	}

	public boolean isPrivilegeGamble() {
		return privilegeGamble;
	}

	public void setPrivilegeGamble(boolean privilegeGamble) {
		this.privilegeGamble = privilegeGamble;
	}

	public boolean isPrivilegeResource() {
		return privilegeResource;
	}

	public void setPrivilegeResource(boolean privilegeResource) {
		this.privilegeResource = privilegeResource;
	}

	public boolean isPrivilegeTechnology() {
		return privilegeTechnology;
	}

	public void setPrivilegeTechnology(boolean privilegeTechnology) {
		this.privilegeTechnology = privilegeTechnology;
	}

}
