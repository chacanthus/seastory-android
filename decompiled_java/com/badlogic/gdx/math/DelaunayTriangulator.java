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

import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

public class DelaunayTriangulator {
    private static final int COMPLETE = 1;
    private static final float EPSILON = 0.000001f;
    private static final int INCOMPLETE = 2;
    private static final int INSIDE;
    private final Vector2 centroid;
    private final BooleanArray complete;
    private final IntArray edges;
    private final ShortArray originalIndices;
    private final IntArray quicksortStack;
    private float[] sortedPoints;
    private final float[] superTriangle;
    private final ShortArray triangles;

    public DelaunayTriangulator() {
        super();
        this.quicksortStack = new IntArray();
        this.triangles = new ShortArray(false, 16);
        this.originalIndices = new ShortArray(false, 0);
        this.edges = new IntArray();
        this.complete = new BooleanArray(false, 16);
        this.superTriangle = new float[6];
        this.centroid = new Vector2();
    }

    private int circumCircle(float xp, float yp, float x1, float y1, float x2, float y2, float x3, float y3) {
        float v12;
        float v9;
        int v13;
        float v10 = Math.abs(y1 - y2);
        float v11 = Math.abs(y2 - y3);
        if(v10 >= 0.000001f) {
            float v2 = -(x2 - x1) / (y2 - y1);
            float v4 = (x1 + x2) / 2f;
            float v6 = (y1 + y2) / 2f;
            if(v11 < 0.000001f) {
                v9 = (x3 + x2) / 2f;
                v12 = (v9 - v4) * v2 + v6;
                goto label_26;
            }
            else {
                float v3 = -(x3 - x2) / (y3 - y2);
                v9 = (v2 * v4 - v3 * ((x2 + x3) / 2f) + (y2 + y3) / 2f - v6) / (v2 - v3);
                v12 = (v9 - v4) * v2 + v6;
            label_26:
                float v0 = x2 - v9;
                float v1 = y2 - v12;
                float v8 = v0 * v0 + v1 * v1;
                v0 = xp - v9;
                v0 *= v0;
                v1 = yp - v12;
                if(v1 * v1 + v0 - v8 <= 0.000001f) {
                    v13 = 0;
                }
                else {
                    if(xp > v9 && v0 > v8) {
                        v13 = 1;
                        goto label_9;
                    }

                    v13 = 2;
                }
            }
        }
        else if(v11 < 0.000001f) {
            v13 = 2;
        }
        else {
            v9 = (x2 + x1) / 2f;
            v12 = (v9 - (x2 + x3) / 2f) * (-(x3 - x2) / (y3 - y2)) + (y2 + y3) / 2f;
            goto label_26;
        }

    label_9:
        return v13;
    }

    public ShortArray computeTriangles(FloatArray points, boolean sorted) {
        return this.computeTriangles(points.items, 0, points.size, sorted);
    }

