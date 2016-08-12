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

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.TimeUtils;

public class InputProcessorQueue implements InputProcessor {
    private static final int KEY_DOWN = 0;
    private static final int KEY_TYPED = 2;
    private static final int KEY_UP = 1;
    private static final int MOUSE_MOVED = 6;
    private static final int SCROLLED = 7;
    private static final int TOUCH_DOWN = 3;
    private static final int TOUCH_DRAGGED = 5;
    private static final int TOUCH_UP = 4;
    private long currentEventTime;
    private final IntArray processingQueue;
    private InputProcessor processor;
    private final IntArray queue;

    public InputProcessorQueue() {
        super();
        this.queue = new IntArray();
        this.processingQueue = new IntArray();
    }

    public InputProcessorQueue(InputProcessor processor) {
        super();
        this.queue = new IntArray();
        this.processingQueue = new IntArray();
        this.processor = processor;
    }

    public void drain() {  // has try-catch handlers
        int v7;
        int v6;
        int v5;
        InputProcessor v4_2;
        IntArray v3 = this.processingQueue;
        try {
            if(this.processor == null) {
                this.queue.clear();
            }
            else {
                v3.addAll(this.queue);
                this.queue.clear();
                goto label_11;
            }

            return;
        }
        catch(Throwable v4) {
            goto label_32;
        }

    label_11:
        int v2 = v3.size;
        int v1;
        for(v1 = 0; v1 < v2; v1 = v0) {
            int v0 = v1 + 1;
            long v4_1 = (((long)v3.get(v1))) << 32;
            v1 = v0 + 1;
            this.currentEventTime = v4_1 | (((long)v3.get(v0))) & 4294967295L;
            v0 = v1 + 1;
            switch(v3.get(v1)) {
                case 0: {
                    this.processor.keyDown(v3.get(v0));
                    ++v0;
                    break;
                }
                case 1: {
                    this.processor.keyUp(v3.get(v0));
                    ++v0;
                    break;
                }
                case 2: {
                    this.processor.keyTyped(((char)v3.get(v0)));
                    ++v0;
                    break;
                }
                case 3: {
                    v4_2 = this.processor;
                    v1 = v0 + 1;
                    v5 = v3.get(v0);
                    v0 = v1 + 1;
                    v6 = v3.get(v1);
                    v1 = v0 + 1;
                    v7 = v3.get(v0);
                    v0 = v1 + 1;
                    v4_2.touchDown(v5, v6, v7, v3.get(v1));
                    break;
                }
                case 4: {
                    v4_2 = this.processor;
                    v1 = v0 + 1;
                    v5 = v3.get(v0);
                    v0 = v1 + 1;
                    v6 = v3.get(v1);
                    v1 = v0 + 1;
                    v7 = v3.get(v0);
                    v0 = v1 + 1;
                    v4_2.touchUp(v5, v6, v7, v3.get(v1));
                    break;
                }
                case 5: {
                    v4_2 = this.processor;
                    v1 = v0 + 1;
                    v5 = v3.get(v0);
                    v0 = v1 + 1;
                    v4_2.touchDragged(v5, v3.get(v1), v3.get(v0));
                    ++v0;
                    break;
                }
                case 6: {
                    v4_2 = this.processor;
                    v1 = v0 + 1;
                    v5 = v3.get(v0);
                    v0 = v1 + 1;
                    v4_2.mouseMoved(v5, v3.get(v1));
                    break;
                }
                case 7: {
                    this.processor.scrolled(v3.get(v0));
                    ++v0;
                    break;
                }
            }
        }

        v3.clear();
        return;
        try {
        label_32:
            throw v4;
        }
        catch(Throwable v4) {
            goto label_32;
        }
    }

    public long getCurrentEventTime() {
        return this.currentEventTime;
    }

    public InputProcessor getProcessor() {
        return this.processor;
    }

    public boolean keyDown(int keycode) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(0);
            this.queue.add(keycode);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public boolean keyTyped(char character) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(2);
            this.queue.add(character);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public boolean keyUp(int keycode) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(1);
            this.queue.add(keycode);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public boolean mouseMoved(int screenX, int screenY) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(6);
            this.queue.add(screenX);
            this.queue.add(screenY);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    private void queueTime() {
        long v0 = TimeUtils.nanoTime();
        this.queue.add(((int)(v0 >> 32)));
        this.queue.add(((int)v0));
    }

    public boolean scrolled(int amount) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(7);
            this.queue.add(amount);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public void setProcessor(InputProcessor processor) {
        this.processor = processor;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(3);
            this.queue.add(screenX);
            this.queue.add(screenY);
            this.queue.add(pointer);
            this.queue.add(button);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(5);
            this.queue.add(screenX);
            this.queue.add(screenY);
            this.queue.add(pointer);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {  // has try-catch handlers
        try {
            this.queueTime();
            this.queue.add(4);
            this.queue.add(screenX);
            this.queue.add(screenY);
            this.queue.add(pointer);
            this.queue.add(button);
            return 0;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }
}

