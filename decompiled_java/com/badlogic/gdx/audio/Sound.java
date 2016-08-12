﻿// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.audio;

import com.badlogic.gdx.utils.Disposable;

public abstract interface Sound extends Disposable {
    public abstract void dispose();

    public abstract long loop();

    public abstract long loop(float arg0);

    public abstract long loop(float arg0, float arg1, float arg2);

    public abstract void pause();

    public abstract void pause(long arg0);

    public abstract long play();

    public abstract long play(float arg0);

    public abstract long play(float arg0, float arg1, float arg2);

    public abstract void resume();

    public abstract void resume(long arg0);

    public abstract void setLooping(long arg0, boolean arg1);

    public abstract void setPan(long arg0, float arg1, float arg2);

    public abstract void setPitch(long arg0, float arg1);

    public abstract void setPriority(long arg0, int arg1);

    public abstract void setVolume(long arg0, float arg1);

    public abstract void stop();

    public abstract void stop(long arg0);
}
