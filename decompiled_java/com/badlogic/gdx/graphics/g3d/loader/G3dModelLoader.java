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

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader$ModelParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BaseJsonReader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import java.util.Iterator;

public class G3dModelLoader extends ModelLoader {
    public static final short VERSION_HI = 0;
    public static final short VERSION_LO = 1;
    protected final BaseJsonReader reader;
    private final Quaternion tempQ;

    public G3dModelLoader(BaseJsonReader reader, FileHandleResolver resolver) {
        super(resolver);
        this.tempQ = new Quaternion();
        this.reader = reader;
    }

    public G3dModelLoader(BaseJsonReader reader) {
        this(reader, null);
    }

    public ModelData loadModelData(FileHandle fileHandle, ModelParameters parameters) {
        return this.parseModel(fileHandle);
    }

    private void parseAnimations(ModelData model, JsonValue json) {
        JsonValue v14;
        JsonValue v21;
        ModelNodeKeyframe v9;
        JsonValue v4 = json.get("animations");
        if(v4 != null) {
            model.animations.ensureCapacity(v4.size);
            JsonValue v2;
            for(v2 = v4.child; v2 != null; v2 = v2.next) {
                JsonValue v12 = v2.get("bones");
                if(v12 != null) {
                    ModelAnimation v3 = new ModelAnimation();
                    model.animations.add(v3);
                    v3.nodeAnimations.ensureCapacity(v12.size);
                    v3.id = v2.getString("id");
                    JsonValue v10;
                    for(v10 = v12.child; v10 != null; v10 = v10.next) {
                        ModelNodeAnimation v11 = new ModelNodeAnimation();
                        v3.nodeAnimations.add(v11);
                        v11.nodeId = v10.getString("boneId");
                        JsonValue v6 = v10.get("keyframes");
                        if(v6 == null || !v6.isArray()) {
                            JsonValue v22 = v10.get("translation");
                            if(v22 != null && (v22.isArray())) {
                                v11.translation = new Array();
                                v11.translation.ensureCapacity(v22.size);
                                for(v5 = v22.child; v5 != null; v5 = v5.next) {
                                    v9 = new ModelNodeKeyframe();
                                    v11.translation.add(v9);
                                    v9.keytime = v5.getFloat("keytime", 0f) / 1000f;
                                    v21 = v5.get("value");
                                    if(v21 != null && v21.size >= 3) {
                                        v9.value = new Vector3(v21.getFloat(0), v21.getFloat(1), v21.getFloat(2));
                                    }
                                }
                            }

                            JsonValue v15 = v10.get("rotation");
                            if(v15 != null && (v15.isArray())) {
                                v11.rotation = new Array();
                                v11.rotation.ensureCapacity(v15.size);
                                for(v5 = v15.child; v5 != null; v5 = v5.next) {
                                    ModelNodeKeyframe v8 = new ModelNodeKeyframe();
                                    v11.rotation.add(v8);
                                    v8.keytime = v5.getFloat("keytime", 0f) / 1000f;
                                    v14 = v5.get("value");
                                    if(v14 != null && v14.size >= 4) {
                                        v8.value = new Quaternion(v14.getFloat(0), v14.getFloat(1), v14.getFloat(2), v14.getFloat(3));
                                    }
                                }
                            }

                            JsonValue v18 = v10.get("scaling");
                            if(v18 == null) {
                                goto label_361;
                            }

                            if(!v18.isArray()) {
                                goto label_361;
                            }

                            v11.scaling = new Array();
                            v11.scaling.ensureCapacity(v18.size);
                            for(v5 = v18.child; v5 != null; v5 = v5.next) {
                                v9 = new ModelNodeKeyframe();
                                v11.scaling.add(v9);
                                v9.keytime = v5.getFloat("keytime", 0f) / 1000f;
                                JsonValue v17 = v5.get("value");
                                if(v17 != null && v17.size >= 3) {
                                    v9.value = new Vector3(v17.getFloat(0), v17.getFloat(1), v17.getFloat(2));
                                }
                            }
                        }
                        else {
                            JsonValue v5;
                            for(v5 = v6.child; v5 != null; v5 = v5.next) {
                                float v7 = v5.getFloat("keytime", 0f) / 1000f;
                                v21 = v5.get("translation");
                                if(v21 != null && v21.size == 3) {
                                    if(v11.translation == null) {
                                        v11.translation = new Array();
                                    }

                                    ModelNodeKeyframe v20 = new ModelNodeKeyframe();
                                    v20.keytime = v7;
                                    v20.value = new Vector3(v21.getFloat(0), v21.getFloat(1), v21.getFloat(2));
                                    v11.translation.add(v20);
                                }

                                v14 = v5.get("rotation");
                                if(v14 != null && v14.size == 4) {
                                    if(v11.rotation == null) {
                                        v11.rotation = new Array();
                                    }

                                    ModelNodeKeyframe v13 = new ModelNodeKeyframe();
                                    v13.keytime = v7;
                                    v13.value = new Quaternion(v14.getFloat(0), v14.getFloat(1), v14.getFloat(2), v14.getFloat(3));
                                    v11.rotation.add(v13);
                                }

                                JsonValue v16 = v5.get("scale");
                                if(v16 != null && v16.size == 3) {
                                    if(v11.scaling == null) {
                                        v11.scaling = new Array();
                                    }

                                    ModelNodeKeyframe v19 = new ModelNodeKeyframe();
                                    v19.keytime = v7;
                                    v19.value = new Vector3(v16.getFloat(0), v16.getFloat(1), v16.getFloat(2));
                                    v11.scaling.add(v19);
                                }
                            }
                        }

                    label_361:
                    }
                }
            }
        }
    }

