// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ScissorStack {
    private static Array scissors;
    static Vector3 tmp;
    static final Rectangle viewport;

    static  {
        ScissorStack.scissors = new Array();
        ScissorStack.tmp = new Vector3();
        ScissorStack.viewport = new Rectangle();
    }

    public ScissorStack() {
        super();
    }

    public static void calculateScissors(Camera camera, float viewportX, float viewportY, float viewportWidth, float viewportHeight, Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        ScissorStack.tmp.set(area.x, area.y, 0f);
        ScissorStack.tmp.mul(batchTransform);
        camera.project(ScissorStack.tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        scissor.x = ScissorStack.tmp.x;
        scissor.y = ScissorStack.tmp.y;
        ScissorStack.tmp.set(area.x + area.width, area.y + area.height, 0f);
        ScissorStack.tmp.mul(batchTransform);
        camera.project(ScissorStack.tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        scissor.width = ScissorStack.tmp.x - scissor.x;
        scissor.height = ScissorStack.tmp.y - scissor.y;
    }

    public static void calculateScissors(Camera camera, Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        ScissorStack.calculateScissors(camera, 0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()), batchTransform, area, scissor);
    }

    private static void fix(Rectangle rect) {
        rect.x = ((float)Math.round(rect.x));
        rect.y = ((float)Math.round(rect.y));
        rect.width = ((float)Math.round(rect.width));
        rect.height = ((float)Math.round(rect.height));
        if(rect.width < 0f) {
            rect.width = -rect.width;
            rect.x -= rect.width;
        }

        if(rect.height < 0f) {
            rect.height = -rect.height;
            rect.y -= rect.height;
        }
    }

    public static Rectangle getViewport() {
        Rectangle v1;
        if(ScissorStack.scissors.size == 0) {
            ScissorStack.viewport.set(0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
            v1 = ScissorStack.viewport;
        }
        else {
            ScissorStack.viewport.set(ScissorStack.scissors.peek());
            v1 = ScissorStack.viewport;
        }

        return v1;
    }

    public static Rectangle peekScissors() {
        return ScissorStack.scissors.peek();
    }

    public static Rectangle popScissors() {
        Object v0 = ScissorStack.scissors.pop();
        if(ScissorStack.scissors.size == 0) {
            Gdx.gl.glDisable(3089);
        }
        else {
            Object v1 = ScissorStack.scissors.peek();
            Gdx.gl.glScissor(((int)((Rectangle)v1).x), ((int)((Rectangle)v1).y), ((int)((Rectangle)v1).width), ((int)((Rectangle)v1).height));
        }

        return ((Rectangle)v0);
    }

    public static boolean pushScissors(Rectangle scissor) {
        boolean v5 = false;
        float v9 = 1f;
        ScissorStack.fix(scissor);
        if(ScissorStack.scissors.size != 0) {
            Object v4 = ScissorStack.scissors.get(ScissorStack.scissors.size - 1);
            float v2 = Math.max(((Rectangle)v4).x, scissor.x);
            float v0 = Math.min(((Rectangle)v4).x + ((Rectangle)v4).width, scissor.x + scissor.width);
            if(v0 - v2 >= v9) {
                float v3 = Math.max(((Rectangle)v4).y, scissor.y);
                float v1 = Math.min(((Rectangle)v4).y + ((Rectangle)v4).height, scissor.y + scissor.height);
                if(v1 - v3 >= v9) {
                    scissor.x = v2;
                    scissor.y = v3;
                    scissor.width = v0 - v2;
                    scissor.height = Math.max(v9, v1 - v3);
                label_14:
                    ScissorStack.scissors.add(scissor);
                    Gdx.gl.glScissor(((int)scissor.x), ((int)scissor.y), ((int)scissor.width), ((int)scissor.height));
                    v5 = true;
                }
            }
        }
        else if(scissor.width >= v9 && scissor.height >= v9) {
            Gdx.gl.glEnable(3089);
            goto label_14;
        }

        return v5;
    }
}

