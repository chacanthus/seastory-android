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

import java.io.Serializable;

public class Matrix4 implements Serializable {
    public static final int M00 = 0;
    public static final int M01 = 4;
    public static final int M02 = 8;
    public static final int M03 = 12;
    public static final int M10 = 1;
    public static final int M11 = 5;
    public static final int M12 = 9;
    public static final int M13 = 13;
    public static final int M20 = 2;
    public static final int M21 = 6;
    public static final int M22 = 10;
    public static final int M23 = 14;
    public static final int M30 = 3;
    public static final int M31 = 7;
    public static final int M32 = 11;
    public static final int M33 = 15;
    static final Vector3 l_vex = null;
    static final Vector3 l_vey = null;
    static final Vector3 l_vez = null;
    static Quaternion quat = null;
    static Quaternion quat2 = null;
    static final Vector3 right = null;
    private static final long serialVersionUID = -2717655254359579617L;
    private static final float[] tmp;
    static final Vector3 tmpForward;
    static final Matrix4 tmpMat;
    static final Vector3 tmpUp;
    static final Vector3 tmpVec;
    public final float[] val;

    static  {
        Matrix4.tmp = new float[16];
        Matrix4.quat = new Quaternion();
        Matrix4.quat2 = new Quaternion();
        Matrix4.l_vez = new Vector3();
        Matrix4.l_vex = new Vector3();
        Matrix4.l_vey = new Vector3();
        Matrix4.tmpVec = new Vector3();
        Matrix4.tmpMat = new Matrix4();
        Matrix4.right = new Vector3();
        Matrix4.tmpForward = new Vector3();
        Matrix4.tmpUp = new Vector3();
    }

    public Matrix4() {
        super();
        this.val = new float[16];
        this.val[0] = 1f;
        this.val[5] = 1f;
        this.val[10] = 1f;
        this.val[15] = 1f;
    }

    public Matrix4(Matrix4 matrix) {
        super();
        this.val = new float[16];
        this.set(matrix);
    }

    public Matrix4(Quaternion quaternion) {
        super();
        this.val = new float[16];
        this.set(quaternion);
    }

    public Matrix4(Vector3 position, Quaternion rotation, Vector3 scale) {
        super();
        this.val = new float[16];
        this.set(position, rotation, scale);
    }

    public Matrix4(float[] values) {
        super();
        this.val = new float[16];
        this.set(values);
    }

    public Matrix4 avg(Matrix4 other, float w) {
        this.getScale(Matrix4.tmpVec);
        other.getScale(Matrix4.tmpForward);
        this.getRotation(Matrix4.quat);
        other.getRotation(Matrix4.quat2);
        this.getTranslation(Matrix4.tmpUp);
        other.getTranslation(Matrix4.right);
        this.setToScaling(Matrix4.tmpVec.scl(w).add(Matrix4.tmpForward.scl(1f - w)));
        this.rotate(Matrix4.quat.slerp(Matrix4.quat2, 1f - w));
        this.setTranslation(Matrix4.tmpUp.scl(w).add(Matrix4.right.scl(1f - w)));
        return this;
    }

    public Matrix4 avg(Matrix4[] t) {
        float v1 = 1f / (((float)t.length));
        Matrix4.tmpVec.set(t[0].getScale(Matrix4.tmpUp).scl(v1));
        Matrix4.quat.set(t[0].getRotation(Matrix4.quat2).exp(v1));
        Matrix4.tmpForward.set(t[0].getTranslation(Matrix4.tmpUp).scl(v1));
        int v0;
        for(v0 = 1; v0 < t.length; ++v0) {
            Matrix4.tmpVec.add(t[v0].getScale(Matrix4.tmpUp).scl(v1));
            Matrix4.quat.mul(t[v0].getRotation(Matrix4.quat2).exp(v1));
            Matrix4.tmpForward.add(t[v0].getTranslation(Matrix4.tmpUp).scl(v1));
        }

        Matrix4.quat.nor();
        this.setToScaling(Matrix4.tmpVec);
        this.rotate(Matrix4.quat);
        this.setTranslation(Matrix4.tmpForward);
        return this;
    }

    public Matrix4 avg(Matrix4[] t, float[] w) {
        Matrix4.tmpVec.set(t[0].getScale(Matrix4.tmpUp).scl(w[0]));
        Matrix4.quat.set(t[0].getRotation(Matrix4.quat2).exp(w[0]));
        Matrix4.tmpForward.set(t[0].getTranslation(Matrix4.tmpUp).scl(w[0]));
        int v0;
        for(v0 = 1; v0 < t.length; ++v0) {
            Matrix4.tmpVec.add(t[v0].getScale(Matrix4.tmpUp).scl(w[v0]));
            Matrix4.quat.mul(t[v0].getRotation(Matrix4.quat2).exp(w[v0]));
            Matrix4.tmpForward.add(t[v0].getTranslation(Matrix4.tmpUp).scl(w[v0]));
        }

        Matrix4.quat.nor();
        this.setToScaling(Matrix4.tmpVec);
        this.rotate(Matrix4.quat);
        this.setTranslation(Matrix4.tmpForward);
        return this;
    }

