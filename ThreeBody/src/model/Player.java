package model;

import java.io.Serializable;
import java.util.Map;

import model.role.Role;

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
     * 是否可以使用特权
     */
    private boolean privilegeAvailable=true;
    /*
     * 是否是AI
     */
    private boolean AI;
    /*
     * 是否可以使用广播
     */
    private boolean broadcast;
    /*
     * 是否已经败北
     */
    private boolean lost;
    /*
     * 已经获知的其他玩家的坐标
     */
    private Map<Player,Coordinate> foundCoordinates;
    /*
     * 已经获知的其他玩家的身份
     */
    private Map<Player,Role> foundRoles;
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
	}
    
    public void findCoordinate(Player player,int position,int value){
    	this.foundCoordinates.get(player).setCoordinateElement(position, value);
    }
    
    public void findRole(Player player,Role role){
    	this.foundRoles.put(player, role);
    }

	/*
     * getters and setters
     */
    public boolean isAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
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

	public boolean isPrivilegeAvailable() {
		return privilegeAvailable;
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

	public void setPrivilegeAvailable(boolean privilegeAvailable) {
		this.privilegeAvailable = privilegeAvailable;
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

	public boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public Map<Player, Coordinate> getFoundCoordinates() {
		return foundCoordinates;
	}

	public Map<Player, Role> getFoundRoles() {
		return foundRoles;
	}

	
    
}
