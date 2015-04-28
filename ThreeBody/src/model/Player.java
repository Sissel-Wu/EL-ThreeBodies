package model;

import java.util.Map;

public class Player {
    /*
     * 关联的账户
     */
    private Account account;
    private Character character;
    private Coordinate coordinate;
    /*
     * 是否已使用特权
     */
    private boolean privilegeUsed;
    /*
     * 是否是AI
     */
    private boolean AI;
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
    private Map<Player,Character> foundCharacters;
}
