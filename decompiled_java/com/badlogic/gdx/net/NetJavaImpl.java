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
import com.badlogic.gdx.Net$HttpResponse;
import com.badlogic.gdx.Net$HttpResponseListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map$Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetJavaImpl {
    class HttpClientResponse implements HttpResponse {
        private final HttpURLConnection connection;
        private HttpStatus status;

        public HttpClientResponse(HttpURLConnection connection) throws IOException {  // has try-catch handlers
            super();
            this.connection = connection;
            try {
                this.status = new HttpStatus(connection.getResponseCode());
            }
            catch(IOException v0) {
                this.status = new HttpStatus(-1);
            }
        }

        public String getHeader(String name) {
            return this.connection.getHeaderField(name);
        }

        public Map getHeaders() {
            return this.connection.getHeaderFields();
        }

        private InputStream getInputStream() {  // has try-catch handlers
            InputStream v1;
            try {
                v1 = this.connection.getInputStream();
            }
            catch(IOException v0) {
                v1 = this.connection.getErrorStream();
            }

            return v1;
        }

        public byte[] getResult() {  // has try-catch handlers
            byte[] v2_1;
            InputStream v1 = this.getInputStream();
            try {
                v2_1 = StreamUtils.copyStreamToByteArray(v1, this.connection.getContentLength());
            }
            catch(Throwable v2) {
            label_11:
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
            catch(IOException v0) {
                try {
                    v2_1 = StreamUtils.EMPTY_BYTES;
                }
                catch(Throwable v2) {
                    goto label_11;
                }

                StreamUtils.closeQuietly(((Closeable)v1));
                goto label_5;
            }

            StreamUtils.closeQuietly(((Closeable)v1));
        label_5:
            return v2_1;
        }

        public InputStream getResultAsStream() {
            return this.getInputStream();
        }

        public String getResultAsString() {  // has try-catch handlers
            InputStream v1 = this.getInputStream();
            if(v1 != null) {
                goto label_4;
            }

            String v2 = "";
            goto label_3;
            try {
            label_4:
                v2 = StreamUtils.copyStreamToString(v1, this.connection.getContentLength());
            }
            catch(Throwable v2_1) {
            label_14:
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2_1;
            }
            catch(IOException v0) {
                try {
                    v2 = "";
                }
                catch(Throwable v2_1) {
                    goto label_14;
                }

                StreamUtils.closeQuietly(((Closeable)v1));
                goto label_3;
            }

            StreamUtils.closeQuietly(((Closeable)v1));
        label_3:
            return v2;
        }

        public HttpStatus getStatus() {
            return this.status;
        }
    }

    final ObjectMap connections;
    private final ExecutorService executorService;
    final ObjectMap listeners;

    public NetJavaImpl() {
        super();
        this.executorService = Executors.newCachedThreadPool();
        this.connections = new ObjectMap();
        this.listeners = new ObjectMap();
    }

    public void cancelHttpRequest(HttpRequest httpRequest) {
        HttpResponseListener v0 = this.getFromListeners(httpRequest);
        if(v0 != null) {
            v0.cancelled();
            this.removeFromConnectionsAndListeners(httpRequest);
        }
    }

    HttpResponseListener getFromListeners(HttpRequest httpRequest) {  // has try-catch handlers
        try {
            return this.listeners.get(httpRequest);
        }
        catch(Throwable v1) {
            throw v1;
        }
    }

    void putIntoConnectionsAndListeners(HttpRequest httpRequest, HttpResponseListener httpResponseListener, HttpURLConnection connection) {  // has try-catch handlers
        try {
            this.connections.put(httpRequest, connection);
            this.listeners.put(httpRequest, httpResponseListener);
            return;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    void removeFromConnectionsAndListeners(HttpRequest httpRequest) {  // has try-catch handlers
        try {
            this.connections.remove(httpRequest);
            this.listeners.remove(httpRequest);
            return;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {  // has try-catch handlers
        boolean v5;
        URL v14;
        if(httpRequest.getUrl() != null) {
            goto label_7;
        }

        httpResponseListener.failed(new GdxRuntimeException("can\'t process a HTTP request without URL set"));
        return;
        try {
        label_7:
            String v12 = httpRequest.getMethod();
            if(v12.equalsIgnoreCase("GET")) {
                String v13 = "";
                String v15 = httpRequest.getContent();
                if(v15 != null && !"".equals(v15)) {
                    v13 = "?" + v15;
                }

                v14 = new URL(httpRequest.getUrl() + v13);
            }
            else {
                v14 = new URL(httpRequest.getUrl());
            }

            URLConnection v7 = v14.openConnection();
            if((v12.equalsIgnoreCase("POST")) || (v12.equalsIgnoreCase("PUT"))) {
                v5 = true;
            }
            else {
                v5 = false;
            }

            ((HttpURLConnection)v7).setDoOutput(v5);
            ((HttpURLConnection)v7).setDoInput(true);
            ((HttpURLConnection)v7).setRequestMethod(v12);
            HttpURLConnection.setFollowRedirects(httpRequest.getFollowRedirects());
            this.putIntoConnectionsAndListeners(httpRequest, httpResponseListener, ((HttpURLConnection)v7));
            Iterator v11 = httpRequest.getHeaders().entrySet().iterator();
            while(v11.hasNext()) {
                Object v10 = v11.next();
                ((HttpURLConnection)v7).addRequestProperty(((Map$Entry)v10).getKey(), ((Map$Entry)v10).getValue());
            }

            ((HttpURLConnection)v7).setConnectTimeout(httpRequest.getTimeOut());
            ((HttpURLConnection)v7).setReadTimeout(httpRequest.getTimeOut());
            this.executorService.submit(new Runnable() {
                public void run() {  // has try-catch handlers
                    HttpClientResponse v0;
                    OutputStream v5;
                    InputStream v1;
                    OutputStreamWriter v6;
                    String v2;
                    try {
                        if(this.val$doingOutPut) {
                            v2 = this.val$httpRequest.getContent();
                            if(v2 != null) {
                                v6 = new OutputStreamWriter(this.val$connection.getOutputStream());
                                goto label_8;
                            }
                            else {
                                goto label_37;
                            }
                        }

                        goto label_10;
                    }
                    catch(Exception v3) {
                        goto label_30;
                    }

                    try {
                    label_8:
                        v6.write(v2);
                        goto label_9;
                    }
                    catch(Throwable v7) {
                        goto label_26;
                    }

                    try {
                    label_9:
                        StreamUtils.closeQuietly(((Closeable)v6));
                        goto label_10;
                    label_26:
                        StreamUtils.closeQuietly(((Closeable)v6));
                        throw v7;
                    label_37:
                        v1 = this.val$httpRequest.getContentStream();
                        if(v1 != null) {
                            v5 = this.val$connection.getOutputStream();
                            goto label_42;
                        }

                        goto label_10;
                    }
                    catch(Exception v3) {
                        goto label_30;
                    }

                    try {
                    label_42:
                        StreamUtils.copyStream(v1, v5);
                        goto label_43;
                    }
                    catch(Throwable v7) {
                        goto label_46;
                    }

                    try {
                    label_43:
                        StreamUtils.closeQuietly(((Closeable)v5));
                        goto label_10;
                    label_46:
                        StreamUtils.closeQuietly(((Closeable)v5));
                        throw v7;
                    label_10:
                        this.val$connection.connect();
                        v0 = new HttpClientResponse(this.val$connection);
                        goto label_14;
                    }
                    catch(Exception v3) {
                        goto label_30;
                    }

                    try {
                    label_14:
                        HttpResponseListener v4 = NetJavaImpl.this.getFromListeners(this.val$httpRequest);
                        if(v4 != null) {
                            v4.handleHttpResponse(((HttpResponse)v0));
                        }

                        NetJavaImpl.this.removeFromConnectionsAndListeners(this.val$httpRequest);
                        goto label_22;
                    }
                    catch(Throwable v7) {
                        goto label_50;
                    }

                    try {
                    label_22:
                        this.val$connection.disconnect();
                        return;
                    label_50:
                        this.val$connection.disconnect();
                        throw v7;
                    }
                    catch(Exception v3) {
                    label_30:
                        this.val$connection.disconnect();
                        try {
                            this.val$httpResponseListener.failed(((Throwable)v3));
                        }
                        catch(Throwable v7) {
                            goto label_55;
                        }

                        NetJavaImpl.this.removeFromConnectionsAndListeners(this.val$httpRequest);
                        return;
                    label_55:
                        NetJavaImpl.this.removeFromConnectionsAndListeners(this.val$httpRequest);
                        throw v7;
                    }
                }
            });
        }
        catch(Exception v9) {
            try {
                httpResponseListener.failed(((Throwable)v9));
            }
            catch(Throwable v3) {
                this.removeFromConnectionsAndListeners(httpRequest);
                throw v3;
            }

            this.removeFromConnectionsAndListeners(httpRequest);
        }
    }
}

