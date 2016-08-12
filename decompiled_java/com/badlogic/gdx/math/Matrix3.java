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

public class Matrix3 implements Serializable {
    public static final int M00 = 0;
    public static final int M01 = 3;
    public static final int M02 = 6;
    public static final int M10 = 1;
    public static final int M11 = 4;
    public static final int M12 = 7;
    public static final int M20 = 2;
    public static final int M21 = 5;
    public static final int M22 = 8;
    private static final long serialVersionUID = 7907569533774959788L;
    private float[] tmp;
    public float[] val;

    public Matrix3() {
        super();
        this.val = new float[9];
        this.tmp = new float[9];
        this.idt();
    }

    public Matrix3(Matrix3 matrix) {
        super();
        this.val = new float[9];
        this.tmp = new float[9];
        this.set(matrix);
    }

    public Matrix3(float[] values) {
        super();
        this.val = new float[9];
        this.tmp = new float[9];
        this.set(values);
    }

    public float det() {
        return this.val[0] * this.val[4] * this.val[8] + this.val[3] * this.val[7] * this.val[2] + this.val[6] * this.val[1] * this.val[5] - this.val[0] * this.val[7] * this.val[5] - this.val[3] * this.val[1] * this.val[8] - this.val[6] * this.val[4] * this.val[2];
    }

    public float getRotation() {
        return 57.295776f * (((float)Math.atan2(((double)this.val[1]), ((double)this.val[0]))));
    }

    public float getRotationRad() {
        return ((float)Math.atan2(((double)this.val[1]), ((double)this.val[0])));
    }

    public Vector2 getScale(Vector2 scale) {
        float[] v0 = this.val;
        scale.x = ((float)Math.sqrt(((double)(v0[0] * v0[0] + v0[3] * v0[3]))));
        scale.y = ((float)Math.sqrt(((double)(v0[1] * v0[1] + v0[4] * v0[4]))));
        return scale;
    }

    public Vector2 getTranslation(Vector2 position) {
        position.x = this.val[6];
        position.y = this.val[7];
        return position;
    }

    public float[] getValues() {
        return this.val;
    }

    public Matrix3 idt() {
        float[] v0 = this.val;
        v0[0] = 1f;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = 1f;
        v0[5] = 0f;
        v0[6] = 0f;
        v0[7] = 0f;
        v0[8] = 1f;
        return this;
    }

    public Matrix3 inv() {
        int v12 = 4;
        int v11 = 3;
        int v10 = 2;
        float v0 = this.det();
        if(v0 == 0f) {
            throw new GdxRuntimeException("Can\'t invert a singular matrix");
        }

        float v1 = 1f / v0;
        float[] v2 = this.tmp;
        float[] v3 = this.val;
        v2[0] = v3[v12] * v3[8] - v3[5] * v3[7];
        v2[1] = v3[v10] * v3[7] - v3[1] * v3[8];
        v2[v10] = v3[1] * v3[5] - v3[v10] * v3[v12];
        v2[v11] = v3[5] * v3[6] - v3[v11] * v3[8];
        v2[v12] = v3[0] * v3[8] - v3[v10] * v3[6];
        v2[5] = v3[v10] * v3[v11] - v3[0] * v3[5];
        v2[6] = v3[v11] * v3[7] - v3[v12] * v3[6];
        v2[7] = v3[1] * v3[6] - v3[0] * v3[7];
        v2[8] = v3[0] * v3[v12] - v3[1] * v3[v11];
        v3[0] = v2[0] * v1;
        v3[1] = v2[1] * v1;
        v3[v10] = v2[v10] * v1;
        v3[v11] = v2[v11] * v1;
        v3[v12] = v2[v12] * v1;
        v3[5] = v2[5] * v1;
        v3[6] = v2[6] * v1;
        v3[7] = v2[7] * v1;
        v3[8] = v2[8] * v1;
        return this;
    }

