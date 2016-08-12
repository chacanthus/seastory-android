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


public final class Field {
    private final java.lang.reflect.Field field;

    Field(java.lang.reflect.Field field) {
        super();
        this.field = field;
    }

    public Object get(Object obj) throws ReflectionException {  // has try-catch handlers
        try {
            return this.field.get(obj);
        }
        catch(IllegalAccessException v0) {
            throw new ReflectionException("Illegal access to field: " + this.getName(), ((Throwable)v0));
        }
        catch(IllegalArgumentException v0_1) {
            throw new ReflectionException("Object is not an instance of " + this.getDeclaringClass(), ((Throwable)v0_1));
        }
    }

    public Annotation getDeclaredAnnotation(Class arg8) {
        Annotation v5 = null;
        java.lang.annotation.Annotation[] v1 = this.field.getDeclaredAnnotations();
        if(v1 != null) {
            java.lang.annotation.Annotation[] v2 = v1;
            int v4 = v2.length;
            int v3 = 0;
            while(v3 < v4) {
                java.lang.annotation.Annotation v0 = v2[v3];
                if(v0.annotationType().equals(arg8)) {
                    v5 = new Annotation(v0);
                }
                else {
                    ++v3;
                    continue;
                }

                break;
            }
        }

        return v5;
    }

    public Annotation[] getDeclaredAnnotations() {
        java.lang.annotation.Annotation[] v0 = this.field.getDeclaredAnnotations();
        Annotation[] v2 = new Annotation[v0.length];
        int v1;
        for(v1 = 0; v1 < v0.length; ++v1) {
            v2[v1] = new Annotation(v0[v1]);
        }

        return v2;
    }

    public Class getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    public Class getElementType(int index) {
        Class v0_1;
        Type v3 = this.field.getGenericType();
        if((v3 instanceof ParameterizedType)) {
            Type[] v1 = ((ParameterizedType)v3).getActualTypeArguments();
            if(v1.length - 1 >= index) {
                Type v0 = v1[index];
                if(!(v0 instanceof Class)) {
                    if((v0 instanceof ParameterizedType)) {
                        v0 = ((ParameterizedType)v0).getRawType();
                    }
                    else if((v0 instanceof GenericArrayType)) {
                        Type v2 = ((GenericArrayType)v0).getGenericComponentType();
                        if((v2 instanceof Class)) {
                            v0_1 = ArrayReflection.newInstance(((Class)v2), 0).getClass();
                        }
                        else {
                            goto label_26;
                        }
                    }
                    else {
                        goto label_26;
                    }
                }
            }
            else {
                goto label_26;
            }
        }
        else {
        label_26:
            v0_1 = null;
        }

        return v0_1;
    }

    public String getName() {
        return this.field.getName();
    }

    public Class getType() {
        return this.field.getType();
    }

    public boolean isAccessible() {
        return this.field.isAccessible();
    }

    public boolean isAnnotationPresent(Class arg2) {
        return this.field.isAnnotationPresent(arg2);
    }

    public boolean isDefaultAccess() {
        boolean v0;
        if((this.isPrivate()) || (this.isProtected()) || (this.isPublic())) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.field.getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(this.field.getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(this.field.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(this.field.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.field.getModifiers());
    }

    public boolean isSynthetic() {
        return this.field.isSynthetic();
    }

    public boolean isTransient() {
        return Modifier.isTransient(this.field.getModifiers());
    }

    public boolean isVolatile() {
        return Modifier.isVolatile(this.field.getModifiers());
    }

    public void set(Object obj, Object value) throws ReflectionException {  // has try-catch handlers
        try {
            this.field.set(obj, value);
            return;
        }
        catch(IllegalAccessException v0) {
            throw new ReflectionException("Illegal access to field: " + this.getName(), ((Throwable)v0));
        }
        catch(IllegalArgumentException v0_1) {
            throw new ReflectionException("Argument not valid for field: " + this.getName(), ((Throwable)v0_1));
        }
    }

    public void setAccessible(boolean accessible) {
        this.field.setAccessible(accessible);
    }
}

