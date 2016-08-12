// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.lib;

import com.badlogic.gdx.math.Rectangle;
import com.dev.ss.Global;
import com.dev.ss.screen.GameScreen;
import com.dev.ss.screen.LoginScreen;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

public class Util {
    public static float screen_pixel_height;
    public static float screen_pixel_width;
    public static float standard_screen_pixel_height;
    public static float standard_screen_pixel_width;

    static  {
        Util.standard_screen_pixel_width = 768f;
        Util.standard_screen_pixel_height = 1280f;
    }

    public Util() {
        super();
    }

    public static float XCenter(float pixel_x, float _width) {
        return (pixel_x - Util.standard_screen_pixel_width / 2f) / Util.standard_screen_pixel_width - _width / 2f;
    }

    public static float YCenter(float pixel_y, float _height) {
        return (pixel_y - Util.standard_screen_pixel_height / 2f) / Util.standard_screen_pixel_height * -Util.screen_pixel_height / Util.screen_pixel_width - _height / 2f;
    }

    public static float getActorHeight(float pixel_height) {
        return Util.screen_pixel_height * pixel_height / Util.standard_screen_pixel_height;
    }

    public static float getActorLeft(float pixel_xpos) {
        return Util.screen_pixel_width * pixel_xpos / Util.standard_screen_pixel_width;
    }

    public static float getActorTop(float pixel_ypos, float actor_height) {
        return Util.screen_pixel_height - Util.screen_pixel_height * pixel_ypos / Util.standard_screen_pixel_height - actor_height;
    }

    public static float getActorWidth(float pixel_width) {
        return Util.screen_pixel_width * pixel_width / Util.standard_screen_pixel_width;
    }

    public static float getBottom(float pixel_y) {
        return (pixel_y - Util.standard_screen_pixel_height / 2f) / Util.standard_screen_pixel_height * -Util.screen_pixel_height / Util.screen_pixel_width;
    }

    public static String getExceptionMessage(Exception _e) {  // has try-catch handlers
        int v1;
        String v2;
        try {
            v2 = String.valueOf(_e.toString()) + " :: ";
            StackTraceElement[] v3 = _e.getStackTrace();
            v1 = v3.length - 1;
            while(true) {
            label_10:
                if(v1 >= 0) {
                    goto label_12;
                }

                goto label_11;
            label_12:
                v2 = String.valueOf(v2) + v3[v1].getClassName() + ":" + v3[v1].getMethodName() + "(" + v3[v1].getFileName() + ":" + v3[v1].getLineNumber() + ")";
                if(v1 != 0) {
                    v2 = String.valueOf(v2) + " -->> ";
                }

                break;
            }
        }
        catch(Exception v0) {
            goto label_44;
        }

        --v1;
        goto label_10;
    label_11:
        return v2;
    label_44:
        v0.printStackTrace();
        goto label_11;
    }

    public static float getHeight(float pixel_height) {
        return pixel_height / Util.standard_screen_pixel_height * Util.screen_pixel_height / Util.screen_pixel_width;
    }

    public static float getLeft(float pixel_x) {
        return (pixel_x - Util.standard_screen_pixel_width / 2f) / Util.standard_screen_pixel_width;
    }

    public static float getTop(float pixel_y, float _height) {
        return (pixel_y - Util.standard_screen_pixel_height / 2f) / Util.standard_screen_pixel_height * -Util.screen_pixel_height / Util.screen_pixel_width - _height;
    }

    public static float getWidth(float pixel_width) {
        return pixel_width / Util.standard_screen_pixel_width;
    }

    public static void ini(float _screen_pixel_width, float _screen_pixel_height) {
        Util.screen_pixel_width = _screen_pixel_width;
        Util.screen_pixel_height = _screen_pixel_height;
    }

    public static byte[] intTobyte(int integer, ByteOrder order) {  // has try-catch handlers
        byte[] v2;
        int v3 = 4;
        try {
            ByteBuffer v0 = ByteBuffer.allocate(v3);
            v0.order(order);
            v0.putInt(integer);
            v2 = v0.array();
        }
        catch(Exception v1) {
            v1.printStackTrace();
        }

        return v2;
    }