    public ShortArray computeTriangles(float[] points, int offset, int count, boolean sorted) {
        int v24;
        float v12;
        float v11;
        float v10;
        float v9;
        float v8;
        float v7;
        int v27;
        int v26;
        short[] v34;
        ShortArray v33 = this.triangles;
        v33.clear();
        if(count >= 6) {
            v33.ensureCapacity(count);
            if(!sorted) {
                if(this.sortedPoints == null || this.sortedPoints.length < count) {
                    this.sortedPoints = new float[count];
                }

                System.arraycopy(points, offset, this.sortedPoints, 0, count);
                points = this.sortedPoints;
                this.sort(points, count);
            }

            int v21 = count;
            float v38 = points[0];
            float v41 = points[1];
            float v36 = v38;
            float v39 = v41;
            int v22;
            for(v22 = 2; v22 < v21; ++v22) {
                float v35 = points[v22];
                if(v35 < v38) {
                    v38 = v35;
                }

                if(v35 > v36) {
                    v36 = v35;
                }

                ++v22;
                v35 = points[v22];
                if(v35 < v41) {
                    v41 = v35;
                }

                if(v35 > v39) {
                    v39 = v35;
                }
            }

            float v17 = v36 - v38;
            float v18 = v39 - v41;
            if(v17 <= v18) {
                v17 = v18;
            }

            float v16 = v17 * 20f;
            float v37 = (v36 + v38) / 2f;
            float v40 = (v39 + v41) / 2f;
            float[] v31 = this.superTriangle;
            v31[0] = v37 - v16;
            v31[1] = v40 - v16;
            v31[2] = v37;
            v31[3] = v40 + v16;
            v31[4] = v37 + v16;
            v31[5] = v40 - v16;
            IntArray v19 = this.edges;
            v19.ensureCapacity(count / 2);
            BooleanArray v13 = this.complete;
            v13.clear();
            v13.ensureCapacity(count);
            v33.add(v21);
            v33.add(v21 + 2);
            v33.add(v21 + 4);
            v13.add(false);
            int v29;
            for(v29 = 0; v29 < v21; v29 += 2) {
                float v5 = points[v29];
                float v6 = points[v29 + 1];
                v34 = v33.items;
                boolean[] v14 = v13.items;
                int v32;
                for(v32 = v33.size - 1; v32 >= 0; v32 += -3) {
                    int v15 = v32 / 3;
                    if(!v14[v15]) {
                        v26 = v34[v32 - 2];
                        v27 = v34[v32 - 1];
                        int v28 = v34[v32];
                        if(v26 >= v21) {
                            v22 = v26 - v21;
                            v7 = v31[v22];
                            v8 = v31[v22 + 1];
                        }
                        else {
                            v7 = points[v26];
                            v8 = points[v26 + 1];
                        }

                        if(v27 >= v21) {
                            v22 = v27 - v21;
                            v9 = v31[v22];
                            v10 = v31[v22 + 1];
                        }
                        else {
                            v9 = points[v27];
                            v10 = points[v27 + 1];
                        }

                        if(v28 >= v21) {
                            v22 = v28 - v21;
                            v11 = v31[v22];
                            v12 = v31[v22 + 1];
                        }
                        else {
                            v11 = points[v28];
                            v12 = points[v28 + 1];
                        }

                        switch(this.circumCircle(v5, v6, v7, v8, v9, v10, v11, v12)) {
                            case 0: {
                                goto label_169;
                            }
                            case 1: {
                                goto label_210;
                            }
                        }

                        goto label_135;
                    label_210:
                        v14[v15] = true;
                        goto label_135;
                    label_169:
                        v19.add(v26);
                        v19.add(v27);
                        v19.add(v27);
                        v19.add(v28);
                        v19.add(v28);
                        v19.add(v26);
                        v33.removeIndex(v32);
                        v33.removeIndex(v32 - 1);
                        v33.removeIndex(v32 - 2);
                        v13.removeIndex(v15);
                    }

                label_135:
                }

                int[] v20 = v19.items;
                v22 = 0;
                v24 = v19.size;
                while(v22 < v24) {
                    v26 = v20[v22];
                    if(v26 != -1) {
                        v27 = v20[v22 + 1];
                        int v30 = 0;
                        int v23;
                        for(v23 = v22 + 2; v23 < v24; v23 += 2) {
                            if(v26 == v20[v23 + 1] && v27 == v20[v23]) {
                                v30 = 1;
                                v20[v23] = -1;
                            }
                        }

                        if(v30 != 0) {
                            goto label_227;
                        }

                        v33.add(v26);
                        v33.add(v20[v22 + 1]);
                        v33.add(v29);
                        v13.add(false);
                    }

                label_227:
                    v22 += 2;
                }

                v19.clear();
            }

            v34 = v33.items;
            for(v22 = v33.size - 1; v22 >= 0; v22 += -3) {
                if(v34[v22] >= v21 || v34[v22 - 1] >= v21 || v34[v22 - 2] >= v21) {
                    v33.removeIndex(v22);
                    v33.removeIndex(v22 - 1);
                    v33.removeIndex(v22 - 2);
                }
            }

            if(!sorted) {
                short[] v25 = this.originalIndices.items;
                v22 = 0;
                v24 = v33.size;
                while(v22 < v24) {
                    v34[v22] = ((short)(v25[v34[v22] / 2] * 2));
                    ++v22;
                }
            }

            if(0 != 0) {
                goto label_328;
            }

            v22 = 0;
            v24 = v33.size;
            while(true) {
                if(v22 >= v24) {
                    goto label_7;
                }

                v34[v22] = ((short)(v34[v22] / 2));
                ++v22;
            }

        label_328:
            v22 = 0;
            v24 = v33.size;
            while(v22 < v24) {
                v34[v22] = ((short)(v34[v22] / 2));
                ++v22;
            }
        }

    label_7:
        return v33;
    }