    public Matrix4 cpy() {
        return new Matrix4(this);
    }

    public static native float det(float[] arg0) {
    }

    public float det() {
        return this.val[3] * this.val[6] * this.val[9] * this.val[12] - this.val[2] * this.val[7] * this.val[9] * this.val[12] - this.val[3] * this.val[5] * this.val[10] * this.val[12] + this.val[1] * this.val[7] * this.val[10] * this.val[12] + this.val[2] * this.val[5] * this.val[11] * this.val[12] - this.val[1] * this.val[6] * this.val[11] * this.val[12] - this.val[3] * this.val[6] * this.val[8] * this.val[13] + this.val[2] * this.val[7] * this.val[8] * this.val[13] + this.val[3] * this.val[4] * this.val[10] * this.val[13] - this.val[0] * this.val[7] * this.val[10] * this.val[13] - this.val[2] * this.val[4] * this.val[11] * this.val[13] + this.val[0] * this.val[6] * this.val[11] * this.val[13] + this.val[3] * this.val[5] * this.val[8] * this.val[14] - this.val[1] * this.val[7] * this.val[8] * this.val[14] - this.val[3] * this.val[4] * this.val[9] * this.val[14] + this.val[0] * this.val[7] * this.val[9] * this.val[14] + this.val[1] * this.val[4] * this.val[11] * this.val[14] - this.val[0] * this.val[5] * this.val[11] * this.val[14] - this.val[2] * this.val[5] * this.val[8] * this.val[15] + this.val[1] * this.val[6] * this.val[8] * this.val[15] + this.val[2] * this.val[4] * this.val[9] * this.val[15] - this.val[0] * this.val[6] * this.val[9] * this.val[15] - this.val[1] * this.val[4] * this.val[10] * this.val[15] + this.val[0] * this.val[5] * this.val[10] * this.val[15];
    }

    public float det3x3() {
        return this.val[0] * this.val[5] * this.val[10] + this.val[4] * this.val[9] * this.val[2] + this.val[8] * this.val[1] * this.val[6] - this.val[0] * this.val[9] * this.val[6] - this.val[4] * this.val[1] * this.val[10] - this.val[8] * this.val[5] * this.val[2];
    }

    public void extract4x3Matrix(float[] dst) {
        dst[0] = this.val[0];
        dst[1] = this.val[1];
        dst[2] = this.val[2];
        dst[3] = this.val[4];
        dst[4] = this.val[5];
        dst[5] = this.val[6];
        dst[6] = this.val[8];
        dst[7] = this.val[9];
        dst[8] = this.val[10];
        dst[9] = this.val[12];
        dst[10] = this.val[13];
        dst[11] = this.val[14];
    }

    public Quaternion getRotation(Quaternion rotation) {
        return rotation.setFromMatrix(this);
    }

    public Quaternion getRotation(Quaternion rotation, boolean normalizeAxes) {
        return rotation.setFromMatrix(normalizeAxes, this);
    }

    public Vector3 getScale(Vector3 scale) {
        return scale.set(this.getScaleX(), this.getScaleY(), this.getScaleZ());
    }

    public float getScaleX() {
        float v0;
        if(!MathUtils.isZero(this.val[4]) || !MathUtils.isZero(this.val[8])) {
            v0 = ((float)Math.sqrt(((double)this.getScaleXSquared())));
        }
        else {
            v0 = Math.abs(this.val[0]);
        }

        return v0;
    }

    public float getScaleXSquared() {
        return this.val[0] * this.val[0] + this.val[4] * this.val[4] + this.val[8] * this.val[8];
    }

    public float getScaleY() {
        float v0;
        if(!MathUtils.isZero(this.val[1]) || !MathUtils.isZero(this.val[9])) {
            v0 = ((float)Math.sqrt(((double)this.getScaleYSquared())));
        }
        else {
            v0 = Math.abs(this.val[5]);
        }

        return v0;
    }

    public float getScaleYSquared() {
        return this.val[1] * this.val[1] + this.val[5] * this.val[5] + this.val[9] * this.val[9];
    }

    public float getScaleZ() {
        float v0;
        if(!MathUtils.isZero(this.val[2]) || !MathUtils.isZero(this.val[6])) {
            v0 = ((float)Math.sqrt(((double)this.getScaleZSquared())));
        }
        else {
            v0 = Math.abs(this.val[10]);
        }

        return v0;
    }

    public float getScaleZSquared() {
        return this.val[2] * this.val[2] + this.val[6] * this.val[6] + this.val[10] * this.val[10];
    }

    public Vector3 getTranslation(Vector3 position) {
        position.x = this.val[12];
        position.y = this.val[13];
        position.z = this.val[14];
        return position;
    }

    public float[] getValues() {
        return this.val;
    }

