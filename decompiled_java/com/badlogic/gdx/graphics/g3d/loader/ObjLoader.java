// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader$ModelParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class ObjLoader extends ModelLoader {
    class Group {
        Array faces;
        boolean hasNorms;
        boolean hasUVs;
        Material mat;
        String materialName;
        int numFaces;

        Group(ObjLoader arg3, String name) {
            ObjLoader.this = arg3;
            super();
            this.name = name;
            this.faces = new Array(200);
            this.numFaces = 0;
            this.mat = new Material("");
            this.materialName = "default";
        }
    }

    public class ObjLoaderParameters extends ModelParameters {
        public boolean flipV;

        public ObjLoaderParameters(boolean flipV) {
            super();
            this.flipV = flipV;
        }

        public ObjLoaderParameters() {
            super();
        }
    }

    final Array groups;
    public static boolean logWarning;
    final FloatArray norms;
    final FloatArray uvs;
    final FloatArray verts;

    static  {
        ObjLoader.logWarning = false;
    }

    public ObjLoader() {
        this(null);
    }

    public ObjLoader(FileHandleResolver resolver) {
        super(resolver);
        this.verts = new FloatArray(300);
        this.norms = new FloatArray(300);
        this.uvs = new FloatArray(200);
        this.groups = new Array(10);
    }

    private int getIndex(String index, int size) {
        int v1;
        if(index == null || index.length() == 0) {
            v1 = 0;
        }
        else {
            int v0 = Integer.parseInt(index);
            if(v0 < 0) {
                v1 = size + v0;
            }
            else {
                v1 = v0 - 1;
            }
        }

        return v1;
    }

    public Model loadModel(FileHandle fileHandle, boolean flipV) {
        return this.loadModel(fileHandle, new ObjLoaderParameters(flipV));
    }

    public ModelData loadModelData(FileHandle x0, ModelParameters x1) {
        return this.loadModelData(x0, ((ObjLoaderParameters)x1));
    }

    public ModelData loadModelData(FileHandle file, ObjLoaderParameters parameters) {
        boolean v0;
        if(parameters == null) {
            v0 = false;
        }
        else {
            v0 = parameters.flipV;
        }

        return this.loadModelData(file, v0);
    }

    protected ModelData loadModelData(FileHandle file, boolean flipV) {  // has try-catch handlers
        int v30;
        int v43_1;
        float v43;
        int v15;
        Array v7;
        String[] v36;
        if(ObjLoader.logWarning) {
            Gdx.app.error("ObjLoader", "Wavefront (OBJ) is not fully supported, consult the documentation for more information");
        }

        MtlLoader v22 = new MtlLoader();
        Group v3 = new Group(this, "default");
        this.groups.add(v3);
        BufferedReader v35 = new BufferedReader(new InputStreamReader(file.read()), 4096);
        int v17 = 0;
        try {
            while(true) {
            label_25:
                String v18 = v35.readLine();
                if(v18 != null) {
                    v36 = v18.split("\\s+");
                    if(v36.length >= 1) {
                        if(v36[0].length() == 0) {
                            continue;
                        }

                        int v10 = v36[0].toLowerCase().charAt(0);
                        if(v10 == 35) {
                            continue;
                        }

                        if(v10 != 118) {
                            if(v10 != 102) {
                                goto label_408;
                            }

                            v7 = v3.faces;
                            v15 = 1;
                            goto label_182;
                        label_408:
                            if(v10 != 111 && v10 != 103) {
                                if(v36[0].equals("mtllib")) {
                                    v22.load(file.parent().child(v36[1]));
                                    continue;
                                }
                                else {
                                    if(!v36[0].equals("usemtl")) {
                                        continue;
                                    }

                                    if(v36.length == 1) {
                                        v3.materialName = "default";
                                        continue;
                                    }
                                    else {
                                        v3.materialName = v36[1];
                                        continue;
                                    }
                                }
                            }

                            if(v36.length <= 1) {
                                goto label_427;
                            }

                            v3 = this.setActiveGroup(v36[1]);
                            continue;
                        label_427:
                            v3 = this.setActiveGroup("default");
                            continue;
                        }
                        else if(v36[0].length() == 1) {
                            this.verts.add(Float.parseFloat(v36[1]));
                            this.verts.add(Float.parseFloat(v36[2]));
                            this.verts.add(Float.parseFloat(v36[3]));
                            continue;
                        }
                        else if(v36[0].charAt(1) == 110) {
                            this.norms.add(Float.parseFloat(v36[1]));
                            this.norms.add(Float.parseFloat(v36[2]));
                            this.norms.add(Float.parseFloat(v36[3]));
                            continue;
                        }
                        else {
                            if(v36[0].charAt(1) != 116) {
                                continue;
                            }

                            this.uvs.add(Float.parseFloat(v36[1]));
                            FloatArray v44 = this.uvs;
                            if(flipV) {
                                v43 = 1f - Float.parseFloat(v36[2]);
                            }
                            else {
                                v43 = Float.parseFloat(v36[2]);
                            }

                            v44.add(v43);
                            continue;
                        }
                    }
                }

                break;
            }

            v35.close();
            v15 = 0;
            goto label_40;
            while(true) {
            label_182:
                if(v15 >= v36.length - 2) {
                    goto label_25;
                }

                String[] v33 = v36[1].split("/");
                v7.add(Integer.valueOf(this.getIndex(v33[0], this.verts.size)));
                if(v33.length > 2) {
                    if(v15 == 1) {
                        v3.hasNorms = true;
                    }

                    v7.add(Integer.valueOf(this.getIndex(v33[2], this.norms.size)));
                }

                if(v33.length > 1 && v33[1].length() > 0) {
                    if(v15 == 1) {
                        v3.hasUVs = true;
                    }

                    v7.add(Integer.valueOf(this.getIndex(v33[1], this.uvs.size)));
                }

                ++v15;
                v33 = v36[v15].split("/");
                v7.add(Integer.valueOf(this.getIndex(v33[0], this.verts.size)));
                if(v33.length > 2) {
                    v7.add(Integer.valueOf(this.getIndex(v33[2], this.norms.size)));
                }

                if(v33.length > 1 && v33[1].length() > 0) {
                    v7.add(Integer.valueOf(this.getIndex(v33[1], this.uvs.size)));
                }

                ++v15;
                v33 = v36[v15].split("/");
                v7.add(Integer.valueOf(this.getIndex(v33[0], this.verts.size)));
                if(v33.length > 2) {
                    v7.add(Integer.valueOf(this.getIndex(v33[2], this.norms.size)));
                }

                if(v33.length > 1 && v33[1].length() > 0) {
                    v7.add(Integer.valueOf(this.getIndex(v33[1], this.uvs.size)));
                }

                ++v3.numFaces;
                --v15;
            }
        }
        catch(IOException v6) {
            goto label_113;
        }

        while(true) {
        label_40:
            if(v15 >= this.groups.size) {
                break;
            }

            if(this.groups.get(v15).numFaces < 1) {
                this.groups.removeIndex(v15);
                --v15;
            }

            ++v15;
        }

        if(this.groups.size >= 1) {
            goto label_478;
        }

        ModelData v5 = null;
        goto label_114;
    label_478:
        int v29 = this.groups.size;
        v5 = new ModelData();
        int v11;
        for(v11 = 0; v11 < v29; ++v11) {
            Object v12 = this.groups.get(v11);
            v7 = ((Group)v12).faces;
            int v27 = v7.size;
            int v28 = ((Group)v12).numFaces;
            boolean v13 = ((Group)v12).hasNorms;
            boolean v14 = ((Group)v12).hasUVs;
            int v44_1 = v28 * 3;
            if(v13) {
                v43_1 = 3;
            }
            else {
                v43_1 = 0;
            }

            int v45 = v43_1 + 3;
            if(v14) {
                v43_1 = 2;
            }
            else {
                v43_1 = 0;
            }

            float[] v9 = new float[(v43_1 + v45) * v44_1];
            int v42 = 0;
            int v16;
            for(v16 = 0; v16 < v27; v16 = v15) {
                v15 = v16 + 1;
                int v39 = v7.get(v16).intValue() * 3;
                int v41 = v42 + 1;
                int v40 = v39 + 1;
                v9[v42] = this.verts.get(v39);
                v42 = v41 + 1;
                v9[v41] = this.verts.get(v40);
                v41 = v42 + 1;
                v9[v42] = this.verts.get(v40 + 1);
                if(v13) {
                    v16 = v15 + 1;
                    int v25 = v7.get(v15).intValue() * 3;
                    v42 = v41 + 1;
                    int v26 = v25 + 1;
                    v9[v41] = this.norms.get(v25);
                    v41 = v42 + 1;
                    v9[v42] = this.norms.get(v26);
                    v42 = v41 + 1;
                    v9[v41] = this.norms.get(v26 + 1);
                }
                else {
                    v42 = v41;
                    v16 = v15;
                }

                if(v14) {
                    v15 = v16 + 1;
                    int v37 = v7.get(v16).intValue() * 2;
                    v41 = v42 + 1;
                    v9[v42] = this.uvs.get(v37);
                    v9[v41] = this.uvs.get(v37 + 1);
                    ++v41;
                }
                else {
                    v41 = v42;
                    v15 = v16;
                }

                v42 = v41;
            }

            if(v28 * 3 >= 32767) {
                v30 = 0;
            }
            else {
                v30 = v28 * 3;
            }

            short[] v8 = new short[v30];
            if(v30 > 0) {
                for(v15 = 0; v15 < v30; ++v15) {
                    v8[v15] = ((short)v15);
                }
            }

            Array v4 = new Array();
            v4.add(new VertexAttribute(1, 3, "a_position"));
            if(v13) {
                v4.add(new VertexAttribute(8, 3, "a_normal"));
            }

            if(v14) {
                v4.add(new VertexAttribute(16, 2, "a_texCoord0"));
            }

            ++v17;
            String v24 = "node" + v17;
            String v20 = "mesh" + v17;
            String v32 = "part" + v17;
            ModelNode v23 = new ModelNode();
            v23.id = v24;
            v23.meshId = v20;
            v23.scale = new Vector3(1f, 1f, 1f);
            v23.translation = new Vector3();
            v23.rotation = new Quaternion();
            ModelNodePart v34 = new ModelNodePart();
            v34.meshPartId = v32;
            v34.materialId = ((Group)v12).materialName;
            ModelNodePart[] v43_2 = new ModelNodePart[1];
            v43_2[0] = v34;
            v23.parts = v43_2;
            ModelMeshPart v31 = new ModelMeshPart();
            v31.id = v32;
            v31.indices = v8;
            v31.primitiveType = 4;
            ModelMesh v19 = new ModelMesh();
            v19.id = v20;
            v19.attributes = v4.toArray(VertexAttribute.class);
            v19.vertices = v9;
            ModelMeshPart[] v43_3 = new ModelMeshPart[1];
            v43_3[0] = v31;
            v19.parts = v43_3;
            v5.nodes.add(v23);
            v5.meshes.add(v19);
            v5.materials.add(v22.getMaterial(((Group)v12).materialName));
        }

        if(this.verts.size > 0) {
            this.verts.clear();
        }

        if(this.norms.size > 0) {
            this.norms.clear();
        }

        if(this.uvs.size > 0) {
            this.uvs.clear();
        }

        if(this.groups.size <= 0) {
            goto label_114;
        }

        this.groups.clear();
        goto label_114;
    label_113:
        v5 = null;
    label_114:
        return v5;
    }

    private Group setActiveGroup(String name) {
        Object v0;
        Iterator v1 = this.groups.iterator();
        do {
            if(v1.hasNext()) {
                v0 = v1.next();
                if(!((Group)v0).name.equals(name)) {
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }
        while(true);

        Group v0_1 = new Group(this, name);
        this.groups.add(v0_1);
    label_8:
        return ((Group)v0);
    }
}

