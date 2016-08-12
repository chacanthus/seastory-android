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

public class Vector3 implements Vector, Serializable {
    public static final Vector3 X = null;
    public static final Vector3 Y = null;
    public static final Vector3 Z = null;
    public static final Vector3 Zero = null;
    private static final long serialVersionUID = 3840054589595372522L;
    private static final Matrix4 tmpMat;
    public float x;
    public float y;
    public float z;

    static  {
        Vector3.X = new Vector3(1f, 0f, 0f);
        Vector3.Y = new Vector3(0f, 1f, 0f);
        Vector3.Z = new Vector3(0f, 0f, 1f);
        Vector3.Zero = new Vector3(0f, 0f, 0f);
        Vector3.tmpMat = new Matrix4();
    }

    public Vector3() {
        super();
    }

    public Vector3(float x, float y, float z) {
        super();
        this.set(x, y, z);
    }

    public Vector3(Vector3 vector) {
        super();
        this.set(vector);
    }

    public Vector3(Vector2 vector, float z) {
        super();
        this.set(vector.x, vector.y, z);
    }

    public Vector3(float[] values) {
        super();
        this.set(values[0], values[1], values[2]);
    }

    public Vector3 add(float x, float y, float z) {
        return this.set(this.x + x, this.y + y, this.z + z);
    }

    public Vector3 add(Vector3 vector) {
        return this.add(vector.x, vector.y, vector.z);
    }

    public Vector3 add(float values) {
        return this.set(this.x + values, this.y + values, this.z + values);
    }

    public Vector add(Vector x0) {
        return this.add(((Vector3)x0));
    }

    public Vector3 clamp(float min, float max) {
        float v0 = this.len2();
        if(v0 != 0f) {
            float v1 = max * max;
            if(v0 > v1) {
                this = this.scl(((float)Math.sqrt(((double)(v1 / v0)))));
            }
            else {
                float v2 = min * min;
                if(v0 < v2) {
                    this = this.scl(((float)Math.sqrt(((double)(v2 / v0)))));
                }
            }
        }

        return this;
    }

    public Vector clamp(float x0, float x1) {
        return this.clamp(x0, x1);
    }

    public Vector3 cpy() {
        return new Vector3(this);
    }

    public Vector cpy() {
        return this.cpy();
    }

    public Vector3 crs(Vector3 vector) {
        return this.set(this.y * vector.z - this.z * vector.y, this.z * vector.x - this.x * vector.z, this.x * vector.y - this.y * vector.x);
    }

