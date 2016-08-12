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

import com.badlogic.gdx.Application$ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader$Setter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader$Uniform;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class ParticleShader extends BaseShader {
    public enum AlignMode {
        static  {
            AlignMode.Screen = new AlignMode("Screen", 0);
            AlignMode.ViewPoint = new AlignMode("ViewPoint", 1);
            AlignMode[] v0 = new AlignMode[2];
            v0[0] = AlignMode.Screen;
            v0[1] = AlignMode.ViewPoint;
            AlignMode.$VALUES = v0;
        }

        private AlignMode(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static AlignMode valueOf(String name) {
            return Enum.valueOf(AlignMode.class, name);
        }

        public static AlignMode[] values() {
            return AlignMode.$VALUES.clone();
        }
    }

    public class Config {
        public AlignMode align;
        public int defaultCullFace;
        public int defaultDepthFunc;
        public String fragmentShader;
        public boolean ignoreUnimplemented;
        public ParticleType type;
        public String vertexShader;

        public Config() {
            super();
            this.vertexShader = null;
            this.fragmentShader = null;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.align = AlignMode.Screen;
            this.type = ParticleType.Billboard;
        }

        public Config(AlignMode align) {
            super();
            this.vertexShader = null;
            this.fragmentShader = null;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.align = AlignMode.Screen;
            this.type = ParticleType.Billboard;
            this.align = align;
        }

        public Config(AlignMode align, ParticleType type) {
            super();
            this.vertexShader = null;
            this.fragmentShader = null;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.align = AlignMode.Screen;
            this.type = ParticleType.Billboard;
            this.align = align;
            this.type = type;
        }

        public Config(ParticleType type) {
            super();
            this.vertexShader = null;
            this.fragmentShader = null;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.align = AlignMode.Screen;
            this.type = ParticleType.Billboard;
            this.type = type;
        }

        public Config(String vertexShader, String fragmentShader) {
            super();
            this.vertexShader = null;
            this.fragmentShader = null;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.align = AlignMode.Screen;
            this.type = ParticleType.Billboard;
            this.vertexShader = vertexShader;
            this.fragmentShader = fragmentShader;
        }
    }

    public class Inputs {
        public static final Uniform regionSize;

        static  {
            Inputs.cameraRight = new Uniform("u_cameraRight");
            Inputs.cameraInvDirection = new Uniform("u_cameraInvDirection");
            Inputs.screenWidth = new Uniform("u_screenWidth");
            Inputs.regionSize = new Uniform("u_regionSize");
        }

        public Inputs() {
            super();
        }
    }

    public enum ParticleType {
        public static final enum ParticleType Point;

        static  {
            ParticleType.Billboard = new ParticleType("Billboard", 0);
            ParticleType.Point = new ParticleType("Point", 1);
            ParticleType[] v0 = new ParticleType[2];
            v0[0] = ParticleType.Billboard;
            v0[1] = ParticleType.Point;
            ParticleType.$VALUES = v0;
        }

        private ParticleType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static ParticleType valueOf(String name) {
            return Enum.valueOf(ParticleType.class, name);
        }

        public static ParticleType[] values() {
            return ParticleType.$VALUES.clone();
        }
    }

    public class Setters {
        public static final Setter worldViewTrans;

        static  {
            Setters.cameraRight = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 1;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ParticleShader.TMP_VECTOR3.set(shader.camera.direction).crs(shader.camera.up).nor());
                }
            };
            Setters.cameraUp = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 1;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ParticleShader.TMP_VECTOR3.set(shader.camera.up).nor());
                }
            };
            Setters.cameraInvDirection = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 1;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ParticleShader.TMP_VECTOR3.set(-shader.camera.direction.x, -shader.camera.direction.y, -shader.camera.direction.z).nor());
                }
            };
            Setters.cameraPosition = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 1;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, shader.camera.position);
                }
            };
            Setters.screenWidth = new Setter() {
                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 1;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, ((float)Gdx.graphics.getWidth()));
                }
            };
            Setters.worldViewTrans = new Setter() {
                final Matrix4 temp;

                public boolean isGlobal(BaseShader shader, int inputID) {
                    return 0;
                }

                public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                    shader.set(inputID, this.temp.set(shader.camera.view).mul(renderable.worldTransform));
                }
            };
        }

        public Setters() {
            super();
        }
    }

    static final Vector3 TMP_VECTOR3;
    protected final Config config;
    Material currentMaterial;
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    protected static long implementedFlags;
    private long materialMask;
    private static final long optionalAttributes;
    private Renderable renderable;
    private long vertexMask;

    static  {
        ParticleShader.defaultVertexShader = null;
        ParticleShader.defaultFragmentShader = null;
        ParticleShader.implementedFlags = BlendingAttribute.Type | TextureAttribute.Diffuse;
        ParticleShader.TMP_VECTOR3 = new Vector3();
        ParticleShader.optionalAttributes = IntAttribute.CullFace | DepthTestAttribute.Type;
    }

    public ParticleShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public ParticleShader(Renderable renderable, Config config) {
        this(renderable, config, ParticleShader.createPrefix(renderable, config));
    }

    public ParticleShader(Renderable renderable, Config config, String prefix) {
        String v5;
        String v4;
        if(config.vertexShader != null) {
            v4 = config.vertexShader;
        }
        else {
            v4 = ParticleShader.getDefaultVertexShader();
        }

        if(config.fragmentShader != null) {
            v5 = config.fragmentShader;
        }
        else {
            v5 = ParticleShader.getDefaultFragmentShader();
        }

        this(renderable, config, prefix, v4, v5);
    }

    public ParticleShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        super();
        this.config = config;
        this.program = shaderProgram;
        this.renderable = renderable;
        this.materialMask = renderable.material.getMask() | ParticleShader.optionalAttributes;
        this.vertexMask = renderable.mesh.getVertexAttributes().getMask();
        if(!config.ignoreUnimplemented && (ParticleShader.implementedFlags & this.materialMask) != this.materialMask) {
            throw new GdxRuntimeException("Some attributes not implemented yet (" + this.materialMask + ")");
        }

        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.viewTrans, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.viewTrans);
        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.projViewTrans, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.projViewTrans);
        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.projTrans, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.projTrans);
        this.register(Inputs.screenWidth, Setters.screenWidth);
        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.cameraUp, Setters.cameraUp);
        this.register(Inputs.cameraRight, Setters.cameraRight);
        this.register(Inputs.cameraInvDirection, Setters.cameraInvDirection);
        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.cameraPosition, Setters.cameraPosition);
        this.register(com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs.diffuseTexture, com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.diffuseTexture);
    }

    public ParticleShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        this(renderable, config, new ShaderProgram(prefix + vertexShader, prefix + fragmentShader));
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
    }

    protected void bindMaterial(Renderable renderable) {
        Object v0;
        int v2;
        int v1;
        int v12 = -1;
        if(this.currentMaterial != renderable.material) {
            if(this.config.defaultCullFace == v12) {
                v1 = 1029;
            }
            else {
                v1 = this.config.defaultCullFace;
            }

            if(this.config.defaultDepthFunc == v12) {
                v2 = 515;
            }
            else {
                v2 = this.config.defaultDepthFunc;
            }

            float v5 = 0f;
            float v4 = 1f;
            boolean v3 = true;
            this.currentMaterial = renderable.material;
            Iterator v7 = this.currentMaterial.iterator();
            do {
            label_20:
                if(!v7.hasNext()) {
                    goto label_60;
                }

                v0 = v7.next();
                long v8 = ((Attribute)v0).type;
                if(!BlendingAttribute.is(v8)) {
                    goto label_39;
                }

                this.context.setBlending(true, v0.sourceFunction, ((BlendingAttribute)v0).destFunction);
                goto label_20;
            label_39:
                if((DepthTestAttribute.Type & v8) != DepthTestAttribute.Type) {
                    continue;
                }

                v2 = v0.depthFunc;
                v5 = v0.depthRangeNear;
                v4 = v0.depthRangeFar;
                v3 = v0.depthMask;
                goto label_20;
            }
            while(this.config.ignoreUnimplemented);

            throw new GdxRuntimeException("Unknown material attribute: " + ((Attribute)v0).toString());
        label_60:
            this.context.setCullFace(v1);
            this.context.setDepthTest(v2, v5, v4);
            this.context.setDepthMask(v3);
        }
    }

    public boolean canRender(Renderable renderable) {
        boolean v0;
        if(this.materialMask != (renderable.material.getMask() | ParticleShader.optionalAttributes) || this.vertexMask != renderable.mesh.getVertexAttributes().getMask()) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public int compareTo(Shader other) {
        int v0 = 0;
        if(other == null) {
            v0 = -1;
        }

        return v0;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String v0 = "";
        if(Gdx.app.getType() == ApplicationType.Desktop) {
            v0 = v0 + "#version 120\n";
        }
        else {
            v0 = v0 + "#version 100\n";
        }

        if(config.type == ParticleType.Billboard) {
            v0 = v0 + "#define billboard\n";
            if(config.align == AlignMode.Screen) {
                v0 = v0 + "#define screenFacing\n";
            }
            else if(config.align == AlignMode.ViewPoint) {
                v0 = v0 + "#define viewPointFacing\n";
            }
        }

        return v0;
    }

    public void dispose() {
        this.program.dispose();
        super.dispose();
    }

    public void end() {
        this.currentMaterial = null;
        super.end();
    }

    public boolean equals(ParticleShader obj) {
        boolean v0;
        if(obj == this) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean equals(Object obj) {
        boolean v0;
        if((obj instanceof ParticleShader)) {
            v0 = this.equals(((ParticleShader)obj));
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public int getDefaultCullFace() {
        int v0;
        if(this.config.defaultCullFace == -1) {
            v0 = 1029;
        }
        else {
            v0 = this.config.defaultCullFace;
        }

        return v0;
    }

    public int getDefaultDepthFunc() {
        int v0;
        if(this.config.defaultDepthFunc == -1) {
            v0 = 515;
        }
        else {
            v0 = this.config.defaultDepthFunc;
        }

        return v0;
    }

    public static String getDefaultFragmentShader() {
        if(ParticleShader.defaultFragmentShader == null) {
            ParticleShader.defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/particles/particles.fragment.glsl").readString();
        }

        return ParticleShader.defaultFragmentShader;
    }

    public static String getDefaultVertexShader() {
        if(ParticleShader.defaultVertexShader == null) {
            ParticleShader.defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/particles/particles.vertex.glsl").readString();
        }

        return ParticleShader.defaultVertexShader;
    }

    public void init() {
        ShaderProgram v0 = this.program;
        this.program = null;
        this.init(v0, this.renderable);
        this.renderable = null;
    }

    public void render(Renderable renderable) {
        if(!renderable.material.has(BlendingAttribute.Type)) {
            this.context.setBlending(false, 770, 771);
        }

        this.bindMaterial(renderable);
        super.render(renderable);
    }

    public void setDefaultCullFace(int cullFace) {
        this.config.defaultCullFace = cullFace;
    }

    public void setDefaultDepthFunc(int depthFunc) {
        this.config.defaultDepthFunc = depthFunc;
    }
}

