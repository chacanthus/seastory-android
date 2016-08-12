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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.NumberUtils;

public class BlendingAttribute extends Attribute {
    public static final String Alias = "blended";
    public boolean blended;
    public int destFunction;
    public float opacity;
    public int sourceFunction;

    static  {
        BlendingAttribute.Type = BlendingAttribute.register("blended");
    }

    public BlendingAttribute(int sourceFunc, int destFunc, float opacity) {
        this(true, sourceFunc, destFunc, opacity);
    }

    public BlendingAttribute() {
        this(null);
    }

    public BlendingAttribute(BlendingAttribute copyFrom) {
        float v0;
        int v1;
        int v2;
        boolean v3;
        if(copyFrom == null) {
            v3 = true;
        }
        else {
            v3 = copyFrom.blended;
        }

        if(copyFrom == null) {
            v2 = 770;
        }
        else {
            v2 = copyFrom.sourceFunction;
        }

        if(copyFrom == null) {
            v1 = 771;
        }
        else {
            v1 = copyFrom.destFunction;
        }

        if(copyFrom == null) {
            v0 = 1f;
        }
        else {
            v0 = copyFrom.opacity;
        }

        this(v3, v2, v1, v0);
    }

    public BlendingAttribute(float opacity) {
        this(true, opacity);
    }

    public BlendingAttribute(boolean blended, float opacity) {
        this(blended, 770, 771, opacity);
    }

    public BlendingAttribute(int sourceFunc, int destFunc) {
        this(sourceFunc, destFunc, 1f);
    }

    public BlendingAttribute(boolean blended, int sourceFunc, int destFunc, float opacity) {
        super(BlendingAttribute.Type);
        this.opacity = 1f;
        this.blended = blended;
        this.sourceFunction = sourceFunc;
        this.destFunction = destFunc;
        this.opacity = opacity;
    }

    public int compareTo(Attribute o) {
        int v1 = 1;
        int v2 = -1;
        if(this.type != o.type) {
            v1 = ((int)(this.type - o.type));
        }
        else {
            Attribute v0 = o;
            if(this.blended != ((BlendingAttribute)v0).blended) {
                if(!this.blended) {
                    v1 = v2;
                }
            }
            else if(this.sourceFunction != ((BlendingAttribute)v0).sourceFunction) {
                v1 = this.sourceFunction - ((BlendingAttribute)v0).sourceFunction;
            }
            else if(this.destFunction != ((BlendingAttribute)v0).destFunction) {
                v1 = this.destFunction - ((BlendingAttribute)v0).destFunction;
            }
            else if(MathUtils.isEqual(this.opacity, ((BlendingAttribute)v0).opacity)) {
                v1 = 0;
            }
            else if(this.opacity >= ((BlendingAttribute)v0).opacity) {
                v1 = v2;
            }
        }

        return v1;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((Attribute)x0));
    }

    public Attribute copy() {
        return this.copy();
    }

    public BlendingAttribute copy() {
        return new BlendingAttribute(this);
    }

    public int hashCode() {
        int v1;
        int v2 = super.hashCode() * 947;
        if(this.blended) {
            v1 = 1;
        }
        else {
            v1 = 0;
        }

        return (((v2 + v1) * 947 + this.sourceFunction) * 947 + this.destFunction) * 947 + NumberUtils.floatToRawIntBits(this.opacity);
    }

    public static final boolean is(long mask) {
        boolean v0;
        if((BlendingAttribute.Type & mask) == mask) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

