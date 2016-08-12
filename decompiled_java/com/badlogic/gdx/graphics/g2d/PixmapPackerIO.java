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
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.math.Rectangle;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class PixmapPackerIO {
    public enum ImageFormat {
        public static final enum ImageFormat CIM;
        public static final enum ImageFormat PNG;
        private final String extension;

        static  {
            ImageFormat.CIM = new ImageFormat("CIM", 0, ".cim");
            ImageFormat.PNG = new ImageFormat("PNG", 1, ".png");
            ImageFormat[] v0 = new ImageFormat[2];
            v0[0] = ImageFormat.CIM;
            v0[1] = ImageFormat.PNG;
            ImageFormat.$VALUES = v0;
        }

        private ImageFormat(String arg1, int arg2, String extension) {
            super(arg1, arg2);
            this.extension = extension;
        }

        public String getExtension() {
            return this.extension;
        }

        public static ImageFormat valueOf(String name) {
            return Enum.valueOf(ImageFormat.class, name);
        }

        public static ImageFormat[] values() {
            return ImageFormat.$VALUES.clone();
        }
    }

    public class SaveParameters {
        public ImageFormat format;
        public TextureFilter magFilter;
        public TextureFilter minFilter;

        public SaveParameters() {
            super();
            this.format = ImageFormat.PNG;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
        }
    }

    public PixmapPackerIO() {
        super();
    }

    public void save(FileHandle file, PixmapPacker packer) throws IOException {
        this.save(file, packer, new SaveParameters());
    }

    public void save(FileHandle file, PixmapPacker packer, SaveParameters parameters) throws IOException {
        Object v4;
        Writer v7 = file.writer(false);
        int v2 = 0;
        Iterator v0 = packer.pages.iterator();
        do {
        label_5:
            if(!v0.hasNext()) {
                goto label_151;
            }

            v4 = v0.next();
        }
        while(((Page)v4).rects.size <= 0);

        ++v2;
        FileHandle v5 = file.sibling(file.nameWithoutExtension() + "_" + v2 + parameters.format.getExtension());
        switch(com.badlogic.gdx.graphics.g2d.PixmapPackerIO$1.$SwitchMap$com$badlogic$gdx$graphics$g2d$PixmapPackerIO$ImageFormat[parameters.format.ordinal()]) {
            case 1: {
                PixmapIO.writeCIM(v5, ((Page)v4).image);
                break;
            }
            case 2: {
                PixmapIO.writePNG(v5, ((Page)v4).image);
                break;
            }
        }

        v7.write("\n");
        v7.write(v5.name() + "\n");
        v7.write("size: " + ((Page)v4).image.getWidth() + "," + ((Page)v4).image.getHeight() + "\n");
        v7.write("format: " + packer.pageFormat.name() + "\n");
        v7.write("filter: " + parameters.minFilter.name() + "," + parameters.magFilter.name() + "\n");
        v7.write("repeat: none\n");
        Iterator v1 = ((Page)v4).rects.keys().iterator();
        goto label_82;
    label_151:
        v7.close();
        return;
        while(true) {
        label_82:
            if(!v1.hasNext()) {
                goto label_5;
            }

            Object v3 = v1.next();
            v7.write((((String)v3)) + "\n");
            Object v6 = ((Page)v4).rects.get(v3);
            v7.write("rotate: false\n");
            v7.write("xy: " + (((int)((Rectangle)v6).x)) + "," + (((int)((Rectangle)v6).y)) + "\n");
            v7.write("size: " + (((int)((Rectangle)v6).width)) + "," + (((int)((Rectangle)v6).height)) + "\n");
            v7.write("orig: " + (((int)((Rectangle)v6).width)) + "," + (((int)((Rectangle)v6).height)) + "\n");
            v7.write("offset: 0, 0\n");
            v7.write("index: -1\n");
        }
    }
}

