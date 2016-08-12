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
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import java.util.Iterator;

public class ParticleController implements Configurable, Serializable {
    protected static final float DEFAULT_TIME_STEP = 0.016667f;
    protected BoundingBox boundingBox;
    public float deltaTime;
    public float deltaTimeSqr;
    public Emitter emitter;
    public Array influencers;
    public String name;
    public ParticleChannels particleChannels;
    public ParticleControllerRenderer renderer;
    public Vector3 scale;
    public Matrix4 transform;

    public ParticleController() {
        super();
        this.transform = new Matrix4();
        this.scale = new Vector3(1f, 1f, 1f);
        this.influencers = new Array(true, 3, Influencer.class);
        this.setTimeStep(0.016667f);
    }

    public ParticleController(String name, Emitter emitter, ParticleControllerRenderer arg4, Influencer[] influencers) {
        this();
        this.name = name;
        this.emitter = emitter;
        this.renderer = arg4;
        this.particleChannels = new ParticleChannels();
        this.influencers = new Array(((Object[])influencers));
    }

    public void activateParticles(int startIndex, int count) {
        this.emitter.activateParticles(startIndex, count);
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().activateParticles(startIndex, count);
        }
    }

    protected void allocateChannels(int maxParticleCount) {
        this.particles = new ParallelArray(maxParticleCount);
        this.emitter.allocateChannels();
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().allocateChannels();
        }

        this.renderer.allocateChannels();
    }

    protected void bind() {
        this.emitter.set(this);
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().set(this);
        }

        this.renderer.set(this);
    }

    protected void calculateBoundingBox() {
        this.boundingBox.clr();
        Channel v2 = this.particles.getChannel(ParticleChannels.Position);
        int v1 = 0;
        int v0 = ((FloatChannel)v2).strideSize * this.particles.size;
        while(v1 < v0) {
            this.boundingBox.ext(((FloatChannel)v2).data[v1], ((FloatChannel)v2).data[v1 + 1], ((FloatChannel)v2).data[v1 + 2]);
            v1 += ((FloatChannel)v2).strideSize;
        }
    }

    public ParticleController copy() {
        ParticleControllerComponent v0 = this.emitter.copy();
        Influencer[] v5 = new Influencer[this.influencers.size];
        int v1 = 0;
        Iterator v3 = this.influencers.iterator();
        while(v3.hasNext()) {
            v5[v1] = v3.next().copy();
            ++v1;
        }

        return new ParticleController(new String(this.name), ((Emitter)v0), this.renderer.copy(), v5);
    }

    public void dispose() {
        this.emitter.dispose();
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().dispose();
        }
    }

    public void draw() {
        if(this.particles.size > 0) {
            this.renderer.update();
        }
    }

    public void end() {
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().end();
        }

        this.emitter.end();
    }

    private int findIndex(Class arg4) {
        int v0 = 0;
        while(true) {
            if(v0 >= this.influencers.size) {
                break;
            }
            else if(!ClassReflection.isAssignableFrom(arg4, this.influencers.get(v0).getClass())) {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v0 = -1;
    label_9:
        return v0;
    }

    public Influencer findInfluencer(Class arg3) {
        Object v1;
        int v0 = this.findIndex(arg3);
        if(v0 > -1) {
            v1 = this.influencers.get(v0);
        }
        else {
            Influencer v1_1 = null;
        }

        return ((Influencer)v1);
    }

    public BoundingBox getBoundingBox() {
        if(this.boundingBox == null) {
            this.boundingBox = new BoundingBox();
        }

        this.calculateBoundingBox();
        return this.boundingBox;
    }

    public void getTransform(Matrix4 transform) {
        transform.set(this.transform);
    }

    public void init() {
        this.bind();
        if(this.particles != null) {
            this.end();
            this.particleChannels.resetIds();
        }

        this.allocateChannels(this.emitter.maxParticleCount);
        this.emitter.init();
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().init();
        }

        this.renderer.init();
    }

    public void killParticles(int startIndex, int count) {
        this.emitter.killParticles(startIndex, count);
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().killParticles(startIndex, count);
        }
    }

    public void load(AssetManager manager, ResourceData data) {
        this.emitter.load(manager, data);
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().load(manager, data);
        }

        this.renderer.load(manager, data);
    }

    public void mul(Matrix4 transform) {
        this.transform.mul(transform);
        this.transform.getScale(this.scale);
    }

    public void read(Json json, JsonValue jsonMap) {
        this.name = json.readValue("name", String.class, jsonMap);
        this.emitter = json.readValue("emitter", Emitter.class, jsonMap);
        this.influencers.addAll(json.readValue("influencers", Array.class, Influencer.class, jsonMap));
        this.renderer = json.readValue("renderer", ParticleControllerRenderer.class, jsonMap);
    }

    public void removeInfluencer(Class arg3) {
        int v0 = this.findIndex(arg3);
        if(v0 > -1) {
            this.influencers.removeIndex(v0);
        }
    }

    public boolean replaceInfluencer(Class arg4, Influencer arg5) {
        boolean v1;
        int v0 = this.findIndex(arg4);
        if(v0 > -1) {
            this.influencers.insert(v0, arg5);
            this.influencers.removeIndex(v0 + 1);
            v1 = true;
        }
        else {
            v1 = false;
        }

        return v1;
    }

    public void reset() {
        this.end();
        this.start();
    }

    public void rotate(Quaternion rotation) {
        this.transform.rotate(rotation);
    }

    public void rotate(Vector3 axis, float angle) {
        this.transform.rotate(axis, angle);
    }

    public void save(AssetManager manager, ResourceData data) {
        this.emitter.save(manager, data);
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().save(manager, data);
        }

        this.renderer.save(manager, data);
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        this.transform.scale(scaleX, scaleY, scaleZ);
        this.transform.getScale(this.scale);
    }

    public void scale(Vector3 scale) {
        this.scale(scale.x, scale.y, scale.z);
    }

    private void setTimeStep(float timeStep) {
        this.deltaTime = timeStep;
        this.deltaTimeSqr = this.deltaTime * this.deltaTime;
    }

    public void setTransform(float x, float y, float z, float qx, float qy, float qz, float qw, float scale) {
        this.transform.set(x, y, z, qx, qy, qz, qw, scale, scale, scale);
        this.scale.set(scale, scale, scale);
    }

    public void setTransform(Matrix4 transform) {
        this.transform.set(transform);
        transform.getScale(this.scale);
    }

    public void setTranslation(Vector3 translation) {
        this.transform.setTranslation(translation);
    }

    public void start() {
        this.emitter.start();
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().start();
        }
    }

    public void translate(Vector3 translation) {
        this.transform.translate(translation);
    }

    public void update() {
        this.emitter.update();
        Iterator v0 = this.influencers.iterator();
        while(v0.hasNext()) {
            v0.next().update();
        }
    }

    public void write(Json json) {
        json.writeValue("name", this.name);
        json.writeValue("emitter", this.emitter, Emitter.class);
        json.writeValue("influencers", this.influencers, Array.class, Influencer.class);
        json.writeValue("renderer", this.renderer, ParticleControllerRenderer.class);
    }
}

