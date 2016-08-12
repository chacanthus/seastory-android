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

import com.badlogic.gdx.utils.Array;

public class BSpline implements Path {
    public boolean continuous;
    public Vector[] controlPoints;
    private static final float d6 = 0.166667f;
    public int degree;
    public Array knots;
    public int spanCount;
    private Vector tmp;
    private Vector tmp2;
    private Vector tmp3;

    public BSpline() {
        super();
    }

    public BSpline(Vector[] arg1, int degree, boolean continuous) {
        super();
        this.set(arg1, degree, continuous);
    }

    public float approxLength(int samples) {
        float v1 = 0f;
        int v0;
        for(v0 = 0; v0 < samples; ++v0) {
            this.tmp2.set(this.tmp3);
            this.valueAt(this.tmp3, (((float)v0)) / ((((float)samples)) - 1f));
            if(v0 > 0) {
                v1 += this.tmp2.dst(this.tmp3);
            }
        }

        return v1;
    }

    public float approximate(Vector arg2) {
        return this.approximate(arg2, this.nearest(arg2));
    }

    public float approximate(Vector arg21, int near) {
        Vector v8;
        Object v7;
        Object v6;
        int v17;
        int v11 = near;
        Object v12 = this.knots.get(v11);
        Array v18 = this.knots;
        if(v11 > 0) {
            v17 = v11 - 1;
        }
        else {
            v17 = this.spanCount - 1;
        }

        Object v14 = v18.get(v17);
        Object v13 = this.knots.get((v11 + 1) % this.spanCount);
        if(arg21.dst2(((Vector)v13)) < arg21.dst2(((Vector)v14))) {
            v6 = v12;
            v7 = v13;
            v8 = arg21;
        }
        else {
            v6 = v14;
            v7 = v12;
            v8 = arg21;
            if(v11 > 0) {
                --v11;
            }
            else {
                v11 = this.spanCount - 1;
            }
        }

        float v3 = ((Vector)v6).dst2(((Vector)v7));
        float v4 = v8.dst2(((Vector)v7));
        float v5 = v8.dst2(((Vector)v6));
        float v2 = ((float)Math.sqrt(((double)v3)));
        return ((((float)v11)) + MathUtils.clamp((v2 - (v4 + v3 - v5) / (2f * v2)) / v2, 0f, 1f)) / (((float)this.spanCount));
    }

    public float approximate(Vector arg2, int start, int count) {
        return this.approximate(arg2, this.nearest(arg2, start, count));
    }

    public float approximate(Object x0) {
        return this.approximate(((Vector)x0));
    }

    public static Vector calculate(Vector arg8, float t, Vector[] arg10, int degree, boolean continuous, Vector arg13) {
        int v1;
        int v7;
        if(continuous) {
            v7 = arg10.length;
        }
        else {
            v7 = arg10.length - degree;
        }

        float v2 = t * (((float)v7));
        if(t >= 1f) {
            v1 = v7 - 1;
        }
        else {
            v1 = ((int)v2);
        }

        return BSpline.calculate(arg8, v1, v2 - (((float)v1)), arg10, degree, continuous, arg13);
    }

    public static Vector calculate(Vector arg6, int i, float u, Vector[] arg9, int degree, boolean continuous, Vector arg12) {
        switch(degree) {
            case 3: {
                arg6 = BSpline.cubic(arg6, i, u, arg9, continuous, arg12);
                break;
            }
        }

        return arg6;
    }

    public static Vector cubic(Vector arg10, int i, float u, Vector[] arg13, boolean continuous, Vector arg15) {
        float v9 = 1f;
        float v8 = 3f;
        float v7 = 0.166667f;
        int v1 = arg13.length;
        float v0 = v9 - u;
        float v2 = u * u;
        float v3 = v2 * u;
        arg10.set(arg13[i]).scl((v8 * v3 - 6f * v2 + 4f) * v7);
        if((continuous) || i > 0) {
            arg10.add(arg15.set(arg13[(v1 + i - 1) % v1]).scl(v0 * v0 * v0 * v7));
        }

        if((continuous) || i < v1 - 1) {
            arg10.add(arg15.set(arg13[(i + 1) % v1]).scl((-3f * v3 + v8 * v2 + v8 * u + v9) * v7));
        }

        if((continuous) || i < v1 - 2) {
            arg10.add(arg15.set(arg13[(i + 2) % v1]).scl(v3 * v7));
        }

        return arg10;
    }

    public static Vector cubic(Vector arg7, float t, Vector[] arg9, boolean continuous, Vector arg11) {
        int v1;
        int v6;
        if(continuous) {
            v6 = arg9.length;
        }
        else {
            v6 = arg9.length - 3;
        }

        float v2 = t * (((float)v6));
        if(t >= 1f) {
            v1 = v6 - 1;
        }
        else {
            v1 = ((int)v2);
        }

        return BSpline.cubic(arg7, v1, v2 - (((float)v1)), arg9, continuous, arg11);
    }

    public static Vector cubic_derivative(Vector arg7, float t, Vector[] arg9, boolean continuous, Vector arg11) {
        int v1;
        int v6;
        if(continuous) {
            v6 = arg9.length;
        }
        else {
            v6 = arg9.length - 3;
        }

        float v2 = t * (((float)v6));
        if(t >= 1f) {
            v1 = v6 - 1;
        }
        else {
            v1 = ((int)v2);
        }

        return BSpline.cubic(arg7, v1, v2 - (((float)v1)), arg9, continuous, arg11);
    }

