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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class ShapeRenderer implements Disposable {
    public enum ShapeType {
        public static final enum ShapeType Point;
        private final int glType;

        static  {
            ShapeType.Point = new ShapeType("Point", 0, 0);
            ShapeType.Line = new ShapeType("Line", 1, 1);
            ShapeType.Filled = new ShapeType("Filled", 2, 4);
            ShapeType[] v0 = new ShapeType[3];
            v0[0] = ShapeType.Point;
            v0[1] = ShapeType.Line;
            v0[2] = ShapeType.Filled;
            ShapeType.$VALUES = v0;
        }

        private ShapeType(String arg1, int arg2, int glType) {
            super(arg1, arg2);
            this.glType = glType;
        }

        public int getGlType() {
            return this.glType;
        }

        public static ShapeType valueOf(String name) {
            return Enum.valueOf(ShapeType.class, name);
        }

        public static ShapeType[] values() {
            return ShapeType.$VALUES.clone();
        }
    }

    private boolean autoShapeType;
    private final Color color;
    private final Matrix4 combinedMatrix;
    private float defaultRectLineWidth;
    private boolean matrixDirty;
    private final Matrix4 projectionMatrix;
    private final ImmediateModeRenderer renderer;
    private ShapeType shapeType;
    private final Vector2 tmp;
    private final Matrix4 transformMatrix;

    public ShapeRenderer() {
        this(5000);
    }

    public ShapeRenderer(int maxVertices) {
        this(maxVertices, null);
    }

    public ShapeRenderer(int maxVertices, ShaderProgram defaultShader) {
        super();
        this.matrixDirty = false;
        this.projectionMatrix = new Matrix4();
        this.transformMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.tmp = new Vector2();
        this.color = new Color(1f, 1f, 1f, 1f);
        this.defaultRectLineWidth = 0.75f;
        if(defaultShader == null) {
            this.renderer = new ImmediateModeRenderer20(maxVertices, false, true, 0);
        }
        else {
            this.renderer = new ImmediateModeRenderer20(maxVertices, false, true, 0, defaultShader);
        }

        this.projectionMatrix.setToOrtho2D(0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
        this.matrixDirty = true;
    }

    public void arc(float x, float y, float radius, float start, float degrees) {
        this.arc(x, y, radius, start, degrees, Math.max(1, ((int)(6f * (((float)Math.cbrt(((double)radius)))) * (degrees / 360f)))));
    }

    public void arc(float x, float y, float radius, float start, float degrees, int segments) {
        float v7;
        if(segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }

        float v1 = this.color.toFloatBits();
        float v8 = 6.283185f * (degrees / 360f) / (((float)segments));
        float v2 = MathUtils.cos(v8);
        float v6 = MathUtils.sin(v8);
        float v3 = radius * MathUtils.cos(0.017453f * start);
        float v4 = radius * MathUtils.sin(0.017453f * start);
        if(this.shapeType == ShapeType.Line) {
            this.check(ShapeType.Line, ShapeType.Filled, segments * 2 + 2);
            this.renderer.color(v1);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(v1);
            this.renderer.vertex(x + v3, y + v4, 0f);
            int v5;
            for(v5 = 0; v5 < segments; ++v5) {
                this.renderer.color(v1);
                this.renderer.vertex(x + v3, y + v4, 0f);
                v7 = v3;
                v3 = v2 * v3 - v6 * v4;
                v4 = v6 * v7 + v2 * v4;
                this.renderer.color(v1);
                this.renderer.vertex(x + v3, y + v4, 0f);
            }

            this.renderer.color(v1);
            this.renderer.vertex(x + v3, y + v4, 0f);
        }
        else {
            this.check(ShapeType.Line, ShapeType.Filled, segments * 3 + 3);
            for(v5 = 0; v5 < segments; ++v5) {
                this.renderer.color(v1);
                this.renderer.vertex(x, y, 0f);
                this.renderer.color(v1);
                this.renderer.vertex(x + v3, y + v4, 0f);
                v7 = v3;
                v3 = v2 * v3 - v6 * v4;
                v4 = v6 * v7 + v2 * v4;
                this.renderer.color(v1);
                this.renderer.vertex(x + v3, y + v4, 0f);
            }

            this.renderer.color(v1);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(v1);
            this.renderer.vertex(x + v3, y + v4, 0f);
        }

        this.renderer.color(v1);
        this.renderer.vertex(x + 0f, y + 0f, 0f);
    }

    public void begin(ShapeType type) {
        if(this.shapeType != null) {
            throw new IllegalStateException("Call end() before beginning a new shape batch.");
        }

        this.shapeType = type;
        if(this.matrixDirty) {
            this.combinedMatrix.set(this.projectionMatrix);
            Matrix4.mul(this.combinedMatrix.val, this.transformMatrix.val);
            this.matrixDirty = false;
        }

        this.renderer.begin(this.combinedMatrix, this.shapeType.getGlType());
    }

    public void begin() {
        if(!this.autoShapeType) {
            throw new IllegalStateException("autoShapeType must be true to use this method.");
        }

        this.begin(ShapeType.Line);
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        depth = -depth;
        float v0 = this.color.toFloatBits();
        if(this.shapeType == ShapeType.Line) {
            this.check(ShapeType.Line, ShapeType.Filled, 24);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
        }
        else {
            this.check(ShapeType.Line, ShapeType.Filled, 36);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, z);
        }
    }

    private void check(ShapeType preferred, ShapeType other, int newVertices) {
        ShapeType v0;
        if(this.shapeType == null) {
            throw new IllegalStateException("begin must be called first.");
        }

        if(this.shapeType == preferred || this.shapeType == other) {
            if(this.matrixDirty) {
                v0 = this.shapeType;
                this.end();
                this.begin(v0);
            }
            else if(this.renderer.getMaxVertices() - this.renderer.getNumVertices() < newVertices) {
                v0 = this.shapeType;
                this.end();
                this.begin(v0);
            }
        }
        else if(this.autoShapeType) {
            this.end();
            this.begin(preferred);
        }
        else if(other == null) {
            throw new IllegalStateException("Must call begin(ShapeType." + preferred + ").");
        }
        else {
            throw new IllegalStateException("Must call begin(ShapeType." + preferred + ") or begin(ShapeType." + other + ").");
        }
    }

    public void circle(float x, float y, float radius) {
        this.circle(x, y, radius, Math.max(1, ((int)(6f * (((float)Math.cbrt(((double)radius))))))));
    }

    public void circle(float x, float y, float radius, int segments) {
        float v8;
        if(segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }

        float v2 = this.color.toFloatBits();
        float v1 = 6.283185f / (((float)segments));
        float v3 = MathUtils.cos(v1);
        float v7 = MathUtils.sin(v1);
        float v4 = radius;
        float v5 = 0f;
        if(this.shapeType == ShapeType.Line) {
            this.check(ShapeType.Line, ShapeType.Filled, segments * 2 + 2);
            int v6;
            for(v6 = 0; v6 < segments; ++v6) {
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, 0f);
                v8 = v4;
                v4 = v3 * v4 - v7 * v5;
                v5 = v7 * v8 + v3 * v5;
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, 0f);
            }

            this.renderer.color(v2);
            this.renderer.vertex(x + v4, y + v5, 0f);
        }
        else {
            this.check(ShapeType.Line, ShapeType.Filled, segments * 3 + 3);
            --segments;
            for(v6 = 0; v6 < segments; ++v6) {
                this.renderer.color(v2);
                this.renderer.vertex(x, y, 0f);
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, 0f);
                v8 = v4;
                v4 = v3 * v4 - v7 * v5;
                v5 = v7 * v8 + v3 * v5;
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, 0f);
            }

            this.renderer.color(v2);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(v2);
            this.renderer.vertex(x + v4, y + v5, 0f);
        }

        this.renderer.color(v2);
        this.renderer.vertex(x + radius, y + 0f, 0f);
    }

    public void cone(float x, float y, float z, float radius, float height) {
        this.cone(x, y, z, radius, height, Math.max(1, ((int)(4f * (((float)Math.sqrt(((double)radius))))))));
    }

    public void cone(float x, float y, float z, float radius, float height, int segments) {
        float v9;
        float v8;
        if(segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }

        this.check(ShapeType.Line, ShapeType.Filled, segments * 4 + 2);
        float v2 = this.color.toFloatBits();
        float v1 = 6.283185f / (((float)segments));
        float v3 = MathUtils.cos(v1);
        float v7 = MathUtils.sin(v1);
        float v4 = radius;
        float v5 = 0f;
        if(this.shapeType == ShapeType.Line) {
            int v6;
            for(v6 = 0; v6 < segments; ++v6) {
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
                this.renderer.color(v2);
                this.renderer.vertex(x, y, z + height);
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
                v8 = v4;
                v4 = v3 * v4 - v7 * v5;
                v5 = v7 * v8 + v3 * v5;
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
            }

            this.renderer.color(v2);
            this.renderer.vertex(x + v4, y + v5, z);
        }
        else {
            --segments;
            for(v6 = 0; v6 < segments; ++v6) {
                this.renderer.color(v2);
                this.renderer.vertex(x, y, z);
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
                v8 = v4;
                v9 = v5;
                v4 = v3 * v4 - v7 * v5;
                v5 = v7 * v8 + v3 * v5;
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
                this.renderer.color(v2);
                this.renderer.vertex(x + v8, y + v9, z);
                this.renderer.color(v2);
                this.renderer.vertex(x + v4, y + v5, z);
                this.renderer.color(v2);
                this.renderer.vertex(x, y, z + height);
            }

            this.renderer.color(v2);
            this.renderer.vertex(x, y, z);
            this.renderer.color(v2);
            this.renderer.vertex(x + v4, y + v5, z);
        }

        v8 = v4;
        v9 = v5;
        v4 = radius;
        this.renderer.color(v2);
        this.renderer.vertex(x + v4, y + 0f, z);
        if(this.shapeType != ShapeType.Line) {
            this.renderer.color(v2);
            this.renderer.vertex(x + v8, y + v9, z);
            this.renderer.color(v2);
            this.renderer.vertex(x + v4, y + 0f, z);
            this.renderer.color(v2);
            this.renderer.vertex(x, y, z + height);
        }
    }

    public void curve(float x1, float y1, float cx1, float cy1, float cx2, float cy2, float x2, float y2, int segments) {
        this.check(ShapeType.Line, null, segments * 2 + 2);
        float v4 = this.color.toFloatBits();
        float v18 = 1f / (((float)segments));
        float v19 = v18 * v18;
        float v20 = v18 * v18 * v18;
        float v13 = 3f * v18;
        float v14 = 3f * v19;
        float v15 = 6f * v19;
        float v16 = 6f * v20;
        float v21 = x1 - 2f * cx1 + cx2;
        float v22 = y1 - 2f * cy1 + cy2;
        float v23 = (cx1 - cx2) * 3f - x1 + x2;
        float v24 = (cy1 - cy2) * 3f - y1 + y2;
        float v11 = x1;
        float v12 = y1;
        float v9 = (cx1 - x1) * v13 + v21 * v14 + v23 * v20;
        float v10 = (cy1 - y1) * v13 + v22 * v14 + v24 * v20;
        float v7 = v21 * v15 + v23 * v16;
        float v8 = v22 * v15 + v24 * v16;
        float v5 = v23 * v16;
        float v6 = v24 * v16;
        int v17;
        for(v17 = segments; true; v17 = segments) {
            segments = v17 - 1;
            if(v17 <= 0) {
                break;
            }

            this.renderer.color(v4);
            this.renderer.vertex(v11, v12, 0f);
            v11 += v9;
            v12 += v10;
            v9 += v7;
            v10 += v8;
            v7 += v5;
            v8 += v6;
            this.renderer.color(v4);
            this.renderer.vertex(v11, v12, 0f);
        }

        this.renderer.color(v4);
        this.renderer.vertex(v11, v12, 0f);
        this.renderer.color(v4);
        this.renderer.vertex(x2, y2, 0f);
    }

    public void dispose() {
        this.renderer.dispose();
    }

    public void ellipse(float x, float y, float width, float height) {
        this.ellipse(x, y, width, height, Math.max(1, ((int)(12f * (((float)Math.cbrt(((double)Math.max(width * 0.5f, 0.5f * height)))))))));
    }

    public void ellipse(float x, float y, float width, float height, int segments) {
        if(segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }

        this.check(ShapeType.Line, ShapeType.Filled, segments * 3);
        float v1 = this.color.toFloatBits();
        float v0 = 6.283185f / (((float)segments));
        float v2 = x + width / 2f;
        float v3 = y + height / 2f;
        if(this.shapeType == ShapeType.Line) {
            int v4;
            for(v4 = 0; v4 < segments; ++v4) {
                this.renderer.color(v1);
                this.renderer.vertex(0.5f * width * MathUtils.cos((((float)v4)) * v0) + v2, 0.5f * height * MathUtils.sin((((float)v4)) * v0) + v3, 0f);
                this.renderer.color(v1);
                this.renderer.vertex(0.5f * width * MathUtils.cos((((float)(v4 + 1))) * v0) + v2, 0.5f * height * MathUtils.sin((((float)(v4 + 1))) * v0) + v3, 0f);
            }
        }
        else {
            for(v4 = 0; v4 < segments; ++v4) {
                this.renderer.color(v1);
                this.renderer.vertex(0.5f * width * MathUtils.cos((((float)v4)) * v0) + v2, 0.5f * height * MathUtils.sin((((float)v4)) * v0) + v3, 0f);
                this.renderer.color(v1);
                this.renderer.vertex(v2, v3, 0f);
                this.renderer.color(v1);
                this.renderer.vertex(0.5f * width * MathUtils.cos((((float)(v4 + 1))) * v0) + v2, 0.5f * height * MathUtils.sin((((float)(v4 + 1))) * v0) + v3, 0f);
            }
        }
    }

    public void end() {
        this.renderer.end();
        this.shapeType = null;
    }

    public void flush() {
        ShapeType v0 = this.shapeType;
        this.end();
        this.begin(v0);
    }

    public Color getColor() {
        return this.color;
    }

    public ShapeType getCurrentType() {
        return this.shapeType;
    }

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public ImmediateModeRenderer getRenderer() {
        return this.renderer;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    public void identity() {
        this.transformMatrix.idt();
        this.matrixDirty = true;
    }

    public boolean isDrawing() {
        boolean v0;
        if(this.shapeType != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public final void line(float x, float y, float x2, float y2) {
        this.line(x, y, 0f, x2, y2, 0f, this.color, this.color);
    }

    public void line(float x, float y, float z, float x2, float y2, float z2, Color c1, Color c2) {
        if(this.shapeType == ShapeType.Filled) {
            this.rectLine(x, y, x2, y2, this.defaultRectLineWidth);
        }
        else {
            this.check(ShapeType.Line, null, 2);
            this.renderer.color(c1.r, c1.g, c1.b, c1.a);
            this.renderer.vertex(x, y, z);
            this.renderer.color(c2.r, c2.g, c2.b, c2.a);
            this.renderer.vertex(x2, y2, z2);
        }
    }

    public final void line(float x, float y, float z, float x2, float y2, float z2) {
        this.line(x, y, z, x2, y2, z2, this.color, this.color);
    }

    public final void line(float x, float y, float x2, float y2, Color c1, Color c2) {
        this.line(x, y, 0f, x2, y2, 0f, c1, c2);
    }

    public final void line(Vector2 v0, Vector2 v1) {
        this.line(v0.x, v0.y, 0f, v1.x, v1.y, 0f, this.color, this.color);
    }

    public final void line(Vector3 v0, Vector3 v1) {
        this.line(v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, this.color, this.color);
    }

    public void point(float x, float y, float z) {
        float v7;
        float v2 = 0.5f;
        if(this.shapeType == ShapeType.Line) {
            v7 = this.defaultRectLineWidth * v2;
            this.line(x - v7, y - v7, z, x + v7, y + v7, z);
        }
        else if(this.shapeType == ShapeType.Filled) {
            v7 = this.defaultRectLineWidth * v2;
            this.box(x - v7, y - v7, z - v7, this.defaultRectLineWidth, this.defaultRectLineWidth, this.defaultRectLineWidth);
        }
        else {
            this.check(ShapeType.Point, null, 1);
            this.renderer.color(this.color);
            this.renderer.vertex(x, y, z);
        }
    }

    public void polygon(float[] vertices) {
        this.polygon(vertices, 0, vertices.length);
    }

    public void polygon(float[] vertices, int offset, int count) {
        float v8;
        float v6;
        if(count < 6) {
            throw new IllegalArgumentException("Polygons must contain at least 3 points.");
        }

        if(count % 2 != 0) {
            throw new IllegalArgumentException("Polygons must have an even number of vertices.");
        }

        this.check(ShapeType.Line, null, count);
        float v0 = this.color.toFloatBits();
        float v1 = vertices[0];
        float v2 = vertices[1];
        int v3 = offset;
        int v4 = offset + count;
        while(v3 < v4) {
            float v5 = vertices[v3];
            float v7 = vertices[v3 + 1];
            if(v3 + 2 >= count) {
                v6 = v1;
                v8 = v2;
            }
            else {
                v6 = vertices[v3 + 2];
                v8 = vertices[v3 + 3];
            }

            this.renderer.color(v0);
            this.renderer.vertex(v5, v7, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(v6, v8, 0f);
            v3 += 2;
        }
    }

    public void polyline(float[] vertices) {
        this.polyline(vertices, 0, vertices.length);
    }

    public void polyline(float[] vertices, int offset, int count) {
        if(count < 4) {
            throw new IllegalArgumentException("Polylines must contain at least 2 points.");
        }

        if(count % 2 != 0) {
            throw new IllegalArgumentException("Polylines must have an even number of vertices.");
        }

        this.check(ShapeType.Line, null, count);
        float v0 = this.color.toFloatBits();
        int v1 = offset;
        int v2 = offset + count - 2;
        while(v1 < v2) {
            float v3 = vertices[v1];
            float v5 = vertices[v1 + 1];
            float v4 = vertices[v1 + 2];
            float v6 = vertices[v1 + 3];
            this.renderer.color(v0);
            this.renderer.vertex(v3, v5, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(v4, v6, 0f);
            v1 += 2;
        }
    }

    public void rect(float x, float y, float width, float height) {
        this.check(ShapeType.Line, ShapeType.Filled, 8);
        float v0 = this.color.toFloatBits();
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(v0);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, 0f);
        }
        else {
            this.renderer.color(v0);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x, y, 0f);
        }
    }

    public void rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees) {
        this.rect(x, y, originX, originY, width, height, scaleX, scaleY, degrees, this.color, this.color, this.color, this.color);
    }

    public void rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees, Color col1, Color col2, Color col3, Color col4) {
        this.check(ShapeType.Line, ShapeType.Filled, 8);
        float v4 = MathUtils.cosDeg(degrees);
        float v9 = MathUtils.sinDeg(degrees);
        float v5 = -originX;
        float v7 = -originY;
        float v6 = width - originX;
        float v8 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v5 *= scaleX;
            v7 *= scaleY;
            v6 *= scaleX;
            v8 *= scaleY;
        }

        float v10 = x + originX;
        float v11 = y + originY;
        float v12 = v4 * v5 - v9 * v7 + v10;
        float v16 = v9 * v5 + v4 * v7 + v11;
        float v13 = v4 * v6 - v9 * v7 + v10;
        float v17 = v9 * v6 + v4 * v7 + v11;
        float v14 = v4 * v6 - v9 * v8 + v10;
        float v18 = v9 * v6 + v4 * v8 + v11;
        float v15 = v12 + (v14 - v13);
        float v19 = v18 - (v17 - v16);
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(v12, v16, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(v13, v17, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(v13, v17, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(v14, v18, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(v14, v18, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(v15, v19, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(v15, v19, 0f);
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(v12, v16, 0f);
        }
        else {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(v12, v16, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(v13, v17, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(v14, v18, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(v14, v18, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(v15, v19, 0f);
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(v12, v16, 0f);
        }
    }

    public void rect(float x, float y, float width, float height, Color col1, Color col2, Color col3, Color col4) {
        this.check(ShapeType.Line, ShapeType.Filled, 8);
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x, y, 0f);
        }
        else {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x, y, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x + width, y, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x + width, y + height, 0f);
            this.renderer.color(col4.r, col4.g, col4.b, col4.a);
            this.renderer.vertex(x, y + height, 0f);
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x, y, 0f);
        }
    }

    public void rectLine(float x1, float y1, float x2, float y2, float width) {
        this.check(ShapeType.Line, ShapeType.Filled, 8);
        float v0 = this.color.toFloatBits();
        Vector2 v1 = this.tmp.set(y2 - y1, x1 - x2).nor();
        width *= 0.5f;
        float v2 = v1.x * width;
        float v3 = v1.y * width;
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(v0);
            this.renderer.vertex(x1 + v2, y1 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1 - v2, y1 - v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 + v2, y2 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 - v2, y2 - v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 + v2, y2 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1 + v2, y1 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 - v2, y2 - v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1 - v2, y1 - v3, 0f);
        }
        else {
            this.renderer.color(v0);
            this.renderer.vertex(x1 + v2, y1 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1 - v2, y1 - v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 + v2, y2 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 - v2, y2 - v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2 + v2, y2 + v3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1 - v2, y1 - v3, 0f);
        }
    }

    public void rectLine(Vector2 p1, Vector2 p2, float width) {
        this.rectLine(p1.x, p1.y, p2.x, p2.y, width);
    }

    public void rotate(float axisX, float axisY, float axisZ, float degrees) {
        this.transformMatrix.rotate(axisX, axisY, axisZ, degrees);
        this.matrixDirty = true;
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        this.transformMatrix.scale(scaleX, scaleY, scaleZ);
        this.matrixDirty = true;
    }

    public void set(ShapeType type) {
        if(this.shapeType != type) {
            if(this.shapeType == null) {
                throw new IllegalStateException("begin must be called first.");
            }
            else if(!this.autoShapeType) {
                throw new IllegalStateException("autoShapeType must be enabled.");
            }
            else {
                this.end();
                this.begin(type);
            }
        }
    }

    public void setAutoShapeType(boolean autoShapeType) {
        this.autoShapeType = autoShapeType;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        this.projectionMatrix.set(matrix);
        this.matrixDirty = true;
    }

    public void setTransformMatrix(Matrix4 matrix) {
        this.transformMatrix.set(matrix);
        this.matrixDirty = true;
    }

    public void translate(float x, float y, float z) {
        this.transformMatrix.translate(x, y, z);
        this.matrixDirty = true;
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        this.check(ShapeType.Line, ShapeType.Filled, 6);
        float v0 = this.color.toFloatBits();
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(v0);
            this.renderer.vertex(x1, y1, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x3, y3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x3, y3, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x1, y1, 0f);
        }
        else {
            this.renderer.color(v0);
            this.renderer.vertex(x1, y1, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(v0);
            this.renderer.vertex(x3, y3, 0f);
        }
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color col1, Color col2, Color col3) {
        this.check(ShapeType.Line, ShapeType.Filled, 6);
        if(this.shapeType == ShapeType.Line) {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x1, y1, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x3, y3, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x3, y3, 0f);
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x1, y1, 0f);
        }
        else {
            this.renderer.color(col1.r, col1.g, col1.b, col1.a);
            this.renderer.vertex(x1, y1, 0f);
            this.renderer.color(col2.r, col2.g, col2.b, col2.a);
            this.renderer.vertex(x2, y2, 0f);
            this.renderer.color(col3.r, col3.g, col3.b, col3.a);
            this.renderer.vertex(x3, y3, 0f);
        }
    }

    public void updateMatrices() {
        this.matrixDirty = true;
    }

    public void x(float x, float y, float size) {
        this.line(x - size, y - size, x + size, y + size);
        this.line(x - size, y + size, x + size, y - size);
    }

    public void x(Vector2 p, float size) {
        this.x(p.x, p.y, size);
    }
}

