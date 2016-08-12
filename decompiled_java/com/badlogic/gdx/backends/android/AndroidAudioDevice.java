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

import android.media.AudioTrack;
import com.badlogic.gdx.audio.AudioDevice;

class AndroidAudioDevice implements AudioDevice {
    private short[] buffer;
    private final boolean isMono;
    private final int latency;
    private final AudioTrack track;

    AndroidAudioDevice(int samplingRate, boolean isMono) {
        int v0;
        int v3 = 12;
        int v2 = 4;
        int v6 = 1;
        int v4 = 2;
        super();
        this.buffer = new short[1024];
        this.isMono = isMono;
        if(isMono) {
            v0 = v2;
        }
        else {
            v0 = v3;
        }

        int v5 = AudioTrack.getMinBufferSize(samplingRate, v0, v4);
        int v1 = 3;
        if(isMono) {
            v3 = v2;
        }

        this.track = new AudioTrack(v1, samplingRate, v3, v4, v5, 1);
        this.track.play();
        if(!isMono) {
            v6 = v4;
        }

        this.latency = v5 / v6;
    }

    public void dispose() {
        this.track.stop();
        this.track.release();
    }

    public int getLatency() {
        return this.latency;
    }

    public boolean isMono() {
        return this.isMono;
    }

    public void setVolume(float volume) {
        this.track.setStereoVolume(volume, volume);
    }

    public void writeSamples(float[] samples, int offset, int numSamples) {
        if(this.buffer.length < samples.length) {
            this.buffer = new short[samples.length];
        }

        int v0 = offset + numSamples;
        int v2 = offset;
        int v3;
        for(v3 = 0; v2 < v0; ++v3) {
            float v1 = samples[v2];
            if(v1 > 1f) {
                v1 = 1f;
            }

            if(v1 < -1f) {
                v1 = -1f;
            }

            this.buffer[v3] = ((short)(((int)(32767f * v1))));
            ++v2;
        }

        int v5;
        for(v5 = this.track.write(this.buffer, 0, numSamples); v5 != numSamples; v5 += this.track.write(this.buffer, v5, numSamples - v5)) {
        }
    }

    public void writeSamples(short[] samples, int offset, int numSamples) {
        int v0;
        for(v0 = this.track.write(samples, offset, numSamples); v0 != numSamples; v0 += this.track.write(samples, offset + v0, numSamples - v0)) {
        }
    }
}

