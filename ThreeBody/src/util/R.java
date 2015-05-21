package util;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.CardConfig;
import config.GameConfig;

public final class R {
    
    public static enum resolution{
        FULL_SCREEN,
        NORMAL
    }
    
    public static enum language{
        SIMPLIFIED_CHINESE,
        STANDALIZED_CHINESE
    }
    
    public static enum info implements Serializable{
    	SUCCESS,
    	ALREADY_EXISTED,
    	ALREADY_IN,
    	NOT_EXISTED,
    	INVALID,
    	ROOM_FULL
    }
    
    public static enum img_format implements Serializable{
    	PNG,
    	JPG
    }
    
    public static enum img_type implements Serializable{
    	HEAD,
    	ADVERTISEMENT,
    }
    
    public static class card {
    	public static Map<String,Integer> cardTechList;
    	public static Map<String,Integer> cardRsrList;
    	public static List<CardConfig> cardList;
    	
    	static{
    		cardTechList = new HashMap<String, Integer>();
    		cardRsrList = new HashMap<String, Integer>();
    		cardList = new GameConfig().getCardsConfig();
    		for(CardConfig cc:cardList){
    			cardTechList.put(cc.getName(), cc.getRequiredTechPoint());
    			cardRsrList.put(cc.getName(), cc.getRequiredResource());
    		}
    	}
    }
    
    public static class text {
    	public static int MAX_ACCOUNT_LENGTH = 14;
    	public static int MAX_PASSWORD_LENGTH = 18;
    }
    
    public static class color{
    	public static Color LIGHT_YELLOW = new Color(244,255,98);
    }
}
