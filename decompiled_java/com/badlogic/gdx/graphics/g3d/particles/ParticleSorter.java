// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public abstract class ParticleSorter {
    public class Distance extends ParticleSorter {
        private int currentSize;
        private float[] distances;
        private int[] particleIndices;
        private int[] particleOffsets;

        public Distance() {
            this();
            this.currentSize = 0;
        }

        public void ensureCapacity(int capacity) {
            if(this.currentSize < capacity) {
                this.distances = new float[capacity];
                this.particleIndices = new int[capacity];
                this.particleOffsets = new int[capacity];
                this.currentSize = capacity;
            }
        }

        public void qsort(int si, int ei) {
            int v5;
            float v4;
            int v1;
            if(si < ei) {
                if(ei - si <= 8) {
                    int v0;
                    for(v0 = si; v0 <= ei; ++v0) {
                        v1 = v0;
                        while(v1 > si) {
                            if(this.distances[v1 - 1] <= this.distances[v1]) {
                                break;
                            }

                            v4 = this.distances[v1];
                            this.distances[v1] = this.distances[v1 - 1];
                            this.distances[v1 - 1] = v4;
                            v5 = this.particleIndices[v1];
                            this.particleIndices[v1] = this.particleIndices[v1 - 1];
                            this.particleIndices[v1 - 1] = v5;
                            --v1;
                        }
                    }
                }
                else {
                    float v3 = this.distances[si];
                    v0 = si + 1;
                    int v2 = this.particleIndices[si];
                    for(v1 = si + 1; v1 <= ei; ++v1) {
                        if(v3 > this.distances[v1]) {
                            if(v1 > v0) {
                                v4 = this.distances[v1];
                                this.distances[v1] = this.distances[v0];
                                this.distances[v0] = v4;
                                v5 = this.particleIndices[v1];
                                this.particleIndices[v1] = this.particleIndices[v0];
                                this.particleIndices[v0] = v5;
                            }

                            ++v0;
                        }
                    }

                    this.distances[si] = this.distances[v0 - 1];
                    this.distances[v0 - 1] = v3;
                    this.particleIndices[si] = this.particleIndices[v0 - 1];
                    this.particleIndices[v0 - 1] = v2;
                    this.qsort(si, v0 - 2);
                    this.qsort(v0, ei);
                }
            }
        }

        public int[] sort(Array arg15) {
            float[] v9 = this.camera.view.val;
            float v2 = v9[2];
            float v3 = v9[6];
            float v4 = v9[10];
            int v1 = 0;
            int v6 = 0;
            Iterator v7 = arg15.iterator();
            while(v7.hasNext()) {
                Object v5 = v7.next();
                int v8 = 0;
                int v0 = v6 + ((ParticleControllerRenderData)v5).controller.particles.size;
                while(v6 < v0) {
                    this.distances[v6] = ((ParticleControllerRenderData)v5).positionChannel.data[v8] * v2 + ((ParticleControllerRenderData)v5).positionChannel.data[v8 + 1] * v3 + ((ParticleControllerRenderData)v5).positionChannel.data[v8 + 2] * v4;
                    this.particleIndices[v6] = v6;
                    ++v6;
                    v8 += ((ParticleControllerRenderData)v5).positionChannel.strideSize;
                }

                v1 += ((ParticleControllerRenderData)v5).controller.particles.size;
            }

            this.qsort(0, v1 - 1);
            for(v6 = 0; v6 < v1; ++v6) {
                this.particleOffsets[this.particleIndices[v6]] = v6;
            }

            return this.particleOffsets;
        }
    }

    public class None extends ParticleSorter {
        int currentCapacity;
        int[] indices;

        public None() {
            this();
            this.currentCapacity = 0;
        }

        public void ensureCapacity(int capacity) {
            if(this.currentCapacity < capacity) {
                this.indices = new int[capacity];
                int v0;
                for(v0 = 0; v0 < capacity; ++v0) {
                    this.indices[v0] = v0;
                }

                this.currentCapacity = capacity;
            }
        }

        public int[] sort(Array arg2) {
            return this.indices;
        }
    }

    static final Vector3 TMP_V1;
    protected Camera camera;

    static  {
        ParticleSorter.TMP_V1 = new Vector3();
    }

    public ParticleSorter() {
        super();
    }

    public void ensureCapacity(int capacity) {
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public abstract int[] sort(Array arg0);
}

