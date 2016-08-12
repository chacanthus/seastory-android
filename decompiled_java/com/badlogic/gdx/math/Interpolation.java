// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

public abstract class Interpolation {
    public class Bounce extends BounceOut {
        public Bounce(int bounces) {
            super(bounces);
        }

        public Bounce(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public float apply(float a) {
            float v0;
            float v3 = 0.5f;
            float v2 = 1f;
            float v1 = 2f;
            if(a <= v3) {
                v0 = (v2 - this.out(v2 - a * v1)) / v1;
            }
            else {
                v0 = this.out(a * v1 - v2) / v1 + v3;
            }

            return v0;
        }

        private float out(float a) {
            float v1;
            float v3 = 2f;
            float v0 = a + this.widths[0] / v3;
            if(v0 < this.widths[0]) {
                v1 = v0 / (this.widths[0] / v3) - 1f;
            }
            else {
                v1 = super.apply(a);
            }

            return v1;
        }
    }

    public class BounceIn extends BounceOut {
        public BounceIn(int bounces) {
            super(bounces);
        }

        public BounceIn(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public float apply(float a) {
            return 1f - super.apply(1f - a);
        }
    }

    public class BounceOut extends Interpolation {
        final float[] heights;
        final float[] widths;

        public BounceOut(int bounces) {
            float v6 = 0.1f;
            int v5 = 3;
            int v3 = 2;
            this();
            if(bounces >= v3 && bounces <= 5) {
                this.widths = new float[bounces];
                this.heights = new float[bounces];
                this.heights[0] = 1f;
                switch(bounces) {
                    case 2: {
                        this.widths[0] = 0.6f;
                        this.widths[1] = 0.4f;
                        this.heights[1] = 0.33f;
                        break;
                    }
                    case 3: {
                        this.widths[0] = 0.4f;
                        this.widths[1] = 0.4f;
                        this.widths[v3] = 0.2f;
                        this.heights[1] = 0.33f;
                        this.heights[v3] = v6;
                        break;
                    }
                    case 4: {
                        this.widths[0] = 0.34f;
                        this.widths[1] = 0.34f;
                        this.widths[v3] = 0.2f;
                        this.widths[v5] = 0.15f;
                        this.heights[1] = 0.26f;
                        this.heights[v3] = 0.11f;
                        this.heights[v5] = 0.03f;
                        break;
                    }
                    case 5: {
                        this.widths[0] = 0.3f;
                        this.widths[1] = 0.3f;
                        this.widths[v3] = 0.2f;
                        this.widths[v5] = v6;
                        this.widths[4] = v6;
                        this.heights[1] = 0.45f;
                        this.heights[v3] = 0.3f;
                        this.heights[v5] = 0.15f;
                        this.heights[4] = 0.06f;
                        break;
                    }
                }

                this.widths[0] *= 2f;
                return;
            }

            throw new IllegalArgumentException("bounces cannot be < 2 or > 5: " + bounces);
        }

        public BounceOut(float[] widths, float[] heights) {
            this();
            if(widths.length != heights.length) {
                throw new IllegalArgumentException("Must be the same number of widths and heights.");
            }

            this.widths = widths;
            this.heights = heights;
        }

        public float apply(float a) {
            a += this.widths[0] / 2f;
            float v3 = 0f;
            float v0 = 0f;
            int v1 = 0;
            int v2 = this.widths.length;
            while(v1 < v2) {
                v3 = this.widths[v1];
                if(a <= v3) {
                    v0 = this.heights[v1];
                }
                else {
                    a -= v3;
                    ++v1;
                    continue;
                }

                break;
            }

            a /= v3;
            float v4 = 4f / v3 * v0 * a;
            return 1f - (v4 - v4 * a) * v3;
        }
    }

    public class Elastic extends Interpolation {
        final float bounces;
        final float power;
        final float scale;
        final float value;

        public Elastic(float value, float power, int bounces, float scale) {
            int v0;
            this();
            this.value = value;
            this.power = power;
            this.scale = scale;
            float v1 = 3.141593f * (((float)bounces));
            if(bounces % 2 == 0) {
                v0 = 1;
            }
            else {
                v0 = -1;
            }

            this.bounces = (((float)v0)) * v1;
        }

        public float apply(float a) {
            float v0;
            float v5 = 2f;
            float v4 = 1f;
            if(a <= 0.5f) {
                a *= v5;
                v0 = (((float)Math.pow(((double)this.value), ((double)(this.power * (a - v4)))))) * MathUtils.sin(this.bounces * a) * this.scale / v5;
            }
            else {
                a = (v4 - a) * v5;
                v0 = v4 - (((float)Math.pow(((double)this.value), ((double)(this.power * (a - v4)))))) * MathUtils.sin(this.bounces * a) * this.scale / v5;
            }

            return v0;
        }
    }

