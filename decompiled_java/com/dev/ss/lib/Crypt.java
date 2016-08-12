// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;

public class Crypt {
    public Crypt() {
        super();
    }

    public static String decrypt(String str) throws IOException {
        ByteArrayInputStream v5 = new ByteArrayInputStream(Base64.decodeBase64(str.getBytes()));
        GZIPInputStream v4 = new GZIPInputStream(((InputStream)v5), 1024);
        StringBuilder v6 = new StringBuilder();
        byte[] v3 = new byte[1024];
        while(true) {
            int v1 = v4.read(v3);
            if(v1 != -1) {
                goto label_15;
            }

            break;
        label_15:
            v6.append(new String(v3, 0, v1, "UTF-8"));
        }

        v4.close();
        v5.close();
        return v6.toString();
    }

    public static String encrypt(String str) throws IOException {
        ByteArrayOutputStream v2 = new ByteArrayOutputStream(str.length());
        GZIPOutputStream v1 = new GZIPOutputStream(((OutputStream)v2));
        v1.write(str.getBytes("UTF-8"));
        v1.close();
        byte[] v0 = Base64.encodeBase64(v2.toByteArray());
        v2.close();
        return new String(v0);
    }
}

