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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NetJavaSocketImpl implements Socket {
    private java.net.Socket socket;

    public NetJavaSocketImpl(Protocol protocol, String host, int port, SocketHints hints) {  // has try-catch handlers
        super();
        try {
            this.socket = new java.net.Socket();
            this.applyHints(hints);
            InetSocketAddress v0 = new InetSocketAddress(host, port);
            if(hints != null) {
                this.socket.connect(((SocketAddress)v0), hints.connectTimeout);
            }
            else {
                this.socket.connect(((SocketAddress)v0));
            }

            return;
        }
        catch(Exception v1) {
            throw new GdxRuntimeException("Error making a socket connection to " + host + ":" + port, ((Throwable)v1));
        }
    }

    public NetJavaSocketImpl(java.net.Socket socket, SocketHints hints) {
        super();
        this.socket = socket;
        this.applyHints(hints);
    }

    private void applyHints(SocketHints hints) {  // has try-catch handlers
        if(hints != null) {
            try {
                this.socket.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency, hints.performancePrefBandwidth);
                this.socket.setTrafficClass(hints.trafficClass);
                this.socket.setTcpNoDelay(hints.tcpNoDelay);
                this.socket.setKeepAlive(hints.keepAlive);
                this.socket.setSendBufferSize(hints.sendBufferSize);
                this.socket.setReceiveBufferSize(hints.receiveBufferSize);
                this.socket.setSoLinger(hints.linger, hints.lingerDuration);
                this.socket.setSoTimeout(hints.socketTimeout);
            }
            catch(Exception v0) {
                throw new GdxRuntimeException("Error setting socket hints.", ((Throwable)v0));
            }
        }
    }

    public void dispose() {  // has try-catch handlers
        if(this.socket != null) {
            try {
                this.socket.close();
                this.socket = null;
            }
            catch(Exception v0) {
                throw new GdxRuntimeException("Error closing socket.", ((Throwable)v0));
            }
        }
    }

    public InputStream getInputStream() {  // has try-catch handlers
        try {
            return this.socket.getInputStream();
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Error getting input stream from socket.", ((Throwable)v0));
        }
    }

    public OutputStream getOutputStream() {  // has try-catch handlers
        try {
            return this.socket.getOutputStream();
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Error getting output stream from socket.", ((Throwable)v0));
        }
    }

    public String getRemoteAddress() {
        return this.socket.getRemoteSocketAddress().toString();
    }

    public boolean isConnected() {
        boolean v0;
        if(this.socket != null) {
            v0 = this.socket.isConnected();
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

