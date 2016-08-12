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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input$Orientation;
import com.badlogic.gdx.Input$Peripheral;
import com.badlogic.gdx.Input$TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class RemoteInput implements Input, Runnable {
    class EventTrigger implements Runnable {
        KeyEvent keyEvent;
        TouchEvent touchEvent;

        public EventTrigger(RemoteInput arg1, TouchEvent touchEvent, KeyEvent keyEvent) {
            RemoteInput.this = arg1;
            super();
            this.touchEvent = touchEvent;
            this.keyEvent = keyEvent;
        }

        public void run() {
            RemoteInput.this.justTouched = false;
            if(RemoteInput.this.keyJustPressed) {
                RemoteInput.this.keyJustPressed = false;
                int v0;
                for(v0 = 0; v0 < RemoteInput.this.justPressedKeys.length; ++v0) {
                    RemoteInput.this.justPressedKeys[v0] = false;
                }
            }

            if(RemoteInput.this.processor != null) {
                if(this.touchEvent != null) {
                    RemoteInput.this.touchX[this.touchEvent.pointer] = this.touchEvent.x;
                    RemoteInput.this.touchY[this.touchEvent.pointer] = this.touchEvent.y;
                    switch(this.touchEvent.type) {
                        case 0: {
                            goto label_47;
                        }
                        case 1: {
                            goto label_64;
                        }
                        case 2: {
                            goto label_79;
                        }
                    }

                    goto label_41;
                label_47:
                    RemoteInput.this.processor.touchDown(this.touchEvent.x, this.touchEvent.y, this.touchEvent.pointer, 0);
                    RemoteInput.this.isTouched[this.touchEvent.pointer] = true;
                    RemoteInput.this.justTouched = true;
                    goto label_41;
                label_79:
                    RemoteInput.this.processor.touchDragged(this.touchEvent.x, this.touchEvent.y, this.touchEvent.pointer);
                    goto label_41;
                label_64:
                    RemoteInput.this.processor.touchUp(this.touchEvent.x, this.touchEvent.y, this.touchEvent.pointer, 0);
                    RemoteInput.this.isTouched[this.touchEvent.pointer] = false;
                }

            label_41:
                if(this.keyEvent == null) {
                    return;
                }

                switch(this.keyEvent.type) {
                    case 0: {
                        goto label_89;
                    }
                    case 1: {
                        goto label_117;
                    }
                    case 2: {
                        goto label_138;
                    }
                }

                return;
            label_117:
                RemoteInput.this.processor.keyUp(this.keyEvent.keyCode);
                if(!RemoteInput.this.keys[this.keyEvent.keyCode]) {
                    return;
                }

                --RemoteInput.this.keyCount;
                RemoteInput.this.keys[this.keyEvent.keyCode] = false;
                return;
            label_89:
                RemoteInput.this.processor.keyDown(this.keyEvent.keyCode);
                if(!RemoteInput.this.keys[this.keyEvent.keyCode]) {
                    ++RemoteInput.this.keyCount;
                    RemoteInput.this.keys[this.keyEvent.keyCode] = true;
                }

                RemoteInput.this.keyJustPressed = true;
                RemoteInput.this.justPressedKeys[this.keyEvent.keyCode] = true;
                return;
            label_138:
                RemoteInput.this.processor.keyTyped(this.keyEvent.keyChar);
            }
            else {
                if(this.touchEvent != null) {
                    RemoteInput.this.touchX[this.touchEvent.pointer] = this.touchEvent.x;
                    RemoteInput.this.touchY[this.touchEvent.pointer] = this.touchEvent.y;
                    if(this.touchEvent.type == 0) {
                        RemoteInput.this.isTouched[this.touchEvent.pointer] = true;
                        RemoteInput.this.justTouched = true;
                    }

                    if(this.touchEvent.type != 1) {
                        goto label_178;
                    }

                    RemoteInput.this.isTouched[this.touchEvent.pointer] = false;
                }

            label_178:
                if(this.keyEvent == null) {
                    return;
                }

                if(this.keyEvent.type == 0) {
                    if(!RemoteInput.this.keys[this.keyEvent.keyCode]) {
                        ++RemoteInput.this.keyCount;
                        RemoteInput.this.keys[this.keyEvent.keyCode] = true;
                    }

                    RemoteInput.this.keyJustPressed = true;
                    RemoteInput.this.justPressedKeys[this.keyEvent.keyCode] = true;
                }

                if(this.keyEvent.type != 1) {
                    return;
                }

                if(!RemoteInput.this.keys[this.keyEvent.keyCode]) {
                    return;
                }

                --RemoteInput.this.keyCount;
                RemoteInput.this.keys[this.keyEvent.keyCode] = false;
            }
        }
    }

    class KeyEvent {
        static final int KEY_DOWN = 0;
        static final int KEY_TYPED = 2;
        static final int KEY_UP = 1;
        long timeStamp;

        KeyEvent(RemoteInput arg1) {
            RemoteInput.this = arg1;
            super();
        }
    }

    public abstract interface RemoteInputListener {
        public abstract void onConnected();

        public abstract void onDisconnected();
    }

    class TouchEvent {
        static final int TOUCH_DOWN = 0;
        static final int TOUCH_DRAGGED = 2;
        static final int TOUCH_UP = 1;
        long timeStamp;

        TouchEvent(RemoteInput arg1) {
            RemoteInput.this = arg1;
            super();
        }
    }

    public static int DEFAULT_PORT;
    private float[] accel;
    private float[] compass;
    private boolean connected;
    public final String[] ips;
    boolean[] isTouched;
    boolean[] justPressedKeys;
    boolean justTouched;
    int keyCount;
    boolean keyJustPressed;
    boolean[] keys;
    private RemoteInputListener listener;
    private boolean multiTouch;
    private final int port;
    InputProcessor processor;
    private float remoteHeight;
    private float remoteWidth;
    private ServerSocket serverSocket;
    int[] touchX;
    int[] touchY;

    static  {
        RemoteInput.DEFAULT_PORT = 8190;
    }

    public RemoteInput() {
        this(RemoteInput.DEFAULT_PORT);
    }

    public RemoteInput(int port) {
        this(port, null);
    }

    public RemoteInput(int port, RemoteInputListener listener) {  // has try-catch handlers
        super();
        this.accel = new float[3];
        this.compass = new float[3];
        this.multiTouch = false;
        this.remoteWidth = 0f;
        this.remoteHeight = 0f;
        this.connected = false;
        this.keyCount = 0;
        this.keys = new boolean[256];
        this.keyJustPressed = false;
        this.justPressedKeys = new boolean[256];
        this.touchX = new int[20];
        this.touchY = new int[20];
        this.isTouched = new boolean[20];
        this.justTouched = false;
        this.processor = null;
        this.listener = listener;
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            Thread v3 = new Thread(((Runnable)this));
            v3.setDaemon(true);
            v3.start();
            InetAddress[] v0 = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
            this.ips = new String[v0.length];
            int v2;
            for(v2 = 0; v2 < v0.length; ++v2) {
                this.ips[v2] = v0[v2].getHostAddress();
            }
        }
        catch(Exception v1) {
            goto label_61;
        }

        return;
    label_61:
        throw new GdxRuntimeException("Couldn\'t open listening socket at port \'" + port + "\'", ((Throwable)v1));
    }

    public RemoteInput(RemoteInputListener listener) {
        this(RemoteInput.DEFAULT_PORT, listener);
    }

    public void cancelVibrate() {
    }

    public float getAccelerometerX() {
        return this.accel[0];
    }

    public float getAccelerometerY() {
        return this.accel[1];
    }

    public float getAccelerometerZ() {
        return this.accel[2];
    }

    public float getAzimuth() {
        return this.compass[0];
    }

    public long getCurrentEventTime() {
        return 0;
    }

    public int getDeltaX() {
        return 0;
    }

    public int getDeltaX(int pointer) {
        return 0;
    }

    public int getDeltaY() {
        return 0;
    }

    public int getDeltaY(int pointer) {
        return 0;
    }

    public String[] getIPs() {
        return this.ips;
    }

    public InputProcessor getInputProcessor() {
        return this.processor;
    }

    public Orientation getNativeOrientation() {
        return Orientation.Landscape;
    }

    public float getPitch() {
        return this.compass[1];
    }

    public float getRoll() {
        return this.compass[2];
    }

    public int getRotation() {
        return 0;
    }

    public void getRotationMatrix(float[] matrix) {
    }

    public void getTextInput(TextInputListener listener, String title, String text, String hint) {
        Gdx.app.getInput().getTextInput(listener, title, text, hint);
    }

    public int getX() {
        return this.touchX[0];
    }

    public int getX(int pointer) {
        return this.touchX[pointer];
    }

    public int getY() {
        return this.touchY[0];
    }

    public int getY(int pointer) {
        return this.touchY[pointer];
    }

    public boolean isButtonPressed(int button) {
        boolean v1 = false;
        if(button == 0) {
            int v0 = 0;
            while(v0 < this.isTouched.length) {
                if(this.isTouched[v0]) {
                    v1 = true;
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }

        return v1;
    }

    public boolean isCatchBackKey() {
        return 0;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isCursorCatched() {
        return 0;
    }

    public boolean isKeyJustPressed(int key) {
        boolean v0;
        if(key == -1) {
            v0 = this.keyJustPressed;
        }
        else {
            if(key >= 0 && key <= 255) {
                v0 = this.justPressedKeys[key];
                goto label_3;
            }

            v0 = false;
        }

    label_3:
        return v0;
    }

    public boolean isKeyPressed(int key) {
        boolean v0 = false;
        if(key == -1) {
            if(this.keyCount > 0) {
                v0 = true;
            }
        }
        else if(key >= 0 && key <= 255) {
            v0 = this.keys[key];
        }

        return v0;
    }

    public boolean isPeripheralAvailable(Peripheral peripheral) {
        boolean v0 = true;
        if(peripheral != Peripheral.Accelerometer && peripheral != Peripheral.Compass) {
            if(peripheral == Peripheral.MultitouchScreen) {
                v0 = this.multiTouch;
            }
            else {
                v0 = false;
            }
        }

        return v0;
    }

    public boolean isTouched() {
        return this.isTouched[0];
    }

    public boolean isTouched(int pointer) {
        return this.isTouched[pointer];
    }

    public boolean justTouched() {
        return this.justTouched;
    }

    public void run() {  // has try-catch handlers
        // Decompilation failed
    }

    public void setCatchBackKey(boolean catchBack) {
    }

    public void setCatchMenuKey(boolean catchMenu) {
    }

    public void setCursorCatched(boolean catched) {
    }

    public void setCursorImage(Pixmap pixmap, int xHotspot, int yHotspot) {
    }

    public void setCursorPosition(int x, int y) {
    }

    public void setInputProcessor(InputProcessor processor) {
        this.processor = processor;
    }

    public void setOnscreenKeyboardVisible(boolean visible) {
    }

    public void vibrate(int milliseconds) {
    }

    public void vibrate(long[] pattern, int repeat) {
    }
}