    private static void mul(float[] mata, float[] matb) {
        float v0 = mata[0] * matb[0] + mata[3] * matb[1] + mata[6] * matb[2];
        float v1 = mata[0] * matb[3] + mata[3] * matb[4] + mata[6] * matb[5];
        float v2 = mata[0] * matb[6] + mata[3] * matb[7] + mata[6] * matb[8];
        float v3 = mata[1] * matb[0] + mata[4] * matb[1] + mata[7] * matb[2];
        float v4 = mata[1] * matb[3] + mata[4] * matb[4] + mata[7] * matb[5];
        float v5 = mata[1] * matb[6] + mata[4] * matb[7] + mata[7] * matb[8];
        float v6 = mata[2] * matb[0] + mata[5] * matb[1] + mata[8] * matb[2];
        float v7 = mata[2] * matb[3] + mata[5] * matb[4] + mata[8] * matb[5];
        float v8 = mata[2] * matb[6] + mata[5] * matb[7] + mata[8] * matb[8];
        mata[0] = v0;
        mata[1] = v3;
        mata[2] = v6;
        mata[3] = v1;
        mata[4] = v4;
        mata[5] = v7;
        mata[6] = v2;
        mata[7] = v5;
        mata[8] = v8;
    }

    public Matrix3 mul(Matrix3 m) {
        float[] v9 = this.val;
        float v0 = v9[0] * m.val[0] + v9[3] * m.val[1] + v9[6] * m.val[2];
        float v1 = v9[0] * m.val[3] + v9[3] * m.val[4] + v9[6] * m.val[5];
        float v2 = v9[0] * m.val[6] + v9[3] * m.val[7] + v9[6] * m.val[8];
        float v3 = v9[1] * m.val[0] + v9[4] * m.val[1] + v9[7] * m.val[2];
        float v4 = v9[1] * m.val[3] + v9[4] * m.val[4] + v9[7] * m.val[5];
        float v5 = v9[1] * m.val[6] + v9[4] * m.val[7] + v9[7] * m.val[8];
        float v6 = v9[2] * m.val[0] + v9[5] * m.val[1] + v9[8] * m.val[2];
        float v7 = v9[2] * m.val[3] + v9[5] * m.val[4] + v9[8] * m.val[5];
        float v8 = v9[2] * m.val[6] + v9[5] * m.val[7] + v9[8] * m.val[8];
        v9[0] = v0;
        v9[1] = v3;
        v9[2] = v6;
        v9[3] = v1;
        v9[4] = v4;
        v9[5] = v7;
        v9[6] = v2;
        v9[7] = v5;
        v9[8] = v8;
        return this;
    }

    public Matrix3 mulLeft(Matrix3 m) {
        float[] v9 = this.val;
        float v0 = m.val[0] * v9[0] + m.val[3] * v9[1] + m.val[6] * v9[2];
        float v1 = m.val[0] * v9[3] + m.val[3] * v9[4] + m.val[6] * v9[5];
        float v2 = m.val[0] * v9[6] + m.val[3] * v9[7] + m.val[6] * v9[8];
        float v3 = m.val[1] * v9[0] + m.val[4] * v9[1] + m.val[7] * v9[2];
        float v4 = m.val[1] * v9[3] + m.val[4] * v9[4] + m.val[7] * v9[5];
        float v5 = m.val[1] * v9[6] + m.val[4] * v9[7] + m.val[7] * v9[8];
        float v6 = m.val[2] * v9[0] + m.val[5] * v9[1] + m.val[8] * v9[2];
        float v7 = m.val[2] * v9[3] + m.val[5] * v9[4] + m.val[8] * v9[5];
        float v8 = m.val[2] * v9[6] + m.val[5] * v9[7] + m.val[8] * v9[8];
        v9[0] = v0;
        v9[1] = v3;
        v9[2] = v6;
        v9[3] = v1;
        v9[4] = v4;
        v9[5] = v7;
        v9[6] = v2;
        v9[7] = v5;
        v9[8] = v8;
        return this;
    }

