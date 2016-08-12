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

import com.badlogic.gdx.math.collision.BoundingBox;

public class Frustum {
    protected static final Vector3[] clipSpacePlanePoints;
    protected static final float[] clipSpacePlanePointsArray;
    public final Vector3[] planePoints;
    protected final float[] planePointsArray;
    public final Plane[] planes;
    private static final Vector3 tmpV;

    static  {
        Vector3[] v6 = new Vector3[8];
        v6[0] = new Vector3(-1f, -1f, -1f);
        v6[1] = new Vector3(1f, -1f, -1f);
        v6[2] = new Vector3(1f, 1f, -1f);
        v6[3] = new Vector3(-1f, 1f, -1f);
        v6[4] = new Vector3(-1f, -1f, 1f);
        v6[5] = new Vector3(1f, -1f, 1f);
        v6[6] = new Vector3(1f, 1f, 1f);
        v6[7] = new Vector3(-1f, 1f, 1f);
        Frustum.clipSpacePlanePoints = v6;
        Frustum.clipSpacePlanePointsArray = new float[24];
        Vector3[] v0 = Frustum.clipSpacePlanePoints;
        int v4 = v0.length;
        int v1 = 0;
        int v3;
        for(v3 = 0; v1 < v4; ++v3) {
            Vector3 v5 = v0[v1];
            int v2 = v3 + 1;
            Frustum.clipSpacePlanePointsArray[v3] = v5.x;
            v3 = v2 + 1;
            Frustum.clipSpacePlanePointsArray[v2] = v5.y;
            Frustum.clipSpacePlanePointsArray[v3] = v5.z;
            ++v1;
        }

        Frustum.tmpV = new Vector3();
    }

    public Frustum() {
        int v5 = 6;
        super();
        this.planes = new Plane[v5];
        Vector3[] v1 = new Vector3[8];
        v1[0] = new Vector3();
        v1[1] = new Vector3();
        v1[2] = new Vector3();
        v1[3] = new Vector3();
        v1[4] = new Vector3();
        v1[5] = new Vector3();
        v1[v5] = new Vector3();
        v1[7] = new Vector3();
        this.planePoints = v1;
        this.planePointsArray = new float[24];
        int v0;
        for(v0 = 0; v0 < v5; ++v0) {
            this.planes[v0] = new Plane(new Vector3(), 0f);
        }
    }

    public boolean boundsInFrustum(float x, float y, float z, float halfWidth, float halfHeight, float halfDepth) {
        boolean v2;
        int v0 = 0;
        int v1 = this.planes.length;
        while(v0 < v1) {
            if(this.planes[v0].testPoint(x + halfWidth, y + halfHeight, z + halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x + halfWidth, y + halfHeight, z - halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x + halfWidth, y - halfHeight, z + halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x + halfWidth, y - halfHeight, z - halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x - halfWidth, y + halfHeight, z + halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x - halfWidth, y + halfHeight, z - halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x - halfWidth, y - halfHeight, z + halfDepth) == PlaneSide.Back && this.planes[v0].testPoint(x - halfWidth, y - halfHeight, z - halfDepth) == PlaneSide.Back) {
                v2 = false;
                goto label_71;
            }

            ++v0;
        }

        v2 = true;
    label_71:
        return v2;
    }

    public boolean boundsInFrustum(Vector3 center, Vector3 dimensions) {
        return this.boundsInFrustum(center.x, center.y, center.z, dimensions.x / 2f, dimensions.y / 2f, dimensions.z / 2f);
    }

