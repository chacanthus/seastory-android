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

import com.dev.ss.lib.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

class ThdReader implements Runnable {
    ThdReader() {
        super();
    }

    public void run() {  // has try-catch handlers
        String v5;
        BufferedReader v1;
        InputStreamReader v4;
        BufferedReader v0 = null;
        InputStreamReader v3 = null;
        try {
            if(MySocket.socket != null) {
                v4 = new InputStreamReader(MySocket.socket.getInputStream(), "UTF-8");
                goto label_8;
            }

            goto label_13;
        }
        catch(Throwable v6) {
        }
        catch(Exception v2) {
            goto label_25;
            try {
            label_8:
                v1 = new BufferedReader(((Reader)v4));
                goto label_9;
            }
            catch(Throwable v6) {
                v3 = v4;
                goto label_47;
            }
            catch(Exception v2) {
                v3 = v4;
                goto label_25;
            }

            try {
                while(true) {
                label_9:
                    v5 = v1.readLine();
                    if(v5 == null) {
                        break;
                    }
                    else {
                        goto label_18;
                    }
                }
            }
            catch(Exception v2) {
                goto label_23;
            }
            catch(Throwable v6) {
                goto label_80;
            }

            v3 = v4;
            v0 = v1;
            goto label_13;
            try {
            label_18:
                MySocket.lsRead.add(v5);
                MySocket.processMsg();
                goto label_9;
            }
            catch(Throwable v6) {
            label_80:
                v3 = v4;
                v0 = v1;
                goto label_47;
            }
            catch(Exception v2) {
            label_23:
                v3 = v4;
                v0 = v1;
                goto label_25;
            }

            try {
            label_25:
                Util.sysout("ERROR", "MySocket->ThdReader->run(1)", Util.getExceptionMessage(v2));
                if(v3 == null) {
                    goto label_31;
                }

                goto label_30;
            }
            catch(Throwable v6) {
            }
        }

    label_47:
        if(v3 != null) {
            try {
                v3.close();
                goto label_49;
            }
            catch(Exception v2) {
                Util.sysout("ERROR", "MySocket->ThdReader->run(2)", Util.getExceptionMessage(v2));
                goto label_49;
            }
        }
        else {
        label_49:
            if(v0 != null) {
                try {
                    v0.close();
                    goto label_51;
                }
                catch(Exception v2) {
                    Util.sysout("ERROR", "MySocket->ThdReader->run(3)", Util.getExceptionMessage(v2));
                    goto label_51;
                }
            }
            else {
            label_51:
                throw v6;
                try {
                label_30:
                    v3.close();
                }
                catch(Exception v2) {
                    Util.sysout("ERROR", "MySocket->ThdReader->run(2)", Util.getExceptionMessage(v2));
                }

            label_31:
                if(v0 != null) {
                    try {
                        v0.close();
                        return;
                    }
                    catch(Exception v2) {
                        Util.sysout("ERROR", "MySocket->ThdReader->run(3)", Util.getExceptionMessage(v2));
                        return;
                    }
                }
                else {
                    return;
                }
            }
        }

    label_13:
        if(v3 == null) {
            goto label_15;
        }

        try {
            v3.close();
        }
        catch(Exception v2) {
            Util.sysout("ERROR", "MySocket->ThdReader->run(2)", Util.getExceptionMessage(v2));
        }

    label_15:
        if(v0 == null) {
            return;
        }

        try {
            v0.close();
        }
        catch(Exception v2) {
            Util.sysout("ERROR", "MySocket->ThdReader->run(3)", Util.getExceptionMessage(v2));
        }
    }
}

