package com.main.voiceoperationapp.client.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.main.voiceoperationapp.config.AudioConf;
import com.main.voiceoperationapp.exception.AudioException;

public class AudioPlayer {

    private AudioConf audioConf;
    public static AudioTrack audioTrack = null;

    public AudioPlayer(AudioConf audioConf) {
        this.audioConf = audioConf;
    }

    public static void play(byte[] sound) throws AudioException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // バッファサイズを取得
                    int bufSize = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

                    if(audioTrack != null){
                        if (audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                            audioTrack.stop();
                            audioTrack.flush();
                            audioTrack.release();
                        }
                    }

                    // AudioTrackインスタンスを生成
                    audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, bufSize, AudioTrack.MODE_STREAM);

                    // 再生
                    audioTrack.play();
                    audioTrack.write(sound, 0, sound.length);
                    audioTrack.stop();
                    audioTrack.flush();
                    audioTrack.release();
                } catch (Exception e) {
                    try {
                        throw new AudioException("Unable to play the response", e);
                    } catch (AudioException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
