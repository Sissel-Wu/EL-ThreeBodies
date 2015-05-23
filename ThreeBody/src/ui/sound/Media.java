package ui.sound;
import java.io.File;

import saint.media.MidiPlayer;
import saint.media.SimplePlayer;
/**
 * 
 * @author СY
 *
 */

public class Media{
	private static SimplePlayer bgmPlayer = null;
	private static SimplePlayer soundPlayer = null;
	private static MidiPlayer midiPlayer = null;
	
	public static void playBGM(String name) {
		if (bgmPlayer!=null){
			bgmPlayer.stop();
			bgmPlayer=new SimplePlayer();
		}
		else{
			bgmPlayer=new SimplePlayer();
		}
	
		try{
			bgmPlayer.open(new File("sound/"+name+".mp3"));
			bgmPlayer.setLoop(true);
			bgmPlayer.setLoopCount(1000);
		}catch (Exception e) {
			System.err.println("ppt");
			return;
		}

		try{
			bgmPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void playSound(String name){
		soundPlayer=new SimplePlayer();
		
		try{
			soundPlayer.open(new File("sound/"+name+".mp3"));
			soundPlayer.setLoop(false);
		}catch (Exception e) {
			System.err.println("jji");
			return;
		}

		try{
			soundPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playMidi(String name){
		midiPlayer=new MidiPlayer();
		
		try{
			midiPlayer.open(new File("sound/"+name+".mid"));
			midiPlayer.setLoop(false);
		}catch (Exception e) {
			System.err.println("oox");
			return;
		}

		try{
			midiPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stopBGM(String name){
		bgmPlayer.setVolume(0);
	}
	
	public static void stopSound(String name){
		soundPlayer.setVolume(0);
	}
	
	public static void stopMidi(String name){
		midiPlayer.setVolume(0);
	}

	public static SimplePlayer getBgmPlayer() {
		return bgmPlayer;
	}

	public static SimplePlayer getSoundPlayer() {
		return soundPlayer;
	}

	public static MidiPlayer getMidiPlayer() {
		return midiPlayer;
	}
	
}