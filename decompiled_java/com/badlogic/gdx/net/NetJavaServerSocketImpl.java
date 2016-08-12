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

import com.badlogic.gdx.Net$Protocol;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NetJavaServerSocketImpl implements ServerSocket {
    private Protocol protocol;
    private java.net.ServerSocket server;

    public NetJavaServerSocketImpl(Protocol protocol, int port, ServerSocketHints hints) {  // has try-catch handlers
        super();
        this.protocol = protocol;
        try {
            this.server = new java.net.ServerSocket();
            if(hints != null) {
                this.server.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency, hints.performancePrefBandwidth);
                this.server.setReuseAddress(hints.reuseAddress);
                this.server.setSoTimeout(hints.acceptTimeout);
                this.server.setReceiveBufferSize(hints.receiveBufferSize);
            }

            InetSocketAddress v0 = new InetSocketAddress(port);
            if(hints != null) {
                this.server.bind(((SocketAddress)v0), hints.backlog);
            }
            else {
                this.server.bind(((SocketAddress)v0));
            }

            return;
        }
        catch(Exception v1) {
            throw new GdxRuntimeException("Cannot create a server socket at port " + port + ".", ((Throwable)v1));
        }
    }

    public Socket accept(SocketHints hints) {  // has try-catch handlers
        try {
            return new NetJavaSocketImpl(this.server.accept(), hints);
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Error accepting socket.", ((Throwable)v0));
        }
    }

    public void dispose() {  // has try-catch handlers
        if(this.server != null) {
            try {
                this.server.close();
                this.server = null;
            }
            catch(Exception v0) {
                throw new GdxRuntimeException("Error closing server.", ((Throwable)v0));
            }
        }
    }

    public Protocol getProtocol() {
        return this.protocol;
    }
}

