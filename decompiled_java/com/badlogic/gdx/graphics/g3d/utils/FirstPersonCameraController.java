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
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

public class FirstPersonCameraController extends InputAdapter {
    private int BACKWARD;
    private int DOWN;
    private int FORWARD;
    private int STRAFE_LEFT;
    private int STRAFE_RIGHT;
    private int UP;
    private final Camera camera;
    private float degreesPerPixel;
    private final IntIntMap keys;
    private final Vector3 tmp;
    private float velocity;

    public FirstPersonCameraController(Camera camera) {
        super();
        this.keys = new IntIntMap();
        this.STRAFE_LEFT = 29;
        this.STRAFE_RIGHT = 32;
        this.FORWARD = 51;
        this.BACKWARD = 47;
        this.UP = 45;
        this.DOWN = 33;
        this.velocity = 5f;
        this.degreesPerPixel = 0.5f;
        this.tmp = new Vector3();
        this.camera = camera;
    }

    public boolean keyDown(int keycode) {
        this.keys.put(keycode, keycode);
        return 1;
    }

    public boolean keyUp(int keycode) {
        this.keys.remove(keycode, 0);
        return 1;
    }

    public void setDegreesPerPixel(float degreesPerPixel) {
        this.degreesPerPixel = degreesPerPixel;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float v0 = (((float)(-Gdx.input.getDeltaX()))) * this.degreesPerPixel;
        float v1 = (((float)(-Gdx.input.getDeltaY()))) * this.degreesPerPixel;
        this.camera.direction.rotate(this.camera.up, v0);
        this.tmp.set(this.camera.direction).crs(this.camera.up).nor();
        this.camera.direction.rotate(this.tmp, v1);
        return 1;
    }

    public void update() {
        this.update(Gdx.graphics.getDeltaTime());
    }

    public void update(float deltaTime) {
        if(this.keys.containsKey(this.FORWARD)) {
            this.tmp.set(this.camera.direction).nor().scl(this.velocity * deltaTime);
            this.camera.position.add(this.tmp);
        }

        if(this.keys.containsKey(this.BACKWARD)) {
            this.tmp.set(this.camera.direction).nor().scl(-deltaTime * this.velocity);
            this.camera.position.add(this.tmp);
        }

        if(this.keys.containsKey(this.STRAFE_LEFT)) {
            this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(-deltaTime * this.velocity);
            this.camera.position.add(this.tmp);
        }

        if(this.keys.containsKey(this.STRAFE_RIGHT)) {
            this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(this.velocity * deltaTime);
            this.camera.position.add(this.tmp);
        }

        if(this.keys.containsKey(this.UP)) {
            this.tmp.set(this.camera.up).nor().scl(this.velocity * deltaTime);
            this.camera.position.add(this.tmp);
        }

        if(this.keys.containsKey(this.DOWN)) {
            this.tmp.set(this.camera.up).nor().scl(-deltaTime * this.velocity);
            this.camera.position.add(this.tmp);
        }

        this.camera.update(true);
    }
}

