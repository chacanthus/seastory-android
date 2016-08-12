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

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Quaternion implements Serializable {
    private static final long serialVersionUID = -7661875440774897168L;
    private static Quaternion tmp1;
    private static Quaternion tmp2;
    public float w;
    public float x;
    public float y;
    public float z;

    static  {
        Quaternion.tmp1 = new Quaternion(0f, 0f, 0f, 0f);
        Quaternion.tmp2 = new Quaternion(0f, 0f, 0f, 0f);
    }

    public Quaternion(float x, float y, float z, float w) {
        super();
        this.set(x, y, z, w);
    }

    public Quaternion() {
        super();
        this.idt();
    }

    public Quaternion(Quaternion quaternion) {
        super();
        this.set(quaternion);
    }

    public Quaternion(Vector3 axis, float angle) {
        super();
        this.set(axis, angle);
    }

    public Quaternion add(float qx, float qy, float qz, float qw) {
        this.x += qx;
        this.y += qy;
        this.z += qz;
        this.w += qw;
        return this;
    }

    public Quaternion add(Quaternion quaternion) {
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }

    public Quaternion conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Quaternion cpy() {
        return new Quaternion(this);
    }

    public static final float dot(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
        return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
    }

    public float dot(float x, float y, float z, float w) {
        return this.x * x + this.y * y + this.z * z + this.w * w;
    }

    public float dot(Quaternion other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean equals(Object obj) {
        boolean v1 = true;
        if(this != (((Quaternion)obj))) {
            if(obj == null) {
                v1 = false;
            }
            else if(!(obj instanceof Quaternion)) {
                v1 = false;
            }
            else {
                Object v0 = obj;
                if(NumberUtils.floatToRawIntBits(this.w) == NumberUtils.floatToRawIntBits(((Quaternion)v0).w) && NumberUtils.floatToRawIntBits(this.x) == NumberUtils.floatToRawIntBits(((Quaternion)v0).x) && NumberUtils.floatToRawIntBits(this.y) == NumberUtils.floatToRawIntBits(((Quaternion)v0).y) && NumberUtils.floatToRawIntBits(this.z) == NumberUtils.floatToRawIntBits(((Quaternion)v0).z)) {
                    goto label_3;
                }

                v1 = false;
            }
        }

    label_3:
        return v1;
    }

    public Quaternion exp(float alpha) {
        float v0;
        float v1 = this.len();
        float v2 = ((float)Math.pow(((double)v1), ((double)alpha)));
        float v3 = ((float)Math.acos(((double)(this.w / v1))));
        if((((double)Math.abs(v3))) < 0.001) {
            v0 = v2 * alpha / v1;
        }
        else {
            v0 = ((float)((((double)v2)) * Math.sin(((double)(alpha * v3))) / ((((double)v1)) * Math.sin(((double)v3)))));
        }

        this.w = ((float)((((double)v2)) * Math.cos(((double)(alpha * v3)))));
        this.x *= v0;
        this.y *= v0;
        this.z *= v0;
        this.nor();
        return this;
    }

    public float getAngle() {
        return this.getAngleRad() * 57.295776f;
    }

    public float getAngleAround(float axisX, float axisY, float axisZ) {
        return this.getAngleAroundRad(axisX, axisY, axisZ) * 57.295776f;
    }

    public float getAngleAround(Vector3 axis) {
        return this.getAngleAround(axis.x, axis.y, axis.z);
    }

    public float getAngleAroundRad(float axisX, float axisY, float axisZ) {
        float v0;
        float v6 = Vector3.dot(this.x, this.y, this.z, axisX, axisY, axisZ);
        float v7 = Quaternion.len2(axisX * v6, axisY * v6, axisZ * v6, this.w);
        if(MathUtils.isZero(v7)) {
            v0 = 0f;
        }
        else {
            v0 = ((float)(2 * Math.acos(((double)MathUtils.clamp(((float)((((double)this.w)) / Math.sqrt(((double)v7)))), -1f, 1f)))));
        }

        return v0;
    }

    public float getAngleAroundRad(Vector3 axis) {
        return this.getAngleAroundRad(axis.x, axis.y, axis.z);
    }

    public float getAngleRad() {
        double v0;
        double v2 = 2;
        if(this.w > 1f) {
            v0 = ((double)(this.w / this.len()));
        }
        else {
            v0 = ((double)this.w);
        }

        return ((float)(Math.acos(v0) * v2));
    }

    public float getAxisAngle(Vector3 axis) {
        return this.getAxisAngleRad(axis) * 57.295776f;
    }

    public float getAxisAngleRad(Vector3 axis) {
        float v8 = 1f;
        if(this.w > v8) {
            this.nor();
        }

        float v0 = ((float)(2 * Math.acos(((double)this.w))));
        double v2 = Math.sqrt(((double)(v8 - this.w * this.w)));
        if(v2 < 0.000001) {
            axis.x = this.x;
            axis.y = this.y;
            axis.z = this.z;
        }
        else {
            axis.x = ((float)((((double)this.x)) / v2));
            axis.y = ((float)((((double)this.y)) / v2));
            axis.z = ((float)((((double)this.z)) / v2));
        }

        return v0;
    }

    public int getGimbalPole() {
        int v1;
        float v0 = this.y * this.x + this.z * this.w;
        if(v0 > 0.499f) {
            v1 = 1;
        }
        else if(v0 < -0.499f) {
            v1 = -1;
        }
        else {
            v1 = 0;
        }

        return v1;
    }

    public float getPitch() {
        return this.getPitchRad() * 57.295776f;
    }

    public float getPitchRad() {
        float v1;
        int v0 = this.getGimbalPole();
        if(v0 == 0) {
            v1 = ((float)Math.asin(((double)MathUtils.clamp(2f * (this.w * this.x - this.z * this.y), -1f, 1f))));
        }
        else {
            v1 = (((float)v0)) * 3.141593f * 0.5f;
        }

        return v1;
    }

    public float getRoll() {
        return this.getRollRad() * 57.295776f;
    }

    public float getRollRad() {
        float v1;
        float v6 = 2f;
        int v0 = this.getGimbalPole();
        if(v0 == 0) {
            v1 = MathUtils.atan2((this.w * this.z + this.y * this.x) * v6, 1f - (this.x * this.x + this.z * this.z) * v6);
        }
        else {
            v1 = (((float)v0)) * v6 * MathUtils.atan2(this.y, this.w);
        }

        return v1;
    }

    public void getSwingTwist(float axisX, float axisY, float axisZ, Quaternion swing, Quaternion twist) {
        float v6 = Vector3.dot(this.x, this.y, this.z, axisX, axisY, axisZ);
        twist.set(axisX * v6, axisY * v6, axisZ * v6, this.w).nor();
        swing.set(twist).conjugate().mulLeft(this);
    }

    public void getSwingTwist(Vector3 axis, Quaternion swing, Quaternion twist) {
        this.getSwingTwist(axis.x, axis.y, axis.z, swing, twist);
    }

    public float getYaw() {
        return this.getYawRad() * 57.295776f;
    }

    public float getYawRad() {
        float v0;
        float v5 = 2f;
        if(this.getGimbalPole() == 0) {
            v0 = MathUtils.atan2((this.y * this.w + this.x * this.z) * v5, 1f - (this.y * this.y + this.x * this.x) * v5);
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public int hashCode() {
        return (((NumberUtils.floatToRawIntBits(this.w) + 31) * 31 + NumberUtils.floatToRawIntBits(this.x)) * 31 + NumberUtils.floatToRawIntBits(this.y)) * 31 + NumberUtils.floatToRawIntBits(this.z);
    }

    public Quaternion idt() {
        return this.set(0f, 0f, 0f, 1f);
    }

    public boolean isIdentity() {
        boolean v0;
        if(!MathUtils.isZero(this.x) || !MathUtils.isZero(this.y) || !MathUtils.isZero(this.z) || !MathUtils.isEqual(this.w, 1f)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isIdentity(float tolerance) {
        boolean v0;
        if(!MathUtils.isZero(this.x, tolerance) || !MathUtils.isZero(this.y, tolerance) || !MathUtils.isZero(this.z, tolerance) || !MathUtils.isEqual(this.w, 1f, tolerance)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public static final float len(float x, float y, float z, float w) {
        return ((float)Math.sqrt(((double)(x * x + y * y + z * z + w * w))));
    }

    public float len() {
        return ((float)Math.sqrt(((double)(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w))));
    }

    public static final float len2(float x, float y, float z, float w) {
        return x * x + y * y + z * z + w * w;
    }

    public float len2() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public Quaternion mul(Quaternion other) {
        float v1 = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        float v2 = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
        float v3 = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
        float v0 = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
        this.x = v1;
        this.y = v2;
        this.z = v3;
        this.w = v0;
        return this;
    }

    public Quaternion mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Quaternion mul(float x, float y, float z, float w) {
        float v1 = this.w * x + this.x * w + this.y * z - this.z * y;
        float v2 = this.w * y + this.y * w + this.z * x - this.x * z;
        float v3 = this.w * z + this.z * w + this.x * y - this.y * x;
        float v0 = this.w * w - this.x * x - this.y * y - this.z * z;
        this.x = v1;
        this.y = v2;
        this.z = v3;
        this.w = v0;
        return this;
    }

    public Quaternion mulLeft(Quaternion other) {
        float v1 = other.w * this.x + other.x * this.w + other.y * this.z - other.z * this.y;
        float v2 = other.w * this.y + other.y * this.w + other.z * this.x - other.x * this.z;
        float v3 = other.w * this.z + other.z * this.w + other.x * this.y - other.y * this.x;
        float v0 = other.w * this.w - other.x * this.x - other.y * this.y - other.z * this.z;
        this.x = v1;
        this.y = v2;
        this.z = v3;
        this.w = v0;
        return this;
    }

    public Quaternion mulLeft(float x, float y, float z, float w) {
        float v1 = this.x * w + this.w * x + this.z * y - z * y;
        float v2 = this.y * w + this.w * y + this.x * z - x * z;
        float v3 = this.z * w + this.w * z + this.y * x - y * x;
        float v0 = this.w * w - this.x * x - this.y * y - z * z;
        this.x = v1;
        this.y = v2;
        this.z = v3;
        this.w = v0;
        return this;
    }

    public Quaternion nor() {
        float v0 = this.len2();
        if(v0 != 0f && !MathUtils.isEqual(v0, 1f)) {
            v0 = ((float)Math.sqrt(((double)v0)));
            this.w /= v0;
            this.x /= v0;
            this.y /= v0;
            this.z /= v0;
        }

        return this;
    }

    public Quaternion set(Vector3 axis, float angle) {
        return this.setFromAxis(axis.x, axis.y, axis.z, angle);
    }

    public Quaternion set(Quaternion quaternion) {
        return this.set(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternion setEulerAngles(float yaw, float pitch, float roll) {
        return this.setEulerAnglesRad(yaw * 0.017453f, pitch * 0.017453f, 0.017453f * roll);
    }

    public Quaternion setEulerAnglesRad(float yaw, float pitch, float roll) {
        float v8 = roll * 0.5f;
        float v11 = ((float)Math.sin(((double)v8)));
        float v3 = ((float)Math.cos(((double)v8)));
        float v7 = pitch * 0.5f;
        float v10 = ((float)Math.sin(((double)v7)));
        float v2 = ((float)Math.cos(((double)v7)));
        float v9 = yaw * 0.5f;
        float v12 = ((float)Math.sin(((double)v9)));
        float v4 = ((float)Math.cos(((double)v9)));
        float v6 = v4 * v10;
        float v13 = v12 * v2;
        float v5 = v4 * v2;
        float v14 = v12 * v10;
        this.x = v6 * v3 + v13 * v11;
        this.y = v13 * v3 - v6 * v11;
        this.z = v5 * v11 - v14 * v3;
        this.w = v5 * v3 + v14 * v11;
        return this;
    }

    public Quaternion setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        return this.setFromAxes(false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
    }

    public Quaternion setFromAxes(boolean normalizeAxes, float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        float v5;
        if(normalizeAxes) {
            float v2 = 1f / Vector3.len(xx, xy, xz);
            float v3 = 1f / Vector3.len(yx, yy, yz);
            float v4 = 1f / Vector3.len(zx, zy, zz);
            xx *= v2;
            xy *= v2;
            xz *= v2;
            yy *= v3;
            yz = yz * v3 * v3;
            zx *= v4;
            zy *= v4;
            zz *= v4;
        }

        float v6 = xx + yy + zz;
        if(v6 >= 0f) {
            v5 = ((float)Math.sqrt(((double)(1f + v6))));
            this.w = 0.5f * v5;
            v5 /= 0.5f;
            this.x = (zy - yz) * v5;
            this.y = (xz - zx) * v5;
            this.z = (yx - xy) * v5;
        }
        else {
            if(xx > yy && xx > zz) {
                v5 = ((float)Math.sqrt(1 + (((double)xx)) - (((double)yy)) - (((double)zz))));
                this.x = 0.5f * v5;
                v5 /= 0.5f;
                this.y = (yx + xy) * v5;
                this.z = (xz + zx) * v5;
                this.w = (zy - yz) * v5;
                goto label_42;
            }

            if(yy <= zz) {
                goto label_98;
            }

            v5 = ((float)Math.sqrt(1 + (((double)yy)) - (((double)xx)) - (((double)zz))));
            this.y = 0.5f * v5;
            v5 /= 0.5f;
            this.x = (yx + xy) * v5;
            this.z = (zy + yz) * v5;
            this.w = (xz - zx) * v5;
            goto label_42;
        label_98:
            v5 = ((float)Math.sqrt(1 + (((double)zz)) - (((double)xx)) - (((double)yy))));
            this.z = 0.5f * v5;
            v5 /= 0.5f;
            this.x = (xz + zx) * v5;
            this.y = (zy + yz) * v5;
            this.w = (yx - xy) * v5;
        }

    label_42:
        return this;
    }

    public Quaternion setFromAxis(float x, float y, float z, float degrees) {
        return this.setFromAxisRad(x, y, z, 0.017453f * degrees);
    }

    public Quaternion setFromAxis(Vector3 axis, float degrees) {
        return this.setFromAxis(axis.x, axis.y, axis.z, degrees);
    }

    public Quaternion setFromAxisRad(float x, float y, float z, float radians) {
        float v1;
        Quaternion v4;
        float v7 = 2f;
        float v5 = 6.283185f;
        float v0 = Vector3.len(x, y, z);
        if(v0 == 0f) {
            v4 = this.idt();
        }
        else {
            v0 /= 1f;
            if(radians < 0f) {
                v1 = v5 - -radians % v5;
            }
            else {
                v1 = radians % v5;
            }

            float v3 = ((float)Math.sin(((double)(v1 / v7))));
            v4 = this.set(v0 * x * v3, v0 * y * v3, v0 * z * v3, ((float)Math.cos(((double)(v1 / v7))))).nor();
        }

        return v4;
    }

    public Quaternion setFromAxisRad(Vector3 axis, float radians) {
        return this.setFromAxisRad(axis.x, axis.y, axis.z, radians);
    }

    public Quaternion setFromCross(Vector3 v1, Vector3 v2) {
        return this.setFromAxisRad(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x, ((float)Math.acos(((double)MathUtils.clamp(v1.dot(v2), -1f, 1f)))));
    }

    public Quaternion setFromCross(float x1, float y1, float z1, float x2, float y2, float z2) {
        return this.setFromAxisRad(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2, ((float)Math.acos(((double)MathUtils.clamp(Vector3.dot(x1, y1, z1, x2, y2, z2), -1f, 1f)))));
    }

    public Quaternion setFromMatrix(Matrix4 matrix) {
        return this.setFromMatrix(false, matrix);
    }

    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix4 matrix) {
        return this.setFromAxes(normalizeAxes, matrix.val[0], matrix.val[4], matrix.val[8], matrix.val[1], matrix.val[5], matrix.val[9], matrix.val[2], matrix.val[6], matrix.val[10]);
    }

    public Quaternion setFromMatrix(Matrix3 matrix) {
        return this.setFromMatrix(false, matrix);
    }

    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix3 matrix) {
        return this.setFromAxes(normalizeAxes, matrix.val[0], matrix.val[3], matrix.val[6], matrix.val[1], matrix.val[4], matrix.val[7], matrix.val[2], matrix.val[5], matrix.val[8]);
    }

    public Quaternion slerp(Quaternion end, float alpha) {
        float v0;
        float v10 = 1f;
        float v2 = this.x * end.x + this.y * end.y + this.z * end.z + this.w * end.w;
        if(v2 < 0f) {
            v0 = -v2;
        }
        else {
            v0 = v2;
        }

        float v4 = v10 - alpha;
        float v5 = alpha;
        if((((double)(v10 - v0))) > 0.1) {
            float v1 = ((float)Math.acos(((double)v0)));
            float v3 = v10 / (((float)Math.sin(((double)v1))));
            v4 = (((float)Math.sin(((double)((v10 - alpha) * v1))))) * v3;
            v5 = (((float)Math.sin(((double)(alpha * v1))))) * v3;
        }

        if(v2 < 0f) {
            v5 = -v5;
        }

        this.x = this.x * v4 + end.x * v5;
        this.y = this.y * v4 + end.y * v5;
        this.z = this.z * v4 + end.z * v5;
        this.w = this.w * v4 + end.w * v5;
        return this;
    }

    public Quaternion slerp(Quaternion[] q) {
        float v1 = 1f / (((float)q.length));
        this.set(q[0]).exp(v1);
        int v0;
        for(v0 = 1; v0 < q.length; ++v0) {
            this.mul(Quaternion.tmp1.set(q[v0]).exp(v1));
        }

        this.nor();
        return this;
    }

    public Quaternion slerp(Quaternion[] q, float[] w) {
        this.set(q[0]).exp(w[0]);
        int v0;
        for(v0 = 1; v0 < q.length; ++v0) {
            this.mul(Quaternion.tmp1.set(q[v0]).exp(w[v0]));
        }

        this.nor();
        return this;
    }

    public void toMatrix(float[] matrix) {
        float v1 = this.x * this.x;
        float v2 = this.x * this.y;
        float v3 = this.x * this.z;
        float v0 = this.x * this.w;
        float v5 = this.y * this.y;
        float v6 = this.y * this.z;
        float v4 = this.y * this.w;
        float v8 = this.z * this.z;
        float v7 = this.z * this.w;
        matrix[0] = 1f - (v5 + v8) * 2f;
        matrix[4] = (v2 - v7) * 2f;
        matrix[8] = (v3 + v4) * 2f;
        matrix[12] = 0f;
        matrix[1] = (v2 + v7) * 2f;
        matrix[5] = 1f - (v1 + v8) * 2f;
        matrix[9] = (v6 - v0) * 2f;
        matrix[13] = 0f;
        matrix[2] = (v3 - v4) * 2f;
        matrix[6] = (v6 + v0) * 2f;
        matrix[10] = 1f - (v1 + v5) * 2f;
        matrix[14] = 0f;
        matrix[3] = 0f;
        matrix[7] = 0f;
        matrix[11] = 0f;
        matrix[15] = 1f;
    }

    public String toString() {
        return "[" + this.x + "|" + this.y + "|" + this.z + "|" + this.w + "]";
    }

    public Vector3 transform(Vector3 v) {
        Quaternion.tmp2.set(this);
        Quaternion.tmp2.conjugate();
        Quaternion.tmp2.mulLeft(Quaternion.tmp1.set(v.x, v.y, v.z, 0f)).mulLeft(this);
        v.x = Quaternion.tmp2.x;
        v.y = Quaternion.tmp2.y;
        v.z = Quaternion.tmp2.z;
        return v;
    }
}

