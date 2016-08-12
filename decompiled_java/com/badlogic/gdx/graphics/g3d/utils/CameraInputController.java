// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector$GestureAdapter;
import com.badlogic.gdx.input.GestureDetector$GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraInputController extends GestureDetector {
    public class CameraGestureListener extends GestureAdapter {
        public CameraInputController controller;
        private float previousZoom;

        protected CameraGestureListener() {
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

        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return 0;
        }

        public boolean tap(float x, float y, int count, int button) {
            return 0;
        }

        public boolean touchDown(float x, float y, int pointer, int button) {
            this.previousZoom = 0f;
            return 0;
        }

        public boolean zoom(float initialDistance, float distance) {
            float v2 = distance - initialDistance;
            float v0 = v2 - this.previousZoom;
            this.previousZoom = v2;
            float v3 = ((float)Gdx.graphics.getWidth());
            float v1 = ((float)Gdx.graphics.getHeight());
            CameraInputController v4 = this.controller;
            if(v3 <= v1) {
                v1 = v3;
            }

            return v4.pinchZoom(v0 / v1);
        }
    }

    public int activateKey;
    protected boolean activatePressed;
    public boolean alwaysScroll;
    public boolean autoUpdate;
    public int backwardKey;
    protected boolean backwardPressed;
    protected int button;
    public Camera camera;
    public int forwardButton;
    public int forwardKey;
    protected boolean forwardPressed;
    public boolean forwardTarget;
    protected final CameraGestureListener gestureListener;
    private boolean multiTouch;
    public float pinchZoomFactor;
    public float rotateAngle;
    public int rotateButton;
    public int rotateLeftKey;
    protected boolean rotateLeftPressed;
    public int rotateRightKey;
    protected boolean rotateRightPressed;
    public float scrollFactor;
    public boolean scrollTarget;
    private float startX;
    private float startY;
    public Vector3 target;
    private final Vector3 tmpV1;
    private final Vector3 tmpV2;
    private int touched;
    public int translateButton;
    public boolean translateTarget;
    public float translateUnits;

    public CameraInputController(Camera camera) {
        this(new CameraGestureListener(), camera);
    }

    protected CameraInputController(CameraGestureListener gestureListener, Camera camera) {
        super(((GestureListener)gestureListener));
        this.rotateButton = 0;
        this.rotateAngle = 360f;
        this.translateButton = 1;
        this.translateUnits = 10f;
        this.forwardButton = 2;
        this.activateKey = 0;
        this.alwaysScroll = true;
        this.scrollFactor = -0.1f;
        this.pinchZoomFactor = 10f;
        this.autoUpdate = true;
        this.target = new Vector3();
        this.translateTarget = true;
        this.forwardTarget = true;
        this.scrollTarget = false;
        this.forwardKey = 51;
        this.backwardKey = 47;
        this.rotateRightKey = 29;
        this.rotateLeftKey = 32;
        this.button = -1;
        this.tmpV1 = new Vector3();
        this.tmpV2 = new Vector3();
        this.gestureListener = gestureListener;
        this.gestureListener.controller = this;
        this.camera = camera;
    }

    public boolean keyDown(int keycode) {
        if(keycode == this.activateKey) {
            this.activatePressed = true;
        }

        if(keycode == this.forwardKey) {
            this.forwardPressed = true;
        }
        else if(keycode == this.backwardKey) {
            this.backwardPressed = true;
        }
        else if(keycode == this.rotateRightKey) {
            this.rotateRightPressed = true;
        }
        else if(keycode == this.rotateLeftKey) {
            this.rotateLeftPressed = true;
        }

        return 0;
    }

    public boolean keyUp(int keycode) {
        if(keycode == this.activateKey) {
            this.activatePressed = false;
            this.button = -1;
        }

        if(keycode == this.forwardKey) {
            this.forwardPressed = false;
        }
        else if(keycode == this.backwardKey) {
            this.backwardPressed = false;
        }
        else if(keycode == this.rotateRightKey) {
            this.rotateRightPressed = false;
        }
        else if(keycode == this.rotateLeftKey) {
            this.rotateLeftPressed = false;
        }

        return 0;
    }

    protected boolean pinchZoom(float amount) {
        return this.zoom(this.pinchZoomFactor * amount);
    }

    protected boolean process(float deltaX, float deltaY, int button) {
        if(button == this.rotateButton) {
            this.tmpV1.set(this.camera.direction).crs(this.camera.up).y = 0f;
            this.camera.rotateAround(this.target, this.tmpV1.nor(), this.rotateAngle * deltaY);
            this.camera.rotateAround(this.target, Vector3.Y, -this.rotateAngle * deltaX);
        }
        else if(button == this.translateButton) {
            this.camera.translate(this.tmpV1.set(this.camera.direction).crs(this.camera.up).nor().scl(-deltaX * this.translateUnits));
            this.camera.translate(this.tmpV2.set(this.camera.up).scl(-deltaY * this.translateUnits));
            if(this.translateTarget) {
                this.target.add(this.tmpV1).add(this.tmpV2);
            }
        }
        else if(button == this.forwardButton) {
            this.camera.translate(this.tmpV1.set(this.camera.direction).scl(this.translateUnits * deltaY));
            if(this.forwardTarget) {
                this.target.add(this.tmpV1);
            }
        }

        if(this.autoUpdate) {
            this.camera.update();
        }

        return 1;
    }

    public boolean scrolled(int amount) {
        return this.zoom((((float)amount)) * this.scrollFactor * this.translateUnits);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean v0;
        boolean v2 = false;
        this.touched |= 1 << pointer;
        if(!MathUtils.isPowerOfTwo(this.touched)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.multiTouch = v0;
        if(this.multiTouch) {
            this.button = -1;
        }
        else if(this.button < 0) {
            if(this.activateKey != 0 && !this.activatePressed) {
                goto label_15;
            }

            this.startX = ((float)screenX);
            this.startY = ((float)screenY);
            this.button = button;
        }

    label_15:
        if((super.touchDown(screenX, screenY, pointer, button)) || this.activateKey == 0 || (this.activatePressed)) {
            v2 = true;
        }

        return v2;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean v2 = super.touchDragged(screenX, screenY, pointer);
        if(!v2 && this.button >= 0) {
            float v0 = ((((float)screenX)) - this.startX) / (((float)Gdx.graphics.getWidth()));
            float v1 = (this.startY - (((float)screenY))) / (((float)Gdx.graphics.getHeight()));
            this.startX = ((float)screenX);
            this.startY = ((float)screenY);
            v2 = this.process(v0, v1, this.button);
        }

        return v2;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean v0;
        boolean v2 = false;
        this.touched &= 1 << pointer ^ -1;
        if(!MathUtils.isPowerOfTwo(this.touched)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.multiTouch = v0;
        if(button == this.button) {
            this.button = -1;
        }

        if((super.touchUp(screenX, screenY, pointer, button)) || (this.activatePressed)) {
            v2 = true;
        }

        return v2;
    }

    public void update() {
        if((this.rotateRightPressed) || (this.rotateLeftPressed) || (this.forwardPressed) || (this.backwardPressed)) {
            float v0 = Gdx.graphics.getDeltaTime();
            if(this.rotateRightPressed) {
                this.camera.rotate(this.camera.up, -v0 * this.rotateAngle);
            }

            if(this.rotateLeftPressed) {
                this.camera.rotate(this.camera.up, this.rotateAngle * v0);
            }

            if(this.forwardPressed) {
                this.camera.translate(this.tmpV1.set(this.camera.direction).scl(this.translateUnits * v0));
                if(this.forwardTarget) {
                    this.target.add(this.tmpV1);
                }
            }

            if(this.backwardPressed) {
                this.camera.translate(this.tmpV1.set(this.camera.direction).scl(-v0 * this.translateUnits));
                if(this.forwardTarget) {
                    this.target.add(this.tmpV1);
                }
            }

            if(!this.autoUpdate) {
                return;
            }

            this.camera.update();
        }
    }

    public boolean zoom(float amount) {
        boolean v0;
        if((this.alwaysScroll) || this.activateKey == 0 || (this.activatePressed)) {
            this.camera.translate(this.tmpV1.set(this.camera.direction).scl(amount));
            if(this.scrollTarget) {
                this.target.add(this.tmpV1);
            }

            if(this.autoUpdate) {
                this.camera.update();
            }

            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