    public ShortArray computeTriangles(float[] polygon, boolean sorted) {
        return this.computeTriangles(polygon, 0, polygon.length, sorted);
    }

    private int quicksortPartition(float[] values, int lower, int upper, short[] originalIndices) {
        short v1;
        float v2;
        float v4 = values[lower];
        int v3 = upper;
        int v0 = lower + 2;
        while(v0 < v3) {
            while(v0 < v3) {
                if(values[v0] > v4) {
                    break;
                }

                v0 += 2;
            }

            while(true) {
                if(values[v3] <= v4) {
                    break;
                }

                v3 += -2;
            }

            if(v0 >= v3) {
                continue;
            }

            v2 = values[v0];
            values[v0] = values[v3];
            values[v3] = v2;
            v2 = values[v0 + 1];
            values[v0 + 1] = values[v3 + 1];
            values[v3 + 1] = v2;
            v1 = originalIndices[v0 / 2];
            originalIndices[v0 / 2] = originalIndices[v3 / 2];
            originalIndices[v3 / 2] = v1;
        }

        values[lower] = values[v3];
        values[v3] = v4;
        v2 = values[lower + 1];
        values[lower + 1] = values[v3 + 1];
        values[v3 + 1] = v2;
        v1 = originalIndices[lower / 2];
        originalIndices[lower / 2] = originalIndices[v3 / 2];
        originalIndices[v3 / 2] = v1;
        return v3;
    }

    private void sort(float[] values, int count) {
        int v3 = count / 2;
        this.originalIndices.clear();
        this.originalIndices.ensureCapacity(v3);
        short[] v2 = this.originalIndices.items;
        short v0;
        for(v0 = 0; v0 < v3; v0 = ((short)(v0 + 1))) {
            v2[v0] = v0;
        }

        IntArray v4 = this.quicksortStack;
        v4.add(0);
        v4.add(count - 2);
        while(true) {
            if(v4.size <= 0) {
                return;
            }

            int v5 = v4.pop();
            int v1 = v4.pop();
            if(v5 <= v1) {
                continue;
            }

            int v0_1 = this.quicksortPartition(values, v1, v5, v2);
            if(v0_1 - v1 > v5 - v0_1) {
                v4.add(v1);
                v4.add(v0_1 - 2);
            }

            v4.add(v0_1 + 2);
            v4.add(v5);
            if(v5 - v0_1 < v0_1 - v1) {
                continue;
            }

            v4.add(v1);
            v4.add(v0_1 - 2);
        }
    }

    public void trim(ShortArray triangles, float[] points, float[] hull, int offset, int count) {
        short[] v14 = triangles.items;
        int v10;
        for(v10 = triangles.size - 1; v10 >= 0; v10 += -3) {
            int v11 = v14[v10 - 2] * 2;
            int v12 = v14[v10 - 1] * 2;
            int v13 = v14[v10] * 2;
            GeometryUtils.triangleCentroid(points[v11], points[v11 + 1], points[v12], points[v12 + 1], points[v13], points[v13 + 1], this.centroid);
            if(!Intersector.isPointInPolygon(hull, offset, count, this.centroid.x, this.centroid.y)) {
                triangles.removeIndex(v10);
                triangles.removeIndex(v10 - 1);
                triangles.removeIndex(v10 - 2);
            }
        }
    }
}

