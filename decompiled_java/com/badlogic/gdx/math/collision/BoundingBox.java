// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class BoundingBox implements Serializable {
    private final Vector3 cnt;
    private final Vector3 dim;
    public final Vector3 max;
    public final Vector3 min;
    private static final long serialVersionUID = -1286036817192127343L;
    private static final Vector3 tmpVector;

    static  {
        BoundingBox.tmpVector = new Vector3();
    }

    public BoundingBox() {
        super();
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.clr();
    }

    public BoundingBox(Vector3 minimum, Vector3 maximum) {
        super();
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.set(minimum, maximum);
    }

    public BoundingBox(BoundingBox bounds) {
        super();
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.set(bounds);
    }

    public BoundingBox clr() {
        return this.set(this.min.set(0f, 0f, 0f), this.max.set(0f, 0f, 0f));
    }

    public boolean contains(Vector3 v) {
        boolean v0;
        if(this.min.x > v.x || this.max.x < v.x || this.min.y > v.y || this.max.y < v.y || this.min.z > v.z || this.max.z < v.z) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean contains(BoundingBox b) {
        boolean v0;
        if(this.isValid()) {
            if(this.min.x <= b.min.x && this.min.y <= b.min.y && this.min.z <= b.min.z && this.max.x >= b.max.x && this.max.y >= b.max.y && this.max.z >= b.max.z) {
                goto label_32;
            }

            v0 = false;
        }
        else {
        label_32:
            v0 = true;
        }

        return v0;
    }

    public BoundingBox ext(float x, float y, float z) {
        return this.set(this.min.set(BoundingBox.min(this.min.x, x), BoundingBox.min(this.min.y, y), BoundingBox.min(this.min.z, z)), this.max.set(BoundingBox.max(this.max.x, x), BoundingBox.max(this.max.y, y), BoundingBox.max(this.max.z, z)));
    }

    public BoundingBox ext(Vector3 point) {
        return this.set(this.min.set(BoundingBox.min(this.min.x, point.x), BoundingBox.min(this.min.y, point.y), BoundingBox.min(this.min.z, point.z)), this.max.set(Math.max(this.max.x, point.x), Math.max(this.max.y, point.y), Math.max(this.max.z, point.z)));
    }

    public BoundingBox ext(BoundingBox a_bounds) {
        return this.set(this.min.set(BoundingBox.min(this.min.x, a_bounds.min.x), BoundingBox.min(this.min.y, a_bounds.min.y), BoundingBox.min(this.min.z, a_bounds.min.z)), this.max.set(BoundingBox.max(this.max.x, a_bounds.max.x), BoundingBox.max(this.max.y, a_bounds.max.y), BoundingBox.max(this.max.z, a_bounds.max.z)));
    }

    public BoundingBox ext(BoundingBox bounds, Matrix4 transform) {
        this.ext(BoundingBox.tmpVector.set(bounds.min.x, bounds.min.y, bounds.min.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.min.x, bounds.min.y, bounds.max.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.min.x, bounds.max.y, bounds.min.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.min.x, bounds.max.y, bounds.max.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.max.x, bounds.min.y, bounds.min.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.max.x, bounds.min.y, bounds.max.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.max.x, bounds.max.y, bounds.min.z).mul(transform));
        this.ext(BoundingBox.tmpVector.set(bounds.max.x, bounds.max.y, bounds.max.z).mul(transform));
        return this;
    }

    public Vector3 getCenter(Vector3 out) {
        return out.set(this.cnt);
    }

    public float getCenterX() {
        return this.cnt.x;
    }

    public float getCenterY() {
        return this.cnt.y;
    }

    public float getCenterZ() {
        return this.cnt.z;
    }

    public Vector3 getCorner000(Vector3 out) {
        return out.set(this.min.x, this.min.y, this.min.z);
    }

    public Vector3 getCorner001(Vector3 out) {
        return out.set(this.min.x, this.min.y, this.max.z);
    }

    public Vector3 getCorner010(Vector3 out) {
        return out.set(this.min.x, this.max.y, this.min.z);
    }

    public Vector3 getCorner011(Vector3 out) {
        return out.set(this.min.x, this.max.y, this.max.z);
    }

    public Vector3 getCorner100(Vector3 out) {
        return out.set(this.max.x, this.min.y, this.min.z);
    }

    public Vector3 getCorner101(Vector3 out) {
        return out.set(this.max.x, this.min.y, this.max.z);
    }

    public Vector3 getCorner110(Vector3 out) {
        return out.set(this.max.x, this.max.y, this.min.z);
    }

    public Vector3 getCorner111(Vector3 out) {
        return out.set(this.max.x, this.max.y, this.max.z);
    }

    public float getDepth() {
        return this.dim.z;
    }

    public Vector3 getDimensions(Vector3 out) {
        return out.set(this.dim);
    }

    public float getHeight() {
        return this.dim.y;
    }

    public Vector3 getMax(Vector3 out) {
        return out.set(this.max);
    }

    public Vector3 getMin(Vector3 out) {
        return out.set(this.min);
    }

    public float getWidth() {
        return this.dim.x;
    }

    public BoundingBox inf() {
        this.min.set(Infinityf, Infinityf, Infinityf);
        this.max.set(-Infinityf, -Infinityf, -Infinityf);
        this.cnt.set(0f, 0f, 0f);
        this.dim.set(0f, 0f, 0f);
        return this;
    }

    public boolean intersects(BoundingBox b) {
        boolean v6 = false;
        float v9 = 2f;
        if(this.isValid()) {
            float v0 = Math.abs(this.cnt.x - b.cnt.x);
            float v3 = this.dim.x / v9 + b.dim.x / v9;
            float v1 = Math.abs(this.cnt.y - b.cnt.y);
            float v4 = this.dim.y / v9 + b.dim.y / v9;
            float v2 = Math.abs(this.cnt.z - b.cnt.z);
            float v5 = this.dim.z / v9 + b.dim.z / v9;
            if(v0 <= v3 && v1 <= v4 && v2 <= v5) {
                v6 = true;
            }
        }

        return v6;
    }

    public boolean isValid() {
        boolean v0;
        if(this.min.x >= this.max.x || this.min.y >= this.max.y || this.min.z >= this.max.z) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    static final float max(float a, float b) {
        if(a <= b) {
            a = b;
        }

        return a;
    }

    static final float min(float a, float b) {
        if(a <= b) {
            b = a;
        }

        return b;
    }

    public BoundingBox mul(Matrix4 transform) {
        float v0 = this.min.x;
        float v2 = this.min.y;
        float v4 = this.min.z;
        float v1 = this.max.x;
        float v3 = this.max.y;
        float v5 = this.max.z;
        this.inf();
        this.ext(BoundingBox.tmpVector.set(v0, v2, v4).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v0, v2, v5).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v0, v3, v4).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v0, v3, v5).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v1, v2, v4).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v1, v2, v5).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v1, v3, v4).mul(transform));
        this.ext(BoundingBox.tmpVector.set(v1, v3, v5).mul(transform));
        return this;
    }

    public BoundingBox set(Vector3 minimum, Vector3 maximum) {
        float v2;
        float v1;
        float v0;
        Vector3 v3 = this.min;
        if(minimum.x < maximum.x) {
            v0 = minimum.x;
        }
        else {
            v0 = maximum.x;
        }

        if(minimum.y < maximum.y) {
            v1 = minimum.y;
        }
        else {
            v1 = maximum.y;
        }

        if(minimum.z < maximum.z) {
            v2 = minimum.z;
        }
        else {
            v2 = maximum.z;
        }

        v3.set(v0, v1, v2);
        v3 = this.max;
        if(minimum.x > maximum.x) {
            v0 = minimum.x;
        }
        else {
            v0 = maximum.x;
        }

        if(minimum.y > maximum.y) {
            v1 = minimum.y;
        }
        else {
            v1 = maximum.y;
        }

        if(minimum.z > maximum.z) {
            v2 = minimum.z;
        }
        else {
            v2 = maximum.z;
        }

        v3.set(v0, v1, v2);
        this.cnt.set(this.min).add(this.max).scl(0.5f);
        this.dim.set(this.max).sub(this.min);
        return this;
    }

    public BoundingBox set(BoundingBox bounds) {
        return this.set(bounds.min, bounds.max);
    }

    public BoundingBox set(List arg4) {
        this.inf();
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.ext(v0.next());
        }

        return this;
    }

    public BoundingBox set(Vector3[] points) {
        this.inf();
        Vector3[] v0 = points;
        int v3 = v0.length;
        int v1;
        for(v1 = 0; v1 < v3; ++v1) {
            this.ext(v0[v1]);
        }

        return this;
    }

    public String toString() {
        return "[" + this.min + "|" + this.max + "]";
    }
}

