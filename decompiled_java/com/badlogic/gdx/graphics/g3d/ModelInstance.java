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

import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class ModelInstance implements RenderableProvider {
    public final Array animations;
    public static boolean defaultShareKeyframes;
    public final Array materials;
    public final Model model;
    private ObjectMap nodePartBones;
    public final Array nodes;
    public Matrix4 transform;
    public Object userData;

    static  {
        ModelInstance.defaultShareKeyframes = true;
    }

    public ModelInstance(Model model) {
        this(model, null);
    }

    public ModelInstance(Model model, String[] rootNodeIds) {
        this(model, null, rootNodeIds);
    }

    public ModelInstance(Model model, float x, float y, float z) {
        this(model);
        this.transform.setToTranslation(x, y, z);
    }

    public ModelInstance(Model model, Matrix4 transform) {
        this(model, transform, null);
    }

    public ModelInstance(Model model, Matrix4 transform, String[] rootNodeIds) {
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.nodePartBones = new ObjectMap();
        this.model = model;
        if(transform == null) {
            transform = new Matrix4();
        }

        this.transform = transform;
        if(rootNodeIds == null) {
            this.copyNodes(model.nodes);
        }
        else {
            this.copyNodes(model.nodes, rootNodeIds);
        }

        this.copyAnimations(model.animations, ModelInstance.defaultShareKeyframes);
        this.calculateTransforms();
    }

    public ModelInstance(Model model, Matrix4 transform, Array arg4) {
        this(model, transform, arg4, ModelInstance.defaultShareKeyframes);
    }

    public ModelInstance(Model model, Matrix4 transform, Array arg4, boolean shareKeyframes) {
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.nodePartBones = new ObjectMap();
        this.model = model;
        if(transform == null) {
            transform = new Matrix4();
        }

        this.transform = transform;
        this.copyNodes(model.nodes, arg4);
        this.copyAnimations(model.animations, shareKeyframes);
        this.calculateTransforms();
    }

    public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean mergeTransform) {
        this(model, transform, nodeId, false, false, mergeTransform);
    }

    public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform) {
        this(model, transform, nodeId, recursive, parentTransform, mergeTransform, ModelInstance.defaultShareKeyframes);
    }

    public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean parentTransform, boolean mergeTransform) {
        this(model, transform, nodeId, true, parentTransform, mergeTransform);
    }

    public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform, boolean shareKeyframes) {
        Matrix4 v2_1;
        float v5 = 1f;
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.nodePartBones = new ObjectMap();
        this.model = model;
        if(transform == null) {
            transform = new Matrix4();
        }

        this.transform = transform;
        this.nodePartBones.clear();
        Node v1 = model.getNode(nodeId, recursive);
        Array v2 = this.nodes;
        Node v0 = this.copyNode(v1);
        v2.add(v0);
        if(mergeTransform) {
            Matrix4 v3 = this.transform;
            if(parentTransform) {
                v2_1 = v1.globalTransform;
            }
            else {
                v2_1 = v1.localTransform;
            }

            v3.mul(v2_1);
            v0.translation.set(0f, 0f, 0f);
            v0.rotation.idt();
            v0.scale.set(v5, v5, v5);
        }
        else {
            if(!parentTransform) {
                goto label_32;
            }

            if(!v0.hasParent()) {
                goto label_32;
            }

            this.transform.mul(v1.getParent().globalTransform);
        }

    label_32:
        this.setBones();
        this.copyAnimations(model.animations, shareKeyframes);
        this.calculateTransforms();
    }

    public ModelInstance(Model model, Vector3 position) {
        this(model);
        this.transform.setToTranslation(position);
    }

    public ModelInstance(Model model, Array arg3) {
        this(model, null, arg3);
    }

    public ModelInstance(Model model, String nodeId, boolean mergeTransform) {
        this(model, null, nodeId, false, false, mergeTransform);
    }

    public ModelInstance(Model model, String nodeId, boolean parentTransform, boolean mergeTransform) {
        this(model, null, nodeId, true, parentTransform, mergeTransform);
    }

    public ModelInstance(Model model, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform) {
        this(model, null, nodeId, recursive, parentTransform, mergeTransform);
    }

    public ModelInstance(ModelInstance copyFrom) {
        this(copyFrom, copyFrom.transform.cpy());
    }

    public ModelInstance(ModelInstance copyFrom, Matrix4 transform) {
        this(copyFrom, transform, ModelInstance.defaultShareKeyframes);
    }

    public ModelInstance(ModelInstance copyFrom, Matrix4 transform, boolean shareKeyframes) {
        super();
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.nodePartBones = new ObjectMap();
        this.model = copyFrom.model;
        if(transform == null) {
            transform = new Matrix4();
        }

        this.transform = transform;
        this.copyNodes(copyFrom.nodes);
        this.copyAnimations(copyFrom.animations, shareKeyframes);
        this.calculateTransforms();
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

    public ModelInstance copy() {
        return new ModelInstance(this);
    }

    private void copyAnimations(Iterable arg15, boolean shareKeyframes) {
        Object v6;
        Iterator v4;
        NodeAnimation v9;
        Node v8;
        Object v7;
        Animation v1;
        Iterator v2 = arg15.iterator();
        while(true) {
            if(!v2.hasNext()) {
                return;
            }

            Object v0 = v2.next();
            v1 = new Animation();
            v1.id = ((Animation)v0).id;
            v1.duration = ((Animation)v0).duration;
            Iterator v3 = ((Animation)v0).nodeAnimations.iterator();
            do {
            label_11:
                if(!v3.hasNext()) {
                    goto label_81;
                }

                v7 = v3.next();
                v8 = this.getNode(((NodeAnimation)v7).node.id);
            }
            while(v8 == null);

            v9 = new NodeAnimation();
            v9.node = v8;
            if(shareKeyframes) {
                v9.translation = ((NodeAnimation)v7).translation;
                v9.rotation = ((NodeAnimation)v7).rotation;
                v9.scaling = ((NodeAnimation)v7).scaling;
            }
            else {
                if(((NodeAnimation)v7).translation != null) {
                    v9.translation = new Array();
                    v4 = ((NodeAnimation)v7).translation.iterator();
                    break;
                label_81:
                    if(v1.nodeAnimations.size <= 0) {
                        continue;
                    }

                    this.animations.add(v1);
                    continue;
                }

                goto label_51;
            }

            goto label_27;
        }

        while(v4.hasNext()) {
            v6 = v4.next();
            v9.translation.add(new NodeKeyframe(((NodeKeyframe)v6).keytime, ((NodeKeyframe)v6).value));
        }

    label_51:
        if(((NodeAnimation)v7).rotation != null) {
            v9.rotation = new Array();
            v4 = ((NodeAnimation)v7).rotation.iterator();
            while(v4.hasNext()) {
                Object v5 = v4.next();
                v9.rotation.add(new NodeKeyframe(((NodeKeyframe)v5).keytime, ((NodeKeyframe)v5).value));
            }
        }

        if(((NodeAnimation)v7).scaling == null) {
            goto label_27;
        }

        v9.scaling = new Array();
        v4 = ((NodeAnimation)v7).scaling.iterator();
        while(v4.hasNext()) {
            v6 = v4.next();
            v9.scaling.add(new NodeKeyframe(((NodeKeyframe)v6).keytime, ((NodeKeyframe)v6).value));
        }

    label_27:
        if(v9.translation == null && v9.rotation == null && v9.scaling == null) {
            goto label_11;
        }

        v1.nodeAnimations.add(v9);
        goto label_11;
    }

    private Node copyNode(Node node) {
        Node v1 = new Node();
        v1.id = node.id;
        v1.inheritTransform = node.inheritTransform;
        v1.translation.set(node.translation);
        v1.rotation.set(node.rotation);
        v1.scale.set(node.scale);
        v1.localTransform.set(node.localTransform);
        v1.globalTransform.set(node.globalTransform);
        Iterator v2 = node.parts.iterator();
        while(v2.hasNext()) {
            v1.parts.add(this.copyNodePart(v2.next()));
        }

        v2 = node.getChildren().iterator();
        while(v2.hasNext()) {
            v1.addChild(this.copyNode(v2.next()));
        }

        return v1;
    }

    private NodePart copyNodePart(NodePart nodePart) {
        NodePart v0 = new NodePart();
        v0.meshPart = new MeshPart();
        v0.meshPart.id = nodePart.meshPart.id;
        v0.meshPart.indexOffset = nodePart.meshPart.indexOffset;
        v0.meshPart.numVertices = nodePart.meshPart.numVertices;
        v0.meshPart.primitiveType = nodePart.meshPart.primitiveType;
        v0.meshPart.mesh = nodePart.meshPart.mesh;
        if(nodePart.invBoneBindTransforms != null) {
            this.nodePartBones.put(v0, nodePart.invBoneBindTransforms);
        }

        int v1 = this.materials.indexOf(nodePart.material, false);
        if(v1 < 0) {
            Array v2 = this.materials;
            Material v3 = nodePart.material.copy();
            v0.material = v3;
            v2.add(v3);
        }
        else {
            v0.material = this.materials.get(v1);
        }

        return v0;
    }

    private void copyNodes(Array arg8, Array arg9) {
        this.nodePartBones.clear();
        int v0 = 0;
        int v2 = arg8.size;
        while(v0 < v2) {
            Object v3 = arg8.get(v0);
            Iterator v1 = arg9.iterator();
            do {
                if(v1.hasNext()) {
                    if(!v1.next().equals(((Node)v3).id)) {
                        continue;
                    }

                    break;
                }

                goto label_16;
            }
            while(true);

            this.nodes.add(this.copyNode(((Node)v3)));
        label_16:
            ++v0;
        }

        this.setBones();
    }

    private void copyNodes(Array arg6) {
        this.nodePartBones.clear();
        int v0 = 0;
        int v1 = arg6.size;
        while(v0 < v1) {
            this.nodes.add(this.copyNode(arg6.get(v0)));
            ++v0;
        }

        this.setBones();
    }

    private void copyNodes(Array arg10, String[] nodeIds) {
        this.nodePartBones.clear();
        int v1 = 0;
        int v4 = arg10.size;
        while(v1 < v4) {
            Object v5 = arg10.get(v1);
            String[] v0 = nodeIds;
            int v3 = v0.length;
            int v2 = 0;
            while(v2 < v3) {
                if(v0[v2].equals(((Node)v5).id)) {
                    this.nodes.add(this.copyNode(((Node)v5)));
                }
                else {
                    ++v2;
                    continue;
                }

                break;
            }

            ++v1;
        }

        this.setBones();
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
            Animation v0_1 = null;
        }

    label_10:
        return ((Animation)v0);
    }

    public Material getMaterial(String id) {
        return this.getMaterial(id, true);
    }

    public Material getMaterial(String id, boolean ignoreCase) {
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
            Material v1_1 = null;
        }

    label_10:
        return ((Material)v1);
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

    public Renderable getRenderable(Renderable out) {
        return this.getRenderable(out, this.nodes.get(0));
    }

    public Renderable getRenderable(Renderable out, Node node) {
        return this.getRenderable(out, node, node.parts.get(0));
    }

    public Renderable getRenderable(Renderable out, Node node, NodePart nodePart) {
        nodePart.setRenderable(out);
        if(nodePart.bones == null && this.transform != null) {
            out.worldTransform.set(this.transform).mul(node.globalTransform);
        }
        else if(this.transform != null) {
            out.worldTransform.set(this.transform);
        }
        else {
            out.worldTransform.idt();
        }

        out.userData = this.userData;
        return out;
    }

    protected void getRenderables(Node node, Array arg6, Pool arg7) {
        Iterator v1;
        if(node.parts.size > 0) {
            v1 = node.parts.iterator();
            while(v1.hasNext()) {
                Object v2 = v1.next();
                if(!((NodePart)v2).enabled) {
                    continue;
                }

                arg6.add(this.getRenderable(arg7.obtain(), node, ((NodePart)v2)));
            }
        }

        v1 = node.getChildren().iterator();
        while(v1.hasNext()) {
            this.getRenderables(v1.next(), arg6, arg7);
        }
    }

    public void getRenderables(Array arg4, Pool arg5) {
        Iterator v0 = this.nodes.iterator();
        while(v0.hasNext()) {
            this.getRenderables(v0.next(), arg4, arg5);
        }
    }

    private void setBones() {
        Iterator v3 = this.nodePartBones.entries().iterator();
    label_3:
        if(!v3.hasNext()) {
            return;
        }

        Object v1 = v3.next();
        if(((Entry)v1).key.invBoneBindTransforms == null) {
            ((Entry)v1).key.invBoneBindTransforms = new ArrayMap(true, ((Entry)v1).value.size, Node.class, Matrix4.class);
        }

        ((Entry)v1).key.invBoneBindTransforms.clear();
        Iterator v4 = ((Entry)v1).value.entries().iterator();
        while(v4.hasNext()) {
            Object v0 = v4.next();
            ((Entry)v1).key.invBoneBindTransforms.put(this.getNode(((Entry)v0).key.id), ((Entry)v0).value);
        }

        ((Entry)v1).key.bones = new Matrix4[((Entry)v1).value.size];
        int v2;
        for(v2 = 0; true; ++v2) {
            if(v2 >= ((Entry)v1).key.bones.length) {
                goto label_3;
            }

            ((Entry)v1).key.bones[v2] = new Matrix4();
        }
    }
}

