// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

public abstract interface Batch extends Disposable {
    public static final int C1 = 2;
    public static final int C2 = 7;
    public static final int C3 = 12;
    public static final int C4 = 17;
    public static final int U1 = 3;
    public static final int U2 = 8;
    public static final int U3 = 13;
    public static final int U4 = 18;
    public static final int V1 = 4;
    public static final int V2 = 9;
    public static final int V3 = 14;
    public static final int V4 = 19;
    public static final int X1 = 0;
    public static final int X2 = 5;
    public static final int X3 = 10;
    public static final int X4 = 15;
    public static final int Y1 = 1;
    public static final int Y2 = 6;
    public static final int Y3 = 11;
    public static final int Y4 = 16;

    public abstract void begin();

    public abstract void disableBlending();

    public abstract void draw(TextureRegion arg0, float arg1, float arg2, float arg3, float arg4);

    public abstract void draw(TextureRegion arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9);

    public abstract void draw(Texture arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8);

    public abstract void draw(Texture arg0, float[] arg1, int arg2, int arg3);

    public abstract void draw(Texture arg0, float arg1, float arg2);

    public abstract void draw(Texture arg0, float arg1, float arg2, float arg3, float arg4);

    public abstract void draw(Texture arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, int arg10, int arg11, int arg12, int arg13, boolean arg14, boolean arg15);

    public abstract void draw(Texture arg0, float arg1, float arg2, float arg3, float arg4, int arg5, int arg6, int arg7, int arg8, boolean arg9, boolean arg10);

    public abstract void draw(Texture arg0, float arg1, float arg2, int arg3, int arg4, int arg5, int arg6);

    public abstract void draw(TextureRegion arg0, float arg1, float arg2);

    public abstract void draw(TextureRegion arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, boolean arg10);

    public abstract void draw(TextureRegion arg0, float arg1, float arg2, Affine2 arg3);

    public abstract void enableBlending();

    public abstract void end();

    public abstract void flush();

    public abstract int getBlendDstFunc();

    public abstract int getBlendSrcFunc();

    public abstract Color getColor();

    public abstract float getPackedColor();

    public abstract Matrix4 getProjectionMatrix();

    public abstract Matrix4 getTransformMatrix();

    public abstract boolean isBlendingEnabled();

    public abstract boolean isDrawing();

    public abstract void setBlendFunction(int arg0, int arg1);

    public abstract void setColor(float arg0, float arg1, float arg2, float arg3);

    public abstract void setColor(Color arg0);

    public abstract void setColor(float arg0);

    public abstract void setProjectionMatrix(Matrix4 arg0);

    public abstract void setShader(ShaderProgram arg0);

    public abstract void setTransformMatrix(Matrix4 arg0);
}

