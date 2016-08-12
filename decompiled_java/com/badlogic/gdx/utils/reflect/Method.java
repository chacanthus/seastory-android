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


public final class Method {
    private final java.lang.reflect.Method method;

    Method(java.lang.reflect.Method method) {
        super();
        this.method = method;
    }

    public Class getDeclaringClass() {
        return this.method.getDeclaringClass();
    }

    public String getName() {
        return this.method.getName();
    }

    public Class[] getParameterTypes() {
        return this.method.getParameterTypes();
    }

    public Class getReturnType() {
        return this.method.getReturnType();
    }

    public Object invoke(Object obj, Object[] args) throws ReflectionException {  // has try-catch handlers
        try {
            return this.method.invoke(obj, args);
        }
        catch(InvocationTargetException v0) {
            throw new ReflectionException("Exception occurred in method: " + this.getName(), ((Throwable)v0));
        }
        catch(IllegalAccessException v0_1) {
            throw new ReflectionException("Illegal access to method: " + this.getName(), ((Throwable)v0_1));
        }
        catch(IllegalArgumentException v0_2) {
            throw new ReflectionException("Illegal argument(s) supplied to method: " + this.getName(), ((Throwable)v0_2));
        }
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.method.getModifiers());
    }

    public boolean isAccessible() {
        return this.method.isAccessible();
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
        return Modifier.isFinal(this.method.getModifiers());
    }

    public boolean isNative() {
        return Modifier.isNative(this.method.getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(this.method.getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(this.method.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(this.method.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    public boolean isVarArgs() {
        return this.method.isVarArgs();
    }

    public void setAccessible(boolean accessible) {
        this.method.setAccessible(accessible);
    }
}

