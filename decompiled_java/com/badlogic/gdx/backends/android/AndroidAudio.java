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

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files$FileType;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class AndroidAudio implements Audio {
    private final AudioManager manager;
    protected final List musics;
    private final SoundPool soundPool;

    public AndroidAudio(Context context, AndroidApplicationConfiguration config) {
        SoundPool v1 = null;
        int v3 = 3;
        super();
        this.musics = new ArrayList();
        if(!config.disableAudio) {
            this.soundPool = new SoundPool(config.maxSimultaneousSounds, v3, 100);
            this.manager = context.getSystemService("audio");
            if((context instanceof Activity)) {
                ((Activity)context).setVolumeControlStream(v3);
            }
        }
        else {
            this.soundPool = v1;
            this.manager = ((AudioManager)v1);
        }
    }

    public void dispose() {  // has try-catch handlers
        SoundPool v3_1;
        if(this.soundPool != null) {
            try {
                Iterator v0 = new ArrayList(this.musics).iterator();
                while(v0.hasNext()) {
                    v0.next().dispose();
                }

                v3_1 = this.soundPool;
            }
            catch(Throwable v3) {
                goto label_13;
            }

            v3_1.release();
        }

        return;
        try {
        label_13:
            throw v3;
        }
        catch(Throwable v3) {
            goto label_13;
        }
    }

    public AudioDevice newAudioDevice(int samplingRate, boolean isMono) {
        if(this.soundPool == null) {
            throw new GdxRuntimeException("Android audio is not enabled by the application config.");
        }

        return new AndroidAudioDevice(samplingRate, isMono);
    }

    public AudioRecorder newAudioRecorder(int samplingRate, boolean isMono) {
        if(this.soundPool == null) {
            throw new GdxRuntimeException("Android audio is not enabled by the application config.");
        }

        return new AndroidAudioRecorder(samplingRate, isMono);
    }

    public Music newMusic(FileHandle file) {  // has try-catch handlers
        List v1_1;
        AndroidMusic v9;
        if(this.soundPool == null) {
            throw new GdxRuntimeException("Android audio is not enabled by the application config.");
        }

        FileHandle v6 = file;
        MediaPlayer v0 = new MediaPlayer();
        if(((AndroidFileHandle)v6).type() == FileType.Internal) {
            try {
                AssetFileDescriptor v7 = ((AndroidFileHandle)v6).assets.openFd(((AndroidFileHandle)v6).path());
                v0.setDataSource(v7.getFileDescriptor(), v7.getStartOffset(), v7.getLength());
                v7.close();
                v0.prepare();
                v9 = new AndroidMusic(this, v0);
                goto label_21;
            }
            catch(Exception v8) {
                goto label_35;
            }

            try {
            label_21:
                v1_1 = this.musics;
                goto label_22;
            }
            catch(Exception v8) {
                goto label_35;
            }
            catch(Throwable v1) {
                goto label_25;
            }

            try {
            label_22:
                v1_1.add(v9);
            }
            catch(Throwable v1) {
                goto label_25;
            }
        }
        else {
            try {
                v0.setDataSource(((AndroidFileHandle)v6).file().getPath());
                v0.prepare();
                v9 = new AndroidMusic(this, v0);
                goto label_42;
            }
            catch(Exception v8) {
                goto label_54;
            }

            try {
            label_42:
                v1_1 = this.musics;
                goto label_43;
            }
            catch(Exception v8) {
                goto label_54;
            }
            catch(Throwable v1) {
                goto label_46;
            }

            try {
            label_43:
                v1_1.add(v9);
                goto label_23;
            }
            catch(Throwable v1) {
                goto label_46;
            }

            try {
            label_46:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_46;
            }
            catch(Exception v8) {
            }

        label_54:
            throw new GdxRuntimeException("Error loading audio file: " + file, ((Throwable)v8));
        }

    label_23:
        return ((Music)v9);
        try {
        label_25:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_25;
        }
        catch(Exception v8) {
        }

    label_35:
        throw new GdxRuntimeException("Error loading audio file: " + file + "\nNote: Internal audio files must be placed in the assets directory.", ((Throwable)v8));
    }

    public Sound newSound(FileHandle file) {  // has try-catch handlers
        AndroidSound v3;
        if(this.soundPool == null) {
            throw new GdxRuntimeException("Android audio is not enabled by the application config.");
        }

        FileHandle v0 = file;
        if(((AndroidFileHandle)v0).type() == FileType.Internal) {
            try {
                AssetFileDescriptor v1 = ((AndroidFileHandle)v0).assets.openFd(((AndroidFileHandle)v0).path());
                v3 = new AndroidSound(this.soundPool, this.manager, this.soundPool.load(v1, 1));
                v1.close();
            }
            catch(IOException v2) {
                throw new GdxRuntimeException("Error loading audio file: " + file + "\nNote: Internal audio files must be placed in the assets directory.", ((Throwable)v2));
            }
        }
        else {
            try {
                v3 = new AndroidSound(this.soundPool, this.manager, this.soundPool.load(((AndroidFileHandle)v0).file().getPath(), 1));
            }
            catch(Exception v2_1) {
                throw new GdxRuntimeException("Error loading audio file: " + file, ((Throwable)v2_1));
            }
        }

        return ((Sound)v3);
    }

    protected void pause() {  // has try-catch handlers
        SoundPool v2_1;
        if(this.soundPool != null) {
            try {
                Iterator v0 = this.musics.iterator();
                while(true) {
                    if(v0.hasNext()) {
                        Object v1 = v0.next();
                        if(((AndroidMusic)v1).isPlaying()) {
                            ((AndroidMusic)v1).pause();
                            ((AndroidMusic)v1).wasPlaying = true;
                            continue;
                        }
                        else {
                            ((AndroidMusic)v1).wasPlaying = false;
                            continue;
                        }
                    }
                    else {
                        break;
                    }

                    return;
                }

                v2_1 = this.soundPool;
            }
            catch(Throwable v2) {
                goto label_16;
            }

            v2_1.autoPause();
        }

        return;
        try {
        label_16:
            throw v2;
        }
        catch(Throwable v2) {
            goto label_16;
        }
    }

    protected void resume() {  // has try-catch handlers
        SoundPool v1_1;
        if(this.soundPool != null) {
            int v0 = 0;
            try {
                while(v0 < this.musics.size()) {
                    if(this.musics.get(v0).wasPlaying) {
                        this.musics.get(v0).play();
                    }

                    ++v0;
                }

                v1_1 = this.soundPool;
            }
            catch(Throwable v1) {
                goto label_22;
            }

            v1_1.autoResume();
        }

        return;
        try {
        label_22:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_22;
        }
    }
}

