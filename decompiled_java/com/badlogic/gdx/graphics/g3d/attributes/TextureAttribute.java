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

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;

public class TextureAttribute extends Attribute {
    public static final long Ambient = 0;
    public static final String AmbientAlias = "ambientTexture";
    public static final long Bump = 0;
    public static final String BumpAlias = "bumpTexture";
    public static final String DiffuseAlias = "diffuseTexture";
    public static final long Emissive = 0;
    public static final String EmissiveAlias = "emissiveTexture";
    protected static long Mask = 0;
    public static final long Normal = 0;
    public static final String NormalAlias = "normalTexture";
    public static final long Reflection = 0;
    public static final String ReflectionAlias = "reflectionTexture";
    public static final long Specular = 0;
    public static final String SpecularAlias = "specularTexture";
    public float offsetU;
    public float offsetV;
    public float scaleU;
    public float scaleV;
    public final TextureDescriptor textureDescription;
    public int uvIndex;

    static  {
        TextureAttribute.Diffuse = TextureAttribute.register("diffuseTexture");
        TextureAttribute.Specular = TextureAttribute.register("specularTexture");
        TextureAttribute.Bump = TextureAttribute.register("bumpTexture");
        TextureAttribute.Normal = TextureAttribute.register("normalTexture");
        TextureAttribute.Ambient = TextureAttribute.register("ambientTexture");
        TextureAttribute.Emissive = TextureAttribute.register("emissiveTexture");
        TextureAttribute.Reflection = TextureAttribute.register("reflectionTexture");
        TextureAttribute.Mask = TextureAttribute.Diffuse | TextureAttribute.Specular | TextureAttribute.Bump | TextureAttribute.Normal | TextureAttribute.Ambient | TextureAttribute.Emissive | TextureAttribute.Reflection;
    }

    public TextureAttribute(long type, TextureDescriptor arg14, float offsetU, float offsetV, float scaleU, float scaleV) {
        this(type, arg14, offsetU, offsetV, scaleU, scaleV, 0);
    }

    public TextureAttribute(long type) {
        super(type);
        this.offsetU = 0f;
        this.offsetV = 0f;
        this.scaleU = 1f;
        this.scaleV = 1f;
        this.uvIndex = 0;
        if(!TextureAttribute.is(type)) {
            throw new GdxRuntimeException("Invalid type specified");
        }

        this.textureDescription = new TextureDescriptor();
    }

    public TextureAttribute(long type, Texture texture) {
        this(type);
        this.textureDescription.texture = ((GLTexture)texture);
    }

    public TextureAttribute(long type, TextureRegion region) {
        this(type);
        this.set(region);
    }

    public TextureAttribute(long type, TextureDescriptor arg4) {
        this(type);
        this.textureDescription.set(arg4);
    }

    public TextureAttribute(long type, TextureDescriptor arg4, float offsetU, float offsetV, float scaleU, float scaleV, int uvIndex) {
        this(type, arg4);
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.uvIndex = uvIndex;
    }

    public TextureAttribute(TextureAttribute copyFrom) {
        this(copyFrom.type, copyFrom.textureDescription, copyFrom.offsetU, copyFrom.offsetV, copyFrom.scaleU, copyFrom.scaleV, copyFrom.uvIndex);
    }

    public int compareTo(Attribute o) {
        int v3 = 1;
        int v2 = -1;
        if(this.type != o.type) {
            if(this.type >= o.type) {
                v2 = 1;
            }

            v3 = v2;
        }
        else {
            Attribute v1 = o;
            int v0 = this.textureDescription.compareTo(((TextureAttribute)v1).textureDescription);
            if(v0 == 0) {
                goto label_19;
            }

            v3 = v0;
            goto label_9;
        label_19:
            if(this.uvIndex == ((TextureAttribute)v1).uvIndex) {
                goto label_26;
            }

            v3 = this.uvIndex - ((TextureAttribute)v1).uvIndex;
            goto label_9;
        label_26:
            if(MathUtils.isEqual(this.offsetU, ((TextureAttribute)v1).offsetU)) {
                goto label_35;
            }

            if(this.offsetU < ((TextureAttribute)v1).offsetU) {
                goto label_9;
            }

            v3 = v2;
            goto label_9;
        label_35:
            if(MathUtils.isEqual(this.offsetV, ((TextureAttribute)v1).offsetV)) {
                goto label_44;
            }

            if(this.offsetV < ((TextureAttribute)v1).offsetV) {
                goto label_9;
            }

            v3 = v2;
            goto label_9;
        label_44:
            if(MathUtils.isEqual(this.scaleU, ((TextureAttribute)v1).scaleU)) {
                goto label_53;
            }

            if(this.scaleU < ((TextureAttribute)v1).scaleU) {
                goto label_9;
            }

            v3 = v2;
            goto label_9;
        label_53:
            if(MathUtils.isEqual(this.scaleV, ((TextureAttribute)v1).scaleV)) {
                goto label_62;
            }

            if(this.scaleV < ((TextureAttribute)v1).scaleV) {
                goto label_9;
            }

            v3 = v2;
            goto label_9;
        label_62:
            v3 = 0;
        }

    label_9:
        return v3;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((Attribute)x0));
    }

    public Attribute copy() {
        return new TextureAttribute(this);
    }

    public static TextureAttribute createAmbient(Texture texture) {
        return new TextureAttribute(TextureAttribute.Ambient, texture);
    }

    public static TextureAttribute createAmbient(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Ambient, region);
    }

    public static TextureAttribute createBump(Texture texture) {
        return new TextureAttribute(TextureAttribute.Bump, texture);
    }

    public static TextureAttribute createBump(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Bump, region);
    }

    public static TextureAttribute createDiffuse(Texture texture) {
        return new TextureAttribute(TextureAttribute.Diffuse, texture);
    }

    public static TextureAttribute createDiffuse(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Diffuse, region);
    }

    public static TextureAttribute createEmissive(Texture texture) {
        return new TextureAttribute(TextureAttribute.Emissive, texture);
    }

    public static TextureAttribute createEmissive(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Emissive, region);
    }

    public static TextureAttribute createNormal(Texture texture) {
        return new TextureAttribute(TextureAttribute.Normal, texture);
    }

    public static TextureAttribute createNormal(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Normal, region);
    }

    public static TextureAttribute createReflection(Texture texture) {
        return new TextureAttribute(TextureAttribute.Reflection, texture);
    }

    public static TextureAttribute createReflection(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Reflection, region);
    }

    public static TextureAttribute createSpecular(Texture texture) {
        return new TextureAttribute(TextureAttribute.Specular, texture);
    }

    public static TextureAttribute createSpecular(TextureRegion region) {
        return new TextureAttribute(TextureAttribute.Specular, region);
    }

    public int hashCode() {
        return (((((super.hashCode() * 991 + this.textureDescription.hashCode()) * 991 + NumberUtils.floatToRawIntBits(this.offsetU)) * 991 + NumberUtils.floatToRawIntBits(this.offsetV)) * 991 + NumberUtils.floatToRawIntBits(this.scaleU)) * 991 + NumberUtils.floatToRawIntBits(this.scaleV)) * 991 + this.uvIndex;
    }

    public static final boolean is(long mask) {
        boolean v0;
        if((TextureAttribute.Mask & mask) != 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void set(TextureRegion region) {
        this.textureDescription.texture = region.getTexture();
        this.offsetU = region.getU();
        this.offsetV = region.getV();
        this.scaleU = region.getU2() - this.offsetU;
        this.scaleV = region.getV2() - this.offsetV;
    }
}

