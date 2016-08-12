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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class ImmediateModeRenderer20 implements ImmediateModeRenderer {
    private final int colorOffset;
    private final int maxVertices;
    private final Mesh mesh;
    private final int normalOffset;
    private int numSetTexCoords;
    private final int numTexCoords;
    private int numVertices;
    private boolean ownsShader;
    private int primitiveType;
    private final Matrix4 projModelView;
    private ShaderProgram shader;
    private final String[] shaderUniformNames;
    private final int texCoordOffset;
    private int vertexIdx;
    private final int vertexSize;
    private final float[] vertices;

    public ImmediateModeRenderer20(int maxVertices, boolean hasNormals, boolean hasColors, int numTexCoords) {
        this(maxVertices, hasNormals, hasColors, numTexCoords, ImmediateModeRenderer20.createDefaultShader(hasNormals, hasColors, numTexCoords));
        this.ownsShader = true;
    }

    public ImmediateModeRenderer20(int maxVertices, boolean hasNormals, boolean hasColors, int numTexCoords, ShaderProgram shader) {
        int v2;
        int v6 = 16;
        int v5 = 8;
        int v4 = 4;
        int v3 = 0;
        super();
        this.projModelView = new Matrix4();
        this.maxVertices = maxVertices;
        this.numTexCoords = numTexCoords;
        this.shader = shader;
        this.mesh = new Mesh(false, maxVertices, 0, this.buildVertexAttributes(hasNormals, hasColors, numTexCoords));
        this.vertices = new float[this.mesh.getVertexAttributes().vertexSize / 4 * maxVertices];
        this.vertexSize = this.mesh.getVertexAttributes().vertexSize / 4;
        if(this.mesh.getVertexAttribute(v5) != null) {
            v2 = this.mesh.getVertexAttribute(v5).offset / 4;
        }
        else {
            v2 = 0;
        }

        this.normalOffset = v2;
        if(this.mesh.getVertexAttribute(v4) != null) {
            v2 = this.mesh.getVertexAttribute(v4).offset / 4;
        }
        else {
            v2 = 0;
        }

        this.colorOffset = v2;
        if(this.mesh.getVertexAttribute(v6) != null) {
            v3 = this.mesh.getVertexAttribute(v6).offset / 4;
        }

        this.texCoordOffset = v3;
        this.shaderUniformNames = new String[numTexCoords];
        int v1;
        for(v1 = 0; v1 < numTexCoords; ++v1) {
            this.shaderUniformNames[v1] = "u_sampler" + v1;
        }
    }

    public ImmediateModeRenderer20(boolean hasNormals, boolean hasColors, int numTexCoords) {
        this(5000, hasNormals, hasColors, numTexCoords, ImmediateModeRenderer20.createDefaultShader(hasNormals, hasColors, numTexCoords));
        this.ownsShader = true;
    }

    public void begin(Matrix4 projModelView, int primitiveType) {
        this.projModelView.set(projModelView);
        this.primitiveType = primitiveType;
    }

    private VertexAttribute[] buildVertexAttributes(boolean hasNormals, boolean hasColor, int numTexCoords) {
        int v7 = 4;
        int v6 = 3;
        Array v1 = new Array();
        v1.add(new VertexAttribute(1, v6, "a_position"));
        if(hasNormals) {
            v1.add(new VertexAttribute(8, v6, "a_normal"));
        }

        if(hasColor) {
            v1.add(new VertexAttribute(v7, v7, "a_color"));
        }

        int v2;
        for(v2 = 0; v2 < numTexCoords; ++v2) {
            v1.add(new VertexAttribute(16, 2, "a_texCoord" + v2));
        }

        VertexAttribute[] v0 = new VertexAttribute[v1.size];
        for(v2 = 0; v2 < v1.size; ++v2) {
            v0[v2] = v1.get(v2);
        }

        return v0;
    }

    public void color(float colorBits) {
        this.vertices[this.vertexIdx + this.colorOffset] = colorBits;
    }

    public void color(float r, float g, float b, float a) {
        this.vertices[this.vertexIdx + this.colorOffset] = Color.toFloatBits(r, g, b, a);
    }

    public void color(Color color) {
        this.vertices[this.vertexIdx + this.colorOffset] = color.toFloatBits();
    }

    public static ShaderProgram createDefaultShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        return new ShaderProgram(ImmediateModeRenderer20.createVertexShader(hasNormals, hasColors, numTexCoords), ImmediateModeRenderer20.createFragmentShader(hasNormals, hasColors, numTexCoords));
    }

    private static String createFragmentShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        String v2;
        String v1 = "#ifdef GL_ES\nprecision mediump float;\n#endif\n";
        if(hasColors) {
            v1 = v1 + "varying vec4 v_col;\n";
        }

        int v0;
        for(v0 = 0; v0 < numTexCoords; ++v0) {
            v1 = new StringBuilder().append(v1).append("varying vec2 v_tex").append(v0).append(";\n").toString() + "uniform sampler2D u_sampler" + v0 + ";\n";
        }

        StringBuilder v3 = new StringBuilder().append(v1).append("void main() {\n   gl_FragColor = ");
        if(hasColors) {
            v2 = "v_col";
        }
        else {
            v2 = "vec4(1, 1, 1, 1)";
        }

        v1 = v3.append(v2).toString();
        if(numTexCoords > 0) {
            v1 = v1 + " * ";
        }

        for(v0 = 0; v0 < numTexCoords; ++v0) {
            if(v0 == numTexCoords - 1) {
                v1 = v1 + " texture2D(u_sampler" + v0 + ",  v_tex" + v0 + ")";
            }
            else {
                v1 = v1 + " texture2D(u_sampler" + v0 + ",  v_tex" + v0 + ") *";
            }
        }

        return v1 + ";\n}";
    }

    private static String createVertexShader(boolean hasNormals, boolean hasColors, int numTexCoords) {
        String v2;
        StringBuilder v3 = new StringBuilder().append("attribute vec4 a_position;\n");
        if(hasNormals) {
            v2 = "attribute vec3 a_normal;\n";
        }
        else {
            v2 = "";
        }

        v3 = v3.append(v2);
        if(hasColors) {
            v2 = "attribute vec4 a_color;\n";
        }
        else {
            v2 = "";
        }

        String v1 = v3.append(v2).toString();
        int v0;
        for(v0 = 0; v0 < numTexCoords; ++v0) {
            v1 = v1 + "attribute vec2 a_texCoord" + v0 + ";\n";
        }

        v3 = new StringBuilder().append(v1 + "uniform mat4 u_projModelView;\n");
        if(hasColors) {
            v2 = "varying vec4 v_col;\n";
        }
        else {
            v2 = "";
        }

        v1 = v3.append(v2).toString();
        for(v0 = 0; v0 < numTexCoords; ++v0) {
            v1 = v1 + "varying vec2 v_tex" + v0 + ";\n";
        }

        v3 = new StringBuilder().append(v1).append("void main() {\n   gl_Position = u_projModelView * a_position;\n");
        if(hasColors) {
            v2 = "   v_col = a_color;\n";
        }
        else {
            v2 = "";
        }

        v1 = v3.append(v2).toString();
        for(v0 = 0; v0 < numTexCoords; ++v0) {
            v1 = v1 + "   v_tex" + v0 + " = " + "a_texCoord" + v0 + ";\n";
        }

        return new StringBuilder().append(v1).append("   gl_PointSize = 1.0;\n").toString() + "}\n";
    }

    public void dispose() {
        if((this.ownsShader) && this.shader != null) {
            this.shader.dispose();
        }

        this.mesh.dispose();
    }

    public void end() {
        this.flush();
    }

    public void flush() {
        if(this.numVertices != 0) {
            this.shader.begin();
            this.shader.setUniformMatrix("u_projModelView", this.projModelView);
            int v0;
            for(v0 = 0; v0 < this.numTexCoords; ++v0) {
                this.shader.setUniformi(this.shaderUniformNames[v0], v0);
            }

            this.mesh.setVertices(this.vertices, 0, this.vertexIdx);
            this.mesh.render(this.shader, this.primitiveType);
            this.shader.end();
            this.numSetTexCoords = 0;
            this.vertexIdx = 0;
            this.numVertices = 0;
        }
    }

    public int getMaxVertices() {
        return this.maxVertices;
    }

    public int getNumVertices() {
        return this.numVertices;
    }

    public void normal(float x, float y, float z) {
        int v0 = this.vertexIdx + this.normalOffset;
        this.vertices[v0] = x;
        this.vertices[v0 + 1] = y;
        this.vertices[v0 + 2] = z;
    }

    public void setShader(ShaderProgram shader) {
        if(this.ownsShader) {
            this.shader.dispose();
        }

        this.shader = shader;
        this.ownsShader = false;
    }

    public void texCoord(float u, float v) {
        int v0 = this.vertexIdx + this.texCoordOffset;
        this.vertices[this.numSetTexCoords + v0] = u;
        this.vertices[this.numSetTexCoords + v0 + 1] = v;
        this.numSetTexCoords += 2;
    }

    public void vertex(float x, float y, float z) {
        int v0 = this.vertexIdx;
        this.vertices[v0] = x;
        this.vertices[v0 + 1] = y;
        this.vertices[v0 + 2] = z;
        this.numSetTexCoords = 0;
        this.vertexIdx += this.vertexSize;
        ++this.numVertices;
    }
}

