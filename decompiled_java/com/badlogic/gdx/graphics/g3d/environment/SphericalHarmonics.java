// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SphericalHarmonics {
    private static final float[] coeff;
    public final float[] data;

    static  {
        SphericalHarmonics.coeff = new float[]{0.282095f, 0.488603f, 0.488603f, 0.488603f, 1.092548f, 1.092548f, 1.092548f, 0.315392f, 0.546274f};
    }

    public SphericalHarmonics() {
        super();
        this.data = new float[27];
    }

    public SphericalHarmonics(float[] copyFrom) {
        super();
        if(copyFrom.length != 27) {
            throw new GdxRuntimeException("Incorrect array size");
        }

        this.data = copyFrom.clone();
    }

    private static final float clamp(float v) {
        float v1 = 1f;
        if(v < 0f) {
            v = 0f;
        }
        else if(v > v1) {
            v = v1;
        }

        return v;
    }

    public SphericalHarmonics set(float r, float g, float b) {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            int v1 = v0 + 1;
            this.data[v0] = r;
            v0 = v1 + 1;
            this.data[v1] = g;
            this.data[v0] = b;
        }

        return this;
    }

    public SphericalHarmonics set(Color color) {
        return this.set(color.r, color.g, color.b);
    }

    public SphericalHarmonics set(AmbientCubemap other) {
        return this.set(other.data);
    }

    public SphericalHarmonics set(float[] values) {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            this.data[v0] = values[v0];
        }

        return this;
    }
}