    public static boolean isEmptyStr(String str) {
        boolean v0;
        if(str == null || ("".equals(str))) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static boolean isInclude(String str, char[] ch) {  // has try-catch handlers
        int v1;
        boolean v2 = false;
        if(str == null) {
            goto label_5;
        }

        try {
            if(!"".equals(str)) {
                goto label_6;
            }

            goto label_5;
        label_6:
            v1 = 0;
            while(true) {
            label_7:
                if(v1 >= ch.length) {
                    goto label_5;
                }

                if(str.indexOf(ch[v1]) < 0) {
                    goto label_14;
                }

                break;
            }
        }
        catch(Exception v0) {
            goto label_17;
        }

        v2 = true;
        goto label_5;
    label_14:
        ++v1;
        goto label_7;
    label_5:
        return v2;
    label_17:
        v0.printStackTrace();
        goto label_5;
    }

    public static boolean isTouched(Rectangle r, float x, float y) {
        boolean v0;
        if(r.x > x || r.x + r.width < x || r.y > y || r.y + r.height < y) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public static int randomInt(int _min, int _max) {
        return (((int)(Math.random() * (((double)(_max - _min + 1)))))) + _min;
    }

    public static boolean randomTrue(int percentage) {
        boolean v1;
        if(percentage >= (((int)(Math.random() * 100))) + 1) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        return v1;
    }

    public static int strToint(String str, int iDefaultValue) {  // has try-catch handlers
        int v0;
        try {
            v0 = Integer.parseInt(str);
        }
        catch(Exception v1) {
        }

        return v0;
    }

    public static void sysout(String type, String pos, String msg) {  // has try-catch handlers
        try {
            if(!"1".equals(Global.errorReport)) {
                goto label_24;
            }

            if("DEBUG".equalsIgnoreCase(type)) {
                goto label_24;
            }

            if("INFO".equalsIgnoreCase(type)) {
                goto label_24;
            }

            LoginScreen.lsLoginWrite.add("G001\t" + type + " :: " + pos + " :: " + msg + "\n");
            goto label_24;
        }
        catch(Exception v0) {
            goto label_24;
        }

        try {
        label_24:
            if(!"1".equals(Global.errorReport)) {
                goto label_48;
            }

            if("DEBUG".equalsIgnoreCase(type)) {
                goto label_48;
            }

            if("INFO".equalsIgnoreCase(type)) {
                goto label_48;
            }

            GameScreen.lsGameWrite.add("G001\t" + type + " :: " + pos + " :: " + msg + "\n");
        }
        catch(Exception v0) {
        }

    label_48:
        System.out.println(String.valueOf(type) + " :: " + pos + " :: " + msg);
    }

    public static void sysout(String type, String pos, String msg, String originalMsg) {  // has try-catch handlers
        try {
            if(!"1".equals(Global.errorReport)) {
                goto label_27;
            }

            if("DEBUG".equalsIgnoreCase(type)) {
                goto label_27;
            }

            if("INFO".equalsIgnoreCase(type)) {
                goto label_27;
            }

            LoginScreen.lsLoginWrite.add("G001\t" + type + " :: " + pos + " :: " + msg + " :: " + originalMsg + "\n");
            goto label_27;
        }
        catch(Exception v0) {
            goto label_27;
        }

        try {
        label_27:
            if(!"1".equals(Global.errorReport)) {
                goto label_54;
            }

            if("DEBUG".equalsIgnoreCase(type)) {
                goto label_54;
            }

            if("INFO".equalsIgnoreCase(type)) {
                goto label_54;
            }

            GameScreen.lsGameWrite.add("G001\t" + type + " :: " + pos + " :: " + msg + " :: " + originalMsg + "\n");
        }
        catch(Exception v0) {
        }

    label_54:
        System.out.println(String.valueOf(type) + " :: " + pos + " :: " + msg + " :: " + originalMsg);
    }

    public static String toNoCmdString(String[] aMsg) {  // has try-catch handlers
        String v2 = "";
        int v1 = 1;
        try {
            while(v1 < aMsg.length) {
                v2 = String.valueOf(v2) + aMsg[v1] + "\t";
                ++v1;
            }

        label_4:
            return v2;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Util->toNoCmdString", Util.getExceptionMessage(v0), "");
            goto label_4;
        }
    }

    public static String toString(String[] aMsg) {  // has try-catch handlers
        String v2 = "";
        int v1 = 0;
        try {
            while(v1 < aMsg.length) {
                v2 = String.valueOf(v2) + aMsg[v1] + "\t";
                ++v1;
            }

        label_4:
            return v2;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Util->toString", Util.getExceptionMessage(v0), "");
            goto label_4;
        }
    }

    public static String toTime(int iTime) {  // has try-catch handlers
        String v6;
        String v4;
        int v9 = 10;
        String v5 = "";
        try {
            int v3 = iTime % 60;
            int v2 = (((int)((((float)iTime)) / 60f))) % 60;
            int v1 = ((int)((((float)iTime)) / 3600f));
            if(v2 < v9) {
                v4 = "0" + v2;
            }
            else {
                v4 = String.valueOf(v2);
            }

            if(v3 < v9) {
                v6 = "0" + v3;
            }
            else {
                v6 = String.valueOf(v3);
            }

            if(v1 <= 0) {
                goto label_42;
            }

            v5 = String.valueOf(String.valueOf(v1)) + "???? " + v4 + "?? " + v6 + "??";
            goto label_37;
        label_42:
            v5 = String.valueOf(v4) + "?? " + v6 + "??";
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Util->toTime", Util.getExceptionMessage(v0), "");
        }

    label_37:
        return v5;
    }

    public static String withComma(long money) {
        return new DecimalFormat("#,###,###,###").format(money);
    }
}