    public Matrix4 idt() {
        this.val[0] = 1f;
        this.val[4] = 0f;
        this.val[8] = 0f;
        this.val[12] = 0f;
        this.val[1] = 0f;
        this.val[5] = 1f;
        this.val[9] = 0f;
        this.val[13] = 0f;
        this.val[2] = 0f;
        this.val[6] = 0f;
        this.val[10] = 1f;
        this.val[14] = 0f;
        this.val[3] = 0f;
        this.val[7] = 0f;
        this.val[11] = 0f;
        this.val[15] = 1f;
        return this;
    }

    public static native boolean inv(float[] arg0) {
    }

    public Matrix4 inv() {
        int v12 = 4;
        int v11 = 3;
        int v10 = 2;
        float v1 = this.val[v11] * this.val[6] * this.val[9] * this.val[12] - this.val[v10] * this.val[7] * this.val[9] * this.val[12] - this.val[v11] * this.val[5] * this.val[10] * this.val[12] + this.val[1] * this.val[7] * this.val[10] * this.val[12] + this.val[v10] * this.val[5] * this.val[11] * this.val[12] - this.val[1] * this.val[6] * this.val[11] * this.val[12] - this.val[v11] * this.val[6] * this.val[8] * this.val[13] + this.val[v10] * this.val[7] * this.val[8] * this.val[13] + this.val[v11] * this.val[v12] * this.val[10] * this.val[13] - this.val[0] * this.val[7] * this.val[10] * this.val[13] - this.val[v10] * this.val[v12] * this.val[11] * this.val[13] + this.val[0] * this.val[6] * this.val[11] * this.val[13] + this.val[v11] * this.val[5] * this.val[8] * this.val[14] - this.val[1] * this.val[7] * this.val[8] * this.val[14] - this.val[v11] * this.val[v12] * this.val[9] * this.val[14] + this.val[0] * this.val[7] * this.val[9] * this.val[14] + this.val[1] * this.val[v12] * this.val[11] * this.val[14] - this.val[0] * this.val[5] * this.val[11] * this.val[14] - this.val[v10] * this.val[5] * this.val[8] * this.val[15] + this.val[1] * this.val[6] * this.val[8] * this.val[15] + this.val[v10] * this.val[v12] * this.val[9] * this.val[15] - this.val[0] * this.val[6] * this.val[9] * this.val[15] - this.val[1] * this.val[v12] * this.val[10] * this.val[15] + this.val[0] * this.val[5] * this.val[10] * this.val[15];
        if(v1 == 0f) {
            throw new RuntimeException("non-invertible matrix");
        }

        float v0 = 1f / v1;
        Matrix4.tmp[0] = this.val[9] * this.val[14] * this.val[7] - this.val[13] * this.val[10] * this.val[7] + this.val[13] * this.val[6] * this.val[11] - this.val[5] * this.val[14] * this.val[11] - this.val[9] * this.val[6] * this.val[15] + this.val[5] * this.val[10] * this.val[15];
        Matrix4.tmp[v12] = this.val[12] * this.val[10] * this.val[7] - this.val[8] * this.val[14] * this.val[7] - this.val[12] * this.val[6] * this.val[11] + this.val[v12] * this.val[14] * this.val[11] + this.val[8] * this.val[6] * this.val[15] - this.val[v12] * this.val[10] * this.val[15];
        Matrix4.tmp[8] = this.val[8] * this.val[13] * this.val[7] - this.val[12] * this.val[9] * this.val[7] + this.val[12] * this.val[5] * this.val[11] - this.val[v12] * this.val[13] * this.val[11] - this.val[8] * this.val[5] * this.val[15] + this.val[v12] * this.val[9] * this.val[15];
        Matrix4.tmp[12] = this.val[12] * this.val[9] * this.val[6] - this.val[8] * this.val[13] * this.val[6] - this.val[12] * this.val[5] * this.val[10] + this.val[v12] * this.val[13] * this.val[10] + this.val[8] * this.val[5] * this.val[14] - this.val[v12] * this.val[9] * this.val[14];
        Matrix4.tmp[1] = this.val[13] * this.val[10] * this.val[v11] - this.val[9] * this.val[14] * this.val[v11] - this.val[13] * this.val[v10] * this.val[11] + this.val[1] * this.val[14] * this.val[11] + this.val[9] * this.val[v10] * this.val[15] - this.val[1] * this.val[10] * this.val[15];
        Matrix4.tmp[5] = this.val[8] * this.val[14] * this.val[v11] - this.val[12] * this.val[10] * this.val[v11] + this.val[12] * this.val[v10] * this.val[11] - this.val[0] * this.val[14] * this.val[11] - this.val[8] * this.val[v10] * this.val[15] + this.val[0] * this.val[10] * this.val[15];
        Matrix4.tmp[9] = this.val[12] * this.val[9] * this.val[v11] - this.val[8] * this.val[13] * this.val[v11] - this.val[12] * this.val[1] * this.val[11] + this.val[0] * this.val[13] * this.val[11] + this.val[8] * this.val[1] * this.val[15] - this.val[0] * this.val[9] * this.val[15];
        Matrix4.tmp[13] = this.val[8] * this.val[13] * this.val[v10] - this.val[12] * this.val[9] * this.val[v10] + this.val[12] * this.val[1] * this.val[10] - this.val[0] * this.val[13] * this.val[10] - this.val[8] * this.val[1] * this.val[14] + this.val[0] * this.val[9] * this.val[14];
        Matrix4.tmp[v10] = this.val[5] * this.val[14] * this.val[v11] - this.val[13] * this.val[6] * this.val[v11] + this.val[13] * this.val[v10] * this.val[7] - this.val[1] * this.val[14] * this.val[7] - this.val[5] * this.val[v10] * this.val[15] + this.val[1] * this.val[6] * this.val[15];
        Matrix4.tmp[6] = this.val[12] * this.val[6] * this.val[v11] - this.val[v12] * this.val[14] * this.val[v11] - this.val[12] * this.val[v10] * this.val[7] + this.val[0] * this.val[14] * this.val[7] + this.val[v12] * this.val[v10] * this.val[15] - this.val[0] * this.val[6] * this.val[15];
        Matrix4.tmp[10] = this.val[v12] * this.val[13] * this.val[v11] - this.val[12] * this.val[5] * this.val[v11] + this.val[12] * this.val[1] * this.val[7] - this.val[0] * this.val[13] * this.val[7] - this.val[v12] * this.val[1] * this.val[15] + this.val[0] * this.val[5] * this.val[15];
        Matrix4.tmp[14] = this.val[12] * this.val[5] * this.val[v10] - this.val[v12] * this.val[13] * this.val[v10] - this.val[12] * this.val[1] * this.val[6] + this.val[0] * this.val[13] * this.val[6] + this.val[v12] * this.val[1] * this.val[14] - this.val[0] * this.val[5] * this.val[14];
        Matrix4.tmp[v11] = this.val[9] * this.val[6] * this.val[v11] - this.val[5] * this.val[10] * this.val[v11] - this.val[9] * this.val[v10] * this.val[7] + this.val[1] * this.val[10] * this.val[7] + this.val[5] * this.val[v10] * this.val[11] - this.val[1] * this.val[6] * this.val[11];
        Matrix4.tmp[7] = this.val[v12] * this.val[10] * this.val[v11] - this.val[8] * this.val[6] * this.val[v11] + this.val[8] * this.val[v10] * this.val[7] - this.val[0] * this.val[10] * this.val[7] - this.val[v12] * this.val[v10] * this.val[11] + this.val[0] * this.val[6] * this.val[11];
        Matrix4.tmp[11] = this.val[8] * this.val[5] * this.val[v11] - this.val[v12] * this.val[9] * this.val[v11] - this.val[8] * this.val[1] * this.val[7] + this.val[0] * this.val[9] * this.val[7] + this.val[v12] * this.val[1] * this.val[11] - this.val[0] * this.val[5] * this.val[11];
        Matrix4.tmp[15] = this.val[v12] * this.val[9] * this.val[v10] - this.val[8] * this.val[5] * this.val[v10] + this.val[8] * this.val[1] * this.val[6] - this.val[0] * this.val[9] * this.val[6] - this.val[v12] * this.val[1] * this.val[10] + this.val[0] * this.val[5] * this.val[10];
        this.val[0] = Matrix4.tmp[0] * v0;
        this.val[v12] = Matrix4.tmp[v12] * v0;
        this.val[8] = Matrix4.tmp[8] * v0;
        this.val[12] = Matrix4.tmp[12] * v0;
        this.val[1] = Matrix4.tmp[1] * v0;
        this.val[5] = Matrix4.tmp[5] * v0;
        this.val[9] = Matrix4.tmp[9] * v0;
        this.val[13] = Matrix4.tmp[13] * v0;
        this.val[v10] = Matrix4.tmp[v10] * v0;
        this.val[6] = Matrix4.tmp[6] * v0;
        this.val[10] = Matrix4.tmp[10] * v0;
        this.val[14] = Matrix4.tmp[14] * v0;
        this.val[v11] = Matrix4.tmp[v11] * v0;
        this.val[7] = Matrix4.tmp[7] * v0;
        this.val[11] = Matrix4.tmp[11] * v0;
        this.val[15] = Matrix4.tmp[15] * v0;
        return this;
    }

