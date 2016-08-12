// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx;

import com.badlogic.gdx.utils.Clipboard;

public abstract interface Application {
    public enum ApplicationType {
        public static final enum ApplicationType Android;
        public static final enum ApplicationType Applet;
        public static final enum ApplicationType Desktop;
        public static final enum ApplicationType HeadlessDesktop;
        public static final enum ApplicationType WebGL;
        public static final enum ApplicationType iOS;

        static  {
            ApplicationType.Android = new ApplicationType("Android", 0);
            ApplicationType.Desktop = new ApplicationType("Desktop", 1);
            ApplicationType.HeadlessDesktop = new ApplicationType("HeadlessDesktop", 2);
            ApplicationType.Applet = new ApplicationType("Applet", 3);
            ApplicationType.WebGL = new ApplicationType("WebGL", 4);
            ApplicationType.iOS = new ApplicationType("iOS", 5);
            ApplicationType[] v0 = new ApplicationType[6];
            v0[0] = ApplicationType.Android;
            v0[1] = ApplicationType.Desktop;
            v0[2] = ApplicationType.HeadlessDesktop;
            v0[3] = ApplicationType.Applet;
            v0[4] = ApplicationType.WebGL;
            v0[5] = ApplicationType.iOS;
            ApplicationType.$VALUES = v0;
        }

        private ApplicationType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static ApplicationType valueOf(String name) {
            return Enum.valueOf(ApplicationType.class, name);
        }

        public static ApplicationType[] values() {
            return ApplicationType.$VALUES.clone();
        }
    }

    public static final int LOG_DEBUG = 3;
    public static final int LOG_ERROR = 1;
    public static final int LOG_INFO = 2;
    public static final int LOG_NONE;

    public abstract void addLifecycleListener(LifecycleListener arg0);

    public abstract void debug(String arg0, String arg1);

    public abstract void debug(String arg0, String arg1, Throwable arg2);

    public abstract void error(String arg0, String arg1);

    public abstract void error(String arg0, String arg1, Throwable arg2);

    public abstract void exit();

    public abstract ApplicationListener getApplicationListener();

    public abstract Audio getAudio();

    public abstract Clipboard getClipboard();

    public abstract Files getFiles();

    public abstract Graphics getGraphics();

    public abstract Input getInput();

    public abstract long getJavaHeap();

    public abstract int getLogLevel();

    public abstract long getNativeHeap();

    public abstract Net getNet();

    public abstract Preferences getPreferences(String arg0);

    public abstract ApplicationType getType();

    public abstract int getVersion();

    public abstract void log(String arg0, String arg1);

    public abstract void log(String arg0, String arg1, Throwable arg2);

    public abstract void postRunnable(Runnable arg0);

    public abstract void removeLifecycleListener(LifecycleListener arg0);

    public abstract void setLogLevel(int arg0);
}

