package com.ability44.bugshield.modules;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;

public class ToneManager {
	final static int TONE_TYPE = ToneGenerator.TONE_CDMA_LOW_L;
	final int STREAM = AudioManager.STREAM_MUSIC;

	private ToneGenerator generator;

	public ToneManager(Activity anActivity) {
		AudioManager audio;
		audio = (AudioManager) anActivity
				.getSystemService(Context.AUDIO_SERVICE);
		generator = new ToneGenerator(STREAM, audio.getStreamMaxVolume(STREAM));
	}

	public void playBeep() {
		generator.startTone(TONE_TYPE);
	}

	public void stopBeep() {
		generator.stopTone();
	}
}
