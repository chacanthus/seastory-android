// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;

public abstract class ParticleControllerComponent implements Configurable, Disposable, Serializable {
    protected static final Matrix3 TMP_M3;
    protected static final Matrix4 TMP_M4;
    protected static final Quaternion TMP_Q;
    protected static final Quaternion TMP_Q2;
    protected static final Vector3 TMP_V1;
    protected static final Vector3 TMP_V2;
    protected static final Vector3 TMP_V3;
    protected static final Vector3 TMP_V4;
    protected static final Vector3 TMP_V5;
    protected static final Vector3 TMP_V6;
    protected ParticleController controller;

    static  {
        ParticleControllerComponent.TMP_V1 = new Vector3();
        ParticleControllerComponent.TMP_V2 = new Vector3();
        ParticleControllerComponent.TMP_V3 = new Vector3();
        ParticleControllerComponent.TMP_V4 = new Vector3();
        ParticleControllerComponent.TMP_V5 = new Vector3();
        ParticleControllerComponent.TMP_V6 = new Vector3();
        ParticleControllerComponent.TMP_Q = new Quaternion();
        ParticleControllerComponent.TMP_Q2 = new Quaternion();
        ParticleControllerComponent.TMP_M3 = new Matrix3();
        ParticleControllerComponent.TMP_M4 = new Matrix4();
    }

    public ParticleControllerComponent() {
        super();
    }

    public void activateParticles(int startIndex, int count) {
    }

    public void allocateChannels() {
    }

    public abstract ParticleControllerComponent copy();

    public void dispose() {
    }

    public void end() {
    }

    public void init() {
    }

    public void killParticles(int startIndex, int count) {
    }

    public void load(AssetManager manager, ResourceData data) {
    }

    public void read(Json json, JsonValue jsonData) {
    }

    public void save(AssetManager manager, ResourceData data) {
    }

    public void set(ParticleController particleController) {
        this.controller = particleController;
    }

    public void start() {
    }

    public void update() {
    }

    public void write(Json json) {
    }
}

