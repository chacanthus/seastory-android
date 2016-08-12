// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.reflect;


public final class ClassReflection {
    public ClassReflection() {
        super();
    }

    public static Class forName(String name) throws ReflectionException {  // has try-catch handlers
        try {
            return Class.forName(name);
        }
        catch(ClassNotFoundException v0) {
            throw new ReflectionException("Class not found: " + name, ((Throwable)v0));
        }
    }

    public static Constructor getConstructor(Class c, Class[] parameterTypes) throws ReflectionException {  // has try-catch handlers
        try {
            return new Constructor(c.getConstructor(parameterTypes));
        }
        catch(NoSuchMethodException v0) {
            throw new ReflectionException("Constructor not found for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation occurred while getting constructor for class: \'" + c.getName() + "\'.", ((Throwable)v0_1));
        }
    }

    public static Constructor[] getConstructors(Class c) {
        java.lang.reflect.Constructor[] v0 = c.getConstructors();
        Constructor[] v3 = new Constructor[v0.length];
        int v1 = 0;
        int v2 = v0.length;
        while(v1 < v2) {
            v3[v1] = new Constructor(v0[v1]);
            ++v1;
        }

        return v3;
    }

    public static Annotation getDeclaredAnnotation(Class c, Class arg7) {
        Annotation v5;
        java.lang.annotation.Annotation[] v2 = c.getDeclaredAnnotations();
        int v4 = v2.length;
        int v3 = 0;
        while(true) {
            if(v3 < v4) {
                java.lang.annotation.Annotation v0 = v2[v3];
                if(v0.annotationType().equals(arg7)) {
                    v5 = new Annotation(v0);
                }
                else {
                    ++v3;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_10;
        }

        v5 = null;
    label_10:
        return v5;
    }

    public static Annotation[] getDeclaredAnnotations(Class c) {
        java.lang.annotation.Annotation[] v0 = c.getDeclaredAnnotations();
        Annotation[] v2 = new Annotation[v0.length];
        int v1;
        for(v1 = 0; v1 < v0.length; ++v1) {
            v2[v1] = new Annotation(v0[v1]);
        }

        return v2;
    }

    public static Constructor getDeclaredConstructor(Class c, Class[] parameterTypes) throws ReflectionException {  // has try-catch handlers
        try {
            return new Constructor(c.getDeclaredConstructor(parameterTypes));
        }
        catch(NoSuchMethodException v0) {
            throw new ReflectionException("Constructor not found for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation while getting constructor for class: " + c.getName(), ((Throwable)v0_1));
        }
    }

    public static Field getDeclaredField(Class c, String name) throws ReflectionException {  // has try-catch handlers
        try {
            return new Field(c.getDeclaredField(name));
        }
        catch(NoSuchFieldException v0) {
            throw new ReflectionException("Field not found: " + name + ", for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation while getting field: " + name + ", for class: " + c.getName(), ((Throwable)v0_1));
        }
    }

    public static Field[] getDeclaredFields(Class c) {
        java.lang.reflect.Field[] v0 = c.getDeclaredFields();
        Field[] v3 = new Field[v0.length];
        int v1 = 0;
        int v2 = v0.length;
        while(v1 < v2) {
            v3[v1] = new Field(v0[v1]);
            ++v1;
        }

        return v3;
    }

    public static Method getDeclaredMethod(Class c, String name, Class[] parameterTypes) throws ReflectionException {  // has try-catch handlers
        try {
            return new Method(c.getDeclaredMethod(name, parameterTypes));
        }
        catch(NoSuchMethodException v0) {
            throw new ReflectionException("Method not found: " + name + ", for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation while getting method: " + name + ", for class: " + c.getName(), ((Throwable)v0_1));
        }
    }

    public static Method[] getDeclaredMethods(Class c) {
        java.lang.reflect.Method[] v2 = c.getDeclaredMethods();
        Method[] v3 = new Method[v2.length];
        int v0 = 0;
        int v1 = v2.length;
        while(v0 < v1) {
            v3[v0] = new Method(v2[v0]);
            ++v0;
        }

        return v3;
    }

    public static Field getField(Class c, String name) throws ReflectionException {  // has try-catch handlers
        try {
            return new Field(c.getField(name));
        }
        catch(NoSuchFieldException v0) {
            throw new ReflectionException("Field not found: " + name + ", for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation while getting field: " + name + ", for class: " + c.getName(), ((Throwable)v0_1));
        }
    }

    public static Field[] getFields(Class c) {
        java.lang.reflect.Field[] v0 = c.getFields();
        Field[] v3 = new Field[v0.length];
        int v1 = 0;
        int v2 = v0.length;
        while(v1 < v2) {
            v3[v1] = new Field(v0[v1]);
            ++v1;
        }

        return v3;
    }

    public static Method getMethod(Class c, String name, Class[] parameterTypes) throws ReflectionException {  // has try-catch handlers
        try {
            return new Method(c.getMethod(name, parameterTypes));
        }
        catch(NoSuchMethodException v0) {
            throw new ReflectionException("Method not found: " + name + ", for class: " + c.getName(), ((Throwable)v0));
        }
        catch(SecurityException v0_1) {
            throw new ReflectionException("Security violation while getting method: " + name + ", for class: " + c.getName(), ((Throwable)v0_1));
        }
    }

    public static Method[] getMethods(Class c) {
        java.lang.reflect.Method[] v2 = c.getMethods();
        Method[] v3 = new Method[v2.length];
        int v0 = 0;
        int v1 = v2.length;
        while(v0 < v1) {
            v3[v0] = new Method(v2[v0]);
            ++v0;
        }

        return v3;
    }

    public static String getSimpleName(Class c) {
        return c.getSimpleName();
    }

    public static boolean isAnnotationPresent(Class c, Class arg2) {
        return c.isAnnotationPresent(arg2);
    }

    public static boolean isArray(Class c) {
        return c.isArray();
    }

    public static boolean isAssignableFrom(Class c1, Class c2) {
        return c1.isAssignableFrom(c2);
    }

    public static boolean isInstance(Class c, Object obj) {
        return c.isInstance(obj);
    }

    public static boolean isMemberClass(Class c) {
        return c.isMemberClass();
    }

    public static boolean isStaticClass(Class c) {
        return Modifier.isStatic(c.getModifiers());
    }

    public static Object newInstance(Class arg4) throws ReflectionException {  // has try-catch handlers
        try {
            return arg4.newInstance();
        }
        catch(IllegalAccessException v0) {
            throw new ReflectionException("Could not instantiate instance of class: " + arg4.getName(), ((Throwable)v0));
        }
        catch(InstantiationException v0_1) {
            throw new ReflectionException("Could not instantiate instance of class: " + arg4.getName(), ((Throwable)v0_1));
        }
    }
}

