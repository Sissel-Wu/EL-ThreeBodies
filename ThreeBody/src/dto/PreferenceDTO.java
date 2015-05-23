package dto;

import java.io.Serializable;

import util.R;
import ai.AI;
import ai.AI.level;

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
	 * 用户设置,aiLevel,language,resolution数值含义参考R类
	 */
	private boolean musicSwitch;
	private boolean effectSwitch;
	private AI.level aiLevel;
	private R.language language;
	/*
	 * 分辨率
	 */
	private R.resolution resolution;
	/*
	 * 音量
	 */
	private int volume;

	public static PreferenceDTO getInstance() {
		return instance;
	}

	public static void init() {
		instance = new PreferenceDTO(true, true, AI.level.NORMAL,
				R.language.SIMPLIFIED_CHINESE, R.resolution.NORMAL, 40);
	}

	public static void init(PreferenceDTO old) {
		instance = old;
	}

	public PreferenceDTO(boolean musicSwitch, boolean effectSwitch,
			level aiLevel, util.R.language language,
			util.R.resolution resolution, int volume) {
		this.musicSwitch = musicSwitch;
		this.effectSwitch = effectSwitch;
		this.aiLevel = aiLevel;
		this.language = language;
		this.resolution = resolution;
		this.volume = volume;
	}

	/*
	 * getters and setters
	 */
	public boolean isMusicSwitch() {
		return musicSwitch;
	}

	public void setMusicSwitch(boolean musicSwitch) {
		this.musicSwitch = musicSwitch;
	}

	public boolean isEffectSwitch() {
		return effectSwitch;
	}

	public void setEffectSwitch(boolean effectSwitch) {
		this.effectSwitch = effectSwitch;
	}

	public AI.level getAiLevel() {
		return aiLevel;
	}

	public void setAiLevel(AI.level aiLevel) {
		this.aiLevel = aiLevel;
	}

	public R.language getLanguage() {
		return language;
	}

	public void setLanguage(R.language language) {
		this.language = language;
	}

	public R.resolution getResolution() {
		return resolution;
	}

	public void setResolution(R.resolution resolution) {
		this.resolution = resolution;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}
