package com.gmainer.budgetbook.testhelper

import com.gmainer.budgetbook.common.model.IConvertableEnum
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Any.toJson(): String {
    return GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeHierarchyAdapter(IConvertableEnum::class.java, ConvertableEnumAdapter<IConvertableEnum>())
        .create()
        .toJson(this)
}

inline fun <reified T> String.toEntity(): T {
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeHierarchyAdapter(IConvertableEnum::class.java, ConvertableEnumAdapter<IConvertableEnum>())
        .create()
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}

class LocalDateAdapter : JsonSerializer<LocalDate?>, TypeAdapter<LocalDate?>() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }

    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, localDate: LocalDate?) {
        jsonWriter.value(localDate?.format(formatter))
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): LocalDate {
        return LocalDate.parse(jsonReader.nextString(), formatter)
    }
}

class ConvertableEnumAdapter<T : IConvertableEnum> : JsonSerializer<T> {
    override fun serialize(src: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return context.serialize(src.code)
    }
}