    public static Vector cubic_derivative(Vector arg9, int i, float u, Vector[] arg12, boolean continuous, Vector arg14) {
        float v8 = 1f;
        float v7 = 0.5f;
        int v1 = arg12.length;
        float v2 = u * u;
        arg9.set(arg12[i]).scl(1.5f * v2 - 2f * u);
        if((continuous) || i > 0) {
            arg9.add(arg14.set(arg12[(v1 + i - 1) % v1]).scl((v8 - u) * v7 * (v8 - u)));
        }

        if((continuous) || i < v1 - 1) {
            arg9.add(arg14.set(arg12[(i + 1) % v1]).scl(-1.5f * v2 + u + v7));
        }

        if((continuous) || i < v1 - 2) {
            arg9.add(arg14.set(arg12[(i + 2) % v1]).scl(v7 * v2));
        }

        return arg9;
    }

    public static Vector derivative(Vector arg8, float t, Vector[] arg10, int degree, boolean continuous, Vector arg13) {
        int v1;
        int v7;
        if(continuous) {
            v7 = arg10.length;
        }
        else {
            v7 = arg10.length - degree;
        }

        float v2 = t * (((float)v7));
        if(t >= 1f) {
            v1 = v7 - 1;
        }
        else {
            v1 = ((int)v2);
        }

        return BSpline.derivative(arg8, v1, v2 - (((float)v1)), arg10, degree, continuous, arg13);
    }

    public static Vector derivative(Vector arg6, int i, float u, Vector[] arg9, int degree, boolean continuous, Vector arg12) {
        switch(degree) {
            case 3: {
                arg6 = BSpline.cubic_derivative(arg6, i, u, arg9, continuous, arg12);
                break;
            }
        }

        return arg6;
    }

    public Vector derivativeAt(Vector arg5, float t) {
        int v0;
        int v1 = this.spanCount;
        float v2 = t * (((float)v1));
        if(t >= 1f) {
            v0 = v1 - 1;
        }
        else {
            v0 = ((int)v2);
        }

        return this.derivativeAt(arg5, v0, v2 - (((float)v0)));
    }

    public Vector derivativeAt(Vector arg8, int span, float u) {
        int v1;
        if(this.continuous) {
            v1 = span;
        }
        else {
            v1 = span + (((int)((((float)this.degree)) * 0.5f)));
        }

        return BSpline.derivative(arg8, v1, u, this.controlPoints, this.degree, this.continuous, this.tmp);
    }

    public Object derivativeAt(Object x0, float x1) {
        return this.derivativeAt(((Vector)x0), x1);
    }

    public float locate(Vector arg2) {
        return this.approximate(arg2);
    }

    public float locate(Object x0) {
        return this.locate(((Vector)x0));
    }

    public int nearest(Vector arg3) {
        return this.nearest(arg3, 0, this.spanCount);
    }

    public int nearest(Vector arg8, int start, int count) {
        while(start < 0) {
            start += this.spanCount;
        }

        int v4 = start % this.spanCount;
        float v1 = arg8.dst2(this.knots.get(v4));
        int v2;
        for(v2 = 1; v2 < count; ++v2) {
            int v3 = (start + v2) % this.spanCount;
            float v0 = arg8.dst2(this.knots.get(v3));
            if(v0 < v1) {
                v1 = v0;
                v4 = v3;
            }
        }

        return v4;
    }

    public BSpline set(Vector[] arg11, int degree, boolean continuous) {
        int v1;
        int v0;
        if(this.tmp == null) {
            this.tmp = arg11[0].cpy();
        }

        if(this.tmp2 == null) {
            this.tmp2 = arg11[0].cpy();
        }

        if(this.tmp3 == null) {
            this.tmp3 = arg11[0].cpy();
        }

        this.controlPoints = arg11;
        this.degree = degree;
        this.continuous = continuous;
        if(continuous) {
            v0 = arg11.length;
        }
        else {
            v0 = arg11.length - degree;
        }

        this.spanCount = v0;
        if(this.knots == null) {
            this.knots = new Array(this.spanCount);
        }
        else {
            this.knots.clear();
            this.knots.ensureCapacity(this.spanCount);
        }

        int v7;
        for(v7 = 0; v7 < this.spanCount; ++v7) {
            Array v8 = this.knots;
            Vector v0_1 = arg11[0].cpy();
            if(continuous) {
                v1 = v7;
            }
            else {
                v1 = ((int)((((float)v7)) + 0.5f * (((float)degree))));
            }

            v8.add(BSpline.calculate(v0_1, v1, 0f, arg11, degree, continuous, this.tmp));
        }

        return this;
    }

    public Vector valueAt(Vector arg5, float t) {
        int v0;
        int v1 = this.spanCount;
        float v2 = t * (((float)v1));
        if(t >= 1f) {
            v0 = v1 - 1;
        }
        else {
            v0 = ((int)v2);
        }

        return this.valueAt(arg5, v0, v2 - (((float)v0)));
    }

    public Vector valueAt(Vector arg8, int span, float u) {
        int v1;
        if(this.continuous) {
            v1 = span;
        }
        else {
            v1 = span + (((int)((((float)this.degree)) * 0.5f)));
        }

        return BSpline.calculate(arg8, v1, u, this.controlPoints, this.degree, this.continuous, this.tmp);
    }

    public Object valueAt(Object x0, float x1) {
        return this.valueAt(((Vector)x0), x1);
    }
}

