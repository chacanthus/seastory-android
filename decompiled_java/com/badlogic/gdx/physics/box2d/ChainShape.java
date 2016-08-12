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

public class ChainShape extends Shape {
    boolean isLooped;
    private static float[] verts;

    static  {
        ChainShape.verts = new float[2];
    }

    ChainShape(long addr) {
        super();
        this.isLooped = false;
        this.addr = addr;
    }

    public ChainShape() {
        super();
        this.isLooped = false;
        this.addr = this.newChainShape();
    }

    public void createChain(float[] vertices) {
        this.jniCreateChain(this.addr, vertices, vertices.length / 2);
        this.isLooped = false;
    }

    public void createChain(Vector2[] vertices) {
        float[] v2 = new float[vertices.length * 2];
        int v0 = 0;
        int v1;
        for(v1 = 0; v0 < vertices.length * 2; ++v1) {
            v2[v0] = vertices[v1].x;
            v2[v0 + 1] = vertices[v1].y;
            v0 += 2;
        }

        this.createChain(v2);
    }

    public void createLoop(float[] vertices) {
        this.jniCreateLoop(this.addr, vertices, vertices.length / 2);
        this.isLooped = true;
    }

    public void createLoop(Vector2[] vertices) {
        float[] v2 = new float[vertices.length * 2];
        int v0 = 0;
        int v1;
        for(v1 = 0; v0 < vertices.length * 2; ++v1) {
            v2[v0] = vertices[v1].x;
            v2[v0 + 1] = vertices[v1].y;
            v0 += 2;
        }

        this.jniCreateLoop(this.addr, v2, v2.length / 2);
        this.isLooped = true;
    }

    public Type getType() {
        return Type.Chain;
    }

    public void getVertex(int index, Vector2 vertex) {
        this.jniGetVertex(this.addr, index, ChainShape.verts);
        vertex.x = ChainShape.verts[0];
        vertex.y = ChainShape.verts[1];
    }

    public int getVertexCount() {
        return this.jniGetVertexCount(this.addr);
    }

    public boolean isLooped() {
        return this.isLooped;
    }

    private native void jniCreateChain(float[] arg1, int arg2) {
    }

    private native void jniCreateLoop(float[] arg1, int arg2) {
    }

    private native void jniGetVertex(int arg1, float[] arg2) {
    }

    private native int jniGetVertexCount() {
    }

    private native void jniSetNextVertex(float arg1, float arg2) {
    }

    private native void jniSetPrevVertex(float arg1, float arg2) {
    }

    private native long newChainShape() {
    }

    public void setNextVertex(float nextVertexX, float nextVertexY) {
        this.jniSetNextVertex(this.addr, nextVertexX, nextVertexY);
    }

    public void setNextVertex(Vector2 nextVertex) {
        this.setNextVertex(nextVertex.x, nextVertex.y);
    }

    public void setPrevVertex(float prevVertexX, float prevVertexY) {
        this.jniSetPrevVertex(this.addr, prevVertexX, prevVertexY);
    }

    public void setPrevVertex(Vector2 prevVertex) {
        this.setPrevVertex(prevVertex.x, prevVertex.y);
    }
}

