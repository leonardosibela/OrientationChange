package com.sibela.examples.orientationchange.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeHierarchyAdapter(Enum.class, enumDeserializer());

        return gsonBuilder.create();
    }

    public static JsonElement toJson(Object jsonObject) {
        if (jsonObject == null)
            return null;

        return createGson().toJsonTree(jsonObject);
    }

    public static String toJsonAsString(Object jsonObject){
        if (jsonObject == null)
            return null;

        return createGson().toJson(jsonObject);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> t) {
        JsonElement jsonElement = fromJson(json, JsonElement.class);
        return fromJsonList(jsonElement, t);
    }

    public static <T> List<T> fromJsonList(JsonElement jsonElement, Class<T> t) {
        List<T> ret = new ArrayList<T>();
        Gson gson = createGson();

        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement item = jsonArray.get(i);

            T obj = (T) gson.fromJson(item, t);
            ret.add(obj);
        }

        return ret;
    }

    public static <T> T fromJson(String json, Class<T> ownerClazz) {
        Gson gson = createGson();
        return gson.fromJson(json, ownerClazz);
    }

    public static <T> T fromJson(JsonElement jsonElement, Class<T> ownerClazz) {
        Gson gson = createGson();
        return gson.fromJson(jsonElement, ownerClazz);
    }

    public static Integer getAsInt(JsonElement element){
        return element.isJsonNull() ? null : element.getAsInt();
    }

    public static String getAsString(JsonElement element){
        return element.isJsonNull() ? null : element.getAsString();
    }

    public static Double getAsDouble(JsonElement element){
        return element.isJsonNull() ? null : element.getAsDouble();
    }

    public static Boolean getAsBoolean(JsonElement element){
        return element.isJsonNull() ? null : element.getAsBoolean();
    }

    public static Boolean hasFieldEqual(JsonElement element, String key, String valueToCompare){
        JsonObject jsonElement = element.getAsJsonObject();
        if (element.isJsonNull() || !jsonElement.has(key)) {
            return false;
        }

        String value = jsonElement.get(key).getAsString();

        return value.equals(valueToCompare);
    }

    private static Object enumDeserializer() {
        return new JsonDeserializer<Enum<?>>() {

            @Override
            public Enum<?> deserialize(JsonElement json, Type type, JsonDeserializationContext arg2) throws JsonParseException {
                return convertJsonToEnum(json, type);
            }
        };
    }

    private static Enum<?> convertJsonToEnum(JsonElement json, Type type) {
        String name = "";

        if (json.isJsonObject() && json.getAsJsonObject().has("nome")) {
            name = json.getAsJsonObject().get("nome").getAsString();
        } else if (json.isJsonPrimitive()) {
            name = json.getAsString();
        }else{
            throw new JsonParseException("Invalid json structure for enum");
        }

        for (Enum<?> e : asClass(type).getEnumConstants()) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }

        throw new JsonParseException("Invalid json structure for enum");

    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Enum<?>> asClass(Type type) {
        return (Class<? extends Enum<?>>) type;
    }


    public static JsonElement toJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        return new JsonParser().parse(json);
    }
}
