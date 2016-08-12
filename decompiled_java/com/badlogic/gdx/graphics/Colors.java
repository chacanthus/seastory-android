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

import com.badlogic.gdx.utils.ObjectMap;

public final class Colors {
    private static final ObjectMap map;

    static  {
        Colors.map = new ObjectMap();
        Colors.reset();
    }

    private Colors() {
        super();
    }

    public static Color get(String name) {
        return Colors.map.get(name);
    }

    public static ObjectMap getColors() {
        return Colors.map;
    }

    public static Color put(String name, Color color) {
        return Colors.map.put(name, color);
    }

    public static void reset() {
        Colors.map.clear();
        Colors.map.put("CLEAR", Color.CLEAR);
        Colors.map.put("WHITE", Color.WHITE);
        Colors.map.put("BLACK", Color.BLACK);
        Colors.map.put("RED", Color.RED);
        Colors.map.put("GREEN", Color.GREEN);
        Colors.map.put("BLUE", Color.BLUE);
        Colors.map.put("LIGHT_GRAY", Color.LIGHT_GRAY);
        Colors.map.put("GRAY", Color.GRAY);
        Colors.map.put("DARK_GRAY", Color.DARK_GRAY);
        Colors.map.put("PINK", Color.PINK);
        Colors.map.put("ORANGE", Color.ORANGE);
        Colors.map.put("YELLOW", Color.YELLOW);
        Colors.map.put("MAGENTA", Color.MAGENTA);
        Colors.map.put("CYAN", Color.CYAN);
        Colors.map.put("OLIVE", Color.OLIVE);
        Colors.map.put("PURPLE", Color.PURPLE);
        Colors.map.put("MAROON", Color.MAROON);
        Colors.map.put("TEAL", Color.TEAL);
        Colors.map.put("NAVY", Color.NAVY);
    }
}