    private VertexAttribute[] parseAttributes(JsonValue attributes) {
        int v4;
        int v2;
        String v0;
        Array v7 = new Array();
        JsonValue v6 = attributes.child;
        int v3 = 0;
        int v5;
        for(v5 = 0; true; v5 = v4) {
            if(v6 == null) {
                goto label_85;
            }

            v0 = v6.asString();
            if(v0.equals("POSITION")) {
                v7.add(VertexAttribute.Position());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.equals("NORMAL")) {
                v7.add(VertexAttribute.Normal());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.equals("COLOR")) {
                v7.add(VertexAttribute.ColorUnpacked());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.equals("COLORPACKED")) {
                v7.add(VertexAttribute.ColorPacked());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.equals("TANGENT")) {
                v7.add(VertexAttribute.Tangent());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.equals("BINORMAL")) {
                v7.add(VertexAttribute.Binormal());
                v2 = v3;
                v4 = v5;
            }
            else if(v0.startsWith("TEXCOORD")) {
                v4 = v5 + 1;
                v7.add(VertexAttribute.TexCoords(v5));
                v2 = v3;
            }
            else if(v0.startsWith("BLENDWEIGHT")) {
                v2 = v3 + 1;
                v7.add(VertexAttribute.BoneWeight(v3));
                v4 = v5;
            }
            else {
                break;
            }

            v6 = v6.next;
            v3 = v2;
        }

        throw new GdxRuntimeException("Unknown vertex attribute \'" + v0 + "\', should be one of position, normal, uv, tangent or binormal");
    label_85:
        return v7.toArray(VertexAttribute.class);
    }

    private Color parseColor(JsonValue colorArray) {
        if(colorArray.size >= 3) {
            return new Color(colorArray.getFloat(0), colorArray.getFloat(1), colorArray.getFloat(2), 1f);
        }

        throw new GdxRuntimeException("Expected Color values <> than three.");
    }

