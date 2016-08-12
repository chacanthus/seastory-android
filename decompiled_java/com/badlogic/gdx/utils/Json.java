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
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map$Entry;

public class Json {
    class FieldMetadata {
        Class elementType;

        public FieldMetadata(Field field) {
            int v0;
            super();
            this.field = field;
            if((ClassReflection.isAssignableFrom(ObjectMap.class, field.getType())) || (ClassReflection.isAssignableFrom(Map.class, field.getType()))) {
                v0 = 1;
            }
            else {
                v0 = 0;
            }

            this.elementType = field.getElementType(v0);
        }
    }

    public abstract class ReadOnlySerializer implements Serializer {
        public ReadOnlySerializer() {
            super();
        }

        public abstract Object read(Json arg0, JsonValue arg1, Class arg2);

        public void write(Json json, Object arg2, Class knownType) {
        }
    }

    public abstract interface Serializable {
        public abstract void read(Json arg0, JsonValue arg1);

        public abstract void write(Json arg0);
    }

    public abstract interface Serializer {
        public abstract Object read(Json arg0, JsonValue arg1, Class arg2);

        public abstract void write(Json arg0, Object arg1, Class arg2);
    }

    private final ObjectMap classToDefaultValues;
    private final ObjectMap classToSerializer;
    private final ObjectMap classToTag;
    private static final boolean debug;
    private Serializer defaultSerializer;
    private boolean enumNames;
    private final Object[] equals1;
    private final Object[] equals2;
    private boolean ignoreUnknownFields;
    private OutputType outputType;
    private boolean quoteLongValues;
    private final ObjectMap tagToClass;
    private String typeName;
    private final ObjectMap typeToFields;
    private boolean usePrototypes;
    private JsonWriter writer;

    public Json() {
        super();
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        Object[] v0 = new Object[1];
        v0[0] = null;
        this.equals1 = v0;
        v0 = new Object[1];
        v0[0] = null;
        this.equals2 = v0;
        this.outputType = OutputType.minimal;
    }

    public Json(OutputType outputType) {
        super();
        this.typeName = "class";
        this.usePrototypes = true;
        this.enumNames = true;
        this.typeToFields = new ObjectMap();
        this.tagToClass = new ObjectMap();
        this.classToTag = new ObjectMap();
        this.classToSerializer = new ObjectMap();
        this.classToDefaultValues = new ObjectMap();
        Object[] v0 = new Object[1];
        v0[0] = null;
        this.equals1 = v0;
        v0 = new Object[1];
        v0[0] = null;
        this.equals2 = v0;
        this.outputType = outputType;
    }

    public void addClassTag(String tag, Class type) {
        this.tagToClass.put(tag, type);
        this.classToTag.put(type, tag);
    }

    private String convertToString(Enum e) {
        String v0;
        if(this.enumNames) {
            v0 = e.name();
        }
        else {
            v0 = e.toString();
        }

        return v0;
    }

    private String convertToString(Object object) {
        String v0;
        if((object instanceof Enum)) {
            v0 = this.convertToString(((Enum)object));
        }
        else if((object instanceof Class)) {
            v0 = ((Class)object).getName();
        }
        else {
            v0 = String.valueOf(object);
        }

        return v0;
    }

    public Object fromJson(Class arg5, FileHandle file) {  // has try-catch handlers
        Class v1 = null;
        try {
            return this.readValue(arg5, v1, new JsonReader().parse(file));
        }
        catch(Exception v0) {
            throw new SerializationException("Error reading file: " + file, ((Throwable)v0));
        }
    }

    public Object fromJson(Class arg3, InputStream input) {
        return this.readValue(arg3, null, new JsonReader().parse(input));
    }

    public Object fromJson(Class arg3, Reader reader) {
        return this.readValue(arg3, null, new JsonReader().parse(reader));
    }

    public Object fromJson(Class arg5, Class elementType, FileHandle file) {  // has try-catch handlers
        try {
            return this.readValue(arg5, elementType, new JsonReader().parse(file));
        }
        catch(Exception v0) {
            throw new SerializationException("Error reading file: " + file, ((Throwable)v0));
        }
    }

    public Object fromJson(Class arg2, Class elementType, InputStream input) {
        return this.readValue(arg2, elementType, new JsonReader().parse(input));
    }

    public Object fromJson(Class arg2, Class elementType, Reader reader) {
        return this.readValue(arg2, elementType, new JsonReader().parse(reader));
    }

    public Object fromJson(Class arg2, Class elementType, String json) {
        return this.readValue(arg2, elementType, new JsonReader().parse(json));
    }

    public Object fromJson(Class arg2, Class elementType, char[] data, int offset, int length) {
        return this.readValue(arg2, elementType, new JsonReader().parse(data, offset, length));
    }

    public Object fromJson(Class arg3, String json) {
        return this.readValue(arg3, null, new JsonReader().parse(json));
    }

    public Object fromJson(Class arg3, char[] data, int offset, int length) {
        return this.readValue(arg3, null, new JsonReader().parse(data, offset, length));
    }