    public Matrix4 lerp(Matrix4 matrix, float alpha) {
        int v0;
        for(v0 = 0; v0 < 16; ++v0) {
            this.val[v0] = this.val[v0] * (1f - alpha) + matrix.val[v0] * alpha;
        }

        return this;
    }

    public Matrix4 mul(Matrix4 matrix) {
        Matrix4.mul(this.val, matrix.val);
        return this;
    }

    public static native void mul(float[] arg0, float[] arg1) {
    }

    public Matrix4 mulLeft(Matrix4 matrix) {
        Matrix4.tmpMat.set(matrix);
        Matrix4.mul(Matrix4.tmpMat.val, this.val);
        return this.set(Matrix4.tmpMat);
    }

    public static native void mulVec(float[] arg0, float[] arg1) {
    }

    public static native void mulVec(float[] arg0, float[] arg1, int arg2, int arg3, int arg4) {
    }

    public static native void prj(float[] arg0, float[] arg1, int arg2, int arg3, int arg4) {
    }

    public static native void prj(float[] arg0, float[] arg1) {
    }

    public static native void rot(float[] arg0, float[] arg1) {
    }

    public static native void rot(float[] arg0, float[] arg1, int arg2, int arg3, int arg4) {
    }

    public Matrix4 rotate(float axisX, float axisY, float axisZ, float degrees) {
        if(degrees != 0f) {
            Matrix4.quat.setFromAxis(axisX, axisY, axisZ, degrees);
            this = this.rotate(Matrix4.quat);
        }

        return this;
    }

