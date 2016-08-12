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

import com.badlogic.gdx.files.FileHandle;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

public class I18NBundle {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Locale ROOT_LOCALE;
    private static boolean exceptionOnMissingKey;
    private TextFormatter formatter;
    private Locale locale;
    private I18NBundle parent;
    private ObjectMap properties;
    private static boolean simpleFormatter;

    static  {
        I18NBundle.ROOT_LOCALE = new Locale("", "", "");
        I18NBundle.simpleFormatter = false;
        I18NBundle.exceptionOnMissingKey = true;
    }

    public I18NBundle() {
        super();
    }

    private static boolean checkFileExistence(FileHandle fh) {  // has try-catch handlers
        boolean v1;
        try {
            fh.read().close();
            v1 = true;
        }
        catch(Exception v0) {
            v1 = false;
        }

        return v1;
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle) {
        return I18NBundle.createBundleImpl(baseFileHandle, Locale.getDefault(), "UTF-8");
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, String encoding) {
        return I18NBundle.createBundleImpl(baseFileHandle, Locale.getDefault(), encoding);
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale) {
        return I18NBundle.createBundleImpl(baseFileHandle, locale, "UTF-8");
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale, String encoding) {
        return I18NBundle.createBundleImpl(baseFileHandle, locale, encoding);
    }

    private static I18NBundle createBundleImpl(FileHandle baseFileHandle, Locale locale, String encoding) {
        I18NBundle v1;
        if(baseFileHandle != null && locale != null && encoding != null) {
            I18NBundle v0 = null;
            Locale v5 = locale;
            do {
                List v3 = I18NBundle.getCandidateLocales(v5);
                v1 = I18NBundle.loadBundleChain(baseFileHandle, encoding, v3, 0, v0);
                if(v1 != null) {
                    Locale v2 = v1.getLocale();
                    boolean v4 = v2.equals(I18NBundle.ROOT_LOCALE);
                    if((v4) && !v2.equals(locale)) {
                        if(v3.size() == 1 && (v2.equals(v3.get(0)))) {
                            break;
                        }

                        if(!v4) {
                            goto label_47;
                        }

                        if(v0 != null) {
                            goto label_47;
                        }

                        v0 = v1;
                        goto label_47;
                    }
                }
                else {
                label_47:
                    v5 = I18NBundle.getFallbackLocale(v5);
                    if(v5 != null) {
                        continue;
                    }
                }

                break;
            }
            while(true);

            if(v1 == null) {
                if(v0 == null) {
                    throw new MissingResourceException("Can\'t find bundle for base file handle " + baseFileHandle.path() + ", locale " + locale, baseFileHandle + "_" + locale, "");
                }
                else {
                    v1 = v0;
                }
            }

            return v1;
        }

        throw new NullPointerException();
    }

    public String format(String key, Object[] args) {
        return this.formatter.format(this.get(key), args);
    }

    public final String get(String key) {
        Object v1_1;
        String v0_1;
        Object v0 = this.properties.get(key);
        if(v0 == null) {
            if(this.parent != null) {
                v0_1 = this.parent.get(key);
            }

            if(v0_1 != null) {
                goto label_27;
            }

            if(I18NBundle.exceptionOnMissingKey) {
                throw new MissingResourceException("Can\'t find bundle key " + key, this.getClass().getName(), key);
            }

            String v1 = "???" + key + "???";
        }
        else {
        label_27:
            v1_1 = v0_1;
        }

        return ((String)v1_1);
    }

    private static List getCandidateLocales(Locale locale) {
        Locale v4;
        String v1 = locale.getLanguage();
        String v0 = locale.getCountry();
        String v3 = locale.getVariant();
        ArrayList v2 = new ArrayList(4);
        if(v3.length() > 0) {
            ((List)v2).add(locale);
        }

        if(v0.length() > 0) {
            if(((List)v2).size() == 0) {
                v4 = locale;
            }
            else {
                v4 = new Locale(v1, v0);
            }

            ((List)v2).add(v4);
        }

        if(v1.length() > 0) {
            if(((List)v2).size() != 0) {
                locale = new Locale(v1);
            }

            ((List)v2).add(locale);
        }

        ((List)v2).add(I18NBundle.ROOT_LOCALE);
        return ((List)v2);
    }

