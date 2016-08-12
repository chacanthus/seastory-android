// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DepthShader extends DefaultShader {
    public class Config extends com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Config {
        public float defaultAlphaTest;
        public boolean depthBufferOnly;

        public Config() {
            super();
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
            this.defaultCullFace = 1028;
        }

        public Config(String vertexShader, String fragmentShader) {
            super(vertexShader, fragmentShader);
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
        }
    }

    private final FloatAttribute alphaTestAttribute;
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    public final int numBones;
    private static final Attributes tmpAttributes;
    public final int weights;

    static  {
        DepthShader.defaultVertexShader = null;
        DepthShader.defaultFragmentShader = null;
        DepthShader.tmpAttributes = new Attributes();
    }

    public DepthShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DepthShader(Renderable renderable, Config config) {
        this(renderable, config, DepthShader.createPrefix(renderable, config));
    }

    public DepthShader(Renderable renderable, Config config, String prefix) {
        String v5;
        String v4;
        if(config.vertexShader != null) {
            v4 = config.vertexShader;
        }
        else {
            v4 = DepthShader.getDefaultVertexShader();
        }

        if(config.fragmentShader != null) {
            v5 = config.fragmentShader;
        }
        else {
            v5 = DepthShader.getDefaultFragmentShader();
        }

        this(renderable, config, prefix, v4, v5);
    }

    public DepthShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        int v5;
        super(renderable, ((com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Config)config), shaderProgram);
        DepthShader.combineAttributes(renderable);
        if(renderable.bones == null) {
            v5 = 0;
        }
        else {
            v5 = config.numBones;
        }

        this.numBones = v5;
        int v4 = 0;
        int v3 = renderable.mesh.getVertexAttributes().size();
        int v2;
        for(v2 = 0; v2 < v3; ++v2) {
            VertexAttribute v0 = renderable.mesh.getVertexAttributes().get(v2);
            if(v0.usage == 64) {
                v4 |= 1 << v0.unit;
            }
        }

        this.weights = v4;
        this.alphaTestAttribute = new FloatAttribute(FloatAttribute.AlphaTest, config.defaultAlphaTest);
    }

    public DepthShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
    }

    public boolean canRender(Renderable renderable) {
        boolean v6;
        int v6_1;
        int v4;
        Attributes v1 = DepthShader.combineAttributes(renderable);
        if(!v1.has(BlendingAttribute.Type)) {
        label_24:
            if((renderable.mesh.getVertexAttributes().getMask() & 64) == 64) {
                v4 = 1;
            }
            else {
                v4 = 0;
            }

            if(this.numBones > 0) {
                v6_1 = 1;
            }
            else {
                v6_1 = 0;
            }

            if(v4 == v6_1) {
                goto label_42;
            }

            v6 = false;
            goto label_10;
        label_42:
            if(v4 != 0) {
                goto label_45;
            }

            v6 = true;
            goto label_10;
        label_45:
            int v5 = 0;
            int v3 = renderable.mesh.getVertexAttributes().size();
            int v2;
            for(v2 = 0; v2 < v3; ++v2) {
                VertexAttribute v0 = renderable.mesh.getVertexAttributes().get(v2);
                if(v0.usage == 64) {
                    v5 |= 1 << v0.unit;
                }
            }

            if(v5 != this.weights) {
                goto label_67;
            }

            v6 = true;
            goto label_10;
        label_67:
            v6 = false;
        }
        else if((this.attributesMask & BlendingAttribute.Type) != BlendingAttribute.Type) {
            v6 = false;
        }
        else {
            boolean v7 = v1.has(TextureAttribute.Diffuse);
            if((this.attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse) {
                v6 = true;
            }
            else {
                v6 = false;
            }

            if(v7 == v6) {
                goto label_24;
            }

            v6 = false;
        }

    label_10:
        return v6;
    }

    private static final Attributes combineAttributes(Renderable renderable) {
        DepthShader.tmpAttributes.clear();
        if(renderable.environment != null) {
            DepthShader.tmpAttributes.set(renderable.environment);
        }

        if(renderable.material != null) {
            DepthShader.tmpAttributes.set(renderable.material);
        }

        return DepthShader.tmpAttributes;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String v0 = DefaultShader.createPrefix(renderable, ((com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Config)config));
        if(!config.depthBufferOnly) {
            v0 = v0 + "#define PackedDepthFlag\n";
        }

        return v0;
    }

    public void end() {
        super.end();
    }

    public static final String getDefaultFragmentShader() {
        if(DepthShader.defaultFragmentShader == null) {
            DepthShader.defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.fragment.glsl").readString();
        }

        return DepthShader.defaultFragmentShader;
    }

    public static final String getDefaultVertexShader() {
        if(DepthShader.defaultVertexShader == null) {
            DepthShader.defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.vertex.glsl").readString();
        }

        return DepthShader.defaultVertexShader;
    }

    public void render(Renderable renderable, Attributes combinedAttributes) {
        if(combinedAttributes.has(BlendingAttribute.Type)) {
            Attribute v0 = combinedAttributes.get(BlendingAttribute.Type);
            combinedAttributes.remove(BlendingAttribute.Type);
            boolean v1 = combinedAttributes.has(FloatAttribute.AlphaTest);
            if(!v1) {
                combinedAttributes.set(this.alphaTestAttribute);
            }

            if(((BlendingAttribute)v0).opacity >= combinedAttributes.get(FloatAttribute.AlphaTest).value) {
                super.render(renderable, combinedAttributes);
            }

            if(!v1) {
                combinedAttributes.remove(FloatAttribute.AlphaTest);
            }

            combinedAttributes.set(v0);
        }
        else {
            super.render(renderable, combinedAttributes);
        }
    }
}

