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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

public class PolygonRegionLoader extends SynchronousAssetLoader {
    public class PolygonRegionParameters extends AssetLoaderParameters {
        public int readerBuffer;
        public String[] textureExtensions;
        public String texturePrefix;

        public PolygonRegionParameters() {
            super();
            this.texturePrefix = "i ";
            this.readerBuffer = 1024;
            String[] v0 = new String[14];
            v0[0] = "png";
            v0[1] = "PNG";
            v0[2] = "jpeg";
            v0[3] = "JPEG";
            v0[4] = "jpg";
            v0[5] = "JPG";
            v0[6] = "cim";
            v0[7] = "CIM";
            v0[8] = "etc1";
            v0[9] = "ETC1";
            v0[10] = "ktx";
            v0[11] = "KTX";
            v0[12] = "zktx";
            v0[13] = "ZKTX";
            this.textureExtensions = v0;
        }
    }

    private PolygonRegionParameters defaultParameters;
    private EarClippingTriangulator triangulator;

    public PolygonRegionLoader(FileHandleResolver resolver) {
        super(resolver);
        this.defaultParameters = new PolygonRegionParameters();
        this.triangulator = new EarClippingTriangulator();
    }

    public PolygonRegionLoader() {
        this(new InternalFileHandleResolver());
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((PolygonRegionParameters)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, PolygonRegionParameters params) {  // has try-catch handlers
        Array v2;
        if(params == null) {
            params = this.defaultParameters;
        }

        String v6 = null;
        try {
            BufferedReader v9 = file.reader(params.readerBuffer);
            String v8 = v9.readLine();
            while(v8 != null) {
                if(v8.startsWith(params.texturePrefix)) {
                    v6 = v8.substring(params.texturePrefix.length());
                }
                else {
                    v8 = v9.readLine();
                    continue;
                }

                break;
            }

            v9.close();
            if(v6 == null) {
                goto label_20;
            }

            goto label_53;
        }
        catch(IOException v3) {
            goto label_52;
        }

    label_20:
        if(params.textureExtensions != null) {
            String[] v1 = params.textureExtensions;
            int v7 = v1.length;
            int v5;
            for(v5 = 0; v5 < v7; ++v5) {
                FileHandle v10 = file.sibling(file.nameWithoutExtension().concat("." + v1[v5]));
                if(v10.exists()) {
                    v6 = v10.name();
                }
            }
        }

    label_53:
        if(v6 != null) {
            v2 = new Array(1);
            v2.add(new AssetDescriptor(file.sibling(v6), Texture.class));
        }
        else {
            v2 = null;
        }

        return v2;
    label_52:
        throw new GdxRuntimeException("Error reading " + fileName, ((Throwable)v3));
    }

    public PolygonRegion load(AssetManager manager, String fileName, FileHandle file, PolygonRegionParameters parameter) {
        return this.load(new TextureRegion(manager.get(manager.getDependencies(fileName).first())), file);
    }

    public PolygonRegion load(TextureRegion textureRegion, FileHandle file) {  // has try-catch handlers
        PolygonRegion v7_1;
        int v1;
        float[] v6;
        String[] v4;
        String v2;
        BufferedReader v5 = file.reader(256);
        try {
            do {
                v2 = v5.readLine();
                if(v2 != null) {
                    continue;
                }

                goto label_4;
            }
            while(!v2.startsWith("s"));

            v4 = v2.substring(1).trim().split(",");
            v6 = new float[v4.length];
            v1 = 0;
            int v3 = v6.length;
            goto label_24;
        }
        catch(IOException v0) {
            goto label_37;
        }
        catch(Throwable v7) {
            goto label_45;
        }

    label_4:
        StreamUtils.closeQuietly(((Closeable)v5));
        throw new GdxRuntimeException("Polygon shape not found: " + file);
        try {
            while(true) {
            label_24:
                if(v1 >= v3) {
                    break;
                }

                v6[v1] = Float.parseFloat(v4[v1]);
                ++v1;
            }

            v7_1 = new PolygonRegion(textureRegion, v6, this.triangulator.computeTriangles(v6).toArray());
        }
        catch(IOException v0) {
            goto label_37;
        }
        catch(Throwable v7) {
            goto label_45;
        }

        StreamUtils.closeQuietly(((Closeable)v5));
        return v7_1;
        try {
        label_37:
            throw new GdxRuntimeException("Error reading polygon shape file: " + file, ((Throwable)v0));
        }
        catch(Throwable v7) {
        label_45:
            StreamUtils.closeQuietly(((Closeable)v5));
            throw v7;
        }
    }

    public Object load(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.load(x0, x1, x2, ((PolygonRegionParameters)x3));
    }
}