    public class ElasticIn extends Elastic {
        public ElasticIn(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            float v0 = 1f;
            if((((double)a)) < 0.99) {
                v0 = (((float)Math.pow(((double)this.value), ((double)((a - v0) * this.power))))) * MathUtils.sin(this.bounces * a) * this.scale;
            }

            return v0;
        }
    }

    public class ElasticOut extends Elastic {
        public ElasticOut(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            --a;
            return 1f - (((float)Math.pow(((double)this.value), ((double)(this.power * (a - 1f)))))) * MathUtils.sin(this.bounces * a) * this.scale;
        }
    }

    public class Exp extends Interpolation {
        final float min;
        final float power;
        final float scale;
        final float value;

        public Exp(float value, float power) {
            this();
            this.value = value;
            this.power = power;
            this.min = ((float)Math.pow(((double)value), ((double)(-power))));
            this.scale = 1f / (1f - this.min);
        }

        public float apply(float a) {
            float v0;
            float v5 = 1f;
            float v4 = 2f;
            if(a <= 0.5f) {
                v0 = ((((float)Math.pow(((double)this.value), ((double)(this.power * (a * v4 - v5)))))) - this.min) * this.scale / v4;
            }
            else {
                v0 = (v4 - ((((float)Math.pow(((double)this.value), ((double)(-this.power * (a * v4 - v5)))))) - this.min) * this.scale) / v4;
            }

            return v0;
        }
    }

    public class ExpIn extends Exp {
        public ExpIn(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return ((((float)Math.pow(((double)this.value), ((double)(this.power * (a - 1f)))))) - this.min) * this.scale;
        }
    }

    public class ExpOut extends Exp {
        public ExpOut(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return 1f - ((((float)Math.pow(((double)this.value), ((double)(-this.power * a))))) - this.min) * this.scale;
        }
    }

    public class Pow extends Interpolation {
        final int power;

        public Pow(int power) {
            this();
            this.power = power;
        }

        public float apply(float a) {
            int v0_1;
            float v0;
            float v5 = 1f;
            float v4 = 2f;
            if(a <= 0.5f) {
                v0 = (((float)Math.pow(((double)(a * v4)), ((double)this.power)))) / v4;
            }
            else {
                float v1 = ((float)Math.pow(((double)((a - v5) * v4)), ((double)this.power)));
                if(this.power % 2 == 0) {
                    v0_1 = -2;
                }
                else {
                    v0_1 = 2;
                }

                v0 = v1 / (((float)v0_1)) + v5;
            }

            return v0;
        }
    }

    public class PowIn extends Pow {
        public PowIn(int power) {
            super(power);
        }

        public float apply(float a) {
            return ((float)Math.pow(((double)a), ((double)this.power)));
        }
    }

    public class PowOut extends Pow {
        public PowOut(int power) {
            super(power);
        }

        public float apply(float a) {
            int v0;
            float v4 = 1f;
            float v1 = ((float)Math.pow(((double)(a - v4)), ((double)this.power)));
            if(this.power % 2 == 0) {
                v0 = -1;
            }
            else {
                v0 = 1;
            }

            return (((float)v0)) * v1 + v4;
        }
    }

    public class Swing extends Interpolation {
        private final float scale;

        public Swing(float scale) {
            this();
            this.scale = 2f * scale;
        }

        public float apply(float a) {
            float v0;
            float v4 = 2f;
            float v3 = 1f;
            if(a <= 0.5f) {
                a *= v4;
                v0 = a * a * ((this.scale + v3) * a - this.scale) / v4;
            }
            else {
                a = (a - v3) * v4;
                v0 = a * a * ((this.scale + v3) * a + this.scale) / v4 + v3;
            }

            return v0;
        }
    }

    public class SwingIn extends Interpolation {
        private final float scale;

        public SwingIn(float scale) {
            this();
            this.scale = scale;
        }

        public float apply(float a) {
            return a * a * ((this.scale + 1f) * a - this.scale);
        }
    }

    public class SwingOut extends Interpolation {
        private final float scale;

        public SwingOut(float scale) {
            this();
            this.scale = scale;
        }

        public float apply(float a) {
            --a;
            return a * a * ((this.scale + 1f) * a + this.scale) + 1f;
        }
    }

