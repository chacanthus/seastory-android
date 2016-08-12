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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;

public class Timer {
    class TimerThread implements LifecycleListener, Runnable {
        Application app;
        private long pauseMillis;

        public TimerThread() {
            super();
            Gdx.app.addLifecycleListener(((LifecycleListener)this));
            this.resume();
        }

        public void dispose() {
            this.pause();
            Gdx.app.removeLifecycleListener(((LifecycleListener)this));
            Timer.instances.clear();
            Timer.instance = null;
        }

        public void pause() {  // has try-catch handlers
            TimerThread v4 = null;
            this.pauseMillis = System.nanoTime() / 1000000;
            Application v0 = null;
            try {
                this.app = v0;
                Timer.wake();
                Timer.thread = v4;
                return;
            }
            catch(Throwable v0_1) {
                try {
                label_12:
                    throw v0_1;
                }
                catch(Throwable v0_1) {
                    goto label_12;
                }
            }
        }

        public void resume() {  // has try-catch handlers
            Application v4_1;
            long v0 = System.nanoTime() / 1000000 - this.pauseMillis;
            int v2 = 0;
            try {
                int v3 = Timer.instances.size;
                while(v2 < v3) {
                    Timer.instances.get(v2).delay(v0);
                    ++v2;
                }

                v4_1 = Gdx.app;
            }
            catch(Throwable v4) {
                goto label_22;
            }

            this.app = v4_1;
            new Thread(((Runnable)this), "Timer").start();
            return;
            try {
            label_22:
                throw v4;
            }
            catch(Throwable v4) {
                goto label_22;
            }
        }

        public void run() {  // has try-catch handlers
            // Decompilation failed
        }
    }

    private static final int CANCELLED = -1;
    private static final int FOREVER = -2;
    static Timer instance;
    static final Array instances;
    private final Array tasks;
    static TimerThread thread;

    static  {
        Timer.instances = new Array(1);
        Timer.instance = new Timer();
    }

    public Timer() {
        super();
        this.tasks = new Array(false, 8);
        this.start();
    }

    public void clear() {  // has try-catch handlers
        int v0 = 0;
        try {
            int v1 = this.tasks.size;
            while(v0 < v1) {
                this.tasks.get(v0).cancel();
                ++v0;
            }

            this.tasks.clear();
            return;
        label_14:
            throw v2;
        }
        catch(Throwable v2) {
            goto label_14;
        }
    }

    public void delay(long delayMillis) {  // has try-catch handlers
        int v0 = 0;
        try {
            int v1 = this.tasks.size;
            while(v0 < v1) {
                Object v2 = this.tasks.get(v0);
                ((Task)v2).executeTimeMillis += delayMillis;
                ++v0;
            }

            return;
        label_14:
            throw v3;
        }
        catch(Throwable v3) {
            goto label_14;
        }
    }

    public static Timer instance() {
        if(Timer.instance == null) {
            Timer.instance = new Timer();
        }

        return Timer.instance;
    }

    public static Task post(Task task) {
        return Timer.instance().postTask(task);
    }

    public Task postTask(Task task) {
        return this.scheduleTask(task, 0f, 0f, 0);
    }

    public static Task schedule(Task task, float delaySeconds) {
        return Timer.instance().scheduleTask(task, delaySeconds);
    }

    public static Task schedule(Task task, float delaySeconds, float intervalSeconds) {
        return Timer.instance().scheduleTask(task, delaySeconds, intervalSeconds);
    }

    public static Task schedule(Task task, float delaySeconds, float intervalSeconds, int repeatCount) {
        return Timer.instance().scheduleTask(task, delaySeconds, intervalSeconds, repeatCount);
    }

    public Task scheduleTask(Task task, float delaySeconds) {
        return this.scheduleTask(task, delaySeconds, 0f, 0);
    }

    public Task scheduleTask(Task task, float delaySeconds, float intervalSeconds) {
        return this.scheduleTask(task, delaySeconds, intervalSeconds, -2);
    }

    public Task scheduleTask(Task task, float delaySeconds, float intervalSeconds, int repeatCount) {  // has try-catch handlers
        float v4 = 1000f;
        if(task.repeatCount != -1) {
            throw new IllegalArgumentException("The same task may not be scheduled twice.");
        }

        task.executeTimeMillis = System.nanoTime() / 1000000 + (((long)(delaySeconds * v4)));
        task.intervalMillis = ((long)(intervalSeconds * v4));
        task.repeatCount = repeatCount;
        try {
            this.tasks.add(task);
            Timer.wake();
            return task;
        }
        catch(Throwable v0) {
            try {
            label_24:
                throw v0;
            }
            catch(Throwable v0) {
                goto label_24;
            }
        }
    }

    public void start() {  // has try-catch handlers
        try {
            if(!Timer.instances.contains(this, true)) {
                Timer.instances.add(this);
                if(Timer.thread == null) {
                    Timer.thread = new TimerThread();
                }

                Timer.wake();
            }

            return;
        label_15:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_15;
        }
    }

    public void stop() {  // has try-catch handlers
        try {
            Timer.instances.removeValue(this, true);
            return;
        label_6:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_6;
        }
    }

    long update(long timeMillis, long waitMillis) {  // has try-catch handlers
        int v5 = -1;
        int v0 = 0;
        try {
            int v1 = this.tasks.size;
            while(v0 < v1) {
                Object v2 = this.tasks.get(v0);
                if(((Task)v2).executeTimeMillis > timeMillis) {
                    waitMillis = Math.min(waitMillis, ((Task)v2).executeTimeMillis - timeMillis);
                }
                else {
                    if(((Task)v2).repeatCount != v5) {
                        if(((Task)v2).repeatCount == 0) {
                            ((Task)v2).repeatCount = -1;
                        }

                        Gdx.app.postRunnable(((Runnable)v2));
                    }

                    if(((Task)v2).repeatCount != v5) {
                        goto label_30;
                    }

                    this.tasks.removeIndex(v0);
                    --v0;
                    --v1;
                    goto label_13;
                label_30:
                    ((Task)v2).executeTimeMillis = ((Task)v2).intervalMillis + timeMillis;
                    waitMillis = Math.min(waitMillis, ((Task)v2).intervalMillis);
                    if(((Task)v2).repeatCount <= 0) {
                        goto label_13;
                    }

                    --((Task)v2).repeatCount;
                }

            label_13:
                ++v0;
            }

            return waitMillis;
        label_42:
            throw v3;
        }
        catch(Throwable v3) {
            goto label_42;
        }
    }

    static void wake() {  // has try-catch handlers
        try {
            Timer.instances.notifyAll();
            return;
        label_5:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_5;
        }
    }
}

