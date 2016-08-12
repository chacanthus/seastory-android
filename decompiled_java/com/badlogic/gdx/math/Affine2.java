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

import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.Serializable;

public final class Affine2 implements Serializable {
    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    private static final long serialVersionUID = 1524569123485049187L;

    public Affine2() {
        super();
        this.m00 = 1f;
        this.m01 = 0f;
        this.m02 = 0f;
        this.m10 = 0f;
        this.m11 = 1f;
        this.m12 = 0f;
    }

    public Affine2(Affine2 other) {
        super();
        this.m00 = 1f;
        this.m01 = 0f;
        this.m02 = 0f;
        this.m10 = 0f;
        this.m11 = 1f;
        this.m12 = 0f;
        this.set(other);
    }

    public void applyTo(Vector2 point) {
        float v0 = point.x;
        float v1 = point.y;
        point.x = this.m00 * v0 + this.m01 * v1 + this.m02;
        point.y = this.m10 * v0 + this.m11 * v1 + this.m12;
    }

    public float det() {
        return this.m00 * this.m11 - this.m01 * this.m10;
    }

    public Vector2 getTranslation(Vector2 position) {
        position.x = this.m02;
        position.y = this.m12;
        return position;
    }

    public Affine2 idt() {
        this.m00 = 1f;
        this.m01 = 0f;
        this.m02 = 0f;
        this.m10 = 0f;
        this.m11 = 1f;
        this.m12 = 0f;
        return this;
    }

    public Affine2 inv() {
        float v0 = this.det();
        if(v0 == 0f) {
            throw new GdxRuntimeException("Can\'t invert a singular affine matrix");
        }

        float v1 = 1f / v0;
        float v2 = this.m11;
        float v3 = -this.m01;
        float v4 = this.m01 * this.m12 - this.m11 * this.m02;
        float v5 = -this.m10;
        float v6 = this.m00;
        float v7 = this.m10 * this.m02 - this.m00 * this.m12;
        this.m00 = v1 * v2;
        this.m01 = v1 * v3;
        this.m02 = v1 * v4;
        this.m10 = v1 * v5;
        this.m11 = v1 * v6;
        this.m12 = v1 * v7;
        return this;
    }

