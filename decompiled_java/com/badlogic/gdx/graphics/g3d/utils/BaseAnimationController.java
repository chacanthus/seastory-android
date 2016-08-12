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

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool$Poolable;
import java.util.Iterator;

public class BaseAnimationController {
    public final class Transform implements Poolable {
        public final Quaternion rotation;
        public final Vector3 scale;
        public final Vector3 translation;

        public Transform() {
            super();
            this.translation = new Vector3();
            this.rotation = new Quaternion();
            this.scale = new Vector3(1f, 1f, 1f);
        }

        public Transform idt() {
            this.translation.set(0f, 0f, 0f);
            this.rotation.idt();
            this.scale.set(1f, 1f, 1f);
            return this;
        }

        public Transform lerp(Vector3 targetT, Quaternion targetR, Vector3 targetS, float alpha) {
            this.translation.lerp(targetT, alpha);
            this.rotation.slerp(targetR, alpha);
            this.scale.lerp(targetS, alpha);
            return this;
        }

        public Transform lerp(Transform target, float alpha) {
            return this.lerp(target.translation, target.rotation, target.scale, alpha);
        }

        public void reset() {
            this.idt();
        }

        public Transform set(Transform other) {
            return this.set(other.translation, other.rotation, other.scale);
        }

        public Transform set(Vector3 t, Quaternion r, Vector3 s) {
            this.translation.set(t);
            this.rotation.set(r);
            this.scale.set(s);
            return this;
        }

        public Matrix4 toMatrix4(Matrix4 out) {
            return out.set(this.translation, this.rotation, this.scale);
        }

        public String toString() {
            return this.translation.toString() + " - " + this.rotation.toString() + " - " + this.scale.toString();
        }
    }

    private boolean applying;
    public final ModelInstance target;
    private static final Transform tmpT;
    private final Pool transformPool;
    private static final ObjectMap transforms;

    static  {
        BaseAnimationController.transforms = new ObjectMap();
        BaseAnimationController.tmpT = new Transform();
    }

    public BaseAnimationController(ModelInstance target) {
        super();
        this.transformPool = new Pool() {
            protected Transform newObject() {
                return new Transform();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.applying = false;
        this.target = target;
    }

    protected void apply(Animation animation, float time, float weight) {
        if(!this.applying) {
            throw new GdxRuntimeException("You must call begin() before adding an animation");
        }

        BaseAnimationController.applyAnimation(BaseAnimationController.transforms, this.transformPool, weight, animation, time);
    }

    protected static void applyAnimation(ObjectMap arg8, Pool arg9, float alpha, Animation animation, float time) {
        Iterator v1;
        if(arg8 == null) {
            v1 = animation.nodeAnimations.iterator();
            while(v1.hasNext()) {
                BaseAnimationController.applyNodeAnimationDirectly(v1.next(), time);
            }
        }
        else {
            v1 = arg8.keys().iterator();
            while(v1.hasNext()) {
                v1.next().isAnimated = false;
            }

            v1 = animation.nodeAnimations.iterator();
            while(v1.hasNext()) {
                BaseAnimationController.applyNodeAnimationBlending(v1.next(), arg8, arg9, alpha, time);
            }

            v1 = arg8.entries().iterator();
            while(v1.hasNext()) {
                Object v0 = v1.next();
                if(((Entry)v0).key.isAnimated) {
                    continue;
                }

                ((Entry)v0).key.isAnimated = true;
                ((Entry)v0).value.lerp(((Entry)v0).key.translation, ((Entry)v0).key.rotation, ((Entry)v0).key.scale, alpha);
            }
        }
    }

    protected void applyAnimation(Animation animation, float time) {
        ObjectMap v1 = null;
        if(this.applying) {
            throw new GdxRuntimeException("Call end() first");
        }

        BaseAnimationController.applyAnimation(v1, ((Pool)v1), 1f, animation, time);
        this.target.calculateTransforms();
    }

    protected void applyAnimations(Animation anim1, float time1, Animation anim2, float time2, float weight) {
        float v1 = 1f;
        if(anim2 == null || weight == 0f) {
            this.applyAnimation(anim1, time1);
        }
        else {
            if(anim1 != null && weight != v1) {
                if(this.applying) {
                    throw new GdxRuntimeException("Call end() first");
                }
                else {
                    this.begin();
                    this.apply(anim1, time1, v1);
                    this.apply(anim2, time2, weight);
                    this.end();
                    return;
                }
            }

            this.applyAnimation(anim2, time2);
        }
    }

    private static final void applyNodeAnimationBlending(NodeAnimation nodeAnim, ObjectMap arg8, Pool arg9, float alpha, float time) {
        float v4 = 0.999999f;
        Node v0 = nodeAnim.node;
        v0.isAnimated = true;
        Transform v2 = BaseAnimationController.getNodeAnimationTransform(nodeAnim, time);
        Object v1 = arg8.get(v0, null);
        if(v1 != null) {
            if(alpha > v4) {
                ((Transform)v1).set(v2);
            }
            else {
                ((Transform)v1).lerp(v2, alpha);
            }
        }
        else if(alpha > v4) {
            arg8.put(v0, arg9.obtain().set(v2));
        }
        else {
            arg8.put(v0, arg9.obtain().set(v0.translation, v0.rotation, v0.scale).lerp(v2, alpha));
        }
    }

    private static final void applyNodeAnimationDirectly(NodeAnimation nodeAnim, float time) {
        Node v0 = nodeAnim.node;
        v0.isAnimated = true;
        BaseAnimationController.getNodeAnimationTransform(nodeAnim, time).toMatrix4(v0.localTransform);
    }

    protected void begin() {
        if(this.applying) {
            throw new GdxRuntimeException("You must call end() after each call to being()");
        }

        this.applying = true;
    }

    protected void end() {
        if(!this.applying) {
            throw new GdxRuntimeException("You must call begin() first");
        }

        Iterator v1 = BaseAnimationController.transforms.entries().iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            ((Entry)v0).value.toMatrix4(((Entry)v0).key.localTransform);
            this.transformPool.free(((Entry)v0).value);
        }

        BaseAnimationController.transforms.clear();
        this.target.calculateTransforms();
        this.applying = false;
    }

    private static final int getFirstKeyframeIndexAtTime(Array arg3, float time) {
        int v1 = arg3.size - 1;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            if(time >= arg3.get(v0).keytime && time <= arg3.get(v0 + 1).keytime) {
                goto label_11;
            }
        }

        v0 = 0;
    label_11:
        return v0;
    }

