// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

public class ShaderProgram implements Disposable {
    public static final String BINORMAL_ATTRIBUTE = "a_binormal";
    public static final String COLOR_ATTRIBUTE = "a_color";
    public static final String NORMAL_ATTRIBUTE = "a_normal";
    public static final String POSITION_ATTRIBUTE = "a_position";
    public static final String TANGENT_ATTRIBUTE = "a_tangent";
    public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
    private String[] attributeNames;
    private final ObjectIntMap attributeSizes;
    private final ObjectIntMap attributeTypes;
    private final ObjectIntMap attributes;
    private int fragmentShaderHandle;
    private final String fragmentShaderSource;
    static final IntBuffer intbuf;
    private boolean invalidated;
    private boolean isCompiled;
    private String log;
    private final FloatBuffer matrix;
    IntBuffer params;
    public static boolean pedantic;
    private int program;
    private int refCount;
    private static final ObjectMap shaders;
    IntBuffer type;
    private String[] uniformNames;
    private final ObjectIntMap uniformSizes;
    private final ObjectIntMap uniformTypes;
    private final ObjectIntMap uniforms;
    private int vertexShaderHandle;
    private final String vertexShaderSource;

    static  {
        ShaderProgram.pedantic = true;
        ShaderProgram.shaders = new ObjectMap();
        ShaderProgram.intbuf = BufferUtils.newIntBuffer(1);
    }

    public ShaderProgram(String vertexShader, String fragmentShader) {
        super();
        this.log = "";
        this.uniforms = new ObjectIntMap();
        this.uniformTypes = new ObjectIntMap();
        this.uniformSizes = new ObjectIntMap();
        this.attributes = new ObjectIntMap();
        this.attributeTypes = new ObjectIntMap();
        this.attributeSizes = new ObjectIntMap();
        this.refCount = 0;
        this.params = BufferUtils.newIntBuffer(1);
        this.type = BufferUtils.newIntBuffer(1);
        if(vertexShader == null) {
            throw new IllegalArgumentException("vertex shader must not be null");
        }

        if(fragmentShader == null) {
            throw new IllegalArgumentException("fragment shader must not be null");
        }

        this.vertexShaderSource = vertexShader;
        this.fragmentShaderSource = fragmentShader;
        this.matrix = BufferUtils.newFloatBuffer(16);
        this.compileShaders(vertexShader, fragmentShader);
        if(this.isCompiled()) {
            this.fetchAttributes();
            this.fetchUniforms();
            this.addManagedShader(Gdx.app, this);
        }
    }

    public ShaderProgram(FileHandle vertexShader, FileHandle fragmentShader) {
        this(vertexShader.readString(), fragmentShader.readString());
    }

    private void addManagedShader(Application app, ShaderProgram shaderProgram) {
        Object v0 = ShaderProgram.shaders.get(app);
        if(v0 == null) {
            Array v0_1 = new Array();
        }

        ((Array)v0).add(shaderProgram);
        ShaderProgram.shaders.put(app, v0);
    }

    public void begin() {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUseProgram(this.program);
    }

    private void checkManaged() {
        if(this.invalidated) {
            this.compileShaders(this.vertexShaderSource, this.fragmentShaderSource);
            this.invalidated = false;
        }
    }

    public static void clearAllShaderPrograms(Application app) {
        ShaderProgram.shaders.remove(app);
    }

    private void compileShaders(String vertexShader, String fragmentShader) {
        int v1 = -1;
        this.vertexShaderHandle = this.loadShader(35633, vertexShader);
        this.fragmentShaderHandle = this.loadShader(35632, fragmentShader);
        if(this.vertexShaderHandle == v1 || this.fragmentShaderHandle == v1) {
            this.isCompiled = false;
        }
        else {
            this.program = this.linkProgram();
            if(this.program == v1) {
                this.isCompiled = false;
            }
            else {
                this.isCompiled = true;
            }
        }
    }

