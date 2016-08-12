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

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View$OnGenericMotionListener;
import com.badlogic.gdx.Application;
import java.util.ArrayList;

public class AndroidInputThreePlus extends AndroidInput implements View$OnGenericMotionListener {
    ArrayList genericMotionListeners;
    private final AndroidMouseHandler mouseHandler;

    public AndroidInputThreePlus(Application activity, Context context, Object view, AndroidApplicationConfiguration config) {
        super(activity, context, view, config);
        this.genericMotionListeners = new ArrayList();
        if((view instanceof View)) {
            view.setOnGenericMotionListener(((View$OnGenericMotionListener)this));
        }

        this.mouseHandler = new AndroidMouseHandler();
    }

    public void addGenericMotionListener(View$OnGenericMotionListener listener) {
        this.genericMotionListeners.add(listener);
    }

    public boolean onGenericMotion(View view, MotionEvent event) {
        boolean v2;
        if(this.mouseHandler.onGenericMotion(event, ((AndroidInput)this))) {
            v2 = true;
        }
        else {
            int v0 = 0;
            int v1 = this.genericMotionListeners.size();
            while(true) {
                if(v0 >= v1) {
                    break;
                }
                else if(this.genericMotionListeners.get(v0).onGenericMotion(view, event)) {
                    v2 = true;
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_5;
            }

            v2 = false;
        }

    label_5:
        return v2;
    }
}

