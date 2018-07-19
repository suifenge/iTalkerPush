package com.suifeng.web.italker.push.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider
import java.io.*
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.time.LocalDateTime

/**
 * 用于设置Jersey的Json转换器
 * 用于替换JacksonJsonProvider
 *
 *
 * 该工具类完成了，把Http请求中的请求数据转换为Model实体，
 * 同时也实现了把返回的Model实体转换为Json字符串
 * 并输出到Http的返回体中。
 *
 * @param <T> 任意类型范型定义
</T> */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GsonProvider<T> : MessageBodyReader<T>, MessageBodyWriter<T> {

    override fun isReadable(type: Class<*>, genericType: Type,
                            annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return true
    }

    /**
     * 把Json的字符串数据, 转换为T类型的实例
     */
    @Throws(IOException::class, WebApplicationException::class)
    override fun readFrom(type: Class<T>, genericType: Type, annotations: Array<Annotation>,
                          mediaType: MediaType, httpHeaders: MultivaluedMap<String, String>,
                          entityStream: InputStream): T {
        JsonReader(InputStreamReader(entityStream, "UTF-8")).use { reader -> return gson.fromJson(reader, genericType) }
    }

    override fun isWriteable(type: Class<*>, genericType: Type,
                             annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return true
    }

    override fun getSize(t: T, type: Class<*>, genericType: Type,
                         annotations: Array<Annotation>, mediaType: MediaType): Long {
        return -1
    }

    /**
     * 把一个T类的实例输出到Http输出流中
     */
    @Throws(IOException::class, WebApplicationException::class)
    override fun writeTo(t: T, type: Class<*>, genericType: Type, annotations: Array<Annotation>,
                         mediaType: MediaType, httpHeaders: MultivaluedMap<String, Any>,
                         entityStream: OutputStream) {
        //TypeAdapter<T> adapter = gson.getAdapter((TypeToken<T>) TypeToken.get(genericType));
        gson.newJsonWriter(OutputStreamWriter(entityStream, Charset.forName("UTF-8"))).use { jsonWriter ->
            gson.toJson(t, genericType, jsonWriter)
            jsonWriter.close()
        }
    }

    companion object {
        // 共用一个全局的Gson
        /**
         * 取得一个全局的Gson
         *
         * @return Gson
         */
        val gson: Gson

        init {
            // Gson 初始化
            val builder = GsonBuilder()
                    // 序列化为null的字段
                    .serializeNulls()
                    // 仅仅处理带有@Expose注解的变量
                    .excludeFieldsWithoutExposeAnnotation()
                    // 支持Map
                    .enableComplexMapKeySerialization()
            // 添加对Java8LocalDateTime时间类型的支持
            builder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
            gson = builder.create()
        }
    }
}