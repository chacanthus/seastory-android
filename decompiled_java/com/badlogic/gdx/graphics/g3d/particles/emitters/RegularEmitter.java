// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.emitters;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.RangedNumericValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class RegularEmitter extends Emitter implements Serializable {
    public enum EmissionMode {
        public static final enum EmissionMode EnabledUntilCycleEnd;

        static  {
            EmissionMode.Enabled = new EmissionMode("Enabled", 0);
            EmissionMode.EnabledUntilCycleEnd = new EmissionMode("EnabledUntilCycleEnd", 1);
            EmissionMode.Disabled = new EmissionMode("Disabled", 2);
            EmissionMode[] v0 = new EmissionMode[3];
            v0[0] = EmissionMode.Enabled;
            v0[1] = EmissionMode.EnabledUntilCycleEnd;
            v0[2] = EmissionMode.Disabled;
            EmissionMode.$VALUES = v0;
        }

        private EmissionMode(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static EmissionMode valueOf(String name) {
            return Enum.valueOf(EmissionMode.class, name);
        }

        public static EmissionMode[] values() {
            return EmissionMode.$VALUES.clone();
        }
    }

    private boolean continuous;
    protected float delay;
    protected float delayTimer;
    public RangedNumericValue delayValue;
    protected float duration;
    protected float durationTimer;
    public RangedNumericValue durationValue;
    protected int emission;
    protected int emissionDelta;
    protected int emissionDiff;
    private EmissionMode emissionMode;
    public ScaledNumericValue emissionValue;
    protected int life;
    private FloatChannel lifeChannel;
    protected int lifeDiff;
    protected int lifeOffset;
    protected int lifeOffsetDiff;
    public ScaledNumericValue lifeOffsetValue;
    public ScaledNumericValue lifeValue;

    public RegularEmitter() {
        super();
        this.delayValue = new RangedNumericValue();
        this.durationValue = new RangedNumericValue();
        this.lifeOffsetValue = new ScaledNumericValue();
        this.lifeValue = new ScaledNumericValue();
        this.emissionValue = new ScaledNumericValue();
        this.durationValue.setActive(true);
        this.emissionValue.setActive(true);
        this.lifeValue.setActive(true);
        this.continuous = true;
        this.emissionMode = EmissionMode.Enabled;
    }

    public RegularEmitter(RegularEmitter regularEmitter) {
        this();
        this.set(regularEmitter);
    }

    public void activateParticles(int startIndex, int count) {
        int v2 = this.life + (((int)((((float)this.lifeDiff)) * this.lifeValue.getScale(this.percent))));
        int v1 = v2;
        int v5 = ((int)((((float)this.lifeOffset)) + (((float)this.lifeOffsetDiff)) * this.lifeOffsetValue.getScale(this.percent)));
        if(v5 > 0) {
            if(v5 >= v1) {
                v5 = v1 - 1;
            }

            v1 -= v5;
        }

        float v4 = 1f - (((float)v1)) / (((float)v2));
        int v3 = startIndex * this.lifeChannel.strideSize;
        int v0 = v3 + this.lifeChannel.strideSize * count;
        while(v3 < v0) {
            this.lifeChannel.data[v3] = ((float)v1);
            this.lifeChannel.data[v3 + 1] = ((float)v2);
            this.lifeChannel.data[v3 + 2] = v4;
            v3 += this.lifeChannel.strideSize;
        }
    }

    private void addParticles(int count) {
        count = Math.min(count, this.maxParticleCount - this.controller.particles.size);
        if(count > 0) {
            this.controller.activateParticles(this.controller.particles.size, count);
            this.controller.particles.size += count;
        }
    }

    public void allocateChannels() {
        this.lifeChannel = this.controller.particles.addChannel(ParticleChannels.Life);
    }

    public ParticleControllerComponent copy() {
        return new RegularEmitter(this);
    }

    public RangedNumericValue getDelay() {
        return this.delayValue;
    }

    public RangedNumericValue getDuration() {
        return this.durationValue;
    }

    public ScaledNumericValue getEmission() {
        return this.emissionValue;
    }

    public EmissionMode getEmissionMode() {
        return this.emissionMode;
    }

    public ScaledNumericValue getLife() {
        return this.lifeValue;
    }

    public ScaledNumericValue getLifeOffset() {
        return this.lifeOffsetValue;
    }

    public float getPercentComplete() {
        float v0;
        if(this.delayTimer < this.delay) {
            v0 = 0f;
        }
        else {
            v0 = Math.min(1f, this.durationTimer / this.duration);
        }

        return v0;
    }

    public void init() {
        super.init();
        this.emissionDelta = 0;
        this.durationTimer = this.duration;
    }

    public boolean isComplete() {
        boolean v0 = false;
        if(this.delayTimer >= this.delay && this.durationTimer >= this.duration && this.controller.particles.size == 0) {
            v0 = true;
        }

        return v0;
    }

    public boolean isContinuous() {
        return this.continuous;
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.continuous = json.readValue("continous", Boolean.TYPE, jsonData).booleanValue();
        this.emissionValue = json.readValue("emission", ScaledNumericValue.class, jsonData);
        this.delayValue = json.readValue("delay", RangedNumericValue.class, jsonData);
        this.durationValue = json.readValue("duration", RangedNumericValue.class, jsonData);
        this.lifeValue = json.readValue("life", ScaledNumericValue.class, jsonData);
        this.lifeOffsetValue = json.readValue("lifeOffset", ScaledNumericValue.class, jsonData);
    }

    public void set(RegularEmitter emitter) {
        super.set(((Emitter)emitter));
        this.delayValue.load(emitter.delayValue);
        this.durationValue.load(emitter.durationValue);
        this.lifeOffsetValue.load(emitter.lifeOffsetValue);
        this.lifeValue.load(emitter.lifeValue);
        this.emissionValue.load(emitter.emissionValue);
        this.emission = emitter.emission;
        this.emissionDiff = emitter.emissionDiff;
        this.emissionDelta = emitter.emissionDelta;
        this.lifeOffset = emitter.lifeOffset;
        this.lifeOffsetDiff = emitter.lifeOffsetDiff;
        this.life = emitter.life;
        this.lifeDiff = emitter.lifeDiff;
        this.duration = emitter.duration;
        this.delay = emitter.delay;
        this.durationTimer = emitter.durationTimer;
        this.delayTimer = emitter.delayTimer;
        this.continuous = emitter.continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public void setEmissionMode(EmissionMode emissionMode) {
        this.emissionMode = emissionMode;
    }

    public void start() {
        int v0_1;
        float v0;
        if(this.delayValue.active) {
            v0 = this.delayValue.newLowValue();
        }
        else {
            v0 = 0f;
        }

        this.delay = v0;
        this.delayTimer = 0f;
        this.durationTimer = 0f;
        this.duration = this.durationValue.newLowValue();
        this.percent = this.durationTimer / this.duration;
        this.emission = ((int)this.emissionValue.newLowValue());
        this.emissionDiff = ((int)this.emissionValue.newHighValue());
        if(!this.emissionValue.isRelative()) {
            this.emissionDiff -= this.emission;
        }

        this.life = ((int)this.lifeValue.newLowValue());
        this.lifeDiff = ((int)this.lifeValue.newHighValue());
        if(!this.lifeValue.isRelative()) {
            this.lifeDiff -= this.life;
        }

        if(this.lifeOffsetValue.active) {
            v0_1 = ((int)this.lifeOffsetValue.newLowValue());
        }
        else {
            v0_1 = 0;
        }

        this.lifeOffset = v0_1;
        this.lifeOffsetDiff = ((int)this.lifeOffsetValue.newHighValue());
        if(!this.lifeOffsetValue.isRelative()) {
            this.lifeOffsetDiff -= this.lifeOffset;
        }
    }

    public void update() {
        int v3;
        float v11 = 1000f;
        int v1 = ((int)(this.controller.deltaTime * v11));
        if(this.delayTimer < this.delay) {
            this.delayTimer += ((float)v1);
        }
        else {
            if(this.emissionMode != EmissionMode.Disabled) {
                v3 = 1;
            }
            else {
                v3 = 0;
            }

            if(this.durationTimer < this.duration) {
                this.durationTimer += ((float)v1);
                this.percent = this.durationTimer / this.duration;
            }
            else {
                if((this.continuous) && v3 != 0 && this.emissionMode == EmissionMode.Enabled) {
                    this.controller.start();
                    goto label_49;
                }

                v3 = 0;
            }

        label_49:
            if(v3 == 0) {
                goto label_13;
            }

            this.emissionDelta += v1;
            float v2 = (((float)this.emission)) + (((float)this.emissionDiff)) * this.emissionValue.getScale(this.percent);
            if(v2 > 0f) {
                v2 /= v11;
                if((((float)this.emissionDelta)) >= v2) {
                    int v4 = Math.min(((int)((((float)this.emissionDelta)) / v2)), this.maxParticleCount - this.controller.particles.size);
                    this.emissionDelta = ((int)((((float)this.emissionDelta)) - (((float)v4)) * v2));
                    this.emissionDelta = ((int)((((float)this.emissionDelta)) % v2));
                    this.addParticles(v4);
                }
            }

            if(this.controller.particles.size >= this.minParticleCount) {
                goto label_13;
            }

            this.addParticles(this.minParticleCount - this.controller.particles.size);
        }

    label_13:
        int v0 = this.controller.particles.size;
        int v5 = 0;
        int v6;
        for(v6 = 0; true; v6 += this.lifeChannel.strideSize) {
            if(v5 >= this.controller.particles.size) {
                break;
            }

            float[] v7 = this.lifeChannel.data;
            int v8 = v6;
            float v9 = v7[v8] - (((float)v1));
            v7[v8] = v9;
            if(v9 > 0f) {
                goto label_115;
            }

            this.controller.particles.removeElement(v5);
            continue;
        label_115:
            this.lifeChannel.data[v6 + 2] = 1f - this.lifeChannel.data[v6] / this.lifeChannel.data[v6 + 1];
            ++v5;
        }

        if(this.controller.particles.size < v0) {
            this.controller.killParticles(this.controller.particles.size, v0 - this.controller.particles.size);
        }
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("continous", Boolean.valueOf(this.continuous));
        json.writeValue("emission", this.emissionValue);
        json.writeValue("delay", this.delayValue);
        json.writeValue("duration", this.durationValue);
        json.writeValue("life", this.lifeValue);
        json.writeValue("lifeOffset", this.lifeOffsetValue);
    }
}

