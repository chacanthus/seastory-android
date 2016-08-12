// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool$Poolable;

public abstract interface MeshPartBuilder {
    public class VertexInfo implements Poolable {
        public final Color color;
        public boolean hasColor;
        public boolean hasNormal;
        public boolean hasPosition;
        public boolean hasUV;
        public final Vector3 normal;
        public final Vector3 position;
        public final Vector2 uv;

        public VertexInfo() {
            super();
            this.position = new Vector3();
            this.normal = new Vector3(0f, 1f, 0f);
            this.color = new Color(1f, 1f, 1f, 1f);
            this.uv = new Vector2();
        }

        public VertexInfo lerp(VertexInfo target, float alpha) {
            if((this.hasPosition) && (target.hasPosition)) {
                this.position.lerp(target.position, alpha);
            }

            if((this.hasNormal) && (target.hasNormal)) {
                this.normal.lerp(target.normal, alpha);
            }

            if((this.hasColor) && (target.hasColor)) {
                this.color.lerp(target.color, alpha);
            }

            if((this.hasUV) && (target.hasUV)) {
                this.uv.lerp(target.uv, alpha);
            }

            return this;
        }

        public void reset() {
            this.position.set(0f, 0f, 0f);
            this.normal.set(0f, 1f, 0f);
            this.color.set(1f, 1f, 1f, 1f);
            this.uv.set(0f, 0f);
        }

        public VertexInfo set(VertexInfo other) {
            Vector3 v0 = null;
            if(other == null) {
                this = this.set(v0, v0, ((Color)v0), ((Vector2)v0));
            }
            else {
                this.hasPosition = other.hasPosition;
                this.position.set(other.position);
                this.hasNormal = other.hasNormal;
                this.normal.set(other.normal);
                this.hasColor = other.hasColor;
                this.color.set(other.color);
                this.hasUV = other.hasUV;
                this.uv.set(other.uv);
            }

            return this;
        }