    public boolean isIdt() {
        boolean v0;
        float v2 = 1f;
        if(this.m00 != v2 || this.m02 != 0f || this.m12 != 0f || this.m11 != v2 || this.m01 != 0f || this.m10 != 0f) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isTranslation() {
        boolean v0;
        float v2 = 1f;
        if(this.m00 != v2 || this.m11 != v2 || this.m01 != 0f || this.m10 != 0f) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public Affine2 mul(Affine2 other) {
        float v0 = this.m00 * other.m00 + this.m01 * other.m10;
        float v1 = this.m00 * other.m01 + this.m01 * other.m11;
        float v2 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02;
        float v3 = this.m10 * other.m00 + this.m11 * other.m10;
        float v4 = this.m10 * other.m01 + this.m11 * other.m11;
        float v5 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12;
        this.m00 = v0;
        this.m01 = v1;
        this.m02 = v2;
        this.m10 = v3;
        this.m11 = v4;
        this.m12 = v5;
        return this;
    }

    public Affine2 preMul(Affine2 other) {
        float v0 = other.m00 * this.m00 + other.m01 * this.m10;
        float v1 = other.m00 * this.m01 + other.m01 * this.m11;
        float v2 = other.m00 * this.m02 + other.m01 * this.m12 + other.m02;
        float v3 = other.m10 * this.m00 + other.m11 * this.m10;
        float v4 = other.m10 * this.m01 + other.m11 * this.m11;
        float v5 = other.m10 * this.m02 + other.m11 * this.m12 + other.m12;
        this.m00 = v0;
        this.m01 = v1;
        this.m02 = v2;
        this.m10 = v3;
        this.m11 = v4;
        this.m12 = v5;
        return this;
    }

    public Affine2 preRotate(float degrees) {
        if(degrees != 0f) {
            float v0 = MathUtils.cosDeg(degrees);
            float v1 = MathUtils.sinDeg(degrees);
            float v2 = this.m00 * v0 - this.m10 * v1;
            float v3 = this.m01 * v0 - this.m11 * v1;
            float v4 = this.m02 * v0 - this.m12 * v1;
            float v5 = this.m00 * v1 + this.m10 * v0;
            float v6 = this.m01 * v1 + this.m11 * v0;
            float v7 = this.m02 * v1 + this.m12 * v0;
            this.m00 = v2;
            this.m01 = v3;
            this.m02 = v4;
            this.m10 = v5;
            this.m11 = v6;
            this.m12 = v7;
        }

        return this;
    }

    public Affine2 preRotateRad(float radians) {
        if(radians != 0f) {
            float v0 = MathUtils.cos(radians);
            float v1 = MathUtils.sin(radians);
            float v2 = this.m00 * v0 - this.m10 * v1;
            float v3 = this.m01 * v0 - this.m11 * v1;
            float v4 = this.m02 * v0 - this.m12 * v1;
            float v5 = this.m00 * v1 + this.m10 * v0;
            float v6 = this.m01 * v1 + this.m11 * v0;
            float v7 = this.m02 * v1 + this.m12 * v0;
            this.m00 = v2;
            this.m01 = v3;
            this.m02 = v4;
            this.m10 = v5;
            this.m11 = v6;
            this.m12 = v7;
        }

        return this;
    }

    public Affine2 preScale(float scaleX, float scaleY) {
        this.m00 *= scaleX;
        this.m01 *= scaleX;
        this.m02 *= scaleX;
        this.m10 *= scaleY;
        this.m11 *= scaleY;
        this.m12 *= scaleY;
        return this;
    }

    public Affine2 preScale(Vector2 scale) {
        return this.preScale(scale.x, scale.y);
    }

    public Affine2 preShear(float shearX, float shearY) {
        float v0 = this.m00 + this.m10 * shearX;
        float v1 = this.m01 + this.m11 * shearX;
        float v2 = this.m02 + this.m12 * shearX;
        float v3 = this.m10 + this.m00 * shearY;
        float v4 = this.m11 + this.m01 * shearY;
        float v5 = this.m12 + this.m02 * shearY;
        this.m00 = v0;
        this.m01 = v1;
        this.m02 = v2;
        this.m10 = v3;
        this.m11 = v4;
        this.m12 = v5;
        return this;
    }

    public Affine2 preShear(Vector2 shear) {
        return this.preShear(shear.x, shear.y);
    }

    public Affine2 preTranslate(float x, float y) {
        this.m02 += x;
        this.m12 += y;
        return this;
    }

    public Affine2 preTranslate(Vector2 trn) {
        return this.preTranslate(trn.x, trn.y);
    }

    public Affine2 rotate(float degrees) {
        if(degrees != 0f) {
            float v0 = MathUtils.cosDeg(degrees);
            float v1 = MathUtils.sinDeg(degrees);
            float v2 = this.m00 * v0 + this.m01 * v1;
            float v3 = this.m00 * -v1 + this.m01 * v0;
            float v4 = this.m10 * v0 + this.m11 * v1;
            float v5 = this.m10 * -v1 + this.m11 * v0;
            this.m00 = v2;
            this.m01 = v3;
            this.m10 = v4;
            this.m11 = v5;
        }

        return this;
    }

    public Affine2 rotateRad(float radians) {
        if(radians != 0f) {
            float v0 = MathUtils.cos(radians);
            float v1 = MathUtils.sin(radians);
            float v2 = this.m00 * v0 + this.m01 * v1;
            float v3 = this.m00 * -v1 + this.m01 * v0;
            float v4 = this.m10 * v0 + this.m11 * v1;
            float v5 = this.m10 * -v1 + this.m11 * v0;
            this.m00 = v2;
            this.m01 = v3;
            this.m10 = v4;
            this.m11 = v5;
        }

        return this;
    }

    public Affine2 scale(float scaleX, float scaleY) {
        this.m00 *= scaleX;
        this.m01 *= scaleY;
        this.m10 *= scaleX;
        this.m11 *= scaleY;
        return this;
    }

    public Affine2 scale(Vector2 scale) {
        return this.scale(scale.x, scale.y);
    }

    public Affine2 set(Affine2 other) {
        this.m00 = other.m00;
        this.m01 = other.m01;
        this.m02 = other.m02;
        this.m10 = other.m10;
        this.m11 = other.m11;
        this.m12 = other.m12;
        return this;
    }

    public Affine2 set(Matrix3 matrix) {
        float[] v0 = matrix.val;
        this.m00 = v0[0];
        this.m01 = v0[3];
        this.m02 = v0[6];
        this.m10 = v0[1];
        this.m11 = v0[4];
        this.m12 = v0[7];
        return this;
    }

    public Affine2 set(Matrix4 matrix) {
        float[] v0 = matrix.val;
        this.m00 = v0[0];
        this.m01 = v0[4];
        this.m02 = v0[12];
        this.m10 = v0[1];
        this.m11 = v0[5];
        this.m12 = v0[13];
        return this;
    }

    public Affine2 setToProduct(Affine2 l, Affine2 r) {
        this.m00 = l.m00 * r.m00 + l.m01 * r.m10;
        this.m01 = l.m00 * r.m01 + l.m01 * r.m11;
        this.m02 = l.m00 * r.m02 + l.m01 * r.m12 + l.m02;
        this.m10 = l.m10 * r.m00 + l.m11 * r.m10;
        this.m11 = l.m10 * r.m01 + l.m11 * r.m11;
        this.m12 = l.m10 * r.m02 + l.m11 * r.m12 + l.m12;
        return this;
    }

    public Affine2 setToRotation(float degrees) {
        float v0 = MathUtils.cosDeg(degrees);
        float v1 = MathUtils.sinDeg(degrees);
        this.m00 = v0;
        this.m01 = -v1;
        this.m02 = 0f;
        this.m10 = v1;
        this.m11 = v0;
        this.m12 = 0f;
        return this;
    }

    public Affine2 setToRotation(float cos, float sin) {
        this.m00 = cos;
        this.m01 = -sin;
        this.m02 = 0f;
        this.m10 = sin;
        this.m11 = cos;
        this.m12 = 0f;
        return this;
    }

    public Affine2 setToRotationRad(float radians) {
        float v0 = MathUtils.cos(radians);
        float v1 = MathUtils.sin(radians);
        this.m00 = v0;
        this.m01 = -v1;
        this.m02 = 0f;
        this.m10 = v1;
        this.m11 = v0;
        this.m12 = 0f;
        return this;
    }

    public Affine2 setToScaling(float scaleX, float scaleY) {
        this.m00 = scaleX;
        this.m01 = 0f;
        this.m02 = 0f;
        this.m10 = 0f;
        this.m11 = scaleY;
        this.m12 = 0f;
        return this;
    }

    public Affine2 setToScaling(Vector2 scale) {
        return this.setToScaling(scale.x, scale.y);
    }

    public Affine2 setToShearing(float shearX, float shearY) {
        this.m00 = 1f;
        this.m01 = shearX;
        this.m02 = 0f;
        this.m10 = shearY;
        this.m11 = 1f;
        this.m12 = 0f;
        return this;
    }

    public Affine2 setToShearing(Vector2 shear) {
        return this.setToShearing(shear.x, shear.y);
    }

    public Affine2 setToTranslation(float x, float y) {
        this.m00 = 1f;
        this.m01 = 0f;
        this.m02 = x;
        this.m10 = 0f;
        this.m11 = 1f;
        this.m12 = y;
        return this;
    }

    public Affine2 setToTranslation(Vector2 trn) {
        return this.setToTranslation(trn.x, trn.y);
    }

    public Affine2 setToTrnRotRadScl(float x, float y, float radians, float scaleX, float scaleY) {
        this.m02 = x;
        this.m12 = y;
        if(radians == 0f) {
            this.m00 = scaleX;
            this.m01 = 0f;
            this.m10 = 0f;
            this.m11 = scaleY;
        }
        else {
            float v1 = MathUtils.sin(radians);
            float v0 = MathUtils.cos(radians);
            this.m00 = v0 * scaleX;
            this.m01 = -v1 * scaleY;
            this.m10 = v1 * scaleX;
            this.m11 = v0 * scaleY;
        }

        return this;
    }

    public Affine2 setToTrnRotRadScl(Vector2 trn, float radians, Vector2 scale) {
        return this.setToTrnRotRadScl(trn.x, trn.y, radians, scale.x, scale.y);
    }

    public Affine2 setToTrnRotScl(float x, float y, float degrees, float scaleX, float scaleY) {
        this.m02 = x;
        this.m12 = y;
        if(degrees == 0f) {
            this.m00 = scaleX;
            this.m01 = 0f;
            this.m10 = 0f;
            this.m11 = scaleY;
        }
        else {
            float v1 = MathUtils.sinDeg(degrees);
            float v0 = MathUtils.cosDeg(degrees);
            this.m00 = v0 * scaleX;
            this.m01 = -v1 * scaleY;
            this.m10 = v1 * scaleX;
            this.m11 = v0 * scaleY;
        }

        return this;
    }

    public Affine2 setToTrnRotScl(Vector2 trn, float degrees, Vector2 scale) {
        return this.setToTrnRotScl(trn.x, trn.y, degrees, scale.x, scale.y);
    }

    public Affine2 setToTrnScl(float x, float y, float scaleX, float scaleY) {
        this.m00 = scaleX;
        this.m01 = 0f;
        this.m02 = x;
        this.m10 = 0f;
        this.m11 = scaleY;
        this.m12 = y;
        return this;
    }

    public Affine2 setToTrnScl(Vector2 trn, Vector2 scale) {
        return this.setToTrnScl(trn.x, trn.y, scale.x, scale.y);
    }

    public Affine2 shear(float shearX, float shearY) {
        float v0 = this.m00 + this.m01 * shearY;
        float v1 = this.m01 + this.m00 * shearX;
        this.m00 = v0;
        this.m01 = v1;
        v0 = this.m10 + this.m11 * shearY;
        v1 = this.m11 + this.m10 * shearX;
        this.m10 = v0;
        this.m11 = v1;
        return this;
    }

    public Affine2 shear(Vector2 shear) {
        return this.shear(shear.x, shear.y);
    }

    public String toString() {
        return "[" + this.m00 + "|" + this.m01 + "|" + this.m02 + "]\n[" + this.m10 + "|" + this.m11 + "|" + this.m12 + "]\n[0.0|0.0|0.1]";
    }

    public Affine2 translate(float x, float y) {
        this.m02 += this.m00 * x + this.m01 * y;
        this.m12 += this.m10 * x + this.m11 * y;
        return this;
    }

    public Affine2 translate(Vector2 trn) {
        return this.translate(trn.x, trn.y);
    }
}

