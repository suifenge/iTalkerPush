package com.suifeng.web.italker.push.provider

import com.google.gson.*

import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQuery
import java.util.Locale

/**
 * LocalDateTime 是一个Java8的新时间类型，
 * 使用起来比常规的Date更加Nice；
 * 但是Gson目前并没有默认支持对LocalDateTime的转换
 *
 *
 * 该工具类主要是为了解决LocalDateTime与Json字符串相互转换的问题
 */
class LocalDateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    /**
     * 把一个LocalDateTime格式的时间转换为Gson支持的JsonElement
     *
     *
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     *
     *
     *
     *
     * In the implementation of this call-back method, you should consider invoking
     * [JsonSerializationContext.serialize] method to create JsonElements for any
     * non-trivial field of the `src` object. However, you should never invoke it on the
     * `src` object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param src       the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @return a JsonElement corresponding to the specified object.
     */
    override fun serialize(src: LocalDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    /**
     * 把一个Gson的JsonElement转换为LocalDateTime时间格式
     *
     *
     *
     *
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     *
     *
     *
     *
     * In the implementation of this call-back method, you should consider invoking
     * [JsonDeserializationContext.deserialize] method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing `json` since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of `T`
     * @throws JsonParseException if json is not in the expected format of `typeOfT`
     */
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        return FORMATTER.parse<LocalDateTime>(json.asString, { LocalDateTime.from(it) })
    }

    companion object {
        /**
         * 时间转换的格式为：yyyy-MM-dd'T'HH:mm:ss.SSS
         */
        val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
    }
}