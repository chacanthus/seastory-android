// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

public class IntAttribute extends Attribute {
    public static final String CullFaceAlias = "cullface";

    static  {
        IntAttribute.CullFace = IntAttribute.register("cullface");
    }

    public IntAttribute(long type) {
        super(type);
    }

    public IntAttribute(long type, int value) {
        super(type);
        this.value = value;
    }

    public int compareTo(Attribute o) {
        int v0;
        if(this.type != o.type) {
            v0 = ((int)(this.type - o.type));
        }
        else {
            v0 = this.value - ((IntAttribute)o).value;
        }

        return v0;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((Attribute)x0));
    }

    public Attribute copy() {
        return new IntAttribute(this.type, this.value);
    }

    public static IntAttribute createCullFace(int value) {
        return new IntAttribute(IntAttribute.CullFace, value);
    }

    public int hashCode() {
        return super.hashCode() * 983 + this.value;
    }
}

