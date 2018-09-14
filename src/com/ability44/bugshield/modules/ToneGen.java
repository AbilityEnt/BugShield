package com.ability44.bugshield.modules;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class ToneGen {

	private final int duration = 3; // seconds
	private final int sampleRate = 8000;
	private final int numSamples = duration * sampleRate;
	private final double sample[] = new double[numSamples];
	private final double freqOfTone = 440; // hz
	private final byte generatedSnd[] = new byte[2 * numSamples];
	boolean m_stop = false;

	public AudioTrack m_audioTrack;
	Thread m_noiseThread;
	Runnable m_noiseGenerator = new Runnable() {
		public void run() {
			genTone();
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

			while (m_stop != true) {
				m_audioTrack.write(generatedSnd, 0, generatedSnd.length);
			}
		}
	};

	void genTone() {
		// fill out the array
		for (int i = 0; i < numSamples; ++i) {
			sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
		}

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		int idx = 0;
		for (final double dVal : sample) {
			// scale to maximum amplitude
			final short val = (short) ((dVal * 32767));
			// in 16 bit wav PCM, first byte is the low order byte
			generatedSnd[idx++] = (byte) (val & 0x00ff);
			generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

		}
	}

	public void startSound() {
		m_stop = false;

		m_audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				numSamples, AudioTrack.MODE_STREAM);

		m_audioTrack.reloadStaticData();
		m_audioTrack.play();

		m_noiseThread = new Thread(m_noiseGenerator);
		m_noiseThread.start();
	}

	public void stopSound() {
		m_stop = true;
		m_noiseThread.notifyAll();
	}
}