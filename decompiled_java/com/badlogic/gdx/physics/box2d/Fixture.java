// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Fixture {
    protected long addr;
    private Body body;
    private final Filter filter;
    protected Shape shape;
    private final short[] tmp;
    protected Object userData;

    protected Fixture(Body body, long addr) {
        super();
        this.tmp = new short[3];
        this.filter = new Filter();
        this.body = body;
        this.addr = addr;
    }

    public Body getBody() {
        return this.body;
    }

    public float getDensity() {
        return this.jniGetDensity(this.addr);
    }

    public Filter getFilterData() {
        this.jniGetFilterData(this.addr, this.tmp);
        this.filter.maskBits = this.tmp[0];
        this.filter.categoryBits = this.tmp[1];
        this.filter.groupIndex = this.tmp[2];
        return this.filter;
    }

    public float getFriction() {
        return this.jniGetFriction(this.addr);
    }

    public float getRestitution() {
        return this.jniGetRestitution(this.addr);
    }

    public Shape getShape() {
        if(this.shape == null) {
            long v0 = this.jniGetShape(this.addr);
            if(v0 == 0) {
                throw new GdxRuntimeException("Null shape address!");
            }
            else {
                switch(Shape.jniGetType(v0)) {
                    case 0: {
                        goto label_14;
                    }
                    case 1: {
                        goto label_18;
                    }
                    case 2: {
                        goto label_21;
                    }
                    case 3: {
                        goto label_24;
                    }
                }

                throw new GdxRuntimeException("Unknown shape type!");
            label_18:
                this.shape = new EdgeShape(v0);
                goto label_16;
            label_21:
                this.shape = new PolygonShape(v0);
                goto label_16;
            label_24:
                this.shape = new ChainShape(v0);
                goto label_16;
            label_14:
                this.shape = new CircleShape(v0);
            }
        }

    label_16:
        return this.shape;
    }

    public Type getType() {
        switch(this.jniGetType(this.addr)) {
            case 0: {
                goto label_6;
            }
            case 1: {
                goto label_8;
            }
            case 2: {
                goto label_10;
            }
            case 3: {
                goto label_12;
            }
        }

        throw new GdxRuntimeException("Unknown shape type!");
    label_6:
        Type v1 = Type.Circle;
        goto label_7;
    label_8:
        v1 = Type.Edge;
        goto label_7;
    label_10:
        v1 = Type.Polygon;
        goto label_7;
    label_12:
        v1 = Type.Chain;
    label_7:
        return v1;
    }

    public Object getUserData() {
        return this.userData;
    }

    public boolean isSensor() {
        return this.jniIsSensor(this.addr);
    }

    private native float jniGetDensity() {
    }

    private native void jniGetFilterData(short[] arg1) {
    }

    private native float jniGetFriction() {
    }

    private native float jniGetRestitution() {
    }

    private native long jniGetShape() {
    }

    private native int jniGetType() {
    }

    private native boolean jniIsSensor() {
    }

    private native void jniRefilter() {
    }

    private native void jniSetDensity(float arg1) {
    }

    private native void jniSetFilterData(short arg1, short arg2, short arg3) {
    }

    private native void jniSetFriction(float arg1) {
    }

    private native void jniSetRestitution(float arg1) {
    }

    private native void jniSetSensor(boolean arg1) {
    }

    private native boolean jniTestPoint(float arg1, float arg2) {
    }

    public void refilter() {
        this.jniRefilter(this.addr);
    }

    protected void reset(Body body, long addr) {
        this.body = body;
        this.addr = addr;
        this.shape = null;
        this.userData = null;
    }

    public void setDensity(float density) {
        this.jniSetDensity(this.addr, density);
    }

    public void setFilterData(Filter filter) {
        this.jniSetFilterData(this.addr, filter.categoryBits, filter.maskBits, filter.groupIndex);
    }

    public void setFriction(float friction) {
        this.jniSetFriction(this.addr, friction);
    }

    public void setRestitution(float restitution) {
        this.jniSetRestitution(this.addr, restitution);
    }

    public void setSensor(boolean sensor) {
        this.jniSetSensor(this.addr, sensor);
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public boolean testPoint(float x, float y) {
        return this.jniTestPoint(this.addr, x, y);
    }

    public boolean testPoint(Vector2 p) {
        return this.jniTestPoint(this.addr, p.x, p.y);
    }
}

