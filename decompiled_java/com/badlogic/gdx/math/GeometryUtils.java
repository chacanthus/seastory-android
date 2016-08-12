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

public final class GeometryUtils {
    private static final Vector2 tmp1;
    private static final Vector2 tmp2;
    private static final Vector2 tmp3;

    static  {
        GeometryUtils.tmp1 = new Vector2();
        GeometryUtils.tmp2 = new Vector2();
        GeometryUtils.tmp3 = new Vector2();
    }

    public GeometryUtils() {
        super();
    }

    private static boolean areVerticesClockwise(float[] polygon, int offset, int count) {
        boolean v7 = true;
        boolean v8 = false;
        if(count > 2) {
            float v0 = 0f;
            int v1 = offset;
            int v2 = offset + count - 3;
            while(v1 < v2) {
                v0 += polygon[v1] * polygon[v1 + 3] - polygon[v1 + 2] * polygon[v1 + 1];
                v1 += 2;
            }

            if(polygon[count - 2] * polygon[1] + v0 - polygon[0] * polygon[count - 1] >= 0f) {
                v7 = false;
            }

            v8 = v7;
        }

        return v8;
    }

    public static boolean barycoordInsideTriangle(Vector2 barycentric) {
        boolean v0;
        if(barycentric.x < 0f || barycentric.y < 0f || barycentric.x + barycentric.y > 1f) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public static boolean colinear(float x1, float y1, float x2, float y2, float x3, float y3) {
        boolean v7;
        if(Math.abs((x3 - x2) * (y2 - y1) - (x2 - x1) * (y3 - y2)) < 0.000001f) {
            v7 = true;
        }
        else {
            v7 = false;
        }

        return v7;
    }

    public static void ensureCCW(float[] polygon) {
        if(GeometryUtils.areVerticesClockwise(polygon, 0, polygon.length)) {
            int v1 = polygon.length - 2;
            int v0 = 0;
            int v2 = polygon.length / 2;
            while(v0 < v2) {
                int v3 = v1 - v0;
                float v4 = polygon[v0];
                float v5 = polygon[v0 + 1];
                polygon[v0] = polygon[v3];
                polygon[v0 + 1] = polygon[v3 + 1];
                polygon[v3] = v4;
                polygon[v3 + 1] = v5;
                v0 += 2;
            }
        }
    }

    public static float fromBarycoord(Vector2 barycentric, float a, float b, float c) {
        return (1f - barycentric.x - barycentric.y) * a + barycentric.x * b + barycentric.y * c;
    }

    public static Vector2 fromBarycoord(Vector2 barycentric, Vector2 a, Vector2 b, Vector2 c, Vector2 interpolatedOut) {
        float v0 = 1f - barycentric.x - barycentric.y;
        interpolatedOut.x = a.x * v0 + barycentric.x * b.x + barycentric.y * c.x;
        interpolatedOut.y = a.y * v0 + barycentric.x * b.y + barycentric.y * c.y;
        return interpolatedOut;
    }

    public static float lowestPositiveRoot(float a, float b, float c) {
        float v2;
        float v6 = NaNf;
        float v0 = b * b - 4f * a * c;
        if(v0 < 0f) {
            v2 = v6;
        }
        else {
            float v4 = ((float)Math.sqrt(((double)v0)));
            float v1 = 1f / (2f * a);
            v2 = (-b - v4) * v1;
            float v3 = (-b + v4) * v1;
            if(v2 > v3) {
                float v5 = v3;
                v3 = v2;
                v2 = v5;
            }

            if(v2 > 0f) {
                goto label_9;
            }

            if(v3 <= 0f) {
                goto label_31;
            }

            v2 = v3;
            goto label_9;
        label_31:
            v2 = v6;
        }

    label_9:
        return v2;
    }

    public static float polygonArea(float[] polygon, int offset, int count) {
        float v0 = 0f;
        int v1 = offset;
        int v2 = offset + count;
        while(v1 < v2) {
            int v3 = v1;
            int v5 = v1 + 1;
            int v4 = (v1 + 2) % v2;
            if(v4 < offset) {
                v4 += offset;
            }

            int v6 = (v1 + 3) % v2;
            if(v6 < offset) {
                v6 += offset;
            }

            v0 = v0 + polygon[v3] * polygon[v6] - polygon[v4] * polygon[v5];
            v1 += 2;
        }

        return v0 * 0.5f;
    }

    public static Vector2 polygonCentroid(float[] polygon, int offset, int count, Vector2 centroid) {
        float v0;
        float v6;
        float v8;
        float v5;
        if(count < 6) {
            throw new IllegalArgumentException("A polygon must have 3 or more coordinate pairs.");
        }

        float v4 = 0f;
        float v7 = 0f;
        float v3 = 0f;
        int v1 = offset;
        int v2 = offset + count - 2;
        while(v1 < v2) {
            v5 = polygon[v1];
            v8 = polygon[v1 + 1];
            v6 = polygon[v1 + 2];
            v0 = v5 * polygon[v1 + 3] - v6 * v8;
            v3 += v0;
            v4 += (v5 + v6) * v0;
            v7 += (v8 + polygon[v1 + 3]) * v0;
            v1 += 2;
        }

        v5 = polygon[v1];
        v8 = polygon[v1 + 1];
        v6 = polygon[offset];
        v0 = v5 * polygon[offset + 1] - v6 * v8;
        v3 += v0;
        v4 += (v5 + v6) * v0;
        v7 += (v8 + polygon[offset + 1]) * v0;
        if(v3 == 0f) {
            centroid.x = 0f;
            centroid.y = 0f;
        }
        else {
            v3 *= 0.5f;
            centroid.x = v4 / (6f * v3);
            centroid.y = v7 / (6f * v3);
        }

        return centroid;
    }

    public static Vector2 quadrilateralCentroid(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Vector2 centroid) {
        float v0 = (x1 + x2 + x3) / 3f;
        float v2 = (y1 + y2 + y3) / 3f;
        centroid.x = v0 - (v0 - (x1 + x4 + x3) / 3f) / 2f;
        centroid.y = v2 - (v2 - (y1 + y4 + y3) / 3f) / 2f;
        return centroid;
    }

    public static Vector2 toBarycoord(Vector2 p, Vector2 a, Vector2 b, Vector2 c, Vector2 barycentricOut) {
        Vector2 v6 = GeometryUtils.tmp1.set(b).sub(a);
        Vector2 v7 = GeometryUtils.tmp2.set(c).sub(a);
        Vector2 v8 = GeometryUtils.tmp3.set(p).sub(a);
        float v0 = v6.dot(v6);
        float v1 = v6.dot(v7);
        float v2 = v7.dot(v7);
        float v3 = v8.dot(v6);
        float v4 = v8.dot(v7);
        float v5 = v0 * v2 - v1 * v1;
        barycentricOut.x = (v2 * v3 - v1 * v4) / v5;
        barycentricOut.y = (v0 * v4 - v1 * v3) / v5;
        return barycentricOut;
    }

    public static float triangleArea(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs((x1 - x3) * (y2 - y1) - (x1 - x2) * (y3 - y1)) * 0.5f;
    }

    public static Vector2 triangleCentroid(float x1, float y1, float x2, float y2, float x3, float y3, Vector2 centroid) {
        centroid.x = (x1 + x2 + x3) / 3f;
        centroid.y = (y1 + y2 + y3) / 3f;
        return centroid;
    }

    public static Vector2 triangleCircumcenter(float x1, float y1, float x2, float y2, float x3, float y3, Vector2 circumcenter) {
        float v3 = x2 - x1;
        float v6 = y2 - y1;
        float v4 = x3 - x2;
        float v7 = y3 - y2;
        float v2 = x1 - x3;
        float v5 = y1 - y3;
        float v1 = v4 * v6 - v3 * v7;
        if(Math.abs(v1) < 0.000001f) {
            throw new IllegalArgumentException("Triangle points must not be colinear.");
        }

        v1 *= 2f;
        float v8 = x1 * x1 + y1 * y1;
        float v9 = x2 * x2 + y2 * y2;
        float v10 = x3 * x3 + y3 * y3;
        circumcenter.set((v8 * v7 + v9 * v5 + v10 * v6) / v1, -(v8 * v4 + v9 * v2 + v10 * v3) / v1);
        return circumcenter;
    }
}

