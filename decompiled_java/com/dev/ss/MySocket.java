// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss;

import com.dev.ss.lib.Crypt;
import com.dev.ss.lib.Util;
import com.dev.ss.screen.GameScreen;
import com.dev.ss.screen.LoginScreen;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MySocket {
    public static String host;
    public static List lsRead;
    public static int port;
    public static Socket socket;
    public static Thread thdRead;

    static  {
        MySocket.lsRead = new ArrayList();
        MySocket.host = "";
        MySocket.port = 0;
    }

    public MySocket() {
        super();
    }

    public static void clear() {  // has try-catch handlers
        try {
            if(MySocket.thdRead == null) {
                goto label_6;
            }

            MySocket.thdRead.interrupt();
            MySocket.thdRead = null;
            goto label_6;
        }
        catch(Exception v0) {
            Util.sysout("INFO", "MySocket->clear(1)", Util.getExceptionMessage(v0));
            goto label_6;
        }

        try {
        label_6:
            if(MySocket.socket == null) {
                goto label_10;
            }

            MySocket.socket.close();
            goto label_10;
        }
        catch(Exception v0) {
            Util.sysout("INFO", "MySocket->clear(2)", Util.getExceptionMessage(v0));
            goto label_10;
        }

        try {
        label_10:
            MySocket.lsRead.clear();
        }
        catch(Exception v0) {
            Util.sysout("INFO", "MySocket->clear(3)", Util.getExceptionMessage(v0));
        }
    }

    public static void close() {  // has try-catch handlers
        try {
            if(MySocket.socket == null) {
                goto label_4;
            }

            MySocket.socket.close();
            goto label_4;
        }
        catch(Exception v0) {
            goto label_4;
        }

        try {
        label_4:
            if(MySocket.socket == null) {
                goto label_8;
            }

            MySocket.socket = null;
            goto label_8;
        }
        catch(Exception v0) {
            goto label_8;
        }

        try {
        label_8:
            if(MySocket.thdRead == null) {
                goto label_12;
            }

            MySocket.thdRead.interrupt();
            goto label_12;
        }
        catch(Exception v0) {
            goto label_12;
        }

        try {
        label_12:
            if(MySocket.thdRead == null) {
                return;
            }

            MySocket.thdRead = null;
        }
        catch(Exception v0) {
        }
    }

    public static boolean connect() throws Exception {  // has try-catch handlers
        boolean v2 = false;
        try {
            InetSocketAddress v3 = new InetSocketAddress(InetAddress.getByName(MySocket.host), MySocket.port);
            if(!MySocket.isConnected()) {
                if(MySocket.socket == null) {
                    MySocket.socket = new Socket();
                }
                else {
                    MySocket.socket.close();
                    MySocket.socket = new Socket();
                }

                MySocket.socket.connect(((SocketAddress)v3), 10000);
                if(MySocket.thdRead != null) {
                    goto label_34;
                }

                MySocket.thdRead = new Thread(new ThdReader());
                MySocket.thdRead.start();
                goto label_21;
            label_34:
                MySocket.thdRead.interrupt();
                MySocket.thdRead = null;
                MySocket.thdRead = new Thread(new ThdReader());
                MySocket.thdRead.start();
            }

        label_21:
            v2 = true;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "MySocket->connect", Util.getExceptionMessage(v0));
        }

        return v2;
    }

    public static boolean isConnected() {  // has try-catch handlers
        BufferedWriter v1;
        OutputStreamWriter v5;
        boolean v6 = false;
        String v3 = "G000";
        try {
            if(MySocket.socket == null) {
                goto label_25;
            }

            if(MySocket.socket.isClosed()) {
                goto label_25;
            }

            v3 = Crypt.encrypt(v3);
            v5 = new OutputStreamWriter(MySocket.socket.getOutputStream(), "UTF-8");
            goto label_14;
        }
        catch(Exception v2) {
            goto label_27;
        }

        try {
        label_14:
            v1 = new BufferedWriter(((Writer)v5));
            goto label_15;
        }
        catch(Exception v2) {
            goto label_27;
        }

        try {
        label_15:
            v1.write(String.valueOf(v3) + "\n");
            v1.flush();
            v6 = true;
            goto label_25;
        }
        catch(Exception v2) {
        }

    label_27:
        Util.sysout("INFO", "MySocket->isConnected", Util.getExceptionMessage(v2));
    label_25:
        return v6;
    }

    public static void processMsg() {  // has try-catch handlers
        String[] v4;
        String v2_1;
        while(true) {
            if(MySocket.lsRead.size() > 0) {
                goto label_6;
            }

            return;
            try {
            label_6:
                Object v2 = MySocket.lsRead.get(0);
                MySocket.lsRead.remove(0);
                v2_1 = Crypt.decrypt(((String)v2));
                v4 = v2_1.split("\t");
                goto label_18;
            }
            catch(Exception v3) {
                goto label_50;
            }

            try {
            label_18:
                String v1 = v4[0];
                if(!"L".equals(v1.substring(0, 1))) {
                    goto label_52;
                }

                LoginScreen.lsLoginRead.add(v2_1);
                continue;
            label_52:
                if(!"G".equals(v1.substring(0, 1))) {
                    continue;
                }

                GameScreen.lsGameRead.add(v2_1);
                continue;
            }
            catch(Exception v3) {
                try {
                    Util.sysout("ERROR", "MySocket->processMsg(1)", Util.getExceptionMessage(v3), "[" + v2_1 + "]");
                    continue;
                }
                catch(Exception v3) {
                label_50:
                    Util.sysout("ERROR", "MySocket->processMsg(2)", Util.getExceptionMessage(v3), "[" + v2_1 + "]");
                    continue;
                }
            }
        }
    }

    public static boolean write(String msg) {  // has try-catch handlers
        BufferedWriter v1;
        OutputStreamWriter v4;
        boolean v5 = false;
        try {
            if(MySocket.socket == null) {
                goto label_21;
            }

            msg = Crypt.encrypt(msg);
            v4 = new OutputStreamWriter(MySocket.socket.getOutputStream(), "UTF-8");
            goto label_10;
        }
        catch(Exception v2) {
            goto label_23;
        }

        try {
        label_10:
            v1 = new BufferedWriter(((Writer)v4));
            goto label_11;
        }
        catch(Exception v2) {
            goto label_23;
        }

        try {
        label_11:
            v1.write(String.valueOf(msg) + "\n");
            v1.flush();
            v5 = true;
            goto label_21;
        }
        catch(Exception v2) {
        }

    label_23:
        Util.sysout("ERROR", "MySocket->write", Util.getExceptionMessage(v2));
    label_21:
        return v5;
    }
}