    public Class getClass(String tag) {
        return this.tagToClass.get(tag);
    }

    private Object[] getDefaultValues(Class type) {  // has try-catch handlers
        Object v7;
        Object[] v10 = null;
        if(this.usePrototypes) {
            goto label_4;
        }

        goto label_3;
    label_4:
        if(!this.classToDefaultValues.containsKey(type)) {
            goto label_10;
        }

        Object v10_1 = this.classToDefaultValues.get(type);
        goto label_3;
        try {
        label_10:
            v7 = this.newInstance(type);
        }
        catch(Exception v0) {
            this.classToDefaultValues.put(type, v10);
            goto label_3;
        }

        OrderedMap v2 = this.getFields(type);
        Object[] v9 = new Object[((ObjectMap)v2).size];
        this.classToDefaultValues.put(type, v9);
        int v3 = 0;
        Iterator v5 = ((ObjectMap)v2).values().iterator();
        while(true) {
            if(!v5.hasNext()) {
                break;
            }

            Field v1 = v5.next().field;
            int v4 = v3 + 1;
            try {
                v9[v3] = v1.get(v7);
                v3 = v4;
                continue;
            }
            catch(RuntimeException v8) {
                v0_1 = new SerializationException(((Throwable)v8));
                v0_1.addTrace(v1 + " (" + type.getName() + ")");
                throw v0_1;
            }
            catch(SerializationException v0_1) {
                v0_1.addTrace(v1 + " (" + type.getName() + ")");
                throw v0_1;
            }
            catch(ReflectionException v0_2) {
                throw new SerializationException("Error accessing field: " + v1.getName() + " (" + type.getName() + ")", ((Throwable)v0_2));
            }
        }

        v10 = v9;
    label_3:
        return ((Object[])v10_1);
    }

    private OrderedMap getFields(Class type) {  // has try-catch handlers
        Object v4 = this.typeToFields.get(type);
        if(v4 == null) {
            goto label_4;
        }

        goto label_3;
    label_4:
        Array v1 = new Array();
        Class v8;
        for(v8 = type; v8 != Object.class; v8 = v8.getSuperclass()) {
            v1.add(v8);
        }

        ArrayList v0 = new ArrayList();
        int v5;
        for(v5 = v1.size - 1; v5 >= 0; --v5) {
            Collections.addAll(((Collection)v0), ClassReflection.getDeclaredFields(v1.get(v5)));
        }

        OrderedMap v7 = new OrderedMap();
        v5 = 0;
        int v6 = v0.size();
        while(v5 < v6) {
            Object v3 = v0.get(v5);
            if(!((Field)v3).isTransient()) {
                goto label_29;
            }

            goto label_27;
        label_29:
            if(((Field)v3).isStatic()) {
                goto label_27;
            }

            if(((Field)v3).isSynthetic()) {
                goto label_27;
            }

            if(!((Field)v3).isAccessible()) {
                try {
                    ((Field)v3).setAccessible(true);
                }
                catch(AccessControlException v2) {
                    goto label_27;
                }
            }

            v7.put(((Field)v3).getName(), new FieldMetadata(((Field)v3)));
        label_27:
            ++v5;
        }

        this.typeToFields.put(type, v7);
        OrderedMap v4_1 = v7;
    label_3:
        return v4_1;
    }

    public Serializer getSerializer(Class arg2) {
        return this.classToSerializer.get(arg2);
    }

    public String getTag(Class type) {
        return this.classToTag.get(type);
    }

    public JsonWriter getWriter() {
        return this.writer;
    }

    protected Object newInstance(Class type) {  // has try-catch handlers
        Object v4;
        try {
            v4 = ClassReflection.newInstance(type);
        }
        catch(Exception v1) {
            try {
                Constructor v0 = ClassReflection.getDeclaredConstructor(type, new Class[0]);
                v0.setAccessible(true);
                v4 = v0.newInstance(new Object[0]);
                goto label_2;
            }
            catch(Exception v3) {
                v1 = v3;
            }
            catch(ReflectionException v2) {
                if(!ClassReflection.isAssignableFrom(Enum.class, type)) {
                    goto label_23;
                }

                if(type.getEnumConstants() == null) {
                    type = type.getSuperclass();
                }

                v4 = type.getEnumConstants()[0];
                goto label_2;
            label_23:
                if(type.isArray()) {
                    throw new SerializationException("Encountered JSON object when expected array of type: " + type.getName(), ((Throwable)v1));
                }

                if((ClassReflection.isMemberClass(type)) && !ClassReflection.isStaticClass(type)) {
                    throw new SerializationException("Class cannot be created (non-static member class): " + type.getName(), ((Throwable)v1));
                }

                throw new SerializationException("Class cannot be created (missing no-arg constructor): " + type.getName(), ((Throwable)v1));
            }
            catch(SecurityException v4_1) {
            }

            throw new SerializationException("Error constructing instance of class: " + type.getName(), ((Throwable)v1));
        }

    label_2:
        return v4;
    }

    public String prettyPrint(Object object) {
        return this.prettyPrint(object, 0);
    }