    private void parseMaterials(ModelData model, JsonValue json, String materialDir) {
        String v19;
        JsonValue v12 = json.get("materials");
        if(v12 != null) {
            model.materials.ensureCapacity(v12.size);
            JsonValue v11 = v12.child;
            while(v11 != null) {
                ModelMaterial v9 = new ModelMaterial();
                String v8 = v11.getString("id", null);
                if(v8 == null) {
                    throw new GdxRuntimeException("Material needs an id.");
                }
                else {
                    v9.id = v8;
                    JsonValue v5 = v11.get("diffuse");
                    if(v5 != null) {
                        v9.diffuse = this.parseColor(v5);
                    }

                    JsonValue v4 = v11.get("ambient");
                    if(v4 != null) {
                        v9.ambient = this.parseColor(v4);
                    }

                    JsonValue v6 = v11.get("emissive");
                    if(v6 != null) {
                        v9.emissive = this.parseColor(v6);
                    }

                    JsonValue v14 = v11.get("specular");
                    if(v14 != null) {
                        v9.specular = this.parseColor(v14);
                    }

                    JsonValue v13 = v11.get("reflection");
                    if(v13 != null) {
                        v9.reflection = this.parseColor(v13);
                    }

                    v9.shininess = v11.getFloat("shininess", 0f);
                    v9.opacity = v11.getFloat("opacity", 1f);
                    JsonValue v18 = v11.get("textures");
                    if(v18 != null) {
                        JsonValue v15 = v18.child;
                        while(v15 != null) {
                            ModelTexture v10 = new ModelTexture();
                            String v16 = v15.getString("id", null);
                            if(v16 == null) {
                                throw new GdxRuntimeException("Texture has no id.");
                            }
                            else {
                                v10.id = v16;
                                String v7 = v15.getString("filename", null);
                                if(v7 == null) {
                                    throw new GdxRuntimeException("Texture needs filename.");
                                }
                                else {
                                    StringBuilder v20 = new StringBuilder().append(materialDir);
                                    if(materialDir.length() == 0 || (materialDir.endsWith("/"))) {
                                        v19 = "";
                                    }
                                    else {
                                        v19 = "/";
                                    }

                                    v10.fileName = v20.append(v19).append(v7).toString();
                                    v10.uvTranslation = this.readVector2(v15.get("uvTranslation"), 0f, 0f);
                                    v10.uvScaling = this.readVector2(v15.get("uvScaling"), 1f, 1f);
                                    String v17 = v15.getString("type", null);
                                    if(v17 == null) {
                                        throw new GdxRuntimeException("Texture needs type.");
                                    }

                                    v10.usage = this.parseTextureUsage(v17);
                                    if(v9.textures == null) {
                                        v9.textures = new Array();
                                    }

                                    v9.textures.add(v10);
                                    v15 = v15.next;
                                    continue;
                                }
                            }

                            break;
                        }
                    }

                    model.materials.add(v9);
                    v11 = v11.next;
                    continue;
                }

                return;
            }
        }
    }

    private void parseMeshes(ModelData model, JsonValue json) {
        JsonValue v9 = json.get("meshes");
        if(v9 != null) {
            model.meshes.ensureCapacity(v9.size);
            JsonValue v6;
            for(v6 = v9.child; v6 != null; v6 = v6.next) {
                ModelMesh v4 = new ModelMesh();
                v4.id = v6.getString("id", "");
                v4.attributes = this.parseAttributes(v6.require("attributes"));
                v4.vertices = v6.require("vertices").asFloatArray();
                JsonValue v8 = v6.require("parts");
                Array v12 = new Array();
                JsonValue v7 = v8.child;
                while(true) {
                    if(v7 != null) {
                        ModelMeshPart v5 = new ModelMeshPart();
                        String v11 = v7.getString("id", null);
                        if(v11 == null) {
                            throw new GdxRuntimeException("Not id given for mesh part");
                        }
                        else {
                            Iterator v2 = v12.iterator();
                            do {
                                if(v2.hasNext()) {
                                    if(!v2.next().id.equals(v11)) {
                                        continue;
                                    }

                                    break;
                                }
                                else {
                                    goto label_53;
                                }
                            }
                            while(true);

                            throw new GdxRuntimeException("Mesh part with id \'" + v11 + "\' already in defined");
                        label_53:
                            v5.id = v11;
                            String v13 = v7.getString("type", null);
                            if(v13 == null) {
                                throw new GdxRuntimeException("No primitive type given for mesh part \'" + v11 + "\'");
                            }
                            else {
                                v5.primitiveType = this.parseType(v13);
                                v5.indices = v7.require("indices").asShortArray();
                                v12.add(v5);
                                v7 = v7.next;
                                continue;
                            }
                        }
                    }
                    else {
                        break;
                    }

                    return;
                }

                v4.parts = v12.toArray(ModelMeshPart.class);
                model.meshes.add(v4);
            }
        }
    }

