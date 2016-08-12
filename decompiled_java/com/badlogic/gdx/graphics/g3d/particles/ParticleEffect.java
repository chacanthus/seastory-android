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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public class ParticleEffect implements Configurable, Disposable {
    private BoundingBox bounds;
    private Array controllers;

    public ParticleEffect() {
        super();
        this.controllers = new Array(true, 3, ParticleController.class);
    }

    public ParticleEffect(ParticleEffect effect) {
        super();
        this.controllers = new Array(true, effect.controllers.size);
        int v0 = 0;
        int v1 = effect.controllers.size;
        while(v0 < v1) {
            this.controllers.add(effect.controllers.get(v0).copy());
            ++v0;
        }
    }

    public ParticleEffect(ParticleController[] emitters) {
        super();
        this.controllers = new Array(((Object[])emitters));
    }

    public ParticleEffect copy() {
        return new ParticleEffect(this);
    }

    public void dispose() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).dispose();
            ++v0;
        }
    }

    public void draw() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).draw();
            ++v0;
        }
    }

    public void end() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).end();
            ++v0;
        }
    }

    public ParticleController findController(String name) {
        Object v0;
        int v1 = 0;
        int v2 = this.controllers.size;
        while(true) {
            if(v1 < v2) {
                v0 = this.controllers.get(v1);
                if(!((ParticleController)v0).name.equals(name)) {
                    ++v1;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_9;
        }

        ParticleController v0_1 = null;
    label_9:
        return ((ParticleController)v0);
    }

    public BoundingBox getBoundingBox() {
        if(this.bounds == null) {
            this.bounds = new BoundingBox();
        }

        BoundingBox v0 = this.bounds;
        v0.inf();
        Iterator v2 = this.controllers.iterator();
        while(v2.hasNext()) {
            v0.ext(v2.next().getBoundingBox());
        }

        return v0;
    }

    public Array getControllers() {
        return this.controllers;
    }

    public void init() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).init();
            ++v0;
        }
    }

    public void load(AssetManager assetManager, ResourceData data) {
        Iterator v2 = this.controllers.iterator();
        while(v2.hasNext()) {
            v2.next().load(assetManager, data);
        }
    }

    public void reset() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).reset();
            ++v0;
        }
    }

    public void rotate(Quaternion rotation) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).rotate(rotation);
            ++v0;
        }
    }

    public void rotate(Vector3 axis, float angle) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).rotate(axis, angle);
            ++v0;
        }
    }

    public void save(AssetManager assetManager, ResourceData data) {
        Iterator v1 = this.controllers.iterator();
        while(v1.hasNext()) {
            v1.next().save(assetManager, data);
        }
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).scale(scaleX, scaleY, scaleZ);
            ++v0;
        }
    }

    public void scale(Vector3 scale) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).scale(scale.x, scale.y, scale.z);
            ++v0;
        }
    }

    public void setBatch(Array arg6) {
        Iterator v2 = this.controllers.iterator();
        while(true) {
        label_2:
            if(!v2.hasNext()) {
                return;
            }

            Object v1 = v2.next();
            Iterator v3 = arg6.iterator();
            do {
                if(!v3.hasNext()) {
                    goto label_2;
                }
            }
            while(!((ParticleController)v1).renderer.setBatch(v3.next()));
        }
    }

    public void setTransform(Matrix4 transform) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).setTransform(transform);
            ++v0;
        }
    }

    public void start() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).start();
            ++v0;
        }
    }

    public void translate(Vector3 translation) {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).translate(translation);
            ++v0;
        }
    }

    public void update() {
        int v0 = 0;
        int v1 = this.controllers.size;
        while(v0 < v1) {
            this.controllers.get(v0).update();
            ++v0;
        }
    }
}

