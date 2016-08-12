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
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.utils.Array;

public abstract interface AndroidApplicationBase extends Application {
    public static final int MINIMUM_SDK = 8;

    public abstract Window getApplicationWindow();

    public abstract Context getContext();

    public abstract Array getExecutedRunnables();

    public abstract Handler getHandler();

    public abstract AndroidInput getInput();

    public abstract Array getLifecycleListeners();

    public abstract Array getRunnables();

    public abstract WindowManager getWindowManager();

    public abstract void runOnUiThread(Runnable arg0);

    public abstract void startActivity(Intent arg0);

    public abstract void useImmersiveMode(boolean arg0);
}