    public static boolean getExceptionOnMissingKey() {
        return I18NBundle.exceptionOnMissingKey;
    }

    private static Locale getFallbackLocale(Locale locale) {
        Locale v0 = Locale.getDefault();
        if(locale.equals(v0)) {
            v0 = null;
        }

        return v0;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public static boolean getSimpleFormatter() {
        return I18NBundle.simpleFormatter;
    }

    protected void load(Reader reader) throws IOException {
        this.properties = new ObjectMap();
        PropertiesUtils.load(this.properties, reader);
    }

    private static I18NBundle loadBundle(FileHandle baseFileHandle, String encoding, Locale targetLocale) {  // has try-catch handlers
        I18NBundle v1;
        FileHandle v3;
        I18NBundle v0 = null;
        Closeable v4 = null;
        try {
            v3 = I18NBundle.toFileHandle(baseFileHandle, targetLocale);
            if(I18NBundle.checkFileExistence(v3)) {
                v1 = new I18NBundle();
                goto label_6;
            }

            goto label_9;
        }
        catch(Throwable v5) {
        }
        catch(IOException v2) {
            goto label_14;
            try {
            label_6:
                Reader v4_1 = v3.reader(encoding);
                v1.load(v4_1);
                v0 = v1;
                goto label_9;
            }
            catch(Throwable v5) {
            }
            catch(IOException v2) {
                try {
                label_14:
                    throw new GdxRuntimeException(((Throwable)v2));
                }
                catch(Throwable v5) {
                }
            }
        }

        StreamUtils.closeQuietly(v4);
        throw v5;
    label_9:
        StreamUtils.closeQuietly(v4);
        if(v0 != null) {
            v0.setLocale(targetLocale);
        }

        return v0;
    }

    private static I18NBundle loadBundleChain(FileHandle baseFileHandle, String encoding, List arg6, int candidateIndex, I18NBundle baseBundle) {
        Object v2 = arg6.get(candidateIndex);
        I18NBundle v1 = null;
        if(candidateIndex != arg6.size() - 1) {
            v1 = I18NBundle.loadBundleChain(baseFileHandle, encoding, arg6, candidateIndex + 1, baseBundle);
            goto label_7;
        }
        else if(baseBundle == null) {
        label_7:
            I18NBundle v0 = I18NBundle.loadBundle(baseFileHandle, encoding, ((Locale)v2));
            if(v0 != null) {
                v0.parent = v1;
                baseBundle = v0;
            }
            else {
                baseBundle = v1;
            }
        }
        else if(((Locale)v2).equals(I18NBundle.ROOT_LOCALE)) {
        }
        else {
            goto label_7;
        }

        return baseBundle;
    }

    public static void setExceptionOnMissingKey(boolean enabled) {
        I18NBundle.exceptionOnMissingKey = enabled;
    }

    private void setLocale(Locale locale) {
        boolean v0;
        this.locale = locale;
        if(!I18NBundle.simpleFormatter) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.formatter = new TextFormatter(locale, v0);
    }

    public static void setSimpleFormatter(boolean enabled) {
        I18NBundle.simpleFormatter = enabled;
    }

    private static FileHandle toFileHandle(FileHandle baseFileHandle, Locale locale) {
        char v8 = '_';
        StringBuilder v5 = new StringBuilder(baseFileHandle.name());
        if(!locale.equals(I18NBundle.ROOT_LOCALE)) {
            String v4 = locale.getLanguage();
            String v0 = locale.getCountry();
            String v6 = locale.getVariant();
            boolean v2 = "".equals(v4);
            boolean v1 = "".equals(v0);
            boolean v3 = "".equals(v6);
            if((v2) && (v1) && (v3)) {
                goto label_25;
            }

            v5.append(v8);
            if(v3) {
                goto label_30;
            }

            v5.append(v4).append(v8).append(v0).append(v8).append(v6);
            goto label_25;
        label_30:
            if(v1) {
                goto label_35;
            }

            v5.append(v4).append(v8).append(v0);
            goto label_25;
        label_35:
            v5.append(v4);
        }

    label_25:
        return baseFileHandle.sibling(v5.append(".properties").toString());
    }
}

