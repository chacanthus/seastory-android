// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.util.Iterator;

public class ResourceData implements Serializable {
    public class AssetData implements Serializable {
        public AssetData() {
            super();
        }

        public AssetData(String filename, Class arg2) {
            super();
            this.filename = filename;
            this.type = arg2;
        }

        public void read(Json json, JsonValue jsonData) {  // has try-catch handlers
            this.filename = json.readValue("filename", String.class, jsonData);
            Object v0 = json.readValue("type", String.class, jsonData);
            try {
                this.type = ClassReflection.forName(((String)v0));
                return;
            }
            catch(ReflectionException v1) {
                throw new GdxRuntimeException("Class not found: " + (((String)v0)), ((Throwable)v1));
            }
        }

        public void write(Json json) {
            json.writeValue("filename", this.filename);
            json.writeValue("type", this.type.getName());
        }
    }

    public abstract interface Configurable {
        public abstract void load(AssetManager arg0, ResourceData arg1);

        public abstract void save(AssetManager arg0, ResourceData arg1);
    }

    public class SaveData implements Serializable {
        IntArray assets;
        ObjectMap data;
        private int loadIndex;
        protected ResourceData resources;

        public SaveData(ResourceData resources) {
            super();
            this.data = new ObjectMap();
            this.assets = new IntArray();
            this.loadIndex = 0;
            this.resources = resources;
        }

        public SaveData() {
            super();
            this.data = new ObjectMap();
            this.assets = new IntArray();
            this.loadIndex = 0;
        }

        public Object load(String key) {
            return this.data.get(key);
        }

        public AssetDescriptor loadAsset() {
            AssetDescriptor v1;
            if(this.loadIndex == this.assets.size) {
                v1 = null;
            }
            else {
                Array v1_1 = this.resources.sharedAssets;
                IntArray v2 = this.assets;
                int v3 = this.loadIndex;
                this.loadIndex = v3 + 1;
                Object v0 = v1_1.get(v2.get(v3));
                v1 = new AssetDescriptor(((AssetData)v0).filename, ((AssetData)v0).type);
            }

            return v1;
        }

        public void read(Json json, JsonValue jsonData) {
            this.data = json.readValue("data", ObjectMap.class, jsonData);
            this.assets.addAll(json.readValue("indices", int[].class, jsonData));
        }

        public void save(String key, Object value) {
            this.data.put(key, value);
        }

        public void saveAsset(String filename, Class arg5) {
            int v0 = this.resources.getAssetData(filename, arg5);
            if(v0 == -1) {
                this.resources.sharedAssets.add(new AssetData(filename, arg5));
                v0 = this.resources.sharedAssets.size - 1;
            }

            this.assets.add(v0);
        }

        public void write(Json json) {
            json.writeValue("data", this.data, ObjectMap.class);
            json.writeValue("indices", this.assets.toArray(), int[].class);
        }
    }

    private int currentLoadIndex;
    private Array data;
    public Object resource;
    Array sharedAssets;
    private ObjectMap uniqueData;

    public ResourceData() {
        super();
        this.uniqueData = new ObjectMap();
        this.data = new Array(true, 3, SaveData.class);
        this.sharedAssets = new Array();
        this.currentLoadIndex = 0;
    }

    public ResourceData(Object arg1) {
        this();
        this.resource = arg1;
    }

    public SaveData createSaveData() {
        SaveData v0 = new SaveData(this);
        this.data.add(v0);
        return v0;
    }

    public SaveData createSaveData(String key) {
        SaveData v0 = new SaveData(this);
        if(this.uniqueData.containsKey(key)) {
            throw new RuntimeException("Key already used, data must be unique, use a different key");
        }

        this.uniqueData.put(key, v0);
        return v0;
    }

    int getAssetData(String filename, Class arg6) {
        int v1 = 0;
        Iterator v2 = this.sharedAssets.iterator();
        while(v2.hasNext()) {
            Object v0 = v2.next();
            if((((AssetData)v0).filename.equals(filename)) && (((AssetData)v0).type.equals(arg6))) {
                goto label_12;
            }

            ++v1;
        }

        v1 = -1;
    label_12:
        return v1;
    }

    public Array getAssetDescriptors() {
        Array v1 = new Array();
        Iterator v2 = this.sharedAssets.iterator();
        while(v2.hasNext()) {
            Object v0 = v2.next();
            v1.add(new AssetDescriptor(((AssetData)v0).filename, ((AssetData)v0).type));
        }

        return v1;
    }

    public Array getAssets() {
        return this.sharedAssets;
    }

    public SaveData getSaveData() {
        Array v0 = this.data;
        int v1 = this.currentLoadIndex;
        this.currentLoadIndex = v1 + 1;
        return v0.get(v1);
    }

    public SaveData getSaveData(String key) {
        return this.uniqueData.get(key);
    }

    public void read(Json json, JsonValue jsonData) {
        this.uniqueData = json.readValue("unique", ObjectMap.class, jsonData);
        Iterator v1 = this.uniqueData.entries().iterator();
        while(v1.hasNext()) {
            v1.next().value.resources = this;
        }

        this.data = json.readValue("data", Array.class, SaveData.class, jsonData);
        v1 = this.data.iterator();
        while(v1.hasNext()) {
            v1.next().resources = this;
        }

        this.sharedAssets.addAll(json.readValue("assets", Array.class, AssetData.class, jsonData));
        this.resource = json.readValue("resource", null, jsonData);
    }

    public void write(Json json) {
        json.writeValue("unique", this.uniqueData, ObjectMap.class);
        json.writeValue("data", this.data, Array.class, SaveData.class);
        json.writeValue("assets", this.sharedAssets.toArray(AssetData.class), AssetData[].class);
        json.writeValue("resource", this.resource, null);
    }
}