    public Matrix4 rotate(Quaternion rotation) {
        rotation.toMatrix(Matrix4.tmp);
        Matrix4.mul(this.val, Matrix4.tmp);
        return this;
    }

    public Matrix4 rotate(Vector3 axis, float degrees) {
        if(degrees != 0f) {
            Matrix4.quat.set(axis, degrees);
            this = this.rotate(Matrix4.quat);
        }

        return this;
    }

    public Matrix4 rotate(Vector3 v1, Vector3 v2) {
        return this.rotate(Matrix4.quat.setFromCross(v1, v2));
    }

    public Matrix4 rotateRad(float axisX, float axisY, float axisZ, float radians) {
        if(radians != 0f) {
            Matrix4.quat.setFromAxisRad(axisX, axisY, axisZ, radians);
            this = this.rotate(Matrix4.quat);
        }

        return this;
    }

    public Matrix4 rotateRad(Vector3 axis, float radians) {
        if(radians != 0f) {
            Matrix4.quat.setFromAxisRad(axis, radians);
            this = this.rotate(Matrix4.quat);
        }

        return this;
    }

    public Matrix4 scale(float scaleX, float scaleY, float scaleZ) {
        Matrix4.tmp[0] = scaleX;
        Matrix4.tmp[4] = 0f;
        Matrix4.tmp[8] = 0f;
        Matrix4.tmp[12] = 0f;
        Matrix4.tmp[1] = 0f;
        Matrix4.tmp[5] = scaleY;
        Matrix4.tmp[9] = 0f;
        Matrix4.tmp[13] = 0f;
        Matrix4.tmp[2] = 0f;
        Matrix4.tmp[6] = 0f;
        Matrix4.tmp[10] = scaleZ;
        Matrix4.tmp[14] = 0f;
        Matrix4.tmp[3] = 0f;
        Matrix4.tmp[7] = 0f;
        Matrix4.tmp[11] = 0f;
        Matrix4.tmp[15] = 1f;
        Matrix4.mul(this.val, Matrix4.tmp);
        return this;
    }

    public Matrix4 scl(float scale) {
        this.val[0] *= scale;
        this.val[5] *= scale;
        this.val[10] *= scale;
        return this;
    }

    public Matrix4 scl(float x, float y, float z) {
        this.val[0] *= x;
        this.val[5] *= y;
        this.val[10] *= z;
        return this;
    }

    public Matrix4 scl(Vector3 scale) {
        this.val[0] *= scale.x;
        this.val[5] *= scale.y;
        this.val[10] *= scale.z;
        return this;
    }

    public Matrix4 set(Matrix4 matrix) {
        return this.set(matrix.val);
    }

    public Matrix4 set(Vector3 position, Quaternion orientation, Vector3 scale) {
        return this.set(position.x, position.y, position.z, orientation.x, orientation.y, orientation.z, orientation.w, scale.x, scale.y, scale.z);
    }

