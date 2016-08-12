// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AmbientCubemap {
    public final float[] data;

    public AmbientCubemap() {
        super();
        this.data = new float[18];
    }

    public AmbientCubemap(AmbientCubemap copyFrom) {
        this(copyFrom.data);
    }

    public AmbientCubemap(float[] copyFrom) {
        super();
        if(copyFrom.length != 18) {
            throw new GdxRuntimeException("Incorrect array size");
        }

        this.data = new float[copyFrom.length];
        System.arraycopy(copyFrom, 0, this.data, 0, this.data.length);
    }

    public AmbientCubemap add(float r, float g, float b) {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            int v1 = v0 + 1;
            this.data[v0] += r;
            v0 = v1 + 1;
            this.data[v1] += g;
            this.data[v0] += b;
        }

        return this;
    }

    public AmbientCubemap add(float r, float g, float b, float x, float y, float z) {
        int v3;
        float v5 = x * x;
        float v6 = y * y;
        float v7 = z * z;
        float v1 = v5 + v6 + v7;
        if(v1 != 0f) {
            v1 = 1f / v1 * (1f + v1);
            float v4 = r * v1;
            float v2 = g * v1;
            float v0 = b * v1;
            if(x > 0f) {
                v3 = 0;
            }
            else {
                v3 = 3;
            }

            this.data[v3] += v5 * v4;
            int v9 = v3 + 1;
            this.data[v9] += v5 * v2;
            v9 = v3 + 2;
            this.data[v9] += v5 * v0;
            if(y > 0f) {
                v3 = 6;
            }
            else {
                v3 = 9;
            }

            this.data[v3] += v6 * v4;
            v9 = v3 + 1;
            this.data[v9] += v6 * v2;
            v9 = v3 + 2;
            this.data[v9] += v6 * v0;
            if(z > 0f) {
                v3 = 12;
            }
            else {
                v3 = 15;
            }

            this.data[v3] += v7 * v4;
            v9 = v3 + 1;
            this.data[v9] += v7 * v2;
            v9 = v3 + 2;
            this.data[v9] += v7 * v0;
        }

        return this;
    }

    public AmbientCubemap add(float r, float g, float b, Vector3 direction) {
        return this.add(r, g, b, direction.x, direction.y, direction.z);
    }

    public AmbientCubemap add(Color color) {
        return this.add(color.r, color.g, color.b);
    }

    public AmbientCubemap add(Color color, float x, float y, float z) {
        return this.add(color.r, color.g, color.b, x, y, z);
    }

    public AmbientCubemap add(Color color, Vector3 direction) {
        return this.add(color.r, color.g, color.b, direction.x, direction.y, direction.z);
    }

    public AmbientCubemap add(Color color, Vector3 point, Vector3 target) {
        return this.add(color.r, color.g, color.b, target.x - point.x, target.y - point.y, target.z - point.z);
    }

    public AmbientCubemap add(Color color, Vector3 point, Vector3 target, float intensity) {
        float v7 = intensity / (1f + target.dst(point));
        return this.add(color.r * v7, color.g * v7, color.b * v7, target.x - point.x, target.y - point.y, target.z - point.z);
    }

    private static final float clamp(float v) {
        float v1 = 1f;
        if(v < 0f) {
            v = 0f;
        }
        else if(v > v1) {
            v = v1;
        }

        return v;
    }

    public AmbientCubemap clamp() {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            this.data[v0] = AmbientCubemap.clamp(this.data[v0]);
        }

        return this;
    }

    public AmbientCubemap clear() {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            this.data[v0] = 0f;
        }

        return this;
    }

    public Color getColor(Color out, int side) {
        side *= 3;
        return out.set(this.data[side], this.data[side + 1], this.data[side + 2], 1f);
    }

    public AmbientCubemap set(float r, float g, float b) {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            int v1 = v0 + 1;
            this.data[v0] = r;
            v0 = v1 + 1;
            this.data[v1] = g;
            this.data[v0] = b;
        }

        return this;
    }

    public AmbientCubemap set(Color color) {
        return this.set(color.r, color.g, color.b);
    }

    public AmbientCubemap set(AmbientCubemap other) {
        return this.set(other.data);
    }

    public AmbientCubemap set(float[] values) {
        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            this.data[v0] = values[v0];
        }

        return this;
    }

    public String toString() {
        String v1 = "";
        int v0;
        for(v0 = 0; v0 < this.data.length; v0 += 3) {
            v1 = v1 + Float.toString(this.data[v0]) + ", " + Float.toString(this.data[v0 + 1]) + ", " + Float.toString(this.data[v0 + 2]) + "\n";
        }

        return v1;
    }
}

