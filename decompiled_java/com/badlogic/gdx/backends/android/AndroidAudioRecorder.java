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

import android.media.AudioRecord;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AndroidAudioRecorder implements AudioRecorder {
    private AudioRecord recorder;

    public AndroidAudioRecorder(int samplingRate, boolean isMono) {
        int v3;
        int v4 = 2;
        super();
        if(isMono) {
            v3 = 16;
        }
        else {
            v3 = 12;
        }

        this.recorder = new AudioRecord(1, samplingRate, v3, v4, AudioRecord.getMinBufferSize(samplingRate, v3, v4));
        if(this.recorder.getState() != 1) {
            throw new GdxRuntimeException("Unable to initialize AudioRecorder.\nDo you have the RECORD_AUDIO permission?");
        }

        this.recorder.startRecording();
    }

    public void dispose() {
        this.recorder.stop();
        this.recorder.release();
    }

    public void read(short[] samples, int offset, int numSamples) {
        int v0;
        for(v0 = 0; v0 != numSamples; v0 += this.recorder.read(samples, offset + v0, numSamples - v0)) {
        }
    }
}