    public Vector3 crs(float x, float y, float z) {
        return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public float dot(Vector3 vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public float dot(Vector x0) {
        return this.dot(((Vector3)x0));
    }

    public float dst(Vector3 vector) {
        float v0 = vector.x - this.x;
        float v1 = vector.y - this.y;
        float v2 = vector.z - this.z;
        return ((float)Math.sqrt(((double)(v0 * v0 + v1 * v1 + v2 * v2))));
    }

    public float dst(float x, float y, float z) {
        float v0 = x - this.x;
        float v1 = y - this.y;
        float v2 = z - this.z;
        return ((float)Math.sqrt(((double)(v0 * v0 + v1 * v1 + v2 * v2))));
    }

    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2) {
        float v0 = x2 - x1;
        float v1 = y2 - y1;
        float v2 = z2 - z1;
        return ((float)Math.sqrt(((double)(v0 * v0 + v1 * v1 + v2 * v2))));
    }

    public float dst(Vector x0) {
        return this.dst(((Vector3)x0));
    }

    public float dst2(float x, float y, float z) {
        float v0 = x - this.x;
        float v1 = y - this.y;
        float v2 = z - this.z;
        return v0 * v0 + v1 * v1 + v2 * v2;
    }

    public float dst2(Vector3 point) {
        float v0 = point.x - this.x;
        float v1 = point.y - this.y;
        float v2 = point.z - this.z;
        return v0 * v0 + v1 * v1 + v2 * v2;
    }

    public static float dst2(float x1, float y1, float z1, float x2, float y2, float z2) {
        float v0 = x2 - x1;
        float v1 = y2 - y1;
        float v2 = z2 - z1;
        return v0 * v0 + v1 * v1 + v2 * v2;
    }

    public float dst2(Vector x0) {
        return this.dst2(((Vector3)x0));
    }

    public boolean epsilonEquals(float x, float y, float z, float epsilon) {
        boolean v0 = false;
        if(Math.abs(x - this.x) <= epsilon && Math.abs(y - this.y) <= epsilon && Math.abs(z - this.z) <= epsilon) {
            v0 = true;
        }

        return v0;
    }

    public boolean epsilonEquals(Vector3 other, float epsilon) {
        boolean v0 = false;
        if(other != null && Math.abs(other.x - this.x) <= epsilon && Math.abs(other.y - this.y) <= epsilon && Math.abs(other.z - this.z) <= epsilon) {
            v0 = true;
        }

        return v0;
    }

    public boolean epsilonEquals(Vector x0, float x1) {
        return this.epsilonEquals(((Vector3)x0), x1);
    }

    public boolean equals(Object obj) {
        boolean v1 = true;
        if(this != (((Vector3)obj))) {
            if(obj == null) {
                v1 = false;
            }
            else if(this.getClass() != obj.getClass()) {
                v1 = false;
            }
            else {
                Object v0 = obj;
                if(NumberUtils.floatToIntBits(this.x) != NumberUtils.floatToIntBits(((Vector3)v0).x)) {
                    v1 = false;
                }
                else if(NumberUtils.floatToIntBits(this.y) != NumberUtils.floatToIntBits(((Vector3)v0).y)) {
                    v1 = false;
                }
                else if(NumberUtils.floatToIntBits(this.z) != NumberUtils.floatToIntBits(((Vector3)v0).z)) {
                    v1 = false;
                }
            }
        }

        return v1;
    }

    public boolean hasOppositeDirection(Vector3 vector) {
        boolean v0;
        if(this.dot(vector) < 0f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean hasOppositeDirection(Vector x0) {
        return this.hasOppositeDirection(((Vector3)x0));
    }

    public boolean hasSameDirection(Vector3 vector) {
        boolean v0;
        if(this.dot(vector) > 0f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean hasSameDirection(Vector x0) {
        return this.hasSameDirection(((Vector3)x0));
    }

    public int hashCode() {
        return ((NumberUtils.floatToIntBits(this.x) + 31) * 31 + NumberUtils.floatToIntBits(this.y)) * 31 + NumberUtils.floatToIntBits(this.z);
    }

    public boolean idt(Vector3 vector) {
        boolean v0;
        if(this.x != vector.x || this.y != vector.y || this.z != vector.z) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public Vector3 interpolate(Vector3 target, float alpha, Interpolation interpolator) {
        return this.lerp(target, interpolator.apply(0f, 1f, alpha));
    }

    public Vector interpolate(Vector x0, float x1, Interpolation x2) {
        return this.interpolate(((Vector3)x0), x1, x2);
    }

    public boolean isCollinear(Vector3 other) {
        boolean v0;
        if(!this.isOnLine(other) || !this.hasSameDirection(other)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isCollinear(Vector3 other, float epsilon) {
        boolean v0;
        if(!this.isOnLine(other, epsilon) || !this.hasSameDirection(other)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isCollinear(Vector x0) {
        return this.isCollinear(((Vector3)x0));
    }

    public boolean isCollinear(Vector x0, float x1) {
        return this.isCollinear(((Vector3)x0), x1);
    }

    public boolean isCollinearOpposite(Vector3 other) {
        boolean v0;
        if(!this.isOnLine(other) || !this.hasOppositeDirection(other)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isCollinearOpposite(Vector3 other, float epsilon) {
        boolean v0;
        if(!this.isOnLine(other, epsilon) || !this.hasOppositeDirection(other)) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isCollinearOpposite(Vector x0) {
        return this.isCollinearOpposite(((Vector3)x0));
    }

    public boolean isCollinearOpposite(Vector x0, float x1) {
        return this.isCollinearOpposite(((Vector3)x0), x1);
    }

    public boolean isOnLine(Vector3 other) {
        boolean v0;
        if(Vector3.len2(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x) <= 0.000001f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isOnLine(Vector3 other, float epsilon) {
        boolean v0;
        if(Vector3.len2(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x) <= epsilon) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isOnLine(Vector x0) {
        return this.isOnLine(((Vector3)x0));
    }

    public boolean isOnLine(Vector x0, float x1) {
        return this.isOnLine(((Vector3)x0), x1);
    }

    public boolean isPerpendicular(Vector3 vector) {
        return MathUtils.isZero(this.dot(vector));
    }

    public boolean isPerpendicular(Vector3 vector, float epsilon) {
        return MathUtils.isZero(this.dot(vector), epsilon);
    }

    public boolean isPerpendicular(Vector x0) {
        return this.isPerpendicular(((Vector3)x0));
    }

    public boolean isPerpendicular(Vector x0, float x1) {
        return this.isPerpendicular(((Vector3)x0), x1);
    }

    public boolean isUnit() {
        return this.isUnit(0f);
    }

    public boolean isUnit(float margin) {
        boolean v0;
        if(Math.abs(this.len2() - 1f) < margin) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isZero() {
        boolean v0;
        if(this.x != 0f || this.y != 0f || this.z != 0f) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isZero(float margin) {
        boolean v0;
        if(this.len2() < margin) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public float len() {
        return ((float)Math.sqrt(((double)(this.x * this.x + this.y * this.y + this.z * this.z))));
    }

    public static float len(float x, float y, float z) {
        return ((float)Math.sqrt(((double)(x * x + y * y + z * z))));
    }

    public float len2() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public static float len2(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    public Vector3 lerp(Vector3 target, float alpha) {
        this.x += (target.x - this.x) * alpha;
        this.y += (target.y - this.y) * alpha;
        this.z += (target.z - this.z) * alpha;
        return this;
    }

    public Vector lerp(Vector x0, float x1) {
        return this.lerp(((Vector3)x0), x1);
    }

    public Vector3 limit(float limit) {
        return this.limit2(limit * limit);
    }

    public Vector limit(float x0) {
        return this.limit(x0);
    }

    public Vector3 limit2(float limit2) {
        float v0 = this.len2();
        if(v0 > limit2) {
            this.scl(((float)Math.sqrt(((double)(limit2 / v0)))));
        }

        return this;
    }

    public Vector limit2(float x0) {
        return this.limit2(x0);
    }

    public Vector3 mul(Matrix4 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[4] + this.z * matrix.val[8] + matrix.val[12], this.x * matrix.val[1] + this.y * matrix.val[5] + this.z * matrix.val[9] + matrix.val[13], this.x * matrix.val[2] + this.y * matrix.val[6] + this.z * matrix.val[10] + matrix.val[14]);
    }

    public Vector3 mul(Matrix3 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[3] + this.z * matrix.val[6], this.x * matrix.val[1] + this.y * matrix.val[4] + this.z * matrix.val[7], this.x * matrix.val[2] + this.y * matrix.val[5] + this.z * matrix.val[8]);
    }

    public Vector3 mul(Quaternion quat) {
        return quat.transform(this);
    }

    public Vector3 mul4x3(float[] matrix) {
        return this.set(this.x * matrix[0] + this.y * matrix[3] + this.z * matrix[6] + matrix[9], this.x * matrix[1] + this.y * matrix[4] + this.z * matrix[7] + matrix[10], this.x * matrix[2] + this.y * matrix[5] + this.z * matrix[8] + matrix[11]);
    }

    public Vector3 mulAdd(Vector3 vec, float scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        this.z += vec.z * scalar;
        return this;
    }

    public Vector3 mulAdd(Vector3 vec, Vector3 mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        this.z += vec.z * mulVec.z;
        return this;
    }

    public Vector mulAdd(Vector x0, float x1) {
        return this.mulAdd(((Vector3)x0), x1);
    }

    public Vector mulAdd(Vector x0, Vector x1) {
        return this.mulAdd(((Vector3)x0), ((Vector3)x1));
    }

    public Vector3 nor() {
        float v4 = 1f;
        float v0 = this.len2();
        if(v0 != 0f && v0 != v4) {
            this = this.scl(v4 / (((float)Math.sqrt(((double)v0)))));
        }

        return this;
    }

    public Vector nor() {
        return this.nor();
    }

    public Vector3 prj(Matrix4 matrix) {
        float[] v0 = matrix.val;
        float v1 = 1f / (this.x * v0[3] + this.y * v0[7] + this.z * v0[11] + v0[15]);
        return this.set((this.x * v0[0] + this.y * v0[4] + this.z * v0[8] + v0[12]) * v1, (this.x * v0[1] + this.y * v0[5] + this.z * v0[9] + v0[13]) * v1, (this.x * v0[2] + this.y * v0[6] + this.z * v0[10] + v0[14]) * v1);
    }

    public Vector3 rot(Matrix4 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[4] + this.z * matrix.val[8], this.x * matrix.val[1] + this.y * matrix.val[5] + this.z * matrix.val[9], this.x * matrix.val[2] + this.y * matrix.val[6] + this.z * matrix.val[10]);
    }

    public Vector3 rotate(float degrees, float axisX, float axisY, float axisZ) {
        return this.mul(Vector3.tmpMat.setToRotation(axisX, axisY, axisZ, degrees));
    }

    public Vector3 rotate(Vector3 axis, float degrees) {
        Vector3.tmpMat.setToRotation(axis, degrees);
        return this.mul(Vector3.tmpMat);
    }

    public Vector3 rotateRad(float radians, float axisX, float axisY, float axisZ) {
        return this.mul(Vector3.tmpMat.setToRotationRad(axisX, axisY, axisZ, radians));
    }

    public Vector3 rotateRad(Vector3 axis, float radians) {
        Vector3.tmpMat.setToRotationRad(axis, radians);
        return this.mul(Vector3.tmpMat);
    }

    public Vector3 scl(float scalar) {
        return this.set(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 scl(float vx, float vy, float vz) {
        return this.set(this.x * vx, this.y * vy, this.z * vz);
    }

    public Vector3 scl(Vector3 other) {
        return this.set(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector scl(float x0) {
        return this.scl(x0);
    }

    public Vector scl(Vector x0) {
        return this.scl(((Vector3)x0));
    }

    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 set(Vector3 vector) {
        return this.set(vector.x, vector.y, vector.z);
    }

    public Vector3 set(Vector2 vector, float z) {
        return this.set(vector.x, vector.y, z);
    }

    public Vector3 set(float[] values) {
        return this.set(values[0], values[1], values[2]);
    }

    public Vector set(Vector x0) {
        return this.set(((Vector3)x0));
    }

    public Vector3 setLength(float len) {
        return this.setLength2(len * len);
    }

    public Vector setLength(float x0) {
        return this.setLength(x0);
    }

    public Vector3 setLength2(float len2) {
        float v0 = this.len2();
        if(v0 != 0f && v0 != len2) {
            this = this.scl(((float)Math.sqrt(((double)(len2 / v0)))));
        }

        return this;
    }

    public Vector setLength2(float x0) {
        return this.setLength2(x0);
    }

    public Vector3 setZero() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
        return this;
    }

    public Vector setZero() {
        return this.setZero();
    }

    public Vector3 slerp(Vector3 target, float alpha) {
        Vector3 v9_1;
        float v9;
        float v1 = this.dot(target);
        if((((double)v1)) > 0.9995 || (((double)v1)) < -0.9995) {
            v9_1 = this.lerp(target, alpha);
        }
        else {
            float v4 = (((float)Math.acos(((double)v1)))) * alpha;
            float v3 = ((float)Math.sin(((double)v4)));
            float v6 = target.x - this.x * v1;
            float v7 = target.y - this.y * v1;
            float v8 = target.z - this.z * v1;
            float v2 = v6 * v6 + v7 * v7 + v8 * v8;
            if(v2 < 0.0001f) {
                v9 = 1f;
            }
            else {
                v9 = 1f / (((float)Math.sqrt(((double)v2))));
            }

            float v0 = v3 * v9;
            v9_1 = this.scl(((float)Math.cos(((double)v4)))).add(v6 * v0, v7 * v0, v8 * v0).nor();
        }

        return v9_1;
    }

    public Vector3 sub(Vector3 a_vec) {
        return this.sub(a_vec.x, a_vec.y, a_vec.z);
    }

    public Vector3 sub(float x, float y, float z) {
        return this.set(this.x - x, this.y - y, this.z - z);
    }

    public Vector3 sub(float value) {
        return this.set(this.x - value, this.y - value, this.z - value);
    }

    public Vector sub(Vector x0) {
        return this.sub(((Vector3)x0));
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    public Vector3 traMul(Matrix3 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[1] + this.z * matrix.val[2], this.x * matrix.val[3] + this.y * matrix.val[4] + this.z * matrix.val[5], this.x * matrix.val[6] + this.y * matrix.val[7] + this.z * matrix.val[8]);
    }

    public Vector3 traMul(Matrix4 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[1] + this.z * matrix.val[2] + matrix.val[3], this.x * matrix.val[4] + this.y * matrix.val[5] + this.z * matrix.val[6] + matrix.val[7], this.x * matrix.val[8] + this.y * matrix.val[9] + this.z * matrix.val[10] + matrix.val[11]);
    }

    public Vector3 unrotate(Matrix4 matrix) {
        return this.set(this.x * matrix.val[0] + this.y * matrix.val[1] + this.z * matrix.val[2], this.x * matrix.val[4] + this.y * matrix.val[5] + this.z * matrix.val[6], this.x * matrix.val[8] + this.y * matrix.val[9] + this.z * matrix.val[10]);
    }

    public Vector3 untransform(Matrix4 matrix) {
        float[] v0 = matrix.val;
        this.x -= v0[12];
        this.y -= v0[12];
        this.z -= v0[12];
        return this.set(this.x * v0[0] + this.y * v0[1] + this.z * v0[2], this.x * v0[4] + this.y * v0[5] + this.z * v0[6], this.x * v0[8] + this.y * v0[9] + this.z * v0[10]);
    }
}

