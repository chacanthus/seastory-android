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

public class PauseableThread extends Thread {
    boolean exit;
    boolean paused;
    final Runnable runnable;

    public PauseableThread(Runnable runnable) {
        super();
        this.paused = false;
        this.exit = false;
        this.runnable = runnable;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void onPause() {
        this.paused = true;
    }

    public void onResume() {  // has try-catch handlers
        try {
            this.paused = false;
            this.notifyAll();
            return;
        label_5:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_5;
        }
    }

    public void run() {  // has try-catch handlers
        try {
            while(true) {
            label_0:
                if(!this.paused) {
                    goto label_6;
                }

                this.wait();
            }
        }
        catch(Throwable v1) {
            try {
            label_10:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_10;
            }
        }
        catch(InterruptedException v0) {
            try {
                v0.printStackTrace();
            label_6:
                if(!this.exit) {
                    goto label_11;
                }
            }
            catch(Throwable v1) {
                goto label_10;
            }

            return;
        label_11:
            this.runnable.run();
            goto label_0;
        }
    }

    public void stopThread() {
        this.exit = true;
        if(this.paused) {
            this.onResume();
        }
    }
}

