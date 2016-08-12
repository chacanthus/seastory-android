// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

public final class VertexAttribute {
    public String alias;
    public final boolean normalized;
    public final int type;
    public int unit;
    public final int usage;
    private final int usageIndex;

    public VertexAttribute(int usage, int numComponents, String alias) {
        this(usage, numComponents, alias, 0);
    }

    private VertexAttribute(int usage, int numComponents, int type, boolean normalized, String alias) {
        this(usage, numComponents, type, normalized, alias, 0);
    }

    private VertexAttribute(int usage, int numComponents, int type, boolean normalized, String alias, int index) {
        super();
        this.usage = usage;
        this.numComponents = numComponents;
        this.type = type;
        this.normalized = normalized;
        this.alias = alias;
        this.unit = index;
        this.usageIndex = Integer.numberOfTrailingZeros(usage);
    }

    public VertexAttribute(int usage, int numComponents, String alias, int index) {
        boolean v4;
        int v3;
        int v0 = 4;
        if(usage == v0) {
            v3 = 5121;
        }
        else {
            v3 = 5126;
        }

        if(usage == v0) {
            v4 = true;
        }
        else {
            v4 = false;
        }

        this(usage, numComponents, v3, v4, alias, index);
    }

    public static VertexAttribute Binormal() {
        return new VertexAttribute(256, 3, "a_binormal");
    }

    public static VertexAttribute BoneWeight(int unit) {
        return new VertexAttribute(64, 2, "a_boneWeight" + unit, unit);
    }

    public static VertexAttribute ColorPacked() {
        return new VertexAttribute(4, 4, 5121, true, "a_color");
    }

    public static VertexAttribute ColorUnpacked() {
        return new VertexAttribute(2, 4, 5126, false, "a_color");
    }

    public static VertexAttribute Normal() {
        return new VertexAttribute(8, 3, "a_normal");
    }

    public static VertexAttribute Position() {
        return new VertexAttribute(1, 3, "a_position");
    }

    public static VertexAttribute Tangent() {
        return new VertexAttribute(128, 3, "a_tangent");
    }

    public static VertexAttribute TexCoords(int unit) {
        return new VertexAttribute(16, 2, "a_texCoord" + unit, unit);
    }

    public boolean equals(VertexAttribute other) {
        boolean v0;
        if(other == null || this.usage != other.usage || this.numComponents != other.numComponents || !this.alias.equals(other.alias) || this.unit != other.unit) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean equals(Object obj) {
        boolean v0;
        if(!(obj instanceof VertexAttribute)) {
            v0 = false;
        }
        else {
            v0 = this.equals(((VertexAttribute)obj));
        }

        return v0;
    }

    public int getKey() {
        return (this.usageIndex << 8) + (this.unit & 255);
    }

    public int hashCode() {
        return (this.getKey() * 541 + this.numComponents) * 541 + this.alias.hashCode();
    }
}