    public String prettyPrint(Object object, int singleLineColumns) {
        return this.prettyPrint(this.toJson(object), singleLineColumns);
    }

    public String prettyPrint(String json, int singleLineColumns) {
        return new JsonReader().parse(json).prettyPrint(this.outputType, singleLineColumns);
    }

    public String prettyPrint(Object object, PrettyPrintSettings settings) {
        return this.prettyPrint(this.toJson(object), settings);
    }

    public String prettyPrint(String json, PrettyPrintSettings settings) {
        return new JsonReader().parse(json).prettyPrint(settings);
    }

    public String prettyPrint(String json) {
        return this.prettyPrint(json, 0);
    }

    public void readField(Object object, Field field, String jsonName, Class elementType, JsonValue jsonMap) {  // has try-catch handlers
        JsonValue v1 = jsonMap.get(jsonName);
        if(v1 != null) {
            try {
                field.set(object, this.readValue(field.getType(), elementType, v1));
            }
            catch(RuntimeException v2) {
                v0 = new SerializationException(((Throwable)v2));
                v0.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + ")");
                throw v0;
            }
            catch(SerializationException v0) {
                v0.addTrace(field.getName() + " (" + field.getDeclaringClass().getName() + ")");
                throw v0;
            }
            catch(ReflectionException v0_1) {
                throw new SerializationException("Error accessing field: " + field.getName() + " (" + field.getDeclaringClass().getName() + ")", ((Throwable)v0_1));
            }
        }
    }

    public void readField(Object object, String name, JsonValue jsonData) {
        this.readField(object, name, name, null, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, Class elementType, JsonValue jsonMap) {
        Class v8 = object.getClass();
        Object v7 = this.getFields(v8).get(fieldName);
        if(v7 == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + v8.getName() + ")");
        }

        Field v2 = ((FieldMetadata)v7).field;
        if(elementType == null) {
            elementType = ((FieldMetadata)v7).elementType;
        }

        this.readField(object, v2, jsonName, elementType, jsonMap);
    }

    public void readField(Object object, String name, Class elementType, JsonValue jsonData) {
        this.readField(object, name, name, elementType, jsonData);
    }

    public void readField(Object object, String fieldName, String jsonName, JsonValue jsonData) {
        this.readField(object, fieldName, jsonName, null, jsonData);
    }

    public void readFields(Object object, JsonValue jsonMap) {  // has try-catch handlers
        Class v6 = object.getClass();
        OrderedMap v3 = this.getFields(v6);
        JsonValue v0;
        for(v0 = jsonMap.child; v0 != null; v0 = v0.next) {
            Object v4 = ((ObjectMap)v3).get(v0.name());
            if(v4 != null) {
                Field v2 = ((FieldMetadata)v4).field;
                try {
                    v2.set(object, this.readValue(v2.getType(), ((FieldMetadata)v4).elementType, v0));
                }
                catch(RuntimeException v5) {
                    v1 = new SerializationException(((Throwable)v5));
                    v1.addTrace(v2.getName() + " (" + v6.getName() + ")");
                    throw v1;
                }
                catch(SerializationException v1) {
                    v1.addTrace(v2.getName() + " (" + v6.getName() + ")");
                    throw v1;
                }
                catch(ReflectionException v1_1) {
                    throw new SerializationException("Error accessing field: " + v2.getName() + " (" + v6.getName() + ")", ((Throwable)v1_1));
                }
            }
            else if(!this.ignoreUnknownFields) {
                throw new SerializationException("Field not found: " + v0.name() + " (" + v6.getName() + ")");
            }
        }
    }

    public Object readValue(String name, Class arg4, JsonValue jsonMap) {
        return this.readValue(arg4, null, jsonMap.get(name));
    }

    public Object readValue(String name, Class arg3, Class elementType, JsonValue jsonMap) {
        return this.readValue(arg3, elementType, jsonMap.get(name));
    }

    public Object readValue(Class arg22, Class elementType, JsonValue jsonData) {  // has try-catch handlers
        Boolean v15_11;
        Float v15_10;
        Integer v15_9;
        Long v15_8;
        Double v15_7;
        Short v15_6;
        Byte v15_5;
        String v15_4;
        ArrayList v15_3;
        Object v16;
        JsonValue v12;
        String v5;
        if(jsonData != null) {
            goto label_3;
        }

        Object v15 = null;
        goto label_2;
    label_3:
        if(jsonData.isObject()) {
            if(this.typeName == null) {
                v5 = null;
            }
            else {
                v5 = jsonData.getString(this.typeName, null);
            }

            if(v5 != null) {
                jsonData.remove(this.typeName);
                arg22 = this.getClass(v5);
                if(arg22 == null) {
                    try {
                        arg22 = ClassReflection.forName(v5);
                    }
                    catch(ReflectionException v9) {
                        throw new SerializationException(((Throwable)v9));
                    }
                }
            }

            if(arg22 != null) {
                goto label_51;
            }

            if(this.defaultSerializer == null) {
                goto label_49;
            }

            v15 = this.defaultSerializer.read(this, jsonData, arg22);
            goto label_2;
        label_49:
            JsonValue v15_1 = jsonData;
            goto label_2;
        label_51:
            if(arg22 != String.class && arg22 != Integer.class && arg22 != Boolean.class && arg22 != Float.class && arg22 != Long.class && arg22 != Double.class && arg22 != Short.class && arg22 != Byte.class && arg22 != Character.class && !ClassReflection.isAssignableFrom(Enum.class, arg22)) {
                if(this.typeName != null && (ClassReflection.isAssignableFrom(Collection.class, arg22))) {
                    v12 = jsonData.get("items");
                    goto label_113;
                }

                v16 = this.classToSerializer.get(arg22);
                if(v16 == null) {
                    goto label_140;
                }

                v15 = v16.read(this, jsonData, arg22);
                goto label_2;
            label_140:
                Object v14 = this.newInstance(arg22);
                if(!(v14 instanceof Serializable)) {
                    goto label_151;
                }

                v14.read(this, jsonData);
                v15 = v14;
                goto label_2;
            label_151:
                if(!(v14 instanceof ObjectMap)) {
                    goto label_169;
                }

                v15 = v14;
                JsonValue v4;
                for(v4 = jsonData.child; true; v4 = v4.next) {
                    if(v4 == null) {
                        goto label_2;
                    }

                    ((ObjectMap)v15).put(v4.name(), this.readValue(elementType, null, v4));
                }

            label_169:
                if(!(v14 instanceof ArrayMap)) {
                    goto label_187;
                }

                v15 = v14;
                for(v4 = jsonData.child; true; v4 = v4.next) {
                    if(v4 == null) {
                        goto label_2;
                    }

                    ((ArrayMap)v15).put(v4.name(), this.readValue(elementType, null, v4));
                }

            label_187:
                if(!(v14 instanceof Map)) {
                    goto label_205;
                }

                v15 = v14;
                for(v4 = jsonData.child; true; v4 = v4.next) {
                    if(v4 == null) {
                        goto label_2;
                    }

                    ((Map)v15).put(v4.name(), this.readValue(elementType, null, v4));
                }

            label_205:
                this.readFields(v14, jsonData);
                v15 = v14;
                goto label_2;
            }

            v15 = this.readValue("value", arg22, jsonData);
            goto label_2;
        }
        else {
            v12 = jsonData;
        }

    label_113:
        if(arg22 != null) {
            v16 = this.classToSerializer.get(arg22);
            if(v16 != null) {
                v15 = v16.read(this, v12, arg22);
                goto label_2;
            }
        }

        if(!v12.isArray()) {
            goto label_308;
        }

        if(arg22 == null || arg22 == Object.class) {
            arg22 = Array.class;
        }

        if(!ClassReflection.isAssignableFrom(Array.class, arg22)) {
            goto label_244;
        }

        if(arg22 == Array.class) {
            Array v15_2 = new Array();
        }
        else {
            v15 = this.newInstance(arg22);
        }

        for(v4 = v12.child; true; v4 = v4.next) {
            if(v4 == null) {
                goto label_2;
            }

            ((Array)v15).add(this.readValue(elementType, null, v4));
        }

    label_244:
        if(!ClassReflection.isAssignableFrom(Collection.class, arg22)) {
            goto label_268;
        }

        if(arg22.isInterface()) {
            v15_3 = new ArrayList();
        }
        else {
            v15 = this.newInstance(arg22);
        }

        for(v4 = v12.child; true; v4 = v4.next) {
            if(v4 == null) {
                goto label_2;
            }

            ((Collection)v15_3).add(this.readValue(elementType, null, v4));
        }

    label_268:
        if(!arg22.isArray()) {
            goto label_294;
        }

        Class v6 = arg22.getComponentType();
        if(elementType == null) {
            elementType = v6;
        }

        v15 = ArrayReflection.newInstance(v6, v12.size);
        v4 = v12.child;
        int v11;
        for(v11 = 0; true; ++v11) {
            if(v4 == null) {
                goto label_2;
            }

            ArrayReflection.set(v15, v11, this.readValue(elementType, null, v4));
            v4 = v4.next;
        }

    label_294:
        throw new SerializationException("Unable to convert value to required type: " + v12 + " (" + arg22.getName() + ")");
    label_308:
        if(!v12.isNumber()) {
            goto label_550;
        }

        if(arg22 != null) {
            try {
                if(arg22 != Float.TYPE && arg22 != Float.class) {
                    if(arg22 != Integer.TYPE && arg22 != Integer.class) {
                        if(arg22 != Long.TYPE && arg22 != Long.class) {
                            if(arg22 != Double.TYPE && arg22 != Double.class) {
                                if(arg22 == String.class) {
                                    v15_4 = v12.asString();
                                    goto label_2;
                                }
                                else {
                                    if(arg22 != Short.TYPE && arg22 != Short.class) {
                                        if(arg22 != Byte.TYPE && arg22 != Byte.class) {
                                            goto label_391;
                                        }

                                        v15_5 = Byte.valueOf(v12.asByte());
                                        goto label_2;
                                    }

                                    v15_6 = Short.valueOf(v12.asShort());
                                    goto label_2;
                                }
                            }

                            v15_7 = Double.valueOf(v12.asDouble());
                            goto label_2;
                        }

                        v15_8 = Long.valueOf(v12.asLong());
                        goto label_2;
                    }

                    v15_9 = Integer.valueOf(v12.asInt());
                    goto label_2;
                }

            label_319:
                v15_10 = Float.valueOf(v12.asFloat());
                goto label_2;
            }
            catch(NumberFormatException v18) {
                goto label_391;
            }
        }

        goto label_319;
    label_391:
        jsonData = new JsonValue(v12.asString());
        goto label_396;
    label_550:
        jsonData = v12;
    label_396:
        if(!jsonData.isBoolean()) {
            goto label_415;
        }

        if(arg22 != null) {
            try {
                if(arg22 != Boolean.TYPE && arg22 != Boolean.class) {
                    goto label_411;
                }

            label_407:
                v15_11 = Boolean.valueOf(jsonData.asBoolean());
                goto label_2;
            }
            catch(NumberFormatException v18) {
                goto label_411;
            }
        }

        goto label_407;
    label_411:
        jsonData = new JsonValue(jsonData.asString());
    label_415:
        if(!jsonData.isString()) {
            goto label_548;
        }

        String v17 = jsonData.asString();
        if(arg22 != null && arg22 != String.class) {
            try {
                if(arg22 != Integer.TYPE && arg22 != Integer.class) {
                    if(arg22 != Float.TYPE && arg22 != Float.class) {
                        if(arg22 != Long.TYPE && arg22 != Long.class) {
                            if(arg22 != Double.TYPE && arg22 != Double.class) {
                                if(arg22 != Short.TYPE && arg22 != Short.class) {
                                    if(arg22 != Byte.TYPE && arg22 != Byte.class) {
                                        goto label_486;
                                    }

                                    v15_5 = Byte.valueOf(v17);
                                    goto label_2;
                                }

                                v15_6 = Short.valueOf(v17);
                                goto label_2;
                            }

                            v15_7 = Double.valueOf(v17);
                            goto label_2;
                        }

                        v15_8 = Long.valueOf(v17);
                        goto label_2;
                    }

                    v15_10 = Float.valueOf(v17);
                    goto label_2;
                }

                v15_9 = Integer.valueOf(v17);
                goto label_2;
            }
            catch(NumberFormatException v18) {
            }

        label_486:
            if(arg22 != Boolean.TYPE && arg22 != Boolean.class) {
                if(arg22 != Character.TYPE && arg22 != Character.class) {
                    if(ClassReflection.isAssignableFrom(Enum.class, arg22)) {
                        Object[] v7 = arg22.getEnumConstants();
                        int v10 = 0;
                        int v13 = v7.length;
                        while(v10 < v13) {
                            Object v8 = v7[v10];
                            if(v17.equals(this.convertToString(((Enum)v8)))) {
                                v15 = v8;
                                goto label_2;
                            }
                            else {
                                ++v10;
                                continue;
                            }
                        }
                    }

                    if(arg22 != CharSequence.class) {
                        goto label_533;
                    }

                    v15_4 = v17;
                    goto label_2;
                label_533:
                    throw new SerializationException("Unable to convert value to required type: " + jsonData + " (" + arg22.getName() + ")");
                }

                Character v15_12 = Character.valueOf(v17.charAt(0));
                goto label_2;
            }

            v15_11 = Boolean.valueOf(v17);
            goto label_2;
        }

        v15_4 = v17;
        goto label_2;
    label_548:
        v15 = null;
    label_2:
        return v15_5;
    }

    public Object readValue(Class arg2, JsonValue jsonData) {
        return this.readValue(arg2, null, jsonData);
    }

    public Object readValue(String name, Class arg4, Object arg5, JsonValue jsonMap) {
        JsonValue v0 = jsonMap.get(name);
        if(v0 != null) {
            arg5 = this.readValue(arg4, null, v0);
        }

        return arg5;
    }

    public Object readValue(Class arg2, Class elementType, Object arg4, JsonValue jsonData) {
        return this.readValue(arg2, elementType, jsonData);
    }

    public Object readValue(String name, Class arg3, Class elementType, Object arg5, JsonValue jsonMap) {
        JsonValue v0 = jsonMap.get(name);
        if(v0 != null) {
            arg5 = this.readValue(arg3, elementType, v0);
        }

        return arg5;
    }

    public void setDefaultSerializer(Serializer defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    public void setElementType(Class type, String fieldName, Class elementType) {
        Object v1 = this.getFields(type).get(fieldName);
        if(v1 == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + type.getName() + ")");
        }

        ((FieldMetadata)v1).elementType = elementType;
    }

    public void setEnumNames(boolean enumNames) {
        this.enumNames = enumNames;
    }

    public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
        this.ignoreUnknownFields = ignoreUnknownFields;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean quoteLongValues) {
        this.quoteLongValues = quoteLongValues;
    }

    public void setSerializer(Class arg2, Serializer arg3) {
        this.classToSerializer.put(arg2, arg3);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setUsePrototypes(boolean usePrototypes) {
        this.usePrototypes = usePrototypes;
    }

    public void setWriter(Writer writer) {
        JsonWriter v4_1;
        if(!(writer instanceof JsonWriter)) {
            v4_1 = new JsonWriter(writer);
        }

        this.writer = v4_1;
        this.writer.setOutputType(this.outputType);
        this.writer.setQuoteLongValues(this.quoteLongValues);
    }

    public String toJson(Object object) {
        Class v1;
        Class v0 = null;
        if(object == null) {
            v1 = v0;
        }
        else {
            v1 = object.getClass();
        }

        return this.toJson(object, v1, v0);
    }

    public String toJson(Object object, Class knownType, Class elementType) {
        StringWriter v0 = new StringWriter();
        this.toJson(object, knownType, elementType, ((Writer)v0));
        return v0.toString();
    }

    public String toJson(Object object, Class knownType) {
        return this.toJson(object, knownType, null);
    }

    public void toJson(Object object, Class knownType, Class elementType, Writer writer) {  // has try-catch handlers
        JsonWriter v2 = null;
        this.setWriter(writer);
        try {
            this.writeValue(object, knownType, elementType);
        }
        catch(Throwable v0) {
            StreamUtils.closeQuietly(this.writer);
            this.writer = v2;
            throw v0;
        }

        StreamUtils.closeQuietly(this.writer);
        this.writer = v2;
    }

    public void toJson(Object object, FileHandle file) {
        Class v0;
        Class v1 = null;
        if(object == null) {
            v0 = v1;
        }
        else {
            v0 = object.getClass();
        }

        this.toJson(object, v0, v1, file);
    }

    public void toJson(Object object, Class knownType, Class elementType, FileHandle file) {  // has try-catch handlers
        Writer v1;
        try {
            v1 = file.writer(false, "UTF-8");
            this.toJson(object, knownType, elementType, v1);
        }
        catch(Throwable v2) {
        }
        catch(Exception v0) {
            try {
                throw new SerializationException("Error writing file: " + file, ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)v1));
    }

    public void toJson(Object object, Writer writer) {
        Class v0;
        Class v1 = null;
        if(object == null) {
            v0 = v1;
        }
        else {
            v0 = object.getClass();
        }

        this.toJson(object, v0, v1, writer);
    }

    public void toJson(Object object, Class knownType, FileHandle file) {
        this.toJson(object, knownType, null, file);
    }

    public void toJson(Object object, Class knownType, Writer writer) {
        this.toJson(object, knownType, null, writer);
    }

    public void writeArrayEnd() {  // has try-catch handlers
        try {
            this.writer.pop();
            return;
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }
    }

    public void writeArrayStart() {  // has try-catch handlers
        try {
            this.writer.array();
            return;
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }
    }

    public void writeArrayStart(String name) {  // has try-catch handlers
        try {
            this.writer.name(name);
            this.writer.array();
            return;
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }
    }

    public void writeField(Object object, String name) {
        this.writeField(object, name, name, null);
    }

    public void writeField(Object object, String fieldName, String jsonName, Class elementType) {  // has try-catch handlers
        Class v5 = object.getClass();
        Object v3 = this.getFields(v5).get(fieldName);
        if(v3 == null) {
            throw new SerializationException("Field not found: " + fieldName + " (" + v5.getName() + ")");
        }

        Field v1 = ((FieldMetadata)v3).field;
        if(elementType == null) {
            elementType = ((FieldMetadata)v3).elementType;
        }

        try {
            this.writer.name(jsonName);
            this.writeValue(v1.get(object), v1.getType(), elementType);
            return;
        }
        catch(Exception v4) {
            v0 = new SerializationException(((Throwable)v4));
            v0.addTrace(v1 + " (" + v5.getName() + ")");
            throw v0;
        }
        catch(SerializationException v0) {
            v0.addTrace(v1 + " (" + v5.getName() + ")");
            throw v0;
        }
        catch(ReflectionException v0_1) {
            throw new SerializationException("Error accessing field: " + v1.getName() + " (" + v5.getName() + ")", ((Throwable)v0_1));
        }
    }

    public void writeField(Object object, String name, Class elementType) {
        this.writeField(object, name, name, elementType);
    }

    public void writeField(Object object, String fieldName, String jsonName) {
        this.writeField(object, fieldName, jsonName, null);
    }

    public void writeFields(Object object) {  // has try-catch handlers
        Object v12;
        Field v4;
        Class v11 = object.getClass();
        Object[] v2 = this.getDefaultValues(v11);
        int v6 = 0;
        Iterator v8 = new OrderedMapValues(this.getFields(v11)).iterator();
        while(true) {
            if(!v8.hasNext()) {
                return;
            }

            Object v9 = v8.next();
            v4 = ((FieldMetadata)v9).field;
            try {
                v12 = v4.get(object);
                if(v2 != null) {
                    goto label_15;
                }

                goto label_50;
            }
            catch(Exception v10) {
                break;
            }
            catch(SerializationException v3) {
                goto label_75;
            }
            catch(ReflectionException v3_1) {
                goto label_60;
            }

        label_15:
            int v7 = v6 + 1;
            try {
                Object v1 = v2[v6];
                if(v12 == null && v1 == null) {
                    v6 = v7;
                    continue;
                }

                if(v12 != null && v1 != null) {
                    if(v12.equals(v1)) {
                        v6 = v7;
                        continue;
                    }
                    else if((v12.getClass().isArray()) && (v1.getClass().isArray())) {
                        this.equals1[0] = v12;
                        this.equals2[0] = v1;
                        if(!Arrays.deepEquals(this.equals1, this.equals2)) {
                            goto label_49;
                        }

                        goto label_47;
                    }
                }

                goto label_49;
            }
            catch(Exception v10) {
                break;
            }
            catch(SerializationException v3) {
                goto label_75;
            }
            catch(ReflectionException v3_1) {
                goto label_60;
            }

        label_47:
            v6 = v7;
            continue;
        label_49:
            v6 = v7;
            try {
            label_50:
                this.writer.name(v4.getName());
                this.writeValue(v12, v4.getType(), ((FieldMetadata)v9).elementType);
                continue;
            }
            catch(Exception v10) {
                break;
            }
            catch(SerializationException v3) {
            label_75:
                v3.addTrace(v4 + " (" + v11.getName() + ")");
                throw v3;
            }
            catch(ReflectionException v3_1) {
            label_60:
                throw new SerializationException("Error accessing field: " + v4.getName() + " (" + v11.getName() + ")", ((Throwable)v3_1));
            }
        }

        v3 = new SerializationException(((Throwable)v10));
        v3.addTrace(v4 + " (" + v11.getName() + ")");
        throw v3;
    }

    public void writeObjectEnd() {  // has try-catch handlers
        try {
            this.writer.pop();
            return;
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }
    }

    public void writeObjectStart() {  // has try-catch handlers
        try {
            this.writer.object();
            return;
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }
    }

    public void writeObjectStart(Class actualType, Class knownType) {  // has try-catch handlers
        try {
            this.writer.object();
            if(knownType == null) {
                goto label_4;
            }
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        if(knownType != actualType) {
        label_4:
            this.writeType(actualType);
        }
    }

    public void writeObjectStart(String name) {  // has try-catch handlers
        try {
            this.writer.name(name);
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        this.writeObjectStart();
    }

    public void writeObjectStart(String name, Class actualType, Class knownType) {  // has try-catch handlers
        try {
            this.writer.name(name);
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        this.writeObjectStart(actualType, knownType);
    }

    public void writeType(Class type) {  // has try-catch handlers
        if(this.typeName != null) {
            String v0 = this.getTag(type);
            if(v0 == null) {
                v0 = type.getName();
            }

            try {
                this.writer.set(this.typeName, v0);
            }
            catch(IOException v1) {
                throw new SerializationException(((Throwable)v1));
            }
        }
    }

    public void writeValue(String name, Object value, Class knownType) {  // has try-catch handlers
        try {
            this.writer.name(name);
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        this.writeValue(value, knownType, null);
    }

    public void writeValue(String name, Object value, Class knownType, Class elementType) {  // has try-catch handlers
        try {
            this.writer.name(name);
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        this.writeValue(value, knownType, elementType);
    }

    public void writeValue(String name, Object value) {  // has try-catch handlers
        Class v2 = null;
        try {
            this.writer.name(name);
            if(value != null) {
                goto label_9;
            }
        }
        catch(IOException v0) {
            throw new SerializationException(((Throwable)v0));
        }

        this.writeValue(value, v2, v2);
        return;
    label_9:
        this.writeValue(value, value.getClass(), v2);
    }

    public void writeValue(Object value, Class knownType, Class elementType) {  // has try-catch handlers
        Object v5;
        Iterator v8;
        int v12;
        int v7;
        if(value == null) {
            try {
                this.writer.value(null);
                return;
            label_6:
                if((knownType == null || !knownType.isPrimitive()) && (knownType != String.class && knownType != Integer.class && knownType != Boolean.class && knownType != Float.class && knownType != Long.class && knownType != Double.class && knownType != Short.class && knownType != Byte.class && knownType != Character.class)) {
                    Class v3 = value.getClass();
                    if(!v3.isPrimitive() && v3 != String.class && v3 != Integer.class && v3 != Boolean.class && v3 != Float.class && v3 != Long.class && v3 != Double.class && v3 != Short.class && v3 != Byte.class && v3 != Character.class) {
                        if((value instanceof Serializable)) {
                            this.writeObjectStart(v3, knownType);
                            value.write(this);
                            this.writeObjectEnd();
                            return;
                        }
                        else {
                            Object v13 = this.classToSerializer.get(v3);
                            if(v13 != null) {
                                ((Serializer)v13).write(this, value, knownType);
                                return;
                            }
                            else if((value instanceof Array)) {
                                if(knownType != null && v3 != knownType && v3 != Array.class) {
                                    throw new SerializationException("Serialization of an Array other than the known type is not supported.\nKnown type: " + knownType + "\nActual type: " + v3);
                                }

                                this.writeArrayStart();
                                Object v4 = value;
                                v7 = 0;
                                v12 = ((Array)v4).size;
                                while(v7 < v12) {
                                    this.writeValue(((Array)v4).get(v7), elementType, null);
                                    ++v7;
                                }

                                this.writeArrayEnd();
                                return;
                            }
                            else {
                                if(!(value instanceof Collection)) {
                                    goto label_169;
                                }

                                if(this.typeName != null && v3 != ArrayList.class && (knownType == null || knownType != v3)) {
                                    this.writeObjectStart(v3, knownType);
                                    this.writeArrayStart("items");
                                    v8 = ((Collection)value).iterator();
                                    while(v8.hasNext()) {
                                        this.writeValue(v8.next(), elementType, null);
                                    }

                                    this.writeArrayEnd();
                                    this.writeObjectEnd();
                                    return;
                                }

                                this.writeArrayStart();
                                v8 = ((Collection)value).iterator();
                                while(v8.hasNext()) {
                                    this.writeValue(v8.next(), elementType, null);
                                }

                                this.writeArrayEnd();
                                return;
                            label_169:
                                if(!v3.isArray()) {
                                    goto label_187;
                                }

                                if(elementType == null) {
                                    elementType = v3.getComponentType();
                                }

                                int v10 = ArrayReflection.getLength(value);
                                this.writeArrayStart();
                                for(v7 = 0; v7 < v10; ++v7) {
                                    this.writeValue(ArrayReflection.get(value, v7), elementType, null);
                                }

                                this.writeArrayEnd();
                                return;
                            label_187:
                                if(!(value instanceof ObjectMap)) {
                                    goto label_214;
                                }

                                if(knownType == null) {
                                    knownType = ObjectMap.class;
                                }

                                this.writeObjectStart(v3, knownType);
                                v8 = ((ObjectMap)value).entries().iterator();
                                while(v8.hasNext()) {
                                    v5 = v8.next();
                                    this.writer.name(this.convertToString(((Entry)v5).key));
                                    this.writeValue(((Entry)v5).value, elementType, null);
                                }

                                this.writeObjectEnd();
                                return;
                            label_214:
                                if(!(value instanceof ArrayMap)) {
                                    goto label_244;
                                }

                                if(knownType == null) {
                                    knownType = ArrayMap.class;
                                }

                                this.writeObjectStart(v3, knownType);
                                Object v11 = value;
                                v7 = 0;
                                v12 = ((ArrayMap)v11).size;
                                while(v7 < v12) {
                                    this.writer.name(this.convertToString(((ArrayMap)v11).keys[v7]));
                                    this.writeValue(((ArrayMap)v11).values[v7], elementType, null);
                                    ++v7;
                                }

                                this.writeObjectEnd();
                                return;
                            label_244:
                                if(!(value instanceof Map)) {
                                    goto label_271;
                                }

                                if(knownType == null) {
                                    knownType = HashMap.class;
                                }

                                this.writeObjectStart(v3, knownType);
                                v8 = ((Map)value).entrySet().iterator();
                                while(v8.hasNext()) {
                                    v5 = v8.next();
                                    this.writer.name(this.convertToString(((Map$Entry)v5).getKey()));
                                    this.writeValue(((Map$Entry)v5).getValue(), elementType, null);
                                }

                                this.writeObjectEnd();
                                return;
                            label_271:
                                if(!ClassReflection.isAssignableFrom(Enum.class, v3)) {
                                    goto label_301;
                                }

                                if(this.typeName != null && (knownType == null || knownType != v3)) {
                                    if(v3.getEnumConstants() == null) {
                                        v3 = v3.getSuperclass();
                                    }

                                    this.writeObjectStart(v3, null);
                                    this.writer.name("value");
                                    this.writer.value(this.convertToString(((Enum)value)));
                                    this.writeObjectEnd();
                                    return;
                                }

                                this.writer.value(this.convertToString(((Enum)value)));
                                return;
                            label_301:
                                this.writeObjectStart(v3, knownType);
                                this.writeFields(value);
                                this.writeObjectEnd();
                                return;
                            }
                        }
                    }

                    this.writeObjectStart(v3, null);
                    this.writeValue("value", value);
                    this.writeObjectEnd();
                    return;
                }

                this.writer.value(value);
                return;
            }
            catch(IOException v6) {
                throw new SerializationException(((Throwable)v6));
            }
        }
        else {
            goto label_6;
        }
    }

    public void writeValue(Object value) {
        Class v1 = null;
        if(value == null) {
            this.writeValue(value, v1, v1);
        }
        else {
            this.writeValue(value, value.getClass(), v1);
        }
    }

    public void writeValue(Object value, Class knownType) {
        this.writeValue(value, knownType, null);
    }
}

