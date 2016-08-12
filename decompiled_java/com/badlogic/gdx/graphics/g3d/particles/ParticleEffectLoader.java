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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import java.io.IOException;
import java.util.Iterator;

public class ParticleEffectLoader extends AsynchronousAssetLoader {
    public class ParticleEffectLoadParameter extends AssetLoaderParameters {
        public ParticleEffectLoadParameter(Array arg1) {
            super();
            this.batches = arg1;
        }
    }

    public class ParticleEffectSaveParameter extends AssetLoaderParameters {
        public ParticleEffectSaveParameter(FileHandle file, AssetManager manager, Array arg3) {
            super();
            this.batches = arg3;
            this.file = file;
            this.manager = manager;
        }
    }

    protected Array items;

    public ParticleEffectLoader(FileHandleResolver resolver) {
        super(resolver);
        this.items = new Array();
    }

    private Object find(Array arg4, Class arg5) {
        Object v1;
        Iterator v0 = arg4.iterator();
        do {
            if(v0.hasNext()) {
                v1 = v0.next();
                if(!ClassReflection.isAssignableFrom(arg5, v1.getClass())) {
                    continue;
                }
            }
            else {
                break;
            }

            goto label_7;
        }
        while(true);

        v1 = null;
    label_7:
        return v1;
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((ParticleEffectLoadParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, ParticleEffectLoadParameter parameter) {  // has try-catch handlers
        Array v3;
        Array v1;
        Object v2 = new Json().fromJson(ResourceData.class, file);
        try {
            Entry v4 = new Entry();
            v4.key = fileName;
            v4.value = v2;
            this.items.add(v4);
            v1 = ((ResourceData)v2).getAssets();
            v3 = new Array();
        }
        catch(Throwable v7) {
            try {
            label_37:
                throw v7;
            }
            catch(Throwable v7) {
                goto label_37;
            }
        }

        Iterator v5 = v1.iterator();
        while(true) {
            if(!v5.hasNext()) {
                break;
            }

            Object v0 = v5.next();
            if(!this.resolve(((AssetData)v0).filename).exists()) {
                ((AssetData)v0).filename = file.parent().child(Gdx.files.internal(((AssetData)v0).filename).name()).path();
            }

            if(((AssetData)v0).type != ParticleEffect.class) {
                goto label_38;
            }

            v3.add(new AssetDescriptor(((AssetData)v0).filename, ((AssetData)v0).type, ((AssetLoaderParameters)parameter)));
            continue;
        label_38:
            v3.add(new AssetDescriptor(((AssetData)v0).filename, ((AssetData)v0).type));
        }

        return v3;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((ParticleEffectLoadParameter)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, ParticleEffectLoadParameter parameter) {
    }

    public ParticleEffect loadSync(AssetManager manager, String fileName, FileHandle file, ParticleEffectLoadParameter parameter) {  // has try-catch handlers
        Object v6_1;
        Object v2_1;
        ResourceData v2 = null;
        int v4 = 0;
        try {
            while(true) {
            label_3:
                if(v4 < this.items.size) {
                    Object v3 = this.items.get(v4);
                    if(((Entry)v3).key.equals(fileName)) {
                        v2_1 = ((Entry)v3).value;
                        this.items.removeIndex(v4);
                    }
                    else {
                        break;
                    }
                }

                goto label_16;
            }
        }
        catch(Throwable v6) {
            goto label_31;
        }

        ++v4;
        goto label_3;
        try {
        label_16:
            v6_1 = ((ResourceData)v2_1).resource;
        }
        catch(Throwable v6) {
            goto label_31;
        }

        ((ParticleEffect)v6_1).load(manager, ((ResourceData)v2_1));
        if(parameter != null) {
            if(parameter.batches != null) {
                Iterator v5 = parameter.batches.iterator();
                while(v5.hasNext()) {
                    v5.next().load(manager, ((ResourceData)v2_1));
                }
            }

            ((ResourceData)v2_1).resource.setBatch(parameter.batches);
        }

        return ((ResourceData)v2_1).resource;
        try {
        label_31:
            throw v6;
        }
        catch(Throwable v6) {
            goto label_31;
        }
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((ParticleEffectLoadParameter)x3));
    }

    public void save(ParticleEffect effect, ParticleEffectSaveParameter parameter) throws IOException {
        ResourceData v2 = new ResourceData(effect);
        effect.save(parameter.manager, v2);
        if(parameter.batches != null) {
            Iterator v3 = parameter.batches.iterator();
            while(v3.hasNext()) {
                Object v0 = v3.next();
                int v6 = 0;
                Iterator v4 = effect.getControllers().iterator();
                do {
                    if(v4.hasNext()) {
                        if(!v4.next().renderer.isCompatible(((ParticleBatch)v0))) {
                            continue;
                        }

                        break;
                    }

                    goto label_20;
                }
                while(true);

                v6 = 1;
            label_20:
                if(v6 == 0) {
                    continue;
                }

                ((ParticleBatch)v0).save(parameter.manager, v2);
            }
        }

        new Json().toJson(v2, parameter.file);
    }
}

