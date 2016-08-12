// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.Vector2;

public enum Scaling {
    public static final enum Scaling fill;
    public static final enum Scaling fillX;
    public static final enum Scaling fillY;
    public static final enum Scaling fit;
    public static final enum Scaling none;
    public static final enum Scaling stretch;
    public static final enum Scaling stretchX;
    public static final enum Scaling stretchY;
    private static final Vector2 temp;

    static  {
        Scaling.fit = new Scaling("fit", 0);
        Scaling.fill = new Scaling("fill", 1);
        Scaling.fillX = new Scaling("fillX", 2);
        Scaling.fillY = new Scaling("fillY", 3);
        Scaling.stretch = new Scaling("stretch", 4);
        Scaling.stretchX = new Scaling("stretchX", 5);
        Scaling.stretchY = new Scaling("stretchY", 6);
        Scaling.none = new Scaling("none", 7);
        Scaling[] v0 = new Scaling[8];
        v0[0] = Scaling.fit;
        v0[1] = Scaling.fill;
        v0[2] = Scaling.fillX;
        v0[3] = Scaling.fillY;
        v0[4] = Scaling.stretch;
        v0[5] = Scaling.stretchX;
        v0[6] = Scaling.stretchY;
        v0[7] = Scaling.none;
        Scaling.$VALUES = v0;
        Scaling.temp = new Vector2();
    }

    private Scaling(String arg1, int arg2) {
        super(arg1, arg2);
    }

    public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
        float v0;
        switch(com.badlogic.gdx.utils.Scaling$1.$SwitchMap$com$badlogic$gdx$utils$Scaling[this.ordinal()]) {
            case 1: {
                if(targetHeight / targetWidth > sourceHeight / sourceWidth) {
                    v0 = targetWidth / sourceWidth;
                }
                else {
                    v0 = targetHeight / sourceHeight;
                }

                Scaling.temp.x = sourceWidth * v0;
                Scaling.temp.y = sourceHeight * v0;
                break;
            }
            case 2: {
                if(targetHeight / targetWidth < sourceHeight / sourceWidth) {
                    v0 = targetWidth / sourceWidth;
                }
                else {
                    v0 = targetHeight / sourceHeight;
                }

                Scaling.temp.x = sourceWidth * v0;
                Scaling.temp.y = sourceHeight * v0;
                break;
            }
            case 3: {
                v0 = targetWidth / sourceWidth;
                Scaling.temp.x = sourceWidth * v0;
                Scaling.temp.y = sourceHeight * v0;
                break;
            }
            case 4: {
                v0 = targetHeight / sourceHeight;
                Scaling.temp.x = sourceWidth * v0;
                Scaling.temp.y = sourceHeight * v0;
                break;
            }
            case 5: {
                Scaling.temp.x = targetWidth;
                Scaling.temp.y = targetHeight;
                break;
            }
            case 6: {
                Scaling.temp.x = targetWidth;
                Scaling.temp.y = sourceHeight;
                break;
            }
            case 7: {
                Scaling.temp.x = sourceWidth;
                Scaling.temp.y = targetHeight;
                break;
            }
            case 8: {
                Scaling.temp.x = sourceWidth;
                Scaling.temp.y = sourceHeight;
                break;
            }
        }

        return Scaling.temp;
    }

    public static Scaling valueOf(String name) {
        return Enum.valueOf(Scaling.class, name);
    }

    public static Scaling[] values() {
        return Scaling.$VALUES.clone();
    }
}

