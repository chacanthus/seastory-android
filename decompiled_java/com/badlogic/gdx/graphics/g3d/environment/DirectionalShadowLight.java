// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class DirectionalShadowLight extends DirectionalLight implements ShadowMap, Disposable {
    protected Camera cam;
    protected FrameBuffer fbo;
    protected float halfDepth;
    protected float halfHeight;
    protected final TextureDescriptor textureDesc;
    protected final Vector3 tmpV;

    public DirectionalShadowLight(int shadowMapWidth, int shadowMapHeight, float shadowViewportWidth, float shadowViewportHeight, float shadowNear, float shadowFar) {
        super();
        this.tmpV = new Vector3();
        this.fbo = new FrameBuffer(Format.RGBA8888, shadowMapWidth, shadowMapHeight, true);
        this.cam = new OrthographicCamera(shadowViewportWidth, shadowViewportHeight);
        this.cam.near = shadowNear;
        this.cam.far = shadowFar;
        this.halfHeight = shadowViewportHeight * 0.5f;
        this.halfDepth = (shadowFar - shadowNear) * 0.5f + shadowNear;
        this.textureDesc = new TextureDescriptor();
        TextureDescriptor v0 = this.textureDesc;
        TextureDescriptor v1 = this.textureDesc;
        TextureFilter v2 = TextureFilter.Nearest;
        v1.magFilter = v2;
        v0.minFilter = v2;
        v0 = this.textureDesc;
        v1 = this.textureDesc;
        TextureWrap v2_1 = TextureWrap.ClampToEdge;
        v1.vWrap = v2_1;
        v0.uWrap = v2_1;
    }

    public void begin() {
        int v1 = this.fbo.getWidth();
        int v0 = this.fbo.getHeight();
        this.fbo.begin();
        Gdx.gl.glViewport(0, 0, v1, v0);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(16640);
        Gdx.gl.glEnable(3089);
        Gdx.gl.glScissor(1, 1, v1 - 2, v0 - 2);
    }

    public void begin(Camera camera) {
        this.update(camera);
        this.begin();
    }

    public void begin(Vector3 center, Vector3 forward) {
        this.update(center, forward);
        this.begin();
    }

    public void dispose() {
        if(this.fbo != null) {
            this.fbo.dispose();
        }

        this.fbo = null;
    }

    public void end() {
        Gdx.gl.glDisable(3089);
        this.fbo.end();
    }

    public Camera getCamera() {
        return this.cam;
    }

    public TextureDescriptor getDepthMap() {
        this.textureDesc.texture = this.fbo.getColorBufferTexture();
        return this.textureDesc;
    }

    public FrameBuffer getFrameBuffer() {
        return this.fbo;
    }

    public Matrix4 getProjViewTrans() {
        return this.cam.combined;
    }

    public void update(Camera camera) {
        this.update(this.tmpV.set(camera.direction).scl(this.halfHeight), camera.direction);
    }

    public void update(Vector3 center, Vector3 forward) {
        this.cam.position.set(this.direction).scl(-this.halfDepth).add(center);
        this.cam.direction.set(this.direction).nor();
        this.cam.normalizeUp();
        this.cam.update();
    }
}

