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

import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

public class EarClippingTriangulator {
    private static final int CONCAVE = -1;
    private static final int CONVEX = 1;
    private static final int TANGENTIAL;
    private short[] indices;
    private final ShortArray indicesArray;
    private final ShortArray triangles;
    private int vertexCount;
    private final IntArray vertexTypes;
    private float[] vertices;

    public EarClippingTriangulator() {
        super();
        this.indicesArray = new ShortArray();
        this.vertexTypes = new IntArray();
        this.triangles = new ShortArray();
    }

    private static boolean areVerticesClockwise(float[] vertices, int offset, int count) {
        boolean v7 = false;
        if(count > 2) {
            float v0 = 0f;
            int v1 = offset;
            int v2 = offset + count - 3;
            while(v1 < v2) {
                v0 += vertices[v1] * vertices[v1 + 3] - vertices[v1 + 2] * vertices[v1 + 1];
                v1 += 2;
            }

            if(vertices[offset + count - 2] * vertices[offset + 1] + v0 - vertices[offset] * vertices[offset + count - 1] < 0f) {
                v7 = true;
            }
        }

        return v7;
    }

    private int classifyVertex(int index) {
        short[] v7 = this.indices;
        int v9 = v7[this.previousIndex(index)] * 2;
        int v6 = v7[index] * 2;
        int v8 = v7[this.nextIndex(index)] * 2;
        return EarClippingTriangulator.computeSpannedAreaSign(this.vertices[v9], this.vertices[v9 + 1], this.vertices[v6], this.vertices[v6 + 1], this.vertices[v8], this.vertices[v8 + 1]);
    }

    private static int computeSpannedAreaSign(float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        return ((int)Math.signum(p1x * (p3y - p2y) + (p1y - p3y) * p2x + (p2y - p1y) * p3x));
    }

    public ShortArray computeTriangles(FloatArray vertices) {
        return this.computeTriangles(vertices.items, 0, vertices.size);
    }

    public ShortArray computeTriangles(float[] vertices, int offset, int count) {
        int v3;
        int v0;
        this.vertices = vertices;
        int v5 = count / 2;
        this.vertexCount = v5;
        int v6 = offset / 2;
        ShortArray v2 = this.indicesArray;
        v2.clear();
        v2.ensureCapacity(v5);
        v2.size = v5;
        short[] v1 = v2.items;
        this.indices = v1;
        if(EarClippingTriangulator.areVerticesClockwise(vertices, offset, count)) {
            v0 = 0;
            while(v0 < v5) {
                v1[v0] = ((short)(v6 + v0));
                short v0_1 = ((short)(v0_1 + 1));
            }
        }
        else {
            v0 = 0;
            v3 = v5 - 1;
            while(v0 < v5) {
                v1[v0] = ((short)(v6 + v3 - v0));
                ++v0;
            }
        }

        IntArray v7 = this.vertexTypes;
        v7.clear();
        v7.ensureCapacity(v5);
        v0 = 0;
        v3 = v5;
        while(v0 < v3) {
            v7.add(this.classifyVertex(v0));
            ++v0;
        }

        ShortArray v4 = this.triangles;
        v4.clear();
        v4.ensureCapacity(Math.max(0, v5 - 2) * 3);
        this.triangulate();
        return v4;
    }

    public ShortArray computeTriangles(float[] vertices) {
        return this.computeTriangles(vertices, 0, vertices.length);
    }

    private void cutEarTip(int earTipIndex) {
        short[] v0 = this.indices;
        ShortArray v1 = this.triangles;
        v1.add(v0[this.previousIndex(earTipIndex)]);
        v1.add(v0[earTipIndex]);
        v1.add(v0[this.nextIndex(earTipIndex)]);
        this.indicesArray.removeIndex(earTipIndex);
        this.vertexTypes.removeIndex(earTipIndex);
        --this.vertexCount;
    }

    private int findEarTip() {
        int v1 = this.vertexCount;
        int v0 = 0;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(!this.isEarTip(v0)) {
                ++v0;
                continue;
            }

            goto label_5;
        }

        int[] v2 = this.vertexTypes.items;
        v0 = 0;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(v2[v0] == -1) {
                ++v0;
                continue;
            }

            goto label_5;
        }

        v0 = 0;
    label_5:
        return v0;
    }

    private boolean isEarTip(int earTipIndex) {
        boolean v8;
        int[] v24 = this.vertexTypes.items;
        if(v24[earTipIndex] == -1) {
            v8 = false;
        }
        else {
            int v22 = this.previousIndex(earTipIndex);
            int v18 = this.nextIndex(earTipIndex);
            short[] v17 = this.indices;
            int v19 = v17[v22] * 2;
            int v20 = v17[earTipIndex] * 2;
            int v21 = v17[v18] * 2;
            float[] v25 = this.vertices;
            float v4 = v25[v19];
            float v5 = v25[v19 + 1];
            float v10 = v25[v20];
            float v11 = v25[v20 + 1];
            float v2 = v25[v21];
            float v3 = v25[v21 + 1];
            int v16;
            for(v16 = this.nextIndex(v18); v16 != v22; v16 = this.nextIndex(v16)) {
                if(v24[v16] != 1) {
                    int v23 = v17[v16] * 2;
                    float v6 = v25[v23];
                    float v7 = v25[v23 + 1];
                    if(EarClippingTriangulator.computeSpannedAreaSign(v2, v3, v4, v5, v6, v7) >= 0 && EarClippingTriangulator.computeSpannedAreaSign(v4, v5, v10, v11, v6, v7) >= 0 && EarClippingTriangulator.computeSpannedAreaSign(v10, v11, v2, v3, v6, v7) >= 0) {
                        v8 = false;
                        goto label_8;
                    }
                }
            }

            v8 = true;
        }

    label_8:
        return v8;
    }

    private int nextIndex(int index) {
        return (index + 1) % this.vertexCount;
    }

    private int previousIndex(int index) {
        if(index == 0) {
            index = this.vertexCount;
        }

        return index - 1;
    }

    private void triangulate() {
        int v2;
        int v8 = 3;
        int[] v5 = this.vertexTypes.items;
        while(this.vertexCount > v8) {
            int v0 = this.findEarTip();
            this.cutEarTip(v0);
            int v3 = this.previousIndex(v0);
            if(v0 == this.vertexCount) {
                v2 = 0;
            }
            else {
                v2 = v0;
            }

            v5[v3] = this.classifyVertex(v3);
            v5[v2] = this.classifyVertex(v2);
        }

        if(this.vertexCount == v8) {
            ShortArray v4 = this.triangles;
            short[] v1 = this.indices;
            v4.add(v1[0]);
            v4.add(v1[1]);
            v4.add(v1[2]);
        }
    }
}