    public ModelData parseModel(FileHandle handle) {
        JsonValue v0 = this.reader.parse(handle);
        ModelData v1 = new ModelData();
        JsonValue v2 = v0.require("version");
        v1.version[0] = v2.getShort(0);
        v1.version[1] = v2.getShort(1);
        if(v1.version[0] == 0 && v1.version[1] == 1) {
            v1.id = v0.getString("id", "");
            this.parseMeshes(v1, v0);
            this.parseMaterials(v1, v0, handle.parent().path());
            this.parseNodes(v1, v0);
            this.parseAnimations(v1, v0);
            return v1;
        }

        throw new GdxRuntimeException("Model version not supported");
    }

    private Array parseNodes(ModelData model, JsonValue json) {
        JsonValue v1 = json.get("nodes");
        if(v1 != null) {
            model.nodes.ensureCapacity(v1.size);
            JsonValue v0;
            for(v0 = v1.child; v0 != null; v0 = v0.next) {
                model.nodes.add(this.parseNodesRecursively(v0));
            }
        }

        return model.nodes;
    }

    private ModelNode parseNodesRecursively(JsonValue json) {
        int v8;
        Quaternion v24_1;
        Vector3 v24;
        ModelNode v11 = new ModelNode();
        String v9 = json.getString("id", null);
        if(v9 == null) {
            throw new GdxRuntimeException("Node id missing.");
        }

        v11.id = v9;
        JsonValue v22 = json.get("translation");
        if(v22 != null && v22.size != 3) {
            throw new GdxRuntimeException("Node translation incomplete");
        }

        if(v22 == null) {
            v24 = null;
        }
        else {
            v24 = new Vector3(v22.getFloat(0), v22.getFloat(1), v22.getFloat(2));
        }

        v11.translation = v24;
        JsonValue v19 = json.get("rotation");
        if(v19 != null && v19.size != 4) {
            throw new GdxRuntimeException("Node rotation incomplete");
        }

        if(v19 == null) {
            v24_1 = null;
        }
        else {
            v24_1 = new Quaternion(v19.getFloat(0), v19.getFloat(1), v19.getFloat(2), v19.getFloat(3));
        }

        v11.rotation = v24_1;
        JsonValue v20 = json.get("scale");
        if(v20 != null && v20.size != 3) {
            throw new GdxRuntimeException("Node scale incomplete");
        }

        if(v20 == null) {
            v24 = null;
        }
        else {
            v24 = new Vector3(v20.getFloat(0), v20.getFloat(1), v20.getFloat(2));
        }

        v11.scale = v24;
        String v15 = json.getString("mesh", null);
        if(v15 != null) {
            v11.meshId = v15;
        }

        JsonValue v14 = json.get("parts");
        if(v14 != null) {
            v11.parts = new ModelNodePart[v14.size];
            v8 = 0;
            JsonValue v12 = v14.child;
            while(true) {
                if(v12 != null) {
                    ModelNodePart v18 = new ModelNodePart();
                    String v16 = v12.getString("meshpartid", null);
                    String v13 = v12.getString("materialid", null);
                    if(v16 != null && v13 != null) {
                        v18.materialId = v13;
                        v18.meshPartId = v16;
                        JsonValue v5 = v12.get("bones");
                        if(v5 != null) {
                            v18.bones = new ArrayMap(true, v5.size, String.class, Matrix4.class);
                            int v10 = 0;
                            JsonValue v4 = v5.child;
                            while(v4 != null) {
                                String v17 = v4.getString("node", null);
                                if(v17 == null) {
                                    throw new GdxRuntimeException("Bone node ID missing");
                                }
                                else {
                                    Matrix4 v21 = new Matrix4();
                                    JsonValue v23 = v4.get("translation");
                                    if(v23 != null && v23.size >= 3) {
                                        v21.translate(v23.getFloat(0), v23.getFloat(1), v23.getFloat(2));
                                    }

                                    v23 = v4.get("rotation");
                                    if(v23 != null && v23.size >= 4) {
                                        v21.rotate(this.tempQ.set(v23.getFloat(0), v23.getFloat(1), v23.getFloat(2), v23.getFloat(3)));
                                    }

                                    v23 = v4.get("scale");
                                    if(v23 != null && v23.size >= 3) {
                                        v21.scale(v23.getFloat(0), v23.getFloat(1), v23.getFloat(2));
                                    }

                                    v18.bones.put(v17, v21);
                                    v4 = v4.next;
                                    ++v10;
                                    continue;
                                }

                                break;
                            }
                        }

                        v11.parts[v8] = v18;
                        v12 = v12.next;
                        ++v8;
                        continue;
                    }

                    break;
                }

                goto label_294;
            }

            throw new GdxRuntimeException("Node " + v9 + " part is missing meshPartId or materialId");
        }

    label_294:
        JsonValue v7 = json.get("children");
        if(v7 != null) {
            v11.children = new ModelNode[v7.size];
            v8 = 0;
            JsonValue v6 = v7.child;
            while(v6 != null) {
                v11.children[v8] = this.parseNodesRecursively(v6);
                v6 = v6.next;
                ++v8;
            }
        }

        return v11;
    }

