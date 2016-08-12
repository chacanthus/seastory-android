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
import com.badlogic.gdx.Input$Peripheral;
import com.badlogic.gdx.InputProcessor;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteSender implements InputProcessor {
    public static final int ACCEL = 6;
    public static final int COMPASS = 7;
    public static final int KEY_DOWN = 0;
    public static final int KEY_TYPED = 2;
    public static final int KEY_UP = 1;
    public static final int SIZE = 8;
    public static final int TOUCH_DOWN = 3;
    public static final int TOUCH_DRAGGED = 5;
    public static final int TOUCH_UP = 4;
    private boolean connected;
    private DataOutputStream out;

    public RemoteSender(String ip, int port) {  // has try-catch handlers
        super();
        this.connected = false;
        try {
            Socket v1 = new Socket(ip, port);
            v1.setTcpNoDelay(true);
            v1.setSoTimeout(3000);
            this.out = new DataOutputStream(v1.getOutputStream());
            this.out.writeBoolean(Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen));
            this.connected = true;
            Gdx.input.setInputProcessor(((InputProcessor)this));
        }
        catch(Exception v0) {
            Gdx.app.log("RemoteSender", "couldn\'t connect to " + ip + ":" + port);
        }
    }

    public boolean isConnected() {  // has try-catch handlers
        try {
            return this.connected;
        label_3:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_3;
        }
    }

    public boolean keyDown(int keycode) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_17;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_17;
        }
        catch(Throwable v0) {
            goto label_12;
        }

        try {
        label_5:
            v1_1.writeInt(0);
            this.out.writeInt(keycode);
        }
        catch(Throwable v0) {
            try {
            label_12:
                this.connected = false;
                return 0;
            label_15:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_15;
            }
        }

        return 0;
        try {
        label_17:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_17;
        }
    }

    public boolean keyTyped(char character) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_17;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_17;
        }
        catch(Throwable v0) {
            goto label_12;
        }

        try {
        label_5:
            v1_1.writeInt(2);
            this.out.writeChar(character);
        }
        catch(Throwable v0) {
            try {
            label_12:
                this.connected = false;
                return 0;
            label_15:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_15;
            }
        }

        return 0;
        try {
        label_17:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_17;
        }
    }

    public boolean keyUp(int keycode) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_17;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_17;
        }
        catch(Throwable v0) {
            goto label_12;
        }

        try {
        label_5:
            v1_1.writeInt(1);
            this.out.writeInt(keycode);
        }
        catch(Throwable v0) {
            try {
            label_12:
                this.connected = false;
                return 0;
            label_15:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_15;
            }
        }

        return 0;
        try {
        label_17:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_17;
        }
    }

    public boolean mouseMoved(int x, int y) {
        return 0;
    }

    public boolean scrolled(int amount) {
        return 0;
    }

    public void sendUpdate() {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_3;
            }

            return;
        }
        catch(Throwable v1) {
            goto label_54;
        }

        try {
        label_3:
            v1_1 = this.out;
            goto label_4;
        }
        catch(Throwable v1) {
            goto label_54;
        }
        catch(Throwable v0) {
            goto label_49;
        }

        try {
        label_4:
            v1_1.writeInt(6);
            this.out.writeFloat(Gdx.input.getAccelerometerX());
            this.out.writeFloat(Gdx.input.getAccelerometerY());
            this.out.writeFloat(Gdx.input.getAccelerometerZ());
            this.out.writeInt(7);
            this.out.writeFloat(Gdx.input.getAzimuth());
            this.out.writeFloat(Gdx.input.getPitch());
            this.out.writeFloat(Gdx.input.getRoll());
            this.out.writeInt(8);
            this.out.writeFloat(((float)Gdx.graphics.getWidth()));
            this.out.writeFloat(((float)Gdx.graphics.getHeight()));
        }
        catch(Throwable v0) {
        label_49:
            this.out = null;
            this.connected = false;
        }

        return;
        try {
        label_54:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_54;
        }
    }

    public boolean touchDown(int x, int y, int pointer, int button) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_21;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_21;
        }
        catch(Throwable v0) {
            goto label_16;
        }

        try {
        label_5:
            v1_1.writeInt(3);
            this.out.writeInt(x);
            this.out.writeInt(y);
            this.out.writeInt(pointer);
        }
        catch(Throwable v0) {
            try {
            label_16:
                this.connected = false;
                return 0;
            label_19:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_19;
            }
        }

        return 0;
        try {
        label_21:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_21;
        }
    }

    public boolean touchDragged(int x, int y, int pointer) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_21;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_21;
        }
        catch(Throwable v0) {
            goto label_16;
        }

        try {
        label_5:
            v1_1.writeInt(5);
            this.out.writeInt(x);
            this.out.writeInt(y);
            this.out.writeInt(pointer);
        }
        catch(Throwable v0) {
            try {
            label_16:
                this.connected = false;
                return 0;
            label_19:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_19;
            }
        }

        return 0;
        try {
        label_21:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_21;
        }
    }

    public boolean touchUp(int x, int y, int pointer, int button) {  // has try-catch handlers
        DataOutputStream v1_1;
        try {
            if(this.connected) {
                goto label_4;
            }

            return 0;
        }
        catch(Throwable v1) {
            goto label_21;
        }

        try {
        label_4:
            v1_1 = this.out;
            goto label_5;
        }
        catch(Throwable v1) {
            goto label_21;
        }
        catch(Throwable v0) {
            goto label_16;
        }

        try {
        label_5:
            v1_1.writeInt(4);
            this.out.writeInt(x);
            this.out.writeInt(y);
            this.out.writeInt(pointer);
        }
        catch(Throwable v0) {
            try {
            label_16:
                this.connected = false;
                return 0;
            label_19:
                throw v1;
            }
            catch(Throwable v1) {
                goto label_19;
            }
        }

        return 0;
        try {
        label_21:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_21;
        }
    }
}

