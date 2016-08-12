// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression;

import com.badlogic.gdx.utils.compression.lzma.Decoder;
import com.badlogic.gdx.utils.compression.lzma.Encoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Lzma {
    class CommandLine {
        public int Algorithm;
        public int Command;
        public int DictionarySize;
        public boolean DictionarySizeIsDefined;
        public boolean Eos;
        public int Fb;
        public boolean FbIsDefined;
        public String InFile;
        public int Lc;
        public int Lp;
        public int MatchFinder;
        public int NumBenchmarkPasses;
        public String OutFile;
        public int Pb;
        public static final int kBenchmak = 2;
        public static final int kDecode = 1;
        public static final int kEncode;

        CommandLine() {
            super();
            this.Command = -1;
            this.NumBenchmarkPasses = 10;
            this.DictionarySize = 8388608;
            this.DictionarySizeIsDefined = false;
            this.Lc = 3;
            this.Lp = 0;
            this.Pb = 2;
            this.Fb = 128;
            this.FbIsDefined = false;
            this.Eos = false;
            this.Algorithm = 2;
            this.MatchFinder = 1;
        }
    }

    public Lzma() {
        super();
    }

    public static void compress(InputStream in, OutputStream out) throws IOException {
        long v10;
        long v4 = -1;
        CommandLine v12 = new CommandLine();
        boolean v0 = false;
        if(v12.Eos) {
            v0 = true;
        }

        Encoder v1 = new Encoder();
        if(!v1.SetAlgorithm(v12.Algorithm)) {
            throw new RuntimeException("Incorrect compression mode");
        }

        if(!v1.SetDictionarySize(v12.DictionarySize)) {
            throw new RuntimeException("Incorrect dictionary size");
        }

        if(!v1.SetNumFastBytes(v12.Fb)) {
            throw new RuntimeException("Incorrect -fb value");
        }

        if(!v1.SetMatchFinder(v12.MatchFinder)) {
            throw new RuntimeException("Incorrect -mf value");
        }

        if(!v1.SetLcLpPb(v12.Lc, v12.Lp, v12.Pb)) {
            throw new RuntimeException("Incorrect -lc or -lp or -pb value");
        }

        v1.SetEndMarkerMode(v0);
        v1.WriteCoderProperties(out);
        if(v0) {
            v10 = -1;
        }
        else {
            v10 = ((long)in.available());
            if(v10 == 0) {
                v10 = -1;
            }
        }

        int v9;
        for(v9 = 0; v9 < 8; ++v9) {
            out.write((((int)(v10 >>> v9 * 8))) & 255);
        }

        v1.Code(in, out, v4, v4, null);
    }

    public static void decompress(InputStream in, OutputStream out) throws IOException {
        byte[] v4 = new byte[5];
        if(in.read(v4, 0, 5) != 5) {
            throw new RuntimeException("input .lzma file is too short");
        }

        Decoder v0 = new Decoder();
        if(!v0.SetDecoderProperties(v4)) {
            throw new RuntimeException("Incorrect stream properties");
        }

        long v2 = 0;
        int v1;
        for(v1 = 0; v1 < 8; ++v1) {
            int v6 = in.read();
            if(v6 < 0) {
                throw new RuntimeException("Can\'t read stream size");
            }

            v2 |= (((long)v6)) << v1 * 8;
        }

        if(!v0.Code(in, out, v2)) {
            throw new RuntimeException("Error in data stream");
        }
    }
}

