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

public class UIUtils {
    public static boolean isLinux;
    public static boolean isMac;
    public static boolean isWindows;

    static  {
        UIUtils.isMac = System.getProperty("os.name").contains("OS X");
        UIUtils.isWindows = System.getProperty("os.name").contains("Windows");
        UIUtils.isLinux = System.getProperty("os.name").contains("Linux");
    }

    public UIUtils() {
        super();
    }

    public static boolean alt() {
        boolean v0;
        if((Gdx.input.isKeyPressed(57)) || (Gdx.input.isKeyPressed(58))) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean alt(int keycode) {
        boolean v0;
        if(keycode == 57 || keycode == 58) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean ctrl() {
        boolean v0;
        if(UIUtils.isMac) {
            v0 = Gdx.input.isKeyPressed(63);
        }
        else {
            if(!Gdx.input.isKeyPressed(129) && !Gdx.input.isKeyPressed(130)) {
                v0 = false;
                goto label_5;
            }

            v0 = true;
        }

    label_5:
        return v0;
    }

    public static boolean ctrl(int keycode) {
        boolean v0 = true;
        boolean v1 = false;
        if(!UIUtils.isMac) {
            if(keycode == 129 || keycode == 130) {
                v1 = true;
            }

            v0 = v1;
        }
        else if(keycode != 63) {
            v0 = false;
        }

        return v0;
    }

    public static boolean left() {
        return Gdx.input.isButtonPressed(0);
    }

    public static boolean left(int button) {
        boolean v0;
        if(button == 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean middle() {
        return Gdx.input.isButtonPressed(2);
    }

    public static boolean middle(int button) {
        boolean v0;
        if(button == 2) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean right() {
        return Gdx.input.isButtonPressed(1);
    }

    public static boolean right(int button) {
        boolean v0 = true;
        if(button != 1) {
            v0 = false;
        }

        return v0;
    }

    public static boolean shift() {
        boolean v0;
        if((Gdx.input.isKeyPressed(59)) || (Gdx.input.isKeyPressed(60))) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean shift(int keycode) {
        boolean v0;
        if(keycode == 59 || keycode == 60) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

