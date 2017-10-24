package com.jacl.capstone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {
	private Sound sound;
	public AudioPlayer(String s){
		try {
			sound = Gdx.audio.newSound(Gdx.files.internal(s));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		if(sound==null) return;
		stop();
		sound.play(1.0f);
	}
	
	public void stop(){
		sound.stop();
	}
	
	public void close(){
		stop();
		sound.dispose();
	}
}
