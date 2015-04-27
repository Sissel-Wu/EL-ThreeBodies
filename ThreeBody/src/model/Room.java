package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Room implements Serializable{
    
    /**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Account> accounts;
	private Account creater;
	private String name;
    /*
     * �Ƿ�ʼ
     */
	private boolean state;
    /*
     * ÿλ����Ƿ�׼����
     */
	private Map<Player,Boolean> ready;
    /*
     * ������������
     */
	private int size;

}
