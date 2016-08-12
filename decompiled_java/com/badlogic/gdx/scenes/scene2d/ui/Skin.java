// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.util.Iterator;

public class Skin implements Disposable {
    public class TintedDrawable {
        public Color color;
        public String name;

        public TintedDrawable() {
            super();
        }
    }

    TextureAtlas atlas;
    ObjectMap resources;

    public Skin() {
        super();
        this.resources = new ObjectMap();
    }

    public Skin(FileHandle skinFile) {
        super();
        this.resources = new ObjectMap();
        FileHandle v0 = skinFile.sibling(skinFile.nameWithoutExtension() + ".atlas");
        if(v0.exists()) {
            this.atlas = new TextureAtlas(v0);
            this.addRegions(this.atlas);
        }

        this.load(skinFile);
    }

    public Skin(FileHandle skinFile, TextureAtlas atlas) {
        super();
        this.resources = new ObjectMap();
        this.atlas = atlas;
        this.addRegions(atlas);
        this.load(skinFile);
    }

    public Skin(TextureAtlas atlas) {
        super();
        this.resources = new ObjectMap();
        this.atlas = atlas;
        this.addRegions(atlas);
    }

    public void add(String name, Object resource) {
        this.add(name, resource, resource.getClass());
    }

    public void add(String name, Object resource, Class type) {
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }

        if(resource == null) {
            throw new IllegalArgumentException("resource cannot be null.");
        }

        Object v0 = this.resources.get(type);
        if(v0 == null) {
            ObjectMap v0_1 = new ObjectMap();
            this.resources.put(type, v0_1);
        }