    public static final Bounce bounce;
    public static final BounceIn bounceIn;
    public static final BounceOut bounceOut;
    public static final Interpolation circle;
    public static final Interpolation circleIn;
    public static final Interpolation circleOut;
    public static final Elastic elastic;
    public static final ElasticIn elasticIn;
    public static final ElasticOut elasticOut;
    public static final Exp exp10;
    public static final ExpIn exp10In;
    public static final ExpOut exp10Out;
    public static final Exp exp5;
    public static final ExpIn exp5In;
    public static final ExpOut exp5Out;
    public static final Interpolation fade;
    public static final Interpolation linear;
    public static final Pow pow2;
    public static final PowIn pow2In;
    public static final PowOut pow2Out;
    public static final Pow pow3;
    public static final PowIn pow3In;
    public static final PowOut pow3Out;
    public static final Pow pow4;
    public static final PowIn pow4In;
    public static final PowOut pow4Out;
    public static final Pow pow5;
    public static final PowIn pow5In;
    public static final PowOut pow5Out;
    public static final Interpolation sine;
    public static final Interpolation sineIn;
    public static final Interpolation sineOut;
    public static final Swing swing;
    public static final SwingIn swingIn;
    public static final SwingOut swingOut;

    static  {
        Interpolation.linear = new Interpolation() {
            public float apply(float a) {
                return a;
            }
        };
        Interpolation.fade = new Interpolation() {
            public float apply(float a) {
                return MathUtils.clamp(a * a * a * ((6f * a - 15f) * a + 10f), 0f, 1f);
            }
        };
        Interpolation.pow2 = new Pow(2);
        Interpolation.pow2In = new PowIn(2);
        Interpolation.pow2Out = new PowOut(2);
        Interpolation.pow3 = new Pow(3);
        Interpolation.pow3In = new PowIn(3);
        Interpolation.pow3Out = new PowOut(3);
        Interpolation.pow4 = new Pow(4);
        Interpolation.pow4In = new PowIn(4);
        Interpolation.pow4Out = new PowOut(4);
        Interpolation.pow5 = new Pow(5);
        Interpolation.pow5In = new PowIn(5);
        Interpolation.pow5Out = new PowOut(5);
        Interpolation.sine = new Interpolation() {
            public float apply(float a) {
                return (1f - MathUtils.cos(3.141593f * a)) / 2f;
            }
        };
        Interpolation.sineIn = new Interpolation() {
            public float apply(float a) {
                return 1f - MathUtils.cos(3.141593f * a / 2f);
            }
        };
        Interpolation.sineOut = new Interpolation() {
            public float apply(float a) {
                return MathUtils.sin(3.141593f * a / 2f);
            }
        };
        Interpolation.exp10 = new Exp(2f, 10f);
        Interpolation.exp10In = new ExpIn(2f, 10f);
        Interpolation.exp10Out = new ExpOut(2f, 10f);
        Interpolation.exp5 = new Exp(2f, 5f);
        Interpolation.exp5In = new ExpIn(2f, 5f);
        Interpolation.exp5Out = new ExpOut(2f, 5f);
        Interpolation.circle = new Interpolation() {
            public float apply(float a) {
                float v0;
                float v3 = 2f;
                float v2 = 1f;
                if(a <= 0.5f) {
                    a *= v3;
                    v0 = (v2 - (((float)Math.sqrt(((double)(v2 - a * a)))))) / v3;
                }
                else {
                    a = (a - v2) * v3;
                    v0 = ((((float)Math.sqrt(((double)(v2 - a * a))))) + v2) / v3;
                }

                return v0;
            }
        };
        Interpolation.circleIn = new Interpolation() {
            public float apply(float a) {
                return 1f - (((float)Math.sqrt(((double)(1f - a * a)))));
            }
        };
        Interpolation.circleOut = new Interpolation() {
            public float apply(float a) {
                --a;
                return ((float)Math.sqrt(((double)(1f - a * a))));
            }
        };
        Interpolation.elastic = new Elastic(2f, 10f, 7, 1f);
        Interpolation.elasticIn = new ElasticIn(2f, 10f, 6, 1f);
        Interpolation.elasticOut = new ElasticOut(2f, 10f, 7, 1f);
        Interpolation.swing = new Swing(1.5f);
        Interpolation.swingIn = new SwingIn(2f);
        Interpolation.swingOut = new SwingOut(2f);
        Interpolation.bounce = new Bounce(4);
        Interpolation.bounceIn = new BounceIn(4);
        Interpolation.bounceOut = new BounceOut(4);
    }

    public Interpolation() {
        super();
    }

    public abstract float apply(float arg0);

    public float apply(float start, float end, float a) {
        return (end - start) * this.apply(a) + start;
    }
}

