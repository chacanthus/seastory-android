// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
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
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider$FileTextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import java.util.Iterator;

public class Model implements Disposable {
    public final Array animations;
    protected final Array disposables;
    private ObjectMap nodePartBones;

    public Model() {
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
    }

    public Model(ModelData modelData, TextureProvider textureProvider) {
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
        this.load(modelData, textureProvider);
    }

    public Model(ModelData modelData) {
        this(modelData, new FileTextureProvider());
    }

    public BoundingBox calculateBoundingBox(BoundingBox out) {
        out.inf();
        return this.extendBoundingBox(out);
    }

    public void calculateTransforms() {
        int v1 = this.nodes.size;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            this.nodes.get(v0).calculateTransforms(true);
        }

        for(v0 = 0; v0 < v1; ++v0) {
            this.nodes.get(v0).calculateBoneTransforms(true);
        }
    }

    private Material convertMaterial(ModelMaterial mtl, TextureProvider textureProvider) {
        float v10;
        float v9;
        float v8;
        float v7;
        Texture v13_1;
        Material v11 = new Material();
        v11.id = mtl.id;
        if(mtl.ambient != null) {
            v11.set(new ColorAttribute(ColorAttribute.Ambient, mtl.ambient));
        }

        if(mtl.diffuse != null) {
            v11.set(new ColorAttribute(ColorAttribute.Diffuse, mtl.diffuse));
        }

        if(mtl.specular != null) {
            v11.set(new ColorAttribute(ColorAttribute.Specular, mtl.specular));
        }

        if(mtl.emissive != null) {
            v11.set(new ColorAttribute(ColorAttribute.Emissive, mtl.emissive));
        }

        if(mtl.reflection != null) {
            v11.set(new ColorAttribute(ColorAttribute.Reflection, mtl.reflection));
        }

        if(mtl.shininess > 0f) {
            v11.set(new FloatAttribute(FloatAttribute.Shininess, mtl.shininess));
        }

        if(mtl.opacity != 1f) {
            v11.set(new BlendingAttribute(770, 771, mtl.opacity));
        }

        ObjectMap v14 = new ObjectMap();
        if(mtl.textures != null) {
            Iterator v2 = mtl.textures.iterator();
            while(v2.hasNext()) {
                Object v12 = v2.next();
                if(v14.containsKey(((ModelTexture)v12).fileName)) {
                    Object v13 = v14.get(((ModelTexture)v12).fileName);
                }
                else {
                    v13_1 = textureProvider.load(((ModelTexture)v12).fileName);
                    v14.put(((ModelTexture)v12).fileName, v13_1);
                    this.disposables.add(v13_1);
                }

                TextureDescriptor v6 = new TextureDescriptor(((GLTexture)v13_1));
                v6.minFilter = v13_1.getMinFilter();
                v6.magFilter = v13_1.getMagFilter();
                v6.uWrap = v13_1.getUWrap();
                v6.vWrap = v13_1.getVWrap();
                if(((ModelTexture)v12).uvTranslation == null) {
                    v7 = 0f;
                }
                else {
                    v7 = ((ModelTexture)v12).uvTranslation.x;
                }

                if(((ModelTexture)v12).uvTranslation == null) {
                    v8 = 0f;
                }
                else {
                    v8 = ((ModelTexture)v12).uvTranslation.y;
                }

                if(((ModelTexture)v12).uvScaling == null) {
                    v9 = 1f;
                }
                else {
                    v9 = ((ModelTexture)v12).uvScaling.x;
                }

                if(((ModelTexture)v12).uvScaling == null) {
                    v10 = 1f;
                }
                else {
                    v10 = ((ModelTexture)v12).uvScaling.y;
                }

                switch(((ModelTexture)v12).usage) {
                    case 2: {
                        goto label_102;
                    }
                    case 3: {
                        goto label_143;
                    }
                    case 4: {
                        goto label_139;
                    }
                    case 5: {
                        goto label_127;
                    }
                    case 7: {
                        goto label_135;
                    }
                    case 8: {
                        goto label_131;
                    }
                    case 10: {
                        goto label_147;
                    }
                }

                continue;
            label_131:
                v11.set(new TextureAttribute(TextureAttribute.Bump, v6, v7, v8, v9, v10));
                continue;
            label_147:
                v11.set(new TextureAttribute(TextureAttribute.Reflection, v6, v7, v8, v9, v10));
                continue;
            label_102:
                v11.set(new TextureAttribute(TextureAttribute.Diffuse, v6, v7, v8, v9, v10));
                continue;
            label_135:
                v11.set(new TextureAttribute(TextureAttribute.Normal, v6, v7, v8, v9, v10));
                continue;
            label_139:
                v11.set(new TextureAttribute(TextureAttribute.Ambient, v6, v7, v8, v9, v10));
                continue;
            label_143:
                v11.set(new TextureAttribute(TextureAttribute.Emissive, v6, v7, v8, v9, v10));
                continue;
            label_127:
                v11.set(new TextureAttribute(TextureAttribute.Specular, v6, v7, v8, v9, v10));
            }
        }

        return v11;
    }

    private void convertMesh(ModelMesh modelMesh) {
        int v6 = 0;
        ModelMeshPart[] v0 = modelMesh.parts;
        int v3 = v0.length;
        int v2;
        for(v2 = 0; v2 < v3; ++v2) {
            v6 += v0[v2].indices.length;
        }

        VertexAttributes v1 = new VertexAttributes(modelMesh.attributes);
        Mesh v4 = new Mesh(true, modelMesh.vertices.length / (v1.vertexSize / 4), v6, v1);
        this.meshes.add(v4);
        this.disposables.add(v4);
        BufferUtils.copy(modelMesh.vertices, v4.getVerticesBuffer(), modelMesh.vertices.length, 0);
        int v8 = 0;
        v4.getIndicesBuffer().clear();
        v0 = modelMesh.parts;
        v3 = v0.length;
        for(v2 = 0; v2 < v3; ++v2) {
            ModelMeshPart v9 = v0[v2];
            MeshPart v5 = new MeshPart();
            v5.id = v9.id;
            v5.primitiveType = v9.primitiveType;
            v5.indexOffset = v8;
            v5.numVertices = v9.indices.length;
            v5.mesh = v4;
            v4.getIndicesBuffer().put(v9.indices);
            v8 += v5.numVertices;
            this.meshParts.add(v5);
        }

        v4.getIndicesBuffer().position(0);
    }

    public void dispose() {
        Iterator v1 = this.disposables.iterator();
        while(v1.hasNext()) {
            v1.next().dispose();
        }
    }

    public BoundingBox extendBoundingBox(BoundingBox out) {
        int v1 = this.nodes.size;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            this.nodes.get(v0).extendBoundingBox(out);
        }

        return out;
    }

    public Animation getAnimation(String id) {
        return this.getAnimation(id, true);
    }

    public Animation getAnimation(String id, boolean ignoreCase) {
        Animation v0_1;
        Object v0;
        int v1;
        int v2 = this.animations.size;
        if(ignoreCase) {
            v1 = 0;
            while(true) {
                if(v1 < v2) {
                    v0 = this.animations.get(v1);
                    if(!((Animation)v0).id.equalsIgnoreCase(id)) {
                        ++v1;
                        continue;
                    }
                }
                else {
                    goto label_22;
                }

                break;
            }
        }
        else {
            v1 = 0;
            while(true) {
                if(v1 < v2) {
                    v0 = this.animations.get(v1);
                    if(!((Animation)v0).id.equals(id)) {
                        ++v1;
                        continue;
                    }
                }
                else {
                    break;
                }

                goto label_10;
            }

        label_22:
            v0_1 = null;
        }

    label_10:
        return v0_1;
    }

    public Iterable getManagedDisposables() {
        return this.disposables;
    }

    public Material getMaterial(String id) {
        return this.getMaterial(id, true);
    }

    public Material getMaterial(String id, boolean ignoreCase) {
        Material v1_1;
        Object v1;
        int v0;
        int v2 = this.materials.size;
        if(ignoreCase) {
            v0 = 0;
            while(true) {
                if(v0 < v2) {
                    v1 = this.materials.get(v0);
                    if(!((Material)v1).id.equalsIgnoreCase(id)) {
                        ++v0;
                        continue;
                    }
                }
                else {
                    goto label_22;
                }

                break;
            }
        }
        else {
            v0 = 0;
            while(true) {
                if(v0 < v2) {
                    v1 = this.materials.get(v0);
                    if(!((Material)v1).id.equals(id)) {
                        ++v0;
                        continue;
                    }
                }
                else {
                    break;
                }

                goto label_10;
            }

        label_22:
            v1_1 = null;
        }

    label_10:
        return v1_1;
    }

    public Node getNode(String id) {
        return this.getNode(id, true);
    }

    public Node getNode(String id, boolean recursive) {
        return this.getNode(id, recursive, false);
    }

    public Node getNode(String id, boolean recursive, boolean ignoreCase) {
        return Node.getNode(this.nodes, id, recursive, ignoreCase);
    }

    private void load(ModelData modelData, TextureProvider textureProvider) {
        this.loadMeshes(modelData.meshes);
        this.loadMaterials(modelData.materials, textureProvider);
        this.loadNodes(modelData.nodes);
        this.loadAnimations(modelData.animations);
        this.calculateTransforms();
    }

    private void loadAnimations(Iterable arg16) {
        Quaternion v10_2;
        Object v10_1;
        Vector3 v10;
        float v13;
        Array v11;
        Object v6;
        Iterator v4;
        NodeAnimation v9;
        Node v8;
        Object v7;
        Animation v1;
        Iterator v2 = arg16.iterator();
        while(true) {
            if(!v2.hasNext()) {
                return;
            }

            Object v0 = v2.next();
            v1 = new Animation();
            v1.id = ((ModelAnimation)v0).id;
            Iterator v3 = ((ModelAnimation)v0).nodeAnimations.iterator();
            do {
            label_9:
                if(!v3.hasNext()) {
                    goto label_122;
                }

                v7 = v3.next();
                v8 = this.getNode(((ModelNodeAnimation)v7).nodeId);
            }
            while(v8 == null);

            v9 = new NodeAnimation();
            v9.node = v8;
            if(((ModelNodeAnimation)v7).translation != null) {
                v9.translation = new Array();
                v9.translation.ensureCapacity(((ModelNodeAnimation)v7).translation.size);
                v4 = ((ModelNodeAnimation)v7).translation.iterator();
                break;
            label_122:
                if(v1.nodeAnimations.size <= 0) {
                    continue;
                }

                this.animations.add(v1);
                continue;
            }

            goto label_46;
        }

        while(v4.hasNext()) {
            v6 = v4.next();
            if(((ModelNodeKeyframe)v6).keytime > v1.duration) {
                v1.duration = ((ModelNodeKeyframe)v6).keytime;
            }

            v11 = v9.translation;
            v13 = ((ModelNodeKeyframe)v6).keytime;
            if(((ModelNodeKeyframe)v6).value == null) {
                v10 = v8.translation;
            }
            else {
                v10_1 = ((ModelNodeKeyframe)v6).value;
            }

            v11.add(new NodeKeyframe(v13, new Vector3(v10)));
        }

    label_46:
        if(((ModelNodeAnimation)v7).rotation != null) {
            v9.rotation = new Array();
            v9.rotation.ensureCapacity(((ModelNodeAnimation)v7).rotation.size);
            v4 = ((ModelNodeAnimation)v7).rotation.iterator();
            while(v4.hasNext()) {
                Object v5 = v4.next();
                if(((ModelNodeKeyframe)v5).keytime > v1.duration) {
                    v1.duration = ((ModelNodeKeyframe)v5).keytime;
                }

                v11 = v9.rotation;
                v13 = ((ModelNodeKeyframe)v5).keytime;
                if(((ModelNodeKeyframe)v5).value == null) {
                    v10_2 = v8.rotation;
                }
                else {
                    v10_1 = ((ModelNodeKeyframe)v5).value;
                }

                v11.add(new NodeKeyframe(v13, new Quaternion(v10_2)));
            }
        }

        if(((ModelNodeAnimation)v7).scaling != null) {
            v9.scaling = new Array();
            v9.scaling.ensureCapacity(((ModelNodeAnimation)v7).scaling.size);
            v4 = ((ModelNodeAnimation)v7).scaling.iterator();
            while(v4.hasNext()) {
                v6 = v4.next();
                if(((ModelNodeKeyframe)v6).keytime > v1.duration) {
                    v1.duration = ((ModelNodeKeyframe)v6).keytime;
                }

                v11 = v9.scaling;
                v13 = ((ModelNodeKeyframe)v6).keytime;
                if(((ModelNodeKeyframe)v6).value == null) {
                    v10 = v8.scale;
                }
                else {
                    v10_1 = ((ModelNodeKeyframe)v6).value;
                }

                v11.add(new NodeKeyframe(v13, new Vector3(v10)));
            }
        }

        if((v9.translation == null || v9.translation.size <= 0) && (v9.rotation == null || v9.rotation.size <= 0)) {
            if(v9.scaling == null) {
                goto label_9;
            }

            if(v9.scaling.size <= 0) {
                goto label_9;
            }
        }

        v1.nodeAnimations.add(v9);
        goto label_9;
    }

    private void loadMaterials(Iterable arg5, TextureProvider textureProvider) {
        Iterator v0 = arg5.iterator();
        while(v0.hasNext()) {
            this.materials.add(this.convertMaterial(v0.next(), textureProvider));
        }
    }

    private void loadMeshes(Iterable arg4) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.convertMesh(v0.next());
        }
    }

    private Node loadNode(ModelNode modelNode) {
        Object v6;
        Object v12;
        Iterator v3;
        int v5;
        Node v10 = new Node();
        v10.id = modelNode.id;
        if(modelNode.translation != null) {
            v10.translation.set(modelNode.translation);
        }

        if(modelNode.rotation != null) {
            v10.rotation.set(modelNode.rotation);
        }

        if(modelNode.scale != null) {
            v10.scale.set(modelNode.scale);
        }

        if(modelNode.parts != null) {
            ModelNodePart[] v1 = modelNode.parts;
            v5 = v1.length;
            int v4 = 0;
            while(true) {
                if(v4 < v5) {
                    ModelNodePart v9 = v1[v4];
                    Object v8 = null;
                    Object v7 = null;
                    if(v9.meshPartId != null) {
                        v3 = this.meshParts.iterator();
                        do {
                            if(v3.hasNext()) {
                                v12 = v3.next();
                                if(!v9.meshPartId.equals(((MeshPart)v12).id)) {
                                    continue;
                                }

                                break;
                            }

                            goto label_50;
                        }
                        while(true);

                        v8 = v12;
                    }

                label_50:
                    if(v9.materialId != null) {
                        v3 = this.materials.iterator();
                        do {
                            if(v3.hasNext()) {
                                v6 = v3.next();
                                if(!v9.materialId.equals(((Material)v6).id)) {
                                    continue;
                                }

                                break;
                            }

                            goto label_63;
                        }
                        while(true);

                        v7 = v6;
                    }

                label_63:
                    if(v8 != null && v7 != null) {
                        if(v8 != null && v7 != null) {
                            NodePart v11 = new NodePart();
                            v11.meshPart = ((MeshPart)v8);
                            v11.material = ((Material)v7);
                            v10.parts.add(v11);
                            if(v9.bones != null) {
                                this.nodePartBones.put(v11, v9.bones);
                            }
                        }

                        ++v4;
                        continue;
                    }

                    break;
                }

                goto label_89;
            }

            throw new GdxRuntimeException("Invalid node: " + v10.id);
        }

    label_89:
        if(modelNode.children != null) {
            ModelNode[] v1_1 = modelNode.children;
            v5 = v1_1.length;
            int v3_1;
            for(v3_1 = 0; v3_1 < v5; ++v3_1) {
                v10.addChild(this.loadNode(v1_1[v3_1]));
            }
        }

        return v10;
    }

    private void loadNodes(Iterable arg10) {
        this.nodePartBones.clear();
        Iterator v2 = arg10.iterator();
        while(v2.hasNext()) {
            this.nodes.add(this.loadNode(v2.next()));
        }

        v2 = this.nodePartBones.entries().iterator();
    label_13:
        if(!v2.hasNext()) {
            return;
        }

        Object v1 = v2.next();
        if(((Entry)v1).key.invBoneBindTransforms == null) {
            ((Entry)v1).key.invBoneBindTransforms = new ArrayMap(Node.class, Matrix4.class);
        }

        ((Entry)v1).key.invBoneBindTransforms.clear();
        Iterator v3 = ((Entry)v1).value.entries().iterator();
        while(true) {
            if(!v3.hasNext()) {
                goto label_13;
            }

            Object v0 = v3.next();
            ((Entry)v1).key.invBoneBindTransforms.put(this.getNode(((Entry)v0).key), new Matrix4(((Entry)v0).value).inv());
        }
    }

    public void manageDisposable(Disposable disposable) {
        if(!this.disposables.contains(disposable, true)) {
            this.disposables.add(disposable);
        }
    }
}

