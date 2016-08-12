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

public class PolygonShape extends Shape {
    private static float[] verts;

    static  {
        PolygonShape.verts = new float[2];
    }

    protected PolygonShape(long addr) {
        super();
        this.addr = addr;
    }

    public PolygonShape() {
        super();
        this.addr = this.newPolygonShape();
    }

    public Type getType() {
        return Type.Polygon;
    }

    public void getVertex(int index, Vector2 vertex) {
        this.jniGetVertex(this.addr, index, PolygonShape.verts);
        vertex.x = PolygonShape.verts[0];
        vertex.y = PolygonShape.verts[1];
    }

    public int getVertexCount() {
        return this.jniGetVertexCount(this.addr);
    }

    private native void jniGetVertex(int arg1, float[] arg2) {
    }

    private native int jniGetVertexCount() {
    }

    private native void jniSet(float[] arg1, int arg2, int arg3) {
    }

    private native void jniSetAsBox(float arg1, float arg2) {
    }

    private native void jniSetAsBox(float arg1, float arg2, float arg3, float arg4, float arg5) {
    }

    private native long newPolygonShape() {
    }

    public void set(float[] vertices) {
        this.jniSet(this.addr, vertices, 0, vertices.length);
    }

    public void set(float[] vertices, int offset, int len) {
        this.jniSet(this.addr, vertices, offset, len);
    }

    public void set(Vector2[] vertices) {
        float[] v4 = new float[vertices.length * 2];
        int v0 = 0;
        int v7;
        for(v7 = 0; v0 < vertices.length * 2; ++v7) {
            v4[v0] = vertices[v7].x;
            v4[v0 + 1] = vertices[v7].y;
            v0 += 2;
        }

        this.jniSet(this.addr, v4, 0, v4.length);
    }

    public void setAsBox(float hx, float hy) {
        this.jniSetAsBox(this.addr, hx, hy);
    }

    public void setAsBox(float hx, float hy, Vector2 center, float angle) {
        this.jniSetAsBox(this.addr, hx, hy, center.x, center.y, angle);
    }
}

