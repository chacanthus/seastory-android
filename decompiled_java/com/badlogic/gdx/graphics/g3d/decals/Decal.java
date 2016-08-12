// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.NumberUtils;

public class Decal {
    public static final int C1 = 3;
    public static final int C2 = 9;
    public static final int C3 = 15;
    public static final int C4 = 21;
    public static final int SIZE = 24;
    public static final int U1 = 4;
    public static final int U2 = 10;
    public static final int U3 = 16;
    public static final int U4 = 22;
    public static final int V1 = 5;
    public static final int V2 = 11;
    public static final int V3 = 17;
    public static final int V4 = 23;
    private static final int VERTEX_SIZE = 6;
    public static final int X1 = 0;
    public static final int X2 = 6;
    public static final int X3 = 12;
    public static final int X4 = 18;
    public static final int Y1 = 1;
    public static final int Y2 = 7;
    public static final int Y3 = 13;
    public static final int Y4 = 19;
    public static final int Z1 = 2;
    public static final int Z2 = 8;
    public static final int Z3 = 14;
    public static final int Z4 = 20;
    protected Color color;
    protected Vector2 dimensions;
    static final Vector3 dir;
    protected Vector3 position;
    protected Quaternion rotation;
    protected static Quaternion rotator;
    protected Vector2 scale;
    private static Vector3 tmp;
    private static Vector3 tmp2;
    public Vector2 transformationOffset;
    protected boolean updated;
    public int value;
    protected float[] vertices;

    static  {
        Decal.tmp = new Vector3();
        Decal.tmp2 = new Vector3();
        Decal.dir = new Vector3();
        Decal.rotator = new Quaternion(0f, 0f, 0f, 0f);
    }

    public Decal() {
        super();
        this.vertices = new float[24];
        this.position = new Vector3();
        this.rotation = new Quaternion();
        this.scale = new Vector2(1f, 1f);
        this.color = new Color();
        this.transformationOffset = null;
        this.dimensions = new Vector2();
        this.material = new DecalMaterial();
        this.updated = false;
    }

    public Color getColor() {
        return this.color;
    }

    public float getHeight() {
        return this.dimensions.y;
    }

    public DecalMaterial getMaterial() {
        return this.material;
    }

    public Vector3 getPosition() {
        return this.position;
    }

