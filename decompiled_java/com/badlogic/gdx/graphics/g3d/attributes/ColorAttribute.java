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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ColorAttribute extends Attribute {
    public static final long Ambient = 0;
    public static final String AmbientAlias = "ambientColor";
    public static final String AmbientLightAlias = "ambientLightColor";
    public static final String DiffuseAlias = "diffuseColor";
    public static final String EmissiveAlias = "emissiveColor";
    public static final String FogAlias = "fogColor";
    protected static long Mask = 0;
    public static final String ReflectionAlias = "reflectionColor";
    public static final String SpecularAlias = "specularColor";

    static  {
        ColorAttribute.Diffuse = ColorAttribute.register("diffuseColor");
        ColorAttribute.Specular = ColorAttribute.register("specularColor");
        ColorAttribute.Ambient = ColorAttribute.register("ambientColor");
        ColorAttribute.Emissive = ColorAttribute.register("emissiveColor");
        ColorAttribute.Reflection = ColorAttribute.register("reflectionColor");
        ColorAttribute.AmbientLight = ColorAttribute.register("ambientLightColor");
        ColorAttribute.Fog = ColorAttribute.register("fogColor");
        ColorAttribute.Mask = ColorAttribute.Ambient | ColorAttribute.Diffuse | ColorAttribute.Specular | ColorAttribute.Emissive | ColorAttribute.Reflection | ColorAttribute.AmbientLight | ColorAttribute.Fog;
    }

    public ColorAttribute(long type, Color color) {
        this(type);
        if(color != null) {
            this.color.set(color);
        }
    }

    public ColorAttribute(long type) {
        super(type);
        this.color = new Color();
        if(!ColorAttribute.is(type)) {
            throw new GdxRuntimeException("Invalid type specified");
        }
    }

    public ColorAttribute(long type, float r, float g, float b, float a) {
        this(type);
        this.color.set(r, g, b, a);
    }

    public ColorAttribute(ColorAttribute copyFrom) {
        this(copyFrom.type, copyFrom.color);
    }

    public int compareTo(Attribute o) {
        int v0;
        if(this.type != o.type) {
            v0 = ((int)(this.type - o.type));
        }
        else {
            v0 = ((ColorAttribute)o).color.toIntBits() - this.color.toIntBits();
        }

        return v0;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((Attribute)x0));
    }

    public Attribute copy() {
        return new ColorAttribute(this);
    }

    public static final ColorAttribute createAmbient(float r, float g, float b, float a) {
        return new ColorAttribute(ColorAttribute.Ambient, r, g, b, a);
    }

    public static final ColorAttribute createAmbient(Color color) {
        return new ColorAttribute(ColorAttribute.Ambient, color);
    }

    public static final ColorAttribute createDiffuse(float r, float g, float b, float a) {
        return new ColorAttribute(ColorAttribute.Diffuse, r, g, b, a);
    }

    public static final ColorAttribute createDiffuse(Color color) {
        return new ColorAttribute(ColorAttribute.Diffuse, color);
    }

    public static final ColorAttribute createReflection(float r, float g, float b, float a) {
        return new ColorAttribute(ColorAttribute.Reflection, r, g, b, a);
    }

    public static final ColorAttribute createReflection(Color color) {
        return new ColorAttribute(ColorAttribute.Reflection, color);
    }

    public static final ColorAttribute createSpecular(float r, float g, float b, float a) {
        return new ColorAttribute(ColorAttribute.Specular, r, g, b, a);
    }

    public static final ColorAttribute createSpecular(Color color) {
        return new ColorAttribute(ColorAttribute.Specular, color);
    }

    public int hashCode() {
        return super.hashCode() * 953 + this.color.toIntBits();
    }

    public static final boolean is(long mask) {
        boolean v0;
        if((ColorAttribute.Mask & mask) != 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