    public Matrix3 rotate(float degrees) {
        return this.rotateRad(0.017453f * degrees);
    }

    public Matrix3 rotateRad(float radians) {
        if(radians != 0f) {
            float v0 = ((float)Math.cos(((double)radians)));
            float v1 = ((float)Math.sin(((double)radians)));
            float[] v2 = this.tmp;
            v2[0] = v0;
            v2[1] = v1;
            v2[2] = 0f;
            v2[3] = -v1;
            v2[4] = v0;
            v2[5] = 0f;
            v2[6] = 0f;
            v2[7] = 0f;
            v2[8] = 1f;
            Matrix3.mul(this.val, v2);
        }

        return this;
    }

    public Matrix3 scale(float scaleX, float scaleY) {
        float[] v0 = this.tmp;
        v0[0] = scaleX;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = scaleY;
        v0[5] = 0f;
        v0[6] = 0f;
        v0[7] = 0f;
        v0[8] = 1f;
        Matrix3.mul(this.val, v0);
        return this;
    }

    public Matrix3 scale(Vector2 scale) {
        float[] v0 = this.tmp;
        v0[0] = scale.x;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = scale.y;
        v0[5] = 0f;
        v0[6] = 0f;
        v0[7] = 0f;
        v0[8] = 1f;
        Matrix3.mul(this.val, v0);
        return this;
    }

    public Matrix3 scl(float scale) {
        this.val[0] *= scale;
        this.val[4] *= scale;
        return this;
    }

    public Matrix3 scl(Vector2 scale) {
        this.val[0] *= scale.x;
        this.val[4] *= scale.y;
        return this;
    }

    public Matrix3 scl(Vector3 scale) {
        this.val[0] *= scale.x;
        this.val[4] *= scale.y;
        return this;
    }

    public Matrix3 set(Matrix4 mat) {
        float[] v0 = this.val;
        v0[0] = mat.val[0];
        v0[1] = mat.val[1];
        v0[2] = mat.val[2];
        v0[3] = mat.val[4];
        v0[4] = mat.val[5];
        v0[5] = mat.val[6];
        v0[6] = mat.val[8];
        v0[7] = mat.val[9];
        v0[8] = mat.val[10];
        return this;
    }

    public Matrix3 set(Matrix3 mat) {
        System.arraycopy(mat.val, 0, this.val, 0, this.val.length);
        return this;
    }