    public Quaternion getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scale.x;
    }

    public float getScaleY() {
        return this.scale.y;
    }

    public TextureRegion getTextureRegion() {
        return this.material.textureRegion;
    }

    public float[] getVertices() {
        return this.vertices;
    }

    public float getWidth() {
        return this.dimensions.x;
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getZ() {
        return this.position.z;
    }

    public void lookAt(Vector3 position, Vector3 up) {
        Decal.dir.set(position).sub(this.position).nor();
        this.setRotation(Decal.dir, up);
    }

    public static Decal newDecal(float width, float height, TextureRegion textureRegion) {
        return Decal.newDecal(width, height, textureRegion, -1, -1);
    }

    public static Decal newDecal(float width, float height, TextureRegion textureRegion, int srcBlendFactor, int dstBlendFactor) {
        Decal v0 = new Decal();
        v0.setTextureRegion(textureRegion);
        v0.setBlending(srcBlendFactor, dstBlendFactor);
        v0.dimensions.x = width;
        v0.dimensions.y = height;
        v0.setColor(1f, 1f, 1f, 1f);
        return v0;
    }

    public static Decal newDecal(float width, float height, TextureRegion textureRegion, boolean hasTransparency) {
        int v1;
        int v0 = -1;
        if(hasTransparency) {
            v1 = 770;
        }
        else {
            v1 = v0;
        }

        if(hasTransparency) {
            v0 = 771;
        }

        return Decal.newDecal(width, height, textureRegion, v1, v0);
    }

    public static Decal newDecal(TextureRegion textureRegion) {
        return Decal.newDecal(((float)textureRegion.getRegionWidth()), ((float)textureRegion.getRegionHeight()), textureRegion, -1, -1);
    }

    public static Decal newDecal(TextureRegion textureRegion, boolean hasTransparency) {
        int v1;
        int v0 = -1;
        float v2 = ((float)textureRegion.getRegionWidth());
        float v3 = ((float)textureRegion.getRegionHeight());
        if(hasTransparency) {
            v1 = 770;
        }
        else {
            v1 = v0;
        }

        if(hasTransparency) {
            v0 = 771;
        }

        return Decal.newDecal(v2, v3, textureRegion, v1, v0);
    }

    protected void resetVertices() {
        float v1 = -this.dimensions.x / 2f;
        float v2 = v1 + this.dimensions.x;
        float v3 = this.dimensions.y / 2f;
        float v0 = v3 - this.dimensions.y;
        this.vertices[0] = v1;
        this.vertices[1] = v3;
        this.vertices[2] = 0f;
        this.vertices[6] = v2;
        this.vertices[7] = v3;
        this.vertices[8] = 0f;
        this.vertices[12] = v1;
        this.vertices[13] = v0;
        this.vertices[14] = 0f;
        this.vertices[18] = v2;
        this.vertices[19] = v0;
        this.vertices[20] = 0f;
        this.updated = false;
    }

    public void rotateX(float angle) {
        Decal.rotator.set(Vector3.X, angle);
        this.rotation.mul(Decal.rotator);
        this.updated = false;
    }

    public void rotateY(float angle) {
        Decal.rotator.set(Vector3.Y, angle);
        this.rotation.mul(Decal.rotator);
        this.updated = false;
    }

    public void rotateZ(float angle) {
        Decal.rotator.set(Vector3.Z, angle);
        this.rotation.mul(Decal.rotator);
        this.updated = false;
    }

    public void setBlending(int srcBlendFactor, int dstBlendFactor) {
        this.material.srcBlendFactor = srcBlendFactor;
        this.material.dstBlendFactor = dstBlendFactor;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        float v0 = NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
        this.vertices[3] = v0;
        this.vertices[9] = v0;
        this.vertices[15] = v0;
        this.vertices[21] = v0;
    }

    public void setColor(float color) {
        this.color.set(NumberUtils.floatToIntColor(color));
        this.vertices[3] = color;
        this.vertices[9] = color;
        this.vertices[15] = color;
        this.vertices[21] = color;
    }

    public void setColor(Color tint) {
        this.color.set(tint);
        float v0 = tint.toFloatBits();
        this.vertices[3] = v0;
        this.vertices[9] = v0;
        this.vertices[15] = v0;
        this.vertices[21] = v0;
    }

    public void setDimensions(float width, float height) {
        this.dimensions.set(width, height);
        this.updated = false;
    }

    public void setHeight(float height) {
        this.dimensions.y = height;
        this.updated = false;
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        this.updated = false;
    }

    public void setPosition(Vector3 pos) {
        this.position.set(pos);
        this.updated = false;
    }

    public void setRotation(Vector3 dir, Vector3 up) {
        Decal.tmp.set(up).crs(dir).nor();
        Decal.tmp2.set(dir).crs(Decal.tmp).nor();
        this.rotation.setFromAxes(Decal.tmp.x, Decal.tmp2.x, dir.x, Decal.tmp.y, Decal.tmp2.y, dir.y, Decal.tmp.z, Decal.tmp2.z, dir.z);
        this.updated = false;
    }

    public void setRotation(float yaw, float pitch, float roll) {
        this.rotation.setEulerAngles(yaw, pitch, roll);
        this.updated = false;
    }

    public void setRotation(Quaternion q) {
        this.rotation.set(q);
        this.updated = false;
    }

    public void setRotationX(float angle) {
        this.rotation.set(Vector3.X, angle);
        this.updated = false;
    }

    public void setRotationY(float angle) {
        this.rotation.set(Vector3.Y, angle);
        this.updated = false;
    }

    public void setRotationZ(float angle) {
        this.rotation.set(Vector3.Z, angle);
        this.updated = false;
    }

    public void setScale(float scale) {
        this.scale.set(scale, scale);
        this.updated = false;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scale.set(scaleX, scaleY);
        this.updated = false;
    }

    public void setScaleX(float scale) {
        this.scale.x = scale;
        this.updated = false;
    }

    public void setScaleY(float scale) {
        this.scale.y = scale;
        this.updated = false;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.material.textureRegion = textureRegion;
        this.updateUVs();
    }

    public void setWidth(float width) {
        this.dimensions.x = width;
        this.updated = false;
    }

    public void setX(float x) {
        this.position.x = x;
        this.updated = false;
    }

    public void setY(float y) {
        this.position.y = y;
        this.updated = false;
    }

    public void setZ(float z) {
        this.position.z = z;
        this.updated = false;
    }

    protected void transformVertices() {
        float v1;
        float v0;
        int v13 = 7;
        int v12 = 6;
        int v11 = 2;
        if(this.transformationOffset != null) {
            v0 = -this.transformationOffset.x;
            v1 = -this.transformationOffset.y;
        }
        else {
            v1 = 0f;
            v0 = 0f;
        }

        float v3 = (this.vertices[0] + v0) * this.scale.x;
        float v4 = (this.vertices[1] + v1) * this.scale.y;
        float v5 = this.vertices[v11];
        this.vertices[0] = this.rotation.w * v3 + this.rotation.y * v5 - this.rotation.z * v4;
        this.vertices[1] = this.rotation.w * v4 + this.rotation.z * v3 - this.rotation.x * v5;
        this.vertices[v11] = this.rotation.w * v5 + this.rotation.x * v4 - this.rotation.y * v3;
        float v2 = -this.rotation.x * v3 - this.rotation.y * v4 - this.rotation.z * v5;
        this.rotation.conjugate();
        v3 = this.vertices[0];
        v4 = this.vertices[1];
        v5 = this.vertices[v11];
        this.vertices[0] = this.rotation.x * v2 + this.rotation.w * v3 + this.rotation.z * v4 - this.rotation.y * v5;
        this.vertices[1] = this.rotation.y * v2 + this.rotation.w * v4 + this.rotation.x * v5 - this.rotation.z * v3;
        this.vertices[v11] = this.rotation.z * v2 + this.rotation.w * v5 + this.rotation.y * v3 - this.rotation.x * v4;
        this.rotation.conjugate();
        this.vertices[0] += this.position.x - v0;
        this.vertices[1] += this.position.y - v1;
        this.vertices[v11] += this.position.z;
        v3 = (this.vertices[v12] + v0) * this.scale.x;
        v4 = (this.vertices[v13] + v1) * this.scale.y;
        v5 = this.vertices[8];
        this.vertices[v12] = this.rotation.w * v3 + this.rotation.y * v5 - this.rotation.z * v4;
        this.vertices[v13] = this.rotation.w * v4 + this.rotation.z * v3 - this.rotation.x * v5;
        this.vertices[8] = this.rotation.w * v5 + this.rotation.x * v4 - this.rotation.y * v3;
        v2 = -this.rotation.x * v3 - this.rotation.y * v4 - this.rotation.z * v5;
        this.rotation.conjugate();
        v3 = this.vertices[v12];
        v4 = this.vertices[v13];
        v5 = this.vertices[8];
        this.vertices[v12] = this.rotation.x * v2 + this.rotation.w * v3 + this.rotation.z * v4 - this.rotation.y * v5;
        this.vertices[v13] = this.rotation.y * v2 + this.rotation.w * v4 + this.rotation.x * v5 - this.rotation.z * v3;
        this.vertices[8] = this.rotation.z * v2 + this.rotation.w * v5 + this.rotation.y * v3 - this.rotation.x * v4;
        this.rotation.conjugate();
        this.vertices[v12] += this.position.x - v0;
        this.vertices[v13] += this.position.y - v1;
        this.vertices[8] += this.position.z;
        v3 = (this.vertices[12] + v0) * this.scale.x;
        v4 = (this.vertices[13] + v1) * this.scale.y;
        v5 = this.vertices[14];
        this.vertices[12] = this.rotation.w * v3 + this.rotation.y * v5 - this.rotation.z * v4;
        this.vertices[13] = this.rotation.w * v4 + this.rotation.z * v3 - this.rotation.x * v5;
        this.vertices[14] = this.rotation.w * v5 + this.rotation.x * v4 - this.rotation.y * v3;
        v2 = -this.rotation.x * v3 - this.rotation.y * v4 - this.rotation.z * v5;
        this.rotation.conjugate();
        v3 = this.vertices[12];
        v4 = this.vertices[13];
        v5 = this.vertices[14];
        this.vertices[12] = this.rotation.x * v2 + this.rotation.w * v3 + this.rotation.z * v4 - this.rotation.y * v5;
        this.vertices[13] = this.rotation.y * v2 + this.rotation.w * v4 + this.rotation.x * v5 - this.rotation.z * v3;
        this.vertices[14] = this.rotation.z * v2 + this.rotation.w * v5 + this.rotation.y * v3 - this.rotation.x * v4;
        this.rotation.conjugate();
        this.vertices[12] += this.position.x - v0;
        this.vertices[13] += this.position.y - v1;
        this.vertices[14] += this.position.z;
        v3 = (this.vertices[18] + v0) * this.scale.x;
        v4 = (this.vertices[19] + v1) * this.scale.y;
        v5 = this.vertices[20];
        this.vertices[18] = this.rotation.w * v3 + this.rotation.y * v5 - this.rotation.z * v4;
        this.vertices[19] = this.rotation.w * v4 + this.rotation.z * v3 - this.rotation.x * v5;
        this.vertices[20] = this.rotation.w * v5 + this.rotation.x * v4 - this.rotation.y * v3;
        v2 = -this.rotation.x * v3 - this.rotation.y * v4 - this.rotation.z * v5;
        this.rotation.conjugate();
        v3 = this.vertices[18];
        v4 = this.vertices[19];
        v5 = this.vertices[20];
        this.vertices[18] = this.rotation.x * v2 + this.rotation.w * v3 + this.rotation.z * v4 - this.rotation.y * v5;
        this.vertices[19] = this.rotation.y * v2 + this.rotation.w * v4 + this.rotation.x * v5 - this.rotation.z * v3;
        this.vertices[20] = this.rotation.z * v2 + this.rotation.w * v5 + this.rotation.y * v3 - this.rotation.x * v4;
        this.rotation.conjugate();
        this.vertices[18] += this.position.x - v0;
        this.vertices[19] += this.position.y - v1;
        this.vertices[20] += this.position.z;
        this.updated = true;
    }

    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
        this.updated = false;
    }

    public void translate(Vector3 trans) {
        this.position.add(trans);
        this.updated = false;
    }

    public void translateX(float units) {
        this.position.x += units;
        this.updated = false;
    }

    public void translateY(float units) {
        this.position.y += units;
        this.updated = false;
    }

    public void translateZ(float units) {
        this.position.z += units;
        this.updated = false;
    }

    protected void update() {
        if(!this.updated) {
            this.resetVertices();
            this.transformVertices();
        }
    }

    protected void updateUVs() {
        TextureRegion v0 = this.material.textureRegion;
        this.vertices[4] = v0.getU();
        this.vertices[5] = v0.getV();
        this.vertices[10] = v0.getU2();
        this.vertices[11] = v0.getV();
        this.vertices[16] = v0.getU();
        this.vertices[17] = v0.getV2();
        this.vertices[22] = v0.getU2();
        this.vertices[23] = v0.getV2();
    }
}

