// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer$Task;

public class GestureDetector extends InputAdapter {
    public class GestureAdapter implements GestureListener {
        public GestureAdapter() {
            super();
        }

        public boolean fling(float velocityX, float velocityY, int button) {
            return 0;
        }

        public boolean longPress(float x, float y) {
            return 0;
        }

        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return 0;
        }

        public boolean panStop(float x, float y, int pointer, int button) {
            return 0;
        }

        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return 0;
        }

        public boolean tap(float x, float y, int count, int button) {
            return 0;
        }

        public boolean touchDown(float x, float y, int pointer, int button) {
            return 0;
        }

        public boolean zoom(float initialDistance, float distance) {
            return 0;
        }
    }

    public abstract interface GestureListener {
        public abstract boolean fling(float arg0, float arg1, int arg2);

        public abstract boolean longPress(float arg0, float arg1);

        public abstract boolean pan(float arg0, float arg1, float arg2, float arg3);

        public abstract boolean panStop(float arg0, float arg1, int arg2, int arg3);

        public abstract boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3);

        public abstract boolean tap(float arg0, float arg1, int arg2, int arg3);

        public abstract boolean touchDown(float arg0, float arg1, int arg2, int arg3);

        public abstract boolean zoom(float arg0, float arg1);
    }

    class VelocityTracker {
        float deltaX;
        float deltaY;
        long lastTime;
        float lastX;
        float lastY;
        long[] meanTime;
        float[] meanX;
        float[] meanY;
        int numSamples;
        int sampleSize;

        VelocityTracker() {
            super();
            this.sampleSize = 10;
            this.meanX = new float[this.sampleSize];
            this.meanY = new float[this.sampleSize];
            this.meanTime = new long[this.sampleSize];
        }

        private float getAverage(float[] values, int numSamples) {
            numSamples = Math.min(this.sampleSize, numSamples);
            float v1 = 0f;
            int v0;
            for(v0 = 0; v0 < numSamples; ++v0) {
                v1 += values[v0];
            }

            return v1 / (((float)numSamples));
        }

        private long getAverage(long[] values, int numSamples) {
            long v4;
            numSamples = Math.min(this.sampleSize, numSamples);
            long v2 = 0;
            int v0;
            for(v0 = 0; v0 < numSamples; ++v0) {
                v2 += values[v0];
            }

            if(numSamples == 0) {
                v4 = 0;
            }
            else {
                v4 = v2 / (((long)numSamples));
            }

            return v4;
        }

        private float getSum(float[] values, int numSamples) {
            numSamples = Math.min(this.sampleSize, numSamples);
            float v1 = 0f;
            int v0;
            for(v0 = 0; v0 < numSamples; ++v0) {
                v1 += values[v0];
            }

            if(numSamples == 0) {
                v1 = 0f;
            }

            return v1;
        }

        public float getVelocityX() {
            float v2 = 0f;
            float v1 = this.getAverage(this.meanX, this.numSamples);
            float v0 = (((float)this.getAverage(this.meanTime, this.numSamples))) / 1000000000f;
            if(v0 != 0f) {
                v2 = v1 / v0;
            }

            return v2;
        }

        public float getVelocityY() {
            float v2 = 0f;
            float v1 = this.getAverage(this.meanY, this.numSamples);
            float v0 = (((float)this.getAverage(this.meanTime, this.numSamples))) / 1000000000f;
            if(v0 != 0f) {
                v2 = v1 / v0;
            }

            return v2;
        }

        public void start(float x, float y, long timeStamp) {
            this.lastX = x;
            this.lastY = y;
            this.deltaX = 0f;
            this.deltaY = 0f;
            this.numSamples = 0;
            int v0;
            for(v0 = 0; v0 < this.sampleSize; ++v0) {
                this.meanX[v0] = 0f;
                this.meanY[v0] = 0f;
                this.meanTime[v0] = 0;
            }

            this.lastTime = timeStamp;
        }

        public void update(float x, float y, long timeStamp) {
            this.deltaX = x - this.lastX;
            this.deltaY = y - this.lastY;
            this.lastX = x;
            this.lastY = y;
            long v2 = timeStamp - this.lastTime;
            this.lastTime = timeStamp;
            int v4 = this.numSamples % this.sampleSize;
            this.meanX[v4] = this.deltaX;
            this.meanY[v4] = this.deltaY;
            this.meanTime[v4] = v2;
            ++this.numSamples;
        }
    }

    private long gestureStartTime;
    private boolean inTapSquare;
    private final Vector2 initialPointer1;
    private final Vector2 initialPointer2;
    private int lastTapButton;
    private int lastTapPointer;
    private long lastTapTime;
    private float lastTapX;
    private float lastTapY;
    final GestureListener listener;
    boolean longPressFired;
    private float longPressSeconds;
    private final Task longPressTask;
    private long maxFlingDelay;
    private boolean panning;
    private boolean pinching;
    Vector2 pointer1;
    private final Vector2 pointer2;
    private int tapCount;
    private long tapCountInterval;
    private float tapSquareCenterX;
    private float tapSquareCenterY;
    private float tapSquareSize;
    private final VelocityTracker tracker;

    public GestureDetector(float halfTapSquareSize, float tapCountInterval, float longPressDuration, float maxFlingDelay, GestureListener listener) {
        super();
        this.tracker = new VelocityTracker();
        this.pointer1 = new Vector2();
        this.pointer2 = new Vector2();
        this.initialPointer1 = new Vector2();
        this.initialPointer2 = new Vector2();
        this.longPressTask = new Task() {
            public void run() {
                if(!GestureDetector.this.longPressFired) {
                    GestureDetector.this.longPressFired = GestureDetector.this.listener.longPress(GestureDetector.this.pointer1.x, GestureDetector.this.pointer1.y);
                }
            }
        };
        this.tapSquareSize = halfTapSquareSize;
        this.tapCountInterval = ((long)(tapCountInterval * 1000000000f));
        this.longPressSeconds = longPressDuration;
        this.maxFlingDelay = ((long)(maxFlingDelay * 1000000000f));
        this.listener = listener;
    }

    public GestureDetector(GestureListener listener) {
        this(20f, 0.4f, 1.1f, 0.15f, listener);
    }

    public void cancel() {
        this.longPressTask.cancel();
        this.longPressFired = true;
    }

    public void invalidateTapSquare() {
        this.inTapSquare = false;
    }

    public boolean isLongPressed() {
        return this.isLongPressed(this.longPressSeconds);
    }

    public boolean isLongPressed(float duration) {
        boolean v0 = false;
        if(this.gestureStartTime != 0 && TimeUtils.nanoTime() - this.gestureStartTime > (((long)(1000000000f * duration)))) {
            v0 = true;
        }

        return v0;
    }

    public boolean isPanning() {
        return this.panning;
    }

    private boolean isWithinTapSquare(float x, float y, float centerX, float centerY) {
        boolean v0;
        if(Math.abs(x - centerX) >= this.tapSquareSize || Math.abs(y - centerY) >= this.tapSquareSize) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public void reset() {
        this.gestureStartTime = 0;
        this.panning = false;
        this.inTapSquare = false;
    }

    public void setLongPressSeconds(float longPressSeconds) {
        this.longPressSeconds = longPressSeconds;
    }

    public void setMaxFlingDelay(long maxFlingDelay) {
        this.maxFlingDelay = maxFlingDelay;
    }

    public void setTapCountInterval(float tapCountInterval) {
        this.tapCountInterval = ((long)(1000000000f * tapCountInterval));
    }

    public void setTapSquareSize(float halfTapSquareSize) {
        this.tapSquareSize = halfTapSquareSize;
    }

    public boolean touchDown(float x, float y, int pointer, int button) {
        boolean v0 = false;
        if(pointer <= 1) {
            if(pointer == 0) {
                this.pointer1.set(x, y);
                this.gestureStartTime = Gdx.input.getCurrentEventTime();
                this.tracker.start(x, y, this.gestureStartTime);
                if(Gdx.input.isTouched(1)) {
                    this.inTapSquare = false;
                    this.pinching = true;
                    this.initialPointer1.set(this.pointer1);
                    this.initialPointer2.set(this.pointer2);
                    this.longPressTask.cancel();
                }
                else {
                    this.inTapSquare = true;
                    this.pinching = false;
                    this.longPressFired = false;
                    this.tapSquareCenterX = x;
                    this.tapSquareCenterY = y;
                    if(!this.longPressTask.isScheduled()) {
                        Timer.schedule(this.longPressTask, this.longPressSeconds);
                    }
                }
            }
            else {
                this.pointer2.set(x, y);
                this.inTapSquare = false;
                this.pinching = true;
                this.initialPointer1.set(this.pointer1);
                this.initialPointer2.set(this.pointer2);
                this.longPressTask.cancel();
            }

            v0 = this.listener.touchDown(x, y, pointer, button);
        }

        return v0;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        return this.touchDown(((float)x), ((float)y), pointer, button);
    }

    public boolean touchDragged(float x, float y, int pointer) {
        boolean v1 = false;
        if(pointer <= 1 && !this.longPressFired) {
            if(pointer == 0) {
                this.pointer1.set(x, y);
            }
            else {
                this.pointer2.set(x, y);
            }

            if(!this.pinching) {
                goto label_34;
            }

            if(this.listener == null) {
                goto label_3;
            }

            boolean v0 = this.listener.pinch(this.initialPointer1, this.initialPointer2, this.pointer1, this.pointer2);
            if(!this.listener.zoom(this.initialPointer1.dst(this.initialPointer2), this.pointer1.dst(this.pointer2)) && !v0) {
                goto label_3;
            }

            v1 = true;
            goto label_3;
        label_34:
            this.tracker.update(x, y, Gdx.input.getCurrentEventTime());
            if((this.inTapSquare) && !this.isWithinTapSquare(x, y, this.tapSquareCenterX, this.tapSquareCenterY)) {
                this.longPressTask.cancel();
                this.inTapSquare = false;
            }

            if(this.inTapSquare) {
                goto label_3;
            }

            this.panning = true;
            v1 = this.listener.pan(x, y, this.tracker.deltaX, this.tracker.deltaY);
        }

    label_3:
        return v1;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        return this.touchDragged(((float)x), ((float)y), pointer);
    }

    public boolean touchUp(float x, float y, int pointer, int button) {
        boolean v0;
        if(pointer > 1) {
            v0 = false;
        }
        else {
            if((this.inTapSquare) && !this.isWithinTapSquare(x, y, this.tapSquareCenterX, this.tapSquareCenterY)) {
                this.inTapSquare = false;
            }

            boolean v1 = this.panning;
            this.panning = false;
            this.longPressTask.cancel();
            if(!this.longPressFired) {
                goto label_21;
            }

            v0 = false;
            goto label_3;
        label_21:
            if(!this.inTapSquare) {
                goto label_53;
            }

            if(this.lastTapButton != button || this.lastTapPointer != pointer || TimeUtils.nanoTime() - this.lastTapTime > this.tapCountInterval || !this.isWithinTapSquare(x, y, this.lastTapX, this.lastTapY)) {
                this.tapCount = 0;
            }

            ++this.tapCount;
            this.lastTapTime = TimeUtils.nanoTime();
            this.lastTapX = x;
            this.lastTapY = y;
            this.lastTapButton = button;
            this.lastTapPointer = pointer;
            this.gestureStartTime = 0;
            v0 = this.listener.tap(x, y, this.tapCount, button);
            goto label_3;
        label_53:
            if(!this.pinching) {
                goto label_79;
            }

            this.pinching = false;
            this.panning = true;
            if(pointer == 0) {
                this.tracker.start(this.pointer2.x, this.pointer2.y, Gdx.input.getCurrentEventTime());
            }
            else {
                this.tracker.start(this.pointer1.x, this.pointer1.y, Gdx.input.getCurrentEventTime());
            }

            v0 = false;
            goto label_3;
        label_79:
            v0 = false;
            if((v1) && !this.panning) {
                v0 = this.listener.panStop(x, y, pointer, button);
            }

            this.gestureStartTime = 0;
            long v2 = Gdx.input.getCurrentEventTime();
            if(v2 - this.tracker.lastTime >= this.maxFlingDelay) {
                goto label_3;
            }

            this.tracker.update(x, y, v2);
            if(!this.listener.fling(this.tracker.getVelocityX(), this.tracker.getVelocityY(), button) && !v0) {
                v0 = false;
                goto label_3;
            }

            v0 = true;
        }

    label_3:
        return v0;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        return this.touchUp(((float)x), ((float)y), pointer, button);
    }
}

