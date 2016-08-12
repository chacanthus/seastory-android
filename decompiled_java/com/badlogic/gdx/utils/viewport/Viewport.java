// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.viewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public abstract class Viewport {
    private Camera camera;
    private int screenHeight;
    private int screenWidth;
    private int screenX;
    private int screenY;
    private final Vector3 tmp;
    private float worldHeight;
    private float worldWidth;

    public Viewport() {
        super();
        this.tmp = new Vector3();
    }

    public void apply() {
        this.apply(false);
    }

    public void apply(boolean centerCamera) {
        float v5 = 2f;
        Gdx.gl.glViewport(this.screenX, this.screenY, this.screenWidth, this.screenHeight);
        this.camera.viewportWidth = this.worldWidth;
        this.camera.viewportHeight = this.worldHeight;
        if(centerCamera) {
            this.camera.position.set(this.worldWidth / v5, this.worldHeight / v5, 0f);
        }

        this.camera.update();
    }

    public void calculateScissors(Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        ScissorStack.calculateScissors(this.camera, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight), batchTransform, area, scissor);
    }

    public int getBottomGutterHeight() {
        return this.screenY;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public int getLeftGutterWidth() {
        return this.screenX;
    }

    public Ray getPickRay(float screenX, float screenY) {
        return this.camera.getPickRay(screenX, screenY, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight));
    }

    public int getRightGutterWidth() {
        return Gdx.graphics.getWidth() - (this.screenX + this.screenWidth);
    }

    public int getRightGutterX() {
        return this.screenX + this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public int getTopGutterHeight() {
        return Gdx.graphics.getHeight() - (this.screenY + this.screenHeight);
    }

    public int getTopGutterY() {
        return this.screenY + this.screenHeight;
    }

    public float getWorldHeight() {
        return this.worldHeight;
    }

    public float getWorldWidth() {
        return this.worldWidth;
    }

    public Vector2 project(Vector2 worldCoords) {
        this.tmp.set(worldCoords.x, worldCoords.y, 1f);
        this.camera.project(this.tmp, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight));
        worldCoords.set(this.tmp.x, this.tmp.y);
        return worldCoords;
    }

    public Vector3 project(Vector3 worldCoords) {
        this.camera.project(worldCoords, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight));
        return worldCoords;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setScreenBounds(int screenX, int screenY, int screenWidth, int screenHeight) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenPosition(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void setScreenSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setWorldHeight(float worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setWorldSize(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void setWorldWidth(float worldWidth) {
        this.worldWidth = worldWidth;
    }

    public Vector2 toScreenCoordinates(Vector2 worldCoords, Matrix4 transformMatrix) {
        this.tmp.set(worldCoords.x, worldCoords.y, 0f);
        this.tmp.mul(transformMatrix);
        this.camera.project(this.tmp);
        this.tmp.y -= ((float)Gdx.graphics.getHeight());
        worldCoords.x = this.tmp.x;
        worldCoords.y = this.tmp.y;
        return worldCoords;
    }

    public Vector2 unproject(Vector2 screenCoords) {
        this.tmp.set(screenCoords.x, screenCoords.y, 1f);
        this.camera.unproject(this.tmp, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight));
        screenCoords.set(this.tmp.x, this.tmp.y);
        return screenCoords;
    }

    public Vector3 unproject(Vector3 screenCoords) {
        this.camera.unproject(screenCoords, ((float)this.screenX), ((float)this.screenY), ((float)this.screenWidth), ((float)this.screenHeight));
        return screenCoords;
    }

    public final void update(int screenWidth, int screenHeight) {
        this.update(screenWidth, screenHeight, false);
    }

    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        this.apply(centerCamera);
    }
}

