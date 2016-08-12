// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

public abstract interface TextureData {
    public enum TextureDataType {
        public static final enum TextureDataType Custom;
        public static final enum TextureDataType Pixmap;

        static  {
            TextureDataType.Pixmap = new TextureDataType("Pixmap", 0);
            TextureDataType.Custom = new TextureDataType("Custom", 1);
            TextureDataType[] v0 = new TextureDataType[2];
            v0[0] = TextureDataType.Pixmap;
            v0[1] = TextureDataType.Custom;
            TextureDataType.$VALUES = v0;
        }

        private TextureDataType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static TextureDataType valueOf(String name) {
            return Enum.valueOf(TextureDataType.class, name);
        }

        public static TextureDataType[] values() {
            return TextureDataType.$VALUES.clone();
        }
    }

    public abstract void consumeCustomData(int arg0);

    public abstract Pixmap consumePixmap();

    public abstract boolean disposePixmap();

    public abstract Format getFormat();

    public abstract int getHeight();

    public abstract TextureDataType getType();

    public abstract int getWidth();

    public abstract boolean isManaged();

    public abstract boolean isPrepared();

    public abstract void prepare();

    public abstract boolean useMipMaps();
}

