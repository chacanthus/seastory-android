// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.graphics.Mesh;

public class MeshPart {
    public String id;
    public int indexOffset;
    public Mesh mesh;
    public int numVertices;
    public int primitiveType;

    public MeshPart() {
        super();
    }

    public MeshPart(MeshPart copyFrom) {
        this(copyFrom.id, copyFrom.mesh, copyFrom.indexOffset, copyFrom.numVertices, copyFrom.primitiveType);
    }

    public MeshPart(String id, Mesh mesh, int offset, int size, int type) {
        super();
        this.id = id;
        this.mesh = mesh;
        this.indexOffset = offset;
        this.numVertices = size;
        this.primitiveType = type;
    }

    public boolean equals(MeshPart other) {
        boolean v0;
        if(other != this) {
            if(other != null && other.mesh == this.mesh && other.primitiveType == this.primitiveType && other.indexOffset == this.indexOffset && other.numVertices == this.numVertices) {
                goto label_14;
            }

            v0 = false;
        }
        else {
        label_14:
            v0 = true;
        }

        return v0;
    }

    public boolean equals(Object arg0) {
        boolean v0 = false;
        if(arg0 != null) {
            if((((MeshPart)arg0)) == this) {
                v0 = true;
            }
            else if((arg0 instanceof MeshPart)) {
                v0 = this.equals(((MeshPart)arg0));
            }
        }

        return v0;
    }
}

