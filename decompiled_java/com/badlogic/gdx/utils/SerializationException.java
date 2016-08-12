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

public class SerializationException extends RuntimeException {
    private StringBuffer trace;

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super("", cause);
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException() {
        super();
    }

    public void addTrace(String info) {
        if(info == null) {
            throw new IllegalArgumentException("info cannot be null.");
        }

        if(this.trace == null) {
            this.trace = new StringBuffer(512);
        }

        this.trace.append('\n');
        this.trace.append(info);
    }

    private boolean causedBy(Throwable ex, Class type) {
        boolean v1;
        Throwable v0 = ex.getCause();
        if(v0 == null || v0 == ex) {
            v1 = false;
        }
        else if(type.isAssignableFrom(v0.getClass())) {
            v1 = true;
        }
        else {
            v1 = this.causedBy(v0, type);
        }

        return v1;
    }

    public boolean causedBy(Class type) {
        return this.causedBy(((Throwable)this), type);
    }

    public String getMessage() {
        String v1;
        if(this.trace == null) {
            v1 = super.getMessage();
        }
        else {
            StringBuffer v0 = new StringBuffer(512);
            v0.append(super.getMessage());
            if(v0.length() > 0) {
                v0.append('\n');
            }

            v0.append("Serialization trace:");
            v0.append(this.trace);
            v1 = v0.toString();
        }

        return v1;
    }
}

