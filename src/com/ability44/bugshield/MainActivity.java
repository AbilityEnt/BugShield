package com.ability44.bugshield;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.ability44.bugshield.modules.ToneGen;
import com.ability44.bugshield.modules.ToneManager;

public class MainActivity extends BaseActivity {

	ToggleButton togg1;
	MediaPlayer grasshopper;
	ToneManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		togg1 = (ToggleButton) findViewById(R.id.tog_shield1);
		grasshopper = MediaPlayer.create(this, R.raw.dragonfly);
		manager = new ToneManager(this);
	}

	public void toggleShield(View v) {
		boolean on = ((ToggleButton) v).isChecked();
		if (on) {
			grasshopper.setVolume(0.002f, 0.002f);
			grasshopper.start();
			grasshopper.setLooping(true);
		} else {
			grasshopper.pause();
		}

	}

	public void tog3055(View v) {
		boolean on = ((ToggleButton) v).isChecked();
		final ToneGen tone = new ToneGen();
		if (on) {
			tone.startSound();
		} else {
			tone.stopSound();
		}
	}

	@Override
	public void onBackPressed() {
		grasshopper.pause();
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}
}
