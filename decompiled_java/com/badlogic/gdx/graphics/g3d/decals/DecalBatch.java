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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh$VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SortedIntList;
import com.badlogic.gdx.utils.SortedIntList$Node;
import java.util.Iterator;

public class DecalBatch implements Disposable {
    private static final int DEFAULT_SIZE = 1000;
    private final SortedIntList groupList;
    private final Pool groupPool;
    private GroupStrategy groupStrategy;
    private Mesh mesh;
    private final Array usedGroups;
    private float[] vertices;

    public DecalBatch(int size, GroupStrategy groupStrategy) {
        super();
        this.groupList = new SortedIntList();
        this.groupPool = new Pool() {
            protected Array newObject() {
                return new Array(false, 100);
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.usedGroups = new Array(16);
        this.initialize(size);
        this.setGroupStrategy(groupStrategy);
    }

    public DecalBatch(GroupStrategy groupStrategy) {
        this(1000, groupStrategy);
    }

    public void add(Decal decal) {
        int v0 = this.groupStrategy.decideGroup(decal);
        Object v1 = this.groupList.get(v0);
        if(v1 == null) {
            v1 = this.groupPool.obtain();
            ((Array)v1).clear();
            this.usedGroups.add(v1);
            this.groupList.insert(v0, v1);
        }

        ((Array)v1).add(decal);
    }

    protected void clear() {
        this.groupList.clear();
        this.groupPool.freeAll(this.usedGroups);
        this.usedGroups.clear();
    }

    public void dispose() {
        this.clear();
        this.vertices = null;
        this.mesh.dispose();
    }

    protected void flush(ShaderProgram shader, int verticesPosition) {
        this.mesh.setVertices(this.vertices, 0, verticesPosition);
        this.mesh.render(shader, 4, 0, verticesPosition / 4);
    }

    public void flush() {
        this.render();
        this.clear();
    }

    public int getSize() {
        return this.vertices.length / 24;
    }

    public void initialize(int size) {
        this.vertices = new float[size * 24];
        VertexDataType v1 = VertexDataType.VertexArray;
        if(Gdx.gl30 != null) {
            v1 = VertexDataType.VertexBufferObjectWithVAO;
        }

        VertexAttribute[] v5 = new VertexAttribute[3];
        v5[0] = new VertexAttribute(1, 3, "a_position");
        v5[1] = new VertexAttribute(4, 4, "a_color");
        v5[2] = new VertexAttribute(16, 2, "a_texCoord0");
        this.mesh = new Mesh(v1, false, size * 4, size * 6, v5);
        short[] v7 = new short[size * 6];
        int v8 = 0;
        int v6 = 0;
        while(v6 < v7.length) {
            v7[v6] = ((short)v8);
            v7[v6 + 1] = ((short)(v8 + 2));
            v7[v6 + 2] = ((short)(v8 + 1));
            v7[v6 + 3] = ((short)(v8 + 1));
            v7[v6 + 4] = ((short)(v8 + 2));
            v7[v6 + 5] = ((short)(v8 + 3));
            v6 += 6;
            v8 += 4;
        }

        this.mesh.setIndices(v7);
    }

    private void render(ShaderProgram shader, Array arg10) {
        DecalMaterial v3 = null;
        int v2 = 0;
        Iterator v1 = arg10.iterator();
        while(true) {
            if(!v1.hasNext()) {
                break;
            }

            Object v0 = v1.next();
            if(v3 == null || !v3.equals(((Decal)v0).getMaterial())) {
                if(v2 > 0) {
                    this.flush(shader, v2);
                    v2 = 0;
                }

                ((Decal)v0).material.set();
                v3 = ((Decal)v0).material;
            }

            ((Decal)v0).update();
            System.arraycopy(((Decal)v0).vertices, 0, this.vertices, v2, ((Decal)v0).vertices.length);
            v2 += ((Decal)v0).vertices.length;
            if(v2 != this.vertices.length) {
                continue;
            }

            this.flush(shader, v2);
            v2 = 0;
        }

        if(v2 > 0) {
            this.flush(shader, v2);
        }
    }

    protected void render() {
        this.groupStrategy.beforeGroups();
        Iterator v1 = this.groupList.iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            this.groupStrategy.beforeGroup(((Node)v0).index, ((Node)v0).value);
            this.render(this.groupStrategy.getGroupShader(((Node)v0).index), ((Node)v0).value);
            this.groupStrategy.afterGroup(((Node)v0).index);
        }

        this.groupStrategy.afterGroups();
    }

    public void setGroupStrategy(GroupStrategy groupStrategy) {
        this.groupStrategy = groupStrategy;
    }
}