    public void disableVertexAttribute(int location) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glDisableVertexAttribArray(location);
    }

    public void disableVertexAttribute(String name) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        int v1 = this.fetchAttributeLocation(name);
        if(v1 != -1) {
            v0.glDisableVertexAttribArray(v1);
        }
    }

    public void dispose() {
        GL20 v0 = Gdx.gl20;
        v0.glUseProgram(0);
        v0.glDeleteShader(this.vertexShaderHandle);
        v0.glDeleteShader(this.fragmentShaderHandle);
        v0.glDeleteProgram(this.program);
        if(ShaderProgram.shaders.get(Gdx.app) != null) {
            ShaderProgram.shaders.get(Gdx.app).removeValue(this, true);
        }
    }

    public void enableVertexAttribute(int location) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glEnableVertexAttribArray(location);
    }

    public void enableVertexAttribute(String name) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        int v1 = this.fetchAttributeLocation(name);
        if(v1 != -1) {
            v0.glEnableVertexAttribArray(v1);
        }
    }

    public void end() {
        Gdx.gl20.glUseProgram(0);
    }

    private int fetchAttributeLocation(String name) {
        GL20 v0 = Gdx.gl20;
        int v1 = this.attributes.get(name, -2);
        if(v1 == -2) {
            v1 = v0.glGetAttribLocation(this.program, name);
            this.attributes.put(name, v1);
        }

        return v1;
    }

    private void fetchAttributes() {
        this.params.clear();
        Gdx.gl20.glGetProgramiv(this.program, 35721, this.params);
        int v3 = this.params.get(0);
        this.attributeNames = new String[v3];
        int v0;
        for(v0 = 0; v0 < v3; ++v0) {
            this.params.clear();
            this.params.put(0, 1);
            this.type.clear();
            String v2 = Gdx.gl20.glGetActiveAttrib(this.program, v0, this.params, this.type);
            this.attributes.put(v2, Gdx.gl20.glGetAttribLocation(this.program, v2));
            this.attributeTypes.put(v2, this.type.get(0));
            this.attributeSizes.put(v2, this.params.get(0));
            this.attributeNames[v0] = v2;
        }
    }

    public int fetchUniformLocation(String name, boolean pedantic) {
        GL20 v0 = Gdx.gl20;
        int v1 = this.uniforms.get(name, -2);
        if(v1 == -2) {
            v1 = v0.glGetUniformLocation(this.program, name);
            if(v1 == -1 && (pedantic)) {
                throw new IllegalArgumentException("no uniform with name \'" + name + "\' in shader");
            }

            this.uniforms.put(name, v1);
        }

        return v1;
    }

    private int fetchUniformLocation(String name) {
        return this.fetchUniformLocation(name, ShaderProgram.pedantic);
    }

    private void fetchUniforms() {
        this.params.clear();
        Gdx.gl20.glGetProgramiv(this.program, 35718, this.params);
        int v3 = this.params.get(0);
        this.uniformNames = new String[v3];
        int v0;
        for(v0 = 0; v0 < v3; ++v0) {
            this.params.clear();
            this.params.put(0, 1);
            this.type.clear();
            String v2 = Gdx.gl20.glGetActiveUniform(this.program, v0, this.params, this.type);
            this.uniforms.put(v2, Gdx.gl20.glGetUniformLocation(this.program, v2));
            this.uniformTypes.put(v2, this.type.get(0));
            this.uniformSizes.put(v2, this.params.get(0));
            this.uniformNames[v0] = v2;
        }
    }

    public int getAttributeLocation(String name) {
        return this.attributes.get(name, -1);
    }

    public int getAttributeSize(String name) {
        return this.attributeSizes.get(name, 0);
    }

    public int getAttributeType(String name) {
        return this.attributeTypes.get(name, 0);
    }

    public String[] getAttributes() {
        return this.attributeNames;
    }

    public String getFragmentShaderSource() {
        return this.fragmentShaderSource;
    }

    public String getLog() {
        String v0;
        if(this.isCompiled) {
            this.log = Gdx.gl20.glGetProgramInfoLog(this.program);
            v0 = this.log;
        }
        else {
            v0 = this.log;
        }

        return v0;
    }

    public static String getManagedStatus() {
        StringBuilder v1 = new StringBuilder();
        v1.append("Managed shaders/app: { ");
        Iterator v3 = ShaderProgram.shaders.keys().iterator();
        while(v3.hasNext()) {
            v1.append(ShaderProgram.shaders.get(v3.next()).size);
            v1.append(" ");
        }

        v1.append("}");
        return v1.toString();
    }

    public int getUniformLocation(String name) {
        return this.uniforms.get(name, -1);
    }

    public int getUniformSize(String name) {
        return this.uniformSizes.get(name, 0);
    }

    public int getUniformType(String name) {
        return this.uniformTypes.get(name, 0);
    }

    public String[] getUniforms() {
        return this.uniformNames;
    }

    public String getVertexShaderSource() {
        return this.vertexShaderSource;
    }

    public boolean hasAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    public boolean hasUniform(String name) {
        return this.uniforms.containsKey(name);
    }

    public static void invalidateAllShaderPrograms(Application app) {
        if(Gdx.gl20 != null) {
            Object v1 = ShaderProgram.shaders.get(app);
            if(v1 != null) {
                int v0;
                for(v0 = 0; v0 < ((Array)v1).size; ++v0) {
                    ((Array)v1).get(v0).invalidated = true;
                    ((Array)v1).get(v0).checkManaged();
                }
            }
        }
    }

    public boolean isCompiled() {
        return this.isCompiled;
    }

    private int linkProgram() {
        int v5 = -1;
        GL20 v0 = Gdx.gl20;
        int v3 = v0.glCreateProgram();
        if(v3 == 0) {
            v3 = v5;
        }
        else {
            v0.glAttachShader(v3, this.vertexShaderHandle);
            v0.glAttachShader(v3, this.fragmentShaderHandle);
            v0.glLinkProgram(v3);
            ByteBuffer v4 = ByteBuffer.allocateDirect(4);
            v4.order(ByteOrder.nativeOrder());
            IntBuffer v1 = v4.asIntBuffer();
            v0.glGetProgramiv(v3, 35714, v1);
            if(v1.get(0) == 0) {
                this.log = Gdx.gl20.glGetProgramInfoLog(v3);
                v3 = v5;
            }
        }

        return v3;
    }

    private int loadShader(int type, String source) {
        int v5 = -1;
        GL20 v1 = Gdx.gl20;
        IntBuffer v3 = BufferUtils.newIntBuffer(1);
        int v4 = v1.glCreateShader(type);
        if(v4 == 0) {
            v4 = v5;
        }
        else {
            v1.glShaderSource(v4, source);
            v1.glCompileShader(v4);
            v1.glGetShaderiv(v4, 35713, v3);
            if(v3.get(0) == 0) {
                this.log = this.log + v1.glGetShaderInfoLog(v4);
                v4 = v5;
            }
        }

        return v4;
    }

    public void setAttributef(String name, float value1, float value2, float value3, float value4) {
        Gdx.gl20.glVertexAttrib4f(this.fetchAttributeLocation(name), value1, value2, value3, value4);
    }

    public void setUniform1fv(int location, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1fv(location, length, values, offset);
    }

    public void setUniform1fv(String name, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1fv(this.fetchUniformLocation(name), length, values, offset);
    }

    public void setUniform2fv(int location, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2fv(location, length / 2, values, offset);
    }

    public void setUniform2fv(String name, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2fv(this.fetchUniformLocation(name), length / 2, values, offset);
    }

    public void setUniform3fv(int location, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3fv(location, length / 3, values, offset);
    }

    public void setUniform3fv(String name, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3fv(this.fetchUniformLocation(name), length / 3, values, offset);
    }

    public void setUniform4fv(int location, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4fv(location, length / 4, values, offset);
    }

    public void setUniform4fv(String name, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4fv(this.fetchUniformLocation(name), length / 4, values, offset);
    }

    public void setUniformMatrix(String name, Matrix4 matrix) {
        this.setUniformMatrix(name, matrix, false);
    }

    public void setUniformMatrix(int location, Matrix3 matrix) {
        this.setUniformMatrix(location, matrix, false);
    }

    public void setUniformMatrix(int location, Matrix4 matrix) {
        this.setUniformMatrix(location, matrix, false);
    }

    public void setUniformMatrix(int location, Matrix3 matrix, boolean transpose) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniformMatrix3fv(location, 1, transpose, matrix.val, 0);
    }

    public void setUniformMatrix(int location, Matrix4 matrix, boolean transpose) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniformMatrix4fv(location, 1, transpose, matrix.val, 0);
    }

    public void setUniformMatrix(String name, Matrix3 matrix) {
        this.setUniformMatrix(name, matrix, false);
    }

    public void setUniformMatrix(String name, Matrix3 matrix, boolean transpose) {
        this.setUniformMatrix(this.fetchUniformLocation(name), matrix, transpose);
    }

    public void setUniformMatrix(String name, Matrix4 matrix, boolean transpose) {
        this.setUniformMatrix(this.fetchUniformLocation(name), matrix, transpose);
    }

    public void setUniformMatrix3fv(String name, FloatBuffer buffer, int count, boolean transpose) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        buffer.position(0);
        v0.glUniformMatrix3fv(this.fetchUniformLocation(name), count, transpose, buffer);
    }

    public void setUniformMatrix4fv(int location, float[] values, int offset, int length) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniformMatrix4fv(location, length / 16, false, values, offset);
    }

    public void setUniformMatrix4fv(String name, FloatBuffer buffer, int count, boolean transpose) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        buffer.position(0);
        v0.glUniformMatrix4fv(this.fetchUniformLocation(name), count, transpose, buffer);
    }

    public void setUniformMatrix4fv(String name, float[] values, int offset, int length) {
        this.setUniformMatrix4fv(this.fetchUniformLocation(name), values, offset, length);
    }

    public void setUniformf(int location, float value) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1f(location, value);
    }

    public void setUniformf(int location, float value1, float value2) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2f(location, value1, value2);
    }

    public void setUniformf(int location, float value1, float value2, float value3) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3f(location, value1, value2, value3);
    }

    public void setUniformf(int location, float value1, float value2, float value3, float value4) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4f(location, value1, value2, value3, value4);
    }

    public void setUniformf(int location, Color values) {
        this.setUniformf(location, values.r, values.g, values.b, values.a);
    }

    public void setUniformf(int location, Vector2 values) {
        this.setUniformf(location, values.x, values.y);
    }

    public void setUniformf(int location, Vector3 values) {
        this.setUniformf(location, values.x, values.y, values.z);
    }

    public void setUniformf(String name, float value) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1f(this.fetchUniformLocation(name), value);
    }

    public void setUniformf(String name, float value1, float value2) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2f(this.fetchUniformLocation(name), value1, value2);
    }

    public void setUniformf(String name, float value1, float value2, float value3) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3f(this.fetchUniformLocation(name), value1, value2, value3);
    }

    public void setUniformf(String name, float value1, float value2, float value3, float value4) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4f(this.fetchUniformLocation(name), value1, value2, value3, value4);
    }

    public void setUniformf(String name, Color values) {
        this.setUniformf(name, values.r, values.g, values.b, values.a);
    }

    public void setUniformf(String name, Vector2 values) {
        this.setUniformf(name, values.x, values.y);
    }

    public void setUniformf(String name, Vector3 values) {
        this.setUniformf(name, values.x, values.y, values.z);
    }

    public void setUniformi(String name, int value) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1i(this.fetchUniformLocation(name), value);
    }

    public void setUniformi(int location, int value) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform1i(location, value);
    }

    public void setUniformi(int location, int value1, int value2) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2i(location, value1, value2);
    }

    public void setUniformi(int location, int value1, int value2, int value3) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3i(location, value1, value2, value3);
    }

    public void setUniformi(int location, int value1, int value2, int value3, int value4) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4i(location, value1, value2, value3, value4);
    }

    public void setUniformi(String name, int value1, int value2) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform2i(this.fetchUniformLocation(name), value1, value2);
    }

    public void setUniformi(String name, int value1, int value2, int value3) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform3i(this.fetchUniformLocation(name), value1, value2, value3);
    }

    public void setUniformi(String name, int value1, int value2, int value3, int value4) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glUniform4i(this.fetchUniformLocation(name), value1, value2, value3, value4);
    }

    public void setVertexAttribute(int location, int size, int type, boolean normalize, int stride, int offset) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glVertexAttribPointer(location, size, type, normalize, stride, offset);
    }

    public void setVertexAttribute(int location, int size, int type, boolean normalize, int stride, Buffer buffer) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        v0.glVertexAttribPointer(location, size, type, normalize, stride, buffer);
    }

    public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, int offset) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        int v1 = this.fetchAttributeLocation(name);
        if(v1 != -1) {
            v0.glVertexAttribPointer(v1, size, type, normalize, stride, offset);
        }
    }

    public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, Buffer buffer) {
        GL20 v0 = Gdx.gl20;
        this.checkManaged();
        int v1 = this.fetchAttributeLocation(name);
        if(v1 != -1) {
            v0.glVertexAttribPointer(v1, size, type, normalize, stride, buffer);
        }
    }
}

