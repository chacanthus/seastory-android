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
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Bezier implements Path {
    public Array points;
    private Vector tmp;
    private Vector tmp2;
    private Vector tmp3;

    public Bezier() {
        super();
        this.points = new Array();
    }

    public Bezier(Array arg2, int offset, int length) {
        super();
        this.points = new Array();
        this.set(arg2, offset, length);
    }

    public Bezier(Vector[] arg2) {
        super();
        this.points = new Array();
        this.set(arg2);
    }

    public Bezier(Vector[] arg2, int offset, int length) {
        super();
        this.points = new Array();
        this.set(arg2, offset, length);
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

    public float approximate(Vector arg12) {
        Object v4 = this.points.get(0);
        Object v5 = this.points.get(this.points.size - 1);
        float v1 = ((Vector)v4).dst2(((Vector)v5));
        float v2 = arg12.dst2(((Vector)v5));
        float v3 = arg12.dst2(((Vector)v4));
        float v0 = ((float)Math.sqrt(((double)v1)));
        return MathUtils.clamp((v0 - (v2 + v1 - v3) / (2f * v0)) / v0, 0f, 1f);
    }

    public float approximate(Object x0) {
        return this.approximate(((Vector)x0));
    }

    public static Vector cubic(Vector arg7, float t, Vector arg9, Vector arg10, Vector arg11, Vector arg12, Vector arg13) {
        float v0 = 1f - t;
        float v1 = v0 * v0;
        float v2 = t * t;
        return arg7.set(arg9).scl(v1 * v0).add(arg13.set(arg10).scl(3f * v1 * t)).add(arg13.set(arg11).scl(3f * v0 * v2)).add(arg13.set(arg12).scl(v2 * t));
    }

    public static Vector cubic_derivative(Vector arg8, float t, Vector arg10, Vector arg11, Vector arg12, Vector arg13, Vector arg14) {
        float v0 = 1f - t;
        return arg8.set(arg11).sub(arg10).scl(v0 * v0 * 3f).add(arg14.set(arg12).sub(arg11).scl(v0 * t * 6f)).add(arg14.set(arg13).sub(arg12).scl(t * t * 3f));
    }

    public Vector derivativeAt(Vector arg9, float t) {
        int v5 = 3;
        int v4 = 2;
        int v7 = this.points.size;
        if(v7 == v4) {
            Bezier.linear_derivative(arg9, t, this.points.get(0), this.points.get(1), this.tmp);
        }
        else if(v7 == v5) {
            Bezier.quadratic_derivative(arg9, t, this.points.get(0), this.points.get(1), this.points.get(v4), this.tmp);
        }
        else if(v7 == 4) {
            Bezier.cubic_derivative(arg9, t, this.points.get(0), this.points.get(1), this.points.get(v4), this.points.get(v5), this.tmp);
        }

        return arg9;
    }

    public Object derivativeAt(Object x0, float x1) {
        return this.derivativeAt(((Vector)x0), x1);
    }

    public static Vector linear(Vector arg2, float t, Vector arg4, Vector arg5, Vector arg6) {
        return arg2.set(arg4).scl(1f - t).add(arg6.set(arg5).scl(t));
    }

    public static Vector linear_derivative(Vector arg1, float t, Vector arg3, Vector arg4, Vector arg5) {
        return arg1.set(arg4).sub(arg3);
    }

    public float locate(Vector arg2) {
        return this.approximate(arg2);
    }

    public float locate(Object x0) {
        return this.locate(((Vector)x0));
    }

    public static Vector quadratic(Vector arg4, float t, Vector arg6, Vector arg7, Vector arg8, Vector arg9) {
        float v0 = 1f - t;
        return arg4.set(arg6).scl(v0 * v0).add(arg9.set(arg7).scl(2f * v0 * t)).add(arg9.set(arg8).scl(t * t));
    }

    public static Vector quadratic_derivative(Vector arg4, float t, Vector arg6, Vector arg7, Vector arg8, Vector arg9) {
        return arg4.set(arg7).sub(arg6).scl(2f).scl(1f - t).add(arg9.set(arg8).sub(arg7).scl(t).scl(2f));
    }

    public Bezier set(Array arg3, int offset, int length) {
        if(length >= 2 && length <= 4) {
            if(this.tmp == null) {
                this.tmp = arg3.get(0).cpy();
            }

            this.points.clear();
            this.points.addAll(arg3, offset, length);
            return this;
        }

        throw new GdxRuntimeException("Only first, second and third degree Bezier curves are supported.");
    }

    public Bezier set(Vector[] arg3) {
        return this.set(arg3, 0, arg3.length);
    }

    public Bezier set(Vector[] arg3, int offset, int length) {
        if(length >= 2 && length <= 4) {
            if(this.tmp == null) {
                this.tmp = arg3[0].cpy();
            }

            if(this.tmp2 == null) {
                this.tmp2 = arg3[0].cpy();
            }

            if(this.tmp3 == null) {
                this.tmp3 = arg3[0].cpy();
            }

            this.points.clear();
            this.points.addAll(((Object[])arg3), offset, length);
            return this;
        }

        throw new GdxRuntimeException("Only first, second and third degree Bezier curves are supported.");
    }

    public Vector valueAt(Vector arg9, float t) {
        int v5 = 3;
        int v4 = 2;
        int v7 = this.points.size;
        if(v7 == v4) {
            Bezier.linear(arg9, t, this.points.get(0), this.points.get(1), this.tmp);
        }
        else if(v7 == v5) {
            Bezier.quadratic(arg9, t, this.points.get(0), this.points.get(1), this.points.get(v4), this.tmp);
        }
        else if(v7 == 4) {
            Bezier.cubic(arg9, t, this.points.get(0), this.points.get(1), this.points.get(v4), this.points.get(v5), this.tmp);
        }

        return arg9;
    }

    public Object valueAt(Object x0, float x1) {
        return this.valueAt(((Vector)x0), x1);
    }
}

