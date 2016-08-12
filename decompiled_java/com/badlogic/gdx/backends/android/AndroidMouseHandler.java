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

import android.view.MotionEvent;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class AndroidMouseHandler {
    private int deltaX;
    private int deltaY;

    public AndroidMouseHandler() {
        super();
        this.deltaX = 0;
        this.deltaY = 0;
    }

    private void logAction(int action) {
        String v0;
        if(action == 9) {
            v0 = "HOVER_ENTER";
        }
        else if(action == 7) {
            v0 = "HOVER_MOVE";
        }
        else if(action == 10) {
            v0 = "HOVER_EXIT";
        }
        else if(action == 8) {
            v0 = "SCROLL";
        }
        else {
            v0 = "UNKNOWN (" + action + ")";
        }

        Gdx.app.log("AndroidMouseHandler", "action " + v0);
    }

    public boolean onGenericMotion(MotionEvent event, AndroidInput input) {  // has try-catch handlers
        Application v2_3;
        boolean v2;
        if((event.getSource() & 2) == 0) {
            v2 = false;
        }
        else {
            int v18 = event.getAction() & 255;
            long v8 = System.nanoTime();
            switch(v18) {
                case 7: {
                    try {
                        int v5 = ((int)event.getX());
                        int v6 = ((int)event.getY());
                        if(v5 == this.deltaX && v6 == this.deltaY) {
                            goto label_13;
                        }

                        this.postTouchEvent(input, 4, v5, v6, 0, v8);
                        this.deltaX = v5;
                        this.deltaY = v6;
                        goto label_13;
                    }
                    catch(Throwable v2_1) {
                        goto label_39;
                    }
                }
                case 8: {
                    int v2_2 = 9;
                    try {
                        this.postTouchEvent(input, 3, 0, 0, ((int)(-Math.signum(event.getAxisValue(v2_2)))), v8);
                    label_13:
                        v2_3 = Gdx.app;
                        goto label_14;
                    label_39:
                        throw v2_1;
                    }
                    catch(Throwable v2_1) {
                        goto label_39;
                    }
                }
            }

        label_14:
            v2_3.getGraphics().requestRendering();
            v2 = true;
        }

        return v2;
    }

    private void postTouchEvent(AndroidInput input, int type, int x, int y, int scrollAmount, long timeStamp) {
        Object v0 = input.usedTouchEvents.obtain();
        ((TouchEvent)v0).timeStamp = timeStamp;
        ((TouchEvent)v0).x = x;
        ((TouchEvent)v0).y = y;
        ((TouchEvent)v0).type = type;
        ((TouchEvent)v0).scrollAmount = scrollAmount;
        input.touchEvents.add(v0);
    }
}