    public Matrix4 set(Quaternion quaternion) {
        return this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, this.val.length);
        return this;
    }

    public Matrix4 set(float quaternionX, float quaternionY, float quaternionZ, float quaternionW) {
        return this.set(0f, 0f, 0f, quaternionX, quaternionY, quaternionZ, quaternionW);
    }

    public Matrix4 set(float translationX, float translationY, float translationZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW) {
        float v4 = quaternionX * 2f;
        float v8 = quaternionY * 2f;
        float v11 = quaternionZ * 2f;
        float v1 = quaternionW * v4;
        float v2 = quaternionW * v8;
        float v3 = quaternionW * v11;
        float v5 = quaternionX * v4;
        float v6 = quaternionX * v8;
        float v7 = quaternionX * v11;
        float v9 = quaternionY * v8;
        float v10 = quaternionY * v11;
        float v12 = quaternionZ * v11;
        this.val[0] = 1f - (v9 + v12);
        this.val[4] = v6 - v3;
        this.val[8] = v7 + v2;
        this.val[12] = translationX;
        this.val[1] = v6 + v3;
        this.val[5] = 1f - (v5 + v12);
        this.val[9] = v10 - v1;
        this.val[13] = translationY;
        this.val[2] = v7 - v2;
        this.val[6] = v10 + v1;
        this.val[10] = 1f - (v5 + v9);
        this.val[14] = translationZ;
        this.val[3] = 0f;
        this.val[7] = 0f;
        this.val[11] = 0f;
        this.val[15] = 1f;
        return this;
    }

    public Matrix4 set(float translationX, float translationY, float translationZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW, float scaleX, float scaleY, float scaleZ) {
        float v4 = quaternionX * 2f;
        float v8 = quaternionY * 2f;
        float v11 = quaternionZ * 2f;
        float v1 = quaternionW * v4;
        float v2 = quaternionW * v8;
        float v3 = quaternionW * v11;
        float v5 = quaternionX * v4;
        float v6 = quaternionX * v8;
        float v7 = quaternionX * v11;
        float v9 = quaternionY * v8;
        float v10 = quaternionY * v11;
        float v12 = quaternionZ * v11;
        this.val[0] = (1f - (v9 + v12)) * scaleX;
        this.val[4] = (v6 - v3) * scaleY;
        this.val[8] = (v7 + v2) * scaleZ;
        this.val[12] = translationX;
        this.val[1] = (v6 + v3) * scaleX;
        this.val[5] = (1f - (v5 + v12)) * scaleY;
        this.val[9] = (v10 - v1) * scaleZ;
        this.val[13] = translationY;
        this.val[2] = (v7 - v2) * scaleX;
        this.val[6] = (v10 + v1) * scaleY;
        this.val[10] = (1f - (v5 + v9)) * scaleZ;
        this.val[14] = translationZ;
        this.val[3] = 0f;
        this.val[7] = 0f;
        this.val[11] = 0f;
        this.val[15] = 1f;
        return this;
    }

    public Matrix4 set(Affine2 affine) {
        this.val[0] = affine.m00;
        this.val[1] = affine.m10;
        this.val[2] = 0f;
        this.val[3] = 0f;
        this.val[4] = affine.m01;
        this.val[5] = affine.m11;
        this.val[6] = 0f;
        this.val[7] = 0f;
        this.val[8] = 0f;
        this.val[9] = 0f;
        this.val[10] = 1f;
        this.val[11] = 0f;
        this.val[12] = affine.m02;
        this.val[13] = affine.m12;
        this.val[14] = 0f;
        this.val[15] = 1f;
        return this;
    }

    public Matrix4 set(Matrix3 mat) {
        this.val[0] = mat.val[0];
        this.val[1] = mat.val[1];
        this.val[2] = mat.val[2];
        this.val[3] = 0f;
        this.val[4] = mat.val[3];
        this.val[5] = mat.val[4];
        this.val[6] = mat.val[5];
        this.val[7] = 0f;
        this.val[8] = 0f;
        this.val[9] = 0f;
        this.val[10] = 1f;
        this.val[11] = 0f;
        this.val[12] = mat.val[6];
        this.val[13] = mat.val[7];
        this.val[14] = 0f;
        this.val[15] = mat.val[8];
        return this;
    }

    public Matrix4 set(Vector3 position, Quaternion orientation) {
        return this.set(position.x, position.y, position.z, orientation.x, orientation.y, orientation.z, orientation.w);
    }

    public Matrix4 set(Vector3 xAxis, Vector3 yAxis, Vector3 zAxis, Vector3 pos) {
        this.val[0] = xAxis.x;
        this.val[4] = xAxis.y;
        this.val[8] = xAxis.z;
        this.val[1] = yAxis.x;
        this.val[5] = yAxis.y;
        this.val[9] = yAxis.z;
        this.val[2] = zAxis.x;
        this.val[6] = zAxis.y;
        this.val[10] = zAxis.z;
        this.val[12] = pos.x;
        this.val[13] = pos.y;
        this.val[14] = pos.z;
        this.val[3] = 0f;
        this.val[7] = 0f;
        this.val[11] = 0f;
        this.val[15] = 1f;
        return this;
    }

    public Matrix4 setAsAffine(Affine2 affine) {
        this.val[0] = affine.m00;
        this.val[1] = affine.m10;
        this.val[4] = affine.m01;
        this.val[5] = affine.m11;
        this.val[12] = affine.m02;
        this.val[13] = affine.m12;
        return this;
    }

    public Matrix4 setAsAffine(Matrix4 mat) {
        this.val[0] = mat.val[0];
        this.val[1] = mat.val[1];
        this.val[4] = mat.val[4];
        this.val[5] = mat.val[5];
        this.val[12] = mat.val[12];
        this.val[13] = mat.val[13];
        return this;
    }

    public Matrix4 setFromEulerAngles(float yaw, float pitch, float roll) {
        Matrix4.quat.setEulerAngles(yaw, pitch, roll);
        return this.set(Matrix4.quat);
    }

    public Matrix4 setToLookAt(Vector3 direction, Vector3 up) {
        Matrix4.l_vez.set(direction).nor();
        Matrix4.l_vex.set(direction).nor();
        Matrix4.l_vex.crs(up).nor();
        Matrix4.l_vey.set(Matrix4.l_vex).crs(Matrix4.l_vez).nor();
        this.idt();
        this.val[0] = Matrix4.l_vex.x;
        this.val[4] = Matrix4.l_vex.y;
        this.val[8] = Matrix4.l_vex.z;
        this.val[1] = Matrix4.l_vey.x;
        this.val[5] = Matrix4.l_vey.y;
        this.val[9] = Matrix4.l_vey.z;
        this.val[2] = -Matrix4.l_vez.x;
        this.val[6] = -Matrix4.l_vez.y;
        this.val[10] = -Matrix4.l_vez.z;
        return this;
    }

    public Matrix4 setToLookAt(Vector3 position, Vector3 target, Vector3 up) {
        Matrix4.tmpVec.set(target).sub(position);
        this.setToLookAt(Matrix4.tmpVec, up);
        this.mul(Matrix4.tmpMat.setToTranslation(-position.x, -position.y, -position.z));
        return this;
    }

    public Matrix4 setToOrtho(float left, float right, float bottom, float top, float near, float far) {
        this.idt();
        this.val[0] = 2f / (right - left);
        this.val[1] = 0f;
        this.val[2] = 0f;
        this.val[3] = 0f;
        this.val[4] = 0f;
        this.val[5] = 2f / (top - bottom);
        this.val[6] = 0f;
        this.val[7] = 0f;
        this.val[8] = 0f;
        this.val[9] = 0f;
        this.val[10] = -2f / (far - near);
        this.val[11] = 0f;
        this.val[12] = -(right + left) / (right - left);
        this.val[13] = -(top + bottom) / (top - bottom);
        this.val[14] = -(far + near) / (far - near);
        this.val[15] = 1f;
        return this;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height) {
        this.setToOrtho(x, x + width, y, y + height, 0f, 1f);
        return this;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height, float near, float far) {
        this.setToOrtho(x, x + width, y, y + height, near, far);
        return this;
    }

    public Matrix4 setToProjection(float near, float far, float fovy, float aspectRatio) {
        this.idt();
        float v2 = ((float)(1 / Math.tan((((double)fovy)) * 0.017453 / 2)));
        this.val[0] = v2 / aspectRatio;
        this.val[1] = 0f;
        this.val[2] = 0f;
        this.val[3] = 0f;
        this.val[4] = 0f;
        this.val[5] = v2;
        this.val[6] = 0f;
        this.val[7] = 0f;
        this.val[8] = 0f;
        this.val[9] = 0f;
        this.val[10] = (far + near) / (near - far);
        this.val[11] = -1f;
        this.val[12] = 0f;
        this.val[13] = 0f;
        this.val[14] = 2f * far * near / (near - far);
        this.val[15] = 0f;
        return this;
    }

    public Matrix4 setToProjection(float left, float right, float bottom, float top, float near, float far) {
        this.val[0] = 2f * near / (right - left);
        this.val[1] = 0f;
        this.val[2] = 0f;
        this.val[3] = 0f;
        this.val[4] = 0f;
        this.val[5] = 2f * near / (top - bottom);
        this.val[6] = 0f;
        this.val[7] = 0f;
        this.val[8] = (right + left) / (right - left);
        this.val[9] = (top + bottom) / (top - bottom);
        this.val[10] = (far + near) / (near - far);
        this.val[11] = -1f;
        this.val[12] = 0f;
        this.val[13] = 0f;
        this.val[14] = 2f * far * near / (near - far);
        this.val[15] = 0f;
        return this;
    }

    public Matrix4 setToRotation(float axisX, float axisY, float axisZ, float degrees) {
        if(degrees == 0f) {
            this.idt();
        }
        else {
            this = this.set(Matrix4.quat.setFromAxis(axisX, axisY, axisZ, degrees));
        }

        return this;
    }

    public Matrix4 setToRotation(float x1, float y1, float z1, float x2, float y2, float z2) {
        return this.set(Matrix4.quat.setFromCross(x1, y1, z1, x2, y2, z2));
    }

    public Matrix4 setToRotation(Vector3 axis, float degrees) {
        if(degrees == 0f) {
            this.idt();
        }
        else {
            this = this.set(Matrix4.quat.set(axis, degrees));
        }

        return this;
    }

    public Matrix4 setToRotation(Vector3 v1, Vector3 v2) {
        return this.set(Matrix4.quat.setFromCross(v1, v2));
    }

    public Matrix4 setToRotationRad(float axisX, float axisY, float axisZ, float radians) {
        if(radians == 0f) {
            this.idt();
        }
        else {
            this = this.set(Matrix4.quat.setFromAxisRad(axisX, axisY, axisZ, radians));
        }

        return this;
    }

    public Matrix4 setToRotationRad(Vector3 axis, float radians) {
        if(radians == 0f) {
            this.idt();
        }
        else {
            this = this.set(Matrix4.quat.setFromAxisRad(axis, radians));
        }

        return this;
    }

    public Matrix4 setToScaling(Vector3 vector) {
        this.idt();
        this.val[0] = vector.x;
        this.val[5] = vector.y;
        this.val[10] = vector.z;
        return this;
    }

    public Matrix4 setToScaling(float x, float y, float z) {
        this.idt();
        this.val[0] = x;
        this.val[5] = y;
        this.val[10] = z;
        return this;
    }

    public Matrix4 setToTranslation(float x, float y, float z) {
        this.idt();
        this.val[12] = x;
        this.val[13] = y;
        this.val[14] = z;
        return this;
    }

    public Matrix4 setToTranslation(Vector3 vector) {
        this.idt();
        this.val[12] = vector.x;
        this.val[13] = vector.y;
        this.val[14] = vector.z;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(float translationX, float translationY, float translationZ, float scalingX, float scalingY, float scalingZ) {
        this.idt();
        this.val[12] = translationX;
        this.val[13] = translationY;
        this.val[14] = translationZ;
        this.val[0] = scalingX;
        this.val[5] = scalingY;
        this.val[10] = scalingZ;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(Vector3 translation, Vector3 scaling) {
        this.idt();
        this.val[12] = translation.x;
        this.val[13] = translation.y;
        this.val[14] = translation.z;
        this.val[0] = scaling.x;
        this.val[5] = scaling.y;
        this.val[10] = scaling.z;
        return this;
    }

    public Matrix4 setToWorld(Vector3 position, Vector3 forward, Vector3 up) {
        Matrix4.tmpForward.set(forward).nor();
        Matrix4.right.set(Matrix4.tmpForward).crs(up).nor();
        Matrix4.tmpUp.set(Matrix4.right).crs(Matrix4.tmpForward).nor();
        this.set(Matrix4.right, Matrix4.tmpUp, Matrix4.tmpForward.scl(-1f), position);
        return this;
    }

    public Matrix4 setTranslation(Vector3 vector) {
        this.val[12] = vector.x;
        this.val[13] = vector.y;
        this.val[14] = vector.z;
        return this;
    }

    public Matrix4 setTranslation(float x, float y, float z) {
        this.val[12] = x;
        this.val[13] = y;
        this.val[14] = z;
        return this;
    }

    public Matrix4 toNormalMatrix() {
        this.val[12] = 0f;
        this.val[13] = 0f;
        this.val[14] = 0f;
        return this.inv().tra();
    }

    public String toString() {
        return "[" + this.val[0] + "|" + this.val[4] + "|" + this.val[8] + "|" + this.val[12] + "]\n" + "[" + this.val[1] + "|" + this.val[5] + "|" + this.val[9] + "|" + this.val[13] + "]\n" + "[" + this.val[2] + "|" + this.val[6] + "|" + this.val[10] + "|" + this.val[14] + "]\n" + "[" + this.val[3] + "|" + this.val[7] + "|" + this.val[11] + "|" + this.val[15] + "]\n";
    }

    public Matrix4 tra() {
        Matrix4.tmp[0] = this.val[0];
        Matrix4.tmp[4] = this.val[1];
        Matrix4.tmp[8] = this.val[2];
        Matrix4.tmp[12] = this.val[3];
        Matrix4.tmp[1] = this.val[4];
        Matrix4.tmp[5] = this.val[5];
        Matrix4.tmp[9] = this.val[6];
        Matrix4.tmp[13] = this.val[7];
        Matrix4.tmp[2] = this.val[8];
        Matrix4.tmp[6] = this.val[9];
        Matrix4.tmp[10] = this.val[10];
        Matrix4.tmp[14] = this.val[11];
        Matrix4.tmp[3] = this.val[12];
        Matrix4.tmp[7] = this.val[13];
        Matrix4.tmp[11] = this.val[14];
        Matrix4.tmp[15] = this.val[15];
        return this.set(Matrix4.tmp);
    }

    public Matrix4 translate(float x, float y, float z) {
        Matrix4.tmp[0] = 1f;
        Matrix4.tmp[4] = 0f;
        Matrix4.tmp[8] = 0f;
        Matrix4.tmp[12] = x;
        Matrix4.tmp[1] = 0f;
        Matrix4.tmp[5] = 1f;
        Matrix4.tmp[9] = 0f;
        Matrix4.tmp[13] = y;
        Matrix4.tmp[2] = 0f;
        Matrix4.tmp[6] = 0f;
        Matrix4.tmp[10] = 1f;
        Matrix4.tmp[14] = z;
        Matrix4.tmp[3] = 0f;
        Matrix4.tmp[7] = 0f;
        Matrix4.tmp[11] = 0f;
        Matrix4.tmp[15] = 1f;
        Matrix4.mul(this.val, Matrix4.tmp);
        return this;
    }

    public Matrix4 translate(Vector3 translation) {
        return this.translate(translation.x, translation.y, translation.z);
    }

    public Matrix4 trn(float x, float y, float z) {
        this.val[12] += x;
        this.val[13] += y;
        this.val[14] += z;
        return this;
    }

    public Matrix4 trn(Vector3 vector) {
        this.val[12] += vector.x;
        this.val[13] += vector.y;
        this.val[14] += vector.z;
        return this;
    }
}

