package com.example.mathTest.base

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * A [Converter.Factory] that returns null for empty response bodies.
 * This is useful when the server returns an empty response for successful requests
 * that don't have a body (e.g., 204 No Content).
 */
class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit
    ): Converter<ResponseBody?, *>? {
        val delegate: Converter<ResponseBody?, *> =
            retrofit.nextResponseBodyConverter<Any?>(this, type, annotations)
        return Converter { body: ResponseBody? ->
            if (body!!.contentLength() == 0L) return@Converter null
            delegate.convert(body)
        } as Converter<ResponseBody?, Any?>
    }
}