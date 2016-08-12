// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net$HttpRequest;
import com.badlogic.gdx.Net$HttpResponseListener;
import com.badlogic.gdx.Net$Protocol;
import com.badlogic.gdx.net.NetJavaImpl;
import com.badlogic.gdx.net.NetJavaServerSocketImpl;
import com.badlogic.gdx.net.NetJavaSocketImpl;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class AndroidNet implements Net {
    final AndroidApplicationBase app;
    NetJavaImpl netJavaImpl;

    public AndroidNet(AndroidApplicationBase app) {
        super();
        this.app = app;
        this.netJavaImpl = new NetJavaImpl();
    }

    public void cancelHttpRequest(HttpRequest httpRequest) {
        this.netJavaImpl.cancelHttpRequest(httpRequest);
    }

    public Socket newClientSocket(Protocol protocol, String host, int port, SocketHints hints) {
        return new NetJavaSocketImpl(protocol, host, port, hints);
    }

    public ServerSocket newServerSocket(Protocol protocol, int port, ServerSocketHints hints) {
        return new NetJavaServerSocketImpl(protocol, port, hints);
    }

    public boolean openURI(String URI) {
        boolean v2 = false;
        Uri v3 = Uri.parse(URI);
        if(this.app.getContext().getPackageManager().resolveActivity(new Intent("android.intent.action.VIEW", v3), 65536) != null) {
            this.app.runOnUiThread(new Runnable() {
                public void run() {
                    Intent v0 = new Intent("android.intent.action.VIEW", this.val$uri);
                    if(!(AndroidNet.this.app.getContext() instanceof Activity)) {
                        v0.addFlags(268435456);
                    }

                    AndroidNet.this.app.startActivity(v0);
                }
            });
            v2 = true;
        }

        return v2;
    }

    public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {
        this.netJavaImpl.sendHttpRequest(httpRequest, httpResponseListener);
    }
}

