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

public class Pools {
    private static final ObjectMap typePools;

    static  {
        Pools.typePools = new ObjectMap();
    }

    private Pools() {
        super();
    }

    public static void free(Object object) {
        if(object == null) {
            throw new IllegalArgumentException("Object cannot be null.");
        }

        Object v0 = Pools.typePools.get(object.getClass());
        if(v0 != null) {
            ((Pool)v0).free(object);
        }
    }

    public static void freeAll(Array objects, boolean samePool) {
        if(objects == null) {
            throw new IllegalArgumentException("Objects cannot be null.");
        }

        Object v3 = null;
        int v0 = 0;
        int v1 = objects.size;
        while(v0 < v1) {
            Object v2 = objects.get(v0);
            if(v2 != null) {
                if(v3 == null) {
                    v3 = Pools.typePools.get(v2.getClass());
                    if(v3 != null) {
                        goto label_17;
                    }

                    goto label_10;
                }

            label_17:
                ((Pool)v3).free(v2);
                if(samePool) {
                    goto label_10;
                }

                v3 = null;
            }

        label_10:
            ++v0;
        }
    }

    public static void freeAll(Array objects) {
        Pools.freeAll(objects, false);
    }

    public static Pool get(Class arg1) {
        return Pools.get(arg1, 100);
    }

    public static Pool get(Class arg2, int max) {
        Object v0 = Pools.typePools.get(arg2);
        if(v0 == null) {
            ReflectionPool v0_1 = new ReflectionPool(arg2, 4, max);
            Pools.typePools.put(arg2, v0_1);
        }

        return ((Pool)v0);
    }

    public static Object obtain(Class arg1) {
        return Pools.get(arg1).obtain();
    }

    public static void set(Class arg1, Pool arg2) {
        Pools.typePools.put(arg1, arg2);
    }
}

