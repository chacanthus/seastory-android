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

import android.content.Context;
import android.os.Build$VERSION;
import android.view.MotionEvent;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class AndroidMultiTouchHandler implements AndroidTouchHandler {
    public AndroidMultiTouchHandler() {
        super();
    }

    private void logAction(int action, int pointer) {
        String v0;
        if(action == 0) {
            v0 = "DOWN";
        }
        else if(action == 5) {
            v0 = "POINTER DOWN";
        }
        else if(action == 1) {
            v0 = "UP";
        }
        else if(action == 6) {
            v0 = "POINTER UP";
        }
        else if(action == 4) {
            v0 = "OUTSIDE";
        }
        else if(action == 3) {
            v0 = "CANCEL";
        }
        else if(action == 2) {
            v0 = "MOVE";
        }
        else {
            v0 = "UNKNOWN (" + action + ")";
        }

        Gdx.app.log("AndroidMultiTouchHandler", "action " + v0 + ", Android pointer id: " + pointer);
    }

    public void onTouch(MotionEvent event, AndroidInput input) {  // has try-catch handlers
        Application v3_2;
        boolean v3_1;
        boolean[] v4;
        int v7;
        int v6;
        int v8;
        int v2 = event.getAction() & 255;
        int v24 = (event.getAction() & 65280) >> 8;
        int v23 = event.getPointerId(v24);
        int v9 = 0;
        long v10 = System.nanoTime();
        switch(v2) {
            case 2: {
                goto label_124;
            }
            case 0: 
            case 5: {
                try {
                    v8 = input.getFreePointerIndex();
                    if(v8 >= 20) {
                        goto label_15;
                    }

                    input.realId[v8] = v23;
                    v6 = ((int)event.getX(v24));
                    v7 = ((int)event.getY(v24));
                    if(Build$VERSION.SDK_INT >= 14) {
                        v9 = this.toGdxButton(event.getButtonState());
                    }

                    if(v9 != -1) {
                        this.postTouchEvent(input, 0, v6, v7, v8, v9, v10);
                    }

                    input.touchX[v8] = v6;
                    input.touchY[v8] = v7;
                    input.deltaX[v8] = 0;
                    input.deltaY[v8] = 0;
                    v4 = input.touched;
                    if(v9 != -1) {
                        v3_1 = true;
                    }
                    else {
                        goto label_71;
                    }

                    goto label_64;
                }
                catch(Throwable v3) {
                    goto label_70;
                }

            label_71:
                v3_1 = false;
                try {
                label_64:
                    v4[v8] = v3_1;
                    input.button[v8] = v9;
                    goto label_15;
                label_73:
                    v8 = input.lookUpPointerIndex(v23);
                    if(v8 == -1) {
                        goto label_15;
                    }

                    if(v8 >= 20) {
                        goto label_15;
                    }

                    input.realId[v8] = -1;
                    v6 = ((int)event.getX(v24));
                    v7 = ((int)event.getY(v24));
                    v9 = input.button[v8];
                    if(v9 != -1) {
                        this.postTouchEvent(input, 1, v6, v7, v8, v9, v10);
                    }

                    input.touchX[v8] = v6;
                    input.touchY[v8] = v7;
                    input.deltaX[v8] = 0;
                    input.deltaY[v8] = 0;
                    input.touched[v8] = false;
                    input.button[v8] = 0;
                    goto label_15;
                label_124:
                    int v22 = event.getPointerCount();
                    int v12;
                    for(v12 = 0; v12 < v22; ++v12) {
                        v23 = event.getPointerId(v12);
                        v6 = ((int)event.getX(v12));
                        v7 = ((int)event.getY(v12));
                        v8 = input.lookUpPointerIndex(v23);
                        if(v8 != -1) {
                            if(v8 < 20) {
                                v9 = input.button[v8];
                                if(v9 != -1) {
                                    this.postTouchEvent(input, 2, v6, v7, v8, v9, v10);
                                }
                                else {
                                    this.postTouchEvent(input, 4, v6, v7, v8, 0, v10);
                                }

                                input.deltaX[v8] = v6 - input.touchX[v8];
                                input.deltaY[v8] = v7 - input.touchY[v8];
                                input.touchX[v8] = v6;
                                input.touchY[v8] = v7;
                            }
                            else {
                                break;
                            }
                        }
                    }

                label_15:
                    v3_2 = Gdx.app;
                    goto label_16;
                label_70:
                    throw v3;
                }
                catch(Throwable v3) {
                    goto label_70;
                }
            }
            case 1: 
            case 3: 
            case 4: 
            case 6: {
                goto label_73;
            }
        }

    label_16:
        v3_2.getGraphics().requestRendering();
    }

    private void postTouchEvent(AndroidInput input, int type, int x, int y, int pointer, int button, long timeStamp) {
        Object v0 = input.usedTouchEvents.obtain();
        ((TouchEvent)v0).timeStamp = timeStamp;
        ((TouchEvent)v0).pointer = pointer;
        ((TouchEvent)v0).x = x;
        ((TouchEvent)v0).y = y;
        ((TouchEvent)v0).type = type;
        ((TouchEvent)v0).button = button;
        input.touchEvents.add(v0);
    }

    public boolean supportsMultitouch(Context activity) {
        return activity.getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch");
    }

    private int toGdxButton(int button) {
        int v2 = 4;
        int v1 = 2;
        int v0 = 1;
        if(button == 0 || button == 1) {
            v0 = 0;
        }
        else if(button != v1) {
            if(button == v2) {
                v0 = v1;
            }
            else if(button == 8) {
                v0 = 3;
            }
            else if(button == 16) {
                v0 = v2;
            }
            else {
                v0 = -1;
            }
        }

        return v0;
    }
}

