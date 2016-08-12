// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class ParticleEmitter {
    public class GradientColorValue extends ParticleValue {
        private float[] colors;
        private static float[] temp;
        float[] timeline;

        static  {
            GradientColorValue.temp = new float[4];
        }

        public GradientColorValue() {
            super();
            this.colors = new float[]{1f, 1f, 1f};
            float[] v0 = new float[1];
            v0[0] = 0f;
            this.timeline = v0;
            this.alwaysActive = true;
        }

        public float[] getColor(float percent) {
            float[] v11;
            int v7 = 0;
            int v1 = -1;
            float[] v10 = this.timeline;
            int v5 = v10.length;
            int v4 = 1;
            while(v4 < v5) {
                if(v10[v4] > percent) {
                    v1 = v4;
                }
                else {
                    v7 = v4;
                    ++v4;
                    continue;
                }

                break;
            }

            float v8 = v10[v7];
            v7 *= 3;
            float v6 = this.colors[v7];
            float v3 = this.colors[v7 + 1];
            float v0 = this.colors[v7 + 2];
            if(v1 == -1) {
                GradientColorValue.temp[0] = v6;
                GradientColorValue.temp[1] = v3;
                GradientColorValue.temp[2] = v0;
                v11 = GradientColorValue.temp;
            }
            else {
                float v2 = (percent - v8) / (v10[v1] - v8);
                v1 *= 3;
                GradientColorValue.temp[0] = (this.colors[v1] - v6) * v2 + v6;
                GradientColorValue.temp[1] = (this.colors[v1 + 1] - v3) * v2 + v3;
                GradientColorValue.temp[2] = (this.colors[v1 + 2] - v0) * v2 + v0;
                v11 = GradientColorValue.temp;
            }

            return v11;
        }

        public float[] getColors() {
            return this.colors;
        }

        public float[] getTimeline() {
            return this.timeline;
        }

        public void load(GradientColorValue value) {
            super.load(((ParticleValue)value));
            this.colors = new float[value.colors.length];
            System.arraycopy(value.colors, 0, this.colors, 0, this.colors.length);
            this.timeline = new float[value.timeline.length];
            System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if(this.active) {
                this.colors = new float[ParticleEmitter.readInt(reader, "colorsCount")];
                int v0;
                for(v0 = 0; v0 < this.colors.length; ++v0) {
                    this.colors[v0] = ParticleEmitter.readFloat(reader, "colors" + v0);
                }

                this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
                for(v0 = 0; v0 < this.timeline.length; ++v0) {
                    this.timeline[v0] = ParticleEmitter.readFloat(reader, "timeline" + v0);
                }
            }
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if(this.active) {
                output.write("colorsCount: " + this.colors.length + "\n");
                int v0;
                for(v0 = 0; v0 < this.colors.length; ++v0) {
                    output.write("colors" + v0 + ": " + this.colors[v0] + "\n");
                }

                output.write("timelineCount: " + this.timeline.length + "\n");
                for(v0 = 0; v0 < this.timeline.length; ++v0) {
                    output.write("timeline" + v0 + ": " + this.timeline[v0] + "\n");
                }
            }
        }

        public void setColors(float[] colors) {
            this.colors = colors;
        }

        public void setTimeline(float[] timeline) {
            this.timeline = timeline;
        }
    }

    public class NumericValue extends ParticleValue {
        private float value;

        public NumericValue() {
            super();
        }

        public float getValue() {
            return this.value;
        }

        public void load(NumericValue value) {
            super.load(((ParticleValue)value));
            this.value = value.value;
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if(this.active) {
                this.value = ParticleEmitter.readFloat(reader, "value");
            }
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if(this.active) {
                output.write("value: " + this.value + "\n");
            }
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    public class Particle extends Sprite {
        protected float angle;
        protected float angleCos;
        protected float angleDiff;
        protected float angleSin;
        protected int currentLife;
        protected float gravity;
        protected float gravityDiff;
        protected int life;
        protected float rotation;
        protected float rotationDiff;
        protected float scale;
        protected float scaleDiff;
        protected float[] tint;
        protected float transparency;
        protected float transparencyDiff;
        protected float velocity;
        protected float velocityDiff;
        protected float wind;
        protected float windDiff;

        public Particle(Sprite sprite) {
            super(sprite);
        }
    }

    public class ParticleValue {
        boolean active;
        boolean alwaysActive;

        public ParticleValue() {
            super();
        }

        public boolean isActive() {
            boolean v0;
            if((this.alwaysActive) || (this.active)) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public boolean isAlwaysActive() {
            return this.alwaysActive;
        }

        public void load(ParticleValue value) {
            this.active = value.active;
            this.alwaysActive = value.alwaysActive;
        }

        public void load(BufferedReader reader) throws IOException {
            if(!this.alwaysActive) {
                this.active = ParticleEmitter.readBoolean(reader, "active");
            }
            else {
                this.active = true;
            }
        }

        public void save(Writer output) throws IOException {
            if(!this.alwaysActive) {
                output.write("active: " + this.active + "\n");
            }
            else {
                this.active = true;
            }
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setAlwaysActive(boolean alwaysActive) {
            this.alwaysActive = alwaysActive;
        }
    }

    public class RangedNumericValue extends ParticleValue {
        private float lowMax;
        private float lowMin;

        public RangedNumericValue() {
            super();
        }

        public float getLowMax() {
            return this.lowMax;
        }

        public float getLowMin() {
            return this.lowMin;
        }

        public void load(RangedNumericValue value) {
            super.load(((ParticleValue)value));
            this.lowMax = value.lowMax;
            this.lowMin = value.lowMin;
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if(this.active) {
                this.lowMin = ParticleEmitter.readFloat(reader, "lowMin");
                this.lowMax = ParticleEmitter.readFloat(reader, "lowMax");
            }
        }

        public float newLowValue() {
            return this.lowMin + (this.lowMax - this.lowMin) * MathUtils.random();
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if(this.active) {
                output.write("lowMin: " + this.lowMin + "\n");
                output.write("lowMax: " + this.lowMax + "\n");
            }
        }

        public void setLow(float min, float max) {
            this.lowMin = min;
            this.lowMax = max;
        }

        public void setLow(float value) {
            this.lowMin = value;
            this.lowMax = value;
        }

        public void setLowMax(float lowMax) {
            this.lowMax = lowMax;
        }

        public void setLowMin(float lowMin) {
            this.lowMin = lowMin;
        }
    }

    public class ScaledNumericValue extends RangedNumericValue {
        private float highMax;
        private float highMin;
        private boolean relative;
        private float[] scaling;
        float[] timeline;

        public ScaledNumericValue() {
            super();
            float[] v0 = new float[1];
            v0[0] = 1f;
            this.scaling = v0;
            v0 = new float[1];
            v0[0] = 0f;
            this.timeline = v0;
        }

        public float getHighMax() {
            return this.highMax;
        }

        public float getHighMin() {
            return this.highMin;
        }

        public float getScale(float percent) {
            float v9;
            int v0 = -1;
            float[] v8 = this.timeline;
            int v2 = v8.length;
            int v1 = 1;
            while(v1 < v2) {
                if(v8[v1] > percent) {
                    v0 = v1;
                }
                else {
                    ++v1;
                    continue;
                }

                break;
            }

            if(v0 == -1) {
                v9 = this.scaling[v2 - 1];
            }
            else {
                int v4 = v0 - 1;
                v9 = (this.scaling[v0] - this.scaling[v4]) * ((percent - v8[v4]) / (v8[v0] - v8[v4])) + this.scaling[v4];
            }

            return v9;
        }

        public float[] getScaling() {
            return this.scaling;
        }

        public float[] getTimeline() {
            return this.timeline;
        }

        public boolean isRelative() {
            return this.relative;
        }

        public void load(ScaledNumericValue value) {
            super.load(((RangedNumericValue)value));
            this.highMax = value.highMax;
            this.highMin = value.highMin;
            this.scaling = new float[value.scaling.length];
            System.arraycopy(value.scaling, 0, this.scaling, 0, this.scaling.length);
            this.timeline = new float[value.timeline.length];
            System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
            this.relative = value.relative;
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if(this.active) {
                this.highMin = ParticleEmitter.readFloat(reader, "highMin");
                this.highMax = ParticleEmitter.readFloat(reader, "highMax");
                this.relative = ParticleEmitter.readBoolean(reader, "relative");
                this.scaling = new float[ParticleEmitter.readInt(reader, "scalingCount")];
                int v0;
                for(v0 = 0; v0 < this.scaling.length; ++v0) {
                    this.scaling[v0] = ParticleEmitter.readFloat(reader, "scaling" + v0);
                }

                this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
                for(v0 = 0; v0 < this.timeline.length; ++v0) {
                    this.timeline[v0] = ParticleEmitter.readFloat(reader, "timeline" + v0);
                }
            }
        }

        public float newHighValue() {
            return this.highMin + (this.highMax - this.highMin) * MathUtils.random();
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if(this.active) {
                output.write("highMin: " + this.highMin + "\n");
                output.write("highMax: " + this.highMax + "\n");
                output.write("relative: " + this.relative + "\n");
                output.write("scalingCount: " + this.scaling.length + "\n");
                int v0;
                for(v0 = 0; v0 < this.scaling.length; ++v0) {
                    output.write("scaling" + v0 + ": " + this.scaling[v0] + "\n");
                }

                output.write("timelineCount: " + this.timeline.length + "\n");
                for(v0 = 0; v0 < this.timeline.length; ++v0) {
                    output.write("timeline" + v0 + ": " + this.timeline[v0] + "\n");
                }
            }
        }

        public void setHigh(float min, float max) {
            this.highMin = min;
            this.highMax = max;
        }

        public void setHigh(float value) {
            this.highMin = value;
            this.highMax = value;
        }

        public void setHighMax(float highMax) {
            this.highMax = highMax;
        }

        public void setHighMin(float highMin) {
            this.highMin = highMin;
        }

        public void setRelative(boolean relative) {
            this.relative = relative;
        }

        public void setScaling(float[] values) {
            this.scaling = values;
        }

        public void setTimeline(float[] timeline) {
            this.timeline = timeline;
        }
    }

    public enum SpawnEllipseSide {
        public static final enum SpawnEllipseSide both;
        public static final enum SpawnEllipseSide bottom;
        public static final enum SpawnEllipseSide top;

        static  {
            SpawnEllipseSide.both = new SpawnEllipseSide("both", 0);
            SpawnEllipseSide.top = new SpawnEllipseSide("top", 1);
            SpawnEllipseSide.bottom = new SpawnEllipseSide("bottom", 2);
            SpawnEllipseSide[] v0 = new SpawnEllipseSide[3];
            v0[0] = SpawnEllipseSide.both;
            v0[1] = SpawnEllipseSide.top;
            v0[2] = SpawnEllipseSide.bottom;
            SpawnEllipseSide.$VALUES = v0;
        }

        private SpawnEllipseSide(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static SpawnEllipseSide valueOf(String name) {
            return Enum.valueOf(SpawnEllipseSide.class, name);
        }

        public static SpawnEllipseSide[] values() {
            return SpawnEllipseSide.$VALUES.clone();
        }
    }

    public enum SpawnShape {
        public static final enum SpawnShape ellipse;
        public static final enum SpawnShape line;
        public static final enum SpawnShape point;
        public static final enum SpawnShape square;

        static  {
            SpawnShape.point = new SpawnShape("point", 0);
            SpawnShape.line = new SpawnShape("line", 1);
            SpawnShape.square = new SpawnShape("square", 2);
            SpawnShape.ellipse = new SpawnShape("ellipse", 3);
            SpawnShape[] v0 = new SpawnShape[4];
            v0[0] = SpawnShape.point;
            v0[1] = SpawnShape.line;
            v0[2] = SpawnShape.square;
            v0[3] = SpawnShape.ellipse;
            SpawnShape.$VALUES = v0;
        }

        private SpawnShape(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static SpawnShape valueOf(String name) {
            return Enum.valueOf(SpawnShape.class, name);
        }

        public static SpawnShape[] values() {
            return SpawnShape.$VALUES.clone();
        }
    }

    public class SpawnShapeValue extends ParticleValue {
        boolean edges;
        SpawnShape shape;
        SpawnEllipseSide side;

        public SpawnShapeValue() {
            super();
            this.shape = SpawnShape.point;
            this.side = SpawnEllipseSide.both;
        }

        public SpawnShape getShape() {
            return this.shape;
        }

        public SpawnEllipseSide getSide() {
            return this.side;
        }

        public boolean isEdges() {
            return this.edges;
        }

        public void load(SpawnShapeValue value) {
            super.load(((ParticleValue)value));
            this.shape = value.shape;
            this.edges = value.edges;
            this.side = value.side;
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if(this.active) {
                this.shape = SpawnShape.valueOf(ParticleEmitter.readString(reader, "shape"));
                if(this.shape == SpawnShape.ellipse) {
                    this.edges = ParticleEmitter.readBoolean(reader, "edges");
                    this.side = SpawnEllipseSide.valueOf(ParticleEmitter.readString(reader, "side"));
                }
            }
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if(this.active) {
                output.write("shape: " + this.shape + "\n");
                if(this.shape == SpawnShape.ellipse) {
                    output.write("edges: " + this.edges + "\n");
                    output.write("side: " + this.side + "\n");
                }
            }
        }

        public void setEdges(boolean edges) {
            this.edges = edges;
        }

        public void setShape(SpawnShape shape) {
            this.shape = shape;
        }

        public void setSide(SpawnEllipseSide side) {
            this.side = side;
        }
    }

    private static final int UPDATE_ANGLE = 2;
    private static final int UPDATE_GRAVITY = 32;
    private static final int UPDATE_ROTATION = 4;
    private static final int UPDATE_SCALE = 1;
    private static final int UPDATE_TINT = 64;
    private static final int UPDATE_VELOCITY = 8;
    private static final int UPDATE_WIND = 16;
    private float accumulator;
    private boolean[] active;
    private int activeCount;
    private boolean additive;
    private boolean aligned;
    private boolean allowCompletion;
    private ScaledNumericValue angleValue;
    private boolean attached;
    private boolean behind;
    private BoundingBox bounds;
    boolean cleansUpBlendFunction;
    private boolean continuous;
    private float delay;
    private float delayTimer;
    private RangedNumericValue delayValue;
    public float duration;
    public float durationTimer;
    private RangedNumericValue durationValue;
    private int emission;
    private int emissionDelta;
    private int emissionDiff;
    private ScaledNumericValue emissionValue;
    private boolean firstUpdate;
    private boolean flipX;
    private boolean flipY;
    private ScaledNumericValue gravityValue;
    private String imagePath;
    private int life;
    private int lifeDiff;
    private int lifeOffset;
    private int lifeOffsetDiff;
    private ScaledNumericValue lifeOffsetValue;
    private ScaledNumericValue lifeValue;
    private int maxParticleCount;
    private int minParticleCount;
    private String name;
    private Particle[] particles;
    private boolean premultipliedAlpha;
    private ScaledNumericValue rotationValue;
    private ScaledNumericValue scaleValue;
    private float spawnHeight;
    private float spawnHeightDiff;
    private ScaledNumericValue spawnHeightValue;
    private SpawnShapeValue spawnShapeValue;
    private float spawnWidth;
    private float spawnWidthDiff;
    private ScaledNumericValue spawnWidthValue;
    private Sprite sprite;
    private GradientColorValue tintValue;
    private ScaledNumericValue transparencyValue;
    private int updateFlags;
    private ScaledNumericValue velocityValue;
    private ScaledNumericValue windValue;
    private float x;
    private RangedNumericValue xOffsetValue;
    private float y;
    private RangedNumericValue yOffsetValue;

    public ParticleEmitter() {
        super();
        this.delayValue = new RangedNumericValue();
        this.lifeOffsetValue = new ScaledNumericValue();
        this.durationValue = new RangedNumericValue();
        this.lifeValue = new ScaledNumericValue();
        this.emissionValue = new ScaledNumericValue();
        this.scaleValue = new ScaledNumericValue();
        this.rotationValue = new ScaledNumericValue();
        this.velocityValue = new ScaledNumericValue();
        this.angleValue = new ScaledNumericValue();
        this.windValue = new ScaledNumericValue();
        this.gravityValue = new ScaledNumericValue();
        this.transparencyValue = new ScaledNumericValue();
        this.tintValue = new GradientColorValue();
        this.xOffsetValue = new ScaledNumericValue();
        this.yOffsetValue = new ScaledNumericValue();
        this.spawnWidthValue = new ScaledNumericValue();
        this.spawnHeightValue = new ScaledNumericValue();
        this.spawnShapeValue = new SpawnShapeValue();
        this.maxParticleCount = 4;
        this.duration = 1f;
        this.additive = true;
        this.premultipliedAlpha = false;
        this.cleansUpBlendFunction = true;
        this.initialize();
    }

    public ParticleEmitter(ParticleEmitter emitter) {
        super();
        this.delayValue = new RangedNumericValue();
        this.lifeOffsetValue = new ScaledNumericValue();
        this.durationValue = new RangedNumericValue();
        this.lifeValue = new ScaledNumericValue();
        this.emissionValue = new ScaledNumericValue();
        this.scaleValue = new ScaledNumericValue();
        this.rotationValue = new ScaledNumericValue();
        this.velocityValue = new ScaledNumericValue();
        this.angleValue = new ScaledNumericValue();
        this.windValue = new ScaledNumericValue();
        this.gravityValue = new ScaledNumericValue();
        this.transparencyValue = new ScaledNumericValue();
        this.tintValue = new GradientColorValue();
        this.xOffsetValue = new ScaledNumericValue();
        this.yOffsetValue = new ScaledNumericValue();
        this.spawnWidthValue = new ScaledNumericValue();
        this.spawnHeightValue = new ScaledNumericValue();
        this.spawnShapeValue = new SpawnShapeValue();
        this.maxParticleCount = 4;
        this.duration = 1f;
        this.additive = true;
        this.premultipliedAlpha = false;
        this.cleansUpBlendFunction = true;
        this.sprite = emitter.sprite;
        this.name = emitter.name;
        this.imagePath = emitter.imagePath;
        this.setMaxParticleCount(emitter.maxParticleCount);
        this.minParticleCount = emitter.minParticleCount;
        this.delayValue.load(emitter.delayValue);
        this.durationValue.load(emitter.durationValue);
        this.emissionValue.load(emitter.emissionValue);
        this.lifeValue.load(emitter.lifeValue);
        this.lifeOffsetValue.load(emitter.lifeOffsetValue);
        this.scaleValue.load(emitter.scaleValue);
        this.rotationValue.load(emitter.rotationValue);
        this.velocityValue.load(emitter.velocityValue);
        this.angleValue.load(emitter.angleValue);
        this.windValue.load(emitter.windValue);
        this.gravityValue.load(emitter.gravityValue);
        this.transparencyValue.load(emitter.transparencyValue);
        this.tintValue.load(emitter.tintValue);
        this.xOffsetValue.load(emitter.xOffsetValue);
        this.yOffsetValue.load(emitter.yOffsetValue);
        this.spawnWidthValue.load(emitter.spawnWidthValue);
        this.spawnHeightValue.load(emitter.spawnHeightValue);
        this.spawnShapeValue.load(emitter.spawnShapeValue);
        this.attached = emitter.attached;
        this.continuous = emitter.continuous;
        this.aligned = emitter.aligned;
        this.behind = emitter.behind;
        this.additive = emitter.additive;
        this.premultipliedAlpha = emitter.premultipliedAlpha;
        this.cleansUpBlendFunction = emitter.cleansUpBlendFunction;
    }

    public ParticleEmitter(BufferedReader reader) throws IOException {
        super();
        this.delayValue = new RangedNumericValue();
        this.lifeOffsetValue = new ScaledNumericValue();
        this.durationValue = new RangedNumericValue();
        this.lifeValue = new ScaledNumericValue();
        this.emissionValue = new ScaledNumericValue();
        this.scaleValue = new ScaledNumericValue();
        this.rotationValue = new ScaledNumericValue();
        this.velocityValue = new ScaledNumericValue();
        this.angleValue = new ScaledNumericValue();
        this.windValue = new ScaledNumericValue();
        this.gravityValue = new ScaledNumericValue();
        this.transparencyValue = new ScaledNumericValue();
        this.tintValue = new GradientColorValue();
        this.xOffsetValue = new ScaledNumericValue();
        this.yOffsetValue = new ScaledNumericValue();
        this.spawnWidthValue = new ScaledNumericValue();
        this.spawnHeightValue = new ScaledNumericValue();
        this.spawnShapeValue = new SpawnShapeValue();
        this.maxParticleCount = 4;
        this.duration = 1f;
        this.additive = true;
        this.premultipliedAlpha = false;
        this.cleansUpBlendFunction = true;
        this.initialize();
        this.load(reader);
    }

    private void activateParticle(int index) {
        float v13;
        float v12;
        float v20;
        float v7;
        float v25;
        Particle v10 = this.particles[index];
        if(v10 == null) {
            Particle[] v28 = this.particles;
            v10 = this.newParticle(this.sprite);
            v28[index] = v10;
            v10.flip(this.flipX, this.flipY);
        }

        float v11 = this.durationTimer / this.duration;
        int v24 = this.updateFlags;
        int v28_1 = this.life + (((int)((((float)this.lifeDiff)) * this.lifeValue.getScale(v11))));
        v10.life = v28_1;
        v10.currentLife = v28_1;
        if(this.velocityValue.active) {
            v10.velocity = this.velocityValue.newLowValue();
            v10.velocityDiff = this.velocityValue.newHighValue();
            if(!this.velocityValue.isRelative()) {
                v10.velocityDiff -= v10.velocity;
            }
        }

        v10.angle = this.angleValue.newLowValue();
        v10.angleDiff = this.angleValue.newHighValue();
        if(!this.angleValue.isRelative()) {
            v10.angleDiff -= v10.angle;
        }

        float v4 = 0f;
        if((v24 & 2) == 0) {
            v4 = v10.angle + v10.angleDiff * this.angleValue.getScale(0f);
            v10.angle = v4;
            v10.angleCos = MathUtils.cosDeg(v4);
            v10.angleSin = MathUtils.sinDeg(v4);
        }

        float v22 = this.sprite.getWidth();
        v10.scale = this.scaleValue.newLowValue() / v22;
        v10.scaleDiff = this.scaleValue.newHighValue() / v22;
        if(!this.scaleValue.isRelative()) {
            v10.scaleDiff -= v10.scale;
        }

        v10.setScale(v10.scale + v10.scaleDiff * this.scaleValue.getScale(0f));
        if(this.rotationValue.active) {
            v10.rotation = this.rotationValue.newLowValue();
            v10.rotationDiff = this.rotationValue.newHighValue();
            if(!this.rotationValue.isRelative()) {
                v10.rotationDiff -= v10.rotation;
            }

            float v17 = v10.rotation + v10.rotationDiff * this.rotationValue.getScale(0f);
            if(this.aligned) {
                v17 += v4;
            }

            v10.setRotation(v17);
        }

        if(this.windValue.active) {
            v10.wind = this.windValue.newLowValue();
            v10.windDiff = this.windValue.newHighValue();
            if(!this.windValue.isRelative()) {
                v10.windDiff -= v10.wind;
            }
        }

        if(this.gravityValue.active) {
            v10.gravity = this.gravityValue.newLowValue();
            v10.gravityDiff = this.gravityValue.newHighValue();
            if(!this.gravityValue.isRelative()) {
                v10.gravityDiff -= v10.gravity;
            }
        }

        float[] v5 = v10.tint;
        if(v5 == null) {
            v5 = new float[3];
            v10.tint = v5;
        }

        float[] v23 = this.tintValue.getColor(0f);
        v5[0] = v23[0];
        v5[1] = v23[1];
        v5[2] = v23[2];
        v10.transparency = this.transparencyValue.newLowValue();
        v10.transparencyDiff = this.transparencyValue.newHighValue() - v10.transparency;
        float v26 = this.x;
        if(this.xOffsetValue.active) {
            v26 += this.xOffsetValue.newLowValue();
        }

        float v27 = this.y;
        if(this.yOffsetValue.active) {
            v27 += this.yOffsetValue.newLowValue();
        }

        switch(com.badlogic.gdx.graphics.g2d.ParticleEmitter$1.$SwitchMap$com$badlogic$gdx$graphics$g2d$ParticleEmitter$SpawnShape[this.spawnShapeValue.shape.ordinal()]) {
            case 1: {
                v25 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(v11);
                v7 = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(v11);
                v26 += MathUtils.random(v25) - v25 / 2f;
                v27 += MathUtils.random(v7) - v7 / 2f;
                break;
            }
            case 2: {
                v25 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(v11);
                float v15 = v25 / 2f;
                float v16 = (this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(v11)) / 2f;
                if(v15 == 0f) {
                    goto label_365;
                }

                if(v16 == 0f) {
                    goto label_365;
                }

                float v18 = v15 / v16;
                if(!this.spawnShapeValue.edges) {
                    goto label_532;
                }

                switch(com.badlogic.gdx.graphics.g2d.ParticleEmitter$1.$SwitchMap$com$badlogic$gdx$graphics$g2d$ParticleEmitter$SpawnEllipseSide[this.spawnShapeValue.side.ordinal()]) {
                    case 1: {
                        v20 = -MathUtils.random(179f);
                        break;
                    }
                    case 2: {
                        v20 = MathUtils.random(179f);
                        break;
                    }
                    default: {
                        v20 = MathUtils.random(360f);
                        break;
                    }
                }

                float v6 = MathUtils.cosDeg(v20);
                float v19 = MathUtils.sinDeg(v20);
                v26 += v6 * v15;
                v27 += v19 * v15 / v18;
                if((v24 & 2) != 0) {
                    goto label_365;
                }

                v10.angle = v20;
                v10.angleCos = v6;
                v10.angleSin = v19;
                goto label_365;
            label_532:
                float v14 = v15 * v15;
                do {
                    v12 = MathUtils.random(v25) - v15;
                    v13 = MathUtils.random(v25) - v15;
                }
                while(v12 * v12 + v13 * v13 > v14);

                v26 += v12;
                v27 += v13 / v18;
                break;
            }
            case 3: {
                v25 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(v11);
                v7 = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(v11);
                if(v25 == 0f) {
                    goto label_580;
                }

                float v8 = v25 * MathUtils.random();
                v26 += v8;
                v27 += v7 / v25 * v8;
                goto label_365;
            label_580:
                v27 += MathUtils.random() * v7;
                break;
            }
        }

    label_365:
        float v21 = this.sprite.getHeight();
        v10.setBounds(v26 - v22 / 2f, v27 - v21 / 2f, v22, v21);
        int v9 = ((int)((((float)this.lifeOffset)) + (((float)this.lifeOffsetDiff)) * this.lifeOffsetValue.getScale(v11)));
        if(v9 > 0) {
            if(v9 >= v10.currentLife) {
                v9 = v10.currentLife - 1;
            }

            this.updateParticle(v10, (((float)v9)) / 1000f, v9);
        }
    }

    public void addParticle() {
        int v1 = this.activeCount;
        if(v1 != this.maxParticleCount) {
            boolean[] v0 = this.active;
            int v2 = 0;
            int v3 = v0.length;
            while(v2 < v3) {
                if(!v0[v2]) {
                    this.activateParticle(v2);
                    v0[v2] = true;
                    this.activeCount = v1 + 1;
                }
                else {
                    ++v2;
                    continue;
                }

                return;
            }
        }
    }

    public void addParticles(int count) {
        int v3;
        count = Math.min(count, this.maxParticleCount - this.activeCount);
        if(count != 0) {
            boolean[] v0 = this.active;
            int v2 = 0;
            int v4 = v0.length;
            int v1 = 0;
            while(true) {
            label_10:
                if(v1 < count) {
                    v3 = v2;
                    while(true) {
                    label_12:
                        if(v3 < v4) {
                            if(!v0[v3]) {
                                this.activateParticle(v3);
                                v2 = v3 + 1;
                                v0[v3] = true;
                                ++v1;
                                goto label_10;
                            }
                            else {
                                goto label_21;
                            }
                        }

                        goto label_25;
                    }
                }

                goto label_25;
            }

        label_21:
            ++v3;
            goto label_12;
        label_25:
            this.activeCount += count;
        }
    }

    public void allowCompletion() {
        this.allowCompletion = true;
        this.durationTimer = this.duration;
    }

    public boolean cleansUpBlendFunction() {
        return this.cleansUpBlendFunction;
    }

    public void draw(Batch batch) {
        int v6 = 771;
        int v5 = 770;
        if(this.premultipliedAlpha) {
            batch.setBlendFunction(1, v6);
        }
        else if(this.additive) {
            batch.setBlendFunction(v5, 1);
        }
        else {
            batch.setBlendFunction(v5, v6);
        }

        Particle[] v3 = this.particles;
        boolean[] v0 = this.active;
        int v1 = 0;
        int v2 = v0.length;
        while(v1 < v2) {
            if(v0[v1]) {
                v3[v1].draw(batch);
            }

            ++v1;
        }

        if((this.cleansUpBlendFunction) && ((this.additive) || (this.premultipliedAlpha))) {
            batch.setBlendFunction(v5, v6);
        }
    }

    public void draw(Batch batch, float delta) {
        this.accumulator += 1000f * delta;
        if(this.accumulator < 1f) {
            this.draw(batch);
        }
        else {
            int v3 = ((int)this.accumulator);
            this.accumulator -= ((float)v3);
            if(this.premultipliedAlpha) {
                batch.setBlendFunction(1, 771);
            }
            else if(this.additive) {
                batch.setBlendFunction(770, 1);
            }
            else {
                batch.setBlendFunction(770, 771);
            }

            Particle[] v9 = this.particles;
            boolean[] v1 = this.active;
            int v2 = this.activeCount;
            int v6 = 0;
            int v7 = v1.length;
            while(v6 < v7) {
                if(v1[v6]) {
                    Particle v8 = v9[v6];
                    if(this.updateParticle(v8, delta, v3)) {
                        v8.draw(batch);
                    }
                    else {
                        v1[v6] = false;
                        --v2;
                    }
                }

                ++v6;
            }

            this.activeCount = v2;
            if((this.cleansUpBlendFunction) && ((this.additive) || (this.premultipliedAlpha))) {
                batch.setBlendFunction(770, 771);
            }

            if(this.delayTimer >= this.delay) {
                goto label_73;
            }

            this.delayTimer += ((float)v3);
            return;
        label_73:
            if(this.firstUpdate) {
                this.firstUpdate = false;
                this.addParticle();
            }

            if(this.durationTimer < this.duration) {
                this.durationTimer += ((float)v3);
            }
            else if(!this.continuous) {
                return;
            }
            else if(!this.allowCompletion) {
                this.restart();
            }
            else {
                return;
            }

            this.emissionDelta += v3;
            float v4 = (((float)this.emission)) + (((float)this.emissionDiff)) * this.emissionValue.getScale(this.durationTimer / this.duration);
            if(v4 > 0f) {
                v4 /= 1000f;
                if((((float)this.emissionDelta)) >= v4) {
                    int v5 = Math.min(((int)((((float)this.emissionDelta)) / v4)), this.maxParticleCount - v2);
                    this.emissionDelta = ((int)((((float)this.emissionDelta)) - (((float)v5)) * v4));
                    this.emissionDelta = ((int)((((float)this.emissionDelta)) % v4));
                    this.addParticles(v5);
                }
            }

            if(v2 >= this.minParticleCount) {
                return;
            }

            this.addParticles(this.minParticleCount - v2);
        }
    }

    public void flipY() {
        this.angleValue.setHigh(-this.angleValue.getHighMin(), -this.angleValue.getHighMax());
        this.angleValue.setLow(-this.angleValue.getLowMin(), -this.angleValue.getLowMax());
        this.gravityValue.setHigh(-this.gravityValue.getHighMin(), -this.gravityValue.getHighMax());
        this.gravityValue.setLow(-this.gravityValue.getLowMin(), -this.gravityValue.getLowMax());
        this.windValue.setHigh(-this.windValue.getHighMin(), -this.windValue.getHighMax());
        this.windValue.setLow(-this.windValue.getLowMin(), -this.windValue.getLowMax());
        this.rotationValue.setHigh(-this.rotationValue.getHighMin(), -this.rotationValue.getHighMax());
        this.rotationValue.setLow(-this.rotationValue.getLowMin(), -this.rotationValue.getLowMax());
        this.yOffsetValue.setLow(-this.yOffsetValue.getLowMin(), -this.yOffsetValue.getLowMax());
    }

    public int getActiveCount() {
        return this.activeCount;
    }

    public ScaledNumericValue getAngle() {
        return this.angleValue;
    }

    public BoundingBox getBoundingBox() {
        if(this.bounds == null) {
            this.bounds = new BoundingBox();
        }

        Particle[] v4 = this.particles;
        boolean[] v0 = this.active;
        BoundingBox v1 = this.bounds;
        v1.inf();
        int v2 = 0;
        int v3 = v0.length;
        while(v2 < v3) {
            if(v0[v2]) {
                Rectangle v5 = v4[v2].getBoundingRectangle();
                v1.ext(v5.x, v5.y, 0f);
                v1.ext(v5.x + v5.width, v5.y + v5.height, 0f);
            }

            ++v2;
        }

        return v1;
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

    public ScaledNumericValue getGravity() {
        return this.gravityValue;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public ScaledNumericValue getLife() {
        return this.lifeValue;
    }

    public ScaledNumericValue getLifeOffset() {
        return this.lifeOffsetValue;
    }

    public int getMaxParticleCount() {
        return this.maxParticleCount;
    }

    public int getMinParticleCount() {
        return this.minParticleCount;
    }

    public String getName() {
        return this.name;
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

    public ScaledNumericValue getRotation() {
        return this.rotationValue;
    }

    public ScaledNumericValue getScale() {
        return this.scaleValue;
    }

    public ScaledNumericValue getSpawnHeight() {
        return this.spawnHeightValue;
    }

    public SpawnShapeValue getSpawnShape() {
        return this.spawnShapeValue;
    }

    public ScaledNumericValue getSpawnWidth() {
        return this.spawnWidthValue;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public GradientColorValue getTint() {
        return this.tintValue;
    }

    public ScaledNumericValue getTransparency() {
        return this.transparencyValue;
    }

    public ScaledNumericValue getVelocity() {
        return this.velocityValue;
    }

    public ScaledNumericValue getWind() {
        return this.windValue;
    }

    public float getX() {
        return this.x;
    }

    public RangedNumericValue getXOffsetValue() {
        return this.xOffsetValue;
    }

    public float getY() {
        return this.y;
    }

    public RangedNumericValue getYOffsetValue() {
        return this.yOffsetValue;
    }

    private void initialize() {
        this.durationValue.setAlwaysActive(true);
        this.emissionValue.setAlwaysActive(true);
        this.lifeValue.setAlwaysActive(true);
        this.scaleValue.setAlwaysActive(true);
        this.transparencyValue.setAlwaysActive(true);
        this.spawnShapeValue.setAlwaysActive(true);
        this.spawnWidthValue.setAlwaysActive(true);
        this.spawnHeightValue.setAlwaysActive(true);
    }

    public boolean isAdditive() {
        return this.additive;
    }

    public boolean isAligned() {
        return this.aligned;
    }

    public boolean isAttached() {
        return this.attached;
    }

    public boolean isBehind() {
        return this.behind;
    }

    public boolean isComplete() {
        boolean v0 = false;
        if(this.delayTimer >= this.delay && this.durationTimer >= this.duration && this.activeCount == 0) {
            v0 = true;
        }

        return v0;
    }

    public boolean isContinuous() {
        return this.continuous;
    }

    public boolean isPremultipliedAlpha() {
        return this.premultipliedAlpha;
    }

    public void load(BufferedReader reader) throws IOException {  // has try-catch handlers
        try {
            this.name = ParticleEmitter.readString(reader, "name");
            reader.readLine();
            this.delayValue.load(reader);
            reader.readLine();
            this.durationValue.load(reader);
            reader.readLine();
            this.setMinParticleCount(ParticleEmitter.readInt(reader, "minParticleCount"));
            this.setMaxParticleCount(ParticleEmitter.readInt(reader, "maxParticleCount"));
            reader.readLine();
            this.emissionValue.load(reader);
            reader.readLine();
            this.lifeValue.load(reader);
            reader.readLine();
            this.lifeOffsetValue.load(reader);
            reader.readLine();
            this.xOffsetValue.load(reader);
            reader.readLine();
            this.yOffsetValue.load(reader);
            reader.readLine();
            this.spawnShapeValue.load(reader);
            reader.readLine();
            this.spawnWidthValue.load(reader);
            reader.readLine();
            this.spawnHeightValue.load(reader);
            reader.readLine();
            this.scaleValue.load(reader);
            reader.readLine();
            this.velocityValue.load(reader);
            reader.readLine();
            this.angleValue.load(reader);
            reader.readLine();
            this.rotationValue.load(reader);
            reader.readLine();
            this.windValue.load(reader);
            reader.readLine();
            this.gravityValue.load(reader);
            reader.readLine();
            this.tintValue.load(reader);
            reader.readLine();
            this.transparencyValue.load(reader);
            reader.readLine();
            this.attached = ParticleEmitter.readBoolean(reader, "attached");
            this.continuous = ParticleEmitter.readBoolean(reader, "continuous");
            this.aligned = ParticleEmitter.readBoolean(reader, "aligned");
            this.additive = ParticleEmitter.readBoolean(reader, "additive");
            this.behind = ParticleEmitter.readBoolean(reader, "behind");
            String v1 = reader.readLine();
            if(v1.startsWith("premultipliedAlpha")) {
                this.premultipliedAlpha = ParticleEmitter.readBoolean(v1);
                reader.readLine();
            }

            this.setImagePath(reader.readLine());
            return;
        }
        catch(RuntimeException v0) {
            if(this.name == null) {
                throw v0;
            }

            throw new RuntimeException("Error parsing emitter: " + this.name, ((Throwable)v0));
        }
    }

    protected Particle newParticle(Sprite sprite) {
        return new Particle(sprite);
    }

    static boolean readBoolean(BufferedReader reader, String name) throws IOException {
        return Boolean.parseBoolean(ParticleEmitter.readString(reader, name));
    }

    static boolean readBoolean(String line) throws IOException {
        return Boolean.parseBoolean(ParticleEmitter.readString(line));
    }

    static float readFloat(BufferedReader reader, String name) throws IOException {
        return Float.parseFloat(ParticleEmitter.readString(reader, name));
    }

    static int readInt(BufferedReader reader, String name) throws IOException {
        return Integer.parseInt(ParticleEmitter.readString(reader, name));
    }

    static String readString(BufferedReader reader, String name) throws IOException {
        String v0 = reader.readLine();
        if(v0 == null) {
            throw new IOException("Missing value: " + name);
        }

        return ParticleEmitter.readString(v0);
    }

    static String readString(String line) throws IOException {
        return line.substring(line.indexOf(":") + 1).trim();
    }

    public void reset() {
        this.emissionDelta = 0;
        this.durationTimer = this.duration;
        boolean[] v0 = this.active;
        int v1 = 0;
        int v2 = v0.length;
        while(v1 < v2) {
            v0[v1] = false;
            ++v1;
        }

        this.activeCount = 0;
        this.start();
    }

    private void restart() {
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
        this.durationTimer -= this.duration;
        this.duration = this.durationValue.newLowValue();
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

        this.spawnWidth = this.spawnWidthValue.newLowValue();
        this.spawnWidthDiff = this.spawnWidthValue.newHighValue();
        if(!this.spawnWidthValue.isRelative()) {
            this.spawnWidthDiff -= this.spawnWidth;
        }

        this.spawnHeight = this.spawnHeightValue.newLowValue();
        this.spawnHeightDiff = this.spawnHeightValue.newHighValue();
        if(!this.spawnHeightValue.isRelative()) {
            this.spawnHeightDiff -= this.spawnHeight;
        }

        this.updateFlags = 0;
        if((this.angleValue.active) && this.angleValue.timeline.length > 1) {
            this.updateFlags |= 2;
        }

        if(this.velocityValue.active) {
            this.updateFlags |= 8;
        }

        if(this.scaleValue.timeline.length > 1) {
            this.updateFlags |= 1;
        }

        if((this.rotationValue.active) && this.rotationValue.timeline.length > 1) {
            this.updateFlags |= 4;
        }

        if(this.windValue.active) {
            this.updateFlags |= 16;
        }

        if(this.gravityValue.active) {
            this.updateFlags |= 32;
        }

        if(this.tintValue.timeline.length > 1) {
            this.updateFlags |= 64;
        }
    }

    public void save(Writer output) throws IOException {
        output.write(this.name + "\n");
        output.write("- Delay -\n");
        this.delayValue.save(output);
        output.write("- Duration - \n");
        this.durationValue.save(output);
        output.write("- Count - \n");
        output.write("min: " + this.minParticleCount + "\n");
        output.write("max: " + this.maxParticleCount + "\n");
        output.write("- Emission - \n");
        this.emissionValue.save(output);
        output.write("- Life - \n");
        this.lifeValue.save(output);
        output.write("- Life Offset - \n");
        this.lifeOffsetValue.save(output);
        output.write("- X Offset - \n");
        this.xOffsetValue.save(output);
        output.write("- Y Offset - \n");
        this.yOffsetValue.save(output);
        output.write("- Spawn Shape - \n");
        this.spawnShapeValue.save(output);
        output.write("- Spawn Width - \n");
        this.spawnWidthValue.save(output);
        output.write("- Spawn Height - \n");
        this.spawnHeightValue.save(output);
        output.write("- Scale - \n");
        this.scaleValue.save(output);
        output.write("- Velocity - \n");
        this.velocityValue.save(output);
        output.write("- Angle - \n");
        this.angleValue.save(output);
        output.write("- Rotation - \n");
        this.rotationValue.save(output);
        output.write("- Wind - \n");
        this.windValue.save(output);
        output.write("- Gravity - \n");
        this.gravityValue.save(output);
        output.write("- Tint - \n");
        this.tintValue.save(output);
        output.write("- Transparency - \n");
        this.transparencyValue.save(output);
        output.write("- Options - \n");
        output.write("attached: " + this.attached + "\n");
        output.write("continuous: " + this.continuous + "\n");
        output.write("aligned: " + this.aligned + "\n");
        output.write("additive: " + this.additive + "\n");
        output.write("behind: " + this.behind + "\n");
        output.write("premultipliedAlpha: " + this.premultipliedAlpha + "\n");
        output.write("- Image Path -\n");
        output.write(this.imagePath + "\n");
    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    public void setAligned(boolean aligned) {
        this.aligned = aligned;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public void setBehind(boolean behind) {
        this.behind = behind;
    }

    public void setCleansUpBlendFunction(boolean cleansUpBlendFunction) {
        this.cleansUpBlendFunction = cleansUpBlendFunction;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
        if(this.particles != null) {
            int v0 = 0;
            int v1 = this.particles.length;
            while(v0 < v1) {
                Particle v2 = this.particles[v0];
                if(v2 != null) {
                    v2.flip(flipX, flipY);
                }

                ++v0;
            }
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
        this.active = new boolean[maxParticleCount];
        this.activeCount = 0;
        this.particles = new Particle[maxParticleCount];
    }

    public void setMinParticleCount(int minParticleCount) {
        this.minParticleCount = minParticleCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(float x, float y) {
        if(this.attached) {
            float v3 = x - this.x;
            float v4 = y - this.y;
            boolean[] v0 = this.active;
            int v1 = 0;
            int v2 = v0.length;
            while(v1 < v2) {
                if(v0[v1]) {
                    this.particles[v1].translate(v3, v4);
                }

                ++v1;
            }
        }

        this.x = x;
        this.y = y;
    }

    public void setPremultipliedAlpha(boolean premultipliedAlpha) {
        this.premultipliedAlpha = premultipliedAlpha;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        if(sprite != null) {
            float v2 = sprite.getOriginX();
            float v3 = sprite.getOriginY();
            Texture v5 = sprite.getTexture();
            int v0 = 0;
            int v1 = this.particles.length;
            while(v0 < v1) {
                Particle v4 = this.particles[v0];
                if(v4 == null) {
                    return;
                }

                v4.setTexture(v5);
                v4.setOrigin(v2, v3);
                ++v0;
            }
        }
    }

    public void start() {
        this.firstUpdate = true;
        this.allowCompletion = false;
        this.restart();
    }

    public void update(float delta) {
        this.accumulator += 1000f * delta;
        if(this.accumulator >= 1f) {
            int v2 = ((int)this.accumulator);
            this.accumulator -= ((float)v2);
            if(this.delayTimer < this.delay) {
                this.delayTimer += ((float)v2);
            }
            else {
                int v3 = 0;
                if(this.firstUpdate) {
                    this.firstUpdate = false;
                    this.addParticle();
                }

                if(this.durationTimer < this.duration) {
                    this.durationTimer += ((float)v2);
                }
                else {
                    if((this.continuous) && !this.allowCompletion) {
                        this.restart();
                        goto label_51;
                    }

                    v3 = 1;
                }

            label_51:
                if(v3 != 0) {
                    goto label_22;
                }

                this.emissionDelta += v2;
                float v4 = (((float)this.emission)) + (((float)this.emissionDiff)) * this.emissionValue.getScale(this.durationTimer / this.duration);
                if(v4 > 0f) {
                    v4 /= 1000f;
                    if((((float)this.emissionDelta)) >= v4) {
                        int v5 = Math.min(((int)((((float)this.emissionDelta)) / v4)), this.maxParticleCount - this.activeCount);
                        this.emissionDelta = ((int)((((float)this.emissionDelta)) - (((float)v5)) * v4));
                        this.emissionDelta = ((int)((((float)this.emissionDelta)) % v4));
                        this.addParticles(v5);
                    }
                }

                if(this.activeCount >= this.minParticleCount) {
                    goto label_22;
                }

                this.addParticles(this.minParticleCount - this.activeCount);
            }

        label_22:
            boolean[] v0 = this.active;
            int v1 = this.activeCount;
            Particle[] v8 = this.particles;
            int v6 = 0;
            int v7 = v0.length;
            while(v6 < v7) {
                if((v0[v6]) && !this.updateParticle(v8[v6], delta, v2)) {
                    v0[v6] = false;
                    --v1;
                }

                ++v6;
            }

            this.activeCount = v1;
        }
    }

    private boolean updateParticle(Particle particle, float delta, int deltaMillis) {
        float v2;
        float[] v4;
        float v7;
        float v11;
        float v10;
        boolean v12;
        int v5 = particle.currentLife - deltaMillis;
        if(v5 <= 0) {
            v12 = false;
        }
        else {
            particle.currentLife = v5;
            float v6 = 1f - (((float)particle.currentLife)) / (((float)particle.life));
            int v8 = this.updateFlags;
            if((v8 & 1) != 0) {
                particle.setScale(particle.scale + particle.scaleDiff * this.scaleValue.getScale(v6));
            }

            if((v8 & 8) != 0) {
                float v9 = (particle.velocity + particle.velocityDiff * this.velocityValue.getScale(v6)) * delta;
                if((v8 & 2) != 0) {
                    float v3 = particle.angle + particle.angleDiff * this.angleValue.getScale(v6);
                    v10 = v9 * MathUtils.cosDeg(v3);
                    v11 = v9 * MathUtils.sinDeg(v3);
                    if((v8 & 4) != 0) {
                        v7 = particle.rotation + particle.rotationDiff * this.rotationValue.getScale(v6);
                        if(this.aligned) {
                            v7 += v3;
                        }

                        particle.setRotation(v7);
                    }
                }
                else {
                    v10 = v9 * particle.angleCos;
                    v11 = v9 * particle.angleSin;
                    if(!this.aligned && (v8 & 4) == 0) {
                        goto label_76;
                    }

                    v7 = particle.rotation + particle.rotationDiff * this.rotationValue.getScale(v6);
                    if(this.aligned) {
                        v7 += particle.angle;
                    }

                    particle.setRotation(v7);
                }

            label_76:
                if((v8 & 16) != 0) {
                    v10 += (particle.wind + particle.windDiff * this.windValue.getScale(v6)) * delta;
                }

                if((v8 & 32) != 0) {
                    v11 += (particle.gravity + particle.gravityDiff * this.gravityValue.getScale(v6)) * delta;
                }

                particle.translate(v10, v11);
            }
            else {
                if((v8 & 4) == 0) {
                    goto label_104;
                }

                particle.setRotation(particle.rotation + particle.rotationDiff * this.rotationValue.getScale(v6));
            }

        label_104:
            if((v8 & 64) != 0) {
                v4 = this.tintValue.getColor(v6);
            }
            else {
                v4 = particle.tint;
            }

            if(this.premultipliedAlpha) {
                if(this.additive) {
                    v2 = 0f;
                }
                else {
                    v2 = 1f;
                }

                float v1 = particle.transparency + particle.transparencyDiff * this.transparencyValue.getScale(v6);
                particle.setColor(v4[0] * v1, v4[1] * v1, v4[2] * v1, v1 * v2);
            }
            else {
                particle.setColor(v4[0], v4[1], v4[2], particle.transparency + particle.transparencyDiff * this.transparencyValue.getScale(v6));
            }

            v12 = true;
        }

        return v12;
    }
}

