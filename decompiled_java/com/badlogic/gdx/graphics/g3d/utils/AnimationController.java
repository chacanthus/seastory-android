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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

public class AnimationController extends BaseAnimationController {
    public class AnimationDesc {
        protected AnimationDesc() {
            super();
        }

        protected float update(float delta) {
            float v3;
            float v5;
            int v2;
            float v4 = 0f;
            if(this.loopCount == 0 || this.animation == null) {
                v3 = delta;
            }
            else {
                float v0 = this.speed * delta;
                if(!MathUtils.isZero(this.duration)) {
                    this.time += v0;
                    v2 = ((int)Math.abs(this.time / this.duration));
                    if(this.time < 0f) {
                        ++v2;
                        while(this.time < 0f) {
                            this.time += this.duration;
                        }
                    }

                    this.time = Math.abs(this.time % this.duration);
                }
                else {
                    v2 = 1;
                }

                int v1;
                for(v1 = 0; v1 < v2; ++v1) {
                    if(this.loopCount > 0) {
                        --this.loopCount;
                    }

                    if(this.loopCount != 0 && this.listener != null) {
                        this.listener.onLoop(this);
                    }

                    if(this.loopCount != 0) {
                        goto label_71;
                    }

                    float v6 = this.duration * (((float)(v2 - 1 - v1)));
                    if(v0 < 0f) {
                        v5 = this.duration - this.time;
                    }
                    else {
                        v5 = this.time;
                    }

                    v3 = v6 + v5;
                    if(v0 >= 0f) {
                        v4 = this.duration;
                    }

                    this.time = v4;
                    if(this.listener == null) {
                        goto label_64;
                    }

                    this.listener.onEnd(this);
                    goto label_64;
                label_71:
                }

                v3 = 0f;
            }

        label_64:
            return v3;
        }
    }

    public abstract interface AnimationListener {
        public abstract void onEnd(AnimationDesc arg0);

        public abstract void onLoop(AnimationDesc arg0);
    }

    public boolean allowSameAnimation;
    protected final Pool animationPool;
    public AnimationDesc current;
    public boolean inAction;
    private boolean justChangedAnimation;
    public boolean paused;
    public AnimationDesc previous;
    public AnimationDesc queued;
    public float queuedTransitionTime;
    public float transitionCurrentTime;
    public float transitionTargetTime;
    private boolean updating;