        ((ObjectMap)v0).put(name, resource);
    }

    public void addRegions(TextureAtlas atlas) {
        Array v3 = atlas.getRegions();
        int v0 = 0;
        int v1 = v3.size;
        while(v0 < v1) {
            Object v2 = v3.get(v0);
            this.add(((AtlasRegion)v2).name, v2, TextureRegion.class);
            ++v0;
        }
    }

    public void dispose() {
        if(this.atlas != null) {
            this.atlas.dispose();
        }

        Iterator v1 = this.resources.values().iterator();
    label_7:
        if(!v1.hasNext()) {
            return;
        }

        Iterator v2 = v1.next().values().iterator();
        while(true) {
            if(!v2.hasNext()) {
                goto label_7;
            }

            Object v3 = v2.next();
            if(!(v3 instanceof Disposable)) {
                continue;
            }

            ((Disposable)v3).dispose();
        }
    }

    public String find(Object resource) {
        Object v1_1;
        if(resource == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        Object v0 = this.resources.get(resource.getClass());
        if(v0 == null) {
            String v1 = null;
        }
        else {
            v1_1 = ((ObjectMap)v0).findKey(resource, true);
        }

        return ((String)v1_1);
    }

    private static Method findMethod(Class type, String name) {
        Method v1;
        Method[] v2 = ClassReflection.getMethods(type);
        int v0 = 0;
        int v3 = v2.length;
        while(true) {
            if(v0 < v3) {
                v1 = v2[v0];
                if(!v1.getName().equals(name)) {
                    ++v0;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }

        v1 = null;
    label_8:
        return v1;
    }

    public Object get(Class arg2) {
        return this.get("default", arg2);
    }

    public Object get(String name, Class arg7) {
        NinePatch v0_2;
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }

        if(arg7 == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }

        if(arg7 == Drawable.class) {
            Drawable v0 = this.getDrawable(name);
        }
        else if(arg7 == TextureRegion.class) {
            TextureRegion v0_1 = this.getRegion(name);
        }
        else if(arg7 == NinePatch.class) {
            v0_2 = this.getPatch(name);
        }
        else if(arg7 == Sprite.class) {
            Sprite v0_3 = this.getSprite(name);
        }
        else {
            Object v1 = this.resources.get(arg7);
            if(v1 == null) {
                throw new GdxRuntimeException("No " + arg7.getName() + " registered with name: " + name);
            }
            else {
                Object v0_4 = ((ObjectMap)v1).get(name);
                if(v0_4 == null) {
                    throw new GdxRuntimeException("No " + arg7.getName() + " registered with name: " + name);
                }
            }
        }

        return v0_2;
    }

    public ObjectMap getAll(Class arg2) {
        return this.resources.get(arg2);
    }

    public TextureAtlas getAtlas() {
        return this.atlas;
    }

    public Color getColor(String name) {
        return this.get(name, Color.class);
    }

    public Drawable getDrawable(String name) {  // has try-catch handlers
        SpriteDrawable v1_3;
        NinePatchDrawable v1_2;
        SpriteDrawable v2_2;
        TextureRegion v6;
        Object v1 = this.optional(name, Drawable.class);
        if(v1 == null) {
            goto label_5;
        }

        Object v2 = v1;
        goto label_4;
    label_5:
        v1 = this.optional(name, TiledDrawable.class);
        if(v1 == null) {
            goto label_10;
        }

        v2 = v1;
        goto label_4;
        try {
        label_10:
            v6 = this.getRegion(name);
            if((v6 instanceof AtlasRegion)) {
                TextureRegion v4 = v6;
                if(((AtlasRegion)v4).splits != null) {
                    NinePatchDrawable v2_1 = new NinePatchDrawable(this.getPatch(name));
                }
                else {
                    if(!((AtlasRegion)v4).rotate && ((AtlasRegion)v4).packedWidth == ((AtlasRegion)v4).originalWidth && ((AtlasRegion)v4).packedHeight == ((AtlasRegion)v4).originalHeight) {
                        goto label_64;
                    }

                    v2_2 = new SpriteDrawable(this.getSprite(name));
                }
            }
            else {
                goto label_64;
            }

            goto label_19;
        }
        catch(GdxRuntimeException v7) {
            goto label_21;
        }

    label_64:
        v2 = v1;
    label_19:
        if((((NinePatchDrawable)v2_2)) != null) {
            goto label_62;
        }

        try {
            TextureRegionDrawable v1_1 = new TextureRegionDrawable(v6);
            goto label_21;
        }
        catch(GdxRuntimeException v7) {
            v1_2 = ((NinePatchDrawable)v2_2);
            goto label_21;
        }

    label_62:
        v1_2 = ((NinePatchDrawable)v2_2);
    label_21:
        if(v1_1 == null) {
            Object v3 = this.optional(name, NinePatch.class);
            if(v3 != null) {
                v1_2 = new NinePatchDrawable(((NinePatch)v3));
            }
            else {
                Object v5 = this.optional(name, Sprite.class);
                if(v5 != null) {
                    v1_3 = new SpriteDrawable(((Sprite)v5));
                }
                else {
                    throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + name);
                }
            }
        }

        if((v1_3 instanceof BaseDrawable)) {
            v1_3.setName(name);
        }

        this.add(name, v1_3, Drawable.class);
        v2 = v1_3;
    label_4:
        return ((Drawable)v2);
    }

    public BitmapFont getFont(String name) {
        return this.get(name, BitmapFont.class);
    }

    protected Json getJsonLoader(FileHandle skinFile) {
        com.badlogic.gdx.scenes.scene2d.ui.Skin$1 v0 = new Json() {
            public Object readValue(Class arg3, Class elementType, JsonValue jsonData) {
                Object v0;
                if(!jsonData.isString() || (ClassReflection.isAssignableFrom(CharSequence.class, arg3))) {
                    v0 = super.readValue(arg3, elementType, jsonData);
                }
                else {
                    v0 = Skin.this.get(jsonData.asString(), arg3);
                }

                return v0;
            }
        };
        ((Json)v0).setTypeName(null);
        ((Json)v0).setUsePrototypes(false);
        ((Json)v0).setSerializer(Skin.class, new ReadOnlySerializer() {
            public Skin read(Json json, JsonValue typeToValueMap, Class ignored) {  // has try-catch handlers
                JsonValue v1;
                for(v1 = typeToValueMap.child; v1 != null; v1 = v1.next) {
                    try {
                        this.readNamedObjects(json, ClassReflection.forName(v1.name()), v1);
                    }
                    catch(ReflectionException v0) {
                        throw new SerializationException(((Throwable)v0));
                    }
                }

                return this.val$skin;
            }

            public Object read(Json x0, JsonValue x1, Class x2) {
                return this.read(x0, x1, x2);
            }

            private void readNamedObjects(Json json, Class type, JsonValue valueMap) {  // has try-catch handlers
                Class v0;
                if(type == TintedDrawable.class) {
                    v0 = Drawable.class;
                }
                else {
                    v0 = type;
                }

                JsonValue v3;
                for(v3 = valueMap.child; v3 != null; v3 = v3.next) {
                    Object v2 = json.readValue(type, v3);
                    if(v2 != null) {
                        try {
                            Skin.this.add(v3.name(), v2, v0);
                        }
                        catch(Exception v1) {
                            throw new SerializationException("Error reading " + ClassReflection.getSimpleName(type) + ": " + v3.name(), ((Throwable)v1));
                        }
                    }
                }
            }
        });
        ((Json)v0).setSerializer(BitmapFont.class, new ReadOnlySerializer() {
            public BitmapFont read(Json json, JsonValue jsonData, Class type) {  // has try-catch handlers
                BitmapFont v2;
                Object v6 = json.readValue("file", String.class, jsonData);
                int v9 = json.readValue("scaledSize", Integer.TYPE, Integer.valueOf(-1), jsonData).intValue();
                Object v1 = json.readValue("flip", Boolean.class, Boolean.valueOf(false), jsonData);
                Object v5 = json.readValue("markupEnabled", Boolean.class, Boolean.valueOf(false), jsonData);
                FileHandle v3 = this.val$skinFile.parent().child(((String)v6));
                if(!v3.exists()) {
                    v3 = Gdx.files.internal(((String)v6));
                }

                if(!v3.exists()) {
                    throw new SerializationException("Font file not found: " + v3);
                }

                String v8 = v3.nameWithoutExtension();
                try {
                    Object v7 = this.val$skin.optional(v8, TextureRegion.class);
                    if(v7 != null) {
                        v2 = new BitmapFont(v3, ((TextureRegion)v7), ((Boolean)v1).booleanValue());
                    }
                    else {
                        FileHandle v4 = v3.parent().child(v8 + ".png");
                        if(v4.exists()) {
                            v2 = new BitmapFont(v3, v4, ((Boolean)v1).booleanValue());
                        }
                        else {
                            v2 = new BitmapFont(v3, ((Boolean)v1).booleanValue());
                        }
                    }

                    v2.getData().markupEnabled = ((Boolean)v5).booleanValue();
                    if(v9 != -1) {
                        v2.getData().setScale((((float)v9)) / v2.getCapHeight());
                    }

                    return v2;
                }
                catch(RuntimeException v0) {
                    throw new SerializationException("Error loading bitmap font: " + v3, ((Throwable)v0));
                }
            }

            public Object read(Json x0, JsonValue x1, Class x2) {
                return this.read(x0, x1, x2);
            }
        });
        ((Json)v0).setSerializer(Color.class, new ReadOnlySerializer() {
            public Color read(Json json, JsonValue jsonData, Class type) {
                Color v5_1;
                Object v5;
                if(jsonData.isString()) {
                    v5 = Skin.this.get(jsonData.asString(), Color.class);
                }
                else {
                    Object v3 = json.readValue("hex", String.class, null, jsonData);
                    if(v3 != null) {
                        v5_1 = Color.valueOf(((String)v3));
                    }
                    else {
                        v5_1 = new Color(json.readValue("r", Float.TYPE, Float.valueOf(0f), jsonData).floatValue(), json.readValue("g", Float.TYPE, Float.valueOf(0f), jsonData).floatValue(), json.readValue("b", Float.TYPE, Float.valueOf(0f), jsonData).floatValue(), json.readValue("a", Float.TYPE, Float.valueOf(1f), jsonData).floatValue());
                    }
                }

                return ((Color)v5);
            }

            public Object read(Json x0, JsonValue x1, Class x2) {
                return this.read(x0, x1, x2);
            }
        });
        ((Json)v0).setSerializer(TintedDrawable.class, new ReadOnlySerializer() {
            public Object read(Json json, JsonValue jsonData, Class type) {
                Object v2 = json.readValue("name", String.class, jsonData);
                Object v0 = json.readValue("color", Color.class, jsonData);
                Drawable v1 = Skin.this.newDrawable(((String)v2), ((Color)v0));
                if((v1 instanceof BaseDrawable)) {
                    v1.setName(jsonData.name + " (" + (((String)v2)) + ", " + v0 + ")");
                }

                return v1;
            }
        });
        return ((Json)v0);
    }

    public NinePatch getPatch(String name) {  // has try-catch handlers
        Object v1_1;
        NinePatch v9_1;
        NinePatch v1;
        TextureRegion v2;
        Object v9 = this.optional(name, NinePatch.class);
        if(v9 != null) {
        }
        else {
            try {
                v2 = this.getRegion(name);
                if((v2 instanceof AtlasRegion)) {
                    int[] v10 = v2.splits;
                    if(v10 != null) {
                        v1 = new NinePatch(v2, v10[0], v10[1], v10[2], v10[3]);
                        goto label_21;
                    }
                }

                goto label_35;
            }
            catch(GdxRuntimeException v7) {
                goto label_43;
            }

            try {
            label_21:
                int[] v8 = v2.pads;
                if(v8 != null) {
                    v1.setPadding(v8[0], v8[1], v8[2], v8[3]);
                }
            }
            catch(GdxRuntimeException v7) {
                goto label_51;
            }

            v9_1 = v1;
        label_35:
            if(v9_1 == null) {
                try {
                    v1 = new NinePatch(v2);
                    goto label_37;
                }
                catch(GdxRuntimeException v7) {
                    goto label_43;
                }
            }
            else {
                v1_1 = v9_1;
            }

            try {
            label_37:
                this.add(name, v1_1, NinePatch.class);
                v9_1 = ((NinePatch)v1_1);
                goto label_4;
            }
            catch(GdxRuntimeException v7) {
            label_51:
            }

        label_43:
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
        }

    label_4:
        return v9_1;
    }

    public TextureRegion getRegion(String name) {
        Object v1;
        Object v0 = this.optional(name, TextureRegion.class);
        if(v0 != null) {
            v1 = v0;
        }
        else {
            Object v2 = this.optional(name, Texture.class);
            if(v2 == null) {
                throw new GdxRuntimeException("No TextureRegion or Texture registered with name: " + name);
            }
            else {
                TextureRegion v0_1 = new TextureRegion(((Texture)v2));
                this.add(name, v0_1, TextureRegion.class);
                TextureRegion v1_1 = v0_1;
            }
        }

        return ((TextureRegion)v1);
    }

    public Sprite getSprite(String name) {  // has try-catch handlers
        Sprite v4_2;
        Sprite v3_1;
        TextureRegion v5;
        Object v4;
        Object v3 = this.optional(name, Sprite.class);
        if(v3 != null) {
            v4 = v3;
        }
        else {
            try {
                v5 = this.getRegion(name);
                if((v5 instanceof AtlasRegion)) {
                    TextureRegion v2 = v5;
                    if(!((AtlasRegion)v2).rotate && ((AtlasRegion)v2).packedWidth == ((AtlasRegion)v2).originalWidth && ((AtlasRegion)v2).packedHeight == ((AtlasRegion)v2).originalHeight) {
                        goto label_38;
                    }

                    AtlasSprite v4_1 = new AtlasSprite(((AtlasRegion)v2));
                }
                else {
                    goto label_38;
                }

                goto label_19;
            }
            catch(GdxRuntimeException v1) {
                goto label_26;
            }

        label_38:
            v4 = v3;
        label_19:
            if((((AtlasSprite)v4)) == null) {
                try {
                    v3_1 = new Sprite(v5);
                    goto label_21;
                }
                catch(GdxRuntimeException v1) {
                    goto label_26;
                }
            }
            else {
                AtlasSprite v3_2 = ((AtlasSprite)v4);
            }

            try {
            label_21:
                this.add(name, v3_1, Sprite.class);
                v4_2 = v3_1;
                goto label_4;
            }
            catch(GdxRuntimeException v1) {
            }

        label_26:
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
        }

    label_4:
        return v4_2;
    }

    public TiledDrawable getTiledDrawable(String name) {
        Object v1;
        Object v0 = this.optional(name, TiledDrawable.class);
        if(v0 != null) {
            v1 = v0;
        }
        else {
            TiledDrawable v0_1 = new TiledDrawable(this.getRegion(name));
            v0_1.setName(name);
            this.add(name, v0_1, TiledDrawable.class);
            TiledDrawable v1_1 = v0_1;
        }

        return ((TiledDrawable)v1);
    }

    public boolean has(String name, Class type) {
        boolean v1;
        Object v0 = this.resources.get(type);
        if(v0 == null) {
            v1 = false;
        }
        else {
            v1 = ((ObjectMap)v0).containsKey(name);
        }

        return v1;
    }

    public void load(FileHandle skinFile) {  // has try-catch handlers
        try {
            this.getJsonLoader(skinFile).fromJson(Skin.class, skinFile);
            return;
        }
        catch(SerializationException v0) {
            throw new SerializationException("Error reading file: " + skinFile, ((Throwable)v0));
        }
    }

    public Drawable newDrawable(Drawable drawable) {
        NinePatchDrawable v0_1;
        if((drawable instanceof TextureRegionDrawable)) {
            TextureRegionDrawable v0 = new TextureRegionDrawable(((TextureRegionDrawable)drawable));
        }
        else if((drawable instanceof NinePatchDrawable)) {
            v0_1 = new NinePatchDrawable(((NinePatchDrawable)drawable));
        }
        else if((drawable instanceof SpriteDrawable)) {
            SpriteDrawable v0_2 = new SpriteDrawable(((SpriteDrawable)drawable));
        }
        else {
            goto label_12;
        }

        return ((Drawable)v0_1);
    label_12:
        throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
    }

    public Drawable newDrawable(Drawable drawable, float r, float g, float b, float a) {
        return this.newDrawable(drawable, new Color(r, g, b, a));
    }

    public Drawable newDrawable(Drawable drawable, Color tint) {
        NinePatchDrawable v1_1;
        SpriteDrawable v1;
        if((drawable instanceof TextureRegionDrawable)) {
            v1 = drawable.tint(tint);
        }
        else if((drawable instanceof NinePatchDrawable)) {
            v1_1 = drawable.tint(tint);
        }
        else if((drawable instanceof SpriteDrawable)) {
            v1 = drawable.tint(tint);
        }
        else {
            goto label_30;
        }

        if(((((SpriteDrawable)v1_1)) instanceof BaseDrawable)) {
            SpriteDrawable v0 = ((SpriteDrawable)v1_1);
            if((drawable instanceof BaseDrawable)) {
                ((BaseDrawable)v0).setName(((BaseDrawable)drawable).getName() + " (" + tint + ")");
            }
            else {
                ((BaseDrawable)v0).setName(" (" + tint + ")");
            }
        }

        return ((Drawable)v1_1);
    label_30:
        throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
    }

    public Drawable newDrawable(String name) {
        return this.newDrawable(this.getDrawable(name));
    }

    public Drawable newDrawable(String name, float r, float g, float b, float a) {
        return this.newDrawable(this.getDrawable(name), new Color(r, g, b, a));
    }

    public Drawable newDrawable(String name, Color tint) {
        return this.newDrawable(this.getDrawable(name), tint);
    }

    public Object optional(String name, Class arg5) {
        Object v1;
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }

        if(arg5 == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }

        Object v0 = this.resources.get(arg5);
        if(v0 == null) {
            v1 = null;
        }
        else {
            v1 = ((ObjectMap)v0).get(name);
        }

        return v1;
    }

    public void remove(String name, Class type) {
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }

        this.resources.get(type).remove(name);
    }

    public void setEnabled(Actor actor, boolean enabled) {  // has try-catch handlers
        String v4;
        Object v3;
        Method v1 = Skin.findMethod(actor.getClass(), "getStyle");
        if(v1 != null) {
            goto label_6;
        }

        return;
        try {
        label_6:
            v3 = v1.invoke(actor, new Object[0]);
        }
        catch(Exception v0) {
            return;
        }

        String v2 = this.find(v3);
        if(v2 == null) {
            return;
        }

        StringBuilder v5 = new StringBuilder().append(v2.replace("-disabled", ""));
        if(enabled) {
            v4 = "";
        }
        else {
            v4 = "-disabled";
        }

        v3 = this.get(v5.append(v4).toString(), v3.getClass());
        v1 = Skin.findMethod(actor.getClass(), "setStyle");
        if(v1 == null) {
            return;
        }

        try {
            Object[] v4_2 = new Object[1];
            v4_2[0] = v3;
            v1.invoke(actor, v4_2);
        }
        catch(Exception v4_1) {
        }
    }
}