        public VertexInfo set(Vector3 pos, Vector3 nor, Color col, Vector2 uv) {
            boolean v0;
            boolean v2 = false;
            this.reset();
            if(pos != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasPosition = v0;
            if(v0) {
                this.position.set(pos);
            }

            if(nor != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasNormal = v0;
            if(v0) {
                this.normal.set(nor);
            }

            if(col != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasColor = v0;
            if(v0) {
                this.color.set(col);
            }

            if(uv != null) {
                v2 = true;
            }

            this.hasUV = v2;
            if(v2) {
                this.uv.set(uv);
            }

            return this;
        }

        public VertexInfo setCol(float r, float g, float b, float a) {
            this.color.set(r, g, b, a);
            this.hasColor = true;
            return this;
        }

        public VertexInfo setCol(Color col) {
            boolean v0;
            if(col != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasColor = v0;
            if(v0) {
                this.color.set(col);
            }

            return this;
        }

        public VertexInfo setNor(float x, float y, float z) {
            this.normal.set(x, y, z);
            this.hasNormal = true;
            return this;
        }

        public VertexInfo setNor(Vector3 nor) {
            boolean v0;
            if(nor != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasNormal = v0;
            if(v0) {
                this.normal.set(nor);
            }

            return this;
        }

        public VertexInfo setPos(float x, float y, float z) {
            this.position.set(x, y, z);
            this.hasPosition = true;
            return this;
        }

        public VertexInfo setPos(Vector3 pos) {
            boolean v0;
            if(pos != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasPosition = v0;
            if(v0) {
                this.position.set(pos);
            }

            return this;
        }

        public VertexInfo setUV(float u, float v) {
            this.uv.set(u, v);
            this.hasUV = true;
            return this;
        }

        public VertexInfo setUV(Vector2 uv) {
            boolean v0;
            if(uv != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasUV = v0;
            if(v0) {
                this.uv.set(uv);
            }

            return this;
        }
    }

    public abstract void addMesh(Mesh arg0);

    public abstract void addMesh(Mesh arg0, int arg1, int arg2);

    public abstract void addMesh(MeshPart arg0);

    public abstract void arrow(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, int arg8);

    public abstract void box(float arg0, float arg1, float arg2);

    public abstract void box(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5);

    public abstract void box(VertexInfo arg0, VertexInfo arg1, VertexInfo arg2, VertexInfo arg3, VertexInfo arg4, VertexInfo arg5, VertexInfo arg6, VertexInfo arg7);

    public abstract void box(Matrix4 arg0);

    public abstract void box(Vector3 arg0, Vector3 arg1, Vector3 arg2, Vector3 arg3, Vector3 arg4, Vector3 arg5, Vector3 arg6, Vector3 arg7);

    public abstract void capsule(float arg0, float arg1, int arg2);

    public abstract void circle(float arg0, int arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7);

    public abstract void circle(float arg0, int arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9);

    public abstract void circle(float arg0, int arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13);

    public abstract void circle(float arg0, int arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float arg15);

    public abstract void circle(float arg0, int arg1, Vector3 arg2, Vector3 arg3);

    public abstract void circle(float arg0, int arg1, Vector3 arg2, Vector3 arg3, float arg4, float arg5);

    public abstract void circle(float arg0, int arg1, Vector3 arg2, Vector3 arg3, Vector3 arg4, Vector3 arg5);

    public abstract void circle(float arg0, int arg1, Vector3 arg2, Vector3 arg3, Vector3 arg4, Vector3 arg5, float arg6, float arg7);

    public abstract void cone(float arg0, float arg1, float arg2, int arg3);

    public abstract void cone(float arg0, float arg1, float arg2, int arg3, float arg4, float arg5);

    public abstract void cylinder(float arg0, float arg1, float arg2, int arg3);

    public abstract void cylinder(float arg0, float arg1, float arg2, int arg3, float arg4, float arg5);

    public abstract void cylinder(float arg0, float arg1, float arg2, int arg3, float arg4, float arg5, boolean arg6);

    public abstract void ellipse(float arg0, float arg1, float arg2, float arg3, int arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10);

    public abstract void ellipse(float arg0, float arg1, float arg2, float arg3, int arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12);

    public abstract void ellipse(float arg0, float arg1, float arg2, float arg3, int arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float arg15, float arg16, float arg17, float arg18);

    public abstract void ellipse(float arg0, float arg1, float arg2, float arg3, int arg4, Vector3 arg5, Vector3 arg6);

    public abstract void ellipse(float arg0, float arg1, int arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8);

    public abstract void ellipse(float arg0, float arg1, int arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10);

    public abstract void ellipse(float arg0, float arg1, int arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14);

    public abstract void ellipse(float arg0, float arg1, int arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float arg15, float arg16);

    public abstract void ellipse(float arg0, float arg1, int arg2, Vector3 arg3, Vector3 arg4);

    public abstract void ellipse(float arg0, float arg1, int arg2, Vector3 arg3, Vector3 arg4, float arg5, float arg6);

    public abstract void ellipse(float arg0, float arg1, int arg2, Vector3 arg3, Vector3 arg4, Vector3 arg5, Vector3 arg6);

    public abstract void ellipse(float arg0, float arg1, int arg2, Vector3 arg3, Vector3 arg4, Vector3 arg5, Vector3 arg6, float arg7, float arg8);

    public abstract VertexAttributes getAttributes();

    public abstract MeshPart getMeshPart();

    public abstract Matrix4 getVertexTransform(Matrix4 arg0);

    public abstract void index(short arg0);

    public abstract void index(short arg0, short arg1);

    public abstract void index(short arg0, short arg1, short arg2);

    public abstract void index(short arg0, short arg1, short arg2, short arg3);

    public abstract void index(short arg0, short arg1, short arg2, short arg3, short arg4, short arg5);

    public abstract void index(short arg0, short arg1, short arg2, short arg3, short arg4, short arg5, short arg6, short arg7);

    public abstract boolean isVertexTransformationEnabled();

    public abstract short lastIndex();

    public abstract void line(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5);

    public abstract void line(VertexInfo arg0, VertexInfo arg1);

    public abstract void line(Vector3 arg0, Color arg1, Vector3 arg2, Color arg3);

    public abstract void line(Vector3 arg0, Vector3 arg1);

    public abstract void line(short arg0, short arg1);

    public abstract void patch(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, int arg15, int arg16);

    public abstract void patch(VertexInfo arg0, VertexInfo arg1, VertexInfo arg2, VertexInfo arg3, int arg4, int arg5);

    public abstract void patch(Vector3 arg0, Vector3 arg1, Vector3 arg2, Vector3 arg3, Vector3 arg4, int arg5, int arg6);

    public abstract void rect(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14);

    public abstract void rect(VertexInfo arg0, VertexInfo arg1, VertexInfo arg2, VertexInfo arg3);

    public abstract void rect(Vector3 arg0, Vector3 arg1, Vector3 arg2, Vector3 arg3, Vector3 arg4);

    public abstract void rect(short arg0, short arg1, short arg2, short arg3);

    public abstract void setColor(float arg0, float arg1, float arg2, float arg3);

    public abstract void setColor(Color arg0);

    public abstract void setUVRange(float arg0, float arg1, float arg2, float arg3);

    public abstract void setUVRange(TextureRegion arg0);

    public abstract void setVertexTransform(Matrix4 arg0);

    public abstract void setVertexTransformationEnabled(boolean arg0);

    public abstract void sphere(float arg0, float arg1, float arg2, int arg3, int arg4);

    public abstract void sphere(float arg0, float arg1, float arg2, int arg3, int arg4, float arg5, float arg6, float arg7, float arg8);

    public abstract void sphere(Matrix4 arg0, float arg1, float arg2, float arg3, int arg4, int arg5);

    public abstract void sphere(Matrix4 arg0, float arg1, float arg2, float arg3, int arg4, int arg5, float arg6, float arg7, float arg8, float arg9);

    public abstract void triangle(VertexInfo arg0, VertexInfo arg1, VertexInfo arg2);

    public abstract void triangle(Vector3 arg0, Color arg1, Vector3 arg2, Color arg3, Vector3 arg4, Color arg5);

    public abstract void triangle(Vector3 arg0, Vector3 arg1, Vector3 arg2);

    public abstract void triangle(short arg0, short arg1, short arg2);

    public abstract short vertex(VertexInfo arg0);

    public abstract short vertex(Vector3 arg0, Vector3 arg1, Color arg2, Vector2 arg3);

    public abstract short vertex(float[] arg0);
}

