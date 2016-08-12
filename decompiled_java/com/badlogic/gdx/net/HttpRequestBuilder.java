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

import com.badlogic.gdx.Net$HttpRequest;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Pools;
import java.io.InputStream;
import java.util.Map;

public class HttpRequestBuilder {
    public static String baseUrl;
    public static int defaultTimeout;
    private HttpRequest httpRequest;
    public static Json json;

    static  {
        HttpRequestBuilder.baseUrl = "";
        HttpRequestBuilder.defaultTimeout = 1000;
        HttpRequestBuilder.json = new Json();
    }

    public HttpRequestBuilder() {
        super();
    }

    public HttpRequestBuilder basicAuthentication(String username, String password) {
        this.validate();
        this.httpRequest.setHeader("Authorization", "Basic " + Base64Coder.encodeString(username + ":" + password));
        return this;
    }

    public HttpRequest build() {
        this.validate();
        HttpRequest v0 = this.httpRequest;
        this.httpRequest = null;
        return v0;
    }

    public HttpRequestBuilder content(InputStream contentStream, long contentLength) {
        this.validate();
        this.httpRequest.setContent(contentStream, contentLength);
        return this;
    }

    public HttpRequestBuilder content(String content) {
        this.validate();
        this.httpRequest.setContent(content);
        return this;
    }

    public HttpRequestBuilder followRedirects(boolean followRedirects) {
        this.validate();
        this.httpRequest.setFollowRedirects(followRedirects);
        return this;
    }

    public HttpRequestBuilder formEncodedContent(Map arg5) {
        this.validate();
        this.httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        this.httpRequest.setContent(HttpParametersUtils.convertHttpParameters(arg5));
        return this;
    }

    public HttpRequestBuilder header(String name, String value) {
        this.validate();
        this.httpRequest.setHeader(name, value);
        return this;
    }

    public HttpRequestBuilder jsonContent(Object content) {
        this.validate();
        this.httpRequest.setHeader("Content-Type", "application/json");
        this.httpRequest.setContent(HttpRequestBuilder.json.toJson(content));
        return this;
    }

    public HttpRequestBuilder method(String httpMethod) {
        this.validate();
        this.httpRequest.setMethod(httpMethod);
        return this;
    }

    public HttpRequestBuilder newRequest() {
        if(this.httpRequest != null) {
            throw new IllegalStateException("A new request has already been started. Call HttpRequestBuilder.build() first.");
        }

        this.httpRequest = Pools.obtain(HttpRequest.class);
        this.httpRequest.setTimeOut(HttpRequestBuilder.defaultTimeout);
        return this;
    }

    public HttpRequestBuilder timeout(int timeOut) {
        this.validate();
        this.httpRequest.setTimeOut(timeOut);
        return this;
    }

    public HttpRequestBuilder url(String url) {
        this.validate();
        this.httpRequest.setUrl(HttpRequestBuilder.baseUrl + url);
        return this;
    }

    private void validate() {
        if(this.httpRequest == null) {
            throw new IllegalStateException("A new request has not been started yet. Call HttpRequestBuilder.newRequest() first.");
        }
    }
}

