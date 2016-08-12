// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

public class ParticleEffect implements Disposable {
    private BoundingBox bounds;
    private final Array emitters;
    private boolean ownsTexture;

    public ParticleEffect() {
        super();
        this.emitters = new Array(8);
    }

    public ParticleEffect(ParticleEffect effect) {
        super();
        this.emitters = new Array(true, effect.emitters.size);
        int v0 = 0;
        int v1 = effect.emitters.size;
        while(v0 < v1) {
            this.emitters.add(new ParticleEmitter(effect.emitters.get(v0)));
            ++v0;
        }
    }

    public void allowCompletion() {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).allowCompletion();
            ++v0;
        }
    }

    public void dispose() {
        if(this.ownsTexture) {
            int v1 = 0;
            int v2 = this.emitters.size;
            while(v1 < v2) {
                this.emitters.get(v1).getSprite().getTexture().dispose();
                ++v1;
            }
        }
    }

    public void draw(Batch spriteBatch) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).draw(spriteBatch);
            ++v0;
        }
    }

    public void draw(Batch spriteBatch, float delta) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).draw(spriteBatch, delta);
            ++v0;
        }
    }

    public ParticleEmitter findEmitter(String name) {
        int v1 = 0;
        int v2 = this.emitters.size;
        while(true) {
            if(v1 < v2) {
                Object v0 = this.emitters.get(v1);
                if(!((ParticleEmitter)v0).getName().equals(name)) {
                    ++v1;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_9;
        }

        ParticleEmitter v0_1 = null;
    label_9:
        return v0_1;
    }

    public void flipY() {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).flipY();
            ++v0;
        }
    }

    public BoundingBox getBoundingBox() {
        if(this.bounds == null) {
            this.bounds = new BoundingBox();
        }

        BoundingBox v0 = this.bounds;
        v0.inf();
        Iterator v2 = this.emitters.iterator();
        while(v2.hasNext()) {
            v0.ext(v2.next().getBoundingBox());
        }

        return v0;
    }

    public Array getEmitters() {
        return this.emitters;
    }

    public boolean isComplete() {
        boolean v3;
        int v1 = 0;
        int v2 = this.emitters.size;
        while(true) {
            if(v1 >= v2) {
                break;
            }
            else if(!this.emitters.get(v1).isComplete()) {
                v3 = false;
            }
            else {
                ++v1;
                continue;
            }

            goto label_9;
        }

        v3 = true;
    label_9:
        return v3;
    }

    public void load(FileHandle effectFile, TextureAtlas atlas, String atlasPrefix) {
        this.loadEmitters(effectFile);
        this.loadEmitterImages(atlas, atlasPrefix);
    }

    public void load(FileHandle effectFile, FileHandle imagesDir) {
        this.loadEmitters(effectFile);
        this.loadEmitterImages(imagesDir);
    }

    public void load(FileHandle effectFile, TextureAtlas atlas) {
        this.load(effectFile, atlas, null);
    }

    public void loadEmitterImages(FileHandle imagesDir) {
        this.ownsTexture = true;
        HashMap v4 = new HashMap(this.emitters.size);
        int v1 = 0;
        int v5 = this.emitters.size;
        while(v1 < v5) {
            Object v0 = this.emitters.get(v1);
            String v3 = ((ParticleEmitter)v0).getImagePath();
            if(v3 != null) {
                String v2 = new File(v3.replace('\\', '/')).getName();
                Object v6 = v4.get(v2);
                if(v6 == null) {
                    Sprite v6_1 = new Sprite(this.loadTexture(imagesDir.child(v2)));
                    v4.put(v2, v6_1);
                }

                ((ParticleEmitter)v0).setSprite(((Sprite)v6));
            }

            ++v1;
        }
    }

    public void loadEmitterImages(TextureAtlas atlas, String atlasPrefix) {
        int v1 = 0;
        int v5 = this.emitters.size;
        while(v1 < v5) {
            Object v0 = this.emitters.get(v1);
            String v3 = ((ParticleEmitter)v0).getImagePath();
            if(v3 != null) {
                String v2 = new File(v3.replace('\\', '/')).getName();
                int v4 = v2.lastIndexOf(46);
                if(v4 != -1) {
                    v2 = v2.substring(0, v4);
                }

                if(atlasPrefix != null) {
                    v2 = atlasPrefix + v2;
                }

                Sprite v6 = atlas.createSprite(v2);
                if(v6 == null) {
                    throw new IllegalArgumentException("SpriteSheet missing image: " + v2);
                }

                ((ParticleEmitter)v0).setSprite(v6);
            }

            ++v1;
        }
    }

    public void loadEmitterImages(TextureAtlas atlas) {
        this.loadEmitterImages(atlas, null);
    }

    public void loadEmitters(FileHandle effectFile) {  // has try-catch handlers
        BufferedReader v3_1;
        BufferedReader v4;
        InputStream v2 = effectFile.read();
        this.emitters.clear();
        Closeable v3 = null;
        try {
            v4 = new BufferedReader(new InputStreamReader(v2), 512);
            goto label_7;
        }
        catch(Throwable v5) {
        }
        catch(IOException v1) {
            goto label_18;
            try {
                do {
                label_7:
                    this.emitters.add(new ParticleEmitter(v4));
                    if(v4.readLine() != null && v4.readLine() != null) {
                        continue;
                    }

                    break;
                }
                while(true);
            }
            catch(Throwable v5) {
                v3_1 = v4;
                goto label_26;
            }
            catch(IOException v1) {
                v3_1 = v4;
                goto label_18;
            }

            StreamUtils.closeQuietly(((Closeable)v4));
            return;
            try {
            label_18:
                throw new GdxRuntimeException("Error loading effect: " + effectFile, ((Throwable)v1));
            }
            catch(Throwable v5) {
            }
        }

    label_26:
        StreamUtils.closeQuietly(((Closeable)v3_1));
        throw v5;
    }

    protected Texture loadTexture(FileHandle file) {
        return new Texture(file, false);
    }

    public void reset() {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).reset();
            ++v0;
        }
    }

    public void save(Writer output) throws IOException {
        int v1 = 0;
        int v4 = this.emitters.size;
        int v3;
        for(v3 = 0; v1 < v4; v3 = v2) {
            Object v0 = this.emitters.get(v1);
            int v2 = v3 + 1;
            if(v3 > 0) {
                output.write("\n\n");
            }

            ((ParticleEmitter)v0).save(output);
            ++v1;
        }
    }

    public void scaleEffect(float scaleFactor) {
        Iterator v0 = this.emitters.iterator();
        while(v0.hasNext()) {
            Object v1 = v0.next();
            ((ParticleEmitter)v1).getScale().setHigh(((ParticleEmitter)v1).getScale().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getScale().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getScale().setLow(((ParticleEmitter)v1).getScale().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getScale().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getVelocity().setHigh(((ParticleEmitter)v1).getVelocity().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getVelocity().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getVelocity().setLow(((ParticleEmitter)v1).getVelocity().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getVelocity().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getGravity().setHigh(((ParticleEmitter)v1).getGravity().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getGravity().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getGravity().setLow(((ParticleEmitter)v1).getGravity().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getGravity().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getWind().setHigh(((ParticleEmitter)v1).getWind().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getWind().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getWind().setLow(((ParticleEmitter)v1).getWind().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getWind().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getSpawnWidth().setHigh(((ParticleEmitter)v1).getSpawnWidth().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getSpawnWidth().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getSpawnWidth().setLow(((ParticleEmitter)v1).getSpawnWidth().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getSpawnWidth().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getSpawnHeight().setHigh(((ParticleEmitter)v1).getSpawnHeight().getHighMin() * scaleFactor, ((ParticleEmitter)v1).getSpawnHeight().getHighMax() * scaleFactor);
            ((ParticleEmitter)v1).getSpawnHeight().setLow(((ParticleEmitter)v1).getSpawnHeight().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getSpawnHeight().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getXOffsetValue().setLow(((ParticleEmitter)v1).getXOffsetValue().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getXOffsetValue().getLowMax() * scaleFactor);
            ((ParticleEmitter)v1).getYOffsetValue().setLow(((ParticleEmitter)v1).getYOffsetValue().getLowMin() * scaleFactor, ((ParticleEmitter)v1).getYOffsetValue().getLowMax() * scaleFactor);
        }
    }

    public void setDuration(int duration) {
        int v1 = 0;
        int v2 = this.emitters.size;
        while(v1 < v2) {
            Object v0 = this.emitters.get(v1);
            ((ParticleEmitter)v0).setContinuous(false);
            ((ParticleEmitter)v0).duration = ((float)duration);
            ((ParticleEmitter)v0).durationTimer = 0f;
            ++v1;
        }
    }

    public void setEmittersCleanUpBlendFunction(boolean cleanUpBlendFunction) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).setCleansUpBlendFunction(cleanUpBlendFunction);
            ++v0;
        }
    }

    public void setFlip(boolean flipX, boolean flipY) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).setFlip(flipX, flipY);
            ++v0;
        }
    }

    public void setPosition(float x, float y) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).setPosition(x, y);
            ++v0;
        }
    }

    public void start() {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).start();
            ++v0;
        }
    }

    public void update(float delta) {
        int v0 = 0;
        int v1 = this.emitters.size;
        while(v0 < v1) {
            this.emitters.get(v0).update(delta);
            ++v0;
        }
    }
}

