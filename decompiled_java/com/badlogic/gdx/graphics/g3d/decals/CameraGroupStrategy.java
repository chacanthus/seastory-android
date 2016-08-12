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
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import java.util.Comparator;
import java.util.Iterator;

public class CameraGroupStrategy implements GroupStrategy, Disposable {
    private static final int GROUP_BLEND = 1;
    private static final int GROUP_OPAQUE;
    Pool arrayPool;
    Camera camera;
    private final Comparator cameraSorter;
    ObjectMap materialGroups;
    ShaderProgram shader;
    Array usedArrays;

    public CameraGroupStrategy(Camera camera) {
        this(camera, new Comparator() {
            public int compare(Decal o1, Decal o2) {
                return ((int)Math.signum(this.val$camera.position.dst(o2.position) - this.val$camera.position.dst(o1.position)));
            }

            public int compare(Object x0, Object x1) {
                return this.compare(((Decal)x0), ((Decal)x1));
            }
        });
    }

    public CameraGroupStrategy(Camera camera, Comparator arg4) {
        super();
        this.arrayPool = new Pool() {
            protected Array newObject() {
                return new Array();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.usedArrays = new Array();
        this.materialGroups = new ObjectMap();
        this.camera = camera;
        this.cameraSorter = arg4;
        this.createDefaultShader();
    }

    public void afterGroup(int group) {
        if(group == 1) {
            Gdx.gl.glDisable(3042);
        }
    }

    public void afterGroups() {
        this.shader.end();
        Gdx.gl.glDisable(2929);
    }

    public void beforeGroup(int group, Array arg9) {
        if(group == 1) {
            Gdx.gl.glEnable(3042);
            arg9.sort(this.cameraSorter);
        }
        else {
            int v1 = 0;
            int v4 = arg9.size;
            while(v1 < v4) {
                Object v0 = arg9.get(v1);
                Object v3 = this.materialGroups.get(((Decal)v0).material);
                if(v3 == null) {
                    v3 = this.arrayPool.obtain();
                    ((Array)v3).clear();
                    this.usedArrays.add(v3);
                    this.materialGroups.put(((Decal)v0).material, v3);
                }

                ((Array)v3).add(v0);
                ++v1;
            }

            arg9.clear();
            Iterator v2 = this.materialGroups.values().iterator();
            while(v2.hasNext()) {
                arg9.addAll(v2.next());
            }

            this.materialGroups.clear();
            this.arrayPool.freeAll(this.usedArrays);
            this.usedArrays.clear();
        }
    }

    public void beforeGroups() {
        Gdx.gl.glEnable(2929);
        this.shader.begin();
        this.shader.setUniformMatrix("u_projectionViewMatrix", this.camera.combined);
        this.shader.setUniformi("u_texture", 0);
    }

    private void createDefaultShader() {
        this.shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projectionViewMatrix;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projectionViewMatrix * a_position;\n}\n", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if(!this.shader.isCompiled()) {
            throw new IllegalArgumentException("couldn\'t compile shader: " + this.shader.getLog());
        }
    }

    public int decideGroup(Decal decal) {
        int v0;
        if(decal.getMaterial().isOpaque()) {
            v0 = 0;
        }
        else {
            v0 = 1;
        }

        return v0;
    }

    public void dispose() {
        if(this.shader != null) {
            this.shader.dispose();
        }
    }

    public Camera getCamera() {
        return this.camera;
    }

    public ShaderProgram getGroupShader(int group) {
        return this.shader;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}

