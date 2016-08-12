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

public class ConvexHull {
    private final FloatArray hull;
    private final IntArray indices;
    private final ShortArray originalIndices;
    private final IntArray quicksortStack;
    private float[] sortedPoints;

    public ConvexHull() {
        super();
        this.quicksortStack = new IntArray();
        this.hull = new FloatArray();
        this.indices = new IntArray();
        this.originalIndices = new ShortArray(false, 0);
    }

    private float ccw(float p3x, float p3y) {
        FloatArray v0 = this.hull;
        int v5 = v0.size;
        float v1 = v0.get(v5 - 4);
        float v2 = v0.get(v5 - 3);
        return (v0.get(v5 - 2) - v1) * (p3y - v2) - (v0.peek() - v2) * (p3x - v1);
    }

    public IntArray computeIndices(FloatArray points, boolean sorted, boolean yDown) {
        return this.computeIndices(points.items, 0, points.size, sorted, yDown);
    }

    public IntArray computeIndices(float[] points, int offset, int count, boolean sorted, boolean yDown) {
        float v14;
        float v13;
        int v4 = offset + count;
        if(!sorted) {
            if(this.sortedPoints == null || this.sortedPoints.length < count) {
                this.sortedPoints = new float[count];
            }

            System.arraycopy(points, offset, this.sortedPoints, 0, count);
            points = this.sortedPoints;
            this.sortWithIndices(points, count, yDown);
        }

        IntArray v8 = this.indices;
        v8.clear();
        FloatArray v5 = this.hull;
        v5.clear();
        int v6 = 0;
        int v7;
        for(v7 = 0; v6 < v4; ++v7) {
            v13 = points[v6];
            v14 = points[v6 + 1];
            while(v5.size >= 4) {
                if(this.ccw(v13, v14) > 0f) {
                    break;
                }

                v5.size += -2;
                --v8.size;
            }

            v5.add(v13);
            v5.add(v14);
            v8.add(v7);
            v6 += 2;
        }

        v6 = v4 - 4;
        v7 = v6 / 2;
        int v12 = v5.size + 2;
        while(v6 >= 0) {
            v13 = points[v6];
            v14 = points[v6 + 1];
            while(v5.size >= v12) {
                if(this.ccw(v13, v14) > 0f) {
                    break;
                }

                v5.size += -2;
                --v8.size;
            }

            v5.add(v13);
            v5.add(v14);
            v8.add(v7);
            v6 += -2;
            --v7;
        }

        if(!sorted) {
            short[] v11 = this.originalIndices.items;
            int[] v9 = v8.items;
            v6 = 0;
            int v10 = v8.size;
            while(v6 < v10) {
                v9[v6] = v11[v9[v6]];
                ++v6;
            }
        }

        return v8;
    }

    public IntArray computeIndices(float[] polygon, boolean sorted, boolean yDown) {
        return this.computeIndices(polygon, 0, polygon.length, sorted, yDown);
    }

    public FloatArray computePolygon(FloatArray points, boolean sorted) {
        return this.computePolygon(points.items, 0, points.size, sorted);
    }

    public FloatArray computePolygon(float[] points, int offset, int count, boolean sorted) {
        float v5;
        float v4;
        int v0 = offset + count;
        if(!sorted) {
            if(this.sortedPoints == null || this.sortedPoints.length < count) {
                this.sortedPoints = new float[count];
            }

            System.arraycopy(points, offset, this.sortedPoints, 0, count);
            points = this.sortedPoints;
            this.sort(points, count);
        }

        FloatArray v1 = this.hull;
        v1.clear();
        int v2;
        for(v2 = 0; v2 < v0; v2 += 2) {
            v4 = points[v2];
            v5 = points[v2 + 1];
            while(v1.size >= 4) {
                if(this.ccw(v4, v5) > 0f) {
                    break;
                }

                v1.size += -2;
            }

            v1.add(v4);
            v1.add(v5);
        }

        v2 = v0 - 4;
        int v3 = v1.size + 2;
        while(v2 >= 0) {
            v4 = points[v2];
            v5 = points[v2 + 1];
            while(v1.size >= v3) {
                if(this.ccw(v4, v5) > 0f) {
                    break;
                }

                v1.size += -2;
            }

            v1.add(v4);
            v1.add(v5);
            v2 += -2;
        }

        return v1;
    }

    public FloatArray computePolygon(float[] polygon, boolean sorted) {
        return this.computePolygon(polygon, 0, polygon.length, sorted);
    }

