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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntIntMap;

public abstract class BaseShader implements Shader {
    public abstract class GlobalSetter implements Setter {
        public GlobalSetter() {
            super();
        }

        public boolean isGlobal(BaseShader shader, int inputID) {
            return 1;
        }
    }

    public abstract class LocalSetter implements Setter {
        public LocalSetter() {
            super();
        }

        public boolean isGlobal(BaseShader shader, int inputID) {
            return 0;
        }
    }

    public abstract interface Setter {
        public abstract boolean isGlobal(BaseShader arg0, int arg1);

        public abstract void set(BaseShader arg0, int arg1, Renderable arg2, Attributes arg3);
    }

    public class Uniform implements Validator {
        public final String alias;
        public final long environmentMask;
        public final long materialMask;
        public final long overallMask;

        public Uniform(String alias) {
            super(alias, 0, 0);
        }

        public Uniform(String alias, long materialMask, long environmentMask) {
            super(alias, materialMask, environmentMask, 0);
        }

        public Uniform(String alias, long overallMask) {
            super(alias, 0, 0, overallMask);
        }

        public Uniform(String alias, long materialMask, long environmentMask, long overallMask) {
            super();
            this.alias = alias;
            this.materialMask = materialMask;
            this.environmentMask = environmentMask;
            this.overallMask = overallMask;
        }

        public boolean validate(BaseShader shader, int inputID, Renderable renderable) {
            boolean v4;
            long v2;
            long v0 = 0;
            if(renderable == null || renderable.material == null) {
                v2 = v0;
            }
            else {
                v2 = renderable.material.getMask();
            }

            if(renderable != null && renderable.environment != null) {
                v0 = renderable.environment.getMask();
            }

            if((this.materialMask & v2) != this.materialMask || (this.environmentMask & v0) != this.environmentMask || ((v2 | v0) & this.overallMask) != this.overallMask) {
                v4 = false;
            }
            else {
                v4 = true;
            }

            return v4;
        }
    }

    public abstract interface Validator {
        public abstract boolean validate(BaseShader arg0, int arg1, Renderable arg2);
    }

    private final IntIntMap attributes;
    public Camera camera;
    private Attributes combinedAttributes;
    public RenderContext context;
    private Mesh currentMesh;
    private final IntArray globalUniforms;
    private final IntArray localUniforms;
    private int[] locations;
    public ShaderProgram program;
    private final Array setters;
    private final IntArray tempArray;
    private final Array uniforms;
    private final Array validators;

    public BaseShader() {
        super();
        this.uniforms = new Array();
        this.validators = new Array();
        this.setters = new Array();
        this.globalUniforms = new IntArray();
        this.localUniforms = new IntArray();
        this.attributes = new IntIntMap();
        this.tempArray = new IntArray();
        this.combinedAttributes = new Attributes();
    }

    public void begin(Camera camera, RenderContext context) {
        Mesh v4 = null;
        this.camera = camera;
        this.context = context;
        this.program.begin();
        this.currentMesh = v4;
        int v0;
        for(v0 = 0; v0 < this.globalUniforms.size; ++v0) {
            Array v2 = this.setters;
            int v1 = this.globalUniforms.get(v0);
            if(v2.get(v1) != null) {
                this.setters.get(v1).set(this, v1, ((Renderable)v4), ((Attributes)v4));
            }
        }
    }

    public void dispose() {
        this.program = null;
        this.uniforms.clear();
        this.validators.clear();
        this.setters.clear();
        this.localUniforms.clear();
        this.globalUniforms.clear();
        this.locations = null;
    }

    public void end() {
        if(this.currentMesh != null) {
            this.currentMesh.unbind(this.program, this.tempArray.items);
            this.currentMesh = null;
        }

        this.program.end();
    }

    private final int[] getAttributeLocations(VertexAttributes attrs) {
        this.tempArray.clear();
        int v1 = attrs.size();
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            this.tempArray.add(this.attributes.get(attrs.get(v0).getKey(), -1));
        }