    public boolean boundsInFrustum(BoundingBox bounds) {
        boolean v2;
        int v0 = 0;
        int v1 = this.planes.length;
        while(v0 < v1) {
            if(this.planes[v0].testPoint(bounds.getCorner000(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner001(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner010(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner011(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner100(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner101(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner110(Frustum.tmpV)) == PlaneSide.Back && this.planes[v0].testPoint(bounds.getCorner111(Frustum.tmpV)) == PlaneSide.Back) {
                v2 = false;
                goto label_63;
            }

            ++v0;
        }

        v2 = true;
    label_63:
        return v2;
    }

    public boolean pointInFrustum(float x, float y, float z) {
        boolean v2;
        int v0 = 0;
        while(true) {
            if(v0 >= this.planes.length) {
                break;
            }
            else if(this.planes[v0].testPoint(x, y, z) == PlaneSide.Back) {
                v2 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_10;
        }

        v2 = true;
    label_10:
        return v2;
    }

    public boolean pointInFrustum(Vector3 point) {
        boolean v2;
        int v0 = 0;
        while(true) {
            if(v0 >= this.planes.length) {
                break;
            }
            else if(this.planes[v0].testPoint(point) == PlaneSide.Back) {
                v2 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_10;
        }

        v2 = true;
    label_10:
        return v2;
    }

    public boolean sphereInFrustum(float x, float y, float z, float radius) {
        boolean v1;
        int v0 = 0;
        while(true) {
            if(v0 >= 6) {
                break;
            }
            else if(this.planes[v0].normal.x * x + this.planes[v0].normal.y * y + this.planes[v0].normal.z * z < -radius - this.planes[v0].d) {
                v1 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_27;
        }

        v1 = true;
    label_27:
        return v1;
    }

    public boolean sphereInFrustum(Vector3 center, float radius) {
        boolean v1;
        int v0 = 0;
        while(true) {
            if(v0 >= 6) {
                break;
            }
            else if(this.planes[v0].normal.x * center.x + this.planes[v0].normal.y * center.y + this.planes[v0].normal.z * center.z < -radius - this.planes[v0].d) {
                v1 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_30;
        }

        v1 = true;
    label_30:
        return v1;
    }

    public boolean sphereInFrustumWithoutNearFar(float x, float y, float z, float radius) {
        boolean v1;
        int v0 = 2;
        while(true) {
            if(v0 >= 6) {
                break;
            }
            else if(this.planes[v0].normal.x * x + this.planes[v0].normal.y * y + this.planes[v0].normal.z * z < -radius - this.planes[v0].d) {
                v1 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_27;
        }

        v1 = true;
    label_27:
        return v1;
    }

    public boolean sphereInFrustumWithoutNearFar(Vector3 center, float radius) {
        boolean v1;
        int v0 = 2;
        while(true) {
            if(v0 >= 6) {
                break;
            }
            else if(this.planes[v0].normal.x * center.x + this.planes[v0].normal.y * center.y + this.planes[v0].normal.z * center.z < -radius - this.planes[v0].d) {
                v1 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_30;
        }

        v1 = true;
    label_30:
        return v1;
    }

    public void update(Matrix4 inverseProjectionView) {
        int v13 = 2;
        int v12 = 4;
        int v11 = 3;
        System.arraycopy(Frustum.clipSpacePlanePointsArray, 0, this.planePointsArray, 0, Frustum.clipSpacePlanePointsArray.length);
        Matrix4.prj(inverseProjectionView.val, this.planePointsArray, 0, 8, v11);
        int v0 = 0;
        int v2;
        for(v2 = 0; v0 < 8; ++v2) {
            Vector3 v3 = this.planePoints[v0];
            int v1 = v2 + 1;
            v3.x = this.planePointsArray[v2];
            v2 = v1 + 1;
            v3.y = this.planePointsArray[v1];
            v3.z = this.planePointsArray[v2];
            ++v0;
        }

        this.planes[0].set(this.planePoints[1], this.planePoints[0], this.planePoints[v13]);
        this.planes[1].set(this.planePoints[v12], this.planePoints[5], this.planePoints[7]);
        this.planes[v13].set(this.planePoints[0], this.planePoints[v12], this.planePoints[v11]);
        this.planes[v11].set(this.planePoints[5], this.planePoints[1], this.planePoints[6]);
        this.planes[v12].set(this.planePoints[v13], this.planePoints[v11], this.planePoints[6]);
        this.planes[5].set(this.planePoints[v12], this.planePoints[0], this.planePoints[1]);
    }
}

