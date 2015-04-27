package model;

import java.awt.Image;
import java.io.Serializable;

public class Account implements Serializable{
    
    /**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
    /*
     * ͷ��
     */
    private Image head;
    /*
     * ����
     */
    private int point;
    /*
     * ����
     */
    private int rank;
    /*
     * ����Ϸ�������������Ӯ����ǿ�˵�
     */
    private int totalGames;
    private int wins;
    private int losts;
    private String regions;

}
