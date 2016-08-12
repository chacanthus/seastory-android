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

import android.content.SharedPreferences;
import android.content.SharedPreferences$Editor;
import com.badlogic.gdx.Preferences;
import java.util.Iterator;
import java.util.Map;
import java.util.Map$Entry;

public class AndroidPreferences implements Preferences {
    SharedPreferences$Editor editor;
    SharedPreferences sharedPrefs;

    public AndroidPreferences(SharedPreferences preferences) {
        super();
        this.sharedPrefs = preferences;
    }

    public void clear() {
        this.edit();
        this.editor.clear();
    }

    public boolean contains(String key) {
        return this.sharedPrefs.contains(key);
    }

    private void edit() {
        if(this.editor == null) {
            this.editor = this.sharedPrefs.edit();
        }
    }

    public void flush() {
        if(this.editor != null) {
            this.editor.commit();
            this.editor = null;
        }
    }

    public Map get() {
        return this.sharedPrefs.getAll();
    }

    public boolean getBoolean(String key) {
        return this.sharedPrefs.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sharedPrefs.getBoolean(key, defValue);
    }

    public float getFloat(String key) {
        return this.sharedPrefs.getFloat(key, 0f);
    }

    public float getFloat(String key, float defValue) {
        return this.sharedPrefs.getFloat(key, defValue);
    }

    public int getInteger(String key) {
        return this.sharedPrefs.getInt(key, 0);
    }

    public int getInteger(String key, int defValue) {
        return this.sharedPrefs.getInt(key, defValue);
    }

    public long getLong(String key) {
        return this.sharedPrefs.getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return this.sharedPrefs.getLong(key, defValue);
    }

    public String getString(String key) {
        return this.sharedPrefs.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return this.sharedPrefs.getString(key, defValue);
    }

    public Preferences put(Map arg7) {
        this.edit();
        Iterator v0 = arg7.entrySet().iterator();
        while(true) {
            if(!v0.hasNext()) {
                break;
            }

            Object v1 = v0.next();
            if((((Map$Entry)v1).getValue() instanceof Boolean)) {
                this.putBoolean(((Map$Entry)v1).getKey(), ((Map$Entry)v1).getValue().booleanValue());
            }

            if((((Map$Entry)v1).getValue() instanceof Integer)) {
                this.putInteger(((Map$Entry)v1).getKey(), ((Map$Entry)v1).getValue().intValue());
            }

            if((((Map$Entry)v1).getValue() instanceof Long)) {
                this.putLong(((Map$Entry)v1).getKey(), ((Map$Entry)v1).getValue().longValue());
            }

            if((((Map$Entry)v1).getValue() instanceof String)) {
                this.putString(((Map$Entry)v1).getKey(), ((Map$Entry)v1).getValue());
            }

            if(!(((Map$Entry)v1).getValue() instanceof Float)) {
                continue;
            }

            this.putFloat(((Map$Entry)v1).getKey(), ((Map$Entry)v1).getValue().floatValue());
        }

        return this;
    }

    public Preferences putBoolean(String key, boolean val) {
        this.edit();
        this.editor.putBoolean(key, val);
        return this;
    }

    public Preferences putFloat(String key, float val) {
        this.edit();
        this.editor.putFloat(key, val);
        return this;
    }

    public Preferences putInteger(String key, int val) {
        this.edit();
        this.editor.putInt(key, val);
        return this;
    }

    public Preferences putLong(String key, long val) {
        this.edit();
        this.editor.putLong(key, val);
        return this;
    }

    public Preferences putString(String key, String val) {
        this.edit();
        this.editor.putString(key, val);
        return this;
    }

    public void remove(String key) {
        this.edit();
        this.editor.remove(key);
    }
}

