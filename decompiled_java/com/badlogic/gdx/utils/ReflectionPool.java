// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class ReflectionPool extends Pool {
    private final Constructor constructor;

    public ReflectionPool(Class arg4, int initialCapacity, int max) {
        super(initialCapacity, max);
        this.constructor = this.findConstructor(arg4);
        if(this.constructor == null) {
            throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + arg4.getName());
        }
    }

    public ReflectionPool(Class arg3) {
        this(arg3, 16, 2147483647);
    }

    public ReflectionPool(Class arg2, int initialCapacity) {
        this(arg2, initialCapacity, 2147483647);
    }

    private Constructor findConstructor(Class arg6) {  // has try-catch handlers
        Constructor v0;
        Constructor v4 = null;
        Class[] v3 = null;
        try {
            v0 = ClassReflection.getConstructor(arg6, v3);
        }
        catch(Exception v1) {
            v3 = null;
            try {
                v0 = ClassReflection.getDeclaredConstructor(arg6, v3);
                v0.setAccessible(true);
            }
            catch(ReflectionException v2) {
                v0 = v4;
            }
        }

        return v0;
    }

    protected Object newObject() {  // has try-catch handlers
        try {
            return this.constructor.newInstance(null);
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Unable to create new instance: " + this.constructor.getDeclaringClass().getName(), ((Throwable)v0));
        }
    }
}