        return this.tempArray.items;
    }

    public String getUniformAlias(int id) {
        return this.uniforms.get(id);
    }

    public int getUniformID(String alias) {
        int v1 = this.uniforms.size;
        int v0 = 0;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(!this.uniforms.get(v0).equals(alias)) {
                ++v0;
                continue;
            }

            goto label_8;
        }

        v0 = -1;
    label_8:
        return v0;
    }

    public final boolean has(int inputID) {
        boolean v0;
        if(inputID < 0 || inputID >= this.locations.length || this.locations[inputID] < 0) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public void init(ShaderProgram program, Renderable renderable) {
        Object v11 = null;
        if(this.locations != null) {
            throw new GdxRuntimeException("Already initialized");
        }

        if(!program.isCompiled()) {
            throw new GdxRuntimeException(program.getLog());
        }

        this.program = program;
        int v6 = this.uniforms.size;
        this.locations = new int[v6];
        int v3;
        for(v3 = 0; v3 < v6; ++v3) {
            Object v4 = this.uniforms.get(v3);
            Object v8 = this.validators.get(v3);
            Object v7 = this.setters.get(v3);
            if(v8 == null || (((Validator)v8).validate(this, v3, renderable))) {
                this.locations[v3] = program.fetchUniformLocation(((String)v4), false);
                if(this.locations[v3] >= 0 && v7 != null) {
                    if(((Setter)v7).isGlobal(this, v3)) {
                        this.globalUniforms.add(v3);
                    }
                    else {
                        this.localUniforms.add(v3);
                    }
                }
            }
            else {
                this.locations[v3] = -1;
            }

            if(this.locations[v3] < 0) {
                this.validators.set(v3, v11);
                this.setters.set(v3, v11);
            }
        }

        if(renderable != null) {
            VertexAttributes v1 = renderable.mesh.getVertexAttributes();
            int v2 = v1.size();
            for(v3 = 0; v3 < v2; ++v3) {
                VertexAttribute v0 = v1.get(v3);
                int v5 = program.getAttributeLocation(v0.alias);
                if(v5 >= 0) {
                    this.attributes.put(v0.getKey(), v5);
                }
            }
        }
    }

    public final int loc(int inputID) {
        int v0;
        if(inputID < 0 || inputID >= this.locations.length) {
            v0 = -1;
        }
        else {
            v0 = this.locations[inputID];
        }

        return v0;
    }

    public int register(Uniform uniform) {
        return this.register(uniform, null);
    }

    public int register(Uniform uniform, Setter setter) {
        return this.register(uniform.alias, ((Validator)uniform), setter);
    }

    public int register(String alias, Validator validator, Setter setter) {
        if(this.locations != null) {
            throw new GdxRuntimeException("Cannot register an uniform after initialization");
        }

        int v0 = this.getUniformID(alias);
        if(v0 >= 0) {
            this.validators.set(v0, validator);
            this.setters.set(v0, setter);
        }
        else {
            this.uniforms.add(alias);
            this.validators.add(validator);
            this.setters.add(setter);
            v0 = this.uniforms.size - 1;
        }

        return v0;
    }

    public int register(String alias) {
        return this.register(alias, null, null);
    }

    public int register(String alias, Setter setter) {
        return this.register(alias, null, setter);
    }

    public int register(String alias, Validator validator) {
        return this.register(alias, validator, null);
    }

    public void render(Renderable renderable) {
        if(renderable.worldTransform.det3x3() != 0f) {
            this.combinedAttributes.clear();
            if(renderable.environment != null) {
                this.combinedAttributes.set(renderable.environment);
            }

            if(renderable.material != null) {
                this.combinedAttributes.set(renderable.material);
            }

            this.render(renderable, this.combinedAttributes);
        }
    }

    public void render(Renderable renderable, Attributes combinedAttributes) {
        int v6;
        for(v6 = 0; v6 < this.localUniforms.size; ++v6) {
            Array v0 = this.setters;
            int v7 = this.localUniforms.get(v6);
            if(v0.get(v7) != null) {
                this.setters.get(v7).set(this, v7, renderable, combinedAttributes);
            }
        }

        if(this.currentMesh != renderable.mesh) {
            if(this.currentMesh != null) {
                this.currentMesh.unbind(this.program, this.tempArray.items);
            }

            this.currentMesh = renderable.mesh;
            this.currentMesh.bind(this.program, this.getAttributeLocations(renderable.mesh.getVertexAttributes()));
        }

        renderable.mesh.render(this.program, renderable.primitiveType, renderable.meshPartOffset, renderable.meshPartSize, false);
    }

    public final boolean set(int uniform, Vector3 value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, float value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, Matrix4 value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformMatrix(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, float v1, float v2) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], v1, v2);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, float v1, float v2, float v3) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], v1, v2, v3);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, float v1, float v2, float v3, float v4) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], v1, v2, v3, v4);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, int value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, int v1, int v2) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], v1, v2);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, int v1, int v2, int v3) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], v1, v2, v3);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, int v1, int v2, int v3, int v4) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], v1, v2, v3, v4);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, Color value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, GLTexture texture) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], this.context.textureBinder.bind(texture));
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, TextureDescriptor textureDesc) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformi(this.locations[uniform], this.context.textureBinder.bind(textureDesc));
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, Matrix3 value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformMatrix(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }

    public final boolean set(int uniform, Vector2 value) {
        boolean v0;
        if(this.locations[uniform] < 0) {
            v0 = false;
        }
        else {
            this.program.setUniformf(this.locations[uniform], value);
            v0 = true;
        }

        return v0;
    }
}

