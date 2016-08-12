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

import android.media.AudioManager;
import android.media.SoundPool;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntArray;

final class AndroidSound implements Sound {
    final AudioManager manager;
    final int soundId;
    final SoundPool soundPool;
    final IntArray streamIds;

    AndroidSound(SoundPool pool, AudioManager manager, int soundId) {
        super();
        this.streamIds = new IntArray(8);
        this.soundPool = pool;
        this.manager = manager;
        this.soundId = soundId;
    }

    public void dispose() {
        this.soundPool.unload(this.soundId);
    }

    public long loop() {
        return this.loop(1f);
    }

    public long loop(float volume) {
        long v0;
        if(this.streamIds.size == 8) {
            this.streamIds.pop();
        }

        int v7 = this.soundPool.play(this.soundId, volume, volume, 1, -1, 1f);
        if(v7 == 0) {
            v0 = -1;
        }
        else {
            this.streamIds.insert(0, v7);
            v0 = ((long)v7);
        }

        return v0;
    }

    public long loop(float volume, float pitch, float pan) {
        long v0;
        float v5 = 1f;
        if(this.streamIds.size == 8) {
            this.streamIds.pop();
        }

        float v2 = volume;
        float v3 = volume;
        if(pan < 0f) {
            v3 *= v5 - Math.abs(pan);
        }
        else if(pan > 0f) {
            v2 *= v5 - Math.abs(pan);
        }

        int v7 = this.soundPool.play(this.soundId, v2, v3, 1, -1, pitch);
        if(v7 == 0) {
            v0 = -1;
        }
        else {
            this.streamIds.insert(0, v7);
            v0 = ((long)v7);
        }

        return v0;
    }

    public void pause() {
        this.soundPool.autoPause();
    }

    public void pause(long soundId) {
        this.soundPool.pause(((int)soundId));
    }

    public long play() {
        return this.play(1f);
    }

    public long play(float volume) {
        long v0;
        if(this.streamIds.size == 8) {
            this.streamIds.pop();
        }

        int v7 = this.soundPool.play(this.soundId, volume, volume, 1, 0, 1f);
        if(v7 == 0) {
            v0 = -1;
        }
        else {
            this.streamIds.insert(0, v7);
            v0 = ((long)v7);
        }

        return v0;
    }

    public long play(float volume, float pitch, float pan) {
        long v0;
        float v6 = 1f;
        if(this.streamIds.size == 8) {
            this.streamIds.pop();
        }

        float v2 = volume;
        float v3 = volume;
        if(pan < 0f) {
            v3 *= v6 - Math.abs(pan);
        }
        else if(pan > 0f) {
            v2 *= v6 - Math.abs(pan);
        }

        int v7 = this.soundPool.play(this.soundId, v2, v3, 1, 0, pitch);
        if(v7 == 0) {
            v0 = -1;
        }
        else {
            this.streamIds.insert(0, v7);
            v0 = ((long)v7);
        }

        return v0;
    }

    public void resume() {
        this.soundPool.autoResume();
    }

    public void resume(long soundId) {
        this.soundPool.resume(((int)soundId));
    }

    public void setLooping(long soundId, boolean looping) {
        int v0;
        SoundPool v1 = this.soundPool;
        int v2 = ((int)soundId);
        if(looping) {
            v0 = -1;
        }
        else {
            v0 = 0;
        }

        v1.setLoop(v2, v0);
    }

    public void setPan(long soundId, float pan, float volume) {
        float v4 = 1f;
        float v0 = volume;
        float v1 = volume;
        if(pan < 0f) {
            v1 *= v4 - Math.abs(pan);
        }
        else if(pan > 0f) {
            v0 *= v4 - Math.abs(pan);
        }

        this.soundPool.setVolume(((int)soundId), v0, v1);
    }

    public void setPitch(long soundId, float pitch) {
        this.soundPool.setRate(((int)soundId), pitch);
    }

    public void setPriority(long soundId, int priority) {
        this.soundPool.setPriority(((int)soundId), priority);
    }

    public void setVolume(long soundId, float volume) {
        this.soundPool.setVolume(((int)soundId), volume, volume);
    }

    public void stop() {
        int v0 = 0;
        int v1 = this.streamIds.size;
        while(v0 < v1) {
            this.soundPool.stop(this.streamIds.get(v0));
            ++v0;
        }
    }

    public void stop(long soundId) {
        this.soundPool.stop(((int)soundId));
    }
}