    public AnimationController(ModelInstance target) {
        super(target);
        this.animationPool = new Pool() {
            protected AnimationDesc newObject() {
                return new AnimationDesc();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.justChangedAnimation = false;
    }

    protected AnimationDesc action(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.action(this.obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc action(AnimationDesc anim, float transitionTime) {
        AnimationDesc v0;
        if(anim.loopCount < 0) {
            throw new GdxRuntimeException("An action cannot be continuous");
        }

        if(this.current == null || this.current.loopCount == 0) {
            this.animate(anim, transitionTime);
        }
        else {
            if(this.inAction) {
                v0 = null;
            }
            else {
                v0 = this.obtain(this.current);
            }

            this.inAction = false;
            this.animate(anim, transitionTime);
            this.inAction = true;
            if(v0 == null) {
                goto label_11;
            }

            this.queue(v0, transitionTime);
        }

    label_11:
        return anim;
    }

    public AnimationDesc action(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.action(this.obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    public AnimationDesc action(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.action(id, 0f, -1f, loopCount, speed, listener, transitionTime);
    }

    protected AnimationDesc animate(AnimationDesc anim, float transitionTime) {
        if(this.current == null) {
            this.current = anim;
        }
        else if(this.inAction) {
            this.queue(anim, transitionTime);
        }
        else {
            if(!this.allowSameAnimation && anim != null && this.current.animation == anim.animation) {
                anim.time = this.current.time;
                this.animationPool.free(this.current);
                this.current = anim;
                goto label_3;
            }

            if(this.previous != null) {
                this.removeAnimation(this.previous.animation);
                this.animationPool.free(this.previous);
            }

            this.previous = this.current;
            this.current = anim;
            this.transitionCurrentTime = 0f;
            this.transitionTargetTime = transitionTime;
        }

    label_3:
        return anim;
    }

    protected AnimationDesc animate(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.animate(this.obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    public AnimationDesc animate(String id, float transitionTime) {
        return this.animate(id, 1, 1f, null, transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.animate(id, 0f, -1f, loopCount, speed, listener, transitionTime);
    }

    public AnimationDesc animate(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.animate(this.obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, AnimationListener listener, float transitionTime) {
        return this.animate(id, loopCount, 1f, listener, transitionTime);
    }

    public AnimationDesc animate(String id, AnimationListener listener, float transitionTime) {
        return this.animate(id, 1, 1f, listener, transitionTime);
    }

    private AnimationDesc obtain(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        AnimationDesc v0;
        float v1 = 0f;
        if(anim == null) {
            v0 = null;
        }
        else {
            Object v0_1 = this.animationPool.obtain();
            ((AnimationDesc)v0_1).animation = anim;
            ((AnimationDesc)v0_1).listener = listener;
            ((AnimationDesc)v0_1).loopCount = loopCount;
            ((AnimationDesc)v0_1).speed = speed;
            ((AnimationDesc)v0_1).offset = offset;
            if(duration < 0f) {
                duration = anim.duration - offset;
            }

            ((AnimationDesc)v0_1).duration = duration;
            if(speed < 0f) {
                v1 = ((AnimationDesc)v0_1).duration;
            }

            ((AnimationDesc)v0_1).time = v1;
        }

        return v0;
    }

    private AnimationDesc obtain(AnimationDesc anim) {
        return this.obtain(anim.animation, anim.offset, anim.duration, anim.loopCount, anim.speed, anim.listener);
    }

    private AnimationDesc obtain(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        AnimationDesc v0;
        if(id == null) {
            v0 = null;
        }
        else {
            Animation v1 = this.target.getAnimation(id);
            if(v1 == null) {
                throw new GdxRuntimeException("Unknown animation: " + id);
            }
            else {
                v0 = this.obtain(v1, offset, duration, loopCount, speed, listener);
            }
        }

        return v0;
    }

    protected AnimationDesc queue(AnimationDesc anim, float transitionTime) {
        if(this.current == null || this.current.loopCount == 0) {
            this.animate(anim, transitionTime);
        }
        else {
            if(this.queued != null) {
                this.animationPool.free(this.queued);
            }

            this.queued = anim;
            this.queuedTransitionTime = transitionTime;
            if(this.current.loopCount >= 0) {
                goto label_6;
            }

            this.current.loopCount = 1;
        }

    label_6:
        return anim;
    }

    protected AnimationDesc queue(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.queue(this.obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    public AnimationDesc queue(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.queue(this.obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    public AnimationDesc queue(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return this.queue(id, 0f, -1f, loopCount, speed, listener, transitionTime);
    }

    protected AnimationDesc setAnimation(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        return this.setAnimation(this.obtain(anim, offset, duration, loopCount, speed, listener));
    }

    protected AnimationDesc setAnimation(AnimationDesc anim) {
        if(this.updating) {
            throw new GdxRuntimeException("Cannot change animation during update");
        }

        if(this.current == null) {
            this.current = anim;
        }
        else {
            if((this.allowSameAnimation) || anim == null || this.current.animation != anim.animation) {
                this.removeAnimation(this.current.animation);
            }
            else {
                anim.time = this.current.time;
            }

            this.animationPool.free(this.current);
            this.current = anim;
        }

        this.justChangedAnimation = true;
        return anim;
    }

    public AnimationDesc setAnimation(String id) {
        return this.setAnimation(id, 1, 1f, null);
    }

    public AnimationDesc setAnimation(String id, int loopCount, float speed, AnimationListener listener) {
        return this.setAnimation(id, 0f, -1f, loopCount, speed, listener);
    }

    public AnimationDesc setAnimation(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        return this.setAnimation(this.obtain(id, offset, duration, loopCount, speed, listener));
    }

    public AnimationDesc setAnimation(String id, int loopCount) {
        return this.setAnimation(id, loopCount, 1f, null);
    }

    public AnimationDesc setAnimation(String id, int loopCount, AnimationListener listener) {
        return this.setAnimation(id, loopCount, 1f, listener);
    }

    public AnimationDesc setAnimation(String id, AnimationListener listener) {
        return this.setAnimation(id, 1, 1f, listener);
    }

    public void update(float delta) {
        AnimationDesc v3 = null;
        if(!this.paused) {
            if(this.previous != null) {
                float v0 = this.transitionCurrentTime + delta;
                this.transitionCurrentTime = v0;
                if(v0 >= this.transitionTargetTime) {
                    this.removeAnimation(this.previous.animation);
                    this.justChangedAnimation = true;
                    this.animationPool.free(this.previous);
                    this.previous = v3;
                }
            }

            if(this.justChangedAnimation) {
                this.target.calculateTransforms();
                this.justChangedAnimation = false;
            }

            if(this.current == null) {
                return;
            }

            if(this.current.loopCount == 0) {
                return;
            }

            if(this.current.animation == null) {
                return;
            }

            this.justChangedAnimation = false;
            this.updating = true;
            float v6 = this.current.update(delta);
            if(v6 != 0f && this.queued != null) {
                this.inAction = false;
                this.animate(this.queued, this.queuedTransitionTime);
                this.queued = v3;
                this.updating = false;
                this.update(v6);
                return;
            }

            if(this.previous != null) {
                this.applyAnimations(this.previous.animation, this.previous.time + this.previous.offset, this.current.animation, this.current.time + this.current.offset, this.transitionCurrentTime / this.transitionTargetTime);
            }
            else {
                this.applyAnimation(this.current.animation, this.current.offset + this.current.time);
            }

            this.updating = false;
        }
    }
}

