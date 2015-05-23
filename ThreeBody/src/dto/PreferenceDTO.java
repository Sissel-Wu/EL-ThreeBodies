package dto;

import io.UserData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class PreferenceDTO implements Serializable {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * singleton
	 */
	private static PreferenceDTO instance;

	/*
	 * 特效开关
	 */
	private boolean effectOn;
	/*
	 * 音量
	 */
	private double volume;
	/*
	 * BGM
	 */
	private String bgm;
	/*
	 * BGM目录
	 */
	private String[] bgmList = new String[]{
			"A Little Story",
			"Cornfield Chase",
			"Paris"
	};
	/*
	 * 是否自动登录
	 */
	private boolean autoLogin;
	
	public static PreferenceDTO getInstance() {
		return instance;
	}

	public static void init() {
		if(instance != null){
			return;
		}
		try {
			instance = UserData.loadPreference();
		} catch (FileNotFoundException e) {
			instance = new PreferenceDTO(true, 0.25, "Paris", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		UserData.savePreference(this);
	}

	public PreferenceDTO(boolean effectSwitch,double volume,String bgm,boolean autoLogin) {
		this.effectOn = effectSwitch;
		this.volume = volume;
		this.bgm = bgm;
		this.autoLogin = autoLogin;
	}

	/*
	 * getters and setters
	 */
	public boolean isEffectOn() {
		return effectOn;
	}

	public void setEffectOn(boolean effectSwitch) {
		this.effectOn = effectSwitch;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getBgm() {
		return bgm;
	}

	public void setBgm(String bgm) {
		this.bgm = bgm;
	}
	
	public void preBGM() {
		for (int i = 0; i < bgmList.length; i++) {
			if (bgmList[i].equals(bgm)) {
				if (i == bgmList.length - 1) {
					bgm = bgmList[0];
				} else {
					bgm = bgmList[i + 1];
				}
				break;
			}
		}
	}

	public void nextBGM() {
		for (int i = 0; i < bgmList.length; i++) {
			if (bgmList[i].equals(bgm)) {
				if (i == 0) {
					bgm = bgmList[bgmList.length - 1];
				} else {
					bgm = bgmList[i - 1];
				}
				break;
			}
		}
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
	
}
