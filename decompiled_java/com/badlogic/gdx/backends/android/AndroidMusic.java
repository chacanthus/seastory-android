// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.media.MediaPlayer;
import android.media.MediaPlayer$OnCompletionListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music$OnCompletionListener;
import java.io.IOException;

public class AndroidMusic implements MediaPlayer$OnCompletionListener, Music {
    private final AndroidAudio audio;
    private boolean isPrepared;
    protected OnCompletionListener onCompletionListener;
    private MediaPlayer player;
    private float volume;
    protected boolean wasPlaying;

    AndroidMusic(AndroidAudio audio, MediaPlayer player) {
        super();
        this.isPrepared = true;
        this.wasPlaying = false;
        this.volume = 1f;
        this.audio = audio;
        this.player = player;
        this.onCompletionListener = null;
        this.player.setOnCompletionListener(((MediaPlayer$OnCompletionListener)this));
    }

    public void dispose() {  // has try-catch handlers
        MediaPlayer v4 = null;
        if(this.player != null) {
            goto label_4;
        }

        return;
        try {
        label_4:
            this.player.release();
        }
        catch(Throwable v1) {
        label_32:
            this.player = v4;
            this.onCompletionListener = ((OnCompletionListener)v4);
            try {
                this.audio.musics.remove(this);
                throw v1;
            }
            catch(Throwable v1) {
                try {
                label_41:
                    throw v1;
                }
                catch(Throwable v1) {
                    goto label_41;
                }
            }
        }
        catch(Throwable v0) {
            try {
                Gdx.app.log("AndroidMusic", "error while disposing AndroidMusic instance, non-fatal");
            }
            catch(Throwable v1) {
                goto label_32;
            }

            this.player = v4;
            this.onCompletionListener = ((OnCompletionListener)v4);
            try {
                this.audio.musics.remove(this);
                return;
            label_30:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_30;
            }
        }

        this.player = v4;
        this.onCompletionListener = ((OnCompletionListener)v4);
        try {
            this.audio.musics.remove(this);
            return;
        label_15:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_15;
        }
    }

    public float getDuration() {
        float v0;
        if(this.player == null) {
            v0 = 0f;
        }
        else {
            v0 = (((float)this.player.getDuration())) / 1000f;
        }

        return v0;
    }

    public float getPosition() {
        float v0;
        if(this.player == null) {
            v0 = 0f;
        }
        else {
            v0 = (((float)this.player.getCurrentPosition())) / 1000f;
        }

        return v0;
    }

    public float getVolume() {
        return this.volume;
    }

    public boolean isLooping() {
        boolean v0;
        if(this.player == null) {
            v0 = false;
        }
        else {
            v0 = this.player.isLooping();
        }

        return v0;
    }

    public boolean isPlaying() {
        boolean v0;
        if(this.player == null) {
            v0 = false;
        }
        else {
            v0 = this.player.isPlaying();
        }

        return v0;
    }

    public void onCompletion(MediaPlayer mp) {
        if(this.onCompletionListener != null) {
            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    AndroidMusic.this.onCompletionListener.onCompletion(AndroidMusic.this);
                }
            });
        }
    }

    public void pause() {
        if(this.player != null) {
            if(this.player.isPlaying()) {
                this.player.pause();
            }

            this.wasPlaying = false;
        }
    }

    public void play() {  // has try-catch handlers
        if(this.player != null) {
            goto label_3;
        }

        return;
    label_3:
        if(this.player.isPlaying()) {
            return;
        }

        try {
            if(!this.isPrepared) {
                this.player.prepare();
                this.isPrepared = true;
            }

            this.player.start();
        }
        catch(IOException v0) {
            v0.printStackTrace();
        }
        catch(IllegalStateException v0_1) {
            v0_1.printStackTrace();
        }
    }

    public void setLooping(boolean isLooping) {
        if(this.player != null) {
            this.player.setLooping(isLooping);
        }
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        this.onCompletionListener = listener;
    }

    public void setPan(float pan, float volume) {
        float v4 = 1f;
        if(this.player != null) {
            float v0 = volume;
            float v1 = volume;
            if(pan < 0f) {
                v1 *= v4 - Math.abs(pan);
            }
            else if(pan > 0f) {
                v0 *= v4 - Math.abs(pan);
            }

            this.player.setVolume(v0, v1);
            this.volume = volume;
        }
    }

    public void setPosition(float position) {  // has try-catch handlers
        if(this.player != null) {
            goto label_3;
        }

        return;
        try {
        label_3:
            if(!this.isPrepared) {
                this.player.prepare();
                this.isPrepared = true;
            }

            this.player.seekTo(((int)(1000f * position)));
        }
        catch(IOException v0) {
            v0.printStackTrace();
        }
        catch(IllegalStateException v0_1) {
            v0_1.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if(this.player != null) {
            this.player.setVolume(volume, volume);
            this.volume = volume;
        }
    }

    public void stop() {
        if(this.player != null) {
            if(this.isPrepared) {
                this.player.seekTo(0);
            }

            this.player.stop();
            this.isPrepared = false;
        }
    }
}

