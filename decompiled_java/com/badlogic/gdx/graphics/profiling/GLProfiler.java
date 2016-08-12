// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.profiling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.FloatCounter;

public abstract class GLProfiler {
    public static int calls;
    public static int drawCalls;
    public static int shaderSwitches;
    public static int textureBindings;
    public static FloatCounter vertexCount;

    static  {
        GLProfiler.vertexCount = new FloatCounter(0);
    }

    public GLProfiler() {
        super();
    }

    public static void disable() {
        if(Gdx.gl30 != null && ((Gdx.gl30 instanceof GL30Profiler))) {
            Gdx.gl30 = Gdx.gl30.gl30;
        }

        if(Gdx.gl20 != null && ((Gdx.gl20 instanceof GL20Profiler))) {
            Gdx.gl20 = Gdx.gl.gl20;
        }

        if(Gdx.gl != null && ((Gdx.gl instanceof GL20Profiler))) {
            Gdx.gl = Gdx.gl.gl20;
        }
    }

    public static void enable() {
        GL20Profiler v0_2;
        GL30 v0;
        if(!GLProfiler.isEnabled()) {
            if(Gdx.gl30 == null) {
                v0 = null;
            }
            else {
                GL30Profiler v0_1 = new GL30Profiler(Gdx.gl30);
            }

            Gdx.gl30 = v0;
            if(Gdx.gl30 != null) {
                v0 = Gdx.gl30;
            }
            else {
                v0_2 = new GL20Profiler(Gdx.gl20);
            }

            Gdx.gl20 = ((GL20)v0_2);
            Gdx.gl = Gdx.gl20;
        }
    }

    public static boolean isEnabled() {
        boolean v0;
        if(((Gdx.gl30 instanceof GL30Profiler)) || ((Gdx.gl20 instanceof GL20Profiler))) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static void reset() {
        GLProfiler.calls = 0;
        GLProfiler.textureBindings = 0;
        GLProfiler.drawCalls = 0;
        GLProfiler.shaderSwitches = 0;
        GLProfiler.vertexCount.reset();
    }
}