    public Matrix3 set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, this.val.length);
        return this;
    }

    public Matrix3 set(Affine2 affine) {
        float[] v0 = this.val;
        v0[0] = affine.m00;
        v0[1] = affine.m10;
        v0[2] = 0f;
        v0[3] = affine.m01;
        v0[4] = affine.m11;
        v0[5] = 0f;
        v0[6] = affine.m02;
        v0[7] = affine.m12;
        v0[8] = 1f;
        return this;
    }

    public Matrix3 setToRotation(Vector3 axis, float cos, float sin) {
        float[] v1 = this.val;
        float v0 = 1f - cos;
        v1[0] = axis.x * v0 * axis.x + cos;
        v1[1] = axis.x * v0 * axis.y - axis.z * sin;
        v1[2] = axis.z * v0 * axis.x + axis.y * sin;
        v1[3] = axis.x * v0 * axis.y + axis.z * sin;
        v1[4] = axis.y * v0 * axis.y + cos;
        v1[5] = axis.y * v0 * axis.z - axis.x * sin;
        v1[6] = axis.z * v0 * axis.x - axis.y * sin;
        v1[7] = axis.y * v0 * axis.z + axis.x * sin;
        v1[8] = axis.z * v0 * axis.z + cos;
        return this;
    }

    public Matrix3 setToRotation(float degrees) {
        return this.setToRotationRad(0.017453f * degrees);
    }

    public Matrix3 setToRotation(Vector3 axis, float degrees) {
        return this.setToRotation(axis, MathUtils.cosDeg(degrees), MathUtils.sinDeg(degrees));
    }

    public Matrix3 setToRotationRad(float radians) {
        float v0 = ((float)Math.cos(((double)radians)));
        float v1 = ((float)Math.sin(((double)radians)));
        float[] v2 = this.val;
        v2[0] = v0;
        v2[1] = v1;
        v2[2] = 0f;
        v2[3] = -v1;
        v2[4] = v0;
        v2[5] = 0f;
        v2[6] = 0f;
        v2[7] = 0f;
        v2[8] = 1f;
        return this;
    }

    public Matrix3 setToScaling(float scaleX, float scaleY) {
        float[] v0 = this.val;
        v0[0] = scaleX;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = scaleY;
        v0[5] = 0f;
        v0[6] = 0f;
        v0[7] = 0f;
        v0[8] = 1f;
        return this;
    }

    public Matrix3 setToScaling(Vector2 scale) {
        float[] v0 = this.val;
        v0[0] = scale.x;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = scale.y;
        v0[5] = 0f;
        v0[6] = 0f;
        v0[7] = 0f;
        v0[8] = 1f;
        return this;
    }

    public Matrix3 setToTranslation(float x, float y) {
        float[] v0 = this.val;
        v0[0] = 1f;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = 1f;
        v0[5] = 0f;
        v0[6] = x;
        v0[7] = y;
        v0[8] = 1f;
        return this;
    }

    public Matrix3 setToTranslation(Vector2 translation) {
        float[] v0 = this.val;
        v0[0] = 1f;
        v0[1] = 0f;
        v0[2] = 0f;
        v0[3] = 0f;
        v0[4] = 1f;
        v0[5] = 0f;
        v0[6] = translation.x;
        v0[7] = translation.y;
        v0[8] = 1f;
        return this;
    }

    public String toString() {
        return "[" + this.val[0] + "|" + this.val[3] + "|" + this.val[6] + "]\n" + "[" + this.val[1] + "|" + this.val[4] + "|" + this.val[7] + "]\n" + "[" + this.val[2] + "|" + this.val[5] + "|" + this.val[8] + "]";
    }

    public Matrix3 translate(float x, float y) {
        float[] v0 = this.val;
        this.tmp[0] = 1f;
        this.tmp[1] = 0f;
        this.tmp[2] = 0f;
        this.tmp[3] = 0f;
        this.tmp[4] = 1f;
        this.tmp[5] = 0f;
        this.tmp[6] = x;
        this.tmp[7] = y;
        this.tmp[8] = 1f;
        Matrix3.mul(v0, this.tmp);
        return this;
    }

    public Matrix3 translate(Vector2 translation) {
        float[] v0 = this.val;
        this.tmp[0] = 1f;
        this.tmp[1] = 0f;
        this.tmp[2] = 0f;
        this.tmp[3] = 0f;
        this.tmp[4] = 1f;
        this.tmp[5] = 0f;
        this.tmp[6] = translation.x;
        this.tmp[7] = translation.y;
        this.tmp[8] = 1f;
        Matrix3.mul(v0, this.tmp);
        return this;
    }

    public Matrix3 transpose() {
        float[] v6 = this.val;
        float v0 = v6[1];
        float v1 = v6[2];
        float v2 = v6[3];
        float v3 = v6[5];
        float v4 = v6[6];
        float v5 = v6[7];
        v6[3] = v0;
        v6[6] = v1;
        v6[1] = v2;
        v6[7] = v3;
        v6[2] = v4;
        v6[5] = v5;
        return this;
    }

    public Matrix3 trn(float x, float y) {
        this.val[6] += x;
        this.val[7] += y;
        return this;
    }

    public Matrix3 trn(Vector2 vector) {
        this.val[6] += vector.x;
        this.val[7] += vector.y;
        return this;
    }

    public Matrix3 trn(Vector3 vector) {
        this.val[6] += vector.x;
        this.val[7] += vector.y;
        return this;
    }
}

