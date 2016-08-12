// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class DynamicsModifier extends Influencer {
    public abstract class Angular extends Strength {
        protected FloatChannel angularChannel;
        public ScaledNumericValue phiValue;
        public ScaledNumericValue thetaValue;

        public Angular() {
            super();
            this.thetaValue = new ScaledNumericValue();
            this.phiValue = new ScaledNumericValue();
        }

        public Angular(Angular value) {
            super(((Strength)value));
            this.thetaValue = new ScaledNumericValue();
            this.phiValue = new ScaledNumericValue();
            this.thetaValue.load(value.thetaValue);
            this.phiValue.load(value.phiValue);
        }

        public void activateParticles(int startIndex, int count) {
            super.activateParticles(startIndex, count);
            int v2 = startIndex * this.angularChannel.strideSize;
            int v0 = v2 + this.angularChannel.strideSize * count;
            while(v2 < v0) {
                float v3 = this.thetaValue.newLowValue();
                float v1 = this.thetaValue.newHighValue();
                if(!this.thetaValue.isRelative()) {
                    v1 -= v3;
                }

                this.angularChannel.data[v2] = v3;
                this.angularChannel.data[v2 + 1] = v1;
                v3 = this.phiValue.newLowValue();
                v1 = this.phiValue.newHighValue();
                if(!this.phiValue.isRelative()) {
                    v1 -= v3;
                }

                this.angularChannel.data[v2 + 2] = v3;
                this.angularChannel.data[v2 + 3] = v1;
                v2 += this.angularChannel.strideSize;
            }
        }

        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation4.id = this.controller.particleChannels.newId();
            this.angularChannel = this.controller.particles.addChannel(ParticleChannels.Interpolation4);
        }

        public void read(Json json, JsonValue jsonData) {
            super.read(json, jsonData);
            this.thetaValue = json.readValue("thetaValue", ScaledNumericValue.class, jsonData);
            this.phiValue = json.readValue("phiValue", ScaledNumericValue.class, jsonData);
        }

        public void write(Json json) {
            super.write(json);
            json.writeValue("thetaValue", this.thetaValue);
            json.writeValue("phiValue", this.phiValue);
        }
    }

    public class BrownianAcceleration extends Strength {
        FloatChannel accelerationChannel;

        public BrownianAcceleration() {
            super();
        }

        public BrownianAcceleration(BrownianAcceleration rotation) {
            super(((Strength)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public BrownianAcceleration copy() {
            return new BrownianAcceleration(this);
        }

        public void update() {
            float v11 = 1f;
            float v10 = -1f;
            int v3 = 2;
            int v5 = 0;
            int v1 = 0;
            int v2 = 0;
            int v0 = this.controller.particles.size;
            while(v2 < v0) {
                BrownianAcceleration.TMP_V3.set(MathUtils.random(v10, v11), MathUtils.random(v10, v11), MathUtils.random(v10, v11)).nor().scl(this.strengthChannel.data[v5] + this.strengthChannel.data[v5 + 1] * this.strengthValue.getScale(this.lifeChannel.data[v3]));
                int v7 = v1;
                this.accelerationChannel.data[v7] += BrownianAcceleration.TMP_V3.x;
                v7 = v1 + 1;
                this.accelerationChannel.data[v7] += BrownianAcceleration.TMP_V3.y;
                v7 = v1 + 2;
                this.accelerationChannel.data[v7] += BrownianAcceleration.TMP_V3.z;
                ++v2;
                v5 += this.strengthChannel.strideSize;
                v1 += this.accelerationChannel.strideSize;
                v3 += this.lifeChannel.strideSize;
            }
        }
    }

    public class CentripetalAcceleration extends Strength {
        FloatChannel accelerationChannel;
        FloatChannel positionChannel;

        public CentripetalAcceleration() {
            super();
        }

        public CentripetalAcceleration(CentripetalAcceleration rotation) {
            super(((Strength)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public CentripetalAcceleration copy() {
            return new CentripetalAcceleration(this);
        }

        public void update() {
            float v2 = 0f;
            float v3 = 0f;
            float v4 = 0f;
            if(!this.isGlobal) {
                float[] v11 = this.controller.transform.val;
                v2 = v11[12];
                v3 = v11[13];
                v4 = v11[14];
            }

            int v7 = 2;
            int v10 = 0;
            int v8 = 0;
            int v5 = 0;
            int v6 = 0;
            int v1 = this.controller.particles.size;
            while(v6 < v1) {
                CentripetalAcceleration.TMP_V3.set(this.positionChannel.data[v8] - v2, this.positionChannel.data[v8 + 1] - v3, this.positionChannel.data[v8 + 2] - v4).nor().scl(this.strengthChannel.data[v10] + this.strengthChannel.data[v10 + 1] * this.strengthValue.getScale(this.lifeChannel.data[v7]));
                int v13 = v5;
                this.accelerationChannel.data[v13] += CentripetalAcceleration.TMP_V3.x;
                v13 = v5 + 1;
                this.accelerationChannel.data[v13] += CentripetalAcceleration.TMP_V3.y;
                v13 = v5 + 2;
                this.accelerationChannel.data[v13] += CentripetalAcceleration.TMP_V3.z;
                ++v6;
                v8 += this.positionChannel.strideSize;
                v10 += this.strengthChannel.strideSize;
                v5 += this.accelerationChannel.strideSize;
                v7 += this.lifeChannel.strideSize;
            }
        }
    }

    public class FaceDirection extends DynamicsModifier {
        FloatChannel accellerationChannel;
        FloatChannel rotationChannel;

        public FaceDirection() {
            this();
        }

        public FaceDirection(FaceDirection rotation) {
            this(((DynamicsModifier)rotation));
        }

        public void allocateChannels() {
            this.rotationChannel = this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.accellerationChannel = this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public ParticleControllerComponent copy() {
            return new FaceDirection(this);
        }

        public void update() {
            int v18 = 0;
            int v13 = 0;
            int v17 = this.controller.particles.size * this.rotationChannel.strideSize;
            while(v18 < v17) {
                Vector3 v16 = FaceDirection.TMP_V1.set(this.accellerationChannel.data[v13], this.accellerationChannel.data[v13 + 1], this.accellerationChannel.data[v13 + 2]).nor();
                Vector3 v15 = FaceDirection.TMP_V2.set(FaceDirection.TMP_V1).crs(Vector3.Y).nor().crs(FaceDirection.TMP_V1).nor();
                Vector3 v14 = FaceDirection.TMP_V3.set(v15).crs(v16).nor();
                FaceDirection.TMP_Q.setFromAxes(false, v14.x, v15.x, v16.x, v14.y, v15.y, v16.y, v14.z, v15.z, v16.z);
                this.rotationChannel.data[v18] = FaceDirection.TMP_Q.x;
                this.rotationChannel.data[v18 + 1] = FaceDirection.TMP_Q.y;
                this.rotationChannel.data[v18 + 2] = FaceDirection.TMP_Q.z;
                this.rotationChannel.data[v18 + 3] = FaceDirection.TMP_Q.w;
                v18 += this.rotationChannel.strideSize;
                v13 += this.accellerationChannel.strideSize;
            }
        }
    }

    public class PolarAcceleration extends Angular {
        FloatChannel directionalVelocityChannel;

        public PolarAcceleration() {
            super();
        }

        public PolarAcceleration(PolarAcceleration rotation) {
            super(((Angular)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public PolarAcceleration copy() {
            return new PolarAcceleration(this);
        }

        public void update() {
            int v5 = 0;
            int v6 = 2;
            int v9 = 0;
            int v1 = 0;
            int v2 = this.controller.particles.size * this.directionalVelocityChannel.strideSize;
            while(v5 < v2) {
                float v7 = this.lifeChannel.data[v6];
                float v12 = this.strengthChannel.data[v9] + this.strengthChannel.data[v9 + 1] * this.strengthValue.getScale(v7);
                float v8 = this.angularChannel.data[v1 + 2] + this.angularChannel.data[v1 + 3] * this.phiValue.getScale(v7);
                float v13 = this.angularChannel.data[v1] + this.angularChannel.data[v1 + 1] * this.thetaValue.getScale(v7);
                float v4 = MathUtils.cosDeg(v13);
                float v11 = MathUtils.sinDeg(v13);
                float v3 = MathUtils.cosDeg(v8);
                float v10 = MathUtils.sinDeg(v8);
                PolarAcceleration.TMP_V3.set(v4 * v10, v3, v11 * v10).nor().scl(v12);
                int v15 = v5;
                this.directionalVelocityChannel.data[v15] += PolarAcceleration.TMP_V3.x;
                v15 = v5 + 1;
                this.directionalVelocityChannel.data[v15] += PolarAcceleration.TMP_V3.y;
                v15 = v5 + 2;
                this.directionalVelocityChannel.data[v15] += PolarAcceleration.TMP_V3.z;
                v9 += this.strengthChannel.strideSize;
                v5 += this.directionalVelocityChannel.strideSize;
                v1 += this.angularChannel.strideSize;
                v6 += this.lifeChannel.strideSize;
            }
        }
    }

    public class Rotational2D extends Strength {
        FloatChannel rotationalVelocity2dChannel;

        public Rotational2D() {
            super();
        }

        public Rotational2D(Rotational2D rotation) {
            super(((Strength)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.rotationalVelocity2dChannel = this.controller.particles.addChannel(ParticleChannels.AngularVelocity2D);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Rotational2D copy() {
            return new Rotational2D(this);
        }

        public void update() {
            int v1 = 0;
            int v2 = 2;
            int v3 = 0;
            int v0 = this.controller.particles.size * this.rotationalVelocity2dChannel.strideSize;
            while(v1 < v0) {
                this.rotationalVelocity2dChannel.data[v1] += this.strengthChannel.data[v3] + this.strengthChannel.data[v3 + 1] * this.strengthValue.getScale(this.lifeChannel.data[v2]);
                v3 += this.strengthChannel.strideSize;
                v1 += this.rotationalVelocity2dChannel.strideSize;
                v2 += this.lifeChannel.strideSize;
            }
        }
    }

    public class Rotational3D extends Angular {
        FloatChannel rotationChannel;
        FloatChannel rotationalForceChannel;

        public Rotational3D() {
            super();
        }

        public Rotational3D(Rotational3D rotation) {
            super(((Angular)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.rotationChannel = this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.rotationalForceChannel = this.controller.particles.addChannel(ParticleChannels.AngularVelocity3D);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Rotational3D copy() {
            return new Rotational3D(this);
        }

        public void update() {
            int v5 = 0;
            int v6 = 2;
            int v9 = 0;
            int v1 = 0;
            int v2 = this.controller.particles.size * this.rotationalForceChannel.strideSize;
            while(v5 < v2) {
                float v7 = this.lifeChannel.data[v6];
                float v12 = this.strengthChannel.data[v9] + this.strengthChannel.data[v9 + 1] * this.strengthValue.getScale(v7);
                float v8 = this.angularChannel.data[v1 + 2] + this.angularChannel.data[v1 + 3] * this.phiValue.getScale(v7);
                float v13 = this.angularChannel.data[v1] + this.angularChannel.data[v1 + 1] * this.thetaValue.getScale(v7);
                float v4 = MathUtils.cosDeg(v13);
                float v11 = MathUtils.sinDeg(v13);
                float v3 = MathUtils.cosDeg(v8);
                float v10 = MathUtils.sinDeg(v8);
                Rotational3D.TMP_V3.set(v4 * v10, v3, v11 * v10);
                Rotational3D.TMP_V3.scl(0.017453f * v12);
                int v15 = v5;
                this.rotationalForceChannel.data[v15] += Rotational3D.TMP_V3.x;
                v15 = v5 + 1;
                this.rotationalForceChannel.data[v15] += Rotational3D.TMP_V3.y;
                v15 = v5 + 2;
                this.rotationalForceChannel.data[v15] += Rotational3D.TMP_V3.z;
                v9 += this.strengthChannel.strideSize;
                v5 += this.rotationalForceChannel.strideSize;
                v1 += this.angularChannel.strideSize;
                v6 += this.lifeChannel.strideSize;
            }
        }
    }

    public abstract class Strength extends DynamicsModifier {
        protected FloatChannel strengthChannel;
        public ScaledNumericValue strengthValue;

        public Strength() {
            this();
            this.strengthValue = new ScaledNumericValue();
        }

        public Strength(Strength rotation) {
            this(((DynamicsModifier)rotation));
            this.strengthValue = new ScaledNumericValue();
            this.strengthValue.load(rotation.strengthValue);
        }

        public void activateParticles(int startIndex, int count) {
            int v2 = startIndex * this.strengthChannel.strideSize;
            int v0 = v2 + this.strengthChannel.strideSize * count;
            while(v2 < v0) {
                float v3 = this.strengthValue.newLowValue();
                float v1 = this.strengthValue.newHighValue();
                if(!this.strengthValue.isRelative()) {
                    v1 -= v3;
                }

                this.strengthChannel.data[v2] = v3;
                this.strengthChannel.data[v2 + 1] = v1;
                v2 += this.strengthChannel.strideSize;
            }
        }

        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
            this.strengthChannel = this.controller.particles.addChannel(ParticleChannels.Interpolation);
        }

        public void read(Json json, JsonValue jsonData) {
            super.read(json, jsonData);
            this.strengthValue = json.readValue("strengthValue", ScaledNumericValue.class, jsonData);
        }

        public void write(Json json) {
            super.write(json);
            json.writeValue("strengthValue", this.strengthValue);
        }
    }

    public class TangentialAcceleration extends Angular {
        FloatChannel directionalVelocityChannel;
        FloatChannel positionChannel;

        public TangentialAcceleration() {
            super();
        }

        public TangentialAcceleration(TangentialAcceleration rotation) {
            super(((Angular)rotation));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public TangentialAcceleration copy() {
            return new TangentialAcceleration(this);
        }

        public void update() {
            int v7 = 0;
            int v8 = 2;
            int v12 = 0;
            int v3 = 0;
            int v11 = 0;
            int v4 = this.controller.particles.size * this.directionalVelocityChannel.strideSize;
            while(v7 < v4) {
                float v9 = this.lifeChannel.data[v8];
                float v15 = this.strengthChannel.data[v12] + this.strengthChannel.data[v12 + 1] * this.strengthValue.getScale(v9);
                float v10 = this.angularChannel.data[v3 + 2] + this.angularChannel.data[v3 + 3] * this.phiValue.getScale(v9);
                float v16 = this.angularChannel.data[v3] + this.angularChannel.data[v3 + 1] * this.thetaValue.getScale(v9);
                float v6 = MathUtils.cosDeg(v16);
                float v14 = MathUtils.sinDeg(v16);
                float v5 = MathUtils.cosDeg(v10);
                float v13 = MathUtils.sinDeg(v10);
                TangentialAcceleration.TMP_V3.set(v6 * v13, v5, v14 * v13).crs(this.positionChannel.data[v11], this.positionChannel.data[v11 + 1], this.positionChannel.data[v11 + 2]).nor().scl(v15);
                int v18 = v7;
                this.directionalVelocityChannel.data[v18] += TangentialAcceleration.TMP_V3.x;
                v18 = v7 + 1;
                this.directionalVelocityChannel.data[v18] += TangentialAcceleration.TMP_V3.y;
                v18 = v7 + 2;
                this.directionalVelocityChannel.data[v18] += TangentialAcceleration.TMP_V3.z;
                v12 += this.strengthChannel.strideSize;
                v7 += this.directionalVelocityChannel.strideSize;
                v3 += this.angularChannel.strideSize;
                v8 += this.lifeChannel.strideSize;
                v11 += this.positionChannel.strideSize;
            }
        }
    }

    protected static final Quaternion TMP_Q;
    protected static final Vector3 TMP_V1;
    protected static final Vector3 TMP_V2;
    protected static final Vector3 TMP_V3;
    public boolean isGlobal;
    protected FloatChannel lifeChannel;

    static  {
        DynamicsModifier.TMP_V1 = new Vector3();
        DynamicsModifier.TMP_V2 = new Vector3();
        DynamicsModifier.TMP_V3 = new Vector3();
        DynamicsModifier.TMP_Q = new Quaternion();
    }

    public DynamicsModifier() {
        super();
        this.isGlobal = false;
    }

    public DynamicsModifier(DynamicsModifier modifier) {
        super();
        this.isGlobal = false;
        this.isGlobal = modifier.isGlobal;
    }

    public void allocateChannels() {
        this.lifeChannel = this.controller.particles.addChannel(ParticleChannels.Life);
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.isGlobal = json.readValue("isGlobal", Boolean.TYPE, jsonData).booleanValue();
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("isGlobal", Boolean.valueOf(this.isGlobal));
    }
}