    private int quicksortPartition(float[] values, int lower, int upper) {
        float v3 = values[lower];
        float v4 = values[lower + 1];
        int v2 = upper;
        int v0 = lower;
        while(true) {
        label_5:
            if(v0 < v2) {
                goto label_6;
            }

            goto label_34;
        label_6:
            while(v0 < v2) {
                if(values[v0] > v3) {
                    break;
                }

                v0 += 2;
            }

        label_11:
            while(values[v2] <= v3) {
                if(values[v2] == v3 && values[v2 + 1] < v4) {
                    break;
                }

                if(v0 >= v2) {
                    goto label_5;
                }

                float v1 = values[v0];
                values[v0] = values[v2];
                values[v2] = v1;
                v1 = values[v0 + 1];
                values[v0 + 1] = values[v2 + 1];
                values[v2 + 1] = v1;
                goto label_5;
            }
        }

        v2 += -2;
        goto label_11;
    label_34:
        values[lower] = values[v2];
        values[v2] = v3;
        values[lower + 1] = values[v2 + 1];
        values[v2 + 1] = v4;
        return v2;
    }

    private int quicksortPartitionWithIndices(float[] values, int lower, int upper, boolean yDown, short[] originalIndices) {
        short v2;
        float v4 = values[lower];
        float v5 = values[lower + 1];
        int v3 = upper;
        int v0 = lower;
        while(v0 < v3) {
            while(v0 < v3) {
                if(values[v0] > v4) {
                    break;
                }

                v0 += 2;
            }

            if(yDown) {
                while(true) {
                    if(values[v3] <= v4) {
                        if(values[v3] == v4) {
                            if(values[v3 + 1] < v5) {
                                goto label_19;
                            }

                            break;
                        }
                        else {
                            break;
                        }
                    }

                label_19:
                    v3 += -2;
                }
            }
            else {
                while(true) {
                    if(values[v3] <= v4) {
                        if(values[v3] == v4) {
                            if(values[v3 + 1] > v5) {
                                goto label_28;
                            }

                            break;
                        }
                        else {
                            break;
                        }
                    }

                label_28:
                    v3 += -2;
                }
            }

            if(v0 >= v3) {
                continue;
            }

            float v1 = values[v0];
            values[v0] = values[v3];
            values[v3] = v1;
            v1 = values[v0 + 1];
            values[v0 + 1] = values[v3 + 1];
            values[v3 + 1] = v1;
            v2 = originalIndices[v0 / 2];
            originalIndices[v0 / 2] = originalIndices[v3 / 2];
            originalIndices[v3 / 2] = v2;
        }

        values[lower] = values[v3];
        values[v3] = v4;
        values[lower + 1] = values[v3 + 1];
        values[v3 + 1] = v5;
        v2 = originalIndices[lower / 2];
        originalIndices[lower / 2] = originalIndices[v3 / 2];
        originalIndices[v3 / 2] = v2;
        return v3;
    }

    private void sort(float[] values, int count) {
        IntArray v2 = this.quicksortStack;
        v2.add(0);
        v2.add(count - 2);
        while(true) {
            if(v2.size <= 0) {
                return;
            }

            int v3 = v2.pop();
            int v1 = v2.pop();
            if(v3 <= v1) {
                continue;
            }

            int v0 = this.quicksortPartition(values, v1, v3);
            if(v0 - v1 > v3 - v0) {
                v2.add(v1);
                v2.add(v0 - 2);
            }

            v2.add(v0 + 2);
            v2.add(v3);
            if(v3 - v0 < v0 - v1) {
                continue;
            }

            v2.add(v1);
            v2.add(v0 - 2);
        }
    }

    private void sortWithIndices(float[] values, int count, boolean yDown) {
        int v7 = count / 2;
        this.originalIndices.clear();
        this.originalIndices.ensureCapacity(v7);
        short[] v5 = this.originalIndices.items;
        short v6;
        for(v6 = 0; v6 < v7; v6 = ((short)(v6 + 1))) {
            v5[v6] = v6;
        }

        IntArray v8 = this.quicksortStack;
        v8.add(0);
        v8.add(count - 2);
        while(true) {
            if(v8.size <= 0) {
                return;
            }

            int v3 = v8.pop();
            int v2 = v8.pop();
            if(v3 <= v2) {
                continue;
            }

            int v6_1 = this.quicksortPartitionWithIndices(values, v2, v3, yDown, v5);
            if(v6_1 - v2 > v3 - v6_1) {
                v8.add(v2);
                v8.add(v6_1 - 2);
            }

            v8.add(v6_1 + 2);
            v8.add(v3);
            if(v3 - v6_1 < v6_1 - v2) {
                continue;
            }

            v8.add(v2);
            v8.add(v6_1 - 2);
        }
    }
}

