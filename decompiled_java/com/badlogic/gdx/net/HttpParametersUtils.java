// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpParametersUtils {
    public static String defaultEncoding;
    public static String nameValueSeparator;
    public static String parameterSeparator;

    static  {
        HttpParametersUtils.defaultEncoding = "UTF-8";
        HttpParametersUtils.nameValueSeparator = "=";
        HttpParametersUtils.parameterSeparator = "&";
    }

    public HttpParametersUtils() {
        super();
    }

    public static String convertHttpParameters(Map arg6) {
        Set v2 = arg6.keySet();
        StringBuffer v0 = new StringBuffer();
        Iterator v1 = v2.iterator();
        while(v1.hasNext()) {
            Object v3 = v1.next();
            v0.append(HttpParametersUtils.encode(((String)v3), HttpParametersUtils.defaultEncoding));
            v0.append(HttpParametersUtils.nameValueSeparator);
            v0.append(HttpParametersUtils.encode(arg6.get(v3), HttpParametersUtils.defaultEncoding));
            v0.append(HttpParametersUtils.parameterSeparator);
        }

        if(v0.length() > 0) {
            v0.deleteCharAt(v0.length() - 1);
        }

        return v0.toString();
    }

    private static String encode(String content, String encoding) {  // has try-catch handlers
        try {
            return URLEncoder.encode(content, encoding);
        }
        catch(UnsupportedEncodingException v0) {
            throw new IllegalArgumentException(((Throwable)v0));
        }
    }
}