    private int parseTextureUsage(String value) {
        int v0;
        if(value.equalsIgnoreCase("AMBIENT")) {
            v0 = 4;
        }
        else if(value.equalsIgnoreCase("BUMP")) {
            v0 = 8;
        }
        else if(value.equalsIgnoreCase("DIFFUSE")) {
            v0 = 2;
        }
        else if(value.equalsIgnoreCase("EMISSIVE")) {
            v0 = 3;
        }
        else if(value.equalsIgnoreCase("NONE")) {
            v0 = 1;
        }
        else if(value.equalsIgnoreCase("NORMAL")) {
            v0 = 7;
        }
        else if(value.equalsIgnoreCase("REFLECTION")) {
            v0 = 10;
        }
        else if(value.equalsIgnoreCase("SHININESS")) {
            v0 = 6;
        }
        else if(value.equalsIgnoreCase("SPECULAR")) {
            v0 = 5;
        }
        else if(value.equalsIgnoreCase("TRANSPARENCY")) {
            v0 = 9;
        }
        else {
            v0 = 0;
        }

        return v0;
    }

    private int parseType(String type) {
        int v0;
        if(type.equals("TRIANGLES")) {
            v0 = 4;
        }
        else if(type.equals("LINES")) {
            v0 = 1;
        }
        else if(type.equals("POINTS")) {
            v0 = 0;
        }
        else if(type.equals("TRIANGLE_STRIP")) {
            v0 = 5;
        }
        else if(type.equals("LINE_STRIP")) {
            v0 = 3;
        }
        else {
            goto label_25;
        }

        return v0;
    label_25:
        throw new GdxRuntimeException("Unknown primitive type \'" + type + "\', should be one of triangle, trianglestrip, line, linestrip, lineloop or point");
    }

    private Vector2 readVector2(JsonValue vectorArray, float x, float y) {
        Vector2 v0;
        if(vectorArray == null) {
            v0 = new Vector2(x, y);
        }
        else if(vectorArray.size == 2) {
            v0 = new Vector2(vectorArray.getFloat(0), vectorArray.getFloat(1));
        }
        else {
            goto label_12;
        }

        return v0;
    label_12:
        throw new GdxRuntimeException("Expected Vector2 values <> than two.");
    }
}