    private static final Transform getNodeAnimationTransform(NodeAnimation nodeAnim, float time) {
        Transform v0 = BaseAnimationController.tmpT;
        BaseAnimationController.getTranslationAtTime(nodeAnim, time, v0.translation);
        BaseAnimationController.getRotationAtTime(nodeAnim, time, v0.rotation);
        BaseAnimationController.getScalingAtTime(nodeAnim, time, v0.scale);
        return v0;
    }

    private static final Quaternion getRotationAtTime(NodeAnimation nodeAnim, float time, Quaternion out) {
        if(nodeAnim.rotation == null) {
            out = out.set(nodeAnim.node.rotation);
        }
        else if(nodeAnim.rotation.size == 1) {
            out = out.set(nodeAnim.rotation.get(0).value);
        }
        else {
            int v1 = BaseAnimationController.getFirstKeyframeIndexAtTime(nodeAnim.rotation, time);
            Object v0 = nodeAnim.rotation.get(v1);
            out.set(((NodeKeyframe)v0).value);
            ++v1;
            if(v1 < nodeAnim.rotation.size) {
                Object v2 = nodeAnim.rotation.get(v1);
                out.slerp(((NodeKeyframe)v2).value, (time - ((NodeKeyframe)v0).keytime) / (((NodeKeyframe)v2).keytime - ((NodeKeyframe)v0).keytime));
            }
        }

        return out;
    }

    private static final Vector3 getScalingAtTime(NodeAnimation nodeAnim, float time, Vector3 out) {
        if(nodeAnim.scaling == null) {
            out = out.set(nodeAnim.node.scale);
        }
        else if(nodeAnim.scaling.size == 1) {
            out = out.set(nodeAnim.scaling.get(0).value);
        }
        else {
            int v1 = BaseAnimationController.getFirstKeyframeIndexAtTime(nodeAnim.scaling, time);
            Object v0 = nodeAnim.scaling.get(v1);
            out.set(((NodeKeyframe)v0).value);
            ++v1;
            if(v1 < nodeAnim.scaling.size) {
                Object v2 = nodeAnim.scaling.get(v1);
                out.lerp(((NodeKeyframe)v2).value, (time - ((NodeKeyframe)v0).keytime) / (((NodeKeyframe)v2).keytime - ((NodeKeyframe)v0).keytime));
            }
        }

        return out;
    }

    private static final Vector3 getTranslationAtTime(NodeAnimation nodeAnim, float time, Vector3 out) {
        if(nodeAnim.translation == null) {
            out = out.set(nodeAnim.node.translation);
        }
        else if(nodeAnim.translation.size == 1) {
            out = out.set(nodeAnim.translation.get(0).value);
        }
        else {
            int v1 = BaseAnimationController.getFirstKeyframeIndexAtTime(nodeAnim.translation, time);
            Object v0 = nodeAnim.translation.get(v1);
            out.set(((NodeKeyframe)v0).value);
            ++v1;
            if(v1 < nodeAnim.translation.size) {
                Object v2 = nodeAnim.translation.get(v1);
                out.lerp(((NodeKeyframe)v2).value, (time - ((NodeKeyframe)v0).keytime) / (((NodeKeyframe)v2).keytime - ((NodeKeyframe)v0).keytime));
            }
        }

        return out;
    }

    protected void removeAnimation(Animation animation) {
        Iterator v0 = animation.nodeAnimations.iterator();
        while(v0.hasNext()) {
            v0.next().node.isAnimated = false;
        }
    }
}

